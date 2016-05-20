package com.bokesoft.thirdparty.weixin.open.handle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;
import com.zaofans.weixin.common.PayUtils;
import com.zaofans.weixin.common.XmlUtils;

public class CallWeixinOrderqueryInterface extends AbstractWeixinSOAServieHandle{
	private static final String appid = SharedBundle.getProperties("server.weixin.pay.appid");
	private static final String mch_id = SharedBundle.getProperties("server.weixin.pay.mchid");
	private static final String key = SharedBundle.getProperties("server.weixin.pay.key");
	@Override
	public SOAResponseMessage handle(WeixinContext context,
			SOARequestMessage message) throws Exception {
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if(publicNumber == null){
			return new SOAResponseMessage(1000, "unknow uname:"+uname);
		}
//		String appid = publicNumber.getApp_id();
//		String mch_id = publicNumber.getMch_id();
//		String key = publicNumber.getKey();
		String params = message.getStringParam();
		Map<String,String> map = parsequeryInfo(params);
		if(map==null){
			return new SOAResponseMessage(1000, "no transaction_id or out_trade_no");
		}
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
		String sign = PayUtils.paySign(map, key);
		map.put("sign",sign);
		String xml = XmlUtils.maptoXml(map);
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String result = invoker.callWeixinOrderqueryInterface(xml);
		Map<String,String> resultMap = XmlUtils.parseContent(result);
		if("FAIL".equalsIgnoreCase(resultMap.get("return_code"))) {
			return new SOAResponseMessage(1000, "return_msg:"+ resultMap.get("return_msg"));
		} else if ("FAIL".equalsIgnoreCase(resultMap.get("result_code"))) {
			return new SOAResponseMessage(1000, "{err_code:"	+ resultMap.get("err_code") + ",err_code_des:"
					+ resultMap.get("err_code_des")+"}");
		} else { 
			JSONObject json = new JSONObject();
			Iterator<String> it = resultMap.keySet().iterator();  
		       while (it.hasNext()) {  
		           String keyStr = it.next().toString();
		           String valueStr = resultMap.get(keyStr);
		           json.put(keyStr, valueStr);
		       }
			return new SOAResponseMessage(0,json.toString());
		}
	}

	private Map<String,String> parsequeryInfo(String params) {
		Map<String,String> map = new HashMap<String,String>();
		JSONObject queryInfo = JSONObject.parseObject(params);
		if(queryInfo.getString("transaction_id")!=null){
			map.put("transaction_id", queryInfo.getString("transaction_id"));
		}
		if(queryInfo.getString("out_trade_no")!=null){
			map.put("out_trade_no", queryInfo.getString("out_trade_no"));
		}
		return map;
	}

}
