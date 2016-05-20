package openHandle;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.DebugUtil;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.open.WeixinSOALocalDispatch;

public class GetWeixinUserInfoHandle {

	public static JSONObject getUserInfo(String uname,String openid){
		SOARequestMessage request = new SOARequestMessage();
		request.setType("getWeixinUserInfo");
		request.setUname(uname);
		request.setObjectParam(openid);
		try {
			SOAResponseMessage response = WeixinSOALocalDispatch.doDispatch(request);
			return JSONObject.parseObject(response.getData().toString());
		} catch (Exception e) {
			DebugUtil.debug("getWeixinUserInfo failed");
		}
		return null;
	}
	
}
