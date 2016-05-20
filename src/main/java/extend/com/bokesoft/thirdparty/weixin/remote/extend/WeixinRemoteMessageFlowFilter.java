package com.bokesoft.thirdparty.weixin.remote.extend;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowService;

/**
 * 业务步骤控制过滤器 #b 回退 #t 退出
 */
public class WeixinRemoteMessageFlowFilter implements WeixinRemoteMessageServiceFilter {

	public SOAResponseMessage doFilter(WeixinRmoteSession session, WeixinRemoteMessage message)throws Exception {
		WeixinMessageFlowService flowService = session.getWeixinMessageFlowService();
		if (message.getMsgType() == WeixinRemoteMessage.REMOTE_TEXT && flowService != null) {
			WeixinRemoteTextMessage textMessage = (WeixinRemoteTextMessage) message;
			String content = textMessage.getContent();
			if ("退出".equals(content)) {
				return flowService.gobreak(session, message);
			}
			if ("返回".equals(content)) {
				return flowService.goback(session, message);
			}
//			if (content.startsWith("#") || content.startsWith("＃")) {
//				if (content.length() == 1) {
//					return null;
//				} else {
//					content = content.substring(1);
//					if ("b".equals(content)) {
//						return flowService.goback(session, message);
//					} else if ("t".equals(content)) {
//						return flowService.gobreak(session, message);
//					}
//				}
//			}
		}
		return null;
	}

}
