package com.bokesoft.thirdparty.weixin.open.handle;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

/**
 * 
 * 获取微信用户信息
 *
 */
public class GetWeixinUserInfoHandle extends AbstractWeixinSOAServieHandle  {
	
	private static final Logger LOGGER = Logger.getLogger(GetWeixinUserInfoHandle.class);
	public static String KEY_NAME = null;
	
	public SOAResponseMessage handle(WeixinContext context, SOARequestMessage requestMessage)
			throws Exception {
		String uname = requestMessage.getUname();
		WeixinPublicNumber weixinPublicNumber = context.getWeixinPublicNumber(uname);
		if (weixinPublicNumber == null) {
			return new SOAResponseMessage(1000,"unknow uname:"+uname);
		}
		String access_token = weixinPublicNumber.genAccess_token(context);
		String openid = requestMessage.getStringParam();
		LOGGER.info("GetWeixinUserInfo openid:"+openid);
		try {
			WeixinApiInvoker apiInvoker = context.getWeixinApiInvoker();
			String result = apiInvoker.getUserInfo(access_token, openid);
			LOGGER.info("GetWeixinUserInfo result:"+result);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if(jsonObject.getInteger("subscribe")==0){
				//说明此用户未关注
				return new SOAResponseMessage(500, null, jsonObject);
			}else if(jsonObject.get("errcode") == null){
				String nickname = jsonObject.getString("nickname");
				nickname = filterEmoji(nickname);
				jsonObject.put("nickname", nickname);
				return new SOAResponseMessage(0, null, jsonObject);
			}else{
				return new SOAResponseMessage(1000, jsonObject.getString("errmsg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	private static boolean isNotEmojiCharacter(char codePoint)  
	{  
	    return (codePoint == 0x0) ||  
	        (codePoint == 0x9) ||  
	        (codePoint == 0xA) ||  
	        (codePoint == 0xD) ||  
	        ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||  
	        ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||  
	        ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));  
	}  
	  
	/** 
	 * 过滤emoji 或者 其他非文字类型的字符 
	 * @param source 
	 * @return 
	 */  
	public static String filterEmoji(String source)  
	{  
	    int len = source.length();  
	    StringBuilder buf = new StringBuilder(len);  
	    for (int i = 0; i < len; i++)  
	    {  
	        char codePoint = source.charAt(i);  
	        if (isNotEmojiCharacter(codePoint))  
	        {  
	            buf.append(codePoint);  
	        }  
	    }  
	    return buf.toString();  
	}  

}
