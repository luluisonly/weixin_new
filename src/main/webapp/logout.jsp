<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
		request.getSession().setAttribute("userinfo", null);
		response.sendRedirect("login.jsp");
%>