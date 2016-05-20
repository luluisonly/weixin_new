package com.zaofans.weixin.remotehandle;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.util.UrlEncoded;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class CanceledOrderQueryHandle implements WeixinRemoteTextMessageCellHandle {

	private static final String QUERYURL = SharedBundle.getProperties("server.soa.orderform.query");

	@Override
	public SOAResponseMessage handleWeixinRemoteTextMessage(WeixinRmoteSession cache,
			WeixinRemoteTextMessage remoteTextMessage) throws Exception {
		String openid = remoteTextMessage.getFromUserName();
		String orderID = remoteTextMessage.getParam()[0];
		if (StringUtil.isBlankOrNull(orderID)) {
			return SOAResponseMessage.NULL;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("__sessionid", openid);
		String ret = SimpleHttpClient.invokeGet4String(QUERYURL + orderID, params, 7200);
		System.out.println(ret);
		JSONObject retJson = JSONObject.parseObject(ret);
		if (retJson.get("success") != null && retJson.get("success").equals(true)) {
			String storeName = retJson.getString("storeName");
			String orderType = retJson.getString("orderType");
			String orderCount = retJson.getString("orderCount");
			String orderCanceledOrderCount= retJson.getString("orderCanceledOrderCount");
			String createDate = retJson.getString("createDate");
			StringBuilder sb = new StringBuilder();
			sb.append("商店名称:" + storeName + "\n");
			sb.append("订单编号:" + orderID + "\n");
			sb.append("套餐份数:" + orderCount + "\n");
			sb.append("取消份数:"+ orderCanceledOrderCount + "\n");
			if (orderType.equals("MONTHLY")) {
				sb.append("订单类型:整周预定\n");
				sb.append("起始日期:" + retJson.getString("startDate") + "\n");
				sb.append("结束日期:" + retJson.getString("endDate") + "\n");
				sb.append("取消日期:"+retJson.getString("updateOrderDate")+"\n");
			} else if (orderType.equals("SINGLE")) {
				sb.append("订单类型:预定\n");
				sb.append("派送日期:" + retJson.getString("dispDate") + "\n");
				sb.append("取消日期:"+retJson.getString("updateOrderDate")+"\n");
			}
			sb.append("下单时间:" + createDate + "\n");
			System.out.println(sb.toString());
			return new SOAResponseMessage(0, sb.toString());
		} else {
			return new SOAResponseMessage(0, retJson.getString("error"));
		}
	}

	@Override
	public String getHandleName() {
		return "orderquery";
	}

	 public static void main(String[] args) throws Throwable {
		 String openid = "onbRUsznFSvU7s1_PsuxGwbdyR0U";
		 String orderID = "20150831aaagJM";
//		 String orderID = "20150830aaafUm";
		 Map<String, String> params = new HashMap<String, String>();
		 params.put("__sessionid", openid);
		 String ret =
				 SimpleHttpClient.invokeGet4String("http://localhost:8080/zaofans-dongze/orderform/orderForm/canceledQuery/"+orderID, params, 7200);
		 JSONObject retJson = JSONObject.parseObject(ret);
		 StringBuilder sb = new StringBuilder();
			if (retJson.get("success") != null && retJson.get("success").equals(true)) {
				String storeName = retJson.getString("storeName");
				String orderType = retJson.getString("orderType");
				String orderCount = retJson.getString("orderCount");
				String orderCanceledOrderCount= retJson.getString("orderCanceledOrderCount");
				String createDate = retJson.getString("createDate");
				sb.append("商店名称:" + storeName + "\n");
				sb.append("订单编号:" + orderID + "\n");
				sb.append("套餐份数:" + orderCount + "\n");
				sb.append("取消份数:"+ orderCanceledOrderCount + "\n");
				if (orderType.equals("MONTHLY")) {
					sb.append("订单类型:整周预定\n");
//					sb.append("起始日期:" + retJson.getString("startDate") + "\n");
//					sb.append("结束日期:" + retJson.getString("endDate") + "\n");
					sb.append("取消日期:"+retJson.getString("dispDate")+"\n");
					sb.append("取消时间:"+retJson.getString("updateOrderDate")+"\n");
				} else if (orderType.equals("SINGLE")) {
					sb.append("订单类型:预定\n");
					sb.append("派送日期:" + retJson.getString("dispDate") + "\n");
					sb.append("取消日期:"+retJson.getString("updateOrderDate")+"\n");
				}
				sb.append("下单时间:" + createDate + "\n");
				System.out.println(sb.toString());
			} else {
				System.out.println(retJson.getString("error"));
			}
			JSONObject message = new JSONObject();
			message.put("touser", "onbRUsyIXOMPNETJ1IkjJGSVAu4A");
			message.put("msgtype", "text");
			JSONObject text = new JSONObject();
			text.put("content",sb.toString());
			message.put("text", text);
			System.out.println(message);
			Map<String,String> map = new HashMap<String,String>();
			map.put("param", UrlEncoded.encodeString(message.toString(),"utf-8"));
			map.put("uname", "zaofans");
			map.put("type", "sendWeixinMessage");
			String result = SimpleHttpClient.invokePost4String("http://www.zaofans.com/weixin-test/open-api", map, 30000);
			System.out.println(result);
	 }
}
