package com.bokesoft.thirdparty.model.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;

/**
 * 
 * 读取关键词回复
 *
 */

public class WeixinKeywordKeysReadHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		Map<String,WeixinMessage> keywords = userinfo.getWeixinPublicNumber().getKeywordsReplyMessage();
		return new SOAResponseMessage(0,null,keywords.keySet().toArray(new String[0]));
	}

}
