package com.zaofans.weixin.remotehandle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class ScanCellHandle implements WeixinRemoteTextMessageCellHandle {

	private static final Logger LOGGER = Logger.getLogger(ScanCellHandle.class);
	private static final String SCANURL = SharedBundle.getProperties("server.soa.usercenter.updateRegister");
	@Override
	public SOAResponseMessage handleWeixinRemoteTextMessage(
			WeixinRmoteSession cache, WeixinRemoteTextMessage remoteTextMessage)
			 {
		String openid = remoteTextMessage.getFromUserName();
		WeixinEventMessage eventMessage = remoteTextMessage.getEventMessage();
		String scene_id = eventMessage.getEventKey();
		Map<String, String> params = new HashMap<String, String>();
		params.put("__sessionid", openid);
		params.put("__wxopenid", openid);
		params.put("SCENEID", scene_id);
		try {
			String  result = SimpleHttpClient.invokePost4String(SCANURL,
						params, 7200);
			JSONObject resJson = JSONObject.parseObject(result);
			String roleLevel = resJson.getString("ROLELEVEL");
			return new SOAResponseMessage(0, roleLevel);
		} catch (IOException e) {
			LOGGER.error(e);
		}
		return new SOAResponseMessage(1000,"scan handler failed");
	}

	@Override
	@Deprecated
	public String getHandleName() {
		// TODO Auto-generated method stub
		return "Subscribe";
	}

}
