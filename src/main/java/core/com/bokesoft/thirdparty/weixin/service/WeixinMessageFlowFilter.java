package com.bokesoft.thirdparty.weixin.service;

import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinTextMessage;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalService;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

/**
 * 业务步骤控制过滤器
 * #b 回退
 * #t 退出
 */
public class WeixinMessageFlowFilter implements WeixinMessageServiceFilter {

	public WeixinMessage doFilter(WeixinSession session, WeixinMessage message) throws Exception {
		WeixinMessageFlowLocalService flowLocalService = session.getWeixinMessageFlowLocalService();
		if (flowLocalService != null && message.getMsgType() == WeixinMessage.TEXT) {
			WeixinTextMessage textMessage = (WeixinTextMessage) message;
			String content = textMessage.getContent();
			if ("返回".equals(content) || "退出".equals(content)) {
				return flowLocalService.handleFlow(session, textMessage);
			}
//			if (content.startsWith("#")  || content.startsWith("＃") ) {
//					if (content.length() == 1) {
//						return message;
//					}else{
//						content = content.substring(1);
//						if ("b".equals(content) ) {
//							message = flowLocalService.goback(session, message);
//						} else if ("t".equals(content)) {
//							message = flowLocalService.gobreak(session, message);
//						}
//					}
//				return flowLocalService.handleFlow(session, textMessage);
//			}
		}
		return message;
		
	}

}
