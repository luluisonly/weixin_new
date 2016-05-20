package com.bokesoft.thirdparty.weixin.open;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.WeixinContextFactory;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.zaofans.weixin.common.PayUtils;

/**
 * 提供HTTP方式的API
 *
 */
public class WeixinSOAServletDispatch extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(WeixinSOALocalDispatch.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long starttime = System.currentTimeMillis();
		WeixinContext context = WeixinContextFactory.getApplicationContext();
		WeixinSOAService weixinSOAService = context.getWeixinSOAService();
		SOAResponseMessage responseMessage = null;
		try {
			SOARequestMessage requestMessage = parseSOARequestMessage(request);
			responseMessage = weixinSOAService.doService(context, requestMessage);
		} catch (Throwable e) {
			LOGGER.error(e);
			responseMessage = new SOAResponseMessage(1000,e.getMessage());
			doPrint(response, responseMessage.toString());
			return;
		}
		if(responseMessage.getCode() == SOAResponseMessage.STREAM_CODE){
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
		long endtime = System.currentTimeMillis();
		LOGGER.info("dispose this request cost:"+(endtime-starttime));
				
	}
	
	private void doPrint(HttpServletResponse response,String content) throws IOException{
		response.setContentType("text/plain; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(content);
		writer.flush();
	}
	
	private SOARequestMessage parseSOARequestMessage(HttpServletRequest request) throws Exception {
		String uname = request.getParameter(SOARequestMessage.UNAME);
		String param = request.getParameter(SOARequestMessage.PARAM);
		String type  = request.getParameter(SOARequestMessage.TYPE);
		String username = request.getParameter(SOARequestMessage.USERNAME);
		String password = request.getParameter(SOARequestMessage.PASSWORD);
		SOARequestMessage requestMessage = new SOARequestMessage(param, type,
				uname, username, password);
		requestMessage.setWeixin(PayUtils.isWeiXin(request));
		return requestMessage;
	}

}