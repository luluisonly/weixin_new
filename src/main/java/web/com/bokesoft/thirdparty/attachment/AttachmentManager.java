package com.bokesoft.thirdparty.attachment;

import org.apache.commons.fileupload.FileItem;

import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;

public interface AttachmentManager extends Initable{

	/**
	 * 上传文件
	 * @param context
	 * @param bytes
	 * @param filePath
	 * @throws Exception
	 */
	public void uploadAttachment(WeixinContext context, byte[] bytes, String filePath) throws Exception;
	
	/**
	 * 上传文件
	 * @param context
	 * @param fileItem
	 * @param filePath
	 * @throws Exception
	 */
	public void uploadAttachment(WeixinContext context, FileItem fileItem, String filePath) throws Exception;
	/**
	 * 向客户端写文件
	 * @param context
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public byte[] downloadAttachment(WeixinContext context, String filePath) throws Exception;
	
	/**
	 * 删除附件
	 * @param context
	 * @param fileMainName
	 */
	public void removeAttachment(WeixinContext context, String fileName) throws Exception;
	
	/**
	 * 列出目录下文件名
	 * @param context
	 * @param fileMainName
	 */
	public String[] listAttachment(WeixinContext context, String filePath) throws Exception;
	
}
