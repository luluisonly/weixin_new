package com.bokesoft.thirdparty.attachment;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;

import com.bokesoft.myerp.common.ByteUtil;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.common.FileUtil;

public class LocalAttachmentManager implements AttachmentManager{
	
	private final String attachmentRoot = "attachment/";

	public void uploadAttachment(WeixinContext context, byte[] bytes, String filePath)
			throws Exception {
		FileUtil.writeBytesByCls(attachmentRoot+filePath, bytes, false);
	}

	public void uploadAttachment(WeixinContext context, FileItem fileItem, String filePath)
			throws Exception {
		InputStream input = fileItem.getInputStream();
		byte[] bytes = ByteUtil.getBytes(input);
		this.uploadAttachment(context, bytes, filePath);
	}

	public byte[] downloadAttachment(WeixinContext context, String filePath) throws Exception {
		return FileUtil.readBytesByCls(attachmentRoot+filePath);
	}

	public String[] listAttachment(WeixinContext context, String filePath)
			throws Exception {
		File file = FileUtil.readFileByCls(attachmentRoot+filePath);
		File [] files = file.listFiles();
		if (files != null) {
			String []fileNames = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				fileNames[i] = files[i].getName();
			}
			return fileNames;
		}
		return null;
	}

	public void removeAttachment(WeixinContext context,String filePath) throws Exception {
		FileUtil.deleteDirectoryOrFileByCls(attachmentRoot+filePath);
	}

	public void initialize(WeixinContext weixinContext) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void shoutdown(WeixinContext weixinContext) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
