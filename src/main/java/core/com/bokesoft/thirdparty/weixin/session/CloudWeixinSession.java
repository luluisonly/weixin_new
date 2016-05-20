package com.bokesoft.thirdparty.weixin.session;

import java.io.Serializable;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.WeixinContextFactory;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalService;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteSessionImpl;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

/**
 * session 的实现类
 *
 */
public class CloudWeixinSession extends WeixinRemoteSessionImpl implements WeixinSession ,WeixinRmoteSession ,Serializable{

	private static final long serialVersionUID = -4931844479274826881L;

	private HashMap<String, Object> attributes = new HashMap<String, Object>();
	
	private String id = null;

	private WeixinMessageFlowLocalService weixinMessageFlowLocalService = null;

	@SuppressWarnings("unchecked")
	public CloudWeixinSession(String json){
		JSONObject object = JSONObject.parseObject(json);
		this.attributes = object.getObject("attributes", HashMap.class);
		this.id = object.getString("id");
		this.setOpenid(object.getString("openid"));
		this.setPublicName(object.getString("publicName"));
	}

	public CloudWeixinSession(String openid, String publicName, String sessionId) throws Exception {
		this.setOpenid(openid);
		this.setPublicName(publicName);
		this.id = sessionId;
	}
	
	public HashMap<String, Object> getAttributes() {
		return attributes;
	}

	public String getId() {
		return this.id;
	}

	public WeixinContext getWeixinContext() {
		return WeixinContextFactory.getApplicationContext();
	}

	public WeixinMessageFlowLocalService getWeixinMessageFlowLocalService() {
		return weixinMessageFlowLocalService;
	}

	public WeixinPublicNumber getWeixinPublicNumber() throws Exception {
		WeixinContext context = this.getWeixinContext();
		WeixinPublicNumber weixinPublicNumber = context.getWeixinPublicNumber(this.getPublicName());
		if (weixinPublicNumber == null) {
			weixinPublicNumber = context.getDefaultWeixinPublicNumber();
		}
		return weixinPublicNumber;
	}

	public void setWeixinMessageFlowLocalService(WeixinMessageFlowLocalService weixinMessageFlowLocalService) {
		this.weixinMessageFlowLocalService = weixinMessageFlowLocalService;
	}
	
}
