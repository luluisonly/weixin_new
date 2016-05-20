package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinTextMessage extends BasicWeixinMessage implements WeixinMessage {
	
	private static final long serialVersionUID = 4730540644833468387L;

	private String content;

	private long msgId;

	public WeixinTextMessage(String content) {
		this.content = content;
		this.setMsgType(TEXT);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		return (T) new WeixinRemoteTextMessage(this.content);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinTextMessage(session, this);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinTextMessage message = new WeixinTextMessage(this.content);
		return (T) message;
	}

	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logRequestTextMessage(session, this);
	}
	
	
}
