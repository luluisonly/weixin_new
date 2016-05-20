package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalService;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public abstract class WeixinReplyMessage extends BasicWeixinMessage{

	private static final long serialVersionUID = -8983873190563203103L;

	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle,
			WeixinMessageFlowLocalService flowLocalService) throws Exception {
		return this;
	}
	
	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)
			throws Exception {
		return this;
	}
	
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage() {
		throw new RuntimeException("not will walk here");
	}
}
