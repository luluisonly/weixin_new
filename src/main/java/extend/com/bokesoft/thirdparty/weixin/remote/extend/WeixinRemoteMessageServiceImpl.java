package com.bokesoft.thirdparty.weixin.remote.extend;

import java.util.List;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowService;

public class WeixinRemoteMessageServiceImpl implements WeixinRemoteMessageService{

	private static final Logger LOGGER = Logger.getLogger(WeixinRemoteMessageServiceImpl.class);
	
	public SOAResponseMessage doService(WeixinRmoteSession session,WeixinRemoteMessage remoteMessage) throws Exception {
		WeixinRemoteContext remoteContext = session.getWeixinContext();
		List<WeixinRemoteMessageServiceFilter> filters = remoteContext.getWeixinRemoteMessageServiceFilters();
		SOAResponseMessage responseMessage = null;
		for (WeixinRemoteMessageServiceFilter filter:filters) {
			responseMessage = filter.doFilter(session, remoteMessage);
			if (responseMessage != null) {
//				WeixinMessageGroup group = new WeixinMessageGroup();
//				group.setReceiveMessage(remoteMessage);
//				group.setReplyMessage(responseMessage.get);
//				break;
				return responseMessage;
			}
		}
		WeixinMessageFlowService flowLocalService = session.getWeixinMessageFlowService();
		if (flowLocalService != null) {
			return flowLocalService.handleFlow(session, remoteMessage);
		} else {
			WeixinRemoteMessageHandle remoteMessageHandle = remoteContext.getWeixinRemoteMessageHandle();
			String msgType = remoteMessage.getMsgType();
			if (msgType == WeixinRemoteMessage.REMOTE_TEXT) {
				return remoteMessageHandle.handleRemoteTextMessage(session, (WeixinRemoteTextMessage)remoteMessage);
			}else if(msgType == WeixinRemoteMessage.REMOTE_LOCATIONEVENT) {
				return remoteMessageHandle.handleRemoteLocationEventMessage(session, (WeixinRemoteLocationEventMessage)remoteMessage);
			}else{
				LOGGER.error("no nuch remote message type:"+msgType);
				return SOAResponseMessage.NULL;
			}
		}
		//cache.getWeixinMessageProcedure().addWeixinMessage(messageGroup);
	}
	
}
