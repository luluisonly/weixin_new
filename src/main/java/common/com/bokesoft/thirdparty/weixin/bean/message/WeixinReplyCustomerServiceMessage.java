package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinReplyCustomerServiceMessage extends WeixinReplyMessage implements WeixinMessage {

	private static final long serialVersionUID = 4897623804809376724L;
	
	public WeixinReplyCustomerServiceMessage(){
		this.setMsgType(CUSTOMERSERVICE);
		this.setReply(true);
	}

	@Override
	public <T extends WeixinMessage> T copyMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
}
