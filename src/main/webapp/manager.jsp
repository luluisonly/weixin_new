<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%	
	Map<String, WeixinPublicNumber> weixinPublicNumbers = (Map<String, WeixinPublicNumber>)request.getSession().getAttribute("weixinPublicNumbers");
	if(weixinPublicNumbers == null){
		response.sendRedirect("manager-login.jsp");
		return ;
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<title>微信服务管理平台-微信公众号管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
		<!-- Bootstrap core CSS -->
		<link href="css/bootstrap.min.css" rel="stylesheet"/>
		<link href="css/google-bootstrap.css" rel="stylesheet"/>
	</head>
<body>
	<div class="main_right">
		<div class="" id="add_zsc" style=""></div>
		<!---------回复列表-------------->
		<%
				StringBuffer sb = new StringBuffer();
				sb.append("<div class='cos-major'>");
				Set<String> s = weixinPublicNumbers.keySet();
				Iterator<String> i = s.iterator();
				int number=1;
				while(i.hasNext()){
					String uname=i.next();
					WeixinPublicNumber weixinPublicNumber = weixinPublicNumbers.get(uname);
					long service_time = weixinPublicNumber.getService_time(); 
					int status = weixinPublicNumber.getStatus();
					sb.append("<div class='list' style='position: relative'><h3>公众号："+uname+
						  "<a href='javascript:;' class='btn btn-small' state='add' id='zsc_btninfo"+number+"' onclick='zsc_show("+number+")' style='float: right;'>展开</a></h3>"+
						  "<div id='zsc_content_"+number+"'><p>状态："+(status==0?"试用":status==1?"使用":"禁用")+"</p>"+
				          "<input id='status_"+number+"' value='"+status+"' style='display:none;'></input>"+
						  "<input id='uname_"+number+"' value='"+uname+"' style='display:none;'></input>"+
				          "<input id='service_time_"+number+"' value='"+service_time+"' style='display:none;'></input></div></div>");
				   number++;
				}
				out.write(sb.toString());
			%>
		</div>
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui.js"></script>
	<script type="text/javascript" src="js/manager.js"></script>
	<script type="text/javascript" src="js/util.js"></script>
</body>
</html>