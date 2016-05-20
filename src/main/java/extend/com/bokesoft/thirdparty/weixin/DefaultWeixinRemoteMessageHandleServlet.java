package com.bokesoft.thirdparty.weixin;

import javax.servlet.ServletConfig;

import com.bokesoft.thirdparty.weixin.example.flow.DefaultFlowTest;
import com.bokesoft.thirdparty.weixin.example.flow.location.LocationFindFlowTest;
import com.bokesoft.thirdparty.weixin.example.flow.location.LocationGoFlowTest;
import com.bokesoft.thirdparty.weixin.example.weather.WeixinRemoteTextMessageWeatherCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.DefaultWeixinRemoteContext;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteContextFactory;


public class DefaultWeixinRemoteMessageHandleServlet extends WeixinRemoteMessageHandleServlet {

	private static final long serialVersionUID = 1L;

	public void initWeixinConfig(ServletConfig config) {
		DefaultWeixinRemoteContext remoteContext = (DefaultWeixinRemoteContext) WeixinRemoteContextFactory.getWeixinRemoteContext();
		remoteContext.putWeixinMessageFlow("我要去", new LocationGoFlowTest());
		remoteContext.putWeixinMessageFlow("附近", new LocationFindFlowTest());
		remoteContext.putWeixinMessageFlow("远程测试", new DefaultFlowTest());
		remoteContext.putWeixinMessageFlow("本地测试", new DefaultFlowTest());
		remoteContext.putWeixinRemoteTextMessageCellHandle("天气", new WeixinRemoteTextMessageWeatherCellHandle());
		remoteContext.init();
	}
}