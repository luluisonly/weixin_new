package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinLocationEventMessage extends WeixinEventMessage implements WeixinMessage {

	private static final long serialVersionUID = 1300186598766464595L;

	@SuppressWarnings("unchecked")
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		WeixinRemoteLocationEventMessage message = new WeixinRemoteLocationEventMessage();
		message.setEvent(getEvent());
		message.setLatitude(latitude);
		message.setLongitude(longitude);
		message.setPrecision(precision);
		return (T) message;
	}

	private String latitude;

	private String longitude;

	private String precision;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public WeixinLocationEventMessage() {
		this.setMsgType(EVENT);
	}

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return messageHandle.handleWeixinLocationEventMessage(session, this);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinLocationEventMessage message = new WeixinLocationEventMessage();
		message.setEvent(this.getEvent());
		message.setEventKey(this.getEventKey());
		message.setLatitude(latitude);
		message.setLongitude(longitude);
		message.setPrecision(precision);
		return (T) message;
	}

}
