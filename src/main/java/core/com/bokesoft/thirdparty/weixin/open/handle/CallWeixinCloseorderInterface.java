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

public class CallWeixinCloseorderInterface extends AbstractWeixinSOAServieHandle{

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
		String out_trade_no = message.getStringParam();
		Map<String,String> map = new HashMap<String,String>();
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("out_trade_no", out_trade_no);
		map.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
		String sign = PayUtils.paySign(map, key);
		map.put("sign",sign);
		String xml = XmlUtils.maptoXml(map);
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String result = invoker.callWeixinCloseorderInterface(xml);
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

}
