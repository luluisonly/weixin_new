package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinRemoteTextMessage extends BasicWeixinRemoteMessage implements
		WeixinMessage, WeixinRemoteMessage {

	private static final long serialVersionUID = 4023512520328584534L;
	
	private String content;
	
	private WeixinEventMessage eventMessage;

	public WeixinRemoteTextMessage(String content) {
		this.content = content;
		this.setServiceKey(content);
		this.setMsgType(REMOTE_TEXT);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinRemoteTextMessage message = new WeixinRemoteTextMessage(content);
		message.setUrl(getUrl());
		message.setRemoteType(getRemoteType());
		message.setServiceKey(this.getServiceKey());
		message.setContent(content);
		return (T) message;
	}

	public WeixinMessage doService(WeixinSession session,
			WeixinMessageHandle messageHandle) throws Exception {
		return messageHandle.handleWeixinRemoteTextMessage(session, this);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public WeixinEventMessage getEventMessage() {
		return eventMessage;
	}

	public void setEventMessage(WeixinEventMessage eventMessage) {
		this.eventMessage = eventMessage;
	}
	
}
