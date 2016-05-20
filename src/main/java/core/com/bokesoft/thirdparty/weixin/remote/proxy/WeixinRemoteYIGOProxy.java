package com.bokesoft.thirdparty.weixin.remote.proxy;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.common.Encryptor;
import com.bokesoft.thirdparty.weixin.common.YigoHttpRequestClient;
import com.bokesoft.thirdparty.weixin.common.YigoHttpRequestHeader;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteProxy;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

/**
 * 
 * 把微信接收到指令发给YIGO
 * 
 */
public class WeixinRemoteYIGOProxy implements WeixinRemoteProxy {

	public SOAResponseMessage request(WeixinSession session, WeixinRemoteMessage remoteMessage)
			throws Exception {
		WeixinContext context = session.getWeixinContext();
		YigoHttpRequestHeader header = context.getYigoHttpRequestHeader();
		WeixinPublicNumber publicNumber = session.getWeixinPublicNumber();
		String remoteMessageString = null;
		if (publicNumber.isEncrypt_remote_message()) {
			remoteMessageString = Encryptor.encryptData(remoteMessage.toString(), publicNumber.getRemote_message_encrypt());
		}else{
			remoteMessageString =  remoteMessage.toString();
		}
		Object[] params = new Object[] { remoteMessageString, remoteMessage.getUsername(),
				remoteMessage.getPassword() };
		Object returnValue = YigoHttpRequestClient.request(header, remoteMessage.getUrl(), params);
		if (returnValue == null) {
			return SOAResponseMessage.SYSTEM_ERROR;
		}
		JSONObject jsonObject = JSONObject.parseObject((String)returnValue);
		return new SOAResponseMessage(jsonObject.getIntValue(SOAResponseMessage.CODE),
				jsonObject.getString(SOAResponseMessage.MESSAGE),
				jsonObject.get(SOAResponseMessage.DATA));

	}

	public String getProxyTypeName() {
		return "yigo";
	}

	
	
	
	
}
