package com.zaofans.weixin.remotehandle;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class CheckcodeQueryHandle implements WeixinRemoteTextMessageCellHandle {

	private static final String QUERYURL = SharedBundle.getProperties("server.soa.scm.query");

	@Override
	public SOAResponseMessage handleWeixinRemoteTextMessage(WeixinRmoteSession cache,
			WeixinRemoteTextMessage remoteTextMessage) throws Exception {
		String openid = remoteTextMessage.getFromUserName();
		Map<String, String> params = new HashMap<String, String>();
		params.put("WXID", openid);
		String ret = SimpleHttpClient.invokePost4String(QUERYURL, params, 7200);
		JSONObject result = JSONObject.parseObject(ret);
		return new SOAResponseMessage(0, result.getString("result"));
	}

	@Override
	public String getHandleName() {
		return "orderquery";
	}

	// public static void main(String[] args) throws Throwable {
	// String openid = "onbRUsyIXOMPNETJ1IkjJGSVAu4A";
	// String orderID = "20150901aaagT1";
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("__sessionid", openid);
	// String ret =
	// SimpleHttpClient.invokeGet4String("http://www.zaofans.com/zaofans_test/orderform/orderForm/query/"+orderID,
	// params, 7200);
	// System.out.println(ret);
	// }
}
