<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>远程地理位置事件消息管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
<body>
	<%@include file="header.jsp"%>
	<div class="container body">
		<%@include file="left.jsp" %>
		<div class="main-right">
		<div id="add_zsc" >
			<div class="list" id="new_reply">
				<div>
					<h3>地理位置远程消息</h3>
				</div>
				<div class='edit-info'>
					<table style="width:100%;">
						<tbody>
							<tr>
								<th width="100">调用类型：</th>
								<td><select id='zsc_remote_type' style="width: 100%;">
										<option value='http'>HTTP</option>
										<option value='yigo'>YIGO</option>
										<option value='local'>LOCAL</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>调用地址：</th>
								<td><input style="width: 100%;" type='text' value='<%=publicNumber.getWeixinRemoteLocationEventMessage().getUrl()%>' id='zsc_remote_url' /></td>
							</tr>
						</tbody>
					</table>
					<div style='padding-top: 45px; clear: both;'>
						<button class="btn btn-large btn-submit" onclick='msgsave("wx-update-remote-location","remote_locationevent")' style="float: right; width: 120px;">保存</button>
					</div>
				</div>
			</div>
			<!---------回复列表-------------->
			<div class="cos-major" style="display: none;">
				<div class="list" style="position: relative">
					<div id="zsc_content">
						<input id="msg_replytype" value="<%=publicNumber.getWeixinRemoteLocationEventMessage().getMsgType() %>" style="display: none;"/>
						<input id="msg_replycontent" value='<%=ConfigUtil.messageToContent(publicNumber.getWeixinRemoteLocationEventMessage()) %>' style="display: none;"/>
						<input id="uname" value="<%=userinfo != null ? userinfo.getUname() : "" %>" style="display:none">
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="js/addkeyword.js"></script>
	</div>
	</div>
	<script type="text/javascript">
	var page_name = '#remote-location_jsp';
	$(function(){
		msg_show();
		document.getElementById('zsc_remote_type').value='<%=publicNumber.getWeixinRemoteLocationEventMessage().getRemoteType() %>';
	});
	</script>
</body>
</html>