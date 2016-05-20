package com.bokesoft.thirdparty.weixin.remote.extend;

public class WeixinRemoteContextFactory {

	private static WeixinRemoteContext weixinRemoteContext = new DefaultWeixinRemoteContext();
	
	public static WeixinRemoteContext getWeixinRemoteContext(){
		return weixinRemoteContext;
	}
	
	public static synchronized void WeixinRemoteContext(WeixinRemoteContext weixinRemoteContext) throws Exception{
		if (weixinRemoteContext == null) {
			throw new Exception("WeixinRemoteContext can not be null");
		}
		WeixinRemoteContextFactory.weixinRemoteContext = weixinRemoteContext;
	}
	
	
}
