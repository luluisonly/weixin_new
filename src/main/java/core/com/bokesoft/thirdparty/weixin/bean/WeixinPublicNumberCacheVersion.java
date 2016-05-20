package com.bokesoft.thirdparty.weixin.bean;

/**
 * 用于与云端或者第三方数据存储进行数据版本比较
 *
 */
public class WeixinPublicNumberCacheVersion {
	
	public static final String VKEY_PREFIX = "weixin-v-";
	
	public static final String CNAME_PREFIX = "weixin-";

	private String cname = null;
	
	private String vkey = null;

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getVkey() {
		return vkey;
	}

	public void setVkey(String vkey) {
		this.vkey = vkey;
	}
	
	public static WeixinPublicNumberCacheVersion newInstance(String uname){
		WeixinPublicNumberCacheVersion cacheVersion = new WeixinPublicNumberCacheVersion();
		cacheVersion.setCname(CNAME_PREFIX+uname);
		cacheVersion.setVkey(VKEY_PREFIX+uname);
		return cacheVersion;
	}
	
}
