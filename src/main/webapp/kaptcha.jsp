<%@page import="com.bokesoft.thirdparty.weixin.common.Kaptcha"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	response.setHeader("Content-Type", "image/JPG");
	String imgcode = Kaptcha.randomCode(5);
	request.getSession().setAttribute("verify_code", imgcode);
	Kaptcha.writeImage2OutputStream(Kaptcha.generate(imgcode, 200, 50,40), response.getOutputStream());
	response.getOutputStream().flush();
	out.clear();
	pageContext.pushBody();
%>