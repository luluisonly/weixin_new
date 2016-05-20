package com.bokesoft.thirdparty.weixin.example.weather;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteResponseFilter;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeatherResponseFilter implements WeixinRemoteResponseFilter{

	private static final Logger logger = Logger.getLogger(WeatherResponseFilter.class);
	
	public SOAResponseMessage doFilter(WeixinSession session, WeixinRemoteMessage remoteMessage,
			SOAResponseMessage responseMessage) throws Exception {
		
		if ("天气".equals(remoteMessage.getServiceKey())) {
			JSONArray jsonArray = (JSONArray) responseMessage.getData();
			if (jsonArray != null && !jsonArray.isEmpty()) {
				Object flag = jsonArray.get(0);
				if ("查询结果为空！".equals(flag)) {
					responseMessage.setMessage("未查询到该城市的天气！");
				}else if ("系统维护中！".equals(flag)) {
					responseMessage.setMessage("天气查询服务商系统正在维护！");
				}else if("直辖市".equals(flag)){
					StringBuffer buffer = new StringBuffer("您查询的城市为：");
					buffer.append(jsonArray.get(1));
					buffer.append("\n");
					buffer.append("今日温度");
					buffer.append(jsonArray.get(5));
					buffer.append("，");
					buffer.append(jsonArray.get(7));
					buffer.append("\n");
					buffer.append(jsonArray.get(10));
					buffer.append(jsonArray.get(11));
					buffer.append("\n");
					buffer.append(jsonArray.get(13));
					buffer.append(jsonArray.get(12));
					buffer.append(jsonArray.get(14));
					buffer.append("\n");
					buffer.append(jsonArray.get(18));
					buffer.append(jsonArray.get(17));
					buffer.append(jsonArray.get(19));
					responseMessage.setMessage(buffer.toString());
				}else{
					responseMessage.setMessage("天气查询服务商系统正在维护！");
					logger.info(jsonArray.toString());
				}
			}
		}
		return responseMessage;
	}

	
}
