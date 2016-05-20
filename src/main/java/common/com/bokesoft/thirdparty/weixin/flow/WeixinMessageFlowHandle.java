package com.bokesoft.thirdparty.weixin.flow;

import java.io.Serializable;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageWrap4Flow;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public interface WeixinMessageFlowHandle extends Serializable{

	public abstract WeixinMessageWrap4Flow doAction(WeixinRmoteSession cache, WeixinRemoteMessage message) throws Exception;
	
	public abstract void setGoback(boolean isGoback) ;
	
	public abstract boolean isGoback();
	
	public abstract WeixinRemoteMessage getStoreMessage();

}
