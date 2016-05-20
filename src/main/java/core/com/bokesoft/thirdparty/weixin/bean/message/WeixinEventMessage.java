package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinEventMessage extends BasicWeixinMessage implements WeixinMessage {

	private static final long serialVersionUID = -3027823296641688326L;

	@SuppressWarnings("unchecked")
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		WeixinRemoteTextMessage message = new WeixinRemoteTextMessage(this.eventKey);
		return (T)message;
	}

	private String event;

	private String eventKey;

	private String scanType;
	
	private String scanResult;

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public String getScanResult() {
		return scanResult;
	}

	public void setScanResult(String scanResult) {
		this.scanResult = scanResult;
	}
	
	public String getEvent() {
		return event;
	}

	public WeixinEventMessage() {
		this.setMsgType(EVENT);
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinEventMessage(session, this);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinEventMessage message = new WeixinEventMessage();
		message.setEvent(this.event);
		message.setEventKey(this.eventKey);
		return (T)message;
	}
	
	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logRequestMenuEventMessage(session, this);
	}

}
