<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta charset="utf-8"/>
		<link href="css/bootstrap.min.css" rel="stylesheet"/>
		<link href="css/google-bootstrap.css" rel="stylesheet"/>
		<title>微信服务管理平台-管理员-登陆</title>
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
	<div class="login">
		<div class="container">
			<div class="signin">
				<div class="signin-box">
					<h2>微信服务管理平台-管理员</h2>
					<form>
						<fieldset>
							<label for="username">用户名</label> 
							<input type="text" name="username" id="username" />
							<label for="password">密码</label>
							<input type="password" name="password" id="password"/>
							<div id="verify_code_div" style="display: none;">
								<label for="verify_code">验证码</label>
								<input type="text" name="verify_code" id="verify_code"/>
								<img src="" title="点击刷新验证码" class="verify_img" style="cursor: pointer;margin: -5px 0 15px 1px" height="30" width="120"/><br/>
							</div>
							<input type="button" class="btn btn-primary" id="btn-login" value="登陆" />
							<label class="remember">
								<input type="checkbox" name="rememberMe" value="yes" />
								<strong class="remember-label">记住用户名</strong>
							</label>
						</fieldset>
					</form>
					<ul>
						<li><a id="link-forgot-pwd" href="#">无法登陆?</a>
						<a id="link-register" href="./manager-password-manage.jsp" style="padding-left: 150px;">密码修改</a></li>
					</ul>
				</div>
			</div>

		</div>
	</div>
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/util.js"></script>
	<script type="text/javascript" src="js/MD5.js"></script>
	<script>
		var show_verify_code = <%=request.getSession().getAttribute("show_verify_code")%>;
		$(function() {
			
			function doLogin() {
				var me = this;
				$(me).attr('disabled','disabled');
				var username = $("input[name='username']").val();
				var password = $("input[name='password']").val();
				var verify_code = $("input[name='verify_code']").val();
				_remober_username = $("input[name='rememberMe']")[0].checked ? 'yes' : 'no';
				if(isNull(username)){
					alert('用户名不能为空');
					$(me).removeAttr('disabled');
					return ;
				}
				if(isNull(password)){
					alert('密码不能为空');
					$(me).removeAttr('disabled');
					return ;
				}
				if(show_verify_code == 2 && isNull(verify_code)){
					alert('验证码不能为空');
					$(me).removeAttr('disabled');
					return ;
				}
				//$.cookie('_remober_username', _remober_username);
				JSCookie.setCookie('_remober_username', _remober_username);
				//$.cookie('_username', username);
				JSCookie.setCookie('manager_username', username);
				if(password && password.length < 32){
					password = MD5(password);
					$("input[name='password']").val(password);
				}
				var param = {
						username:username,
						password:password,
						verify_code:verify_code
				};
				command('manager-login', param, function(data){
					if(data.code == 0){
						window.location.href = 'manager.jsp';
						return;
					}else if(data.code == 805){
						alert('验证码不正确');
						if(show_verify_code < 2){
							show_verify_code++;
						}
						if(show_verify_code == 2){
							$('#verify_code_div').show();
						}
						$(me).removeAttr('disabled');
					}else if(data.code == 806){
						alert('用户名或密码不正确');
						if(show_verify_code < 2){
							show_verify_code++;
						}
						if(show_verify_code == 2){
							$('#verify_code_div').show();
						}
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
					$('#password').focus();
				}
			});
			
			$('#password').keyup(function(event){
				if(event.keyCode == 13){
					doLogin();
				}
			});
			
			//var _remober_username = $.cookie("_remober_username");
			var _remober_username = JSCookie.getCookie("_remober_username");
			if(_remober_username == 'yes'){
				$("input[name='rememberMe']").attr('checked',true);
				//var _username = $.cookie("_username");
				var _username = JSCookie.getCookie("manager_username");
				if(!isNull(_username)){
					 $("input[name='username']").val(_username);
				}
			}else{
				$("input[name='rememberMe']").attr('checked',false);
			}
			
			if(show_verify_code == 2){
				$('#verify_code_div').show();
				var timenow = new Date().getTime();
				$(".verify_img").attr("src","kaptcha.jsp?r="+ timenow);
			}

			$(".verify_img").click(function() {
				var timenow = new Date().getTime();
				$(this).attr("src","kaptcha.jsp?r="+ timenow);
			});
			
			$("#btn-login").click(doLogin);
		});
	</script>
</body>
</html>