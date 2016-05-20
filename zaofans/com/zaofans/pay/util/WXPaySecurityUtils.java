package com.zaofans.pay.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.myerp.common.io.CloseUtil;
import com.zhouxw.utils.security.EncryptUtil;


public class WXPaySecurityUtils {
	
	private static final Logger LOGGER = Logger.getLogger(WXPaySecurityUtils.class);
	
	private static String read(ServletInputStream inputStream) throws Throwable {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		}finally {
			CloseUtil.close(reader);
		}
		return buffer.toString();
	}
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String content) throws Throwable {
		Map<String, String> result = new HashMap<String, String>();
		Document doc = DocumentHelper.parseText(content);
		Element rootElement = doc.getRootElement();
		List<Element> elements = rootElement.elements();
		for(Element e : elements){
			result.put(e.getName(), e.getText());
		}
		return result;
	}
	/**
	 * @param req
	 * @param key
	 * @return datas
	 * @throws Throwable
	 */
	public static Map<String, String> parseXml(HttpServletRequest req) throws Throwable{
		String content = read(req.getInputStream());
		Map<String, String> params = parseXml(content);
		return params;
	}
	/**
	 * @param req
	 * @param key
	 * @return datas
	 * @throws Throwable
	 */
	public static Map<String, String> vaildateWXPaySign(HttpServletRequest req,String key) throws Throwable{
		Map<String, String> params = parseXml(req);
		String sign = params.get("sign");
		if(StringUtil.isEmpty(sign))
			throw new Exception( "[bad request, sign lost]");
		params.remove("sign");
		String sign2 = paySign(params, key);
		if (!sign2.equals(sign)) // 签名比对
			throw new Exception( "[bad request, sign check failed]");
		return params;
	}
	/**
	 * 生成签名
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String paySign(Map<String, String> params,String key) throws UnsupportedEncodingException {
		String stringA;
		try {
			stringA = createSign(params, false);
//			System.out.println("#1.生成字符串：\n"+stringA);
		} catch (UnsupportedEncodingException ex) {
			LOGGER.error(ex);
			return "";
		}
		String stringSignTemp = stringA + (key==null?"":("&key=" + key)) ;// 需要从配置中取出key值
//		System.out.println("#2.连接商户key：\n"+stringSignTemp);
		String signValue = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
//		System.out.println("#3.md5编码并转成大写："+signValue);
		return signValue;
	}
	/**
	 * 拼接生成StringA
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
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
	
	public static JSONObject converMapToJson(Map<String, String> params){
		JSONObject result = new JSONObject();
		Set<String> set = params.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String key = it.next();
			result.put(key, params.get(key));
		}
		return result;
	}
	
	public static void main(String[] args) throws Throwable {
		String content = "<xml><appid><![CDATA[wx2421b1c4370ec43b]]></appid><attach><![CDATA[支付测试]]></attach><bank_type><![CDATA[CFT]]></bank_type><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str><openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid><out_trade_no><![CDATA[1409811653]]></out_trade_no><sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign><sub_mch_id><![CDATA[10000100]]></sub_mch_id><time_end><![CDATA[20140903131540]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code></xml>";
		Map<String, String> result = parseXml(content);
		System.out.println(result.toString());
		JSONObject jsonObject = converMapToJson(result);
		System.out.println(jsonObject);
	}
	
	public static String sign4zaofans(String datas) throws Throwable {
		String sign = EncryptUtil.toHexString(EncryptUtil.security(datas, null), "", "");
		return sign;
	}
	
}
