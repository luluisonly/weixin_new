package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinImageMessage extends BasicWeixinMessage implements WeixinMessage {

	private static final long serialVersionUID = -7101228429630618504L;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		WeixinRemoteImageMessage message = new WeixinRemoteImageMessage();
		message.setMsgId(this.msgId);
		message.setPicUrl(this.picUrl);
		message.setMediaId(this.mediaId);
		return (T)message;
	}

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

	public WeixinImageMessage() {
		this.setMsgType(IMAGE);
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
		return messageHandle.handleWeixinImageMessage(session, this);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinImageMessage message = new WeixinImageMessage();
		message.setMsgId(this.msgId);
		message.setPicUrl(this.picUrl);
		message.setMediaId(this.mediaId);
		return (T)message;
	}

	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logRequestImageMessage(session, this);
	}
	
	
}
