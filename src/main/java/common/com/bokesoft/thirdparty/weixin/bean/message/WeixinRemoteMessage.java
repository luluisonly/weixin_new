package com.bokesoft.thirdparty.weixin.bean.message;

import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public interface WeixinRemoteMessage extends WeixinMessage {

	public static final String PARAM = "param";

	public static final String PASSWORD = "password";

	public static final String REMOTE_CHAINING = "remote_chaining";

	public static final String REMOTE_EVENT = "remote_event";

	public static final String REMOTE_IMAGE = "remote_image";

	public static final String REMOTE_LOCATION = "remote_location";

	public static final String REMOTE_LOCATIONEVENT = "remote_locationevent";

	public static final String REMOTE_TEXT = "remote_text";

	public static final String REMOTE_TYPE = "remoteType";

	public static final String REMOTE_VIDEO = "remote_video";

	public static final String REMOTE_VOICE = "remote_voice";

	public static final String UNAME = "uname";

	public static final String USERNAME = "username";

	public abstract int getIndex();

	public abstract String[] getParam();

	public abstract String getPassword();

	public abstract String getRemoteType();

	public abstract String getUname();

	public abstract String getUrl();

	public abstract String getUsername();

	public abstract void setIndex(int index);

	public abstract void setParam(String[] param);

	public abstract void setPassword(String password);

	public abstract void setBasicWeixinRemoteMessage(WeixinSession session,WeixinRemoteMessage oldMessage);

	public abstract void setRemoteType(String remoteType);

	public abstract void setUname(String uname);

	public abstract void setUrl(String url);
	
	public abstract void setUsername(String username);
}
