package com.bokesoft.thirdparty.model;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.common.MD5Token;

/**
 * 新用户注册
 */
public class RegisterHandle extends ModelHandle  {

	private SOAResponseMessage VERIFY_CODE_NOT_THROUGH = new SOAResponseMessage(805,"VERIFY_CODE_NOT_THROUGH");
	
	private SOAResponseMessage USER_EXIST = new SOAResponseMessage(806,"THE UNAME EXISTED");
	
	private static final Logger logger = Logger.getLogger(RegisterHandle.class);
	
	
	private boolean validate_verify_code( HttpServletRequest request){
		String verify_code = request.getParameter("verify_code");
		HttpSession session = request.getSession();
	    return session.getAttribute("verify_code").toString().toLowerCase().equals(verify_code.toLowerCase());
	}
	
	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		if (!validate_verify_code(request)) {
			return VERIFY_CODE_NOT_THROUGH;
		}
		String uname = request.getParameter("username");
		String password = request.getParameter("password");
		synchronized (this) {
			WeixinPublicNumber temp = context.getWeixinPublicNumber4Edit(uname);
			if (temp != null) {
				return USER_EXIST;
			}
			password = MD5Token.getInstance().getLongToken(password, "UTF-8");
			WeixinPublicNumber publicNumber = WeixinPublicNumber.createDefault();
			publicNumber.setUname(uname);
			publicNumber.setPassword(password);
			publicNumber.setStatus(0);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH,5);
			publicNumber.setService_time(calendar.getTimeInMillis());
			context.addWeixinPublicNumber(publicNumber);
			context.putWeixinPublicNumber2Local(uname, publicNumber);
		}
		logger.info("user registered:"+uname);
		return SOAResponseMessage.SUCCESS;
	}
	

}
