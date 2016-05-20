package com.bokesoft.thirdparty.weixin.remote.proxy;

import javax.naming.OperationNotSupportedException;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteProxy;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

/**
 * 把微信接收到指令发到RMI
 *
 */
public class WeixinRemoteRMIProxy implements WeixinRemoteProxy{

	public SOAResponseMessage request(WeixinSession session,WeixinRemoteMessage remoteMessage) throws Exception {
		throw new OperationNotSupportedException("not support WeixinRemoteRMIProxy");
	}

	public String getProxyTypeName() {
		return "rmi";
	}

	
}
