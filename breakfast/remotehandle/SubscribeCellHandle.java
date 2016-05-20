package remotehandle;

import java.util.HashMap;
import java.util.Map;

import openHandle.GetWeixinUserInfoHandle;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;
import common.BFConstant;

public class SubscribeCellHandle implements WeixinRemoteTextMessageCellHandle{

	private static Logger LOGGER = Logger.getLogger(SubscribeCellHandle.class);

	@Override
	public SOAResponseMessage handleWeixinRemoteTextMessage(
			WeixinRmoteSession cache, WeixinRemoteTextMessage remoteTextMessage)
			throws Exception {
		String openid = remoteTextMessage.getFromUserName();
		LOGGER.debug("-----------------Subscribe openid :"+ openid);
		JSONObject userInfo = GetWeixinUserInfoHandle.getUserInfo(BFConstant.UNAME, openid);
		LOGGER.debug("-----------------Userinfo :"+ userInfo);
		if(userInfo != null){
			Map<String, String> params = new HashMap<String, String>();
			params.put("ID", openid);
			params.put("WXID", openid);
			params.put("NAME", userInfo.getString("nickname"));
			params.put("PORTRAIT", userInfo.getString("headimgurl"));
			params.put("SEX", userInfo.getString("sex"));
			LOGGER.debug("-----------------params :"+ params);
			//String result = SimpleHttpClient.invokePost4String("http://test.5proapp.com:8888/usercenter/user/add", params, 3000);
			//LOGGER.debug("-----------------result :"+ result);
		}
		return null;
	}

	@Override
	@Deprecated
	public String getHandleName() {
		// TODO Auto-generated method stub
		return "Subscribe";
	}

	
	
}
