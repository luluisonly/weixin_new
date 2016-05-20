package com.bokesoft.thirdparty.model;

import javax.servlet.http.HttpServletRequest;

import com.bokesoft.thirdparty.attachment.AttachmentManager;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

/**
 * 
 * 测试用的
 *
 */
public class DeleteImageHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		WeixinPublicNumber publicNumber = userinfo.getWeixinPublicNumber();
		String fileName = request.getParameter("fileName");
		String filePath = publicNumber.getUname()+"/"+fileName;
		AttachmentManager attachmentManager = context.getAttachmentManager();
		attachmentManager.removeAttachment(context, filePath);
		return SOAResponseMessage.SUCCESS;
		
	}
	
}
