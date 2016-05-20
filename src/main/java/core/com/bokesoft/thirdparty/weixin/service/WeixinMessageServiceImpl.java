package com.bokesoft.thirdparty.weixin.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.WeixinContextFactory;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinImageMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyWasteMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinTextMessage;
import com.bokesoft.thirdparty.weixin.common.Encryptor;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalService;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;
import com.zaofans.utils.MsgQueueSender;
/**
 * 接收微信用户消息入口
 *
 */
public class WeixinMessageServiceImpl implements WeixinMessageService {

	private static final Logger LOGGER = Logger.getLogger(WeixinMessageServiceImpl.class);

	private static final String sendJms = SharedBundle.getProperties("server.soa.weixin.sendJms");

	private static final String srcsys = SharedBundle.getProperties("server.soa.weixin.srcsys");
	
	public WeixinMessage doFilter( WeixinSession session, WeixinMessage message)
			throws Exception {
		WeixinContext context = session.getWeixinContext();
		List<WeixinMessageServiceFilter> filters = context.getWeixinMessageServiceFilters();
		if (filters != null) {
			for (WeixinMessageServiceFilter filter : filters) {
				message = filter.doFilter(session, message);
				if (message.isReply()) {
					break;
				}
			}
		}
		return message;
	}
	
	private WeixinSession getWeixinSession(WeixinMessage message,String uname) throws Exception {
		WeixinSession session = WeixinContextFactory.getWeixinSession(message.getFromUserName(),uname);
//		WeixinMessageGroup messageGroup = new WeixinMessageGroup();
//		messageGroup.setReceiveMessage(message);
//		session.getWeixinMessageProcedure().addWeixinMessage(messageGroup);
		return session;
	}
	
	public String handleMessage(WeixinContext context,HttpServletRequest request,HttpServletResponse response) {
		if (!validate(request,response,context)) {
			LOGGER.info("Request forbidden:" + request.getRemoteAddr());
			return "REQUEST FORBIDDEN";
		}
		WeixinMessage oldMessage = null,newMessage = null;
		WeixinReplyMessageBuilder weixinReplyMessageBuilder = context.getWeixinReplyMessageBuilder();
		WeixinSession session = null;
		try {
			//String requestBody = request.getParameter("requestBody");
			String requestBody = readStreamParameter(request.getInputStream());
			oldMessage = context.buildWeixinMessage(requestBody);
			if(oldMessage.getMsgType().equalsIgnoreCase("voice")||
					oldMessage.getMsgType().equalsIgnoreCase("voice")){
				//TODO 记录下来
			}else if(oldMessage.getMsgType().equalsIgnoreCase("event")){
				
			}
			session = getWeixinSession(oldMessage,request.getParameter(WeixinPublicNumber.UNAME));
			newMessage = handleMessage(context, session, oldMessage);
			context.getWeixinSessionProvider().updateWeixinSession(context, session);
		} catch (Throwable e) {
			newMessage = WeixinReplyWasteMessage.WEIXIN_REPLY_WASTE_MESSAGE;
			LOGGER.error(e);
		}
		try {
			if (session != null) {
				context.logConversation(session,oldMessage, newMessage);
			}
		} catch (Throwable e) {
			LOGGER.error(e);
		}
		return weixinReplyMessageBuilder.buildMessage(newMessage);
	}
	
	public WeixinMessage handleMessage(WeixinContext context,WeixinSession session, WeixinMessage oldMessage)throws Exception {
		if(sendJms!=null&&"true".equalsIgnoreCase(sendJms)){
			sendWXUserOperator(oldMessage);
		}
		WeixinMessageHandle messageHandle = context.getWeixinMessageHandle();
		WeixinMessage newMessage = doFilter(session, oldMessage);
		WeixinMessageFlowLocalService flowLocalService = session.getWeixinMessageFlowLocalService();
		newMessage = newMessage.doService(session, messageHandle, flowLocalService);
		newMessage.setBasicWeixinMessage(oldMessage);
		return newMessage;
	}
	/**
	 * 发送用户操作到jms
	 * @param oldMessage
	 * @throws JMSException
	 */
	private void sendWXUserOperator(WeixinMessage message){
		try {
			JSONObject operatorJson = new JSONObject();
			operatorJson.put("open_id", message.getFromUserName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			operatorJson.put("touch_time", sdf.format(new Date(message.getCreateTime()*1000)));
			String type =  message.getMsgType();
			operatorJson.put("msgtype", type);
			if("text".equalsIgnoreCase(type)){
				WeixinTextMessage textMessage = (WeixinTextMessage)message;
				operatorJson.put("info", textMessage.getContent());
			}else if("image".equalsIgnoreCase(type)){
				WeixinImageMessage imageMessage = (WeixinImageMessage)message;
				operatorJson.put("info", imageMessage.getPicUrl());
			}else if("event".equalsIgnoreCase(type)){
				WeixinEventMessage eventMesage = (WeixinEventMessage)message;
				String event = eventMesage.getEvent();
				operatorJson.put("msgtype", event);
				operatorJson.put("info", eventMesage.getEventKey());
			}else{
				return;
			}
			operatorJson.put("src_sys", srcsys);
			MsgQueueSender.getInstance().sendTextMsg(operatorJson.toString());
		} catch (Exception e) {
			LOGGER.info("sendWXUserOperator : "+message+" : "+e);
		}
	}
	
	public String readStreamParameter(ServletInputStream inputStream) throws IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		}finally {
			if (null != reader) {
				reader.close();
			}
		}
		return buffer.toString();
	}

	public boolean validate(HttpServletRequest request,HttpServletResponse response,WeixinContext context){
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		if (StringUtil.isBlankOrNull(signature)
				|| StringUtil.isBlankOrNull(timestamp)
				|| StringUtil.isBlankOrNull(nonce)) {
			return false;
		}
		String uname = request.getParameter(WeixinPublicNumber.UNAME);
		WeixinPublicNumber publicNumber;
		try {
			publicNumber = context.getWeixinPublicNumber(uname);
		} catch (Exception e) {
			LOGGER.error(e);
			e.printStackTrace();
			return false;
		}
		if (publicNumber == null) {
			return false;
		}
		List<String> list = new ArrayList<String>();
		list.add(nonce);
		list.add(timestamp);
		list.add(publicNumber.getMessage_token());
		Collections.sort(list);
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(str);
		}
		return signature.equalsIgnoreCase(Encryptor.encodeBYSHA1(stringBuffer.toString()));
	}

}
