<%@page import="com.bokesoft.thirdparty.weixin.common.TConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>文本远程消息管理</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
<body>
	<%@include file="header.jsp"%>
	<div class="container body">
		<%@include file="left.jsp"%>
		<div class="main-right">
			<button class="btn btn-large btn-submit" onclick="addremote()" type="button" style="margin: 10px 0px 20px 0px; float: right;">添加新回复</button>
			<div id="add_zsc">
				<div class="list" id="new_reply" style="display: none;"></div>
				<div class='cos-major'></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var page_name = '#remote-text_jsp';
	var attachment_server = "<%=TConstant.WEIXIN_SERVER%>";
		$(function() {
			command('wx-read-remote-text', null, function(data) {
				var d = data.data;
				var h = createRemoteList(d);
				$('.cos-major').append(h);
				$(".cos-major>.list").hover(function(event) {
					var that = this;
					$($(this).parent().children()).each(function(i,item){
						var state = $($(item).find('.btn-small')[1]).attr('state');
						if(item == that){
							if ('add dec'!=state) {
								$(item).addClass("hover");
							}
							$(item).find("h3>a").css("display","block");
						}else{
							$(item).removeClass("hover");
							if(state != 'add dec'){
								$(item).find("h3>a").css("display","none");
							}
						}
					});
				},
				function(){
					$(event.target).removeClass("hover");
					if($($(this).find('.btn-small')[1]).attr('state') != 'add dec'){
						$(event.target).find("h3>a").css("display","none");
					}
				});
			});
		});
	</script>
	<script type="text/javascript" src="js/addkeyword.js"></script>
</body>
</html>