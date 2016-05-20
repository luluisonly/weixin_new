package com.bokesoft.thirdparty.weixin.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bokesoft.myerp.common.ByteUtil;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.common.APIURL;
import com.bokesoft.thirdparty.weixin.common.Kaptcha;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.common.TConstant;
import com.zaofans.utils.RedisUtils;

/**
 * 微信公众号对象
 *
 */
public class WeixinPublicNumber implements Serializable {

	private static final Logger LOGGER = Logger
			.getLogger(WeixinPublicNumber.class);

	public static final String ACCESS_TOKEN = "access_token";

	public static final String APP_ID = "app_id";

	public static final String APP_SECRET = "app_secret";

	public static final String MCH_ID = "mch_id";

	public static final String KEY = "key";

	public static final String DEFAULTREPLY_MESSAGE = "defaultReplyMessage";

	public static final String EDIT_KEYWORDS_REPLYMESSAGE = "editKeywordsReplyMessage";

	public static final String ENCRYPT_REMOTE_MESSAGE = "encrypt_remote_message";

	private static final int[] KEYWORDS_SIZE = new int[] { 50, 100, 200, 500 };

	public static final String KEYWORDSREPLY_MESSAGE = "keywordsReplyMessage";

	public static final String MESSAGE_TOKEN = "message_token";

	public static final String PASSWORD = "password";

	public static final String PUBLIC_TYPE = "public_Type";

	public static final String REMOTE_MESSAGE_ENCRYPT = "remote_message_encrypt";

	private static final long serialVersionUID = 5303254656424300728L;

	public static final String SERVICE_TIME = "service_time";

	public static final String STATUS = "status";

	public static final String SUBSCRIBEREPLY_MESSAGE = "subscribeReplyMessage";

	public static final String UNAME = "uname";

	public static final String WEIXIN_REMOTE_LOCATIONEVENT_MESSAGE = "weixinRemoteLocationEventMessage";

	public static final String WEIXINMENU = "weixinMenu";
	
	public static final String API_TICKET = "api_ticket";

	public static WeixinPublicNumber createDefault() {
		WeixinPublicNumber publicNumber = new WeixinPublicNumber();
		publicNumber.setPublic_Type(WeixinPublicNumberType.SUBSCRIPTION);
		// WeixinRemoteImageMessage weixinRemoteImageMessage = new
		// WeixinRemoteImageMessage();
		// weixinRemoteImageMessage.setRemoteType("http");
		// weixinRemoteImageMessage.setUrl("请输入图片远程消息调用地址");
		// publicNumber.setWeixinRemoteImageMessage(weixinRemoteImageMessage);

		WeixinRemoteLocationEventMessage weixinRemoteLocationEventMessage = new WeixinRemoteLocationEventMessage();
		weixinRemoteLocationEventMessage.setRemoteType("http");
		weixinRemoteLocationEventMessage.setUrl("请输入地理位置消息远程调用地址");
		publicNumber
				.setWeixinRemoteLocationEventMessage(weixinRemoteLocationEventMessage);

		publicNumber.setWeixinMenu("{\"button\":[]}");
		publicNumber.setMessage_token(Kaptcha.randomCode(16));

		return publicNumber;
	}

	public static WeixinPublicNumber parse(byte[] bytes) {
		try {
			return (WeixinPublicNumber) ByteUtil.toObject(bytes);
		} catch (Throwable ex) {
			LOGGER.error(ex);
			return null;
		}
	}

	private String access_token = "";
	
	private String api_ticket = "";

	private String app_id = null;

	private String app_secret = null;

	private String mch_id = null;

	private String key = null;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private long createTime = 0;

	private WeixinMessage defaultReplyMessage = new WeixinReplyTextMessage(
			TConstant.DEFAULT_REPLY_MESSAGE);

	// public static final String WEIXIN_REMOTE_IMAGE_MESSAGE =
	// "weixinRemoteImageMessage";

	private Map<String, WeixinMessage> editKeywordsReplyMessage = new HashMap<String, WeixinMessage>();

	private boolean encrypt_remote_message = false;

	private long expires_in = 0;

	private String get_access_token_url = null;

	private Map<String, WeixinMessage> keywordsReplyMessage = new HashMap<String, WeixinMessage>();

	private int level = 0;

	private String message_token = null;

	private boolean needRegenerate = true;

	private String password = null;

	private WeixinPublicNumberType public_Type = null;

	private String remote_message_encrypt = "";

	// 过期时间
	private long service_time;

	private boolean showPassword;

	// 0=试用 1=使用 -1=禁用 2=永久
	private int status = 0;

	private WeixinMessage subscribeReplyMessage = new WeixinReplyTextMessage(
			TConstant.SUBSCRIBE_MESSAGE);

	private int system_version = 0;

	private String token = null;

	private String uname = null;

	private long version = 0;

	private String weixinMenu = null;

	private WeixinRemoteLocationEventMessage weixinRemoteLocationEventMessage = null;

	public String genAccess_token(WeixinContext context) throws Exception {
		synchronized (access_token) {
			String value = RedisUtils.find(this.app_id);
			if(StringUtil.isBlankOrNull(value)){//redis中未初始化
				updateAccesstoken(context);
			}else{
				String[] params = value.split(":");
				long createTime = Long.parseLong(params[1]);
				if(System.currentTimeMillis()- createTime > expires_in ){//已过期
					updateAccesstoken(context);
				}else{
					this.access_token = params[0];
				}
			}
			return access_token;
		}
	}
	public String refreshAccess_token(WeixinContext context) throws Exception {
		synchronized(access_token){
			updateAccesstoken(context);
		}
		return access_token;
	}

	private void updateAccesstoken(WeixinContext context) throws IOException, Exception {
		this.generate("client_credential", this.app_id, this.app_secret);
		context.updateWeixinPublicNumber(this);
		RedisUtils.add(this.app_id, access_token+":"+createTime);
	}

	private String generate(String grant_type, String appid, String secret)
			throws IOException {
		if (needRegenerate || get_access_token_url == null) {
			needRegenerate = get_access_token_url == null;
			get_access_token_url = APIURL.GEN_ACCESS_TOKEN_URL + "grant_type="
					+ grant_type + "&appid=" + appid + "&secret=" + secret;
		}
		this.createTime = System.currentTimeMillis();
		String result = SimpleHttpClient.invokeGet4String(get_access_token_url,
				3000);
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (jsonObject.containsKey("errcode")) {
			throw new IOException(result);
		}
		this.expires_in = jsonObject.getLong("expires_in") * 1000;
		this.access_token = jsonObject.getString("access_token");
		return this.access_token;
	}

	public String getAccess_token() {
		if (access_token != null && access_token.length() == 0) {
			access_token = "P1FjR0kE4QLwAs42LOMY-uXFN20KTY27ytNT-2myiG3vXSyA-cUL2ikEH8NBTicwoAFoDauc7NF94jHMWLYuNxzvLo2Lk6Fqs1L5HYry-pw";
		}
		return access_token;
	}
	
	
	public String genApi_ticket(WeixinContext context) throws Exception {
		if(api_ticket==null){
			api_ticket="";
		}
		synchronized (api_ticket) {
			if (this.needApi_ticketGen()) {
				this.generateApi_ticket(context);
				context.updateWeixinPublicNumber(this);
			}
			return api_ticket;
		}
	}
	
	private boolean needApi_ticketGen() {
		return System.currentTimeMillis() - api_ticketCreateTime > api_ticketExpires_in;
	}
	
	private long api_ticketCreateTime = 0;
	private long api_ticketExpires_in = 0;
	
	private String generateApi_ticket(WeixinContext context) throws Exception {
		if(StringUtils.isBlank(access_token)){
			genAccess_token(context);
		}
		String jsapiticket_url = APIURL.JSAPITICKET_URL+access_token;
		String result = SimpleHttpClient.invokeGet4String(jsapiticket_url, 3000);
		JSONObject resJson = JSONObject.parseObject(result);
		this.api_ticketCreateTime = System.currentTimeMillis();
		this.api_ticketExpires_in = resJson.getLong("expires_in") * 1000;
		this.api_ticket = resJson.getString("ticket");
		return this.api_ticket;
	}

	public String getApi_ticket(){
		return api_ticket;
	}

	public String getApp_id() {
		return app_id;
	}

	public String getApp_secret() {
		return app_secret;
	}

	public long getCreateTime() {
		return createTime;
	}

	public WeixinMessage getDefaultReplyMessage() {
		return defaultReplyMessage.copyMessage();
	}

	public Map<String, WeixinMessage> getEditKeywordsReplyMessage() {
		return editKeywordsReplyMessage;
	}

	// private WeixinRemoteImageMessage weixinRemoteImageMessage = null;

	public WeixinMessage getEditKeywordsReplyMessage(String key) {
		WeixinMessage message = editKeywordsReplyMessage.get(key);
		if (message == null) {
			return null;
		}
		return message;
	}

	// private WeixinRemoteLocationMessage weixinRemoteLocationMessage = null;

	public long getExpires_in() {
		return expires_in;
	}

	public Map<String, WeixinMessage> getKeywordsReplyMessage() {
		return keywordsReplyMessage;
	}

	public WeixinMessage getKeywordsReplyMessage(String key) {
		WeixinMessage message = keywordsReplyMessage.get(key);
		if (message == null) {
			return null;
		}
		return message.copyMessage();
	}

	public int getLevel() {
		return level;
	}

	public String getMessage_token() {
		return message_token;
	}

	public synchronized String getPassword() {
		if (showPassword) {
			this.showPassword = false;
			return password;
		}
		return null;
	}

	public WeixinPublicNumberType getPublic_Type() {
		return public_Type;
	}

	public String getRemote_message_encrypt() {
		return remote_message_encrypt;
	}

	public long getService_time() {
		return service_time;
	}

	public int getStatus() {
		return status;
	}

	public WeixinMessage getSubscribeReplyMessage() {
		return subscribeReplyMessage.copyMessage();
	}

	public int getSystem_version() {
		return system_version;
	}

	public String getToken() {
		return token;
	}

	public String getUname() {
		return uname;
	}

	public long getVersion() {
		return version;
	}

	public String getWeixinMenu() {
		return weixinMenu;
	}

	public WeixinRemoteLocationEventMessage getWeixinRemoteLocationEventMessage() {
		if (weixinRemoteLocationEventMessage == null) {
			return null;
		}
		return weixinRemoteLocationEventMessage.copyMessage();
	}

	public boolean isEncrypt_remote_message() {
		return encrypt_remote_message;
	}

	private boolean needGen() {
		return System.currentTimeMillis() - createTime > expires_in;
	}

	public void putEditKeywordsReplyMessage(String key, WeixinMessage message) {
		if (key == null) {
			return;
		}
		if (key != message.getServiceKey()) {
			key = message.getServiceKey();
		}
		String[] splitKeys = key.split(" ");
		synchronized (editKeywordsReplyMessage) {
			this.editKeywordsReplyMessage.put(key, message);
		}
		synchronized (this.keywordsReplyMessage) {
			for (int i = 0; i < splitKeys.length; i++) {
				String splitKey = splitKeys[i];
				if (!StringUtil.isBlankOrNull(splitKey)) {
					splitKey = splitKey.trim();
					// message.setServiceKey(splitKey);
					this.keywordsReplyMessage.put(splitKey, message);
				}
			}
		}
	}

	public void putKeywordsReplyMessage(String key, WeixinMessage message)
			throws Exception {
		synchronized (this.keywordsReplyMessage) {
			if (this.keywordsReplyMessage.size() > KEYWORDS_SIZE[level]) {
				throw new Exception("关键词个数超出限制，当前等级：" + level + "，关键词最多个数："
						+ KEYWORDS_SIZE[level]);
			}
			this.keywordsReplyMessage.put(key, message);
		}
	}

	// public WeixinRemoteImageMessage getWeixinRemoteImageMessage() {
	// return weixinRemoteImageMessage;
	// }

	public void removeEditKeywordsReplyMessage(String key) {
		if (key == null) {
			return;
		}
		String[] splitKeys = key.split(" ");
		synchronized (editKeywordsReplyMessage) {
			this.editKeywordsReplyMessage.remove(key);
		}
		synchronized (this.keywordsReplyMessage) {
			for (int i = 0; i < splitKeys.length; i++) {
				String splitKey = splitKeys[i];
				if (!StringUtil.isBlankOrNull(splitKey)) {
					this.keywordsReplyMessage.remove(splitKey.trim());
				}
			}
		}
	}

	// public WeixinRemoteLocationMessage getWeixinRemoteLocationMessage() {
	// if (weixinRemoteLocationMessage == null) {
	// return null;
	// }
	// return weixinRemoteLocationMessage.copyMessage();
	// }

	public void removeKeywordsReplyMessage(String key) {
		synchronized (this.keywordsReplyMessage) {
			this.keywordsReplyMessage.remove(key);
		}
	}

	public void setAccess_token(String access_token) {
		if (access_token != null && access_token.length() > 100) {
			return;
		}
		this.access_token = access_token;
	}

	public void setApi_ticket(String api_ticket) {
		if (api_ticket != null && api_ticket.length() > 100) {
			return;
		}
		this.api_ticket = api_ticket;
	}
	
	public void setApp_id(String appid) {
		if (appid != null && appid.length() > 100) {
			return;
		}
		this.setNeedRegenerate(true);
		this.app_id = appid;
	}

	public void setApp_secret(String app_secret) {
		if (app_secret != null && app_secret.length() > 100) {
			return;
		}
		this.setNeedRegenerate(true);
		this.app_secret = app_secret;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void setDefaultReplyMessage(WeixinMessage defaultReplyMessage) {
		this.defaultReplyMessage = defaultReplyMessage;
	}

	public void setEncrypt_remote_message(boolean encrypt_remote_message) {
		this.encrypt_remote_message = encrypt_remote_message;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	public void setLevel(int level) {
		if (level > 3) {
			level = 3;
		}
		if (level < 0) {
			level = 0;
		}
		this.level = level;

	}

	public void setMessage_token(String message_token) {
		this.message_token = message_token;
	}

	public void setNeedRegenerate(boolean needRegenerate) {
		this.needRegenerate = needRegenerate;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPublic_Type(WeixinPublicNumberType publicType) {
		this.public_Type = publicType;
	}

	public void setRemote_message_encrypt(String remote_message_encrypt) {
		if (remote_message_encrypt != null
				&& remote_message_encrypt.length() > 32) {
			return;
		}
		this.remote_message_encrypt = remote_message_encrypt;
	}

	public void setService_time(long service_time) {
		this.service_time = service_time;
	}

	public void setShowPassword(boolean showPassword) {
		this.showPassword = showPassword;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setSubscribeReplyMessage(WeixinMessage subscribeReplyMessage) {
		this.subscribeReplyMessage = subscribeReplyMessage;
	}

	public void setSystem_version(int system_version) {
		this.system_version = system_version;
	}

	public void setToken(String token) {
		if (token != null && token.length() > 100) {
			return;
		}
		this.token = token;
	}

	public void setUname(String uname) {
		if (uname != null && uname.length() > 100) {
			return;
		}
		this.uname = uname;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	// public void setWeixinRemoteImageMessage(WeixinRemoteImageMessage
	// weixinRemoteImageMessage) {
	// this.weixinRemoteImageMessage = weixinRemoteImageMessage;
	// }

	public void setWeixinMenu(String weixinMenu) {
		if(weixinMenu == null){
			return;
		}
		if (weixinMenu.length() > 10240) {
			return;
		}
		// 在此处统一处理特殊菜单
		weixinMenu = parseMenuJson(weixinMenu);
		System.out.println("-----------------------weixinMenu---------------------" + weixinMenu);
		this.weixinMenu = weixinMenu;
	}

	private String parseMenuJson(String weixinMenu) {
		JSONObject menuJson = JSONObject.parseObject(weixinMenu);
		JSONArray buttons = menuJson.getJSONArray("button");
		if(buttons.size()==0){
			//如果没有详细的内容，直接返回
			return weixinMenu;
		}else{
			//先处理外层，再处理内层
			for(int i=0;i<buttons.size();i++){
				JSONObject temp = buttons.getJSONObject(i);
				String type = temp.getString("type");
				if("click".equalsIgnoreCase(type)||type.length()==0){
					//只有在click事件中才可能会有subbutton
					JSONArray sub_buttons = temp.getJSONArray("sub_button");
					if(sub_buttons.size()>0){
						sub_buttons = parseSubButton(sub_buttons);
					}
				}else{
					if ("scancode_push".equalsIgnoreCase(type)) {
						temp.put("key","rselfmenu_0_1");
					} else if ("scancode_waitmsg".equalsIgnoreCase(type)) {
						temp.put("key","rselfmenu_0_0");
					} else if ("pic_sysphoto".equalsIgnoreCase(type)) {
						temp.put("key","rselfmenu_1_0");
					} else if ("pic_photo_or_album".equalsIgnoreCase(type)) {
						temp.put("key","rselfmenu_1_1");
					} else if ("pic_weixin".equalsIgnoreCase(type)) {
						temp.put("key","rselfmenu_1_2");
					} else if ("location_select".equalsIgnoreCase(type)) {
						temp.put("key","rselfmenu_2_0");
					}
				}
			}
		}
		return menuJson.toString();
	}

	private String parseMenu(JSONArray jArray) {
		JSONArray menu = new JSONArray();
		for (int i = 0; i < jArray.size(); i++) {
			JSONObject json = jArray.getJSONObject(i);
			JSONArray temp = new JSONArray();
			JSONArray sub_button = json.getJSONArray("sub_button");
			temp = parseSubButton(sub_button);
			json.put("sub_button", temp);
			menu.add(json);
		}
		return menu.toString();
	}
	private JSONArray parseSubButton(JSONArray sub_button){
		JSONArray temp = new JSONArray();
		for(int j =0;j<sub_button.size();j++){
			JSONObject sub_button_json = sub_button.getJSONObject(j);
			if (sub_button_json.getString("type").equalsIgnoreCase("view")
					|| sub_button_json.getString("type").equalsIgnoreCase("click")) {
				temp.add(sub_button_json);
			} else {
				if (sub_button_json.getString("type").equalsIgnoreCase("scancode_push")) {
					sub_button_json.put("key", "rselfmenu_0_1");
				} else if (sub_button_json.getString("type").equalsIgnoreCase(
						"scancode_waitmsg")) {
					sub_button_json.put("key", "rselfmenu_0_0");
				} else if (sub_button_json.getString("type").equalsIgnoreCase(
						"pic_sysphoto")) {
					sub_button_json.put("key", "rselfmenu_1_0");
				} else if (sub_button_json.getString("type").equalsIgnoreCase(
						"pic_photo_or_album")) {
					sub_button_json.put("key", "rselfmenu_1_1");
				} else if (sub_button_json.getString("type")
						.equalsIgnoreCase("pic_weixin")) {
					sub_button_json.put("key", "rselfmenu_1_2");
				} else if (sub_button_json.getString("type").equalsIgnoreCase(
						"location_select")) {
					sub_button_json.put("key", "rselfmenu_2_0");
				}
				temp.add(sub_button_json);
			}
		}
		return temp;
	}

	// public void setWeixinRemoteLocationMessage(WeixinRemoteLocationMessage
	// weixinRemoteLocationMessage) {
	// this.weixinRemoteLocationMessage = weixinRemoteLocationMessage;
	// }

	public void setWeixinRemoteLocationEventMessage(
			WeixinRemoteLocationEventMessage weixinRemoteLocationEventMessage) {
		this.weixinRemoteLocationEventMessage = weixinRemoteLocationEventMessage;
	}

	public byte[] toBytes() {
		try {
			return ByteUtil.toBytes(this);
		} catch (Throwable e) {
			LOGGER.error(e);
			return null;
		}
	}

	public String toString() {
		return JSONObject.toJSONString(this,
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.PrettyFormat);
	}

	public String toString(boolean showPassword) {
		this.showPassword = showPassword;
		String temp = JSONObject.toJSONString(this,
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.PrettyFormat);
		this.showPassword = false;
		return temp;
	}
}
