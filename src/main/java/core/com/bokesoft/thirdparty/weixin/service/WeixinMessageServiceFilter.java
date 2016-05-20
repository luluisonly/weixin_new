package com.bokesoft.thirdparty.weixin.service;

import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

/**
 * 消息内容过滤 
 *
 */
public interface WeixinMessageServiceFilter {

	/**
	 * 对消息内容进行过滤处理
	 * @param session
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public abstract WeixinMessage doFilter(WeixinSession session, WeixinMessage message)
			throws Exception;

}
