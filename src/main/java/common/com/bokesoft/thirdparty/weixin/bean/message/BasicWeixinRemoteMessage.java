package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public abstract class BasicWeixinRemoteMessage extends BasicWeixinMessage
		implements WeixinMessage, WeixinRemoteMessage {

	private static final long serialVersionUID = 1687108197797438456L;

	private int index;
	
	private String password;

	private String remoteType;

	private String uname;
	
	private String username;
	
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage(){
		return (T) this;
	}

	public int getIndex() {
		return index;
	}

	public String getPassword() {
		return password == null ? "" : password;
	}

	public String getRemoteType() {
		return remoteType;
	}

	public String getUname() {
		return uname;
	}

	public String getUsername() {
		return username == null ? "" :username;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRemoteType(String remoteType) {
		this.remoteType = remoteType;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setBasicWeixinRemoteMessage(WeixinSession session,WeixinRemoteMessage remoteMessage) {
		this.setUname(session.getPublicName());
		this.setUrl(remoteMessage.getUrl());
		this.setRemoteType(remoteMessage.getRemoteType());
		this.setUsername(remoteMessage.getUsername());
		this.setPassword(remoteMessage.getPassword());
	}

	public void setBasicWeixinMessage(WeixinMessage oldMessage) {
		this.setFromUserName(oldMessage.getFromUserName());
		this.setToUserName(oldMessage.getToUserName());
		this.setCreateTime(System.currentTimeMillis());
	}

	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		//ignore
	}
	
}
