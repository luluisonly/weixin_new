package com.bokesoft.thirdparty.weixin.open.handle;

import java.net.URLDecoder;
import java.util.HashMap;
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
import com.zaofans.weixin.common.PayUtils;

/**
 * 针对使用JSAPI实现支付
 *
 */
public class CallWeixinOrderInterfaceFormJSAPIv2 extends AbstractWeixinSOAServieHandle {
	private static final Logger LOGGER = Logger.getLogger(CallWeixinOrderInterfaceFormJSAPIv2.class);
	private static final String appid = SharedBundle.getProperties("server.weixin.pay.appid");
	private static final String mch_id = SharedBundle.getProperties("server.weixin.pay.mchid");
	private static final String paternerKey = SharedBundle.getProperties("server.weixin.pay.key");
	private static final String paySignKey = SharedBundle.getProperties("server.weixin.pay.signkey");
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
		String param = message.getStringParam();
		param =  URLDecoder.decode(param,"UTF-8");
		JSONObject orderInfo = JSONObject.parseObject(param);
		LOGGER.info("PayMessageFromWeixin:"+orderInfo.toString());
		Map<String, String> pack = parseOrderInfo(orderInfo);
		if(pack == null){
			return new SOAResponseMessage(1000, "core文件中server.weixin.pay.notifyurl2必须配置，请检查");
		}
		String string1 = PayUtils.createSign(pack,true);
		String paySign = PayUtils.paySign(pack, paternerKey);
		String packStr = string1+"&sign="+paySign;
		
		Map<String, String> map = new HashMap<String,String>();
		map.put("appid", appid);
		String timeStamp = System.currentTimeMillis()+"";
		map.put("timestamp", timeStamp);
		String noncestr = RandomStringUtils.randomAlphanumeric(16);
		map.put("noncestr", noncestr);// 随机生成16位验证字符
		map.put("package", packStr);
		map.put("appkey", paySignKey);
		String paySign2 = PayUtils.paySignSHA1(map, null);
		
		JSONObject json = new JSONObject();
		json.put("appId",appid);
		json.put("timeStamp",timeStamp);
		json.put("nonceStr",noncestr);
		json.put("package",packStr);
		json.put("signType","SHA1");
		json.put("paySign",paySign2);
		LOGGER.info("json : "+json);
		
		return new SOAResponseMessage(0, json.toString());
	}

	private Map<String, String> parseOrderInfo(JSONObject orderInfo) throws Exception {
		LOGGER.info("orderInfo : "+orderInfo);
		Map<String, String> pack = new HashMap<String, String>();
		pack.put("body", orderInfo.getString("body"));
		pack.put("bank_type", "WX");
		if (orderInfo.getString("attach") != null) {
			pack.put("attach", orderInfo.getString("attach"));
		}
		pack.put("partner",mch_id);
		pack.put("out_trade_no", orderInfo.getString("out_trade_no"));
		if(orderInfo.getString("total_fee").indexOf(".")!=-1){
			throw new RuntimeException("total_fee contains a decimal point,it's wrong");
		}
		Integer total_fee = orderInfo.getInteger("total_fee");
		if(total_fee <= 1){
			total_fee = 1;
		}
		pack.put("total_fee", total_fee+"");
		pack.put("fee_type", "1");
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
		pack.put("notify_url", notify_url);
		String spbill_create_ip = orderInfo.getString("spbill_create_ip");
		if(StringUtils.isBlank(spbill_create_ip)){
			pack.put("spbill_create_ip", "127.0.0.1");
		}else{
			pack.put("spbill_create_ip", spbill_create_ip);
		}
		pack.put("input_charset", "UTF-8");
		return pack;
	}

}
