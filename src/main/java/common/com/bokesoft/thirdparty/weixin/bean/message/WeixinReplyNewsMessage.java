package com.bokesoft.thirdparty.weixin.bean.message;

import java.util.ArrayList;
import java.util.List;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public class WeixinReplyNewsMessage extends WeixinReplyMessage implements WeixinMessage {

	private static final long serialVersionUID = -4002682988121144913L;
	private int articleCount;

	public int getArticleCount() {
		return articleCount;
	}

	public WeixinReplyNewsMessage() {
		this.setMsgType(NEWS);
		this.setReply(true);
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public void addArticle(Article article) {
		this.articles.add(article);
	}

	private List<Article> articles = new ArrayList<Article>();

	@SuppressWarnings("unchecked")
	public <T extends WeixinMessage> T copyMessage() {
		WeixinReplyNewsMessage message = new WeixinReplyNewsMessage();
		message.setArticleCount(this.articleCount);
		ArrayList<Article> arrayList = new ArrayList<Article>();
		for (int i = 0; i < this.articles.size(); i++) {
			Article article = this.articles.get(i);
			Article copy = new Article();
			copy.setDescription(article.getDescription());
			copy.setPicUrl(article.getPicUrl());
			copy.setTitle(article.getTitle());
			copy.setUrl(article.getUrl());
			arrayList.add(copy);
		}
		message.setArticles(arrayList);
		return (T)message;
	}
	
	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle) throws Exception{
		return messageHandle.handleWeixinReplyNewsMessage(session, this);
	}

	public void logMessage(WeixinSession session, WeixinMessageLogger logger) throws Exception {
		logger.logResponseNewsMessage(session, this);
	}
	
	
}
