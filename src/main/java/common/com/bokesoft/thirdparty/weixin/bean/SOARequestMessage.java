package com.bokesoft.thirdparty.weixin.bean;


/**
 * 
 * 封装请求的消息
 *
 */
public class SOARequestMessage {
	
	public static final String PARAM = "param";
	
	public static final String PASSWORD = "password";
	
	public static final String TYPE = "type";
	
	public static final String UNAME = "uname";
	
	public static final String USERNAME = "username";
	
	private Object objectParam = null;
	
	private String password;

	private String type;
	
	private String uname;
	
	private String username;
	
	private boolean isWeixin;

	public SOARequestMessage() {
	}
	
	public SOARequestMessage(Object objectParam, String type, String uname,String username,String password) {
		this.objectParam = objectParam;
		this.type = type;
		this.uname = uname;
	}

	public Object getObjectParam() {
		return objectParam;
	}

	public String getStringParam() {
		return (String) objectParam;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getType() {
		return type;
	}

	public String getUname() {
		return uname;
	}

	public String getUsername() {
		return username;
	}

	public void setObjectParam(Object objectParam) {
		this.objectParam = objectParam;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isWeixin() {
		return isWeixin;
	}

	public void setWeixin(boolean isWeixin) {
		this.isWeixin = isWeixin;
	}
	
}
