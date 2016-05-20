package com.bokesoft.thirdparty.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

/**
 * 登陆系统
 */
public class LoginHandle extends ModelHandle  {

	private SOAResponseMessage VERIFY_CODE_NOT_THROUGH = new SOAResponseMessage(805,"VERIFY_CODE_NOT_THROUGH");
	
	private SOAResponseMessage USERINFO_NOT_THROUGH = new SOAResponseMessage(806,"USERINFO_NOT_THROUGH");
	
	private SOAResponseMessage PUBLICNUMBER_OUT_DATE = new SOAResponseMessage(807,"PERMISSION_OUT_DATE");
	
	private SOAResponseMessage PUBLICNUMBER_FORBIDDEN = new SOAResponseMessage(808,"PUBLICNUMBER_FORBIDDEN");
	
	private static final Logger logger = Logger.getLogger(LoginHandle.class);
	
	private void addShow_verify_code(HttpSession session){
		int value = (Integer) session.getAttribute("show_verify_code");
		if (value < 2) {
			session.setAttribute("show_verify_code", ++value);
		}
	}
	
	private boolean validate_verify_code( HttpServletRequest request){
		String verify_code = request.getParameter("verify_code");
		if (verify_code == null) {
			return false;
		}
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
	
	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		if (!validate_verify_code(request)) {
			return VERIFY_CODE_NOT_THROUGH;
		}
		String username = request.getParameter("username");
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber4Edit(username);
		if (publicNumber == null) {
			addShow_verify_code(request.getSession());
			return USERINFO_NOT_THROUGH;
		}
		String password = request.getParameter("password");
		synchronized (publicNumber) {
			publicNumber.setShowPassword(true);
			if (!publicNumber.getPassword().equals(password)) {
				addShow_verify_code(request.getSession());
				return USERINFO_NOT_THROUGH;
			}
		}
		request.getSession().removeAttribute("show_verify_code");
		if( -1 == publicNumber.getStatus()) return PUBLICNUMBER_FORBIDDEN;
		if(System.currentTimeMillis() >= publicNumber.getService_time()) return PUBLICNUMBER_OUT_DATE;
		userinfo = new UserInfo();
		userinfo.setUname(username);
		userinfo.setWeixinPublicNumber(publicNumber);
		request.getSession().setAttribute("userinfo", userinfo);
		logger.info("user logined:"+username);
		return SOAResponseMessage.SUCCESS;
	}
	

}
