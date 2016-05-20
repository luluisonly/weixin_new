package com.zaofans.weixin.remotehandle;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class TemplatesendjobfinishHandle implements WeixinRemoteTextMessageCellHandle{

	@Override
	public SOAResponseMessage handleWeixinRemoteTextMessage(
			WeixinRmoteSession cache, WeixinRemoteTextMessage remoteTextMessage)
			throws Exception {
		// TODO Auto-generated method stub
		WeixinMessage eventMessage = remoteTextMessage.getEventMessage();
		//根据返回的内容判断是否需要重新发送，需要实现定时重发机制
		return null;
	}
	@Override
	public String getHandleName() {
		return "Templatesendjobfinish";
	}

}
