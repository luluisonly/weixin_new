package com.bokesoft.thirdparty.model;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;

/**
 * 
 * 登出操作
 *
 */
public class LogoutHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		request.getSession().setAttribute("userinfo", null);
		return SOAResponseMessage.SUCCESS;
	}
	

}
