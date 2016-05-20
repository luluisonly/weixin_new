package com.bokesoft.thirdparty.attachment;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.myerp.common.intf.Factories;
import com.bokesoft.myerp.common.intf.IAttachmentMgr;
import com.bokesoft.myerp.common.midproxy.Env;
import com.bokesoft.myerp.common.midproxy.MAP_C_ENV;
import com.bokesoft.myerp.soa.CloudLadder;
import com.bokesoft.myerp.soa.ICloudProvider;
import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;

public class YigoCloudAttachmentManager implements AttachmentManager ,Initable{
	
	private IAttachmentMgr atg = null;
	
	private ICloudProvider cloudProvider = null;
	
	private Env env = null;
	
	private String dsn = null;
	
	private void appendFileName(WeixinContext context,String uname,String fileName) throws Exception{
		String fileNamesString = readFileNames(context, uname);
		if (StringUtil.isBlankOrNull(fileNamesString)) {
			writeFileNames(context, uname, fileName+";");
		}else{
			String [] fileNames = fileNamesString.split(";");
			for(String name:fileNames){
				if (name.equals(fileName)) {
					return ;
				}
			}
			writeFileNames(context, uname, fileNamesString+fileName+";");
		}
	}
	
	public byte[] downloadAttachment(WeixinContext context, String filePath) throws Exception {
		try {
			if (existFilePath(context, extractUname(filePath), dsn+ filePath)) {
				return atg.downloadFile(env, dsn+ filePath);
			}
			return null;
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
	
	private boolean existFilePath(WeixinContext context,String uname,String fileName) throws Exception{
		String [] fileNames = readFileNamesArray(context, uname);
		if (fileNames == null) {
			return false;
		}
		for(String name:fileNames){
			if(name.equals(fileName)){
				return true;
			}
		}
		return false;
	}
	
	private String extractUname(String filePath) throws Exception{
		int index = filePath.indexOf("/");
		if (index == -1) {
			throw new  Exception("file path did not contain uname info.");
		}
		return filePath.substring(0,index);
	}
	
	public String[] listAttachment(WeixinContext context, String uname)throws Exception {
		return readFileNamesArray(context, uname);
	}
	
	@SuppressWarnings("unchecked")
	private String readFileNames(WeixinContext context,String uname) throws Exception{
		try {
			Map<String,Object> dataMap = (Map<String, Object>)this.cloudProvider.getOneRow("yigo_weixin_publicnumber_filepath", uname);
			return  (String)dataMap.get("filePath");
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
	
	private String [] readFileNamesArray(WeixinContext context,String uname) throws Exception{
		String fileNames = readFileNames(context, uname);
		if (StringUtil.isBlankOrNull(fileNames)) {
			return null;
		}
		return fileNames.split(";");
	}
	

	public void removeAttachment(WeixinContext context,String filePath) throws Exception {
		removeFilePath(context, extractUname(filePath), dsn+ filePath);
	}

	private void removeFilePath(WeixinContext context,String uname,String filePath) throws Exception{
		String filePaths = readFileNames(context, uname);
		if (!StringUtil.isBlankOrNull(filePaths)) {
			filePaths = filePaths.replace(filePath+";", "");
			writeFileNames(context, uname, filePaths);
		}
	}

	public void uploadAttachment(WeixinContext context, byte[] bytes, String filePath)
			throws Exception {
		try {
			atg.uploadFile(env, bytes, filePath);
			appendFileName(context, extractUname(filePath), dsn+ filePath);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}

	public void uploadAttachment(WeixinContext context, FileItem fileItem, String filePath)
			throws Exception {
		try {
			atg.uploadFile(env, fileItem,dsn+filePath);
			appendFileName(context, extractUname(filePath), dsn+ filePath);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void writeFileNames(WeixinContext context,String uname,String fileNames) throws Exception{
		try {
			Map map = new HashMap();
			map.put("filePath", fileNames);
			map.put("id", uname);
			this.cloudProvider.setOneRow("yigo_weixin_publicnumber_filepath", uname, map);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}

	public void initialize(WeixinContext weixinContext) throws Exception {
		atg = Factories.createAttachmentMgr(null);
		cloudProvider = CloudLadder.getProvider();
		env = MAP_C_ENV.createEnv(MAP_C_ENV.getDefaultDSN());
		dsn = "/DSN/"+MAP_C_ENV.getDefaultDSN()+"/";
	}

	public void shoutdown(WeixinContext weixinContext) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
