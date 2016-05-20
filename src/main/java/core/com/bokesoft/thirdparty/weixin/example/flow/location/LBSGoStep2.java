package com.bokesoft.thirdparty.weixin.example.flow.location;

import java.net.URLEncoder;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlowDealType;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageWrap4Flow;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.flow.AbstractWeixinMessageFlowHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class LBSGoStep2 extends AbstractWeixinMessageFlowHandle{

	private static final long serialVersionUID = 1669786878458722230L;

	public WeixinMessageWrap4Flow doActionImpl(WeixinRmoteSession cache,
			WeixinRemoteMessage message) throws Exception {
		WeixinMessageWrap4Flow flow = new WeixinMessageWrap4Flow();
		if(message instanceof WeixinRemoteLocationMessage){
			String destination=(String) cache.getAttribute("destination");
			WeixinRemoteLocationMessage remoteLocationMessage = (WeixinRemoteLocationMessage) message;
			String Location_X = remoteLocationMessage.getLocation_X();
			String Location_Y = remoteLocationMessage.getLocation_Y();
			String address = remoteLocationMessage.getLabel();
//			String city = address.substring(address.indexOf("中国")+"中国".length(),address.indexOf("市")+"市".length());
			String city = getCity(address);
			String location = Location_X+","+Location_Y;
			String changedLocation = locChange(location);
			String walkUrl = "http://api.map.baidu.com/direction?mode=walking&output=html&origin="+changedLocation+"&region="+URLEncoder.encode(city,"UTF-8");
			String walkingUrl = walkUrl+"&destination="+URLEncoder.encode(destination,"UTF-8");
			String resultURL = SimpleHttpClient.getRedirectURL(walkingUrl,3000);
			WeixinReplyTextMessage replyTextMessage = new WeixinReplyTextMessage("<a href='"+resultURL+"'>点击查看如何前往"+destination+"</a>");
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
