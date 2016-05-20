package com.bokesoft.thirdparty.weixin.remote.extend;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;

public interface WeixinRemoteTextMessageCellHandle {

	
	public abstract SOAResponseMessage handleWeixinRemoteTextMessage(WeixinRmoteSession cache,WeixinRemoteTextMessage remoteTextMessage) throws Exception;
	
	@Deprecated
	public abstract String getHandleName();
	
}
