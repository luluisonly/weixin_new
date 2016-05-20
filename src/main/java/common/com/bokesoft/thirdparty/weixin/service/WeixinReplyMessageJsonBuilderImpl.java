package com.bokesoft.thirdparty.weixin.service;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.bean.message.Article;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteImageMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyImageMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyMusicMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyNewsMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyVideoMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyVoiceMessage;
/**
 * 
 * JSON文本转成消息对象
 *
 */
public class WeixinReplyMessageJsonBuilderImpl implements WeixinMessageBuilder {

	private interface $WeixinMessageHandle {
		public abstract WeixinMessage buildMessage( JSONObject jsonObject) throws Exception;
	}

	private void setBasicMessage(WeixinMessage message, JSONObject jsonObject) throws Exception  {
		message.setToUserName(jsonObject.getString("toUserName"));
		message.setFromUserName(jsonObject.getString("fromUserName"));
		message.setCreateTime(jsonObject.getLongValue("createTime"));
	}

	private String getShortString(JSONObject json,String key,int length){
		String value = json.getString(key);
		if (value != null && value.length() > length) {
			return value.substring(0,length);
		}
		return value;
	}
	
	private String [] getStringArray(JSONObject json,String key){
		JSONArray array = json.getJSONArray(key);
		if (array == null) {
			return null;
		}
		return array.toArray(new String[0]);
	}
	
	@SuppressWarnings("serial")
	private Map<String, $WeixinMessageHandle> messageHandles = new HashMap<String, $WeixinMessageHandle>() {
		
		{
			put(WeixinMessage.TEXT, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage(JSONObject jsonObject) throws Exception {
					WeixinReplyTextMessage message = new WeixinReplyTextMessage(getShortString(jsonObject, "content", 1024));
					message.setServiceKey(getShortString(jsonObject, "serviceKey",32));
					setBasicMessage(message, jsonObject);
					return message;
				}
			});
			put(WeixinRemoteMessage.REMOTE_TEXT, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage(JSONObject jsonObject) throws Exception {
					String serviceKey = getShortString(jsonObject, "serviceKey",32);
					WeixinRemoteTextMessage message = new WeixinRemoteTextMessage(serviceKey);
					message.setServiceKey(serviceKey);
					message.setContent(getShortString(jsonObject, "content",1024));
					message.setUrl(getShortString(jsonObject,"url",1024));
					message.setRemoteType(getShortString(jsonObject,"remoteType",32));
					message.setParam(getStringArray(jsonObject, "param"));
					message.setUname(getShortString(jsonObject, "uname",32));
					setBasicMessage(message, jsonObject);
					return message;
				}
			});
			put(WeixinMessage.IMAGE, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage(JSONObject jsonObject) throws Exception {
					WeixinReplyImageMessage message = new WeixinReplyImageMessage();
					setBasicMessage(message, jsonObject);
					message.setMediaId(getShortString(jsonObject,"mediaId",128));
					return message;
				}
			});
			put(WeixinRemoteMessage.REMOTE_IMAGE, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage(JSONObject jsonObject) throws Exception {
					WeixinRemoteImageMessage message = new WeixinRemoteImageMessage();
					message.setUrl(getShortString(jsonObject,"url",1024));
					message.setRemoteType(getShortString(jsonObject,"remoteType",32));
					message.setUname(getShortString(jsonObject, "uname",32));
					setBasicMessage(message, jsonObject);
					return message;
				}
			});
			put(WeixinRemoteMessage.REMOTE_LOCATION, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage(JSONObject jsonObject) throws Exception {
					WeixinRemoteLocationMessage message = new WeixinRemoteLocationMessage();
					message.setUrl(getShortString(jsonObject,"url",1024));
					message.setRemoteType(getShortString(jsonObject,"remoteType",32));
					message.setUname(getShortString(jsonObject, "uname",32));
					message.setLabel(getShortString(jsonObject, "label",512));
					message.setLocation_X(getShortString(jsonObject, "location_X",128));
					message.setLocation_Y(getShortString(jsonObject, "location_Y",128));
					message.setScale(getShortString(jsonObject, "scale",32));
					setBasicMessage(message, jsonObject);
					return message;
				}
			});
			put(WeixinRemoteMessage.REMOTE_LOCATIONEVENT, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage(JSONObject jsonObject) throws Exception {
					WeixinRemoteLocationEventMessage message = new WeixinRemoteLocationEventMessage();
					message.setUrl(getShortString(jsonObject,"url",1024));
					message.setRemoteType(getShortString(jsonObject,"remoteType",32));
					message.setUname(getShortString(jsonObject, "uname",32));
//					message.setLabel(getString(jsonObject, "label"));
					message.setLatitude(getShortString(jsonObject, "latitude",128));
					message.setLongitude(getShortString(jsonObject, "longitude",128));
					message.setPrecision(getShortString(jsonObject, "precision",256));
//					message.setLocation_X(getString(jsonObject, "location_X"));
//					message.setLocation_Y(getString(jsonObject, "location_Y"));
//					message.setScale(getString(jsonObject, "scale"));
					setBasicMessage(message, jsonObject);
					return message;
				}
			});
			put(WeixinMessage.VOICE, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage( JSONObject jsonObject) throws Exception {
					WeixinReplyVoiceMessage message = new WeixinReplyVoiceMessage();
					setBasicMessage(message, jsonObject);
					message.setMediaId(getShortString(jsonObject,"mediaId",64));
					return message;
				}
			});
			put(WeixinMessage.VIDEO, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage( JSONObject jsonObject) throws Exception {
					WeixinReplyVideoMessage message = new WeixinReplyVideoMessage();
					setBasicMessage(message, jsonObject);
					message.setMediaId(getShortString(jsonObject,"mediaId",64));
					message.setThumbMediaId(getShortString(jsonObject,"thumbMediaId",64));
					return message;
				}
			});
			put(WeixinMessage.MUSIC, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage(JSONObject jsonObject) throws Exception {
					WeixinReplyMusicMessage message = new WeixinReplyMusicMessage();
					setBasicMessage(message, jsonObject);
					message.setTitle(getShortString(jsonObject,"title",128));
					message.setDescription(getShortString(jsonObject,"description",512));
					message.setMusicUrl(getShortString(jsonObject,"musicURL",1024));
					message.setHqMusicUrl(getShortString(jsonObject,"hqMusicUrl",1024));
					message.setThumbMediaId(getShortString(jsonObject,"thumbMediaId",64));
					return message;
				}
			});
			put(WeixinMessage.NEWS, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage(JSONObject jsonObject) throws Exception {
					WeixinReplyNewsMessage message = new WeixinReplyNewsMessage();
					message.setServiceKey(getShortString(jsonObject, "serviceKey",32));
					setBasicMessage(message, jsonObject);
					JSONArray array = jsonObject.getJSONArray("articles");
					message.setArticleCount(array.size());
					for(int i = 0;i <array.size();i++){
						JSONObject articleJsonObject = array.getJSONObject(i);
						Article article = new Article();
						article.setTitle(getShortString(articleJsonObject,"title",64));
						article.setDescription(getShortString(articleJsonObject,"description",512));
						article.setPicUrl(getShortString(articleJsonObject,"picUrl",1024));
						article.setUrl(getShortString(articleJsonObject,"url",1024));
						message.addArticle(article);
					}
					return message;
				}
			});
		}
	};

	public WeixinMessage buildMessage(String content) throws Exception {
		return this.buildMessage(JSONObject.parseObject(content));
	}
	
	public WeixinMessage buildMessage(JSONObject jsonObject) throws Exception {
		String msgType = jsonObject.getString("msgType");
		$WeixinMessageHandle messageHandle = messageHandles.get(msgType);
		if (messageHandle == null) {
			throw new Exception("unknown MsgType:" + msgType);
		}
		return messageHandle.buildMessage(jsonObject);
	}

}
