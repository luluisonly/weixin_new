package com.bokesoft.thirdparty.weixin.service;

import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;

/**
 * 
 * 文本消息转成消息对象
 *
 */
public interface WeixinMessageBuilder {

	/**
	 * 文本消息转成消息对象
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public abstract WeixinMessage buildMessage(String content) throws Exception;

}
