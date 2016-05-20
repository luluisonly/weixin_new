package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;


public class WeixinReplyVoiceMessage extends WeixinReplyMessage implements WeixinMessage {

	private static final long serialVersionUID = -1880598183516216759L;
	private String mediaId = null;

	public WeixinReplyVoiceMessage() {
		this.setMsgType(VOICE);
		this.setReply(true);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinReplyVoiceMessage message = new WeixinReplyVoiceMessage();
		message.setMediaId(this.mediaId);
		return (T)message;
	}
	
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		throw new RuntimeException("not will walk here");
	}
	
	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logResponseVoiceMessage(session, this);
	}

}