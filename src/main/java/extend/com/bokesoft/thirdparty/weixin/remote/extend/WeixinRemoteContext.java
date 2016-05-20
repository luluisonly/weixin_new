package com.bokesoft.thirdparty.weixin.remote.extend;

import java.util.List;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlow;

public interface WeixinRemoteContext {

	public abstract WeixinMessageFlow getWeixinMessageFlow(String key);
	
	public abstract WeixinRemoteMessageHandle getWeixinRemoteMessageHandle();

	public abstract List<WeixinRemoteMessageServiceFilter> getWeixinRemoteMessageServiceFilters();
	
	public abstract WeixinRemoteTextMessageCellHandle getWeixinRemoteTextMessageCellHandle(String key);
	
	public abstract void putWeixinMessageFlow(String key,WeixinMessageFlow weixinMessageFlow);
	
	public abstract void putWeixinRemoteTextMessageCellHandle(String key,WeixinRemoteTextMessageCellHandle remoteTextMessageCellHandle);
	
	public abstract void setWeixinRemoteMessageHandle(WeixinRemoteMessageHandle weixinRemoteMessageHandle);
	
}
