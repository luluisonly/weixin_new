package com.bokesoft.thirdparty.weixin.service;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.bokesoft.thirdparty.weixin.bean.message.WeixinChainingMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinImageMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinLocationEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinLocationMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinVideoMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinVoiceMessage;

/**
 * 
 * XML文本转成消息对象
 *
 */
public class WeixinMessageXMLBuilderImpl implements WeixinMessageBuilder {

	private interface $WeixinMessageHandle {
		public abstract WeixinMessage buildMessage( Element element) throws Exception;
	}

	private String getElementText(Element element, String name, boolean check) throws DocumentException {
		element = element.element(name);
		if (element == null) {
			if (check) {
				throw new NullPointerException("no such element:" + name);
			}else{
				//FIXME or return null
				return "no such element:" + name;
			}
		}
		String value = element.getText();
		if (check && "".equals(value)) {
			throw new DocumentException("element empty:" + element.getName());
		}
		return value;
	}

	private long getElementLong(Element element, String name, boolean check) throws DocumentException {
		return Long.parseLong(getElementText(element, name, check));
	}

	@SuppressWarnings("unused")
	private int getElementInt(Element element, String name, boolean check) throws DocumentException {
		return Integer.parseInt(getElementText(element, name, check));
	}

	private void setBasicMessage(WeixinMessage message, Element element) throws DocumentException {
		message.setToUserName(getElementText(element, "ToUserName", true));
		message.setFromUserName(getElementText(element, "FromUserName", true));
		message.setCreateTime(getElementLong(element, "CreateTime", true));
	}
	
	private void setScancodeInfo(WeixinEventMessage message,Element element)throws DocumentException{
		Element scanCodeInfo = element.element("ScanCodeInfo");
		message.setScanType(getElementText(scanCodeInfo, "ScanType", true));
		message.setScanResult(getElementText(scanCodeInfo, "ScanResult", true));
	}

	@SuppressWarnings("serial")
	private Map<String, $WeixinMessageHandle> messageHandles = new HashMap<String, $WeixinMessageHandle>() {
		
		{
			put(WeixinMessage.TEXT, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage( Element element) throws Exception {
					WeixinTextMessage message = new WeixinTextMessage(getElementText(element, "Content", false));
					setBasicMessage(message, element);
					message.setMsgId(getElementLong(element, "MsgId", true));
					return message;
				}
			});
			put(WeixinMessage.IMAGE, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage( Element element) throws Exception {
					WeixinImageMessage message = new WeixinImageMessage();
					setBasicMessage(message, element);
					message.setPicUrl(getElementText(element, "PicUrl", false));
					message.setMsgId(getElementLong(element, "MsgId", true));
					message.setMediaId(getElementText(element, "MediaId", true));
					return message;
				}
			});
			put(WeixinMessage.LOCATION, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage( Element element) throws Exception {
					WeixinLocationMessage message = new WeixinLocationMessage();
					setBasicMessage(message, element);
					message.setLabel(getElementText(element, "Label", false));
					message.setLocation_X(getElementText(element, "Location_X", true));
					message.setLocation_Y(getElementText(element, "Location_Y", true));
					message.setMsgId(getElementText(element, "MsgId", true));
					message.setScale(getElementText(element, "Scale", true));
					return message;
				}
			});
			put(WeixinMessage.LINK, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage( Element element) throws Exception {
					WeixinChainingMessage message = new WeixinChainingMessage();
					setBasicMessage(message, element);
					message.setDescription(getElementText(element, "Description", false));
					message.setMsgId(getElementText(element, "MsgId", true));
					message.setTitle(getElementText(element, "Title", true));
					message.setUrl(getElementText(element, "Url", true));
					return message;
				}
			});
			put(WeixinMessage.EVENT, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage( Element element) throws Exception {
					String event = getElementText(element, "Event", true);
					if ("LOCATION".equals(event)) {
						WeixinLocationEventMessage eventMessage = new WeixinLocationEventMessage();
						setBasicMessage(eventMessage, element);
						eventMessage.setEvent(event);
						eventMessage.setLatitude(getElementText(element, "Latitude", true));
						eventMessage.setLongitude(getElementText(element, "Longitude", true));
						eventMessage.setPrecision(getElementText(element, "Precision", true));
						return eventMessage;
					}else{
						WeixinEventMessage eventMessage = new WeixinEventMessage();
						setBasicMessage(eventMessage, element);
						eventMessage.setEvent(event);
						eventMessage.setEventKey(getElementText(element, "EventKey", false));
						if(event.startsWith("scancode")){
							setScancodeInfo(eventMessage, element);
						}
						if(event.equalsIgnoreCase("Templatesendjobfinish")){
							//TODO
						}
						return eventMessage;
					}
				}
			});
			put(WeixinMessage.VOICE, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage( Element element) throws Exception {
					WeixinVoiceMessage message = new WeixinVoiceMessage();
					setBasicMessage(message, element);
					message.setMediaId(getElementText(element, "MediaId", true));
					//log
					System.out.println("-----------------------"+message.getMediaId());
					message.setFormat(getElementText(element, "Format", false));
					message.setMsgId(getElementText(element, "MsgId", true));
					return message;
				}
			});
			put(WeixinMessage.VIDEO, new $WeixinMessageHandle() {
				public WeixinMessage buildMessage( Element element) throws Exception {
					WeixinVideoMessage message = new WeixinVideoMessage();
					setBasicMessage(message, element);
					message.setMediaId(getElementText(element, "MediaId", true));
					message.setThumbMediaId(getElementText(element, "ThumbMediaId", false));
					message.setMsgId(getElementText(element, "MsgId", true));
					return message;
				}
			});
		}
	};

	public WeixinMessage buildMessage(String content) throws Exception {
		Document doc = DocumentHelper.parseText(content);
		Element rootElement = doc.getRootElement();
		String msgType = rootElement.element("MsgType").getText();
		$WeixinMessageHandle messageHandle = messageHandles.get(msgType);
		if (messageHandle == null) {
			throw new DocumentException("unknown MsgType:" + msgType);
		}
		return messageHandle.buildMessage(rootElement);
	}

}
