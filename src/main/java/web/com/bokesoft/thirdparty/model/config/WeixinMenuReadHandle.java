package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;

/**
 * 
 * 读取微信自定义菜单
 *
 */

public class WeixinMenuReadHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		return new SOAResponseMessage(0, null, userinfo.getWeixinPublicNumber().getWeixinMenu());
	}

}
