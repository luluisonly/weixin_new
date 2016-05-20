package com.bokesoft.thirdparty.weixin.open.handle;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

/**
 * 
 * 通过网页授权的code获取微信用户openid
 * 
 */
public class GetOpenidByCode extends AbstractWeixinSOAServieHandle{

	private static final Logger LOGGER = Logger.getLogger(GetOpenidByCode.class);
	
	@Override
	public SOAResponseMessage handle(WeixinContext context,SOARequestMessage message) throws Exception {
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if(publicNumber == null){
			return new SOAResponseMessage(1000, "unknow uname:"+uname);
		}
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String code = message.getStringParam();
		long starttime = System.currentTimeMillis();
		String result = invoker.getOpenid(publicNumber.getApp_id(), publicNumber.getApp_secret(), code);
		long endtime = System.currentTimeMillis();
		LOGGER.info("invoke getOpenidByCode cost time:"+(endtime-starttime));
		JSONObject resultJson = JSONObject.parseObject(result);
		if(resultJson.get("errcode") == null){
			//此处只获取openid
			JSONObject json = new JSONObject();
			json.put("openid",resultJson.getString("openid"));
			//TODO 添加UnionID
			return new SOAResponseMessage(0, json.toString());
		}else{
			return new SOAResponseMessage(1000, resultJson.getString("errmsg"));
		}
	}


}
