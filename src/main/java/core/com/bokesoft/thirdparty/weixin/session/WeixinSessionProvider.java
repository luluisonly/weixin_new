package com.bokesoft.thirdparty.weixin.session;

import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;

public interface WeixinSessionProvider extends Initable{

	public abstract WeixinSession getSession(WeixinContext context,String uname,String openid) throws Exception;
	
	public abstract WeixinSession createWeixinSession(WeixinContext context,String uname,String openid) throws Exception;
	
	@Deprecated
	public abstract void removeWeixinSession(WeixinContext context,String uname,String openid) throws Exception;
	
	public abstract void updateWeixinSession(WeixinContext context,WeixinSession session) throws Exception;
	
}
