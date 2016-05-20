package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

/**
 * 
 * 更新微信自定义菜单
 *
 */
public class WeixinMenuUpdateHandle extends ModelHandle  {
	
	private static final Logger logger = Logger.getLogger(WeixinMenuUpdateHandle.class);

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		String menu = request.getParameter("menu");
		WeixinPublicNumber publicNumber = userinfo.getWeixinPublicNumber(); 
		publicNumber.setWeixinMenu(menu);
		context.updateWeixinPublicNumber( publicNumber);
		logger.info("UPDATE MENU"+menu);
		return SOAResponseMessage.SUCCESS;
	}

}
