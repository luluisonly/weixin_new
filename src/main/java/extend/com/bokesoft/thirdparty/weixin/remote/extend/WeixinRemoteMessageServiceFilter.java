package com.bokesoft.thirdparty.weixin.remote.extend;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;

/**
 * 消息内容过滤 
 *
 */
public interface WeixinRemoteMessageServiceFilter {

	/**
	 * 对消息内容进行过滤处理
	 * @param cache
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public abstract SOAResponseMessage doFilter(WeixinRmoteSession cache, WeixinRemoteMessage message)	throws Exception;

}
