package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;

/**
 * 
 * 更新默认自动回复
 *
 */
public class WeixinDefaultReplyUpdateHandle extends ModelHandle  {

	private static final Logger logger = Logger.getLogger(WeixinDefaultReplyUpdateHandle.class);
	
	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		
		WeixinPublicNumber publicNumber = userinfo.getWeixinPublicNumber();
		WeixinMessage message = context.buildWeixinMessage(request.getParameter("jsonobj"));
		publicNumber.setDefaultReplyMessage(message);
		context.updateWeixinPublicNumber( publicNumber);
		logger.info("UPDATE MESSAGE:"+message.toString());
		return SOAResponseMessage.SUCCESS;
	}
	

}
