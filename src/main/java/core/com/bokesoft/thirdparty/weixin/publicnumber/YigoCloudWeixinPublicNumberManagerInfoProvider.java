package com.bokesoft.thirdparty.weixin.publicnumber;

import java.util.HashMap;
import java.util.Map;

import com.bokesoft.myerp.soa.CloudLadder;
import com.bokesoft.myerp.soa.ICloudProvider;
import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumberManagerInfo;

public class YigoCloudWeixinPublicNumberManagerInfoProvider implements WeixinPublicNumberManagerInfoProvider,Initable{

	private ICloudProvider cloudProvider = null;
	
	@SuppressWarnings("rawtypes")
	public void initialize(WeixinContext context) throws Exception {
		cloudProvider = CloudLadder.getProvider();
		try {
			WeixinPublicNumberManagerInfo publicNumberManagerInfo = context.getWeixinPublicNumberManagerInfoFromLocal();
			Map map = (Map) cloudProvider.getOneRow("yigo_weixin_publicnumber_managerinfo", "admin");
			if (map == null || map.isEmpty()) {
				publicNumberManagerInfo.setManagerName("admin");
				publicNumberManagerInfo.setManagerPassword("e10adc3949ba59abbe56e057f20f883e");
				this.updateWeixinPublicNumberManagerInfo(context, publicNumberManagerInfo);
			}else{
				publicNumberManagerInfo.setManagerName(String.valueOf(map.get("id")));
				publicNumberManagerInfo.setManagerPassword(String.valueOf(map.get("password")));
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private WeixinPublicNumberManagerInfo getFromCloud(WeixinContext context,ICloudProvider provider) throws Exception{
		try {
			WeixinPublicNumberManagerInfo publicNumberManagerInfo = context.getWeixinPublicNumberManagerInfoFromLocal();
			Map map = (Map) cloudProvider.getOneRow("yigo_weixin_publicnumber_managerinfo", "admin");
			publicNumberManagerInfo.setManagerName(String.valueOf(map.get("id")));
			publicNumberManagerInfo.setManagerPassword(String.valueOf(map.get("password")));
			return publicNumberManagerInfo;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	public void shoutdown(WeixinContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public WeixinPublicNumberManagerInfo getWeixinPublicNumberManagerInfo(WeixinContext context)throws Exception {
		return getFromCloud(context, cloudProvider);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateWeixinPublicNumberManagerInfo(WeixinContext context,WeixinPublicNumberManagerInfo publicNumberManagerInfo)
			throws Exception {
		Map map = new HashMap();
		map.put("id", "admin");
		map.put("password", publicNumberManagerInfo.getManagerPassword());
		try {
			cloudProvider.setOneRow("yigo_weixin_publicnumber_managerinfo", "admin", map);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
}
