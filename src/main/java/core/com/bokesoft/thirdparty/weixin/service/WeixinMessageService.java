package com.bokesoft.thirdparty.weixin.service;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.session.WeixinSession;

/**
 * 接收微信用户消息入口
 *
 */
public interface WeixinMessageService {

	/**
	 * 进行过滤处理
	 * @param session
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public abstract WeixinMessage doFilter(WeixinSession session, WeixinMessage message)
			throws Exception;

	/**
	 * 处理接收到的消息
	 * 
	 * @param context
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract String handleMessage(WeixinContext context,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 
	 * @param context
	 * @param session
	 * @param message
	 * @return
	 */
	public abstract WeixinMessage handleMessage(WeixinContext context,WeixinSession session, WeixinMessage oldMessage)throws Exception;

	/**
	 * 读取消息文本
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public abstract String readStreamParameter(ServletInputStream inputStream) throws IOException;
	
	/**
	 * 验证是否是微信客户端发送来的消息
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public abstract boolean validate(HttpServletRequest request,HttpServletResponse response,WeixinContext context);

}
