<%@page import="com.bokesoft.thirdparty.weixin.common.TConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>关键词回复管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
<body>
	<%@include file="header.jsp"%>
	<div class="container body">
		<%@include file="left.jsp"%>
		<div class="main-right">
			<button class="btn btn-large btn-submit" onclick="addreply()" type="button" style="margin: 10px 0px 20px 0px; float: right;">添加新回复</button>
			<div id="add_zsc">
				<div class="list" id="new_reply" style="display: none;"></div>
				<!---------回复列表-------------->
				<div class='cos-major'></div>
				<div id="localImag">
					<img id="preview" width="-1" height="-1" style="diplay: none">
				</div>
			</div>
			<div class="showimg" style="display: none">
				<div style="margin-top: 15px;margin-left: 20px;font-size: 18px;font-weight: bold;margin-bottom: 27px;">上传图片</div>
				<div class="btn_menu_del menu-icon-btn" onclick="zsc_close()" style="margin-top: -50px;"></div>
				<div class="sup">
					<input type="button" value="上传图片" class="btn btn-submit">
					<form action="/weixin_debug/m.do?type=wx-upload-image" method="post" id="zsc_myform" enctype="multipart/form-data" target="yframe">
						<input type="file" value="上传图片" style="position: absolute; top: 75px; left: 20px; filter: alpha(pacity =    0); opacity: 0; z-index: 999;" onchange="zsc_upload()" name="fileName">大小不超过1M，仅限png,jpeg,jpg
						<input type="hidden" name="sub" value="submit">
					</form>
					<iframe name="yframe" style="border: none; display: none;"></iframe>
				</div>
				<div class="imgbox"></div>
				<div class="sbottom">
					<input type="button" class="btn btn-submit" style="float: right;width: 110px;" id="zsc_surebtn" value="确定">
				</div>
				<!-----正在提交------>
				<span class="loadsubmit">正在上传...</span>
			</div>
			<div class="zhe" style="display: none"></div>
		</div>
	</div>
	<script type="text/javascript">
	var page_name = '#keyword-reply_jsp';
	var attachment_server = "<%=TConstant.WEIXIN_SERVER %>";
	var uname = "<%=publicNumber.getUname() %>";
	$(function(){
		command('wx-read-keywordReply', null, function(data){
			var d = data.data;
			var h = createThumb(d);
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
		command('wx-get-pic-list',null,function(data){
			var d = data.data;
			var h = createPicList(d,uname);
			$('.imgbox').append(h);
		});
	});
	</script>
	<script type="text/javascript" src="js/addkeyword.js"></script>
</body>
</html>