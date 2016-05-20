package com.bokesoft.thirdparty.weixin.open.handle;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

/**
 * 
 * 获取累计用户数据
 *
 */
public class GetUserCumulateHandle extends AbstractWeixinSOAServieHandle  {
	
	public SOAResponseMessage handle(WeixinContext context, SOARequestMessage requestMessage)
			throws Exception {
		String uname = requestMessage.getUname();
		WeixinPublicNumber weixinPublicNumber = context.getWeixinPublicNumber(uname);
		if (weixinPublicNumber == null) {
			return new SOAResponseMessage(1000,"unknow uname:"+uname);
		}
		String access_token = weixinPublicNumber.genAccess_token(context);
		String dateInfo = requestMessage.getStringParam();
		WeixinApiInvoker apiInvoker = context.getWeixinApiInvoker();
		String result = apiInvoker.getStatisticsData(access_token, "getUserCumulate", dateInfo);
		JSONObject jsonObject = JSONObject.parseObject(result);
		if(jsonObject.get("errcode") == null){
			return new SOAResponseMessage(0, null, jsonObject);
		}else{
			return new SOAResponseMessage(1000, jsonObject.getString("errmsg"));
		}
	}
}
