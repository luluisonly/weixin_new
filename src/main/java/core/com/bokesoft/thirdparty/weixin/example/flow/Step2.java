package com.bokesoft.thirdparty.weixin.example.flow;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlowDealType;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageWrap4Flow;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.flow.AbstractWeixinMessageFlowHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class Step2 extends AbstractWeixinMessageFlowHandle{

	private static final long serialVersionUID = -6211299807252262184L;

	public WeixinMessageWrap4Flow doActionImpl(WeixinRmoteSession cache, WeixinRemoteMessage message)
			throws Exception {
		WeixinRemoteTextMessage textMessage = (WeixinRemoteTextMessage)message;
		String content = textMessage.getContent();
		WeixinMessageWrap4Flow flow = new WeixinMessageWrap4Flow();
		if ("1".equals(content)) {
			WeixinReplyTextMessage replyTextMessage = new WeixinReplyTextMessage("请输入2");
			flow.setFlowDealType(WeixinMessageFlowDealType.CONTINUE);
			flow.setWeixinMessage(replyTextMessage);
		}else{
			WeixinReplyTextMessage replyTextMessage = new WeixinReplyTextMessage("请输入1");
			flow.setFlowDealType(WeixinMessageFlowDealType.HOLD);
			flow.setWeixinMessage(replyTextMessage);
		}
		return flow;
	}
	
}
