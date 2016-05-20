package com.bokesoft.thirdparty.weixin.session;

import com.bokesoft.myerp.common.ByteUtil;
import com.bokesoft.myerp.soa.CloudLadder;
import com.bokesoft.myerp.soa.ICloudProvider;
import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;

/**
 * session管理
 */
public class CloudWeixinSessionByteFactory implements WeixinSessionFactory ,Initable {
	
	private ICloudProvider cloudProvider = null;
	
	private static final String sessionid_prefix = "wx-session-b-";
	
	public CloudWeixinSession getWeixinSession(String openid,String uname) throws Exception {
		String sessionid = new StringBuffer(sessionid_prefix).append(uname).append(openid).toString();
		try {
			byte [] bytes = (byte[]) this.cloudProvider.getCache(sessionid);
			if (bytes ==null) {
				return null;
			}
			return (CloudWeixinSession) ByteUtil.toObject(bytes);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
	
	public CloudWeixinSession addWeixinSession(String openid,String uname) throws Exception {
		String sessionId = new StringBuffer(sessionid_prefix).append(uname).append(openid).toString();
		CloudWeixinSession session = new CloudWeixinSession(openid,uname,sessionId);
		try {
			this.cloudProvider.setCache(sessionId,ByteUtil.toBytes(session));
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
			this.cloudProvider.setCache(session.getId(),ByteUtil.toBytes(session));
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
}