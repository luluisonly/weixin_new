package com.bokesoft.thirdparty.weixin.bean.message;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalService;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageHandle;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;


public abstract class BasicWeixinMessage implements WeixinMessage {
	
	private static final long serialVersionUID = 8542244575262602271L;

	private long createTime;

	private String fromUserName;
	
	private String msgType;

	private String [] param;

	private boolean reply;

	private String serviceKey;

	private String toUserName;

	public abstract <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage();

	@SuppressWarnings("unchecked")
	public <T extends WeixinRemoteMessage> T copyWeixinRemoteMessage(String uname,String serviceKey,String username,String password){
		WeixinRemoteMessage remoteMessage = this.copyWeixinRemoteMessage();
		remoteMessage.setUname(uname);
		remoteMessage.setServiceKey(serviceKey);
		remoteMessage.setUsername(username);
		remoteMessage.setPassword(password);
		return (T) remoteMessage ;
	}
	
	public WeixinMessage doService(WeixinSession session, WeixinMessageHandle messageHandle,WeixinMessageFlowLocalService flowLocalService) 
			throws Exception {
		if (flowLocalService != null) {
			return flowLocalService.handleFlow(session, this);
		}
		return doService(session, messageHandle);
	}

	public long getCreateTime() {
		return createTime;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	public String [] getParam() {
		return param;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getToUserName() {
		return toUserName;
	}

	public boolean isReply() {
		return reply;
	}

	public void setBasicWeixinMessage(WeixinMessage oldMessage) {
		this.setFromUserName(oldMessage.getToUserName());
		this.setToUserName(oldMessage.getFromUserName());
		this.setCreateTime(System.currentTimeMillis());
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public void setParam(String [] param) {
		this.param = param;
	}
	
	public void setReply(boolean reply) {
		this.reply = reply;
	}
	
	
	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}
	
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
