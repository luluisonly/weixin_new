package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;

/**
 * 
 * 测试用的
 *
 */
public class WeixinConfigUpdateHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		
		
	
		return SOAResponseMessage.SYSTEM_ERROR;
	}
	

}
