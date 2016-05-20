package com.bokesoft.thirdparty.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;

import com.bokesoft.thirdparty.model.config.DownloadImageHandle;
import com.bokesoft.thirdparty.model.config.UploadImageHandle;
import com.bokesoft.thirdparty.model.config.WeixinAccessTokenRefreshHandle;
import com.bokesoft.thirdparty.model.config.WeixinAdministratorManagement;
import com.bokesoft.thirdparty.model.config.WeixinAppidUpdateHandle;
import com.bokesoft.thirdparty.model.config.WeixinCareReplyReadHandle;
import com.bokesoft.thirdparty.model.config.WeixinCareReplyUpdateHandle;
import com.bokesoft.thirdparty.model.config.WeixinCheckUnameHandle;
import com.bokesoft.thirdparty.model.config.WeixinDefaultReplyReadHandle;
import com.bokesoft.thirdparty.model.config.WeixinDefaultReplyUpdateHandle;
import com.bokesoft.thirdparty.model.config.WeixinKeywordKeysReadHandle;
import com.bokesoft.thirdparty.model.config.WeixinKeywordReplyReadHandle;
import com.bokesoft.thirdparty.model.config.WeixinKeywordUpdateHandle;
import com.bokesoft.thirdparty.model.config.WeixinMenuCreateHandle;
import com.bokesoft.thirdparty.model.config.WeixinMenuReadHandle;
import com.bokesoft.thirdparty.model.config.WeixinMenuUpdateHandle;
import com.bokesoft.thirdparty.model.config.WeixinPicListReadHandle;
import com.bokesoft.thirdparty.model.config.WeixinPublicnumberManagement;
import com.bokesoft.thirdparty.model.config.WeixinPublicnumberManagerHandle;
import com.bokesoft.thirdparty.model.config.WeixinPublicnumbersReadHandle;
import com.bokesoft.thirdparty.model.config.WeixinRemoteLocationReadHandle;
import com.bokesoft.thirdparty.model.config.WeixinRemoteLocationUpdateHandle;
import com.bokesoft.thirdparty.model.config.WeixinRemoteMessageEncryptUpdateHandle;
import com.bokesoft.thirdparty.model.config.WeixinRemoteTextReadHandle;
import com.bokesoft.thirdparty.model.config.WeixinRemoteTextUpdateHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.WeixinContextFactory;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;

public class WeixinModelServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, ModelHandle> modelHandles = new HashMap<String, ModelHandle>();
	
	private SOAResponseMessage handle_not_found = new SOAResponseMessage(1000, "model handle not found");
	
	private SOAResponseMessage not_login = new SOAResponseMessage(1000, "not login");
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SOAResponseMessage responseMessage = null;
		WeixinContext context = WeixinContextFactory.getApplicationContext();
		ModelHandle handle = modelHandles.get(request.getParameter("type"));
		if (handle == null) {
			responseMessage = handle_not_found;
		}else{
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute("userinfo");
			if (handle.needLogin && userinfo == null) {
				responseMessage = not_login;
			}else{
				 try {
					responseMessage = handle.handle(context, request, userinfo);
				} catch (Throwable e) {
					e.printStackTrace();
					responseMessage = new SOAResponseMessage(-1, e.getMessage());
					doPrint(response, responseMessage.toString());
					return ;
				}
			}
		}
		
		if(responseMessage.getCode() == SOAResponseMessage.STREAM_CODE){
			response.setCharacterEncoding("UTF-8");
			Header[] headers = responseMessage.getHeaders();
			if (headers != null) {
				for(Header header:headers){
					response.setHeader(header.getName(), header.getValue());
				}
			}
			OutputStream os = response.getOutputStream();
			byte [] bytes = responseMessage.getBytes();
			if(bytes != null){
				os.write(bytes);
				os.flush();
			}
		}else{
			doPrint(response, responseMessage.toString());
		}
	}
	
	private void doPrint(HttpServletResponse response,String content) throws IOException{
		response.setContentType("text/plain; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(content);
		writer.flush();
	}
	
	public void init(ServletConfig config) throws ServletException {
		ModelHandle handle = new LoginHandle();
		handle.needLogin = false;
		modelHandles.put("login", handle);
		
		handle = new LogoutHandle();
		handle.needLogin = true;
		modelHandles.put("logout", handle);
		
		handle = new RegisterHandle();
		handle.needLogin = false;
		modelHandles.put("register", handle);
		
		handle = new WeixinCheckUnameHandle();
		handle.needLogin = false;
		modelHandles.put("checkuname", handle);
		
		handle = new ManagerLoginHandle();
		handle.needLogin = false;
		modelHandles.put("manager-login", handle);
		
		handle = new WeixinPublicnumbersReadHandle();
		handle.needLogin = true;
		modelHandles.put("wx-get-publicnumbers", handle);
		
		handle = new WeixinAccessTokenRefreshHandle();
		handle.needLogin=true;
		modelHandles.put("wx-refresh-accesstoken", handle);
		
		handle = new WeixinPublicnumberManagerHandle();
		handle.needLogin = false;
		modelHandles.put("wx-manager", handle);
		
		handle = new DownloadImageHandle();
		handle.needLogin = false;
		modelHandles.put("wx-download-image", handle);
		
		handle = new WeixinMenuReadHandle();
		handle.needLogin = true;
		modelHandles.put("wx-read-menu", handle);
		
		handle = new WeixinMenuCreateHandle();
		handle.needLogin = true;
		modelHandles.put("wx-create-menu", handle);
		
		handle = new WeixinMenuUpdateHandle();
		handle.needLogin = true;
		modelHandles.put("wx-update-menu", handle);
		
		handle = new WeixinDefaultReplyUpdateHandle();
		handle.needLogin = true;
		modelHandles.put("wx-update-default-reply", handle);
		
		handle = new WeixinCareReplyUpdateHandle();
		handle.needLogin = true;
		modelHandles.put("wx-update-care-reply", handle);
		
		handle = new WeixinKeywordUpdateHandle();
		handle.needLogin = true;
		modelHandles.put("wx-update-keyword-reply", handle);
		
		handle = new WeixinAppidUpdateHandle();
		handle.needLogin = true;
		modelHandles.put("wx-update-appid", handle);
		handle = new WeixinRemoteTextUpdateHandle();
		handle.needLogin = true;
		modelHandles.put("wx-update-remote-text", handle);
		
		handle = new WeixinRemoteLocationUpdateHandle();
		handle.needLogin = true;
		modelHandles.put("wx-update-remote-location", handle);
		
		handle = new WeixinAdministratorManagement();
		handle.needLogin = false;
		modelHandles.put("admin-manage", handle);
		
		handle = new WeixinPublicnumberManagement();
		handle.needLogin = true;
		modelHandles.put("password-manage", handle);
		
		handle = new WeixinPicListReadHandle();
		handle.needLogin = true;
		modelHandles.put("wx-get-pic-list", handle);
		
		handle = new WeixinDefaultReplyReadHandle();
		handle.needLogin = true;
		modelHandles.put("wx-read-defaultReply", handle);
		
		handle = new WeixinKeywordKeysReadHandle();
		handle.needLogin = true;
		modelHandles.put("wx-read-keyword-keys", handle);
		
		
		handle = new WeixinKeywordReplyReadHandle();
		handle.needLogin=true;
		modelHandles.put("wx-read-keywordReply", handle);
		
		handle = new WeixinCareReplyReadHandle();
		handle.needLogin=true;
		modelHandles.put("wx-read-careReply", handle);
		
		handle = new WeixinRemoteTextReadHandle();
		handle.needLogin=true;
		modelHandles.put("wx-read-remote-text", handle);
		
		handle = new WeixinRemoteLocationReadHandle();
		handle.needLogin=true;
		modelHandles.put("wx-read-remote-location", handle);
		
		handle = new UploadImageHandle();
		handle.needLogin=true;
		modelHandles.put("wx-upload-image", handle);
		
		handle = new DeleteImageHandle();
		handle.needLogin=true;
		modelHandles.put("wx-delete-image", handle);
		
		handle = new WeixinRemoteMessageEncryptUpdateHandle();
		handle.needLogin=true;
		modelHandles.put("wx-remote-message-encrypt-update", handle);
		
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
}