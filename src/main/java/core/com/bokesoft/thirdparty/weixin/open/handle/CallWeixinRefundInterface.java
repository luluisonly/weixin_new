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

public class CallWeixinRefundInterface extends AbstractWeixinSOAServieHandle {
	private static final Logger LOGGER = Logger.getLogger(CallWeixinRefundInterface.class);
	private static final String appid = SharedBundle.getProperties("server.weixin.pay.appid");
	private static final String mch_id = SharedBundle.getProperties("server.weixin.pay.mchid");
	private static final String key = SharedBundle.getProperties("server.weixin.pay.key");
	private static final String op_user_id = SharedBundle.getProperties("server.weixin.pay.opuserid");
	private static final String cer = SharedBundle.getProperties("server.weixin.refund.cerlocation");
	

	@Override
	public SOAResponseMessage handle(WeixinContext context,
			SOARequestMessage message) throws Exception {
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if (publicNumber == null) {
			return new SOAResponseMessage(1000, "unknow uname:" + uname);
		}
		String param = message.getStringParam();
		param =  URLDecoder.decode(param,"UTF-8");
		JSONObject orderInfo = JSONObject.parseObject(param);
		Map<String, String> map = parseOrderInfo(orderInfo);
		map.put("appid", appid);// 放入appid
		map.put("mch_id", mch_id);// 放入商户号
		map.put("op_user_id", op_user_id);//放入操作员id
		map.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));// 随机生成16位验证字符
		String signValue = PayUtils.paySign(map, key);
		if (StringUtils.isBlank(signValue)) {
			return new SOAResponseMessage(1000, "something wrong");
		}
		map.put("sign", signValue);
		String xml = XmlUtils.maptoXml(map);
		LOGGER.info("refund info:"+xml);
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String result = invoker.callWeixinRefundInterface(mch_id, cer, xml);
		Map<String, String> resultMap = XmlUtils.parseContent(result);
		if (resultMap.get("return_code").equalsIgnoreCase("fail")) {
			LOGGER.info("refund result:fail - "+resultMap.get("return_msg"));
			return new SOAResponseMessage(1000, "return_msg:"
					+ resultMap.get("return_msg"));
		} else if (resultMap.get("result_code").equalsIgnoreCase("fail")) {
			String temp = "err_code:"
					+ resultMap.get("err_code") + ";err_code_des:"
					+ resultMap.get("err_code_des");
			LOGGER.info("refund result:fail - "+temp);
			return new SOAResponseMessage(1000, temp);
		} else {
			Map<String,String> params = new HashMap<String,String>();
			params.put("return_code","SUCCESS");
			params.put("result_code","SUCCESS");
			params.put("out_trade_no", resultMap.get("out_trade_no"));
			params.put("out_refund_no", resultMap.get("out_refund_no"));
			params.put("refund_id", resultMap.get("refund_id"));
			params.put("refund_fee", resultMap.get("refund_fee"));
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
		Map<String, String> map = new HashMap<String, String>();
		map.put("out_trade_no", orderInfo.getString("out_trade_no"));
		map.put("out_refund_no", orderInfo.getString("out_refund_no"));
		map.put("refund_fee", orderInfo.getString("refund_fee"));
		map.put("total_fee", orderInfo.getString("total_fee"));
		return map;
	}

}
