package com.bokesoft.thirdparty.weixin.remote.extend;

import java.util.Set;

import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowService;

public interface WeixinRmoteSession {
	
	public abstract Object getAttribute(String key);

	public abstract Set<String> getAttributeNames();
	
	public abstract String getOpenid() ;
	
	public abstract String getPublicName() ;

	public abstract WeixinMessageFlowService getWeixinMessageFlowService();
	
	public abstract WeixinRemoteContext getWeixinContext();
	
	public abstract void setAttribute(String key, Object value);
	
	public abstract void setOpenid(String openid) ;
	
	public abstract void setPublicName(String uname) ;
	
	public abstract void removeAttribute(String key);
	
	public abstract void setWeixinMessageFlowService(WeixinMessageFlowService weixinMessageFlowService);
	
}
