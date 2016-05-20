package com.bokesoft.thirdparty.weixin.open.handle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;
import com.zaofans.weixin.common.PayUtils;
import com.zaofans.weixin.common.XmlUtils;

public class CallWeixinRefundqueryInterface extends AbstractWeixinSOAServieHandle{

	@Override
	public SOAResponseMessage handle(WeixinContext context,
			SOARequestMessage message) throws Exception {
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if(publicNumber == null){
			return new SOAResponseMessage(1000, "unknow uname:"+uname);
		}
		String appid = publicNumber.getApp_id();
		String mch_id = publicNumber.getMch_id();
		String key = publicNumber.getKey();
		String params = message.getStringParam();
		Map<String,String> map = parsequeryInfo(params);
		if(map.size()==0){
			return new SOAResponseMessage(1000, "未传入单号");
		}
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
		String sign = PayUtils.paySign(map, key);
		map.put("sign",sign);
		String xml = XmlUtils.maptoXml(map);
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String result = invoker.callWeixinRefundqueryInterface(xml);
		Map<String,String> resultMap = XmlUtils.parseContent(result);
		if (resultMap.get("return_code").equalsIgnoreCase("fail")) {
			return new SOAResponseMessage(1000, "return_msg:"
					+ resultMap.get("return_msg"));
		} else { 
			//TODO 需要确定返回什么样的数据
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

	private Map<String, String> parsequeryInfo(String params) {
		Map<String,String> map = new HashMap<String,String>();
		JSONObject queryInfo = JSONObject.parseObject(params);
		if(queryInfo.getString("refund_id")!=null){
			map.put("refund_id", queryInfo.getString("refund_id"));
		}
		if(queryInfo.getString("out_refund_no")!=null){
			map.put("out_refund_no", queryInfo.getString("out_refund_no"));
		}
		if(queryInfo.getString("transaction_id")!=null){
			map.put("transaction_id", queryInfo.getString("transaction_id"));
		}
		if(queryInfo.getString("out_trade_no")!=null){
			map.put("out_trade_no", queryInfo.getString("out_trade_no"));
		}
		return map;
	}

}
