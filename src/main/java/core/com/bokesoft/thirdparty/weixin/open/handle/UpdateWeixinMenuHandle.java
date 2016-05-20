package com.bokesoft.thirdparty.weixin.open.handle;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinApiResult;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

/**
 * 
 * 更新微信菜单
 *
 */
public class UpdateWeixinMenuHandle extends AbstractWeixinSOAServieHandle  {
	
	public static String KEY_NAME = null;
	
	public SOAResponseMessage handle(WeixinContext context, SOARequestMessage requestMessage)
			throws Exception {
		String uname = requestMessage.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if (publicNumber == null) {
			return new SOAResponseMessage(1000,"unknow uname:"+uname);
		}
		WeixinApiResult result = context.getWeixinApiInvoker().createWeixinMenu(publicNumber.genAccess_token(context)
				,publicNumber.getWeixinMenu());
		if (result.getErrcode() == 0) {
			return SOAResponseMessage.SUCCESS;
		}else{
			return new SOAResponseMessage(1000, result.getErrmsg());
		}
			
	}

}
