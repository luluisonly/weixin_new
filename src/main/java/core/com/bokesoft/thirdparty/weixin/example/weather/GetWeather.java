package com.bokesoft.thirdparty.weixin.example.weather;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.example.weather.WeatherWebServiceStub.GetWeatherbyCityName;
import com.bokesoft.thirdparty.weixin.example.weather.WeatherWebServiceStub.GetWeatherbyCityNameResponse;

public class GetWeather {

	private static Map<String,SOAResponseMessage> weathers = new HashMap<String,SOAResponseMessage>();
	
	private static Map<String,Long> weatherUpdates = new HashMap<String,Long>();
	
	public static SOAResponseMessage getWeather(String city) throws RemoteException{
		if (StringUtil.isBlankOrNull(city)) {
			city = "上海";
		}
		SOAResponseMessage message = (SOAResponseMessage) weathers.get(city);
		if (message == null) {
			synchronized (weathers) {
				message = (SOAResponseMessage) weathers.get(city);
				if (message == null) {
					message = getFreshWeather(city);
					weathers.put(city, message);
					weatherUpdates.put(city, System.currentTimeMillis());
				}
			}
		}else{
			long last = weatherUpdates.get(city);
			synchronized (weathers) {
				if (System.currentTimeMillis() - last > 2*60*60*1000) {
					message = getFreshWeather(city);
					weathers.put(city, message);
					weatherUpdates.put(city, System.currentTimeMillis());
				}
			}
		}
		return message;
	}
	
	
	private static SOAResponseMessage getFreshWeather(String city) throws RemoteException{
		WeatherWebServiceStub stub = new WeatherWebServiceStub();
		GetWeatherbyCityName getWeatherbyCityName4 = new GetWeatherbyCityName();
		getWeatherbyCityName4.setTheCityName(city);
		GetWeatherbyCityNameResponse response2 = stub.getWeatherbyCityName(getWeatherbyCityName4);
		String []strs = response2.getGetWeatherbyCityNameResult().getString();
		return new SOAResponseMessage(0, null, strs);
	}
}
