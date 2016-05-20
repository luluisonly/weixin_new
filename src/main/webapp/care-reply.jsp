<%@page import="com.bokesoft.thirdparty.weixin.common.TConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>关注消息回复管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
<body>
	<%@include file="header.jsp"%>
	<div class="container body">
		<%@include file="left.jsp" %>
		<div class="main-right">
		<div id="add_zsc">
			<div class="list" id="new_reply">
				<div>
					<h3>关注消息回复</h3>
				</div>
				<div style="margin-top: 20px;">
					<div style="float:left;">回复内容设置</div>
					<div style="border-bottom: #cccccc 1px solid;float: left;width: 663px;margin: 10px 0px 0px 10px;"></div><br><br>
					<div style="float:left;cursor: pointer;" onclick="$('#ch_wen')[0].checked = true;check_tuwen(0);">
						<div style="float:left;margin: -1px 8px 0px 0px;">
							<input type="radio" onclick="check_tuwen(0)" id="ch_wen" checked="checked" name="type">
						</div>
						<div style="float:left;">文本</div>
					</div>
					<div style="cursor: pointer;width: 127px;" onclick="$('#ch_tu')[0].checked = true;check_tuwen(1);">
						<div style="float:left;margin: -1px 8px 0px 25px;">
							<input type="radio" onclick="check_tuwen(1)" id="ch_tu" name="type">
						</div>
						<div>图文</div>
					</div>
				</div>
				<div class="clear" id="wen" style="display: block;">
					<div style="margin-top: 20px;">
						<textarea id="zsc_kecontent" style="width: 100%; height: 137px; border: #ccc solid 1px;"></textarea>
					</div>
					<div style="padding-top: 45px; clear: both;">
						<p style="float: right;">
							<button class="btn btn-large btn-submit" onclick="msgsave('wx-update-care-reply','text')" style="width: 120px;">保存</button>
						</p>
					</div>
				</div>
				<div class="clear" id="tu" style="display: none;">
					<div class="tuwen">
						<div class="tuwen_left" style="margin-left: 0px;">
							<a href="javascript:;" class="imgcon">
								<img id="zsc_imgs0" src="img/bigimg.jpg" width="295" height="126">
								<div class="tuwen-head-image"></div>
								<span id="zsc_titles0">标题</span>
								<div class="modify">
									<div class="tuwen-btn-edit" onclick="zsc_editimg(0)"></div>
								</div>
								<input type="hidden" value="" class="addtitle" id="zsc_t0">
								<input type="hidden" value="" class="addimg" id="zsc_i0">
								<input type="hidden" value="" class="addurls" id="zsc_urls0">
							</a>
							<div style="width: 295px;" id="zsc_add1bg">
								<button class="add1" style="cursor: pointer;" type="button" onclick="zsc_addimg(1)">新增一条</button>
							</div>
						</div>
						<div class="tuwen_right" style="top: 1px;">
							<img src="./img/tuwen-left.png">
							<p>
								<br>标题：<input id="zsc_titles" type="text" style="width: 360px;" onkeyup="zsc_keytitle()" onchange="zsc_keytitle()"><br>
								<br>封面：<input type="text" style="width: 360px" id="zsc_imgurls"><br>
								<br>链接：<input id="zsc_urls" type="text" style="width: 360px" onkeyup="zsc_urlinfo()" onchange="zsc_urlinfo()">
							</p>
							<div style="float: right; margin-top: 6px;">
								<button class="btn btn-large btn-submit" onclick='msgsave("wx-update-care-reply","news")' style="width: 120px;">保存</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!---------回复列表-------------->
			<div class="cos-major" style="display: none;">
				<div class="list" style="position: relative">
					<div id="zsc_content">
						<input id="msg_replytype" value="<%=publicNumber == null ? null : publicNumber.getSubscribeReplyMessage().getMsgType() %>" style="display: none;"/>
						<textarea id="msg_replycontent" style="display: none;"><%=ConfigUtil.messageToContent(publicNumber == null ? null : publicNumber.getSubscribeReplyMessage()) %></textarea>
						<input id="uname" value="<%=userinfo != null ? userinfo.getUname() : "" %>" style="display:none">
					</div>
				</div>
			</div>
			<div id="localImag">
				<img id="preview" width="-1" height="-1" style="diplay: none">
			</div>
		</div>
		<div class="showimg" style="display: none">
			<div class="stit">
				<span>上传图片</span><a href="javascript:zsc_close();"><i class="icon-remove"></i></a>
			</div>
			<div class="sup">
				<input type="button" value="上传图片" class="uploadbtn">
				<form action="/weixin-debug/m.do?type=wx-upload-image" method="post" id="zsc_myform" enctype="multipart/form-data" target="yframe">
					<input type="file" value="上传图片" class="uploadbtn" style="position: absolute; top: 75px; left: 20px; filter: alpha(pacity =    0); opacity: 0; z-index: 999;" onchange="zsc_upload()" name="fileName">大小不超过1M，仅限png,jpeg,jpg
					<input type="hidden" name="sub" value="submit">
				</form>
				<iframe name="yframe" style="border: none; display: none;"></iframe>
			</div>
			<div class="imgbox"></div>
			<div class="sbottom">
				<input type="button" class="submit" id="zsc_surebtn" value="确定">
			</div>
			<!-----正在提交------>
			<span class="loadsubmit">正在上传...</span>
		</div>
		<div class="zhe" style="display: none"></div>
		<script type="text/javascript" src="js/addkeyword.js"></script>
	</div>
	</div>
	<script type="text/javascript">
	var page_name = '#care-reply_jsp';
	var attachment_server = "<%=TConstant.WEIXIN_SERVER%>";
	var uname = "<%=userinfo.getUname()%>";
	$(function(){
		msg_show();
		command('wx-get-pic-list',null,function(data){
			var d = data.data;
			var h = createPicList(d,uname);
			$('.imgbox').append(h);
		});
	});
	</script>
</body>
</html>