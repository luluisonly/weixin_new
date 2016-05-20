package com.bokesoft.thirdparty.weixin.open;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
/**
 *  控制服务处理接口
 *
 */
public interface WeixinSOAService {

	/**
	 * 处理服务
	 * @param context
	 * @param requestMessage
	 * @return
	 * @throws Exception
	 */
	public SOAResponseMessage doService(WeixinContext context,SOARequestMessage requestMessage)throws Exception;
	
	
}
