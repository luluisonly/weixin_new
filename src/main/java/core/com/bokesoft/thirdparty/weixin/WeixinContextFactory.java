package com.bokesoft.thirdparty.weixin;

import com.bokesoft.thirdparty.weixin.session.WeixinSession;
import com.bokesoft.thirdparty.weixin.session.WeixinSessionProvider;

/**
 * 从这里获取微信的上下文环境
 *
 */
public class WeixinContextFactory {

	private static WeixinContext applicationContext = null;
	
	public static WeixinContext getApplicationContext(){
		return applicationContext;
	}
	
	/**
	 * 获取session
	 * @param openid
	 * @param uname
	 * @return
	 * @throws Exception
	 */
	public static WeixinSession getWeixinSession(String openid,String uname) throws Exception {
		WeixinSessionProvider sessionProvider = applicationContext.getWeixinSessionProvider();
		WeixinSession session = sessionProvider.getSession(applicationContext, uname, openid);
		if (session == null) {
			return sessionProvider.createWeixinSession(applicationContext, uname, openid);
		}
		return session;
	}
	
	/**
	 * 初始化factory
	 * @throws Exception
	 */
	public synchronized static void initFactory() throws Exception{
		if (applicationContext == null) {
			applicationContext = new ApplicationContext();
		}
		applicationContext.initialize(applicationContext);
	}
	
	public synchronized static void shutdownFactory() throws Exception{
		if (applicationContext != null) {
			applicationContext.shoutdown(applicationContext);
		}
	} 
	
}
