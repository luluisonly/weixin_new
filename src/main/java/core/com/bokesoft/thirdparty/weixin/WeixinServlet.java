package com.bokesoft.thirdparty.weixin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.service.WeixinMessageService;
import com.bokesoft.thirdparty.weixin.service.WeixinMessageServiceImpl;

public class WeixinServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(WeixinServlet.class);

	private WeixinMessageService weixinMessageService = new WeixinMessageServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		WeixinContext context = WeixinContextFactory.getApplicationContext();
		if (weixinMessageService.validate(request,response,context)) {
			String echostr = request.getParameter("echostr");
			writer.write(echostr);
		} else {
			LOGGER.info("Request forbidden:" + request.getRemoteAddr());
			writer.write("REQUEST FORBIDDEN");
		}
		writer.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		WeixinContext context = WeixinContextFactory.getApplicationContext();
		String responseBody = weixinMessageService.handleMessage(context,request, response);
		writer.write(responseBody);
		writer.flush();
	}
	
	public void init() throws ServletException {
		try {
			WeixinContextFactory.initFactory();
		} catch (Throwable e) {
			LOGGER.error("WeixinContextFactory INIT FAILED");
			LOGGER.error(e);
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	public void destroy() {
		try {
			WeixinContextFactory.shutdownFactory();
		} catch (Throwable e) {
			LOGGER.error("WeixinContextFactory SHUTDOWN FAILED");
			LOGGER.error(e);
			e.printStackTrace();
		}
	}
}