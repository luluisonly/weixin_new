package com.bokesoft.thirdparty.weixin.service;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinChainingMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinImageMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinLocationEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinLocationMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyImageMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyMusicMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyNewsMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyVideoMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyVoiceMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyWasteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinVideoMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinVoiceMessage;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinMessageLoggerImpl implements WeixinMessageLogger, Initable {

	private static final Logger LOGGER = Logger.getLogger(WeixinMessageLoggerImpl.class);

	public void logConversation(WeixinSession session, WeixinMessage request, WeixinMessage response) throws Exception {
		request.logMessage(session, this);
		response.logMessage(session, this);
	}
	
	public void logRequestDefaultMessage(WeixinSession session, WeixinMessage request) {
		LOGGER.info("request message:" + request.toString());
	}
	
	public void logResponseDefaultMessage(WeixinSession session, WeixinMessage response) {
		LOGGER.info("reply   message:" + response.toString());
	}

	public void initialize(WeixinContext context) throws Exception {

	}

	public void shoutdown(WeixinContext context) throws Exception {

	}

	// ******************************      log request message   ******************************//
	
	public void logRequestTextMessage(WeixinSession session,WeixinTextMessage message) throws Exception {
		this.logRequestDefaultMessage(session, message);
	}

	public void logRequestImageMessage(WeixinSession session,WeixinImageMessage message) throws Exception {
		this.logRequestDefaultMessage(session, message);
	}

	public void logRequestVoiceMessage(WeixinSession session,WeixinVoiceMessage message) throws Exception {
		this.logRequestDefaultMessage(session, message);
	}

	public void logRequestVideoMessage(WeixinSession session,WeixinVideoMessage message) throws Exception {
		this.logRequestDefaultMessage(session, message);
	}

	public void logRequestLocationMessage(WeixinSession session,WeixinLocationMessage message) throws Exception {
		this.logRequestDefaultMessage(session, message);
	}

	public void logRequestLocationEventMessage(WeixinSession session,WeixinLocationEventMessage message) throws Exception {
		this.logRequestDefaultMessage(session, message);
	}
	
	public void logRequestMenuEventMessage(WeixinSession session,WeixinEventMessage message) throws Exception {
		this.logRequestDefaultMessage(session, message);
	}
	
	public void logRequestChainingMessage(WeixinSession session, WeixinChainingMessage message) throws Exception {
		this.logRequestDefaultMessage(session, message);
	}
	
	// ******************************      log reply message   ******************************//
	

	public void logResponseTextMessage(WeixinSession session,WeixinReplyTextMessage message) throws Exception {
		this.logResponseDefaultMessage(session, message);
	}

	public void logResponseImageMessage(WeixinSession session,WeixinReplyImageMessage message) throws Exception {
		this.logResponseDefaultMessage(session, message);
	}

	public void logResponseVoiceMessage(WeixinSession session,WeixinReplyVoiceMessage message) throws Exception {
		this.logResponseDefaultMessage(session, message);
	}

	public void logResponseVideoMessage(WeixinSession session,WeixinReplyVideoMessage message) throws Exception {
		this.logResponseDefaultMessage(session, message);
	}

	public void logResponseMusicMessage(WeixinSession session,WeixinReplyMusicMessage message) throws Exception {
		this.logResponseDefaultMessage(session, message);
	}

	public void logResponseNewsMessage(WeixinSession session,WeixinReplyNewsMessage message) throws Exception {
		this.logResponseDefaultMessage(session, message);
	}
	
	public void logResponseWasteMessage(WeixinSession session,WeixinReplyWasteMessage message) throws Exception {
		this.logResponseDefaultMessage(session, message);
	}

}
