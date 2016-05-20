package com.bokesoft.thirdparty.weixin.example.flow.location;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlowDealType;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageWrap4Flow;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.flow.AbstractWeixinMessageFlowHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class LBSFindStep1 extends AbstractWeixinMessageFlowHandle {

	private static final long serialVersionUID = -8240396389006556955L;

	public WeixinMessageWrap4Flow doActionImpl(WeixinRmoteSession cache,
			WeixinRemoteMessage message) throws Exception {
		cache.setAttribute("keyWord", message.getParam());
		WeixinReplyTextMessage replyTextMessage = new WeixinReplyTextMessage("请上传您的地理位置");
		WeixinMessageWrap4Flow flow = new WeixinMessageWrap4Flow();
		flow.setFlowDealType(WeixinMessageFlowDealType.CONTINUE);
		flow.setWeixinMessage(replyTextMessage);
		return flow;
	}

}
