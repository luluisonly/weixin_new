package com.bokesoft.thirdparty.weixin.open.handle;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;
import com.zaofans.weixin.common.PayUtils;
import com.zaofans.weixin.common.XmlUtils;

/**
 * 针对使用JSAPI实现支付
 *
 */
public class CallWeixinOrderInterfaceFormJSAPI extends AbstractWeixinSOAServieHandle {
	private static final Logger LOGGER = Logger.getLogger(CallWeixinOrderInterfaceFormJSAPI.class);
	private static final String appid = SharedBundle.getProperties("server.weixin.pay.appid");
	private static final String mch_id = SharedBundle.getProperties("server.weixin.pay.mchid");
	private static final String key = SharedBundle.getProperties("server.weixin.pay.key");
	@Override
	public SOAResponseMessage handle(WeixinContext context,
			SOARequestMessage message) throws Exception {
		//  调用统一下单接口
		 if (!message.isWeixin()) {
		 // 如果请求不是来自于微信，直接返回错误信息
			 return new SOAResponseMessage(1000,"this request is not from weixin");
		 }
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if (publicNumber == null) {
			return new SOAResponseMessage(1000, "unknow uname:" + uname);
		}
//		String appid = publicNumber.getApp_id();
//		String mch_id = publicNumber.getMch_id();
//		String key = publicNumber.getKey();
		String param = message.getStringParam();
		param =  URLDecoder.decode(param,"UTF-8");
		JSONObject orderInfo = JSONObject.parseObject(param);
		LOGGER.info("PayMessageFromWeixin:"+orderInfo.toString());
		Map<String, String> map = parseOrderInfo(orderInfo);
		if(map == null){
			return new SOAResponseMessage(1000, "core文件中server.weixin.pay.notifyurl2必须配置，请检查");
		}
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));// 随机生成16位验证字符
		String signValue = PayUtils.paySign(map, key);
		if (StringUtils.isBlank(signValue)) {
			return new SOAResponseMessage(1000, "sign wrong");
		}
		map.put("sign", signValue);
		String xml = XmlUtils.maptoXml(map);
		LOGGER.info("PayMessageToWeixin:"+xml);
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String result = invoker.callWeixinOrderInterface(xml);
		LOGGER.info("ResponseMessageFromWeixin:"+result);
		Map<String, String> resultMap = XmlUtils.parseContent(result);
		if ("FAIL".equalsIgnoreCase(resultMap.get("return_code"))) {
			return new SOAResponseMessage(1000, "return_msg:"
					+ resultMap.get("return_msg"));
		} else if ("FAIL".equalsIgnoreCase(resultMap.get("result_code"))) {
			return new SOAResponseMessage(1000, "err_code:"
					+ resultMap.get("err_code") + ";err_code_des:"
					+ resultMap.get("err_code_des"));
		} else {
			//此处的返回值和扫码支付有区别
			Map<String,String> params = new HashMap<String,String>();
			params.put("appId", appid);
			params.put("timeStamp", System.currentTimeMillis()+"");
			params.put("nonceStr", RandomStringUtils.randomAlphanumeric(16));
			params.put("package", "prepay_id="+resultMap.get("prepay_id"));
			params.put("signType", "MD5");
			String paySign = PayUtils.paySign(params, key);
			params.put("paySign", paySign);
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

	private Map<String, String> parseOrderInfo(JSONObject orderInfo) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("body", orderInfo.getString("body"));
		if (orderInfo.getString("detail") != null) {
			map.put("detail", orderInfo.getString("detail"));
		}
		if (orderInfo.getString("attach") != null) {
			map.put("attach", orderInfo.getString("attach"));
		}
		map.put("out_trade_no", orderInfo.getString("out_trade_no"));
		if (orderInfo.getString("fee_type") != null) {
			map.put("fee_type", orderInfo.getString("fee_type"));
		}
		if(orderInfo.getString("total_fee").indexOf(".")!=-1){
			throw new RuntimeException("total_fee contains a decimal point,it's wrong");
		}
		Integer total_fee = orderInfo.getInteger("total_fee");
		if(total_fee <= 1){
			total_fee = 1;
		}
		map.put("total_fee", total_fee+"");
		String spbill_create_ip = orderInfo.getString("spbill_create_ip");
		if(StringUtils.isBlank(spbill_create_ip)){
			//如果没有获取到ip，写一个默认的ip
			map.put("spbill_create_ip", "127.0.0.1");
		}else{
			map.put("spbill_create_ip", spbill_create_ip);
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
		String notify_url="";
		if(orderInfo.getString("notify_url")!=null){
			notify_url=orderInfo.getString("notify_url");
		}else{
			notify_url = SharedBundle.getProperties("server.weixin.pay.notifyurl2");
			if(StringUtils.isBlank(notify_url)){
				LOGGER.error("core文件中server.weixin.pay.notifyurl2必须配置，请检查");
				return null;
			}
		}
		map.put("notify_url", notify_url);
		if (orderInfo.getString("product_id") != null) {
			map.put("product_id", orderInfo.getString("product_id"));
		}
		if (orderInfo.getString("limit_pay") != null) {
			map.put("limit_pay", orderInfo.getString("limit_pay"));
		}
		String trade_type = orderInfo.getString("trade_type");
		if(StringUtils.isBlank(trade_type)){
			trade_type="JSAPI";
		}
		map.put("trade_type", trade_type);
		if ("JSAPI".equals(trade_type)) {
			map.put("openid", orderInfo.getString("openid"));
		}
		return map;
	}

}
