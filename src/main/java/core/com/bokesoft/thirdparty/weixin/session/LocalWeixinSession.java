package com.bokesoft.thirdparty.weixin.session;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.WeixinContextFactory;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalService;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteSessionImpl;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

/**
 * session 的实现类
 *
 */
public class LocalWeixinSession extends WeixinRemoteSessionImpl implements WeixinSession ,WeixinRmoteSession{

	private static final long serialVersionUID = 1L;

	private long creationTime = System.currentTimeMillis();

	private String id = null;

	private long lastuse = System.currentTimeMillis();

	private long maxInactiveInterval = 30 * 60 * 1000;

	private WeixinMessageFlowLocalService weixinMessageFlowLocalService = null;

	public LocalWeixinSession(String openid, String publicName, String sessionId) throws Exception {
		this.setOpenid(openid);
		this.setPublicName(publicName);
		this.id = sessionId;
	}

	public void active() {
		lastuse = System.currentTimeMillis();
	}

	public long getCreationTime() {
		return this.creationTime;
	}

	public String getId() {
		return this.id;
	}

	public long getLastAccessedTime() {
		return this.lastuse;
	}

	public long getMaxInactiveInterval() {
		return this.maxInactiveInterval;
	}

	public WeixinContext getWeixinContext() {
		return WeixinContextFactory.getApplicationContext();
	}

	public WeixinMessageFlowLocalService getWeixinMessageFlowLocalService() {
		return weixinMessageFlowLocalService;
	}

	public WeixinPublicNumber getWeixinPublicNumber() throws Exception {
		WeixinContext context = this.getWeixinContext();
		WeixinPublicNumber weixinPublicNumber = context.getWeixinPublicNumber(this.getPublicName());
		if (weixinPublicNumber == null) {
			weixinPublicNumber = context.getDefaultWeixinPublicNumber();
		}
		return weixinPublicNumber;
	}

	public boolean isValid() {
		return System.currentTimeMillis() - lastuse < maxInactiveInterval;
	}

	public void setMaxInactiveInterval(long millisecond) {
		this.maxInactiveInterval = millisecond;
	}

	public void setWeixinMessageFlowLocalService(WeixinMessageFlowLocalService weixinMessageFlowLocalService) {
		this.weixinMessageFlowLocalService = weixinMessageFlowLocalService;
	}
	
}
