package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinVoiceMessage extends BasicWeixinMessage implements WeixinMessage {
	
	private static final long serialVersionUID = 7385400446482827421L;

	private String format = null;
	
	private String mediaId = null;

	private String msgId;
	
	private String recognition = null;

	public WeixinVoiceMessage() {
		this.setMsgType(VOICE);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinVoiceMessage message = new WeixinVoiceMessage();
		message.setMediaId(this.mediaId);
		message.setMsgId(this.msgId);
		message.setFormat(this.format);
		message.setRecognition(this.recognition);
		return (T)message;
	}

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinVoiceMessage(session, this);
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

	@SuppressWarnings("unchecked")
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		WeixinRemoteVoiceMessage remoteVoiceMessage = new WeixinRemoteVoiceMessage();
		remoteVoiceMessage.setFormat(format);
		remoteVoiceMessage.setMediaId(mediaId);
		remoteVoiceMessage.setRecognition(recognition);
		return (T) remoteVoiceMessage;
	}
	
	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logRequestVoiceMessage(session, this);
	}

}
