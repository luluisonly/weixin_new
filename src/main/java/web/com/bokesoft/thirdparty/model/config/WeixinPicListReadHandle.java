package com.bokesoft.thirdparty.model.config;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.attachment.AttachmentManager;
import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;

/**
 * 
 * 读取公众号图片文件列表
 *
 */

public class WeixinPicListReadHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		AttachmentManager attachmentManager = context.getAttachmentManager();
		String[] filenames = attachmentManager.listAttachment(context, userinfo.getUname());
		return new SOAResponseMessage(0, null,filenames);
	}

}
