package com.bokesoft.thirdparty.weixin.remote.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlow;

public class DefaultWeixinRemoteContext implements WeixinRemoteContext {

	private Map<String, WeixinMessageFlow> weixinMessageFlows = new HashMap<String, WeixinMessageFlow>();

	private List<WeixinRemoteMessageServiceFilter> weixinRemoteMessageServiceFilters = new ArrayList<WeixinRemoteMessageServiceFilter>();

	private Map<String, WeixinRemoteTextMessageCellHandle> weixinRemoteTextMessageCellHandles = new HashMap<String, WeixinRemoteTextMessageCellHandle>();

	public void addWeixinRemoteMessageServiceFilter(WeixinRemoteMessageServiceFilter filter) {
		synchronized (weixinRemoteMessageServiceFilters) {
			weixinRemoteMessageServiceFilters.add(filter);
		}
	}

	public WeixinMessageFlow getWeixinMessageFlow(String key) {
		return weixinMessageFlows.get(key);
	}

	public List<WeixinRemoteMessageServiceFilter> getWeixinRemoteMessageServiceFilters() {
		return weixinRemoteMessageServiceFilters;
	}

	public WeixinRemoteTextMessageCellHandle getWeixinRemoteTextMessageCellHandle(String key) {
		return weixinRemoteTextMessageCellHandles.get(key);
	}

	public void putWeixinMessageFlow(String key, WeixinMessageFlow weixinMessageFlow) {
		synchronized (weixinMessageFlows) {
			weixinMessageFlows.put(key, weixinMessageFlow);
		}
	}

	public void putWeixinRemoteTextMessageCellHandle(String key,
			WeixinRemoteTextMessageCellHandle weixinRemoteTextMessageCellHandle) {
		synchronized (weixinRemoteTextMessageCellHandles) {
			weixinRemoteTextMessageCellHandles.put(key, weixinRemoteTextMessageCellHandle);
		}
	}

	private WeixinRemoteMessageHandle weixinRemoteMessageHandle = new DefaultWeixinRemoteMessageHandle();
	
	public WeixinRemoteMessageHandle getWeixinRemoteMessageHandle() {
		return weixinRemoteMessageHandle;
	}

	public void setWeixinRemoteMessageHandle(WeixinRemoteMessageHandle weixinRemoteMessageHandle) {
		this.weixinRemoteMessageHandle = weixinRemoteMessageHandle;
	}
	
	public synchronized void init(){
		
		addWeixinRemoteMessageServiceFilter(new WeixinRemoteMessageFlowFilter());
	}

}
