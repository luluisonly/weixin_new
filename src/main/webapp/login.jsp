<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if(request.getSession().getAttribute("userinfo") != null){
		response.sendRedirect("index.jsp");
	}else{
		if(session.getAttribute("show_verify_code") == null){
			session.setAttribute("show_verify_code", 0);
		}
	}
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta charset="utf-8"/>
		<link href="css/bootstrap.min.css" rel="stylesheet"/>
		<link href="css/google-bootstrap.css" rel="stylesheet"/>
		<link href="css/style.css" rel="stylesheet"/>
		<title>微信服务管理平台-登陆</title>
		
	</head>
<body>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation" style="background-image: url('img/header-bg.png');height: 60px;margin-bottom: 0px;">
		<div class="container">
			<div class="navbar-header" style="margin-top: 6px;">
				<a class="navbar-brand" href="index.jsp"
					style="color: #444444; font-size: 20px; font-weight: bold;">微信服务管理平台
				</a>
			</div>
		</div>
	</div>
	<div class="wrap">
		<div class="container body" style="background: #70D7F8;border:  none;height: 612px;background-image: url('img/login-bg.png');">
			<div class="signin">
				<div class="signin-box">
					<!-- <img class="header" alt="" src="img/avatar_2x.png"> -->
					<h3>用户登录</h3>
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
							<input type="button" class="btn btn-submit" id="btn-login" value="登录" />
						</fieldset>
					</form>
					<div style="margin-top: 15px;">
						<div style="float: left;">
							<label class="remember">
							<input type="checkbox" name="rememberMe" value="yes" />
							<strong class="remember-label">记住用户名</strong>
						</label>
						</div>
						<div style="font-size: 12px;float: right;">
							<a id="link-forgot-pwd" href="#">无法登陆?</a> | <a id="link-register" href="./register.jsp">创建帐户</a>
						</div>
					</div >
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
				JSCookie.setCookie('_username', username);
				if(password && password.length < 32){
					password = MD5(password);
					$("input[name='password']").val(password);
				}
				var param = {
						username:username,
						password:password,
						verify_code:verify_code
				};
				command('login', param, function(data){
					if(data.code == 0){
						window.location.href = 'index.jsp';
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
					}else if(data.code == 807){
						alert('试用时间已到，若需继续使用请联系管理员');
						if(show_verify_code < 2){
							show_verify_code++;
						}
						if(show_verify_code == 2){
							$('#verify_code_div').show();
						}
						$(me).removeAttr('disabled');
					}else if(data.code == 808){
						alert('该账户已被禁用，若有疑问请联系管理员');
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
				var _username = JSCookie.getCookie("_username");
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