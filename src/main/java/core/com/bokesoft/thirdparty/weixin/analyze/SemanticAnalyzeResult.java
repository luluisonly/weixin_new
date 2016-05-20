package com.bokesoft.thirdparty.weixin.analyze;

import com.alibaba.fastjson.JSONObject;

public class SemanticAnalyzeResult {

	private String key;
	
	private String [] param;

	public SemanticAnalyzeResult(String key, String [] param) {
		this.key = key;
		this.param = param;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String [] getParam() {
		return param;
	}

	public void setParam(String [] param) {
		this.param = param;
	}
	
	public String toString() {
		return JSONObject.toJSONString(this).toString();
	}
	
}
