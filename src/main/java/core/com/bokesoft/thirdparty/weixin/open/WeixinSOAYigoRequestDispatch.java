package com.bokesoft.thirdparty.weixin.open;

import com.bokesoft.myerp.common.intf.IContext;
import com.bokesoft.myerp.common.intf.IDispatchImpl;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.WeixinContextFactory;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;

/**
 * YIGO调用微信SOA入口
 */
public class WeixinSOAYigoRequestDispatch implements IDispatchImpl {
	
	private static SOAResponseMessage NOT_ENOUGH_PARAM = new SOAResponseMessage(1000,"not enough param!");

	public Object dispatch(final IContext context, String className,String methodName, Object[] params) throws Throwable {
		WeixinContext weixinContext = WeixinContextFactory.getApplicationContext();
		WeixinSOAService weixinSOAService = weixinContext.getWeixinSOAService();
		String result = null;
		try {
			if (params.length != 5) {
				return NOT_ENOUGH_PARAM.toString();
			}
			
			SOARequestMessage requestMessage = new SOARequestMessage((String)params[2], (String)params[0], (String)params[1]
					,(String)params[3],(String)params[4]);
			
			result = weixinSOAService.doService(weixinContext, requestMessage).toString();
		} catch (Throwable e) {
			e.printStackTrace();
			result = new SOAResponseMessage(1000,e.getMessage()).toString();
		}
		return result;
	}
	
	

}
