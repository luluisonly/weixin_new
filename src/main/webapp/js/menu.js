var page_name = '#menu_jsp';
var submit_appid = false;
var menus = {};
var menu_operate_id = -1;
var menu_row_id = 1;
var treeTable = null;
var isAddParentMenu = false;
var keyword_keys;
$(function(){
	command('wx-read-menu',{}, function(data){
		if(data.code == 0){
			var menu = JSON.parse(data.data);
//			var buttons = JSON.parse(menu.button);
		var	buttons = menu.button;
			for(var i in buttons){
				var pid = menu_row_id++;
				var button = buttons[i];
				var sub_button = button.sub_button;
				var parent_html = createMenuParent(button, pid);
//				menus[pid] = button.name;
				menus[pid] = button;
				$('#wx-menu-table').append(parent_html);
				$('#table_check_'+pid).attr('checked',button.enable);
				if(sub_button && sub_button.length > 0){
					for(var i in sub_button){
						var s_id = menu_row_id++;
						var s_button = sub_button[i];
//						menus[s_id] = s_button.name;
						menus[s_id] = s_button;
						var dom = createMenuChildren(s_button, s_id, pid);
						$('#wx-menu-table').append(dom);
						$('#table_check_'+s_id).attr('checked',s_button.enable);
					} 
				}
			}
			$('.btn_menu_del').click(function(evnet){
				menu_operate_id = $(event.target).attr('for');
				$('.menu_del_dialog').html('确实要删除菜单《'+menus[menu_operate_id].name+'》吗？');
			});
			
			$('.btn_menu_add').click(function(evnet){
				menu_operate_id = $(event.target).attr('for');
				$('input[name="menu_name"]').val('');
				$('select[name="menu_keyword"]').val('');
				//$('input[name="menu_compare"]').val(0);
			});
			
			$('.btn_menu_update').click(btn_menu_update);
			
			var option = {
		        theme:'vsStyle',
		        expandLevel : 1,
		        beforeExpand : function($treeTable, id) {
		        	//var xx = '';
		        	/*
		            //判断id是否已经有了孩子节点，如果有了就不再加载，这样就可以起到缓存的作用
		            if ($('.' + id, $treeTable).length) { return; }
		            //这里的html可以是ajax请求
		            var html = '&lt;tr id="8" pId="6"&gt;&lt;td&gt;5.1&lt;/td&gt;&lt;td&gt;可以是ajax请求来的内容&lt;/td&gt;&lt;/tr&gt;'
		                     + '&lt;tr id="9" pId="6"&gt;&lt;td&gt;5.2&lt;/td&gt;&lt;td&gt;动态的内容&lt;/td&gt;&lt;/tr&gt;';
		            $treeTable.addChilds(html);
		            */
		        },
		        onSelect : function($treeTable, id) {
		           // window.console && console.log('onSelect:' + id);
		        }
		    };
		    treeTable = $('#wx-menu-table').treeTable(option);
		    
			button_click('add_children_menu', add_children_click);
		}
	});
	
	command('wx-read-keyword-keys', null, function(data){
		if(data.code == 0){
			keyword_keys = data.data;
			var html ='';
			for(key in keyword_keys){
				html += ('<option value='+keyword_keys[key]+'>'+keyword_keys[key]+'</option>');
			}
			$('select[name="menu_keyword"]').html('');
			$('select[name="menu_keyword"]').append(html);
			$('select[name="menu_keyword_u"]').html('');
			$('select[name="menu_keyword_u"]').append(html);
		}else{
			alert(code.message);
		}
	});
	
	//button_click('add_parent_menu', add_parent_click);
	
	$('#div_add_parent_menu').click(add_parent_click);
	
	button_click('del_menu', function(event){
		if(isParent(menu_operate_id)){
			$('tr[pid="'+$('#'+menu_operate_id).attr('id')+'"]').remove();
			$('#'+menu_operate_id).remove();
		}else{
			$('#'+menu_operate_id).remove();
		}
		$('#menu_del_dialog').modal('hide');
	});
	
	button_click('add_menu', function(event){
		var id = menu_row_id++;
		var menu_name = $('input[name="menu_name"]').val();
		var menu_type = $('select[name="menu_type"]').val();
		var menu_keyword = $('select[name="menu_keyword"]').val();
		var menu_url = $('input[name="menu_url"]').val();
		var menu_compare = $('input[name="menu_compare"]').val();
		
		if (isNull(menu_name) || isNull(menu_type) || isNull(menu_compare)) {
			alert('请填写完整信息');
			return ;
		}
		
		if(menu_type == 'click' && isNull(menu_keyword)){
			alert('请填写完整信息');
			return ;
		}else if(menu_type == 'view' && isNull(menu_url)){
			alert('请填写完整信息');
			return ;
		}else{
			if(isNull(menu_name)){
				alert('请填写完整信息');
				return ;
			}
		}
		
		var button = {
			name : menu_name,
			key:menu_keyword,
			compare:menu_compare,
			enable:true,
			type:menu_type,
			url:menu_url
		};
		menus[id] = button;
		if(isAddParentMenu){
			var dom = createMenuParent(button, id);
			treeTable.addParent(dom);
			div_click('add_children_menu',add_children_click);
			menu_operate_id = id;
		}else{
			var dom = createMenuChildren(button, id, menu_operate_id);
			treeTable.addChilds(dom);
		}
		
		$('.btn_menu_update').click(btn_menu_update);
		
		$('#table_check_'+id).attr('checked',button.enable);
		$('#menu_add_dialog').modal('hide');
		$('.btn_menu_del').click(function(event){
			menu_operate_id = $(event.target).attr('for');
			$('.menu_del_dialog').html('确实要删除菜单《'+menus[menu_operate_id].name+'》吗？');
		});
	});
	
	button_click('update_menu', update_menu);
	
	button_click('save_menu', function(event){
		var menu = menuTable2Json();
		if(menu == false){
			return false;
		}
		var menuString= JSON.stringify({button:menu});
		var param =  {
				menu:menuString
		};
		command('wx-update-menu', param, function(data){
			if(data.code == 0){
				alert('保存成功');
			}else{
				alert(data.message);
			}
			
		});
		
	});
	
	button_click('create_menu', function(event){
		var menu = menuTable2Json();
		var menuString= JSON.stringify({button:menu});
		var param =  {
			menu:menuString
		};
		command('wx-create-menu', param, function(data){
			if(data.code == 0){
				alert('创建成功');
			}else{
				alert(data.message);
			}
		});
	});
	
	button_click('wx-update-appid', function(){
		if(submit_appid == true){
			var appid = $('input[name="appid"]').val();
			var appSecret = $('input[name="appSecret"]').val();
			
			if (isNull(appid) || isNull(appSecret)) {
				alert('请填写完整信息');
				return ;
			}
			if(appid.length > 100){
				alert('APPID长度过长');
				return ;
			}
			if(appSecret.length > 100){
				alert('APPSecret长度过长');
				return ;
			}
			var param =  {
				appid:appid,
				appSecret:appSecret
			};
			var that = this;
			command('wx-update-appid',param, function(data){
				if(data.code == 0){
					alert('保存成功');
					submit_appid = false;
					$('input[name="appid"]').attr('readonly','readonly');
					$('input[name="appid"]').addClass('c-pointer');
					$('input[name="appSecret"]').attr('readonly','readonly');
					$('input[name="appSecret"]').addClass('c-pointer');
					$(that).removeClass('btn-submit');
					$(that).addClass('btn-edit');
					$(that).html('修 改');
				}else{
					alert(data.message);
				}
			});
		}else{
			submit_appid = true;
			$('input[name="appid"]').removeAttr('readonly');
			$('input[name="appid"]').removeClass('c-pointer');
			$('input[name="appSecret"]').removeAttr('readonly');
			$('input[name="appSecret"]').removeClass('c-pointer');
			$('input[name="appid"]').focus();
			$(this).removeClass('btn-edit');
			$(this).addClass('btn-submit');
			$(this).html('提 交');
		}
	});
	
	$('select[name="menu_type"]').change(function(event){
		var type = $(this).val();
		if('click' == type){
			$('#menu_url_tr').hide();
			$('#menu_keyword_tr').show();
		}else if('view' == type){
			$('#menu_keyword_tr').hide();
			$('#menu_url_tr').show();
		}else{
			$('#menu_keyword_tr').hide();
			$('#menu_url_tr').hide();
		}
	});
	
	$('select[name="menu_type_u"]').change(function(event){
		var type = $(this).val();
		if('click' == type){
			$('#menu_url_u_tr').hide();
			$('#menu_keyword_u_tr').show();
		}else if('view' == type){
			$('#menu_keyword_u_tr').hide();
			$('#menu_url_u_tr').show();
		}else{
			$('#menu_keyword_u_tr').hide();
			$('#menu_url_u_tr').hide();
		}
	});
});

function btn_menu_update(evnet){
	menu_operate_id = $(event.target).attr('for');
	var menu = menus[menu_operate_id];
	//var p = $(event.target).attr('p');
	var children = $('#'+menu_operate_id).children();
	$('input[name="menu_name_u"]').val(menu.name);
	var type = menu.type;
	if(!type || type == ''){
		type = 'click';
	}
	if(isNeedEdit(menu_operate_id)){
		$('select[name="menu_type_u"]').val(type);
		$('select[name="menu_keyword_u"]').val(menu.key);
		$('input[name="menu_url_u"]').val(menu.url);
		$('#menu_type_u_tr').show();
		$('#menu_keyword_u_tr').show();
		$('#menu_url_u_tr').show();
		if('click' == type){
			$('#menu_url_u_tr').hide();
		}else if('view' == type){
			$('#menu_keyword_u_tr').hide();
		}else{
			$('#menu_keyword_u_tr').hide();
			$('#menu_url_u_tr').hide();
		}
	}else{
		$('#menu_type_u_tr').hide();
		$('#menu_keyword_u_tr').hide();
		$('#menu_url_u_tr').hide();
	}
	$('input[name="menu_compare_u"]').val(menu.compare);
}

function submitAppid(){
	
	
	
}

function update_menu(event){
	var menu = menus[menu_operate_id];
	var menu_name = $('input[name="menu_name_u"]').val();
	var menu_type = $('select[name="menu_type_u"]').val();
	var menu_keyword = $('select[name="menu_keyword_u"]').val();
	var menu_url = $('input[name="menu_url_u"]').val();
	var menu_compare = $('input[name="menu_compare_u"]').val();
	
	var children = $('#'+menu_operate_id).children();
	if(isNeedEdit(menu_operate_id)){
		if (isNull(menu_name) || isNull(menu_type) || isNull(menu_compare)) {
			alert('请填写完整信息');
			return ;
		}
		if(menu_type == 'click' && isNull(menu_keyword)){
			alert('请填写完整信息');
			return ;
		}else if(menu_type == 'view' && isNull(menu_url)){
			alert('请填写完整信息');
			return ;
		}else{
			if(isNull(menu_name)){
				alert('请填写完整信息');
				return;
			}
		}
		$(children[1]).html(menu_type);
		$(children[2]).html(menu_keyword);
		//$(children[3]).html(menu_url);
	}else{
		if (isNull(menu_name) || isNull(menu_compare)) {
			alert('请填写完整信息');
			return ;
		}
	}
	$(children[0]).html($(children[0]).html().replace(menu.name,menu_name));
	$(children[4]).html(menu_compare);
	$('#menu_update_dialog').modal('hide');
	menu['name'] = menu_name;
	menu['type'] = menu_type;
	if(menu_type=="view" || menu_type=="click"){
		menu['key'] = menu_keyword;
	}else{
		menu['key'] = "";
	}
	$(children[2]).html(menu['key']);
	menu['url'] = menu_url;
	menu['compare'] = menu_compare;
}

function add_parent_click(){
	isAddParentMenu = true;
	$('input[name="menu_name"]').val('');
	$('select[name="menu_type"]').val('click');
	$('select[name="menu_keyword"]').val('');
	$('input[name="menu_url"]').val('');
	//$('input[name="menu_compare"]').val(0);
	
}

function add_children_click(){
	isAddParentMenu = false;
	$('input[name="menu_name"]').val('');
	$('select[name="menu_type"]').val('click');
	$('select[name="menu_keyword"]').val('');
	$('input[name="menu_url"]').val('');
	menu_operate_id = $(this).attr('for');
	//$('input[name="menu_compare"]').val(0);
}

function isParent(oid){
	var pid = $('#'+menu_operate_id).attr('pid');
	return pid == null || pid == '';
}

function isNeedEdit(oid){
	return $('tr[pid='+oid+']').length == 0;
}

function createMenuParent(button,pid){
	var enable = '<label class="checkbox table-menu-enable"><input id="table_check_'+pid+'" type="checkbox" checked="true" style="margin-left: auto;"></label>';
	var operate_add = '<div class="btn_menu_add menu-icon-btn" for="'+pid+'" data-toggle="modal" data-target="#menu_add_dialog" event="add_children_menu" onclick="isAddParentMenu = false;"/>';
	var operate_update = '<div for="'+pid+'" class="btn_menu_update menu-icon-btn" p="true" data-toggle="modal" data-target="#menu_update_dialog"/>';
	var operate_del = '<div class="btn_menu_del menu-icon-btn" for="'+pid+'" data-toggle="modal" data-target="#menu_del_dialog"/>';
	//var operate = operate_add + operate_update + operate_del;
	var operate = operate_del  + operate_update + operate_add ;
	var tr1 = '<tr id="'+pid+'" class="info">';
	var td1 = '<td controller="true" style="line-height: 30px;">'+button.name+'</td>';
	var td2 = '<td controller="true" style="text-align:center;">'+(button.type?button.type:'')+'</td>';
	var td3 = '<td controller="true">'+(button.key?button.key:'')+'</td>';
	var td4 = '<td style="text-align: center;">'+(button.type == 'view' ? '<a href="javascript:;" onclick="showViewUrl('+pid+')">查看</a>':'')+'</td>';
	var td5 = '<td style="text-align: center;">'+button.compare+'</td>';
	var td6 = '<td style="text-align: right;">'+operate+'</td>';
	var td7 = '<td>'+enable+'</td>';
	var tr2 = '</tr>';
	return tr1+td1+td2+td3+td4+td5+td6+td7+tr2;
}

function showViewUrl(id){
	var url = menus[id].url;
	alert(isNull(url) ? '没有设置跳转地址' : '跳转地址：'+url);
}

function createMenuChildren(s_button,s_id,pid){
	var s_enable = '<label class="checkbox table-menu-enable"><input id="table_check_'+s_id+'" type="checkbox" pid="'+pid+'" style="margin-left: auto;"></label>';
	var s_operate_update = '<div for="'+s_id+'" class="btn_menu_update menu-icon-btn" p="true" data-toggle="modal" data-target="#menu_update_dialog"/>';
	var s_operate_del = '<div class="btn_menu_del menu-icon-btn" for="'+s_id+'" data-toggle="modal" data-target="#menu_del_dialog"/>';
	//var s_operate = s_operate_update + s_operate_del;
	var s_operate =  s_operate_del + s_operate_update;
	var s_tr1 = '<tr id="'+s_id+'" pid="'+pid+'">';
	var s_td1 = '<td style="line-height: 30px;">'+s_button.name+'</td>';
	var s_td2 = '<td style="text-align:center;">'+(s_button.type?s_button.type:'click')+'</td>';
	var s_td3 = '<td >'+(s_button.type == 'view' ? '' : (s_button.key?s_button.key:''))+'</td>';
	var s_td4 = '<td style="text-align: center;">'+(s_button.type == 'view' ? '<a href="javascript:;" onclick="showViewUrl('+s_id+')">查看</a>':'')+'</td>';
	var s_td5 = '<td style="text-align: center;">'+s_button.compare+'</td>';
	var s_td6 = '<td style="text-align: right;">'+s_operate+'</td>';
	var s_td7 = '<td>'+s_enable+'</td>';
	var s_tr2 = '</tr>';
	return s_tr1+s_td1+s_td2+s_td3+s_td4+s_td5+s_td6+s_td7+s_tr2;
}



function menuTable2Json(){
	var nodes = $('#wx-menu-table').children().children();
	var buttons = {};
	for(var i = 1;i< nodes.length;i++ ){
		var node = nodes[i];
		var id = $(node).attr('id');
		var depth = $(node).attr('depth');
//		var enable = $('#table_check_'+id).val();
		var enable = isCheckBoxChecked($('#table_check_'+id));
		var menu = menus[id];
		if('1' == depth){
			var item = buttons[id];
			if(!item){
				item = buttons[id] = {
					key:menu.key,
					url:menu.url,
					name:menu.name,
					type:menu.type,
					compare:menu.compare,
					enable:enable,
					sub_button:[]
				};
			}
		}else{
			var pid = $(node).attr('pid');
			var sub_button = buttons[pid].sub_button;
			sub_button.push({
				key:menu.key,
				url:menu.url,
				name:menu.name,
				type:menu.type,
				compare:menu.compare,
				enable:enable
			});
		}
	}
	var realButton = [];
	var root_size = 0;
	for(var i in buttons){
		var button = buttons[i];
		var s_buttons = button.sub_button;
		if(s_buttons.length > 0){
			button.key = '';
			button.type = '';
			button.url = '';
		}
		if(button.enable == true){
			if(++root_size > 3){
				alert('一级菜单超出3个');
				return false;
			}
		}
		var level2_size = 0;
		for(j in s_buttons){
			var s_button = s_buttons[j];
			if(s_button.enable == true){
				if(++level2_size > 5){
					alert('一级菜单['+button.name+']下的二级菜单超出5个');
					return false;
				}
			}
		}
		realButton.push(button);
	}
	return realButton;
}

function empty(){}

