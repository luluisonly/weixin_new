package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;


public class WeixinReplyVideoMessage extends WeixinReplyMessage implements WeixinMessage {

	private static final long serialVersionUID = -4189790626869890006L;

	private String mediaId = null;
	
	private String thumbMediaId = null;

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public WeixinReplyVideoMessage() {
		this.setMsgType(VIDEO);
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
		WeixinReplyVideoMessage message = new WeixinReplyVideoMessage();
		message.setMediaId(this.mediaId);
		return (T)message;
	}
	
	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logResponseVideoMessage(session, this);
	}

}
