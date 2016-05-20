package com.bokesoft.thirdparty.weixin.remote.extend;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationEventMessage;

public class DefaultWeixinRemoteMessageHandle extends AbstractWeixinRemoteMessageHandle{

	private static final Logger LOGGER = Logger.getLogger(DefaultWeixinRemoteMessageHandle.class); 
	
	public SOAResponseMessage handleRemoteLocationEventMessage(WeixinRmoteSession cache,WeixinRemoteLocationEventMessage remoteLocationEventMessage) throws Exception {
		LOGGER.warn("default impl");
		return SOAResponseMessage.NULL;
	}

}
