package com.zaofans.weixin.remotehandle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class ScanCellHandle4Share implements WeixinRemoteTextMessageCellHandle {

	private static final Logger LOGGER = Logger.getLogger(ScanCellHandle4Share.class);
	private static final String SCANURL = SharedBundle.getProperties("server.soa.usercenter.updateRegister");
	@Override
	public SOAResponseMessage handleWeixinRemoteTextMessage(
			WeixinRmoteSession cache, WeixinRemoteTextMessage remoteTextMessage) throws Exception
			 {
		String openid = remoteTextMessage.getFromUserName();
		Map<String, String> params = new HashMap<String, String>();
		params.put("__sessionid", openid);
		params.put("__wxopenid", openid);
		params.put("SCENEID", "91529");
		try {
			String  result = SimpleHttpClient.invokePost4String(SCANURL,params, 7200);
			JSONObject resJson = JSONObject.parseObject(result);
			String roleLevel = resJson.getString("ROLELEVEL");
			WeixinSession session = (WeixinSession) cache;
			WeixinPublicNumber publicNumber = session.getWeixinPublicNumber();
			WeixinMessage message = publicNumber.getKeywordsReplyMessage("Subscribe_"+roleLevel);
			return new SOAResponseMessage(0, null,JSONObject.parse(message.toString()));
			
		} catch (IOException e) {
			LOGGER.error(e);
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Deprecated
	public String getHandleName() {
		// TODO Auto-generated method stub
		return "Subscribe";
	}

}
