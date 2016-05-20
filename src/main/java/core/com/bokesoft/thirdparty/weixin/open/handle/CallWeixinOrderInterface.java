package com.bokesoft.thirdparty.weixin.open.handle;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;
import com.zaofans.weixin.common.PayUtils;
import com.zaofans.weixin.common.XmlUtils;

public class CallWeixinOrderInterface extends AbstractWeixinSOAServieHandle {

	@Override
	public SOAResponseMessage handle(WeixinContext context,
			SOARequestMessage message) throws Exception {
		// 调用统一下单接口
		// if (!message.isWeixin()) {
		// // 如果请求不是来自于微信，直接返回错误信息
		// return new SOAResponseMessage(1000,
		// "this request is not from weixin");
		// }
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if (publicNumber == null) {
			return new SOAResponseMessage(1000, "unknow uname:" + uname);
		}
//		String appid = publicNumber.getApp_id();
//		String mch_id = publicNumber.getMch_id();
//		String key = publicNumber.getKey();
		String appid = SharedBundle.getProperties("server.weixin.pay.appid");
		String mch_id = SharedBundle.getProperties("server.weixin.pay.mchid");
		String key = SharedBundle.getProperties("server.weixin.pay.key");
		String param = message.getStringParam();
		param =  URLDecoder.decode(param,"UTF-8");
		JSONObject orderInfo = JSONObject.parseObject(param);
		Map<String, String> map = parseOrderInfo(orderInfo);
		map.put("appid", appid);// 放入appid
		map.put("mch_id", mch_id);// 放入商户号
		map.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));// 随机生成16位验证字符
		String signValue = PayUtils.paySign(map, key);
		if (StringUtils.isBlank(signValue)) {
			return new SOAResponseMessage(1000, "something wrong");
		}
		map.put("sign", signValue);
		String xml = XmlUtils.maptoXml(map);
		System.out.println("----- send -----"+xml);
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String result = invoker.callWeixinOrderInterface(xml);
		System.out.println("-----return-----"+xml);
		Map<String, String> resultMap = XmlUtils.parseContent(result);
		if (resultMap.get("return_code").equalsIgnoreCase("fail")) {
			return new SOAResponseMessage(1000, "return_msg:"
					+ resultMap.get("return_msg"));
		} else if (resultMap.get("result_code").equalsIgnoreCase("fail")) {
			return new SOAResponseMessage(1000, "err_code:"
					+ resultMap.get("err_code") + ";err_code_des:"
					+ resultMap.get("err_code_des"));
		} else {
			Map<String,String> params = new HashMap<String,String>();
			params.put("return_code","SUCCESS");
			params.put("result_code","SUCCESS");
			params.put("appid", appid);
			params.put("mch_id", mch_id);
			params.put("prepay_id", resultMap.get("prepay_id"));
			params.put("nonce_str", resultMap.get("nonce_str"));
			String paySign = PayUtils.paySign(params, key);
			params.put("sign", paySign);
			JSONObject json = new JSONObject();
			Iterator<String> it = params.keySet().iterator();  
		       while (it.hasNext()) {  
		           String keyStr = it.next().toString();
		           String valueStr = params.get(keyStr);
		           json.put(keyStr, valueStr);
		       }
			return new SOAResponseMessage(0, json.toString());
		}
	}

	private Map<String, String> parseOrderInfo(JSONObject orderInfo) {
		String money = "1";
		Map<String, String> map = new HashMap<String, String>();
		map.put("trade_type", orderInfo.getString("trade_type"));
		if(map.get("trade_type")==null){
			map.put("trade_type", "NATIVE");
			map.put("product_id", orderInfo.getString("product_id"));
		}
		if (orderInfo.getString("product_id") != null && map.get("trade_type")==null) {
			map.put("product_id", orderInfo.getString("product_id"));
		}
		map.put("body", orderInfo.getString("body"));
		if(map.get("body")==null){
			String product_id = null;
			if((product_id=map.get("product_id"))!=null){
				String[] strs = product_id.split("_");
				money = strs[1];
				if("0".equalsIgnoreCase(money)){
					money="1";
				}
				double temp = Double.valueOf(money);
				temp = temp/100.0;
				map.put("body","打赏"+temp+"元钱");
			}
		}
		if (orderInfo.getString("detail") != null) {
			map.put("detail", orderInfo.getString("detail"));
		}
		if (orderInfo.getString("attach") != null) {
			map.put("attach", orderInfo.getString("attach"));
		}
		map.put("out_trade_no", orderInfo.getString("out_trade_no"));
		if(map.get("out_trade_no")==null){
			map.put("out_trade_no", System.currentTimeMillis()+"");
		}
		if (orderInfo.getString("fee_type") != null) {
			map.put("fee_type", orderInfo.getString("fee_type"));
		}
		map.put("total_fee", orderInfo.getString("total_fee"));
		if(map.get("total_fee")==null){
			map.put("total_fee", money);
		}
		map.put("spbill_create_ip", orderInfo.getString("spbill_create_ip"));
		if(map.get("spbill_create_ip")==null){
			map.put("spbill_create_ip", "192.168.1.1");
		}
		if (orderInfo.getString("time_start") != null) {
			map.put("time_start", orderInfo.getString("time_start"));
		}
		if (orderInfo.getString("time_expire") != null) {
			map.put("time_expire", orderInfo.getString("time_expire"));
		}
		if (orderInfo.getString("goods_tag") != null) {
			map.put("goods_tag", orderInfo.getString("goods_tag"));
		}
		map.put("notify_url", orderInfo.getString("notify_url"));
		if(map.get("notify_url")==null){
			map.put("notify_url", SharedBundle.getProperties("server.weixin.pay.notifyurl2"));
		}
		if (orderInfo.getString("limit_pay") != null) {
			map.put("limit_pay", orderInfo.getString("limit_pay"));
		}
		if ("JSAPI".equals(orderInfo.getString("trade_type"))) {
			map.put("openid", orderInfo.getString("openid"));
		}
		return map;
	}

}
