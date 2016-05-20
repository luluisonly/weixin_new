package com.bokesoft.thirdparty.weixin.publicnumber;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumberType;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationEventMessage;

/**
 * 更新微信公众号
 *
 */
public class WeixinPublicNumberUpdater {

	private static final Logger LOGGER = Logger.getLogger(WeixinPublicNumberUpdater.class);
	public SOAResponseMessage updateAllProperties(WeixinContext context, JSONObject jsonObject)
			throws Exception {
		String uname = jsonObject.getString(SOARequestMessage.UNAME);
		LOGGER.info("start update weixinpublicnumber uname:" + uname);
		String action = jsonObject.getString("action");
		if ("delete".equals(action)) {
			context.removeWeixinPublicNumber(uname);
		} else {
			updateWeixinPublicNumber(context, jsonObject);
		}
		LOGGER.info("end update weixinpublicnumber");
		return SOAResponseMessage.SUCCESS;
	}
	public WeixinPublicNumber updateWeixinPublicNumber(WeixinContext context, JSONObject jsonObject) throws Exception{
		//update weixinPublicNumber basic properties
		LOGGER.info("update weixinpublicnumber "+jsonObject);
		String uname = jsonObject.getString(SOARequestMessage.UNAME);
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumberFromLocal(uname);
		if (publicNumber == null) {
			publicNumber = new WeixinPublicNumber();
			publicNumber.setUname(uname);
			context.putWeixinPublicNumber2Local(uname, publicNumber);
		}
		publicNumber.setApp_id(jsonObject.getString(WeixinPublicNumber.APP_ID));
		publicNumber.setMch_id(jsonObject.getString(WeixinPublicNumber.MCH_ID));
		publicNumber.setKey(jsonObject.getString(WeixinPublicNumber.KEY));
		publicNumber.setApi_ticket(jsonObject.getString(WeixinPublicNumber.API_TICKET));
		publicNumber.setPassword(jsonObject.getString(WeixinPublicNumber.PASSWORD));
		publicNumber.setStatus(jsonObject.getIntValue(WeixinPublicNumber.STATUS));
		publicNumber.setService_time(jsonObject.getLongValue(WeixinPublicNumber.SERVICE_TIME));
		publicNumber.setWeixinMenu(jsonObject.getString(WeixinPublicNumber.WEIXINMENU));
		publicNumber.setRemote_message_encrypt(jsonObject.getString(WeixinPublicNumber.REMOTE_MESSAGE_ENCRYPT));
		publicNumber.setEncrypt_remote_message(jsonObject.getBooleanValue(WeixinPublicNumber.ENCRYPT_REMOTE_MESSAGE));
		publicNumber.setMessage_token(jsonObject.getString(WeixinPublicNumber.MESSAGE_TOKEN));
		String public_type = jsonObject.getString(WeixinPublicNumber.PUBLIC_TYPE);
		
		WeixinPublicNumberType publicNumberType = null;
		if ("SERVIC".equals(public_type)) {
			publicNumberType = WeixinPublicNumberType.SERVIC;
		} else if ("SUBSCRIPTION".equals(public_type)) {
			publicNumberType = WeixinPublicNumberType.SUBSCRIPTION;
		} else {
			throw new Exception("未知公众账号类型:" + public_type);
		}
		publicNumber.setPublic_Type(publicNumberType);
		publicNumber.setApp_secret((String)jsonObject.get(WeixinPublicNumber.APP_SECRET));
		
		// update weixinPublicNumber message properties
		JSONObject temp = jsonObject.getJSONObject(WeixinPublicNumber.DEFAULTREPLY_MESSAGE);
		if (temp != null) {
			publicNumber.setDefaultReplyMessage(context.buildWeixinMessage(temp));
		}
		publicNumber.getKeywordsReplyMessage().clear();
		temp = jsonObject.getJSONObject(WeixinPublicNumber.KEYWORDSREPLY_MESSAGE);
		if (temp!= null) {
			Set<String> s = temp.keySet();
			Iterator<String> it = s.iterator();
			while(it.hasNext()){
				String serviceKey = it.next();
				WeixinMessage message = context.buildWeixinMessage(temp.getJSONObject(serviceKey));
				LOGGER.info("Key@"+WeixinPublicNumber.KEYWORDSREPLY_MESSAGE+" value :"+serviceKey );
				publicNumber.putKeywordsReplyMessage(serviceKey, message);
			}
		}
		temp = jsonObject.getJSONObject(WeixinPublicNumber.EDIT_KEYWORDS_REPLYMESSAGE);
		if (temp!= null) {
			Set<String> s = temp.keySet();
			Iterator<String> it = s.iterator();
			while(it.hasNext()){
				String serviceKey = it.next();
				WeixinMessage message = context.buildWeixinMessage(temp.getJSONObject(serviceKey));
				LOGGER.info("Key@"+WeixinPublicNumber.EDIT_KEYWORDS_REPLYMESSAGE+" value :"+serviceKey );
				publicNumber.putEditKeywordsReplyMessage(serviceKey, message);
			}
		}
		temp =  jsonObject.getJSONObject(WeixinPublicNumber.SUBSCRIBEREPLY_MESSAGE);
		if (temp != null) {
			publicNumber.setSubscribeReplyMessage(context.buildWeixinMessage(temp));
		}
//		temp =  jsonObject.getJSONObject("weixinRemoteImageMessage");
//		if (temp != null) {
//			publicNumber.setWeixinRemoteImageMessage((WeixinRemoteImageMessage)context.buildWeixinMessage(temp));
//		}
		temp =  jsonObject.getJSONObject(WeixinPublicNumber.WEIXIN_REMOTE_LOCATIONEVENT_MESSAGE);
		if (temp != null) {
			publicNumber.setWeixinRemoteLocationEventMessage((WeixinRemoteLocationEventMessage)context.buildWeixinMessage(temp));
		}
		return publicNumber;
	}
//	private SOAResponseMessage updateWeixinPublicNumber(ApplicationContext applicationContext, String uname
//			, JSONObject jsonObject)throws Exception {
//		String action = jsonObject.getString("action");
//		if("update".equals(action)){
//			WeixinPublicNumber publicNumber = applicationContext.getWeixinPublicNumberFromLocal(uname);
//			if (publicNumber == null) {
//				publicNumber = new WeixinPublicNumber();
//				publicNumber.setUname(uname);
//			}
//			publicNumber.setAppid((String)jsonObject.get(WeixinPublicNumber.APP_ID));
//			publicNumber.setGrant_type((String)jsonObject.get(WeixinPublicNumber.GRANT_TYPE));
//			String public_type = (String)jsonObject.get(WeixinPublicNumber.PUBLICTYPE);
//			WeixinPublicNumberType publicNumberType = null;
//			if ("SERVIC".equals(public_type)) {
//				publicNumberType = WeixinPublicNumberType.SERVIC;
//			}else if("SUBSCRIPTION".equals(public_type)){
//				publicNumberType = WeixinPublicNumberType.SUBSCRIPTION;
//			}else{
//				return new SOAResponseMessage(1000,"未知公众账号类型:"+public_type);
//			}
//			publicNumber.setPublicType(publicNumberType);
//			publicNumber.setSecret((String)jsonObject.get(WeixinPublicNumber.SECRET));
//			applicationContext.putWeixinPublicNumber(uname,publicNumber);
//		}else if("delete".equals(action)){
//			applicationContext.removeWeixinPublicNumber(uname);
//		}else{
//			return new SOAResponseMessage(1000,"unknow action"+action);
//		}
//		return SOAResponseMessage.SUCCESS;
//		
//	}
	
}
