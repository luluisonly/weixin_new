package com.bokesoft.thirdparty.weixin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bokesoft.thirdparty.weixin.bean.message.Article;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyCustomerServiceMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyImageMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyMusicMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyNewsMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyVideoMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyVoiceMessage;

/**
 * 
 * 根据Message生成微信消息XML
 *
 */
public class WeixinReplyMessageXMLBuilderImpl implements WeixinReplyMessageBuilder {

	private interface $WeixinReplyMessageHandle {
		public abstract String buildMessage(WeixinMessage replyMessage);
	}
	private Map<String, $WeixinReplyMessageHandle> messageHandles = new HashMap<String, $WeixinReplyMessageHandle>() {

		private static final long serialVersionUID = 1L;

		private final String musicTemplate = 
				  "<xml><ToUserName><![CDATA[%s]]></ToUserName>"
				+ "<FromUserName><![CDATA[%s]]></FromUserName>" 
				+ "<CreateTime>%d</CreateTime>"
				+ "<MsgType><![CDATA[music]]></MsgType>" 
				+ "<Music><Title><![CDATA[%s]]></Title>"
				+ "<Description><![CDATA[%s]]></Description>" 
				+ "<MusicUrl><![CDATA[%s]]></MusicUrl>"
				+ "<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>"
				+ "<ThumbMediaId><![CDATA[%s]]></ThumbMediaId></Music></xml>";

		private final String newsItemTemplate = 
				  "<item><Title><![CDATA[%s]]></Title>"
				+ "<Description><![CDATA[%s]]></Description>" 
				+ "<PicUrl><![CDATA[%s]]></PicUrl>"
				+ "<Url><![CDATA[%s]]></Url></item>";

		private final String newsTemplate1 = 
				  "<xml><ToUserName><![CDATA[%s]]></ToUserName>"
				+ "<FromUserName><![CDATA[%s]]></FromUserName>"
				+ "<CreateTime>%d</CreateTime>"
				+ "<MsgType><![CDATA[news]]></MsgType>" 
				+ "<ArticleCount>%d</ArticleCount><Articles>";

		private final String newsTemplate2 = "</Articles></xml>";

		private final String textTemplate = 
				  "<xml><ToUserName><![CDATA[%s]]></ToUserName>"
				+ "<FromUserName><![CDATA[%s]]></FromUserName>" 
				+ "<CreateTime>%d</CreateTime>"
				+ "<MsgType><![CDATA[text]]></MsgType>" 
				+ "<Content><![CDATA[%s]]></Content></xml>";
		private final String voiceTemplate = 
				"<xml><ToUserName><![CDATA[%s]]></ToUserName>"
				+"<FromUserName><![CDATA[%s]]></FromUserName>"
				+"<CreateTime>%d</CreateTime>"
				+"<MsgType><![CDATA[voice]]></MsgType>"
				+"<Voice><MediaId><![CDATA[%s]]></MediaId></Voice></xml>";
		private final String videoTemplate = 
				"<xml><ToUserName><![CDATA[%s]]></ToUserName>"
				+"<FromUserName><![CDATA[%s]]></FromUserName>"
				+"<CreateTime>%d</CreateTime>"
				+"<MsgType><![CDATA[video]]></MsgType>"
				+"<Video><MediaId><![CDATA[%s]]></MediaId>"
				+"<ThumbMediaId><![CDATA[%s]]></ThumbMediaId></Video></xml>";
		
		private final String imageTemplate = 
				"<xml><ToUserName><![CDATA[%s]]></ToUserName>"
				+"<FromUserName><![CDATA[%s]]></FromUserName>"
				+"<CreateTime>%d</CreateTime>"
				+"<MsgType><![CDATA[video]]></MsgType>"
				+"<Image><MediaId><![CDATA[%s]]></MediaId></Image></xml>";
		
		private final String coustomerServiceTemplate = 
				"<xml><ToUserName><![CDATA[%s]]></ToUserName>"
				+"<FromUserName><![CDATA[%s]]></FromUserName>"
				+"<CreateTime>%d</CreateTime>"
				+"<MsgType><![CDATA[transfer_customer_service]]></MsgType></xml>";
		{
			put(WeixinMessage.NEWS, new $WeixinReplyMessageHandle() {
				@Override
				public String buildMessage(WeixinMessage replyMessage) {
					WeixinReplyNewsMessage replyNewsMessage = (WeixinReplyNewsMessage) replyMessage;
					Object[] params = new Object[] { 
							replyNewsMessage.getToUserName(),
							replyNewsMessage.getFromUserName(),
							replyNewsMessage.getCreateTime(),
							replyNewsMessage.getArticleCount() 
						};
					StringBuffer buffer = new StringBuffer(String.format(newsTemplate1, params));
					List<Article> articles = replyNewsMessage.getArticles();
					for (Article article : articles) {
						Object[] paramsItem = new Object[] { 
								article.getTitle(),
								article.getDescription(),
								article.getPicUrl(),
								article.getUrl() 
							};
						buffer.append(String.format(newsItemTemplate, paramsItem));
					}
					return buffer.append(newsTemplate2).toString();
				}
			});
			put(WeixinMessage.WASTE, new $WeixinReplyMessageHandle() {
				@Override
				public String buildMessage(WeixinMessage message) {
					return "";
				}
			});
			put(WeixinMessage.TEXT, new $WeixinReplyMessageHandle() {
				@Override
				public String buildMessage(WeixinMessage message) {
					WeixinReplyTextMessage replyTextMessage = (WeixinReplyTextMessage) message;
					Object[] params = new Object[] { 
							replyTextMessage.getToUserName(),
							replyTextMessage.getFromUserName(), 
							replyTextMessage.getCreateTime(),
							replyTextMessage.getContent() 
						};
					return String.format(textTemplate, params);
				}
			});
			put(WeixinMessage.MUSIC, new $WeixinReplyMessageHandle() {
				@Override
				public String buildMessage(WeixinMessage replyMessage) {
					WeixinReplyMusicMessage replyMusicMessage = (WeixinReplyMusicMessage) replyMessage;
					Object[] params = new Object[] { 
							replyMusicMessage.getToUserName(),
							replyMusicMessage.getFromUserName(), 
							replyMusicMessage.getCreateTime(),
							replyMusicMessage.getTitle(), 
							replyMusicMessage.getDescription(),
							replyMusicMessage.getMusicUrl(), 
							replyMusicMessage.getHqMusicUrl(),
							replyMusicMessage.getThumbMediaId()
						};
					return String.format(musicTemplate, params);
				}
			});
			put(WeixinMessage.IMAGE,new $WeixinReplyMessageHandle() {
				@Override
				public String buildMessage(WeixinMessage replyMessage) {
					WeixinReplyImageMessage replyImageMessage = (WeixinReplyImageMessage) replyMessage;
					Object[] params = new Object[] { 
							replyImageMessage.getToUserName(),
							replyImageMessage.getFromUserName(), 
							replyImageMessage.getCreateTime(),
							replyImageMessage.getMediaId()
						};
					return String.format(imageTemplate, params);
				}
			});
			put(WeixinMessage.VOICE, new $WeixinReplyMessageHandle() {
				@Override
				public String buildMessage(WeixinMessage replyMessage) {
					WeixinReplyVoiceMessage replyVoiceMessage = (WeixinReplyVoiceMessage) replyMessage;
					Object[] params = new Object[] { 
							replyVoiceMessage.getToUserName(),
							replyVoiceMessage.getFromUserName(), 
							replyVoiceMessage.getCreateTime(),
							replyVoiceMessage.getMediaId()
						};
					return String.format(voiceTemplate, params);
				}
			});
			put(WeixinMessage.VIDEO, new $WeixinReplyMessageHandle() {
				@Override
				public String buildMessage(WeixinMessage replyMessage) {
					WeixinReplyVideoMessage replyVideoMessage = (WeixinReplyVideoMessage) replyMessage;
					Object[] params = new Object[] { 
							replyVideoMessage.getToUserName(),
							replyVideoMessage.getFromUserName(), 
							replyVideoMessage.getCreateTime(),
							replyVideoMessage.getMediaId(), 
							replyVideoMessage.getThumbMediaId()
						};
					return String.format(videoTemplate, params);
				}
			});
			put(WeixinMessage.CUSTOMERSERVICE, new $WeixinReplyMessageHandle(){
				@Override
				public String buildMessage(WeixinMessage replyMessage) {
					WeixinReplyCustomerServiceMessage replyCustomerServiceMessage = (WeixinReplyCustomerServiceMessage) replyMessage;
					Object[] params = new Object[]{
						replyCustomerServiceMessage.getToUserName(),
						replyCustomerServiceMessage.getFromUserName(),
						replyCustomerServiceMessage.getCreateTime()
					};
					return String.format(coustomerServiceTemplate, params);
				}
			});
		}
	};
	@Override
	public String buildMessage(WeixinMessage replyMessage) {
		$WeixinReplyMessageHandle replyMessageHandle = messageHandles.get(replyMessage.getMsgType());
		if (replyMessageHandle == null) {
			// throw new Exception("unknow MsgType:" +
			// replyMessage.getMsgType());
		}
		return replyMessageHandle.buildMessage(replyMessage);
	}

}
