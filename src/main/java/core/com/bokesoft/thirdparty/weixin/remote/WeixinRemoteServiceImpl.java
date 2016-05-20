package com.bokesoft.thirdparty.weixin.remote;

import java.util.List;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;
/**
 * 用于远程调用第三方组件处理用户发来的指令
 *
 */
public class WeixinRemoteServiceImpl implements WeixinRemoteService {
	
	private static final Logger LOGGER = Logger.getLogger(WeixinRemoteServiceImpl.class);

	public SOAResponseMessage doService(WeixinSession session, WeixinRemoteMessage message) throws Exception {
		WeixinContext context = session.getWeixinContext();
		WeixinRemoteProxy remoteProxy = context.getWeixinRemoteProxy(message.getRemoteType());
		if (remoteProxy == null) {
			LOGGER.warn("no such proxy:" + message.getRemoteType());
			return new SOAResponseMessage(1000,"no such proxy:" + message.getRemoteType());
		}
		LOGGER.info("remote service:" +message.getRemoteType()+",url:"+message.getUrl());
		SOAResponseMessage responseMessage = remoteProxy.request(session, message);
		List<WeixinRemoteResponseFilter> filters = context.getWeixinRemoteResponseFilters();
		for(WeixinRemoteResponseFilter filter:filters){
			responseMessage = filter.doFilter(session, message, responseMessage);
		}
		return responseMessage;
	}

}
