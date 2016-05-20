package com.bokesoft.thirdparty.weixin.remote.extend;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;

public interface WeixinRemoteMessageHandle {

	public abstract SOAResponseMessage handleRemoteTextMessage(WeixinRmoteSession cache,WeixinRemoteTextMessage remoteTextMessage)throws Exception;
	
	public abstract SOAResponseMessage handleRemoteLocationEventMessage(WeixinRmoteSession cache,WeixinRemoteLocationEventMessage remoteLocationEventMessage)throws Exception;
	
	
}
