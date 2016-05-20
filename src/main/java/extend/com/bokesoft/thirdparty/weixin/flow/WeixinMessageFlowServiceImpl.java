package com.bokesoft.thirdparty.weixin.flow;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlow;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlowDealType;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageWrap4Flow;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class WeixinMessageFlowServiceImpl implements WeixinMessageFlowService {
	
	private static final long serialVersionUID = 4266186219294029500L;

	private static final Logger LOGGER = Logger.getLogger(WeixinMessageFlowServiceImpl.class);

	private int currentIndex = 0;
	
	private WeixinMessageFlow messageFlow;

	private WeixinRemoteMessage weixinRemoteMessage;

	private WeixinMessage createBreakMessage(){
		//return new WeixinReplyTextMessage("您已经退出操作："	+ messageFlow.getName());
		return new WeixinReplyTextMessage("退出成功！");
	}

	public WeixinMessageFlow getMessageFlow() {
		return messageFlow;
	}

	public WeixinRemoteMessage getWeixinRemoteMessage() {
		return weixinRemoteMessage;
	}

	public SOAResponseMessage goback(WeixinRmoteSession cache, WeixinRemoteMessage message) throws Exception {
		 if (currentIndex == 1) {
			cache.setWeixinMessageFlowService(null);
			currentIndex = 0;
			return new SOAResponseMessage(301, null,createBreakMessage());
		} else {
			// currentIndex > 1
			currentIndex -= 2;
			WeixinMessageFlowHandle[] flows = messageFlow.getFlowHandles();
			WeixinMessageFlowHandle flow = flows[currentIndex];
			flow.setGoback(true);
			return handleFlow(cache, message);
		}
	}

	public SOAResponseMessage gobreak(WeixinRmoteSession cache, WeixinRemoteMessage message)throws Exception  {
		cache.setWeixinMessageFlowService(null);
		return new SOAResponseMessage(301, null,createBreakMessage());
	}
	
	public SOAResponseMessage handleFlow(WeixinRmoteSession cache, WeixinRemoteMessage message)
			throws Exception {
		WeixinMessageFlowHandle[] handles = messageFlow.getFlowHandles();
		if (handles == null) {
			LOGGER.error("null flow:"+messageFlow.getName());
			return SOAResponseMessage.SYSTEM_ERROR_302;
		}
		WeixinMessageFlowHandle flow = handles[currentIndex];
		WeixinMessageWrap4Flow messageWrap4Flow = flow.doAction(cache, message);
		WeixinMessageFlowDealType dealType = messageWrap4Flow.getFlowDealType();
		switch (dealType) {
		case CONTINUE:
			currentIndex++;
			if (currentIndex == handles.length) {
				cache.setWeixinMessageFlowService(null);
				return new SOAResponseMessage(301,  null,messageWrap4Flow.getWeixinMessage());
			}
			return new SOAResponseMessage(300, null,messageWrap4Flow.getWeixinMessage());
		case GOBACK:
			if (currentIndex > 0) {
				currentIndex--;
			}else{
				cache.setWeixinMessageFlowService(null);
				return new SOAResponseMessage(301,  null,messageWrap4Flow.getWeixinMessage());
			}
		case BREAK:
			cache.setWeixinMessageFlowService(null);
			return new SOAResponseMessage(301,  null,messageWrap4Flow.getWeixinMessage());
		case HOLD:
			return new SOAResponseMessage(300,  null,messageWrap4Flow.getWeixinMessage());
		default:
			cache.setWeixinMessageFlowService(null);
			return new SOAResponseMessage(301, null,messageWrap4Flow.getWeixinMessage());
		}
		
	}

	public void setCurrentAction(int currentIndex){
		if (currentIndex-1 >= messageFlow.getFlowHandles().length) {
			throw new ArrayIndexOutOfBoundsException("max action :"
						+messageFlow.getFlowHandles().length+",currentIndex :"+currentIndex);
		}
		this.currentIndex = currentIndex;
	}

	public void setMessageFlow(WeixinMessageFlow messageFlow) {
		this.messageFlow = messageFlow;
	}

	public void setWeixinRemoteMessage(WeixinRemoteMessage weixinRemoteMessage) {
		this.weixinRemoteMessage = weixinRemoteMessage;
	}
	
}
