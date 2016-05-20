package com.bokesoft.thirdparty.model;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumberManagerInfo;

/**
 * 登陆系统
 */
public class ManagerLoginHandle extends ModelHandle  {

	private SOAResponseMessage VERIFY_CODE_NOT_THROUGH = new SOAResponseMessage(805,"VERIFY_CODE_NOT_THROUGH");
	
	private SOAResponseMessage USERINFO_NOT_THROUGH = new SOAResponseMessage(806,"USERINFO_NOT_THROUGH");
	
	private static final Logger logger = Logger.getLogger(ManagerLoginHandle.class);
	
	private void addShow_verify_code(HttpSession session){
		int value = (Integer) session.getAttribute("show_verify_code");
		if (value < 2) {
			session.setAttribute("show_verify_code", ++value);
		}
	}
	
	private boolean validate_verify_code( HttpServletRequest request){
		String verify_code = request.getParameter("verify_code");
		HttpSession session = request.getSession();
		Object temp = session.getAttribute("show_verify_code");
		int value = 0 ;
		if (temp == null) {
			session.setAttribute("show_verify_code",0);
		}else{
			value = (Integer)temp;
		}
		if (value == 2) {
			return session.getAttribute("verify_code").toString().toLowerCase().equals(verify_code.toLowerCase());
		}else{
			return true;
		}
	}
	
	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,UserInfo userinfo) throws Exception {
		if (!validate_verify_code(request)) {
			return VERIFY_CODE_NOT_THROUGH;
		}
		String username = request.getParameter("username");
		WeixinPublicNumberManagerInfo publicNumberManagerInfo = context.getWeixinPublicNumberManagerInfo();
		if (!publicNumberManagerInfo.getManagerName().equals(username)) {
			addShow_verify_code(request.getSession());
			return USERINFO_NOT_THROUGH;
		}
		String password = request.getParameter("password");
		if (!publicNumberManagerInfo.getManagerPassword().equals(password)) {
			addShow_verify_code(request.getSession());
			return USERINFO_NOT_THROUGH;
		}
		Map<String, WeixinPublicNumber> weixinPublicNumbers = context.getWeixinPublicNumbers();
		HttpSession session = request.getSession();
		session.setAttribute("weixinPublicNumbers", weixinPublicNumbers);
		session.removeAttribute("show_verify_code");
		logger.info("manager logined:"+username);
		return SOAResponseMessage.SUCCESS;
	}
	

}
