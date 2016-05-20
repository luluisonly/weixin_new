package com.bokesoft.thirdparty.weixin.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.message.Article;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinChainingMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinImageMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinLocationEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinLocationMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteChainingMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteImageMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteVideoMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteVoiceMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyCustomerServiceMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyNewsMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyWasteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinVideoMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinVoiceMessage;
import com.bokesoft.thirdparty.weixin.common.Encryptor;
import com.bokesoft.thirdparty.weixin.common.TConstant;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalService;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalServiceImpl;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteProxy;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteService;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

/**
 * 微信消息处理实现类
 *
 */
public class WeixinMessageHandleImpl implements WeixinMessageHandle{
	
	private static final Logger LOGGER = Logger.getLogger(WeixinMessageHandleImpl.class);
	
	public WeixinMessage handleClickEventMessage(WeixinSession session, WeixinEventMessage eventMessage)
			throws Exception {
		String key = eventMessage.getEventKey();
		WeixinPublicNumber publicNumber = session.getWeixinPublicNumber();
		WeixinMessage message = session.getWeixinContext().getKeywordsReplyMessage(publicNumber, key);
		message.setBasicWeixinMessage(eventMessage);
		if (message.getMsgType() == WeixinRemoteMessage.REMOTE_TEXT) {
			((WeixinRemoteMessage)message).setUname(publicNumber.getUname());
		}
		return message.doService(session, this);
	}

	private void SendCustomerServiceMsg(WeixinSession session, String openid)
			throws Exception, IOException {
		WeixinContext context = session.getWeixinContext();
		WeixinPublicNumber publicNumber = session.getWeixinPublicNumber();
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		JSONObject message = new JSONObject();
		message.put("touser", openid);
		message.put("msgtype", "text");
		JSONObject content = new JSONObject();
		content.put("content", "您已进入客服会话，请等候客服接入，也可直接留言。");
		message.put("text", content);
		invoker.sendMessage(publicNumber.genAccess_token(context), message.toString());
	}
	
	public WeixinMessage handleWeixinTextMessage(WeixinSession session, WeixinTextMessage textMessage)
			throws Exception {
		String key = textMessage.getContent();
		if(key.equals("联系客服")){
			SendCustomerServiceMsg(session, textMessage.getFromUserName());
			WeixinMessage replyMsg = new WeixinReplyCustomerServiceMessage();
			return replyMsg;
		}
		WeixinPublicNumber publicNumber = session.getWeixinPublicNumber();
		WeixinMessage message = session.getWeixinContext().getKeywordsReplyMessage(publicNumber, key);
		message.setBasicWeixinMessage(textMessage);
		if (message.getMsgType() == WeixinRemoteMessage.REMOTE_TEXT) {
			((WeixinRemoteMessage)message).setUname(publicNumber.getUname());
		}
		return message.doService(session, this);
	}

	public WeixinMessage handleWeixinChainingMessage(WeixinSession session,
			WeixinChainingMessage chainingMessage) throws Exception {
		return new WeixinReplyTextMessage("未找到相应的服务支持");
	}
	
	public WeixinMessage handleWeixinEventMessage(WeixinSession session,
			WeixinEventMessage eventMessage) throws Exception {
		String eventKey = eventMessage.getEvent();
		if ("CLICK".equals(eventKey)) {
			eventMessage.setServiceKey(eventMessage.getEventKey());
			return this.handleClickEventMessage(session, eventMessage);
		} else if ("LOCATION".equals(eventKey)) {
			return this.handleWeixinLocationEventMessage(session, (WeixinLocationEventMessage)eventMessage);
		} else if ("subscribe".equals(eventKey)) {
			return this.handleWeixinSubscribeEventMessage(session, eventMessage);
		} else if ("unsubscribe".equals(eventKey)) {
			return this.handleWeixinUNSubscribeEventMessage(session, eventMessage);
		} else if ("VIEW".equals(eventKey)) {
			return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
		} else if("scancode_waitmsg".equals(eventKey)){
			return this.handleScancodeWaitmsgEventMessage(session, eventMessage);
		} else if("TEMPLATESENDJOBFINISH".equalsIgnoreCase(eventKey)){
			//对于模版消息发送结束后的返回结果要做处理
			return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
//			return this.handleTemplatesendjobfinish(session,eventMessage);
		}
		//用户已关注时的事件推送
		else if("scan".equalsIgnoreCase(eventKey)){
			return this.handleWeixinScanEventMessage(session, eventMessage);
//			return new WeixinReplyTextMessage(eventMessage.getScanResult());
		}else if("scancode_push".equals(eventKey)){
			//对于扫码推事件，我们并不需要进行处理，因为没有意义
			return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
		}
		else{
			throw new Exception("new key:" + eventKey);
		}
	}

	public WeixinMessage handleWeixinImageMessage(WeixinSession session, WeixinImageMessage imageMessage)
			throws Exception {
//		WeixinRemoteImageMessage tempMessage = session.getWeixinPublicNumber().getWeixinRemoteImageMessage();
//		if (tempMessage == null) {
//			return new WeixinReplyTextMessage("未找到相应的服务支持");
//		}else{
//			WeixinRemoteImageMessage remoteImageMessage = imageMessage.copyWeixinRemoteMessage();
//			remoteImageMessage.setBasicWeixinRemoteMessage(session, tempMessage);
//			remoteImageMessage.setBasicWeixinMessage(imageMessage);
//			return handleWeixinRemoteImageMessage(session, remoteImageMessage);
//		}
		return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
	}

	public WeixinMessage handleWeixinLocationEventMessage(WeixinSession session,WeixinLocationEventMessage locationEventMessage) throws Exception {
		WeixinRemoteLocationEventMessage tempMessage = session.getWeixinPublicNumber().getWeixinRemoteLocationEventMessage();
		if (tempMessage != null && !StringUtil.isBlankOrNull(tempMessage.getUrl())) {
			WeixinRemoteLocationEventMessage remoteLocationMessage = locationEventMessage.copyWeixinRemoteMessage();
			remoteLocationMessage.setBasicWeixinRemoteMessage(session, tempMessage);
			remoteLocationMessage.setBasicWeixinMessage(locationEventMessage);
			return this.handleWeixinRemoteLocationEventMessage(session, remoteLocationMessage);
		}
		return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
	}

	public WeixinMessage handleWeixinLocationMessage(WeixinSession session,WeixinLocationMessage locationMessage) throws Exception {
//		WeixinRemoteLocationMessage tempMessage = session.getWeixinPublicNumber().getWeixinRemoteLocationMessage();
//		if (tempMessage == null) {
//			return new WeixinReplyTextMessage("未找到相应的服务支持");
//		}else{
//			WeixinRemoteLocationMessage remoteLocationMessage = locationMessage.copyWeixinRemoteMessage();
//			remoteLocationMessage.setBasicWeixinRemoteMessage(session, tempMessage);
//			remoteLocationMessage.setBasicWeixinMessage(locationMessage);
//			return handleWeixinRemoteLocationMessage(session, remoteLocationMessage);
//		}
		return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
	}

	public WeixinMessage handleWeixinRemoteChainingMessage(WeixinSession session,WeixinRemoteChainingMessage remoteChainingMessage) throws Exception {
//		return new WeixinReplyTextMessage("未找到相应的服务支持");
		return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
	}
	
	public WeixinMessage handleWeixinRemoteImageMessage(WeixinSession session,
			WeixinRemoteImageMessage remoteImageMessage) throws Exception {
		return handleWeixinRemoteMessage(session, remoteImageMessage);
	}
	
	public WeixinMessage handleWeixinRemoteLocationEventMessage(WeixinSession session,
			WeixinRemoteLocationEventMessage remoteLocationEventMessage) throws Exception {
		LOGGER.info("handleWeixinRemoteLocationEventMessage:"+JSONObject.toJSONString(remoteLocationEventMessage));
		WeixinRemoteProxy remoteProxy = session.getWeixinContext().getWeixinRemoteProxy(remoteLocationEventMessage.getRemoteType());
		remoteProxy.request(session, remoteLocationEventMessage);
		return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
	}

	public WeixinMessage handleWeixinRemoteLocationMessage(WeixinSession session,
			WeixinRemoteLocationMessage remoteLocationMessage) throws Exception {
		return handleWeixinRemoteMessage(session, remoteLocationMessage);
	}

	private WeixinMessage handleWeixinRemoteMessage(WeixinSession session,
			WeixinRemoteMessage remoteMessage) throws Exception{
		WeixinContext context = session.getWeixinContext();
		WeixinRemoteService remoteService = context.getWeixinRemoteService();
		SOAResponseMessage responseMessage = remoteService.doService(session, remoteMessage);
		if(responseMessage.getCode() == 300){
			WeixinMessageFlowLocalService flowLocalService = session.getWeixinMessageFlowLocalService();
			if (flowLocalService == null) {
				flowLocalService = new WeixinMessageFlowLocalServiceImpl();
				flowLocalService.setWeixinRemoteMessage(remoteMessage);
				session.setWeixinMessageFlowLocalService(flowLocalService);
			}
		}else if(responseMessage.getCode() == 301){
			session.setWeixinMessageFlowLocalService(null);
		}else if(responseMessage.getCode() == 302){
			session.setWeixinMessageFlowLocalService(null);
			return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
		}
		
		String message = responseMessage.getMessage();
		if (message == null || message.length() == 0) {
			Object data = responseMessage.getData();
			if (data == null) {
				return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
			}else if(data instanceof JSONObject){
//				JSONObject jsonObject = ((JSONObject) data);
//				if (jsonObject.isNullObject()) {
//					return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
//				}
//				return applicationContext.buildWeixinMessage(jsonObject);
				return context.buildWeixinMessage((JSONObject) data);
			}else{
				return (WeixinMessage)data;
			}
//			JSONObject data = (JSONObject)responseMessage.getData();
//			if (data == null) {
//				return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
//			}else{
//				return applicationContext.buildWeixinMessage(data);
//			}
			
		}
		return new WeixinReplyTextMessage(message);
//		if (message instanceof String) {
//			return new WeixinReplyTextMessage((String)message);
//		}else if(message instanceof JSONObject){
//			return  applicationContext.buildWeixinMessage((JSONObject)message);
//		}
//		LOGGER.error("remote error,your response is not null and is not String or an json object:"+remoteMessage.toString());
//		LOGGER.error("remote error,your response is not null and is not String or an json object:"+responseMessage.toString());
//		return new WeixinReplyTextMessage("网络异常");
	}

	public WeixinMessage handleWeixinRemoteTextMessage(WeixinSession session,
			WeixinRemoteTextMessage remoteTextMessage) throws Exception {
		return handleWeixinRemoteMessage(session, remoteTextMessage);
	}

	public WeixinMessage handleWeixinRemoteVideoMessage(WeixinSession session,
			WeixinRemoteVideoMessage remoteVideoMessage) throws Exception {
		return handleWeixinRemoteMessage(session, remoteVideoMessage);
	}

	public WeixinMessage handleWeixinRemoteVoiceMessage(WeixinSession session,
			WeixinRemoteVoiceMessage remoteVoiceMessage) throws Exception {
		return handleWeixinRemoteMessage(session, remoteVoiceMessage);
	}
	
	private String encodeURLAppend(String uname ,String openid) throws UnsupportedEncodingException{
		String param = uname+" "+openid+" "+System.currentTimeMillis();
		String encrptUrl = Encryptor.encryptData2number(param, TConstant.YIGO_URL_APPEND_ENCRYPT);
		return URLEncoder.encode(encrptUrl,"UTF-8");
	}

	public WeixinMessage handleWeixinReplyNewsMessage(WeixinSession session,
			WeixinReplyNewsMessage replyNewsMessage) throws Exception {
		String append = null;
		List<Article> aritcles = replyNewsMessage.getArticles();
		String uname = session.getPublicName();
		String openid = session.getOpenid();
		for (int i = 0; i < aritcles.size(); i++) {
			String fix = "${userinfo}";
			Article article = aritcles.get(i);
			String url = article.getUrl();
			int index = url.indexOf(fix);
			if (index != -1) {
				if (append == null) {
					append = encodeURLAppend(uname,openid);
				}
//				url = new StringBuffer(url.substring(0, index)).append(append)
//						.append(url.substring(index+fix.length(),url.length())).toString();
				url = url.replace(fix, append);
			}
			url = url.replace("${uname}", uname);
			url = url.replace("${openid}", openid);
			article.setUrl(url);
		}
		return replyNewsMessage;
	}

	public WeixinMessage handleWeixinReplyTextMessage(WeixinSession session,WeixinReplyTextMessage replyTextMessage) throws Exception {
		String fix = "${userinfo}";
		String content = replyTextMessage.getContent();
		String uname = session.getPublicName();
		String openid = session.getOpenid();
		int index = content.indexOf(fix);
		if (index != -1) {
			String append = encodeURLAppend(session.getPublicName(), session.getOpenid());
//			content = new StringBuffer(content.substring(0, index)).append(append)
//					.append(content.substring(index+fix.length(),content.length())).toString();
			content = content.replace(fix, append);
		}
		content = content.replace("${uname}", uname);
		content = content.replace("${openid}", openid);
		replyTextMessage.setContent(content);
		return replyTextMessage;
	}

	public WeixinMessage handleWeixinSubscribeEventMessage(WeixinSession session, WeixinEventMessage eventMessage)
			throws Exception {
		LOGGER.error("Handling subscribe message,openid = "+ eventMessage.getFromUserName());
		WeixinRemoteTextMessageCellHandle handle = session.getWeixinContext().getWeixinRemoteTextMessageCellHandle("Subscribe");
		if(handle == null){
			LOGGER.error("Please config your subscribe message");
			return session.getWeixinContext().getReplyTextMessage_unsubscribe();
		}
		WeixinRemoteTextMessage weixinRemoteTextMessage = new WeixinRemoteTextMessage("Subscribe");
		weixinRemoteTextMessage.setBasicWeixinMessage(eventMessage);
		weixinRemoteTextMessage.setEventMessage(eventMessage);
		SOAResponseMessage responseMessage = handle.handleWeixinRemoteTextMessage(session, weixinRemoteTextMessage);
		if(0 == responseMessage.getCode()){
			String roleLevel = responseMessage.getMessage();
			return session.getWeixinPublicNumber().getEditKeywordsReplyMessage("Subscribe_"+roleLevel);
		}
		return session.getWeixinPublicNumber().getSubscribeReplyMessage();
	}
	
	public WeixinMessage handleWeixinScanEventMessage(WeixinSession session, WeixinEventMessage eventMessage)
			throws Exception {
		LOGGER.error("Handling scan message,openid = "+ eventMessage.getFromUserName());
		WeixinRemoteTextMessageCellHandle handle = session.getWeixinContext().getWeixinRemoteTextMessageCellHandle("Scan");
		if(handle == null){
			LOGGER.error("Please config your scan message");
			return session.getWeixinContext().getReplyTextMessage_unsubscribe();
		}
		WeixinRemoteTextMessage weixinRemoteTextMessage = new WeixinRemoteTextMessage("Scan");
		weixinRemoteTextMessage.setBasicWeixinMessage(eventMessage);
		weixinRemoteTextMessage.setEventMessage(eventMessage);
		SOAResponseMessage soaResponseMessage = handle.handleWeixinRemoteTextMessage(session, weixinRemoteTextMessage);
		if(0 == soaResponseMessage.getCode() ){
			return session.getWeixinPublicNumber().getKeywordsReplyMessage("Subscribe_"+soaResponseMessage.getMessage());
//			return session.getWeixinContext().buildWeixinMessage(soaResponseMessage.getMessage());
		}
		return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
	}

	public WeixinMessage handleWeixinUNSubscribeEventMessage(WeixinSession session, WeixinEventMessage eventMessage)
			throws Exception {
		WeixinRemoteTextMessageCellHandle handle = session.getWeixinContext().getWeixinRemoteTextMessageCellHandle("UNSubscribe");
		if(handle == null){
			LOGGER.error("Please config your unsubscribe message");
			return session.getWeixinContext().getReplyTextMessage_unsubscribe();
		}
		WeixinRemoteTextMessage weixinRemoteTextMessage = new WeixinRemoteTextMessage("UNSubscribe");
		weixinRemoteTextMessage.setBasicWeixinMessage(eventMessage);
		handle.handleWeixinRemoteTextMessage(session, weixinRemoteTextMessage);
		return session.getWeixinContext().getReplyTextMessage_unsubscribe();
	}
	
	public WeixinMessage handleScancodeWaitmsgEventMessage(WeixinSession session, WeixinEventMessage eventMessage)
			throws Exception {
		WeixinRemoteTextMessageCellHandle handle = session.getWeixinContext().getWeixinRemoteTextMessageCellHandle("ScancodeWaitmsg");
		if(handle == null){
			LOGGER.error("Please config your ScancodeWaitmsg message");
			return new WeixinReplyTextMessage(eventMessage.getScanResult());
		}
		WeixinRemoteTextMessage weixinRemoteTextMessage = new WeixinRemoteTextMessage("ScancodeWaitmsg");
		weixinRemoteTextMessage.setBasicWeixinMessage(eventMessage);
		weixinRemoteTextMessage.setEventMessage(eventMessage);
		SOAResponseMessage responseMessage = handle.handleWeixinRemoteTextMessage(session, weixinRemoteTextMessage);
		if(responseMessage.getMessage().length()==0){
			return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
		}
		return new WeixinReplyTextMessage(responseMessage.getMessage());
	}
	

	@SuppressWarnings("unused")
	private WeixinMessage handleTemplatesendjobfinish(WeixinSession session,
			WeixinEventMessage eventMessage) throws Exception {
		WeixinRemoteTextMessageCellHandle handle = session.getWeixinContext().getWeixinRemoteTextMessageCellHandle("Templatesendjobfinish");
		if(handle == null){
			LOGGER.error("Please config your Templatesendjobfinish message");
			return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
		}
		WeixinRemoteTextMessage weixinRemoteTextMessage = new WeixinRemoteTextMessage("Templatesendjobfinish");
		weixinRemoteTextMessage.setBasicWeixinMessage(eventMessage);
		weixinRemoteTextMessage.setEventMessage(eventMessage);
		handle.handleWeixinRemoteTextMessage(session, weixinRemoteTextMessage);
		return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
	}
	
	public WeixinMessage handleWeixinVideoMessage(WeixinSession session,WeixinVideoMessage videoMessage) throws Exception {
//		return new WeixinReplyTextMessage("未找到相应的服务支持");
		return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
	}

	public WeixinMessage handleWeixinVoiceMessage(WeixinSession session,WeixinVoiceMessage voiceMessage) throws Exception {
//		return new WeixinReplyTextMessage("未找到相应的服务支持");
		return WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
	}
}
