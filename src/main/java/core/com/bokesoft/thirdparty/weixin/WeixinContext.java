package com.bokesoft.thirdparty.weixin;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.attachment.AttachmentManager;
import com.bokesoft.thirdparty.weixin.analyze.SemanticAnalyze;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumberManagerInfo;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.common.YigoHttpRequestHeader;
import com.bokesoft.thirdparty.weixin.open.WeixinSOAService;
import com.bokesoft.thirdparty.weixin.open.WeixinSOAServieHandle;
import com.bokesoft.thirdparty.weixin.publicnumber.WeixinPublicNumberUpdater;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteProxy;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteResponseFilter;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteService;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteContext;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteMessageServiceFilter;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageServiceFilter;
import com.bokesoft.thirdparty.weixin.service.WeixinReplyMessageBuilder;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;
import com.bokesoft.thirdparty.weixin.session.WeixinSessionProvider;

public interface WeixinContext extends WeixinRemoteContext, Initable{

	public abstract void addWeixinPublicNumber(WeixinPublicNumber publicNumber)throws Exception;

	public abstract void addWeixinRemoteMessageServiceFilter(WeixinRemoteMessageServiceFilter filter);

	public abstract void addWeixinRemoteResponseFilter(WeixinRemoteResponseFilter filter);

	public abstract void addWeixinServiceFilter(WeixinMessageServiceFilter filter);
	
	public abstract void logConversation(WeixinSession session,WeixinMessage request,WeixinMessage response) throws Exception;
	
	/**
	 * 把JSONObject转成Java对象
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public abstract WeixinMessage buildWeixinMessage(JSONObject content) throws Exception;
	
	/**
	 * 把文本消息转成java对象，文本消息可以是XML的或者JSON的
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public abstract WeixinMessage buildWeixinMessage(String content) throws Exception;

	public abstract AttachmentManager getAttachmentManager();

	public abstract WeixinMessageHandle getWeixinMessageHandle();

	public abstract WeixinPublicNumber getDefaultWeixinPublicNumber();

	/**
	 * 根据关键字获取回复的消息，先根据key去拿，如果拿不到则对key进行语义分析，根据语义分析的结果去拿，如果拿不到则返回默认的消息回复
	 * @param publicNumber
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public abstract WeixinMessage getKeywordsReplyMessage(WeixinPublicNumber publicNumber,String key) throws Exception;

	public abstract WeixinReplyTextMessage getReplyTextMessage_unsubscribe();

	public abstract SemanticAnalyze getSemanticAnalyze();

	public abstract WeixinApiInvoker getWeixinApiInvoker();

	public abstract List<WeixinMessageServiceFilter> getWeixinMessageServiceFilters();

	public abstract WeixinPublicNumber getWeixinPublicNumber(String uname) throws Exception;
	
	public abstract WeixinPublicNumber getWeixinPublicNumber4Edit(String uname) throws Exception;

	public abstract WeixinPublicNumber getWeixinPublicNumberFromLocal(String uname);

	public abstract WeixinPublicNumberManagerInfo getWeixinPublicNumberManagerInfo() throws Exception ;
	
	public abstract void updateWeixinPublicNumberManagerInfo(WeixinPublicNumberManagerInfo weixinPublicNumberManagerInfo) throws Exception ;

	public abstract WeixinPublicNumberManagerInfo getWeixinPublicNumberManagerInfoFromLocal();

	public abstract Map<String, WeixinPublicNumber> getWeixinPublicNumbers() ;
	
	public abstract WeixinPublicNumberUpdater getWeixinPublicNumberUpdater();

	public abstract WeixinRemoteProxy getWeixinRemoteProxy(String key);

	public abstract List<WeixinRemoteResponseFilter> getWeixinRemoteResponseFilters();

	public abstract WeixinRemoteService getWeixinRemoteService();

	public abstract WeixinReplyMessageBuilder getWeixinReplyMessageBuilder();

	public abstract WeixinSessionProvider getWeixinSessionProvider();

	public abstract WeixinSOAService getWeixinSOAService();

	public abstract WeixinSOAServieHandle getWeixinSOAServieHandle(String serviceName);

	public abstract Map<String, WeixinSOAServieHandle> getWeixinSOAServieHandles();

	public abstract YigoHttpRequestHeader getYigoHttpRequestHeader();

	public abstract void putWeixinPublicNumber2Local(String uname, WeixinPublicNumber publicNumber);

	public abstract void putWeixinRemoteProxy(String key, WeixinRemoteProxy remoteProxy);

	public abstract void putWeixinSOAServieHandle(String handleName,WeixinSOAServieHandle soaServieHandle);

	public abstract void removeWeixinPublicNumber(String uname)throws Exception;

	public abstract void setAttachmentManager(AttachmentManager attachmentManager);

	public abstract void setWeixinMessageHandle(WeixinMessageHandle weixinMessageHandle);

	public abstract void setDefaultWeixinPublicNumber(WeixinPublicNumber defaultWeixinPublicNumber);

	public abstract void setReplyTextMessage_unsubscribe(WeixinReplyTextMessage replyTextMessage_unsubscribe);

	public abstract void setSemanticAnalyze(SemanticAnalyze semanticAnalyze);

	public abstract void setWeixinApiInvoker(WeixinApiInvoker weixinApiInvoker);

	public abstract void setWeixinPublicNumberUpdater(WeixinPublicNumberUpdater weixinPublicNumberUpdater);

	public abstract void setWeixinRemoteService(WeixinRemoteService weixinRemoteService);

	public abstract void setWeixinReplyMessageBuilder(WeixinReplyMessageBuilder weixinReplyMessageBuilder);
	
	public abstract void setWeixinSessionProvider(WeixinSessionProvider weixinSessionProvider);
	
	public abstract void setWeixinSOAService(WeixinSOAService weixinSOAService);
	
	public abstract void setYigoHttpRequestHeader(YigoHttpRequestHeader yigoHttpRequestHeader);
	
	public abstract void updateWeixinPublicNumber(WeixinPublicNumber publicNumber)throws Exception;

}