package com.bokesoft.thirdparty.weixin.example.flow;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlowDealType;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageWrap4Flow;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.flow.AbstractWeixinMessageFlowHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class Step1 extends AbstractWeixinMessageFlowHandle{

	private static final long serialVersionUID = 2575971879060869575L;

	public WeixinMessageWrap4Flow doActionImpl(WeixinRmoteSession cache, WeixinRemoteMessage message)
			throws Exception {
		
		WeixinReplyTextMessage replyTextMessage = new WeixinReplyTextMessage("请输入1");
		WeixinMessageWrap4Flow flow = new WeixinMessageWrap4Flow();
		flow.setFlowDealType(WeixinMessageFlowDealType.CONTINUE);
		flow.setWeixinMessage(replyTextMessage);
		return flow;
	}

	
	
}
