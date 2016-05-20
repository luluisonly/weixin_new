package com.bokesoft.thirdparty.model.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyNewsMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;

public class ConfigUtil {
	
	public static String messageToContent(WeixinMessage message){
		if (message == null) {
			return null;
		}
		if ("text".equals(message.getMsgType())) {
			return ((WeixinReplyTextMessage)message).getContent();
		}else if ("news".equals(message.getMsgType())) {
			return JSONArray.toJSONString(((WeixinReplyNewsMessage)message).getArticles());
		}else if ("remote_text".equals(message.getMsgType())) {
			return JSONObject.toJSONString(((WeixinRemoteTextMessage)message));
		}
		return null;
	}
	
	public static String getWeixinPublicnumberStatus(int status){
		if (status == 1) {
			return "";
		}else if(status == 0){
			return "[<span style='color:red;'>未授权使用</span>]";
		}else{
			return "[<span style='color:red;'>已禁用</span>]";
		}
	}
	
	public static String getWeixinSystemVersion(int code){
		return code == 0 ? "标准版" : "企业版" ;
	}

	
//	public int msgType2IntType(String msgType){
//		if ("text".equals(msgType)) {
//			return 0;
//		}else if("news".equals(msgType)) {
//			return 1;
//		}else if("text".equals(msgType)) {
//			return 0;
//		}
//		
//	}
}
