package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinChainingMessage extends BasicWeixinMessage implements WeixinMessage {

	private static final long serialVersionUID = -6115708403967447545L;

	private String title;

	private String url;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		WeixinRemoteChainingMessage message = new WeixinRemoteChainingMessage();
		message.setTitle(this.title);
		message.setUrl(this.url);
		return (T)message;
	}

	private String msgId;

	private String description = "";

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinChainingMessage(session, this);
	}

	public WeixinChainingMessage() {
		this.setMsgType(LINK);
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
		WeixinChainingMessage message = new WeixinChainingMessage();
		message.setTitle(this.title);
		message.setUrl(this.url);
		return (T)message;
	}
	
	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logRequestChainingMessage(session, this);
	}
	
}
