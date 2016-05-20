package com.bokesoft.thirdparty.weixin.bean;

import com.alibaba.fastjson.JSONObject;

/**
 *	调用微信api的返回结果 
 *
 */
public class WeixinApiResult {

	public WeixinApiResult(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		this.setErrcode(jsonObject.getIntValue("errcode"));
		this.setErrmsg(jsonObject.getString("errmsg"));
	}

	public WeixinApiResult(int errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	private int errcode;
	
	private String errmsg;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	
}
