package com.bokesoft.thirdparty.weixin.flow;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageWrap4Flow;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public abstract class AbstractWeixinMessageFlowHandle implements WeixinMessageFlowHandle {

	private static final long serialVersionUID = 4598731114888864035L;

	private boolean isGoback = false;
	
	private WeixinRemoteMessage storeMessage = null;

	public WeixinMessageWrap4Flow doAction(WeixinRmoteSession cache, WeixinRemoteMessage message)
			throws Exception {
		if (isGoback) {
			message = storeMessage;
			isGoback = false;
		}else{
			storeMessage = message;
		}
		return this.doActionImpl(cache, message);
	}

	public abstract WeixinMessageWrap4Flow doActionImpl(WeixinRmoteSession cache, WeixinRemoteMessage message)
			throws Exception;

	public WeixinRemoteMessage getStoreMessage() {
		return storeMessage;
	}

	public boolean isGoback() {
		return isGoback;
	}

	public void setGoback(boolean isGoback) {
		this.isGoback = isGoback;
	}

}
