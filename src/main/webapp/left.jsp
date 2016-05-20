<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		    <div class="nav-wrap left-menu">
				<ul class="nav nav-list">
					<li class="nav-header" style="line-height: 15px">&nbsp;</li>
					<li id="index_jsp" class="nav-header"><a href="index.jsp" style="padding-left: 40px;">系统接口配置</a></li>
					<li id="menu_jsp" class="nav-header"><a href="menu.jsp" style="padding-left: 40px;">菜单管理</a></li>
					<li class="nav-header" style="padding-left: 40px;">普通消息管理</li>
					<li id="keyword-reply_jsp"><a href="keyword-reply.jsp" style="padding-left: 70px;">关键词回复</a></li>
					<li id="default-reply_jsp"><a href="default-reply.jsp" style="padding-left: 70px;">默认消息回复</a></li>
					<li id="care-reply_jsp"><a href="care-reply.jsp" style="padding-left: 70px;">关注消息回复</a></li>
					<li class="nav-header" style="padding-left: 40px;">远程消息管理</li>
					<li id="remote-text_jsp"><a href="remote-text.jsp" style="padding-left: 70px;">文本远程消息</a></li>
					<li id="remote-location_jsp"><a href="remote-location.jsp" style="padding-left: 70px;">地理位置远程消息</a></li>
					<li id="remote-message-encrypt-config_jsp">
						<a href="remote-message-encrypt-config.jsp" style="padding-left: 70px;">远程消息加密管理</a>
					</li>
					<li class="nav-header" style="line-height: 15px">&nbsp;</li>
				</ul>
			</div>
			<script type="text/javascript">
				$(function(){
					if($(page_name).attr('class') == 'nav-header'){
						$(page_name).find('a').css('padding-left','40px');
					}else{
						$(page_name).find('a').css('padding-left','70px');
					}
					$(page_name).addClass('menu-active');
				});
			</script>