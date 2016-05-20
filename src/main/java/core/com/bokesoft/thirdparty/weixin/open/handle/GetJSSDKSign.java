package com.bokesoft.thirdparty.weixin.open.handle;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

public class GetJSSDKSign extends AbstractWeixinSOAServieHandle{

	private static final Logger LOGGER = Logger.getLogger(GetJSSDKSign.class);
	
	@Override
	public SOAResponseMessage handle(WeixinContext context,SOARequestMessage message) throws Exception {
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if(publicNumber == null){
			return new SOAResponseMessage(1000, "unknow uname:"+uname);
		}
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String param = message.getStringParam();
//		JSONObject json  = JSONObject.parseObject(param);
//		String jsapiTicket = invoker.getJsapiTicket(publicNumber.genAccess_token(context));
		String jsapiTicket = publicNumber.genApi_ticket(context);
		if(jsapiTicket==null){
			return new SOAResponseMessage(1000, "get jsapiTicket error");
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("url", URLDecoder.decode(param, "utf-8"));
		map.put("jsapi_ticket", jsapiTicket);
		map.put("timestamp", System.currentTimeMillis()+"");
		map.put("noncestr",RandomStringUtils.randomAlphanumeric(16));
		String sign = jsapiSign(map);
		JSONObject resultJson = new JSONObject();
		resultJson.put("noncestr", map.get("noncestr"));
		resultJson.put("timestamp",map.get("timestamp"));
		resultJson.put("signature", sign);
		resultJson.put("appId",publicNumber.getApp_id());
		return new SOAResponseMessage(0, resultJson.toString());
	}
	
	/**
	 * 生成签名
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String jsapiSign(Map<String, String> params) throws UnsupportedEncodingException {
		String stringA;
		try {
			stringA = createSign(params, false);
		} catch (UnsupportedEncodingException ex) {
			LOGGER.error(ex);
			return "";
		}
		String signValue = DigestUtils.sha1Hex(stringA);
		return signValue;
	}

	private static String createSign(Map<String, String> params, boolean encode)
			throws UnsupportedEncodingException {
		Set<String> keysSet = params.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		StringBuffer temp = new StringBuffer();
		boolean first = true;
		for (Object key : keys) {
			if (first) {
				first = false;
			} else {
				temp.append("&");
			}
			temp.append(key).append("=");
			Object value = params.get(key);
			String valueString = "";
			if (null != value) {
				valueString = value.toString();
			}
			if (encode) {
				temp.append(URLEncoder.encode(valueString, "UTF-8"));
			} else {
				temp.append(valueString);
			}
		}
		return temp.toString();
	}

}
