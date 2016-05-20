package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinRemoteVoiceMessage extends BasicWeixinRemoteMessage implements 
		WeixinRemoteMessage,WeixinMessage {
	
	private static final long serialVersionUID = -1936107415132706559L;

	private String format = null;
	
	private String mediaId = null;

	private String msgId;
	
	private String recognition = null;

	public WeixinRemoteVoiceMessage() {
		this.setMsgType(REMOTE_VOICE);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinRemoteVoiceMessage message = new WeixinRemoteVoiceMessage();
		message.setMediaId(this.mediaId);
		message.setMsgId(this.msgId);
		message.setFormat(this.format);
		message.setRecognition(this.recognition);
		message.setUrl(getUrl());
		message.setRemoteType(getRemoteType());
		return (T)message;
	}

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinRemoteVoiceMessage(session, this);
	}

	public String getFormat() {
		return format;
	}

	public String getMediaId() {
		return mediaId;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setFormat(String format) {
		this.format = format;
	}


	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}


	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

}
