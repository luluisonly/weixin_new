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

public class CallWeixinDownloadbillInterface extends AbstractWeixinSOAServieHandle{

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
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
		String sign = PayUtils.paySign(map, key);
		map.put("sign",sign);
		String xml = XmlUtils.maptoXml(map);
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String result = invoker.callWeixinOrderqueryInterface(xml);
		if(result.startsWith("<")){
			//如果是返回的XML可能就是错误信息
			Map<String,String> resultMap = XmlUtils.parseContent(result);
			if (resultMap.get("return_code").equalsIgnoreCase("fail")) {
				return new SOAResponseMessage(1000, "return_msg:"
						+ resultMap.get("return_msg"));
			}
		}
		return new SOAResponseMessage(0,result);
	}

	private Map<String, String> parsequeryInfo(String params) {
		Map<String,String> map = new HashMap<String,String>();
		JSONObject downloadbillInfo = JSONObject.parseObject(params);
//		map.put("bill_date", new SimpleDateFormat("yyyyMMdd").format(new Date()));
		if(downloadbillInfo.getString("bill_date")!=null){
			map.put("bill_date",downloadbillInfo.getString("bill_date"));
		}
		if(downloadbillInfo.getString("bill_type")!=null){
			map.put("bill_type",downloadbillInfo.getString("bill_type"));
		}
		return map;
	}
}
