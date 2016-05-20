package com.bokesoft.thirdparty.weixin.open.handle;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

public class BatchMoveUserHandle extends AbstractWeixinSOAServieHandle {
	

	@Override
	public SOAResponseMessage handle(WeixinContext context,
			SOARequestMessage message) throws Exception {
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if(publicNumber == null){
			return new SOAResponseMessage(1000, "unknow uname:"+uname);
		}
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String access_token = publicNumber.genAccess_token(context);
		String param = message.getStringParam();
		JSONObject json = JSONObject.parseObject(param);
		String openidList = json.getString("openidList");
		int toGroupid = json.getInteger("toGroupid");
		String result = invoker.batchMoveUser(access_token, openidList,toGroupid);
		JSONObject resultJson = JSONObject.parseObject(result);
		if (resultJson.getInteger("errcode") == 0) {
			return SOAResponseMessage.SUCCESS;
		} else {
			return new SOAResponseMessage(1000, resultJson.getString("errmsg"));
		}
	}

}
