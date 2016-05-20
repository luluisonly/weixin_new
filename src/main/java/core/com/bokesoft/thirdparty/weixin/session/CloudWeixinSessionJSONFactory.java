package com.bokesoft.thirdparty.weixin.session;

import com.bokesoft.myerp.soa.CloudLadder;
import com.bokesoft.myerp.soa.ICloudProvider;
import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;

/**
 * session管理
 */
public class CloudWeixinSessionJSONFactory implements WeixinSessionFactory ,Initable {
	
	private ICloudProvider cloudProvider = null;
	
	private static final String sessionid_prefix = "wx-session-j-";
	
	public CloudWeixinSession getWeixinSession(String openid,String uname) throws Exception {
		String sessionid = new StringBuffer(sessionid_prefix).append(uname).append(openid).toString();
		try {
			String json = (String) this.cloudProvider.getCache(sessionid);
			if (json ==null) {
				return null;
			}
			return new CloudWeixinSession(json);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
	
	public CloudWeixinSession addWeixinSession(String openid,String uname) throws Exception {
		String sessionId = new StringBuffer(sessionid_prefix).append(uname).append(openid).toString();
		CloudWeixinSession session = new CloudWeixinSession(openid,uname,sessionId);
		try {
			this.cloudProvider.setCache(sessionId, session.toString());
		} catch (Throwable e) {
			throw new Exception(e);
		}
		return session;
	}
	
	public void initialize(WeixinContext context) throws Exception {
		cloudProvider = CloudLadder.getProvider();
	}

	public void shoutdown(WeixinContext context) throws Exception {
		
	}

	public void updateWeixinSession(WeixinSession session) throws Exception {
		try {
			this.cloudProvider.setCache(session.getId(), session.toString());
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
}