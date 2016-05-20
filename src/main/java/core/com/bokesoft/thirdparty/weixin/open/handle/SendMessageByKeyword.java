package com.bokesoft.thirdparty.weixin.open.handle;

import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinApiResult;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.message.Article;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyNewsMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

/**
 * 
 * 发送关键字对应信息
 * 
 */
public class SendMessageByKeyword extends AbstractWeixinSOAServieHandle{
	
	private static final Logger LOGGER = Logger.getLogger(SendMessageByKeyword.class);

	@Override
	public SOAResponseMessage handle(WeixinContext context,
			SOARequestMessage message) throws Exception {
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if(publicNumber == null){
			return new SOAResponseMessage(1000, "unknow uname:"+uname);
		}
		String param = message.getStringParam();
		JSONObject paramJSON = JSONObject.parseObject(param);
		JSONObject mesgInfo = new JSONObject();
		mesgInfo.put("touser",paramJSON.getString("openid"));
		WeixinMessage replyMessage = publicNumber.getKeywordsReplyMessage(paramJSON.getString("keyword"));
		if(replyMessage.getMsgType().equalsIgnoreCase(WeixinMessage.NEWS)){
			mesgInfo.put("msgtype","news");
			JSONObject news = new JSONObject();
			JSONArray articlesArray = new JSONArray();
			WeixinReplyNewsMessage replyNewsMessage = (WeixinReplyNewsMessage)replyMessage;
			List<Article> articles = replyNewsMessage.getArticles();
			for(Article a : articles){
				JSONObject art = new JSONObject();
				art.put("title",a.getTitle());
				art.put("description",a.getDescription());
				art.put("url",a.getUrl());
				art.put("picurl",a.getPicUrl());
				articlesArray.add(art);
			}
			news.put("articles",articlesArray);
			mesgInfo.put("news", news);
		}else if(replyMessage.getMsgType().equalsIgnoreCase(WeixinMessage.TEXT)){
			mesgInfo.put("msgtype","text");
			WeixinReplyTextMessage replyTextMessage = (WeixinReplyTextMessage)replyMessage;
			JSONObject text = new JSONObject();
			text.put("content", replyTextMessage.getContent());
			mesgInfo.put("text", text);
		}
		LOGGER.info("mesgInfo:"+mesgInfo);
		WeixinApiInvoker apiInvoker = context.getWeixinApiInvoker();
		WeixinApiResult result = apiInvoker.sendMessage(publicNumber.genAccess_token(context),mesgInfo.toString());
		if(result.getErrcode()==0){
			return new SOAResponseMessage(0, "ok");
		}else{
			return new SOAResponseMessage(1000, result.getErrmsg());
		}
	}

}
