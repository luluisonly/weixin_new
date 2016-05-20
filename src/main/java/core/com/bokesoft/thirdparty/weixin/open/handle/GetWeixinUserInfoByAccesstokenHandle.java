package com.bokesoft.thirdparty.weixin.open.handle;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

/**
 * 网页授权获取用户基本信息，暂时不会使用这种方式，因为我们用户信息在关注时已经推送
 * 
 * @author dongze
 *
 */
public class GetWeixinUserInfoByAccesstokenHandle extends
		AbstractWeixinSOAServieHandle {

	@Override
	public SOAResponseMessage handle(WeixinContext context,
			SOARequestMessage message) throws Exception {
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if (publicNumber == null) {
			return new SOAResponseMessage(1000, "unknow uname:" + uname);
		}
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		JSONObject paramJson = JSONObject.parseObject(message.getStringParam());
		String access_token = paramJson.getString("access_token");
		String openid = paramJson.getString("openid");
		String result = invoker.getUserInfoByAccesstoken(access_token, openid);
		JSONObject resultJson = JSONObject.parseObject(result);
		if (resultJson.getString("errcode") == null) {
			return new SOAResponseMessage(0, resultJson.toString());
		} else {
			return new SOAResponseMessage(1000, resultJson.getString("errmsg"));
		}
	}

}
