package com.bokesoft.thirdparty.weixin.flow;

import java.io.Serializable;

import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public interface WeixinMessageFlowLocalService extends Serializable {

	public abstract WeixinRemoteMessage getWeixinRemoteMessage();

	public abstract void setWeixinRemoteMessage(WeixinRemoteMessage weixinRemoteMessage);
	
	public abstract WeixinMessage handleFlow(WeixinSession session, WeixinMessage message)throws Exception;

}