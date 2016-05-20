package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

public class WeixinRemoteMessageEncryptUpdateHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		String encrypt = request.getParameter("encrypt");
		boolean isEncrypt = Boolean.valueOf(request.getParameter("isEncrypt"));
		WeixinPublicNumber publicNumber = userinfo.getWeixinPublicNumber(); 
		publicNumber.setEncrypt_remote_message(isEncrypt);
		publicNumber.setRemote_message_encrypt(encrypt);
		context.updateWeixinPublicNumber( publicNumber);
		return SOAResponseMessage.SUCCESS;
	}

}
