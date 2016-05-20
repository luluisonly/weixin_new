package com.bokesoft.thirdparty.weixin.flow;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyWasteMessage;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteService;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinMessageFlowLocalServiceImpl implements WeixinMessageFlowLocalService {

	private static final long serialVersionUID = -4080416332917963354L;
	
	private WeixinRemoteMessage weixinRemoteMessage;

	public WeixinRemoteMessage getWeixinRemoteMessage() {
		return weixinRemoteMessage;
	}

	public void setWeixinRemoteMessage(WeixinRemoteMessage weixinRemoteMessage) {
		this.weixinRemoteMessage = weixinRemoteMessage;
	}

	public WeixinMessage handleFlow(WeixinSession session, WeixinMessage message) throws Exception {
		WeixinRemoteMessage remoteMessage = message.copyWeixinRemoteMessage(session.getPublicName(), null, "", "");
		remoteMessage.setBasicWeixinRemoteMessage(session, weixinRemoteMessage);
		remoteMessage.setBasicWeixinMessage(message);
		return handleWeixinRemoteMessage(session, remoteMessage);
	}
	
	private WeixinMessage handleWeixinRemoteMessage(WeixinSession session,
			WeixinRemoteMessage remoteMessage) throws Exception{
		WeixinContext context = session.getWeixinContext();
		WeixinRemoteService remoteService = context.getWeixinRemoteService();
		SOAResponseMessage responseMessage = remoteService.doService(session, remoteMessage);
		if(responseMessage.getCode() == 300){
			WeixinMessageFlowLocalService flowLocalService = session.getWeixinMessageFlowLocalService();
			if (flowLocalService == null) {
				flowLocalService = new WeixinMessageFlowLocalServiceImpl();
				flowLocalService.setWeixinRemoteMessage(remoteMessage);
				session.setWeixinMessageFlowLocalService(flowLocalService);
			}
		}else if(responseMessage.getCode() == 301){
			session.setWeixinMessageFlowLocalService(null);
		}else if(responseMessage.getCode() == 302){
			session.setWeixinMessageFlowLocalService(null);
			return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
		}
		String message = responseMessage.getMessage();
		if (message == null || message.length() ==0) {
			Object data = responseMessage.getData();
			if (data == null) {
				return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
			}else if(data instanceof JSONObject){
//				JSONObject jsonObject = ((JSONObject) data);
//				if (jsonObject.isNullObject()) {
//					return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
//				}
				return context.buildWeixinMessage((JSONObject) data);
			}else{
				return (WeixinMessage)data;
			}
//			JSONObject data = (JSONObject)responseMessage.getData();
//			if (data == null) {
//				return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
//			}else{
//				return applicationContext.buildWeixinMessage(data);
//			}
			
		}
		return new WeixinReplyTextMessage(message);
//		if (message instanceof String) {
//			return new WeixinReplyTextMessage((String)message);
//		}else if(message instanceof JSONObject){
//			return  applicationContext.buildWeixinMessage((JSONObject)message);
//		}
//		LOGGER.error("remote error,your response is not null and is not String or an json object:"+remoteMessage.toString());
//		LOGGER.error("remote error,your response is not null and is not String or an json object:"+responseMessage.toString());
//		return new WeixinReplyTextMessage("网络异常");
	}

	
}
