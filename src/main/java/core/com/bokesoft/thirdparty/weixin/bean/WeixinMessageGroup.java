package com.bokesoft.thirdparty.weixin.bean;

import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;

/**
 * 微信消息会话
 *
 */
public class WeixinMessageGroup {

	private WeixinMessage receiveMessage;

	private WeixinMessage replyMessage;

	public WeixinMessage getReceiveMessage() {
		return receiveMessage;
	}

	public WeixinMessage getReplyMessage() {
		return replyMessage;
	}

	public void setReceiveMessage(WeixinMessage receiveMessage) {
		this.receiveMessage = receiveMessage;
	}

	public void setReplyMessage(WeixinMessage replyMessage) {
		this.replyMessage = replyMessage;
	}

}
