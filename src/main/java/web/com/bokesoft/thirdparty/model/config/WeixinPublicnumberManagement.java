package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

public class WeixinPublicnumberManagement extends ModelHandle{

	private SOAResponseMessage VERIFY_CODE_NOT_THROUGH = new SOAResponseMessage(805,"VERIFY_CODE_NOT_THROUGH");
	
	private SOAResponseMessage USERINFO_NOT_THROUGH = new SOAResponseMessage(806,"USERINFO_NOT_THROUGH");
	
	private boolean validate_verify_code( HttpServletRequest request){
		String verify_code = request.getParameter("verify_code");
		HttpSession session = request.getSession();
	    return session.getAttribute("verify_code").toString().toLowerCase().equals(verify_code.toLowerCase());
	}
	
	
	public SOAResponseMessage handle(WeixinContext context,HttpServletRequest request, UserInfo userinfo) throws Exception {
		if (!validate_verify_code(request)) {
			return VERIFY_CODE_NOT_THROUGH;
		}
		String username = request.getParameter("username");
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber4Edit(username);
		if (publicNumber == null) {
			return USERINFO_NOT_THROUGH;
		}
		String password = request.getParameter("old_password");
		synchronized (publicNumber) {
			publicNumber.setShowPassword(true);
			if (!publicNumber.getPassword().equals(password)) {
				return USERINFO_NOT_THROUGH;
			}
		}
		String new_password = request.getParameter("new_password");
		publicNumber.setPassword(new_password);
		context.updateWeixinPublicNumber(publicNumber);
		return new SOAResponseMessage(0, "修改成功");
	}

}
