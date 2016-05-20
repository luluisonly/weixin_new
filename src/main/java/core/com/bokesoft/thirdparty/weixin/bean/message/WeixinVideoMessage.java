package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinVideoMessage extends BasicWeixinMessage implements WeixinMessage {

	private static final long serialVersionUID = -3065503351885098759L;

	private String mediaId = null;
	
	private String msgId;

	private String thumbMediaId = null;

	public WeixinVideoMessage() {
		this.setMsgType(VIDEO);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinVideoMessage message = new WeixinVideoMessage();
		message.setMediaId(this.mediaId);
		message.setThumbMediaId(this.thumbMediaId);
		message.setMsgId(this.msgId);
		return (T)message;
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		WeixinRemoteVideoMessage remoteVideoMessage = new WeixinRemoteVideoMessage();
		remoteVideoMessage.setMediaId(mediaId);
		remoteVideoMessage.setThumbMediaId(thumbMediaId);
		return (T) remoteVideoMessage;
	}

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinVideoMessage(session, this);
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}


	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}


	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logRequestVideoMessage(session, this);
	}
	

}
