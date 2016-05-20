package com.bokesoft.thirdparty.weixin.bean;

import java.util.Map;

import org.apache.http.Header;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 返回服务给调用者的返回消息
 * 业务消息code请勿使用100以下的数值
 */
public class SOAResponseMessage {
	
	public static final String CODE = "code";
	
	public static final String DATA = "data";
	
	public static final String MESSAGE = "message";
	
	public static final SOAResponseMessage NULL = new SOAResponseMessage(-1, null);
	
	public static final SOAResponseMessage REQUEST_FORBIDDEN = new SOAResponseMessage(403, "request forbidden");

	public static final int STREAM_CODE = 16;
	
	public static final SOAResponseMessage SUCCESS = new SOAResponseMessage(0, "success");

	public static final SOAResponseMessage SYSTEM_ERROR = new SOAResponseMessage(-1, "system error");
	
	public static final SOAResponseMessage SYSTEM_ERROR_302 = new SOAResponseMessage(302, "system error");
	
	@SuppressWarnings("rawtypes")
	public static SOAResponseMessage parse(Map json){
		return new SOAResponseMessage((Integer)json.get(CODE),(String)json.get(MESSAGE),json.get(DATA));
	}

	public static SOAResponseMessage parse(String json){
		return parse(JSONObject.parseObject(json));
	}

	private byte [] bytes = null;

	private int code = -1;
	
	private Object data = null;
	
	private Header[] headers = null; 
	
	private String jsonString = null;
	
	private String message = null; 
	
	private boolean needUpdate = true;
	
	public SOAResponseMessage() {
 	}

	public SOAResponseMessage( int code,String message) {
		this.code = code;
		this.message = message;
		needUpdate = true;
	}

	public SOAResponseMessage( int code, String message,Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
		needUpdate = true;
	}
	
	public static SOAResponseMessage createStreamMessage(Header []headers,byte []bytes){
		SOAResponseMessage message = new SOAResponseMessage();
		message.setCode(STREAM_CODE);
		message.setBytes(bytes);
		message.setHeaders(headers);
		return message;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public int getCode() {
		return code;
	}
	
	public Object getData() {
		return data;
	}
	
	public Header[] getHeaders() {
		return headers;
	}

	public String getMessage() {
		return message;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setCode(int code) {
		this.code = code;
		needUpdate = true;
	}

	public void setData(Object data) {
		this.data = data;
		needUpdate = true;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	public void setMessage(String message) {
		this.message = message;
		needUpdate = true;
	}
	
	public String toString() {
		if (code == STREAM_CODE) {
			return "this is a stream message.";
		}
		if (needUpdate) {
			jsonString = JSONObject.toJSONString(this);
			needUpdate = false;
		}
		return jsonString;
	}

}
