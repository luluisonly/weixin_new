package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinRemoteLocationEventMessage extends BasicWeixinRemoteMessage implements 
		WeixinRemoteMessage,WeixinMessage {
	private static final long serialVersionUID = -4709604841013713226L;

	private String event;

	private String latitude;

	private String longitude;

	private String precision;

	public WeixinRemoteLocationEventMessage() {
		this.setMsgType(REMOTE_LOCATIONEVENT);
	}


	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinRemoteLocationEventMessage message = new WeixinRemoteLocationEventMessage();
		message.setEvent(getEvent());
		message.setLatitude(latitude);
		message.setLongitude(longitude);
		message.setPrecision(precision);
		message.setUrl(getUrl());
		message.setRemoteType(getRemoteType());
		return (T)message;
	}
	
	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinRemoteLocationEventMessage(session, this);
	}
	
	public String getEvent() {
		return event;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public void setPrecision(String precision) {
		this.precision = precision;
	}

}
