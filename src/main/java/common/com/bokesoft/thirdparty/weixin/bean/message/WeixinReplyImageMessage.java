package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;


public class WeixinReplyImageMessage extends WeixinReplyMessage implements WeixinMessage {

	private static final long serialVersionUID = 6575750628886572545L;
	private String mediaId = null;

	public WeixinReplyImageMessage() {
		this.setMsgType(IMAGE);
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
		WeixinReplyImageMessage message = new WeixinReplyImageMessage();
		message.setMediaId(this.mediaId);
		return (T)message;
	}

	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logResponseImageMessage(session, this);
	}
	

}
