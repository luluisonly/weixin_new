package com.bokesoft.thirdparty.weixin.remote.extend;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;

/**
 * 提供给第三方处理远程的微信消息
 */
public interface WeixinRemoteMessageService {

	
	/**
	 * 处理消息
	 * @param cache
	 * @param remoteMessage
	 * @return
	 * @throws Exception
	 */
	public SOAResponseMessage doService(WeixinRmoteSession cache,WeixinRemoteMessage remoteMessage) throws Exception;

	
}
