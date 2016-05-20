<%@page import="com.bokesoft.thirdparty.model.config.ConfigUtil"%>
<%@page import="com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber"%>
<%@page import="com.bokesoft.thirdparty.weixin.bean.UserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	UserInfo userinfo = (UserInfo)request.getSession().getAttribute("userinfo");
	WeixinPublicNumber publicNumber = null;
	if(userinfo == null){
		response.sendRedirect("login.jsp");
		return ;
	}else{
		publicNumber = userinfo.getWeixinPublicNumber();	
	}
%>
	<!-- Bootstrap core CSS -->
	<link href="css/bootstrap.min.css" rel="stylesheet"/>
	<link href="css/google-bootstrap.css" rel="stylesheet"/>

	<style type="text/css">
		html {overflow:-moz-scrollbars-vertical;}
		html {min-height:101%;}
		.navbar-static-top { margin-bottom: 19px;}
	</style>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation" style="background-image: url('img/header-bg.png');height: 60px;margin-bottom: 0px;">
		<div class="container">
			<div class="navbar-header" style="margin-top: 6px;">
				<a class="navbar-brand" href="index.jsp"
					style="color: #444444; font-size: 20px; font-weight: bold;">微信服务管理平台[<%=ConfigUtil.getWeixinSystemVersion(publicNumber.getSystem_version())%>]
				</a>
			</div>
			<ul class="nav navbar-nav navbar-right" style="margin-top: 7px;">
				<li><a>欢迎您：<%=userinfo.getUname()%> <%=ConfigUtil.getWeixinPublicnumberStatus(publicNumber.getStatus())%></a></li>
				<li class="dropdown" id="navbar-system-manage"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown">系统管理 <b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="user-password-manage.jsp">密码管理</a></li>
						<li><a href="logout.jsp">注销</a></li>
						<li><a href="#">帮助</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
	<!-- Bootstrap core JavaScript  								-->
    <!-- ========================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script language="javascript" type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script language="javascript" type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/util.js"></script>