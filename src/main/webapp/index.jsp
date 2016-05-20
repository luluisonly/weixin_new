<%@page import="com.bokesoft.thirdparty.weixin.common.TConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>zaofans微信服务管理平台首页</title>
		<link href="css/style.css" rel="stylesheet"/>
		<style type="text/css">
			.zclip{
				margin-top: -8px;
			}
		</style>
	</head>
	<body>
	    <%@include file="header.jsp" %>
		<div class="container body">
	    	<%@include file="left.jsp" %>
			<div class="main-right">
				<div class="alert alert-wx">
					微信接口配置URL：<input type="text" value="<%=TConstant.WEIXIN_SERVER %>weixin?uname=<%=userinfo != null ? userinfo.getUname() : "" %>"
							class="c-pointer" readonly="readonly" style="width: 60%;margin-right: 10px;margin-left: 13px;" id="w_url" onclick="$(this).select()"/>
					<a href="#" id="w_url_btn">点击复制链接</a>
				</div>
				<div class="alert alert-wx">
					微信接口配置Token：<input type="text" class="c-pointer" id="w_token" value="<%=publicNumber.getMessage_token() %>" readonly="readonly" style="width: 60%;margin-right: 10px;" onclick="$(this).select()"/>
					<a href="#" id="w_token_btn">点击复制链接</a>
				</div>
				<div class="alert alert-wx">
					微信接口调用凭证access_token：<input type="text" class="c-pointer" id="w_access_token" value="<%=publicNumber.getAccess_token() %>" readonly="readonly" style="width: 60%;margin-right: 10px;" onclick="$(this).select()"/>
					<a href="#" id="w_accesstoken_token_btn">点击复制</a>
				</div>
					<button class="btn btn-large btn-submit" id="btn-refresh" type="button" style="float: right;">刷新AccessToken</button>
				<br/>
				<br/>
				<br/>
				<div class="alert alert-wx">
				使用提示：<br/><br/>请将上述信息复制并填写至<a href="https://mp.weixin.qq.com/" target="blank">腾讯微信管理后台</a>的 "功能>高级功能>开发模式" 下对应的输入框中
				</div>
			</div>
		</div>
	<!-- <div class="container">
		<div class="footer">&nbsp;版权所有   ©</div>
	</div> -->
	<script type="text/javascript" src="js/zclip/jquery.zclip.min.js"></script>
	<script type="text/javascript">
		var page_name = '#index_jsp';
		$(function(){
			$('#w_url_btn').zclip({
				path:'js/zclip/ZeroClipboard.swf',
				copy:$('#w_url').val(),
				afterCopy:function(){
					alert($('#w_url').val()+'\n已经复制到粘贴版');
				}
			});
			$('#w_token_btn').zclip({
				path:'js/zclip/ZeroClipboard.swf',
				copy:$('#w_token').val(),
				afterCopy:function(){
					alert($('#w_token').val()+'\n已经复制到粘贴版');
				}
			});
			$('#w_accesstoken_token_btn').zclip({
				path:'js/zclip/ZeroClipboard.swf',
				copy:$('#w_access_token').val(),
				afterCopy:function(){
					alert($('#w_access_token').val()+'\n已经复制到粘贴版');
				}
			});
			var zclips = $('.zclip');
			for(var i=0;i<zclips.length;i++){
				var zclip = zclips[i];
				//var top = parseInt(zclip.style['top']);
				//$(zclip).css('top',top - 96 +'px');
				var height = parseInt($(zclip).children().attr('height'));
				$(zclip).children().attr('height',height + 5);
			}
			function refresh(){
				var type = 'wx-refresh-accesstoken';
				command(type, null, function(data){
					if(data.code == 0){
						alert('刷新成功！');
						$("#w_access_token").val(data.data);
					}else{
						alert(data.message);
					}
				});
			}
			$("#btn-refresh").click(refresh);
		});
	</script>
</body>
</html>