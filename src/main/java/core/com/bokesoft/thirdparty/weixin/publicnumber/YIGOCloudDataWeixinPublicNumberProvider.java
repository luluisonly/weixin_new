package com.bokesoft.thirdparty.weixin.publicnumber;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.soa.CloudLadder;
import com.bokesoft.myerp.soa.ICloudProvider;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

/**
 * 
 * 从YIGO云存储获取微信公众号信息
 *
 */
public class YIGOCloudDataWeixinPublicNumberProvider implements WeixinPublicNumberProvider{
	
	private ICloudProvider cloudProvider = null;
	
	@SuppressWarnings("unchecked")
	public WeixinPublicNumber getWeixinPublicNumber(WeixinContext context, String uname) {
		try {
			Map<String,Object> versionMap = (Map<String, Object>)this.cloudProvider.getOneRow("yigo_weixin_publicnumber_version", uname);
			Long v = Long.parseLong((String) versionMap.get("weixin_v"));
			if (v==null) {
				context.removeWeixinPublicNumber(uname);
				return context.getDefaultWeixinPublicNumber();
			}
			WeixinPublicNumber publicNumber = context.getWeixinPublicNumberFromLocal(uname);
			if (publicNumber != null && v == publicNumber.getVersion()) {
				return publicNumber;
			}
			Map<String,Object> dataMap = (Map<String, Object>)this.cloudProvider.getOneRow("yigo_weixin_publicnumber_data", uname);
			String json = (String) dataMap.get("weixin_d");
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
	
	public void shoutdown(WeixinContext context) throws Exception {
		// TODO Auto-generated method stub
	}

	public void initialize(WeixinContext context) throws Exception {
		cloudProvider = CloudLadder.getProvider();
	}

	public void updateWeixinPublicNumber(WeixinContext context, WeixinPublicNumber publicNumber) throws Exception{
		String uname = publicNumber.getUname();
		Map<String, Object> version = new LinkedHashMap<String, Object>();
		version.put("id",uname);
		version.put("weixin_v",String.valueOf(System.currentTimeMillis()));
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("id", uname);
		data.put("weixin_d", publicNumber.toString(true));
		try {
			this.cloudProvider.setOneRow("yigo_weixin_publicnumber_version",null, version);
			this.cloudProvider.setOneRow("yigo_weixin_publicnumber_data",null, data);
		} catch (Throwable e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void removeWeixinPublicNumber(WeixinContext context, String uname)throws Exception {
		//FIXME complete me
		throw new Exception("complete me please");
	}

	public void addWeixinPublicNumber(WeixinContext context,WeixinPublicNumber publicNumber) throws Exception {
		updateWeixinPublicNumber(context, publicNumber);
	}
	
}
