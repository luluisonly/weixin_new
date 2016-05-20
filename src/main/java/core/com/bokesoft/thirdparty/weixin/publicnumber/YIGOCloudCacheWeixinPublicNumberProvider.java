package com.bokesoft.thirdparty.weixin.publicnumber;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.soa.CloudLadder;
import com.bokesoft.myerp.soa.ICloudProvider;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumberCacheVersion;

/**
 * 
 * 从YIGO云存储获取微信公众号信息
 *
 */
@Deprecated
public class YIGOCloudCacheWeixinPublicNumberProvider implements WeixinPublicNumberProvider{
	
	private ICloudProvider cloudProvider = null;
	
	public WeixinPublicNumber getWeixinPublicNumber(WeixinContext context, String uname) {
		WeixinPublicNumberCacheVersion cacheVersion = this.getWeixinPublicNumberCacheVersion(uname);
		try {
			Integer v = (Integer) this.cloudProvider.getCache(cacheVersion.getVkey());
			if (v == null) {
				context.removeWeixinPublicNumber(uname);
				return context.getDefaultWeixinPublicNumber();
			}
			WeixinPublicNumber publicNumber = context.getWeixinPublicNumberFromLocal(uname);
			if (publicNumber != null && v == publicNumber.getVersion()) {
				return publicNumber;
			}
			String json = (String) this.cloudProvider.getCache(cacheVersion.getCname());
			if (json != null) {
				context.getWeixinPublicNumberUpdater().updateAllProperties(context, JSONObject.parseObject(json));
				publicNumber = context.getWeixinPublicNumberFromLocal(uname);
				publicNumber.setVersion(v);
				return publicNumber;
			}else{
				context.removeWeixinPublicNumber(uname);
				return null;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Map<String, WeixinPublicNumberCacheVersion> cacheVersions = new HashMap<String, WeixinPublicNumberCacheVersion>();
	
	public WeixinPublicNumberCacheVersion getWeixinPublicNumberCacheVersion(String uname) {
		WeixinPublicNumberCacheVersion cacheVersion = this.cacheVersions.get(uname);
		if (cacheVersion == null) {
			synchronized (this.cacheVersions) {
				cacheVersion = this.cacheVersions.get(uname);
				if (cacheVersion == null) {
					cacheVersion = WeixinPublicNumberCacheVersion.newInstance(uname);
					this.cacheVersions
							.put(uname, WeixinPublicNumberCacheVersion.newInstance(uname));
				}
			}
		}
		return cacheVersion;
	}
	
	public void shoutdown(WeixinContext context) throws Exception {
		// TODO Auto-generated method stub
	}

	public void initialize(WeixinContext context) throws Exception {
		cloudProvider = CloudLadder.getProvider();
	}

	public void updateWeixinPublicNumber(WeixinContext context, WeixinPublicNumber publicNumber) throws Exception {
		//FIXME complete me
		throw new Exception("complete me please");
	}

	public void removeWeixinPublicNumber(WeixinContext context, String uname)
			throws Exception {
		//FIXME complete me
		throw new Exception("complete me please");
	}

	@Override
	public void addWeixinPublicNumber(WeixinContext context,WeixinPublicNumber publicNumber) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
