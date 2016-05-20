<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="header.jsp" %>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta charset="utf-8"/>
		<title>微信服务管理平台-用户密码修改</title>
		<style>
			body {
				padding-top: 80px;
				padding-bottom: 40px;
			}
			
			a {
				color: #15c;
				text-decoration: none;
			}
			
			a:hover {
				color: #15c;
				text-decoration: underline;
			}
			
			form,label,input[type=text],input[type=checkbox],input[type=password] {
				margin: 0;
			}
			
			.signin {
				width: 335px;
				margin: 0 auto;
			}
			
			.signin-box {
				padding: 20px 25px 15px;
				background: #f1f1f1;
				border: 1px solid #e5e5e5;
			}
			
			.signin-box h2 {
				font-size: 16px;
				font-weight: normal;
				line-height: 17px;
				height: 16px;
				margin: 0 0 19px;
			}
			
			.signin-box input[type=checkbox] {
				vertical-align: bottom;
			}
			
			.signin-box input[type=text],.signin-box input[type=password] {
				width: 100%;
				font-size: 15px;
				color: black;
				line-height: normal;
				height: 32px;
				margin: 0 0 20px;
				box-sizing: border-box;
			}
			
			.signin-box input[type=button] {
				margin: 0 20px 15px 0;
				width: 60px;
			}
			
			.signin-box label {
				color: #222;
				margin: 0 0 5px;
				display: block;
				font-weight: bold;
				font-size: 13px;
			}
			
			.signin-box label.remember {
				display: inline-block;
				vertical-align: top;
				margin: 9px 0 0;
				line-height: 1;
				font-size: 13px;
			}
			
			.signin-box .remember-label {
				font-weight: normal;
				color: #666;
				line-height: 0;
				padding: 0 0 0 5px;
			}
			
			.signin-box ul {
				list-style: none;
				line-height: 17px;
				margin: 0;
				padding: 0;
			}
			
		</style>
	</head>
<body>
<div class="container body" >  
	<div class="login">
		<div class="container">
			<div class="signin">
				<div class="signin-box">
					<h2>用户密码修改</h2>
					<form>
						<fieldset>
							<label for="username">用户名</label> 
							<input type="text" name="username" id="username" disabled="disabled"/>
							<label for="old_password">原密码验证</label>
							<input type="password" name="old_password" id="old_password"/>
							<label for="new_password">新密码</label>
							<input type="password" name="new_password" id="new_password"/>
							<label for="confirm_password">新密码确认</label>
							<input type="password" name="confirm_password" id="confirm_password"/>
							<div id="verify_code_div" style="display: block;">
								<label for="verify_code">验证码</label>
								<input type="text" name="verify_code" id="verify_code"/>
								<img src="kaptcha.jsp" title="点击刷新验证码" class="verify_img" style="cursor: pointer;margin: -5px 0 15px 1px" height="30" width="120"/><br/>
							</div>
							<input type="button" class="btn btn-primary" id="btn-passwordManage" value="修改" />
						</fieldset>
					</form>
				</div>
			</div>

		</div>
	</div>
</div>
	<script type="text/javascript" src="js/MD5.js"></script>
	<script>
		var username = "<%=userinfo.getUname()%>";
		$(function() {
			$("input[name='username']").val(username);
			function doPasswordManage() {
				var me = this;
				$(me).attr('disabled','disabled');
				var old_password = $("input[name='old_password']").val();
				var new_password = $("input[name='new_password']").val();
				var confirm_password = $("input[name='confirm_password']").val();
				var verify_code = $("input[name='verify_code']").val();
				if(isNull(username)){
					alert('用户名不能为空');
					$(me).removeAttr('disabled');
					return ;
				}
				if(isNull(old_password)||isNull(new_password)){
					alert('密码不能为空');
					$(me).removeAttr('disabled');
					return ;
				}
				if(new_password.length<5||new_password.length>16){
					alert('密码长度为6~16位');
					$(me).removeAttr('disabled');
					return ;
				}
				if(new_password != confirm_password){
					alert('您两次输入的密码不相同，请重新输入新密码！');
					$(me).removeAttr('disabled');
					$('#new_password').val('');
					$('#confirm_password').val('');
					return ;
				}
				if(isNull(verify_code)){
					alert('验证码不能为空');
					$(me).removeAttr('disabled');
					return ;
				}
				if(old_password && old_password.length < 32){
					old_password = MD5(old_password);
				}
				if(new_password && new_password.length < 32){
					new_password = MD5(new_password);
				}
				var params = {
						username:username,
						old_password:old_password,
						new_password:new_password,
						verify_code:verify_code
				};
				command('password-manage', params, function(data){
					if(data.code == 0){
						alert('修改成功');
						JSCookie.setCookie('_username', username);
						window.location.href='index.jsp';
						return;
					}else if(data.code == 805){
						alert('验证码不正确');
						$(me).removeAttr('disabled');
					}else if(data.code == 806){
						alert('用户名或密码不正确');
						$(me).removeAttr('disabled');
					}else{
						alert(data.message);
						$(me).removeAttr('disabled');
					}
					var timenow = new Date().getTime();
					$(".verify_img").attr("src","kaptcha.jsp?r="+ timenow);
				});
			}
			
			$('#username').keyup(function(event){
				if(event.keyCode == 13){
					$('#old_password').focus();
				}
			});
			
			$('#old_password').keyup(function(event){
				if(event.keyCode == 13){
					$('#new_password').focus();
				}
			});
			
			$('#new_password').keyup(function(event){
				if(event.keyCode == 13){
					$('#confirm_password').focus();
				}
			});
			
			$('#confirm_password').keyup(function(event){
				if(event.keyCode == 13){
					$('#verify_code').focus();
				}
			});
			
			$('#verify_code').keyup(function(event){
				if(event.keyCode == 13){
					doPasswordManage();
				}
			});

			$(".verify_img").click(function() {
				var timenow = new Date().getTime();
				$(this).attr("src","kaptcha.jsp?r="+ timenow);
			});
			$("#btn-passwordManage").click(doPasswordManage);
			$('#navbar-system-manage').addClass("active");
			$('#navbar-menu').removeClass("active");
			$('#navbar-simple-message').removeClass("active");
			$('#navbar-remote-message').removeClass("active");
		});
	</script>
</body>
</html>