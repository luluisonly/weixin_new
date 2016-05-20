package com.bokesoft.thirdparty.weixin;

public interface Initable {

	public abstract void initialize(WeixinContext context) throws Exception;
	
	public abstract void shoutdown(WeixinContext context) throws Exception;
	
}
