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
 * 更新关键词自动回复
 *
 */
public class WeixinKeywordUpdateHandle extends ModelHandle  {
	
	private static final Logger logger = Logger.getLogger(WeixinKeywordUpdateHandle.class);

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		
		WeixinPublicNumber publicNumber = userinfo.getWeixinPublicNumber();
		String keyword = request.getParameter("keyword");
		String action = request.getParameter("action");
		if(action==null){
			return SOAResponseMessage.SYSTEM_ERROR;
		}else if(action.equals("delete")){
			publicNumber.removeEditKeywordsReplyMessage(keyword);
			context.updateWeixinPublicNumber( publicNumber);
			return SOAResponseMessage.SUCCESS;
		}else if(action.equals("update")){
			String oldkeyword = request.getParameter("oldkeyword");
			publicNumber.removeEditKeywordsReplyMessage(oldkeyword);
			
		}
		WeixinMessage message = context.buildWeixinMessage(request.getParameter("jsonobj"));
		publicNumber.putEditKeywordsReplyMessage(keyword, message);
		context.updateWeixinPublicNumber( publicNumber);
		logger.info("UPDATE MESSAGE:"+message.toString());
		return SOAResponseMessage.SUCCESS;
	}
	

}
