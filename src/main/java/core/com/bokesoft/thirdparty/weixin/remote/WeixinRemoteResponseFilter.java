package com.bokesoft.thirdparty.weixin.remote;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

/**
 * 过滤业务系统返回的消息
 */
public interface WeixinRemoteResponseFilter {

	/**
	 * 进行过滤
	 * @param session
	 * @param remoteMessage
	 * @param responseMessage
	 * @return
	 * @throws Exception
	 */
	public SOAResponseMessage doFilter(WeixinSession session ,WeixinRemoteMessage remoteMessage,SOAResponseMessage responseMessage) throws Exception;
	
	
}
