package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

/**
 * 
 * 更新远程图片消息
 *
 */
@Deprecated
public class WeixinRemoteImageUpdateHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		
		WeixinPublicNumber publicNumber = userinfo.getWeixinPublicNumber();
//		WeixinMessage message = context.buildWeixinMessage(request.getParameter("jsonobj"));
//		publicNumber.setWeixinRemoteImageMessage((WeixinRemoteImageMessage)message);
		context.updateWeixinPublicNumber( publicNumber);
		return SOAResponseMessage.SUCCESS;
	}
	

}
