package com.zaofans.weixin.remotehandle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class OrderListQueryHandle implements WeixinRemoteTextMessageCellHandle{
	
	private static final Logger LOGGER = Logger.getLogger(OrderListQueryHandle.class);
	
	private static final String url = SharedBundle.getProperties("server.soa.orderlist.query");
	private static final String MORE = SharedBundle.getProperties("server.soa.content.dingdan");
	private static final String YUDING = SharedBundle.getProperties("server.soa.content.yuding");
	
	@Override
	public SOAResponseMessage handleWeixinRemoteTextMessage(WeixinRmoteSession cache,
			WeixinRemoteTextMessage remoteTextMessage) throws Exception {
		String wxid = remoteTextMessage.getFromUserName();
		LOGGER.info(wxid);
		Map<String,String> headers = new HashMap<String, String>();
		headers.put("__sessionid", wxid);
		String res = SimpleHttpClient.invokeGet4String(url, headers, 7200);
		LOGGER.info(res);
		StringBuffer content = new StringBuffer();
		JSONArray array = JSONArray.parseArray(res);
		if(array.size()>0){
			for(int i=0;i<array.size();i++){
				JSONObject json =array.getJSONObject(i);
				content.append("订单编号："+json.getString("OrderId")+",下单日期："+json.get("OrderDate")+"\n");
			}
			content.append("点击<a href=\""+MORE+"\">此处</a>查看更多订单信息\n");
			content.append("回复t+订单号,可以快速查询订单详情哦");
		}else{
			content.append("您未支付完成过订单，点击<a href=\""+YUDING+"\">此处</a>购买");
		}
		return new SOAResponseMessage(0,content.toString());
	}

	@Override
	public String getHandleName() {
		// TODO Auto-generated method stub
		return "OrderListQuery";
	}
	
//	public static void main(String[] args) throws IOException {
//		String wxid = "onbRUsyIXOMPNETJ1IkjJGSVAu4A";
//		Map<String,String> headers = new HashMap<String, String>();
//		headers.put("__sessionid", wxid);
//		String res = SimpleHttpClient.invokeGet4String("http://localhost:8080/zaofans-dongze/orderform/orderForms/search", headers, 7200);
//		LOGGER.info(res);
//		StringBuffer content = new StringBuffer();
//		if(!StringUtils.isBlank(res)){
//			JSONArray array = JSONArray.parseArray(res);
//			for(int i=0;i<array.size();i++){
//				JSONObject json =array.getJSONObject(i);
//				content.append("订单编号："+json.getString("OrderId")+",下单日期："+json.get("OrderDate")+"\n");
//			}
//			content.append("点击<a href=\""+MORE+"\">此处</a>查看更多订单信息\n");
//			content.append("回复t+订单号,可以快速查询订单详情哦");
//		}else{
//			content.append("您还未下过单，点击<a href=\""+YUDING+"\">此处</a>购买");
//		}
//	}
	
}
