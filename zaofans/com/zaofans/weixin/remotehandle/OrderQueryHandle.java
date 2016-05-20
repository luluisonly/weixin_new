package com.zaofans.weixin.remotehandle;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

public class OrderQueryHandle implements WeixinRemoteTextMessageCellHandle {

	private static final String QUERYURL = SharedBundle.getProperties("server.soa.orderform.query");

	private static final String BRAND = SharedBundle.getProperties("brand");
	
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
			String orderType = retJson.getString("ORDERTYPE");
			String createDate = retJson.getString("CREATEDATE");
			String payStatus = retJson.getString("PAYSTATUS");
			StringBuilder sb = new StringBuilder();
			sb.append("商店名称:" + BRAND + "\n");
			sb.append("订单编号:" + orderID + "\n");
			if (orderType.equals("MONTHLY")) {
				sb.append("订单类型:整周预定\n");
				sb.append("起始日期:" + retJson.getString("START") + "\n");
				sb.append("结束日期:" + retJson.getString("END") + "\n");
			} else if (orderType.equals("SINGLE")) {
				sb.append("订单类型:预定\n");
				sb.append("派送日期:");
				JSONArray orders = retJson.getJSONArray("ORDERS");
				for(int i = 0 ; i < orders.size(); i++ ){
					JSONObject order = orders.getJSONObject(i);
					sb.append(order.getString("DISPDATE")+",");
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append("\n");
			}
			sb.append("下单时间:" + createDate + "\n");
			sb.append("订单状态:" + ("PAID".equals(payStatus) ? "已支付" : "未支付" + "\n\n"));
			sb.append("如有疑问，请联系客服电话4008900301。非工作时段也可以发送\"help\"或\"帮助\"进行相关咨询。");
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

	// public static void main(String[] args) throws Throwable {
	// String openid = "onbRUsyIXOMPNETJ1IkjJGSVAu4A";
	// String orderID = "20150901aaagT1";
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("__sessionid", openid);
	// String ret =
	// SimpleHttpClient.invokeGet4String("http://www.zaofans.com/zaofans_test/orderform/orderForm/query/"+orderID,
	// params, 7200);
	// System.out.println(ret);
	// }
}
