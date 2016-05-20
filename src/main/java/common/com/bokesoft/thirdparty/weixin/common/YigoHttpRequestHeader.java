package com.bokesoft.thirdparty.weixin.common;

import com.bokesoft.myerp.common.midproxy.Env;
import com.bokesoft.myerp.common.midproxy.MAP_C_ENV;
/**
 * 用于YigoHttpRequest
 *
 */
public class YigoHttpRequestHeader {
	
	private String dispatchName = null;

	private Env env = MAP_C_ENV.createEnv(MAP_C_ENV.getDefaultDSN());

	private String midClassName = null;

	private String midMethodName = null;
	
	private boolean returnEnv = false;
	
	private String soaServiceType = null;
	
	public String getDispatchName() {
		return dispatchName;
	}
	
	public Env getEnv() {
		return env;
	}

	public String getMidClassName() {
		return midClassName;
	}

	public String getMidMethodName() {
		return midMethodName;
	}

	public String getSoaServiceType() {
		return soaServiceType;
	}

	public boolean isReturnEnv() {
		return returnEnv;
	}

	public void setDispatchName(String dispatchName) {
		this.dispatchName = dispatchName;
	}

	public void setEnv(Env env) {
		this.env = env;
	}

	public void setMidClassName(String midClassName) {
		this.midClassName = midClassName;
	}

	public void setMidMethodName(String midMethodName) {
		this.midMethodName = midMethodName;
	}

	public void setReturnEnv(boolean returnEnv) {
		this.returnEnv = returnEnv;
	}

	public void setSoaServiceType(String soaServiceType) {
		this.soaServiceType = soaServiceType;
	}
	
}
