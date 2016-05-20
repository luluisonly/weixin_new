package com.bokesoft.thirdparty.weixin.flow;

import java.io.Serializable;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlow;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public interface WeixinMessageFlowService extends Serializable{

	public abstract WeixinRemoteMessage getWeixinRemoteMessage();
	
	public abstract WeixinMessageFlow getMessageFlow();

	public abstract SOAResponseMessage goback(WeixinRmoteSession cache, WeixinRemoteMessage message)throws Exception ;

	public abstract SOAResponseMessage gobreak(WeixinRmoteSession cache, WeixinRemoteMessage message)throws Exception ;

	public abstract SOAResponseMessage handleFlow(WeixinRmoteSession cache, WeixinRemoteMessage message)throws Exception;

	public abstract void setCurrentAction(int currentIndex);
	
	public abstract void setMessageFlow(WeixinMessageFlow messageFlow) ;

	public abstract void setWeixinRemoteMessage(WeixinRemoteMessage remoteMessage);

}
