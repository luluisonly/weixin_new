package com.zaofans.pay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

public class PayUtils {
	private static final Logger LOGGER = Logger.getLogger(PayUtils.class);
	/**
	 * 拼接生成StringA
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(Map<String, String> params, boolean encode)
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
	/**
	 * 生成签名
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String paySign(Map<String, String> params,String key) throws UnsupportedEncodingException {
		String stringA;
		try {
			stringA = createSign(params, false);
		} catch (UnsupportedEncodingException ex) {
			LOGGER.error(ex);
			return "";
		}
		String stringSignTemp = stringA + (key==null?"":("&key=" + key)) ;// 需要从配置中取出key值
		String signValue = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
		return signValue;
	}
	/**
	 * 判断请求是不是来在于微信
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isWeiXin(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isNotBlank(userAgent)) {
			Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
			Matcher m = p.matcher(userAgent);
			String version = null;
			if (m.find()) {
				version = m.group(1);
			}
			return (null != version && NumberUtils.toInt(version) >= 5);
		}
		return false;
	}
}
