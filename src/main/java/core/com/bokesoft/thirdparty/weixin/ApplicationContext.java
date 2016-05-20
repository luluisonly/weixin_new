package com.bokesoft.thirdparty.weixin;

import static com.bokesoft.thirdparty.weixin.common.TConstant.YIGO_URL_APPEND_ENCRYPT;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.thirdparty.attachment.AttachmentManager;
import com.bokesoft.thirdparty.attachment.LocalAttachmentManager;
import com.bokesoft.thirdparty.weixin.analyze.SemanticAnalyze;
import com.bokesoft.thirdparty.weixin.analyze.SemanticAnalyzeResult;
import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlow;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumberManagerInfo;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.common.APIURL;
import com.bokesoft.thirdparty.weixin.common.FileUtil;
import com.bokesoft.thirdparty.weixin.common.TConstant;
import com.bokesoft.thirdparty.weixin.common.YigoHttpRequestHeader;
import com.bokesoft.thirdparty.weixin.open.WeixinSOAService;
import com.bokesoft.thirdparty.weixin.open.WeixinSOAServiceImpl;
import com.bokesoft.thirdparty.weixin.open.WeixinSOAServieHandle;
import com.bokesoft.thirdparty.weixin.open.handle.BatchGetWeixinOpenidList;
import com.bokesoft.thirdparty.weixin.open.handle.BatchGetWeixinUserInfoHandle;
import com.bokesoft.thirdparty.weixin.open.handle.BatchMoveUserHandle;
import com.bokesoft.thirdparty.weixin.open.handle.CallWeixinCloseorderInterface;
import com.bokesoft.thirdparty.weixin.open.handle.CallWeixinOrderInterfaceFormJSAPI;
import com.bokesoft.thirdparty.weixin.open.handle.CallWeixinOrderInterfaceFormJSAPIv2;
import com.bokesoft.thirdparty.weixin.open.handle.CallWeixinOrderInterfaceFromNative;
import com.bokesoft.thirdparty.weixin.open.handle.CallWeixinOrderqueryInterface;
import com.bokesoft.thirdparty.weixin.open.handle.CallWeixinRefundInterface;
import com.bokesoft.thirdparty.weixin.open.handle.DownloadWeixinMediaHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetArticleSummaryHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetArticleTotalHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetJSSDKSign;
import com.bokesoft.thirdparty.weixin.open.handle.GetOpenidByCode;
import com.bokesoft.thirdparty.weixin.open.handle.GetPermanentQRCode;
import com.bokesoft.thirdparty.weixin.open.handle.GetTemporaryQRCode;
import com.bokesoft.thirdparty.weixin.open.handle.GetUserCumulateHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetUserReadHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetUserReadHourHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetUserShareHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetUserShareHourHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetUserSummaryHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetWeixinUserInfoByAccesstokenHandle;
import com.bokesoft.thirdparty.weixin.open.handle.GetWeixinUserInfoHandle;
import com.bokesoft.thirdparty.weixin.open.handle.Long2ShortURLHandle;
import com.bokesoft.thirdparty.weixin.open.handle.SendMessageByKeyword;
import com.bokesoft.thirdparty.weixin.open.handle.SendTemplateMessageHandler;
import com.bokesoft.thirdparty.weixin.open.handle.SendWeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.open.handle.TestWeixinHandle;
import com.bokesoft.thirdparty.weixin.open.handle.UpdateWeixinMenuHandle;
import com.bokesoft.thirdparty.weixin.open.handle.UploadWeixinMediaHandle;
import com.bokesoft.thirdparty.weixin.publicnumber.LocalWeixinPublicNumberManagerInfoProvider;
import com.bokesoft.thirdparty.weixin.publicnumber.LocalWeixinPublicNumberProvider;
import com.bokesoft.thirdparty.weixin.publicnumber.WeixinPublicNumberManagerInfoProvider;
import com.bokesoft.thirdparty.weixin.publicnumber.WeixinPublicNumberProvider;
import com.bokesoft.thirdparty.weixin.publicnumber.WeixinPublicNumberUpdater;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteProxy;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteResponseFilter;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteService;
import com.bokesoft.thirdparty.weixin.remote.WeixinRemoteServiceImpl;
import com.bokesoft.thirdparty.weixin.remote.extend.DefaultWeixinRemoteMessageHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteContext;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteMessageFlowFilter;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteMessageHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteMessageServiceFilter;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.proxy.WeixinRemoteHttpProxy;
import com.bokesoft.thirdparty.weixin.remote.proxy.WeixinRemoteLocalProxy;
import com.bokesoft.thirdparty.weixin.remote.proxy.WeixinRemoteRMIProxy;
import com.bokesoft.thirdparty.weixin.remote.proxy.WeixinRemoteSocketProxy;
import com.bokesoft.thirdparty.weixin.remote.proxy.WeixinRemoteWSProxy;
import com.bokesoft.thirdparty.weixin.remote.proxy.WeixinRemoteYIGOProxy;
import com.bokesoft.thirdparty.weixin.service.Log4jWeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageBuilder;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageFlowFilter;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandleImpl;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageServiceFilter;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageXMLBuilderImpl;
import com.bokesoft.thirdparty.weixin.service.WeixinReplyMessageBuilder;
import com.bokesoft.thirdparty.weixin.service.WeixinReplyMessageJsonBuilderImpl;
import com.bokesoft.thirdparty.weixin.service.WeixinReplyMessageXMLBuilderImpl;
import com.bokesoft.thirdparty.weixin.session.LocalWeixinSessionProvider;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;
import com.bokesoft.thirdparty.weixin.session.WeixinSessionProvider;

/**
 * 
 * 存储微信SOA上下文环境和本地数据的地方
 * 
 */
public class ApplicationContext implements WeixinRemoteContext, Initable, WeixinContext {
	
	private static final Logger LOGGER = Logger.getLogger(ApplicationContext.class);
	
	private AttachmentManager attachmentManager = null;

	private WeixinMessageHandle weixinMessageHandle = new WeixinMessageHandleImpl();

	private WeixinPublicNumber defaultWeixinPublicNumber = null;
	
	private WeixinReplyTextMessage replyTextMessage_unsubscribe = new WeixinReplyTextMessage("");

	private SemanticAnalyze semanticAnalyze = null;

	private WeixinApiInvoker weixinApiInvoker = new WeixinApiInvoker();

	private Map<String, WeixinMessageFlow> weixinMessageFlows = new HashMap<String, WeixinMessageFlow>();

	private WeixinReplyMessageJsonBuilderImpl weixinMessageJsonBuilder = new WeixinReplyMessageJsonBuilderImpl();

	private WeixinMessageBuilder weixinMessageXMLBuilder = new WeixinMessageXMLBuilderImpl();

	private WeixinPublicNumberManagerInfo weixinPublicNumberManagerInfo = new WeixinPublicNumberManagerInfo();

	private WeixinPublicNumberManagerInfoProvider weixinPublicNumberManagerInfoProvider = null;

	private WeixinPublicNumberProvider weixinPublicNumberProvider = null;

	private Map<String, WeixinPublicNumber> weixinPublicNumbers = new HashMap<String, WeixinPublicNumber>();

	private WeixinPublicNumberUpdater weixinPublicNumberUpdater = new WeixinPublicNumberUpdater();

	private WeixinRemoteMessageHandle weixinRemoteMessageHandle = null;

	private List<WeixinRemoteMessageServiceFilter> weixinRemoteMessageServiceFilters = new ArrayList<WeixinRemoteMessageServiceFilter>();

	private Map<String, WeixinRemoteProxy> weixinRemoteProxys = new HashMap<String, WeixinRemoteProxy>();

	private List<WeixinRemoteResponseFilter> weixinRemoteResponseFilters = new ArrayList<WeixinRemoteResponseFilter>();

	private WeixinRemoteService weixinRemoteService = new WeixinRemoteServiceImpl();

	private Map<String, WeixinRemoteTextMessageCellHandle> weixinRemoteTextMessageCellHandles = new HashMap<String, WeixinRemoteTextMessageCellHandle>();

	private WeixinReplyMessageBuilder weixinReplyMessageBuilder = new WeixinReplyMessageXMLBuilderImpl();

	private List<WeixinMessageServiceFilter> weixinServiceFilters = new ArrayList<WeixinMessageServiceFilter>();

	private WeixinSessionProvider weixinSessionProvider = null;

	private WeixinSOAService weixinSOAService = new WeixinSOAServiceImpl();

	private Map<String, WeixinSOAServieHandle> weixinSOAServieHandles = new HashMap<String, WeixinSOAServieHandle>();

	private YigoHttpRequestHeader yigoHttpRequestHeader = null;
	@Override
	public void addWeixinPublicNumber(WeixinPublicNumber publicNumber) throws Exception {
		this.weixinPublicNumberProvider.addWeixinPublicNumber(this, publicNumber);
	}
	@Override
	public void addWeixinRemoteMessageServiceFilter(WeixinRemoteMessageServiceFilter filter) {
		synchronized (weixinRemoteMessageServiceFilters) {
			weixinRemoteMessageServiceFilters.add(filter);
		}
	}
	@Override
	public void updateWeixinPublicNumberManagerInfo(WeixinPublicNumberManagerInfo weixinPublicNumberManagerInfo) throws Exception {
		this.weixinPublicNumberManagerInfoProvider.updateWeixinPublicNumberManagerInfo(this, weixinPublicNumberManagerInfo);
	}
	@Override
	public void addWeixinRemoteResponseFilter(WeixinRemoteResponseFilter filter) {
		synchronized (this.weixinRemoteResponseFilters) {
			this.weixinRemoteResponseFilters.add(filter);
		}
	}
	@Override
	public void addWeixinServiceFilter(WeixinMessageServiceFilter filter) {
		synchronized (this.weixinServiceFilters) {
			this.weixinServiceFilters.add(filter);
		}
	}
	
	private WeixinMessageLogger weixinMessageLogger = null;
	
	private boolean isLogWeixinMessage = false;
	@Override
	public void logConversation(WeixinSession session,WeixinMessage request, WeixinMessage response) throws Exception {
		if (isLogWeixinMessage) {
			this.weixinMessageLogger.logConversation(session, request, response);
		}
	}
	@Override
	public WeixinMessage buildWeixinMessage(JSONObject content) throws Exception {
		return this.weixinMessageJsonBuilder.buildMessage(content);
	}
	@Override
	public WeixinMessage buildWeixinMessage(String content) throws Exception {
		if (content.charAt(0) == '<') {
			return this.weixinMessageXMLBuilder.buildMessage(content);
		} else {
			return this.weixinMessageJsonBuilder.buildMessage(content);
		}
	}

	/**
	 * 清除Application Context中的数据
	 */
	private void clearContext() {
		this.weixinMessageFlows.clear();
		this.weixinPublicNumbers.clear();
		this.weixinRemoteMessageServiceFilters.clear();
		this.weixinRemoteProxys.clear();
		this.weixinRemoteResponseFilters.clear();
		this.weixinRemoteTextMessageCellHandles.clear();
		this.weixinServiceFilters.clear();
		this.weixinSOAServieHandles.clear();
	}

	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}

	public WeixinMessageHandle getWeixinMessageHandle() {
		return weixinMessageHandle;
	}
	@Override
	public WeixinPublicNumber getDefaultWeixinPublicNumber() {
		return defaultWeixinPublicNumber;
	}
	@Override
	public WeixinMessage getKeywordsReplyMessage(WeixinPublicNumber publicNumber, String key)
			throws Exception {
		WeixinMessage message = publicNumber.getKeywordsReplyMessage(key);
		if (message != null) {
			return message;
		}
		if (semanticAnalyze != null) {
			SemanticAnalyzeResult result = semanticAnalyze.analyze(key);
			if (result != null) {
				message = publicNumber.getKeywordsReplyMessage(result.getKey());
				if (message != null) {
					message.setParam(result.getParam());
					return message;
				}
			}
		}
		return publicNumber.getDefaultReplyMessage();
	}
	@Override
	public WeixinReplyTextMessage getReplyTextMessage_unsubscribe() {
		return replyTextMessage_unsubscribe;
	}
	@Override
	public SemanticAnalyze getSemanticAnalyze() {
		return semanticAnalyze;
	}
	@Override
	public WeixinApiInvoker getWeixinApiInvoker() {
		return weixinApiInvoker;
	}
	@Override
	public WeixinMessageFlow getWeixinMessageFlow(String key) {
		return this.weixinMessageFlows.get(key);
	}
	@Override
	public List<WeixinMessageServiceFilter> getWeixinMessageServiceFilters() {
		return this.weixinServiceFilters;
	}
	@Override
	public WeixinPublicNumber getWeixinPublicNumber4Edit(String uname) throws Exception{
		return this.weixinPublicNumberProvider.getWeixinPublicNumber(this, uname);
	}
	@Override
	public WeixinPublicNumber getWeixinPublicNumber(String uname) throws Exception {
		WeixinPublicNumber publicNumber = this.weixinPublicNumberProvider.getWeixinPublicNumber(this, uname);
		if (publicNumber == null) {
			return null;
		}
		switch (publicNumber.getStatus()) {
		case -1:
			return null;
		case 0:
			if(System.currentTimeMillis() < publicNumber.getService_time()){
				return publicNumber;
			}
			return null;
		case 1:
			if(System.currentTimeMillis() < publicNumber.getService_time()){
				return publicNumber;
			}
			return null;
		case 2:
			return publicNumber;
		default:
			return publicNumber;
		}
	}

	public WeixinPublicNumber getWeixinPublicNumberFromLocal(String uname) {
		return this.weixinPublicNumbers.get(uname);
	}

	public WeixinPublicNumberManagerInfo getWeixinPublicNumberManagerInfo() throws Exception {
		return weixinPublicNumberManagerInfoProvider.getWeixinPublicNumberManagerInfo(this);
	}

	public WeixinPublicNumberManagerInfo getWeixinPublicNumberManagerInfoFromLocal() {
		return weixinPublicNumberManagerInfo;
	}

	public Map<String, WeixinPublicNumber> getWeixinPublicNumbers() {
		return weixinPublicNumbers;
	}

	public WeixinPublicNumberUpdater getWeixinPublicNumberUpdater() {
		return weixinPublicNumberUpdater;
	}

	public WeixinRemoteMessageHandle getWeixinRemoteMessageHandle() {
		return weixinRemoteMessageHandle;
	}

	public List<WeixinRemoteMessageServiceFilter> getWeixinRemoteMessageServiceFilters() {
		return weixinRemoteMessageServiceFilters;
	}

	public WeixinRemoteProxy getWeixinRemoteProxy(String key) {
		return this.weixinRemoteProxys.get(key);
	}

	public List<WeixinRemoteResponseFilter> getWeixinRemoteResponseFilters() {
		return weixinRemoteResponseFilters;
	}

	public WeixinRemoteService getWeixinRemoteService() {
		return weixinRemoteService;
	}

	public WeixinRemoteTextMessageCellHandle getWeixinRemoteTextMessageCellHandle(String key) {
		return weixinRemoteTextMessageCellHandles.get(key);
	}

	public WeixinReplyMessageBuilder getWeixinReplyMessageBuilder() {
		return weixinReplyMessageBuilder;
	}

	public WeixinSessionProvider getWeixinSessionProvider() {
		return weixinSessionProvider;
	}

	public WeixinSOAService getWeixinSOAService() {
		return weixinSOAService;
	}

	public WeixinSOAServieHandle getWeixinSOAServieHandle(String serviceName) {
		return this.weixinSOAServieHandles.get(serviceName);
	}

	public Map<String, WeixinSOAServieHandle> getWeixinSOAServieHandles() {
		return weixinSOAServieHandles;
	}

	public YigoHttpRequestHeader getYigoHttpRequestHeader() {
		return yigoHttpRequestHeader;
	}
	
	private void warn(String name,String defaultValue){
		LOGGER.warn("server.soa.weixin."+name+" is empty,using default:"+defaultValue);
	}
	
	private void initApiUrls() throws Exception{
		String param = SharedBundle.getProperties("server.soa.weixin.support_type");
		if (StringUtil.isBlankOrNull(param)) {
			throw new Exception("server.soa.weixin.support_type can not be empty");
		}
		APIURL.API_TYPE = param;

		param = SharedBundle.getProperties("server.soa." + APIURL.API_TYPE + ".send_weixin_message_url");
		if (StringUtil.isBlankOrNull(param)) {
			throw new Exception("server.soa." + APIURL.API_TYPE	+ ".send_weixin_message_url can not be empty");
		}
		APIURL.SEND_WEIXIN_MESSAGE_URL = param;

		param = SharedBundle.getProperties("server.soa." + APIURL.API_TYPE + ".gen_access_token_url");
		if (StringUtil.isBlankOrNull(param)) {
			throw new Exception("server.soa." + APIURL.API_TYPE	+ ".gen_access_token_url can not be empty");
		}
		APIURL.GEN_ACCESS_TOKEN_URL = param;

		param = SharedBundle.getProperties("server.soa." + APIURL.API_TYPE + ".create_weixin_menu_url");
		if (StringUtil.isBlankOrNull(param)) {
			throw new Exception("server.soa." + APIURL.API_TYPE	+ ".create_weixin_menu_url can not be empty");
		}
		APIURL.CREATE_WEIXIN_MENU_URL = param;

		param = SharedBundle.getProperties("server.soa." + APIURL.API_TYPE + ".get_weixin_userinfo_url");
		if (param == null) {
			throw new Exception("server.soa." + APIURL.API_TYPE	+ ".get_weixin_userinfo_url can not be empty");
		}
		APIURL.GET_WEIXIN_USERINFO_URL = param;

		param = SharedBundle.getProperties("server.soa." + APIURL.API_TYPE + ".upload_weixin_media_url");
		if (param == null) {
			throw new Exception("server.soa." + APIURL.API_TYPE	+ ".upload_weixin_media_url can not be empty");
		}
		APIURL.UPLOAD_WEIXIN_MEDIA_URL = param;

		param = SharedBundle.getProperties("server.soa." + APIURL.API_TYPE + ".download_weixin_media_url");
		if (param == null) {
			throw new Exception("server.soa." + APIURL.API_TYPE	+ ".download_weixin_media_url can not be empty");
		}
		APIURL.DOWNLOAD_WEIXIN_MEDIA_URL = param;
	}
	
	private void initFilters() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		String param = SharedBundle.getProperties("server.soa.weixin.weixin_message_service_filters");
		if (StringUtil.isBlankOrNull(param)) {
			warn("weixin_message_service_filters", "");
		} else {
			String[] classes = param.split(";");
			for (String clazz : classes) {
				if (!"".equals(clazz)) {
					WeixinMessageServiceFilter filter = (WeixinMessageServiceFilter) Class.forName(clazz).newInstance();
					addWeixinServiceFilter(filter);
				}
			}
		}
		addWeixinServiceFilter(new WeixinMessageFlowFilter());

		param = SharedBundle.getProperties("server.soa.weixin.weixinremotemessage_service_filters");
		if (StringUtil.isBlankOrNull(param)) {
			warn("weixinremotemessage_service_filters", "");
		} else {
			String[] classes = param.split(";");
			for (String clazz : classes) {
				if (!"".equals(clazz)) {
					WeixinRemoteMessageServiceFilter filter = (WeixinRemoteMessageServiceFilter) Class.forName(clazz).newInstance();
					addWeixinRemoteMessageServiceFilter(filter);
				}
			}
		}
		addWeixinRemoteMessageServiceFilter(new WeixinRemoteMessageFlowFilter());

		param = SharedBundle.getProperties("server.soa.weixin.remote_response_filters");
		if (StringUtil.isBlankOrNull(param)) {
			warn("remote_response_filters", "");
		} else {
			String[] classes = param.split(";");
			for (String clazz : classes) {
				if (!"".equals(clazz)) {
					WeixinRemoteResponseFilter filter = (WeixinRemoteResponseFilter) Class.forName(clazz).newInstance();
					addWeixinRemoteResponseFilter(filter);
				}
			}
		}
	}
	// ===================================================================== //
	private static String getDSN(String DSN) {
//		if (ZFHttpRequestImpl.ZF_CHANGEDSN_COOKIE != null) { // 胖客户端
//			DSN = "SOA@" + ZFHttpRequestImpl.ZF_CHANGEDSN_COOKIE.toUpperCase();
//			return DSN;
//		}
		DSN = StringUtil.isEmpty(DSN) ? "SOA" : "SOA@" + DSN;
		return DSN;
	}
	// ===================================================================== //
	@Override
	public void initialize(WeixinContext context) throws Exception {
		clearContext();

//		String DSN = EnvUtil.getDefaultDSN();
//		String param = PropUtil.getProperty(getDSN(DSN), "server.soa.weixin.weixinmessage_procedure_size", null);
		String param = SharedBundle.getProperties("server.soa.weixin.weixinmessage_procedure_size");
		if (StringUtil.isBlankOrNull(param)) {
			warn("weixinmessage_procedure_size", "5");
		} else {
			TConstant.WEIXINMESSAGE_PROCEDURE_SIZE = Integer.parseInt(param);
		}

		param = SharedBundle.getProperties("server.soa.weixin.yigo_url_append_encrypt");
		if (StringUtil.isBlankOrNull(param)) {
			throw new Exception("server.soa.weixin. can not be empty");
		}
		YIGO_URL_APPEND_ENCRYPT = param;

		param = SharedBundle.getProperties("server.soa.weixin.server");
		if (StringUtil.isBlankOrNull(param)) {
			throw new Exception("server.soa.weixin.server can not be empty");
		}
		TConstant.WEIXIN_SERVER = param;
		
		param = SharedBundle.getProperties("server.soa.weixin.conf_path", 
				TConstant.WEBINF_PATH+File.separator+"wx-conf");
		if (StringUtil.isBlankOrNull(param)) {
			throw new Exception("server.soa.weixin.conf_path can not be empty");
		}
//		StringUtil.replaceAll(param, "${classpath}", replacement);
//		param = System.getProperty("user.dir")+File.separator+param;
		if (!param.endsWith("/")) {
			param = param + "/";
		}
		File file = new File(param);
		if (!file.exists())
			throw new Exception("File ${server.soa.weixin.conf_path} lost:" + param);
		TConstant.WEIXIN_CONF_PATH = param;

		param = SharedBundle.getProperties("server.soa.weixin.subscribe_message");
		if (StringUtil.isBlankOrNull(param)) {
			warn("subscribe_message",TConstant.SUBSCRIBE_MESSAGE);
		} else {
			TConstant.SUBSCRIBE_MESSAGE = param;
		}

		param = SharedBundle.getProperties("server.soa.weixin.default_reply_message");
		if (StringUtil.isBlankOrNull(param)) {
			warn("default_reply_message", TConstant.DEFAULT_REPLY_MESSAGE);
		} else {
			TConstant.DEFAULT_REPLY_MESSAGE = param;
		}
		
		param = SharedBundle.getProperties("server.soa.weixin.is_weixinmessage_logger");
		if (StringUtil.isBlankOrNull(param)) {
			warn("is_weixinmessage_logger", "false");
			this.isLogWeixinMessage = false;
		} else {
			this.isLogWeixinMessage = Boolean.parseBoolean(param);
		}
		
		param = SharedBundle.getProperties("server.soa.weixin.weixinmessage_logger");
		if (StringUtil.isBlankOrNull(param)) {
			warn("weixinmessage_logger", Log4jWeixinMessageLogger.class.getName());
			weixinMessageLogger = new Log4jWeixinMessageLogger();
		} else {
			weixinMessageLogger = (WeixinMessageLogger) Class.forName(param).newInstance();
		}
		weixinMessageLogger.initialize(this);
		
		param = SharedBundle.getProperties("server.soa.weixin.remote_message_handle");
		if (StringUtil.isBlankOrNull(param)) {
			warn("remote_message_handle", DefaultWeixinRemoteMessageHandle.class.getName());
			weixinRemoteMessageHandle = new DefaultWeixinRemoteMessageHandle();
		} else {
			weixinRemoteMessageHandle = (WeixinRemoteMessageHandle) Class.forName(param).newInstance();
		}
		
		param = SharedBundle.getProperties("server.soa.weixin.weixinmessage_handle");
		if (StringUtil.isBlankOrNull(param)) {
			warn("weixinmessage_Handle", WeixinMessageHandleImpl.class.getName());
			weixinMessageHandle = new WeixinMessageHandleImpl();
		} else {
			weixinMessageHandle = (WeixinMessageHandle) Class.forName(param).newInstance();
		}

		param = SharedBundle.getProperties("server.soa.weixin.semantic_analyze");
		if (StringUtil.isBlankOrNull(param)) {
			LOGGER.warn("server.soa.weixin. is null");
			warn("semantic_analyze","");
		} else {
			semanticAnalyze = (SemanticAnalyze) Class.forName(param).newInstance();
		}
		
		param = SharedBundle.getProperties("server.soa.weixin.route_yigo");
		if (StringUtil.isBlankOrNull(param)) {
			warn("route_yigo", "");
		} else {
			String[] params = param.split(";");
			if (params.length < 3) {
				LOGGER.warn("server.soa.weixin.route_yigo is not complete");
			} else {
				yigoHttpRequestHeader = new YigoHttpRequestHeader();
				yigoHttpRequestHeader.setMidClassName(params[0]);
				yigoHttpRequestHeader.setMidMethodName(params[1]);
				yigoHttpRequestHeader.setDispatchName(params[2]);
			}
		}

		param = SharedBundle.getProperties("server.soa.weixin.remote_proxys");
		if (StringUtil.isBlankOrNull(param)) {
			warn("remote_proxys", "");
		} else {
			String[] params = param.split(";");
			for (String clazz : params) {
				if (!"".equals(clazz)) {
					WeixinRemoteProxy proxy = (WeixinRemoteProxy) Class.forName(clazz)
							.newInstance();
					putWeixinRemoteProxy(proxy.getProxyTypeName(), proxy);
				}
			}
		}
		// 创建默认的公众微信号处理策略
		defaultWeixinPublicNumber = new WeixinPublicNumber();
		defaultWeixinPublicNumber.setDefaultReplyMessage(new WeixinReplyTextMessage("该微信公众号未被授权！"));
		defaultWeixinPublicNumber.setSubscribeReplyMessage(new WeixinReplyTextMessage("该微信公众号未被授权！"));
		defaultWeixinPublicNumber.setStatus(2);
		defaultWeixinPublicNumber.setMessage_token("NexbELv6sBZx");
		
		initApiUrls();

		regiestWeixinSOAServiceHandle();
		regiestDefaultWeixinRemoteProxy();
		
		initWeixinRemoteHandle();
		initProviders();
		initFilters();
		LOGGER.info("yigo Weixin application context load complete.");
	}
	private void initProviders() throws Exception{
		String param = SharedBundle.getProperties("server.soa.weixin.weixinpublicnumber_provider");
		if (StringUtil.isBlankOrNull(param)) {
			warn("weixinpublicnumber_provider", LocalWeixinPublicNumberProvider.class.getName());
			weixinPublicNumberProvider = new LocalWeixinPublicNumberProvider();
		} else {
			weixinPublicNumberProvider = (WeixinPublicNumberProvider) Class.forName(param).newInstance();
		}
		weixinPublicNumberProvider.initialize(this);

		param = SharedBundle.getProperties("server.soa.weixin.weixinsession_provider");
		if (StringUtil.isBlankOrNull(param)) {
			warn("weixinsession_provider", LocalWeixinSessionProvider.class.getName());
			weixinSessionProvider = new LocalWeixinSessionProvider();
		} else {
			weixinSessionProvider = (WeixinSessionProvider) Class.forName(param).newInstance();
		}
		weixinSessionProvider.initialize(this);

		param = SharedBundle.getProperties("server.soa.weixin.attachment_manager");
		if (StringUtil.isBlankOrNull(param)) {
			warn("attachment_manager", LocalAttachmentManager.class.getName());
			attachmentManager = new LocalAttachmentManager();
		} else {
			attachmentManager = (AttachmentManager) Class.forName(param).newInstance();
		}
		attachmentManager.initialize(this);

		param = SharedBundle.getProperties("server.soa.weixin.weixinpublicnumbermanagerinfo_provider");
		if (StringUtil.isBlankOrNull(param)) {
			warn("weixinpublicnumbermanagerinfo_provider", LocalWeixinPublicNumberManagerInfoProvider.class.getName());
			weixinPublicNumberManagerInfoProvider = new LocalWeixinPublicNumberManagerInfoProvider();
		} else {
			weixinPublicNumberManagerInfoProvider = (WeixinPublicNumberManagerInfoProvider) Class.forName(param).newInstance();
		}
		weixinPublicNumberManagerInfoProvider.initialize(this);
	}
	private void initWeixinRemoteHandle() throws Exception{
		Map<Object,Object> properties = FileUtil.readProperties("remote.properties");
		Set<Entry<Object,Object>> entries = properties.entrySet();
		for(Entry<Object,Object> entry : entries){
			String key = String.valueOf(entry.getKey());
			String className = String.valueOf(entry.getValue()).trim();
			Object clazz = Class.forName(className).newInstance();
			if (clazz instanceof WeixinMessageFlow) {
				this.putWeixinMessageFlow(key, (WeixinMessageFlow)clazz);
			}else if (clazz instanceof WeixinRemoteTextMessageCellHandle) {
				this.putWeixinRemoteTextMessageCellHandle(key, (WeixinRemoteTextMessageCellHandle)clazz);
			}else{
				throw new ClassCastException("class ["+className+"] load failed!");
			}
			LOGGER.info("load class from remote.properties:"+className+",trigger:"+key);
		}
	}
	@Override
	public void putWeixinMessageFlow(String key, WeixinMessageFlow messageFlow) {
		synchronized (this.weixinMessageFlows) {
			this.weixinMessageFlows.put(key, messageFlow);
		}
	}
	@Override
	public void putWeixinPublicNumber2Local(String uname, WeixinPublicNumber publicNumber) {
		synchronized (this.weixinPublicNumbers) {
			this.weixinPublicNumbers.put(uname, publicNumber);
		}
	}
	@Override
	public void putWeixinRemoteProxy(String key, WeixinRemoteProxy remoteProxy) {
		synchronized (this.weixinRemoteProxys) {
			this.weixinRemoteProxys.put(key, remoteProxy);
		}
	}
	@Override
	public void putWeixinRemoteTextMessageCellHandle(String key,
			WeixinRemoteTextMessageCellHandle weixinRemoteTextMessageCellHandle) {
		synchronized (weixinRemoteTextMessageCellHandles) {
			weixinRemoteTextMessageCellHandles.put(key, weixinRemoteTextMessageCellHandle);
		}

	}
	@Override
	public void putWeixinSOAServieHandle(String handleName, WeixinSOAServieHandle soaServieHandle) {
		synchronized (this.weixinSOAServieHandles) {
			this.weixinSOAServieHandles.put(handleName, soaServieHandle);
		}
	}
	/**
	 * 注册远程消息类型
	 */
	private void regiestDefaultWeixinRemoteProxy() {
		putWeixinRemoteProxy("yigo", new WeixinRemoteYIGOProxy());
		putWeixinRemoteProxy("http", new WeixinRemoteHttpProxy());
		putWeixinRemoteProxy("local", new WeixinRemoteLocalProxy());
		putWeixinRemoteProxy("rmi", new WeixinRemoteRMIProxy());
		putWeixinRemoteProxy("socket", new WeixinRemoteSocketProxy());
		putWeixinRemoteProxy("ws", new WeixinRemoteWSProxy());
	}

	/**
	 * 注册供外部调用的服务
	 */
	private void regiestWeixinSOAServiceHandle() {
		putWeixinSOAServieHandle("updateWeixinMenu", new UpdateWeixinMenuHandle());
		putWeixinSOAServieHandle("sendWeixinMessage", new SendWeixinMessageHandle());
		putWeixinSOAServieHandle("test", new TestWeixinHandle());
		putWeixinSOAServieHandle("downloadWeixinMedia", new DownloadWeixinMediaHandle());
		putWeixinSOAServieHandle("getWeixinUserInfo", new GetWeixinUserInfoHandle());
		putWeixinSOAServieHandle("batchGetWeixinUserInfo", new BatchGetWeixinUserInfoHandle());
		putWeixinSOAServieHandle("batchGetWeixinOpenidList", new BatchGetWeixinOpenidList());
		putWeixinSOAServieHandle("batchMoveUserHandle", new BatchMoveUserHandle());
		putWeixinSOAServieHandle("getWeixinUserInfoByAccesstoken", new GetWeixinUserInfoByAccesstokenHandle());
		putWeixinSOAServieHandle("uploadWeixinMedia", new UploadWeixinMediaHandle());
		putWeixinSOAServieHandle("getOpenidByCode", new GetOpenidByCode());
		putWeixinSOAServieHandle("getTemporaryQRCode",new GetTemporaryQRCode());
		putWeixinSOAServieHandle("getPermanentQRCode",new GetPermanentQRCode());
		putWeixinSOAServieHandle("sendTemplateMessage",new SendTemplateMessageHandler());
		putWeixinSOAServieHandle("sendMessageByKeyword",new SendMessageByKeyword());
		//数据统计接口相关
		putWeixinSOAServieHandle("getUserSummaryHandle", new GetUserSummaryHandle());
		putWeixinSOAServieHandle("getUserCumulateHandle", new GetUserCumulateHandle());
		putWeixinSOAServieHandle("getArticleSummaryHandle", new GetArticleSummaryHandle());
		putWeixinSOAServieHandle("getArticleTotalHandle", new GetArticleTotalHandle());
		putWeixinSOAServieHandle("getUserReadHandle", new GetUserReadHandle());
		putWeixinSOAServieHandle("getUserReadHourHandle", new GetUserReadHourHandle());
		putWeixinSOAServieHandle("getUserShareHandle", new GetUserShareHandle());
		putWeixinSOAServieHandle("getUserShareHourHandle", new GetUserShareHourHandle());
		//支付相关的api
		putWeixinSOAServieHandle("callWeixinOrderInterfaceFromNative", new CallWeixinOrderInterfaceFromNative());
		putWeixinSOAServieHandle("callWeixinOrderInterfaceFromJSAPI", new CallWeixinOrderInterfaceFormJSAPI());
		putWeixinSOAServieHandle("callWeixinOrderInterfaceFromJSAPIv2", new CallWeixinOrderInterfaceFormJSAPIv2());
		putWeixinSOAServieHandle("callWeixinOrderqueryInterface", new CallWeixinOrderqueryInterface());
		putWeixinSOAServieHandle("callWeixinCloseorderInterface", new CallWeixinCloseorderInterface());
		putWeixinSOAServieHandle("callWeixinRefundInterface", new CallWeixinRefundInterface());
		putWeixinSOAServieHandle("long2shortUrl", new Long2ShortURLHandle());
		//JSSDK
		putWeixinSOAServieHandle("getJSSDKSign", new GetJSSDKSign());
	}

	public void removeWeixinPublicNumber(String uname) {
		synchronized (this.weixinPublicNumbers) {
			this.weixinPublicNumbers.remove(uname);
		}
	}

	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}

	public void setWeixinMessageHandle(WeixinMessageHandle weixinMessageHandle) {
		this.weixinMessageHandle = weixinMessageHandle;
	}

	public void setDefaultWeixinPublicNumber(WeixinPublicNumber defaultWeixinPublicNumber) {
		this.defaultWeixinPublicNumber = defaultWeixinPublicNumber;
	}

	public void setReplyTextMessage_unsubscribe(WeixinReplyTextMessage replyTextMessage_unsubscribe) {
		this.replyTextMessage_unsubscribe = replyTextMessage_unsubscribe;
	}

	public void setSemanticAnalyze(SemanticAnalyze semanticAnalyze) {
		this.semanticAnalyze = semanticAnalyze;
	}

	public void setWeixinApiInvoker(WeixinApiInvoker weixinApiInvoker) {
		this.weixinApiInvoker = weixinApiInvoker;
	}

	public void setWeixinPublicNumberUpdater(WeixinPublicNumberUpdater weixinPublicNumberUpdater) {
		this.weixinPublicNumberUpdater = weixinPublicNumberUpdater;
	}

	public void setWeixinRemoteMessageHandle(WeixinRemoteMessageHandle weixinRemoteMessageHandle) {
		this.weixinRemoteMessageHandle = weixinRemoteMessageHandle;
	}

	public void setWeixinRemoteService(WeixinRemoteService weixinRemoteService) {
		this.weixinRemoteService = weixinRemoteService;
	}

	public void setWeixinReplyMessageBuilder(WeixinReplyMessageBuilder weixinReplyMessageBuilder) {
		this.weixinReplyMessageBuilder = weixinReplyMessageBuilder;
	}

	public void setWeixinSessionProvider(WeixinSessionProvider weixinSessionProvider) {
		this.weixinSessionProvider = weixinSessionProvider;
	}

	public void setWeixinSOAService(WeixinSOAService weixinSOAService) {
		this.weixinSOAService = weixinSOAService;
	}

	public void setYigoHttpRequestHeader(YigoHttpRequestHeader yigoHttpRequestHeader) {
		this.yigoHttpRequestHeader = yigoHttpRequestHeader;
	}

	public void shoutdown(WeixinContext context) throws Exception {
		this.weixinPublicNumberProvider.shoutdown(this);
		this.weixinSessionProvider.shoutdown(this);
		this.attachmentManager.shoutdown(this);
		this.weixinPublicNumberManagerInfoProvider.shoutdown(this);
		this.weixinMessageLogger.shoutdown(this);
	}

	public void updateWeixinPublicNumber(WeixinPublicNumber publicNumber) throws Exception {
		this.weixinPublicNumberProvider.updateWeixinPublicNumber(this, publicNumber);
	}

}
