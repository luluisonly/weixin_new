package com.bokesoft.thirdparty.weixin.bean;

import com.bokesoft.thirdparty.weixin.common.TConstant;

/**
 * 微信消息历史记录
 *
 */
public class WeixinMessageProcedure {

	private int currentIndex = 0;

	private int index = 0;

	private WeixinMessageGroup[] messageGroups;

	private int move = 0;

	public WeixinMessageProcedure() {
		this.messageGroups = new WeixinMessageGroup[TConstant.WEIXINMESSAGE_PROCEDURE_SIZE];
	}

	/**
	 * 添加一个消息对话
	 * @param messageGroup
	 */
	public void addWeixinMessage(WeixinMessageGroup messageGroup) {
		if (this.index == TConstant.WEIXINMESSAGE_PROCEDURE_SIZE - 1) {
			this.index = 0;
		} else {
			this.index++;
		}
		this.messageGroups[this.index] = messageGroup;
		this.currentIndex = index;
		this.move = 0;
	}

	/**
	 * 定位到当前消息
	 * @return
	 */
	public WeixinMessageGroup current() {
		this.currentIndex = index;
		this.move = 0;
		return this.messageGroups[currentIndex];
	}

	/**
	 * 定位到最早的消息
	 * @return
	 */
	public WeixinMessageGroup first() {
		this.move = TConstant.WEIXINMESSAGE_PROCEDURE_SIZE;
		if (index == TConstant.WEIXINMESSAGE_PROCEDURE_SIZE - 1) {
			this.currentIndex = 0;
			return this.messageGroups[this.currentIndex];
		}
		this.currentIndex = this.index + 1;
		return this.messageGroups[this.currentIndex];
	}

	/**
	 * 向前一个消息
	 * @return
	 */
	public WeixinMessageGroup previous() {
		if (this.move == TConstant.WEIXINMESSAGE_PROCEDURE_SIZE) {
			return null;
		}
		if (this.currentIndex == 0) {
			this.currentIndex = TConstant.WEIXINMESSAGE_PROCEDURE_SIZE - 1;
			return this.messageGroups[this.currentIndex];
		}
		this.move++;
		return this.messageGroups[--this.currentIndex];
	}

}
