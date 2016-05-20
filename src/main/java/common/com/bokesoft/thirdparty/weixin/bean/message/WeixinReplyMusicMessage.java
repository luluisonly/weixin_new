package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinReplyMusicMessage extends WeixinReplyMessage implements WeixinMessage {

	private static final long serialVersionUID = 4061223368944131297L;

	private String description = "";

	private String hqMusicUrl;

	private String musicUrl;

	private String thumbMediaId;
	
	private String title;

	public WeixinReplyMusicMessage() {
		this.setMsgType(MUSIC);
		this.setReply(true);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinReplyMusicMessage message = new WeixinReplyMusicMessage();
		message.setDescription(this.description);
		message.setHqMusicUrl(this.hqMusicUrl);
		message.setMusicUrl(this.musicUrl);
		message.setTitle(this.title);
		message.setThumbMediaId(this.thumbMediaId);
		return (T)message;
	}

	public String getDescription() {
		return description;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logResponseMusicMessage(session, this);
	}
	

}
