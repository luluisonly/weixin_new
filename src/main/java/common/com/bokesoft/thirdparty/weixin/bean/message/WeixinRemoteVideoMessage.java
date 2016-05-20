package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinRemoteVideoMessage extends BasicWeixinRemoteMessage implements 
		WeixinRemoteMessage,WeixinMessage {
	private static final long serialVersionUID = 1735623470550359423L;

	private String mediaId = null;
	
	private String msgId;

	private String thumbMediaId = null;

	public WeixinRemoteVideoMessage() {
		this.setMsgType(REMOTE_VIDEO);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinRemoteVideoMessage message = new WeixinRemoteVideoMessage();
		message.setMediaId(this.mediaId);
		message.setThumbMediaId(this.thumbMediaId);
		message.setMsgId(this.msgId);
		message.setUrl(getUrl());
		message.setRemoteType(getRemoteType());
		return (T)message;
	}

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinRemoteVideoMessage(session, this);
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

}
