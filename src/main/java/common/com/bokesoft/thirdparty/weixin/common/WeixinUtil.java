package com.bokesoft.thirdparty.weixin.common;

import com.bokesoft.thirdparty.weixin.bean.WeixinAuthorInfo;

public class WeixinUtil {

	public static WeixinAuthorInfo getWeixinAuthorInfo(String userinfo,String key){
		if (userinfo == null || userinfo.length() == 0) {
			return null;
		}
		String [] contents = Encryptor.decryptDataWithNumber(userinfo, key).split(" ");
		WeixinAuthorInfo authorInfo = new WeixinAuthorInfo();
		
		authorInfo.setUname(contents[0]);
		authorInfo.setOpenid(contents[1]);
		authorInfo.setAuthorTime(Long.parseLong(contents[2]));
		return authorInfo;
	}
	
	public static WeixinAuthorInfo getWeixinAuthorInfo(String data){
		return getWeixinAuthorInfo(data, TConstant.YIGO_URL_APPEND_ENCRYPT);
	}
	
	public static String [] getDecryptDataArray(String userinfo,String key){
		if (userinfo == null || userinfo.length() == 0) {
			return null;
		}
		return  Encryptor.decryptDataWithNumber(userinfo, key).split(" ");
	}
	
	public static String [] getDecryptDataArray(String data){
		return getDecryptDataArray(data, TConstant.YIGO_URL_APPEND_ENCRYPT);
	}
	
}
