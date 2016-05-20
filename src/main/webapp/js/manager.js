	var click_id="";//记录点击
	var zsc_count = 0;//记录编辑
	//查看详细信息
	
	$(function(){
//		command('wx-get-publicnumbers',null,function(data){
//			if(data.code == 0){
//				var d = data.data;
//				var h = '';
//				for(var i in d){
//					var publicnumber = d[i];
//					h = h + "<div class='list' style='position: relative'><h3>公众号："+publicnumber.uname+
//					  "<a href='javascript:;' class='btn btn-small' state='add' id='zsc_btninfo"+publicnumber.number+"' onclick='zsc_show("+publicnumber.number+")' style='float: right;'>展开</a></h3>"+
//					  "<div id='zsc_content_"+publicnumber.number+"'><p>状态："+(publicnumber.status==0?"试用":publicnumber.status==1?"使用":"禁用")+"</p>"+
//			          "<input id='status_"+publicnumber.number+"' value='"+publicnumber.status+"' style='display:none;'></input>"+
//					  "<input id='uname_"+publicnumber.number+"' value='"+publicnumber.uname+"' style='display:none;'></input>"+
//			          "<input id='service_time_"+publicnumber.number+"' value='"+publicnumber.service_time+"' style='display:none;'></input></div></div>";
//				}
//				$('.cos-major').append(h);
		$(".cos-major>.list").hover(function(event) {
			var that = this;
			$($(this).parent().children()).each(function(i,item){
				if(item == that){
					$(item).addClass("hover");
					$(item).find("h3>a").css("display","block");
				}else{
					$(item).removeClass("hover");
					if(!$(item).hasClass("chosed")){
						$(item).find("h3>a").css("display","none");
					}
				}
			});
		},
		function(){
			$(event.target).removeClass("hover");
			if(!$(event.target).hasClass("chosed")){
				$(event.target).find("h3>a").css("display","none");
			}
		});
	});
	
	function zsc_show(id){
		var zsc_state = $('#zsc_btninfo'+id).attr('state');
		if(zsc_state!='add'){
			$('#zsc_btninfo'+id).attr('state','add');
			$('#zsc_btninfo'+id).text('展开');
			$(event.target.parentNode.parentNode).removeClass("chosed");
			click_id="";
			$('#zsc_content_'+id+' p').show();
			$('#zsc_content_'+id+' div').remove();
		}else{
			$('#zsc_btninfo'+id).attr('state','add dec');
			$('#zsc_btninfo'+id).text('收起');
			if(click_id!=""){
				$('#zsc_content_'+click_id+' p').show();
				$('#zsc_content_'+click_id+' div').remove();
				$('#zsc_btninfo'+click_id).attr('state','add');
				$('#zsc_btninfo'+click_id).text('展开');
				$('.list').removeClass("chosed");
			}
			$(event.target.parentNode.parentNode).addClass("chosed");
			click_id = id;
			var zsc_id = id;
			var uname = $('#uname_'+id).val();
			var status = $('#status_'+id).val();
			var service_time = $('#service_time_'+id).val();
			var zsc_html = "";
			zsc_html = "<div class='edit-info'>"+
			   "<table><tbody><tr>"+
			   "<th width='150'>公众号名称：</th>"+
			   "<td><input type='text' class='text1' value='' id='zsc_uname' disabled='true' style='width:578px;'/></td>"+
			   "</tr><tr><th>公众号状态：</th>"+
			   "<td><select class='text1' id='zsc_status' onchange='check_status();' style='width:578px;'><option value=0>试用</option><option value=1>使用</option><option id='opt_forbid' value=-1>禁用</option></select></td>"+
			   "</tr><tr><th>过期时间：</th>"+
			   "<td><input type='text' class='text1' value='' id='zsc_service_time' readOnly='readOnly' style='cursor:pointer;width:578px;'/></td>"+
			   "</tr></tbody></table>"+
			   "<div style='padding-top: 45px; clear: both;'>"+	
			   "<p style='float: right;'>"+
			   "<button class='btn btn-large btn-info' onclick='btnsave(\"wx-manager\")' style='width:100px;'>保存</button>&emsp;"+
			   "<button class='btn btn-large btn-info' onclick='javascript:btnfalse();' style='width:100px;'>取消</button>"+
			   "</div>";
			$('#zsc_content_'+zsc_id+' p').hide();
			$('#zsc_content_'+zsc_id+' div').remove();
			$('#zsc_content_'+zsc_id).append(zsc_html);
			$('#zsc_uname').val(uname);
			$('#zsc_status').val(status);
			$('#zsc_service_time').datepicker({ dateFormat: "yy-mm-dd" ,minDate: new Date()});
			$('#zsc_service_time').datepicker( "setDate", new Date(parseInt(service_time)));
		}
	}
	
	//保存对公众号的修改
	function btnsave(type){
		var uname = $('#zsc_uname').val();//公众号
		var staus = $('#zsc_status').val();
		var service_time = $('#zsc_service_time').val();
		if(''==service_time){
			alert('请输入过期时间');
			return false;
		}
		var params ={
				uname:uname,
				staus:staus,
				service_time:service_time,
		};
		command(type, params, function(data){
			if(data.code == 0){
				alert('修改成功！');	
			}else if(data.code == 1000){
				window.location.href = 'manager-login.jsp';
			}else{
				alert(data.message);
			}
			window.location.reload();
		});
		
	}
	
	//取消按钮
	function btnfalse(){
			$('#zsc_btninfo'+click_id).attr('state','add');
			$('#zsc_btninfo'+click_id).text('展开');
			$('#zsc_content_'+click_id+' p').show();
			$('#zsc_content_'+click_id+' div').remove();
			click_id="";
	}
	
	//禁用状态锁定日期控件
	function check_status(){
		var status = $('#zsc_status').val();
		if(status == -1){
			$('#zsc_service_time').datepicker("option", "disabled", true );
		}
	}
	