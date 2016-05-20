package com.bokesoft.thirdparty.weixin.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.bokesoft.myerp.exception.BKException;

/**
 * 加密解密类
 * 
 */
public class Encryptor {
	
	private static final Logger LOGGER = Logger.getLogger(Encryptor.class);

	private static final String DES_ALGORITHM = "DES";

	// 定义加密算法，有DES、DESede(即3DES)、Blowfish
	private final static String DESEDE_ALGORITHM = "DESede";

	private final static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f' };

	
	/**
	 * 根据字符串生成密钥字节数组
	 * 
	 * @param keyStr
	 *            密钥字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	public static String decryptData(String input, String key) {
		try {
			SecretKey deskey = new SecretKeySpec(BASE64.decode(key), DES_ALGORITHM);
			Cipher c1 = Cipher.getInstance(DES_ALGORITHM);
			c1.init(2, deskey);
			byte[] clearByte = c1.doFinal(BASE64.decode(input));
			return new String(clearByte);
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		return null;
	}

	/**
	 * 解密函数
	 * 
	 * @param src
	 *            密文的字节数组
	 * @return
	 */
	public static byte[] decryptMode(byte[] src, byte[] key) {
		try {
			SecretKey deskey = new SecretKeySpec(key, DESEDE_ALGORITHM);
			Cipher c1 = Cipher.getInstance(DESEDE_ALGORITHM);
			c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
			return c1.doFinal(src);
		} catch (java.lang.Exception ex) {
			LOGGER.error(ex);
		}
		return null;
	}

	public static String encodeBYSHA1(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw BKException.dealEx(e);
		}
	}

	public static String encryptData(String input, String key) {
		try {
			SecretKey deskey = new SecretKeySpec(BASE64.decode(key), DES_ALGORITHM);
			Cipher c1 = Cipher.getInstance(DES_ALGORITHM);

			c1.init(1, deskey);
			byte[] cipherByte = c1.doFinal(input.getBytes());
			return BASE64.encode(cipherByte);
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		return null;
	}

	/**
	 * 加密方法
	 * 
	 * @param src
	 *            源数据的字节数组
	 * @return
	 */
	public static byte[] encryptMode(byte[] src, byte[] key) {
		try {
			SecretKey deskey = new SecretKeySpec(key, DESEDE_ALGORITHM); // 生成密钥
			Cipher c1 = Cipher.getInstance(DESEDE_ALGORITHM); // 实例化负责加密/解密的Cipher工具类
			c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
			return c1.doFinal(src);
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	public static String getSecretKey() {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance(DES_ALGORITHM);
			SecretKey deskey = keygen.generateKey();
			return BASE64.encode(deskey.getEncoded());
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		return null;
	}
	
	private static int char2int(char ch){
		return (int)ch;
	}
	
	public static String encryptData2number(String input, String key) {
		String miwen = encryptData(input, key);
		char [] chars = miwen.toCharArray();
		StringBuilder builder = new StringBuilder();
		for(char c : chars){
			builder.append('A');
			builder.append(char2int(c));
		}
		return builder.toString();
	}
	
	private static char int2char(int number){
		return (char)number;
	}
	
	public static String decryptDataWithNumber(String input, String key) {
		String [] array= input.split("A");
		StringBuilder builder = new StringBuilder();
		for(int i = 1 ; i < array.length ; i ++){
			char c = int2char(Integer.parseInt(array[i]));
			builder.append(c);
		}
		return decryptData(builder.toString(), key);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "你好fjasdfjkpew=23-4=-9=0-3245s';l'./z.xv-4o3=-.()#*_)&$)!@#^)$#_";
		String key = "/rwHNx/gBGI=";
		//
		String miwen = encryptData2number(str, key);
		
		System.out.println(miwen);
		
		//miwen = "Ldtleacr9QZAnRv4dMoN%2BE2gt%2FGBAwQlIWwqSHvy8YXt%2BwWJiRJ%2FohU68brnVT%2Bpfe%2BJQqNBkBU%3D";
		//miwen = URLDecoder.decode(miwen, "UTF-8");
		String yuanwen = decryptDataWithNumber(miwen, key);
		System.out.println(yuanwen);

	}
	
}