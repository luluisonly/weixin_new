package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumberType;

public class WeixinAppidUpdateHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		String appid = request.getParameter("appid");
		String appSecret = request.getParameter("appSecret");
		WeixinPublicNumber publicNumber = userinfo.getWeixinPublicNumber(); 
		publicNumber.setApp_id(appid);
		publicNumber.setApp_secret(appSecret);
		publicNumber.setPublic_Type(WeixinPublicNumberType.SERVIC);
		context.updateWeixinPublicNumber( publicNumber);
		return SOAResponseMessage.SUCCESS;
	}

}
