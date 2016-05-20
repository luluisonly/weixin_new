package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;


public class WeixinReplyWasteMessage extends WeixinReplyMessage implements WeixinMessage {
	
	private static final long serialVersionUID = 6845281685331563547L;
	public static final WeixinReplyWasteMessage WEIXIN_REPLY_WASTE_MESSAGE = new WeixinReplyWasteMessage();

	public WeixinReplyWasteMessage() {
		this.setMsgType(WASTE);
		this.setReply(true);
	}

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		return (T) this;
	}
	
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		throw new RuntimeException("not will walk here");
	}

	public void setBasicWeixinMessage(WeixinMessage oldMessage) {
		// do nothing
	}

	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logResponseWasteMessage(session, this);
	}


}
