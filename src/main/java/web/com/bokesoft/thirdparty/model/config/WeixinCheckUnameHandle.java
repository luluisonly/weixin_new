package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

/**
 * 
 * 验证用户名是否已存在
 *
 */
public class WeixinCheckUnameHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber4Edit(request.getParameter("username"));
		if(publicNumber!=null){
			return new SOAResponseMessage(199, "该用户名已存在");
		}
		return SOAResponseMessage.SUCCESS;
	}
	

}
