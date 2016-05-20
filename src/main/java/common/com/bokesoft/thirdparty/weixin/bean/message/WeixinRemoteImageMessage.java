package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinRemoteImageMessage extends BasicWeixinRemoteMessage implements 
		WeixinRemoteMessage,WeixinMessage {
	private static final long serialVersionUID = -1197733084002092232L;

	private String picUrl;

	private long msgId;
	
	private String mediaId = null;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public WeixinRemoteImageMessage() {
		this.setMsgType(REMOTE_IMAGE);
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinRemoteImageMessage(session, this);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinRemoteImageMessage message = new WeixinRemoteImageMessage();
		message.setMsgId(this.msgId);
		message.setPicUrl(this.picUrl);
		message.setMediaId(this.mediaId);
		message.setUrl(getUrl());
		message.setRemoteType(getRemoteType());
		return (T)message;
	}

}
