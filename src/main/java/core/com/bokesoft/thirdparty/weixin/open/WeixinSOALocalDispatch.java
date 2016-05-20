package com.bokesoft.thirdparty.weixin.open;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.WeixinContextFactory;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;

/**
 * 提供Local方式的API
 *
 */
public class WeixinSOALocalDispatch{

	public static SOAResponseMessage doDispatch(SOARequestMessage requestMessage) throws Exception{
		WeixinContext context = WeixinContextFactory.getApplicationContext();
		WeixinSOAService weixinSOAService = context.getWeixinSOAService();
		return weixinSOAService.doService(context, requestMessage);
	}

}