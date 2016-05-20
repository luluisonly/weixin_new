package com.bokesoft.thirdparty.weixin.remote.extend;

import java.util.HashMap;
import java.util.Set;

import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowService;

public class WeixinRemoteSessionImpl implements WeixinRmoteSession{
	
	public WeixinRemoteSessionImpl() {
		
	}

	private HashMap<String, Object> attributes = new HashMap<String, Object>();
	
	private String publicName = null;
	
	private String openid = null;

	private WeixinMessageFlowService weixinMessageFlowService = null;
	
	public WeixinRemoteSessionImpl (String uname,String openid){
		this.publicName = uname;
		this.openid = openid;
	}
	
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public Set<String> getAttributeNames() {
		return attributes.keySet();
	}

	public String getPublicName() {
		return publicName;
	}

	public String getOpenid() {
		return openid;
	}

	public void setAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}

	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setWeixinMessageFlowService(WeixinMessageFlowService weixinMessageFlowService) {
		this.weixinMessageFlowService = weixinMessageFlowService;
	}

	public WeixinMessageFlowService getWeixinMessageFlowService() {
		return weixinMessageFlowService;
	}

	public WeixinRemoteContext getWeixinContext() {
		return WeixinRemoteContextFactory.getWeixinRemoteContext();
	}

	public void removeAttribute(String key) {
		this.attributes.remove(key);
	}
	
	
}
