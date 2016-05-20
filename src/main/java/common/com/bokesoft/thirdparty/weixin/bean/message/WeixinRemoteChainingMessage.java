package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinRemoteChainingMessage extends BasicWeixinRemoteMessage implements 
		WeixinRemoteMessage,WeixinMessage {
	private static final long serialVersionUID = -7653699003443164927L;

	private String title;

	private String url;

	private String msgId;

	private String description = "";

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinRemoteChainingMessage(session, this);
	}

	public WeixinRemoteChainingMessage() {
		this.setMsgType(REMOTE_CHAINING);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinRemoteChainingMessage message = new WeixinRemoteChainingMessage();
		message.setTitle(this.title);
		message.setUrl(this.url);
		message.setUrl(getUrl());
		message.setRemoteType(getRemoteType());
		return (T)message;
	}
	
}
