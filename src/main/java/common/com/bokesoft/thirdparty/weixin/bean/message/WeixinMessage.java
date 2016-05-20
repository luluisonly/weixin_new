package com.bokesoft.thirdparty.weixin.bean.message;

import java.io.Serializable;

import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalService;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageLogger;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

public interface WeixinMessage extends Serializable{

	public static final String EVENT = "event";

	public static final String IMAGE = "image";
	
	public static final String LINK = "link";
	
	public static final String LOCATION = "location";

	public static final String MUSIC = "music";

	public static final String NEWS = "news";

	public static final String PARAM = "param";

	public static final String TEXT = "text";
	
	public static final String VIDEO = "video";

	public static final String VOICE = "voice";
	
	public static final String WASTE = "waste";
	
	public static final String CUSTOMERSERVICE = "customerservice";
	
	public abstract <T extends WeixinMessage> T copyMessage();
	
	public abstract <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage(String uname,String serviceKey,String username,String password);
	
	public abstract WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle,WeixinMessageFlowLocalService flowLocalService)
			throws Exception;
	
	public abstract WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle)throws Exception;
	
	public abstract void logMessage(WeixinSession session,WeixinMessageLogger logger) throws Exception;
	
	public abstract long getCreateTime();

	public abstract String getFromUserName();
	
	public abstract String getMsgType();

	public abstract String [] getParam();
	
	public abstract String getServiceKey();

	public abstract String getToUserName();

	public abstract boolean isReply() ;

	public abstract void setBasicWeixinMessage(WeixinMessage oldMessage);
	
	public abstract void setCreateTime(long createTime);

	public abstract void setFromUserName(String fromUserName);
	
	public abstract void setMsgType(String msgType);
	
	public abstract void setParam(String [] param);
	
	public abstract void setReply(boolean reply) ;
	
	public abstract void setServiceKey(String serviceKey);

	public abstract void setToUserName(String toUserName);

}
