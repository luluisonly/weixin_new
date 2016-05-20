package com.bokesoft.thirdparty.model.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

public class WeixinKeywordReplyReadHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		Map<String, WeixinMessage> keywordreply = new HashMap<String, WeixinMessage>() ;
		Map<String,WeixinMessage> keywords = userinfo.getWeixinPublicNumber().getEditKeywordsReplyMessage();
		Set<String> s = keywords.keySet();
		Iterator<String> i = s.iterator();
		while(i.hasNext()){
			String key = i.next();
			if(keywords.get(key).getMsgType()!="remote_text") keywordreply.put(key, keywords.get(key));
		}
		return new SOAResponseMessage(0,null,keywordreply);
	}

}
