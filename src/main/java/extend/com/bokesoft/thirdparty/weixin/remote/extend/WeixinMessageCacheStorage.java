package com.bokesoft.thirdparty.weixin.remote.extend;

import java.util.HashMap;
import java.util.Map;

public class WeixinMessageCacheStorage {

	private static Map<String, WeixinRmoteSession> weixinMessageCacheMap = new HashMap<String, WeixinRmoteSession>();
	
	
	public static WeixinRmoteSession getWeixinMessageCache(String key){
		return weixinMessageCacheMap.get(key);
	}
	
	public static void putWeixinMessageCache(String key,WeixinRmoteSession cache){
		synchronized (weixinMessageCacheMap) {
			weixinMessageCacheMap.put(key, cache);
		}
	}
	
	public static void removeWeixinMessageCache(String key){
		synchronized (weixinMessageCacheMap) {
			weixinMessageCacheMap.remove(key);
		}
	}
	
}
