package com.bokesoft.thirdparty.weixin.open.handle;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;

/**
 * 
 * 测试用的
 *
 */
public class TestWeixinHandle extends AbstractWeixinSOAServieHandle  {

	public static String KEY_NAME = null;
	
	public SOAResponseMessage handle(WeixinContext appContext, SOARequestMessage requestMessage)
			throws Exception {
		
		return SOAResponseMessage.SUCCESS;
	}
}
