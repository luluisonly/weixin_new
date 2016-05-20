package com.bokesoft.thirdparty.weixin.session;

import com.bokesoft.thirdparty.weixin.WeixinContext;

public class CloudWeixinSessionProvider implements WeixinSessionProvider {

	private WeixinSessionFactory sessionFactory = new CloudWeixinSessionByteFactory();

	public void initialize(WeixinContext context) throws Exception {
		sessionFactory.initialize(context);
	}

	public void shoutdown(WeixinContext context) throws Exception {
		sessionFactory.shoutdown(context);
	}

	public WeixinSession getSession(WeixinContext context, String uname, String openid)
			throws Exception {
		return sessionFactory.getWeixinSession(openid, uname);
	}

	public WeixinSession createWeixinSession(WeixinContext context, String uname, String openid)
			throws Exception {
		return sessionFactory.addWeixinSession(openid, uname);
	}

	public void removeWeixinSession(WeixinContext context, String uname, String openid)
			throws Exception {

	}

	public void updateWeixinSession(WeixinContext context, WeixinSession session)
			throws Exception {
		sessionFactory.updateWeixinSession(session);
	}

}
