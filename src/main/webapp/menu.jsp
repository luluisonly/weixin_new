<%@page import="com.bokesoft.thirdparty.weixin.common.TConstant"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>微信自定义菜单管理</title>
		<link href="css/style.css" rel="stylesheet"/>
	</head>
<body>
	<%@include file="header.jsp" %>
	<div class="container body">
	    <%@include file="left.jsp" %>
	    <div class="main-right">
	    	<div>
	    		<div class="alert alert-wx">
					微信AppId：<input type="text" name="appid" style="width:650px;margin-left: 30px;" class="c-pointer" readonly="readonly" value="<%=publicNumber.getApp_id() == null ? "请填写您微信公众号的APPID" : publicNumber.getApp_id()%>">
					<br/><br/>微信AppSecret：<input type="text" name="appSecret" style="width:650px;" class="c-pointer" readonly="readonly" value="<%=publicNumber.getApp_secret() == null ? "请填写您微信公众号的APPSECRET" : publicNumber.getApp_secret()%>">
				</div>
				<br/>
	    		<div>
					<span style="color: red;">TIP</span>：微信AppId和微信AppSecret需要到<a href="https://mp.weixin.qq.com/" target="blank">腾讯微信管理后台</a>获取
					<br/><br/>
					<button class="btn btn-edit" event="wx-update-appid" style="margin-top: -58px !important;float: right;width: 120px;height: 40px;">修 改</button>
				</div>
				<div style="border-bottom: 1px #cccccc solid"></div>
			</div>
			<br/><br/><br/><br/>
			<div style="margin-bottom: 18px;">
				<div style="cursor: pointer;width: 150px;" data-toggle="modal" data-target="#menu_add_dialog" id="div_add_parent_menu">
					<img src="img/icon-add-menu.jpg"><div style="margin-top: -24px;font-size: 20px;margin-left: 30px;">添加一级菜单</div>
				</div>
			</div>
			<div style="margin-left: 5px;width: 828px;">	
				<table id="wx-menu-table" class="table table-hover table-bordered table-menu" style="margin-left: -5px;">
	                <tr class="menu-title">
	                    <td style="width:200px;">菜单名称</td>
	                    <td style="width:75px;">事件类型</td>
	                    <td style="width: 132px;">关键词</td>
	                    <td style="width:75px;text-align:center;">跳转地址</td>
	                    <td style="text-align: center; width: 50px;">排序</td>
	                    <td style="text-align: center; width: 141px;">操作</td>
	                    <td style="width:43px;text-align:center;" >启用</td>
	                </tr>
	            </table>
            </div>
           <div style="height:100px;width:100%;float: right;">
           		<div style="margin-top: 35px;">
	           		<span style="color: red;">TIP</span>：一级菜单数最多只能启用3个，二级菜单数最多启用5个，否则会生成失败！
           		</div>
           		<div style="float: right;margin-top: -35px;">
	           		<button class="btn btn-large btn-edit" event="save_menu">保存菜单</button>
					<button class="btn btn-large btn-edit" event="create_menu">生成菜单</button>
           		</div>
			</div>
			<!-- <div class="footer">版权所有 © bokesoft.com 2013</div> -->
			<!-- Modal 删除菜单 -->
			<div class="modal fade" id="menu_del_dialog" role="dialog" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			        <h4 class="modal-title" >删除菜单提示</h4>
			      </div>
			      <div class="modal-body menu_del_dialog">
			        	确认要删除该菜单吗
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-submit" style="width: 120px;" event="del_menu">是的</button>
			        <button type="button" class="btn btn-edit" data-dismiss="modal">取消</button>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			
			<!-- Modal 添加微信菜单-->
			<div class="modal fade" id="menu_add_dialog" role="dialog" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			        <h4 class="modal-title" >添加微信菜单</h4>
			      </div>
			      <div class="modal-body menu_add_dialog">
			      	<table>
						<tr>
							<th width="150">菜单名称：</th>
							<td><input type="text" style="width: 350px;" value="" name="menu_name"></td>
						</tr>
						<tr>
							<th>事件类型：</th>
							<td><select style="width: 100%;" name="menu_type">
								  <option value='click'>CLICK</option>
								  <option value='view'>VIEW</option>
								  <option value='scancode_push'>扫码推SCANCODE_PUSH</option>
								  <option value='scancode_waitmsg'>扫码推-等待SCANCODE_WAITMSG</option>
								  <option value='pic_sysphoto'>系统拍照PIC_SYSPHOTO</option>
								  <option value='pic_photo_or_album'>拍照|相册PIC_PHOTO_OR_ALBUM</option>
								  <option value='pic_weixin'>相册PIC_WEIXIN</option>
								  <option value='location_select'>位置选择LOCATION_SELECT</option>
								</select>
							</td>
						</tr>
						<tr id="menu_keyword_tr">
							<th>关键词：</th>
							<td><select style="width: 100%;" name="menu_keyword"></select></td>
						</tr>
						<tr id="menu_url_tr" style="display: none;">
							<th>跳转地址：</th>
							<td><input type="text" style="width: 100%;" value="" name="menu_url"></td>
						</tr>
						<tr>
							<th>排序：</th>
							<td><input type="text" style="width: 100%;" value="0" name="menu_compare"></td>
						</tr>
					</table>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-submit" style="width: 120px;" event="add_menu">添加</button>
			        <button type="button" class="btn btn-edit" data-dismiss="modal">取消</button>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			
			<!-- Modal 更新微信菜单-->
			<div class="modal fade" id="menu_update_dialog" role="dialog" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			        <h4 class="modal-title" >更新微信菜单</h4>
			      </div>
			      <div class="modal-body menu_add_dialog">
			      	<table>
						<tr>
							<th width="150">菜单名称：</th>
							<td><input type="text" style="width: 350px;" value="" name="menu_name_u"></td>
						</tr>
						<tr id="menu_type_u_tr">
							<th>事件类型：</th>
							<td><select style="width: 100%;" name="menu_type_u">
								  <option value='click'>CLICK</option>
								  <option value='view'>VIEW</option>
								  <option value='scancode_push'>扫码推SCANCODE_PUSH</option>
								  <option value='scancode_waitmsg'>扫码推-等待SCANCODE_WAITMSG</option>
								  <option value='pic_sysphoto'>系统拍照PIC_SYSPHOTO</option>
								  <option value='pic_photo_or_album'>拍照|相册PIC_PHOTO_OR_ALBUM</option>
								  <option value='pic_weixin'>相册PIC_WEIXIN</option>
								  <option value='location_select'>位置选择LOCATION_SELECT</option>
								</select>
							</td>
						</tr>
						<tr id="menu_keyword_u_tr">
							<th>关键词：</th>
							<td><select style="width: 100%;" name="menu_keyword_u"></select></td>
						</tr>
						<tr id="menu_url_u_tr" style="display: none;">
							<th>跳转地址：</th>
							<td><input type="text" style="width: 100%;" value="" name="menu_url_u"></td>
						</tr>
						<tr>
							<th>排序：</th>
							<td><input type="text" style="width: 100%;" value="0" name="menu_compare_u"></td>
						</tr>
					</table>
			      </div>
			      <div class="modal-footer">
			      	<button type="button" class="btn btn-submit" style="width: 120px;" event="update_menu">更新</button>
			        <button type="button" class="btn btn-edit" data-dismiss="modal">取消</button>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
            <div class="btn-group dropup" style="display: none;">
			  <a class="btn dropdown-toggle" style="width: 166px;" data-toggle="dropdown" href="#" >
			    Action
			    <span class="caret"></span>
			  </a>
			  <ul class="dropdown-menu">
			    <li><a href="#">文本远程消息</a></li>
                <li><a href="#">图片远程消息</a></li>
                <li><a href="#">地理位置远程消息</a></li>
			  </ul>
			</div>
		</div>
		<!-- <div class="container" style="padding-bottom: 50px;">
			<div class="footer">
				&nbsp;版权所有 © 
			</div>
		</div> -->
	</div>
	<br/>
	<script type="text/javascript" src="js/treeTable/jquery.treeTable.js"></script>
	<script type="text/javascript" src="js/menu.js"></script>
</body>
</html>