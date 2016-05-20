<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>远程消息加密管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
<body>
	<%@include file="header.jsp"%>
	<div class="container body">
		<%@include file="left.jsp" %>
		<div class="main-right">
			<h3>远程消息加密管理</h3>
			<div class="alert alert-wx" style="height: 100px;">
				<div>
					微信消息加密密钥：<input type="text" name="encrypt" style="width:640px;" value="<%=publicNumber.getRemote_message_encrypt() == null ? "" : publicNumber.getRemote_message_encrypt()%>">	
				</div>
				<div style="float: left;margin-right: -1px;margin-top: 3px;">启用远程消息加密：</div>
				<div>
					<input  type="checkbox" style="float: left;" name="isEncrypt" <%=publicNumber.isEncrypt_remote_message() ? "checked='checked'" : ""%>/>
				</div>
				
			</div>
			<div>
				<div style="margin-top: 50px;">
					<span style="color:red;">注意</span>：启用远程消息加密后，微信服务管理平台会将远程类型的消息加密发送给第三方业务系统
				</div>
				<div style="margin-top: -40px;">
					<button class="btn btn-submit" style="float: right;width: 120px;height: 38px;" event="wx-remote-message-encrypt-update">提 交</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var page_name = '#remote-message-encrypt-config_jsp';
	$(function(){
		button_click('wx-remote-message-encrypt-update', function(){
			var encrypt = $('input[name="encrypt"]').val();
			var isEncrypt = $('input[name="isEncrypt"]')[0].checked;
			if(isEncrypt && isNull(encrypt)){
				alert('请填写加密密钥');
				return;
			}
			if(encrypt.length > 16){
				alert('加密密钥长度不得超过16位');
				return;
			}
			var params = {
				encrypt:encrypt,
				isEncrypt:isEncrypt
			};
			command('wx-remote-message-encrypt-update', params, function(data){
				if(data.code == 0){
					alert('保存成功');
				}else{
					alert(data.message);
				}
			});
		});
	});
	</script>
</body>
</html>