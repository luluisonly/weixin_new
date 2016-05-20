package com.bokesoft.thirdparty.weixin.example.flow.location;

import java.net.URLEncoder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlowDealType;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageWrap4Flow;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.flow.AbstractWeixinMessageFlowHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class LBSFindStep2 extends AbstractWeixinMessageFlowHandle{

	private static final long serialVersionUID = -5516890617675160178L;

	public WeixinMessageWrap4Flow doActionImpl(WeixinRmoteSession cache,
			WeixinRemoteMessage message) throws Exception {
		WeixinMessageWrap4Flow flow = new WeixinMessageWrap4Flow();
		if(message instanceof WeixinRemoteLocationMessage){
			String keyWord=(String) cache.getAttribute("keyWord");
			keyWord = java.net.URLEncoder.encode(keyWord,"UTF-8");
			WeixinRemoteLocationMessage remoteLocationMessage = (WeixinRemoteLocationMessage) message;
			String content = "";
			String Location_X = remoteLocationMessage.getLocation_X();
			String Location_Y = remoteLocationMessage.getLocation_Y();
			String location = Location_X+","+Location_Y;
			String address = remoteLocationMessage.getLabel();
			String city =getCity(address);
			String url = "http://api.map.baidu.com/place/v2/search?&radius=2000&output=json&ak=UlL8OIXLtG2u5Tn5SzcrEmkN";
			url=url+"&query="+keyWord+"&location="+location;
			String msg = SimpleHttpClient.invokeGet4String(url,3000);
			JSONObject result = JSONObject.parseObject(msg);
			if("ok".equals(result.getString("message"))){
				JSONArray resultArray = result.getJSONArray("results");
				String changedLocation = locChange(location);
				String walkUrl = "http://api.map.baidu.com/direction?mode=walking&output=html&origin="+changedLocation+"&region="+URLEncoder.encode(city,"UTF-8");
				int size =  resultArray.size() >= 5 ? 5 : resultArray.size();
				for(int i = 0;i<size;i++){
					JSONObject jsonObject = resultArray.getJSONObject(i);
					String name = jsonObject.getString("name");
					JSONObject locaJson = (JSONObject) jsonObject.get("location");
					String lat = locaJson.getString("lat");
					String lng = locaJson.getString("lng");
					String loc = "latlng:"+lat+","+lng+"|name:"+URLEncoder.encode(name,"UTF-8");
					String walkingUrl = walkUrl+"&destination="+loc;
					String resultURL = SimpleHttpClient.getRedirectURL(walkingUrl,3000);
					content = content+"<a href='"+resultURL+"'>"+name+"</a>\n\n";
				}
			}
			WeixinReplyTextMessage replyTextMessage = new WeixinReplyTextMessage(content);
			flow.setFlowDealType(WeixinMessageFlowDealType.END);
			flow.setWeixinMessage(replyTextMessage);
		}else{
			WeixinReplyTextMessage replyTextMessage = new WeixinReplyTextMessage("请上传您的地理位置。");
			flow.setFlowDealType(WeixinMessageFlowDealType.HOLD);
			flow.setWeixinMessage(replyTextMessage);
		}
		return flow;
	}
	private String locChange(String location){
		String[] locations = location.split(",");
		double lat = Double.parseDouble(locations[0]);
		double lng = Double.parseDouble(locations[1]);
		lat=lat+0.005609;
		lng=lng+0.006770;
		String changedLoc = lat+","+lng;
		return changedLoc;
	}

	private String getCity(String allPath){
		int cityEnd = allPath.indexOf("市");
		if (allPath.indexOf("中国") > -1) {
			return allPath.substring(2, cityEnd+1);
		}else{
			return allPath.substring(0, cityEnd+1);
		}
	}
	
	
	
}
