package com.bokesoft.thirdparty.model;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;

public abstract class ModelHandle {
	
	boolean needLogin = true;

	public abstract SOAResponseMessage handle(WeixinContext context, HttpServletRequest request
			,UserInfo userinfo) throws Exception;
	
}
