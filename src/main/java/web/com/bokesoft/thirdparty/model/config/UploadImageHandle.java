package com.bokesoft.thirdparty.model.config;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bokesoft.thirdparty.attachment.AttachmentManager;
import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;

/**
 * 
 * 测试用的
 *
 */
public class UploadImageHandle extends ModelHandle  {

	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		AttachmentManager attachmentManager = context.getAttachmentManager();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024*1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		upload.setFileSizeMax(3*1024*1024);
		List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
		for(FileItem item : list){
			if(!item.isFormField()){
				String value = item.getName();
				value = URLDecoder.decode(value, "UTF-8");
				int start = value.lastIndexOf("\\");
				String fileName = value.substring(start+1);
				attachmentManager.uploadAttachment(context, item, userinfo.getUname()+"/"+fileName);
			}
		}
		return SOAResponseMessage.SUCCESS;
		
	}
	
}
