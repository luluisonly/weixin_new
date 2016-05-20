package com.bokesoft.thirdparty.weixin.session;

import com.bokesoft.thirdparty.weixin.Initable;

public interface WeixinSessionFactory extends Initable{

	public abstract WeixinSession getWeixinSession(String openid,String uname) throws Exception;
	
	public abstract WeixinSession addWeixinSession(String openid,String uname) throws Exception ;
	
	public abstract void updateWeixinSession(WeixinSession session) throws Exception ;
	
}
