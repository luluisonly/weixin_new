package com.bokesoft.thirdparty.weixin.remote;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;
/**
 * 用于远程调用第三方组件处理用户发来的指令
 *
 */
public interface WeixinRemoteProxy {

	/**
	 * 发送请求
	 * @param session
	 * @param remoteMessage
	 * @return
	 * @throws Exception
	 */
	public abstract SOAResponseMessage request(WeixinSession session, WeixinRemoteMessage remoteMessage) throws Exception;
	
	/**
	 * 获取代理类型名称
	 * @return
	 */
	public abstract String getProxyTypeName();
	
}
