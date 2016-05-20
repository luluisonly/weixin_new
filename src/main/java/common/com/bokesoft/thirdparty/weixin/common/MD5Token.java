package com.bokesoft.thirdparty.weixin.common;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

public class MD5Token {
	
	private static final Logger logger = Logger.getLogger(MD5Token.class);
	
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static MD5Token instance = new MD5Token();

	private MD5Token() {
	}

	public synchronized static MD5Token getInstance() {
		return instance;
	}

	public String getShortToken(String value,String encoding) {
		return encoder(value,encoding).substring(8, 24);
	}

	public String getLongToken(String value,String encoding) {
		return encoder(value,encoding).toString();
	}

	private StringBuffer encoder(String value,String encoding) {
		if (value == null) {
			value = "";
		}
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(value.getBytes(encoding));
		} catch (Exception e) {
			logger.error(e);
		}
		return toHex(md5.digest());
	}

	private StringBuffer toHex(byte[] bytes) {
		StringBuffer str = new StringBuffer(32);
		int length = bytes.length;
		for (int i = 0; i < length; i++) {
			str.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
			str.append(hexDigits[bytes[i] & 0x0f]);
		}
		return str;
	}
}
