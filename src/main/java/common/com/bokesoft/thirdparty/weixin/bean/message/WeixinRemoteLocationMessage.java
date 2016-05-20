package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinRemoteLocationMessage extends BasicWeixinRemoteMessage implements 
		WeixinRemoteMessage,WeixinMessage {

	private static final long serialVersionUID = -1760799463141955697L;

	private String location_X;

	private String location_Y;

	private String scale;

	private String label;

	public WeixinRemoteLocationMessage() {
		this.setMsgType(REMOTE_LOCATION);
	}

	private String msgId;

	public String getLocation_X() {
		return location_X;
	}

	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}

	public String getLocation_Y() {
		return location_Y;
	}

	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinRemoteLocationMessage(session, this);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinRemoteLocationMessage message = new WeixinRemoteLocationMessage();
		message.setLabel(this.label);
		message.setLocation_X(this.location_X);
		message.setLocation_Y(this.location_Y);
		message.setScale(this.scale);
		message.setUrl(getUrl());
		message.setRemoteType(getRemoteType());
		return (T)message;
	}
	
	

}
