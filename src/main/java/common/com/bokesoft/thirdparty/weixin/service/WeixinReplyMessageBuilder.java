package com.bokesoft.thirdparty.weixin.service;

import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;

/**
 * 
 * 根据Message生成微信消息XML
 *
 */
public interface WeixinReplyMessageBuilder {

	/**
	 * 根据Message生成微信消息XML
	 * @param session
	 * @param replyMessage
	 * @return
	 */
	public abstract String buildMessage(WeixinMessage replyMessage);
}
