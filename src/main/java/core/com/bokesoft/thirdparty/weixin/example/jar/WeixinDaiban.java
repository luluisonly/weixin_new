package com.bokesoft.thirdparty.weixin.example.jar;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;

public class WeixinDaiban implements WeixinRemoteTextMessageCellHandle{

	public SOAResponseMessage handleWeixinRemoteTextMessage(WeixinRmoteSession cache,
			WeixinRemoteTextMessage remoteTextMessage) throws Exception {
		return new SOAResponseMessage(0, "审批完成");
	}

	public String getHandleName() {
		return "审批";
	}
	
	
}
