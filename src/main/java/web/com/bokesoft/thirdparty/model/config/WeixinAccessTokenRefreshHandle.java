package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

/**
 * 
 * 强制刷新AccessToken
 *
 */

public class WeixinAccessTokenRefreshHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		WeixinPublicNumber publicNumber = userinfo.getWeixinPublicNumber();
		String access_token = publicNumber.refreshAccess_token(context);
		return new SOAResponseMessage(0,null,access_token);
	}

}
