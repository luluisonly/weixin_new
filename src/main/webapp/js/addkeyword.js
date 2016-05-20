var click_id="";//记录点击
var zsc_count = 0;//记录编辑
var flag = true;

//查看详细信息
function zsc_show(id){
	if(!flag){
		$('#new_reply div').remove();
		$('#new_reply').hide();
		flag = true;
	}
	var zsc_state = $('#zsc_btninfo'+id).attr('state');
	$('#zsc_btninfo'+click_id).parent().parent().css('border','#cccccc 1px solid');
	if(zsc_state!='add'){
		$('#zsc_btninfo'+id).attr('state','add');
		$('#zsc_btninfo'+id).text('展开');
		$('#zsc_btninfo'+id).parent().parent().addClass("hover");
		//$(event.target.parentNode.parentNode).removeClass("chosed");
		click_id="";
		$('#zsc_content_'+id+' p').show();
		$('#zsc_content_'+id+' div').remove();
	}else{
		$('#zsc_btninfo'+id).parent().parent().removeClass("hover");
		$('#zsc_btninfo'+id).parent().parent().css('border','1px solid rgb(63,151,240)');
		$('#zsc_btninfo'+id).attr('state','add dec');
		$('#zsc_btninfo'+id).text('收起');
		if(click_id!=""){
			$('#zsc_content_'+click_id+' p').show();
			$('#zsc_content_'+click_id+' div').remove();
			$('#zsc_btninfo'+click_id).attr('state','add');
			$('#zsc_btninfo'+click_id).text('展开');
			$('.list').removeClass("chosed");
		}
		//$(event.target.parentNode.parentNode).addClass("chosed");
		click_id = id;
		var zsc_id = id;
		var key_words = $('#key_replykeywords_'+id).val();
		var reply_type = $('#key_replytype_'+id).val();
		var replyCntent = $('#key_replycontent_'+id).val();
		var v = {
			type:reply_type,
			kyword:key_words
		};
		if("text" == reply_type){
			v.kecontent = replyCntent;
		}else if("news" == reply_type){
			v.titles = JSON.parse(replyCntent);
		}else if("remote_text"==reply_type){
			var jsonobj = JSON.parse(replyCntent);
			v.remotetype = $('#key_remotetype_'+id).val();
			v.url =jsonobj.url;
		}else if('yigo_bill' == reply_type){
			v.bill = replyCntent;
			v.targetPath =  $('#key_yigo_path_'+id).val();
		}
		var zsc_html = "";
		if("remote_text"!=reply_type){
			zsc_html = create_zsc_html(id);
		}else {
			zsc_html = create_remote_html(id,'update');
		}
		$('#zsc_content_'+zsc_id+' p').hide();
		$('#zsc_content_'+zsc_id+' div').remove();
		$('#zsc_content_'+zsc_id).append(zsc_html);
		if(v.type=="text"){
			check_tuwen(0);
			$('#zsc_kword').val(v.kyword);
			$('#zsc_kecontent').val(v.kecontent);
		}else if(v.type =="news"){
			check_tuwen(1);
			$('#zsc_kword').val(v.kyword);
			$.each(v.titles,function(i,info){
				if(i>0){
					zsc_addimg(i);
					$('#zsc_imgs'+i+' span').html(info.title);
					$('#zsc_simg'+i).attr('src',info.picUrl.substring(info.picUrl.indexOf('m.do')));
				}else{
					$('#zsc_imgs0').attr('src',info.picUrl.substring(info.picUrl.indexOf('m.do')));
					$('#zsc_titles0').html(info.title);
					$('#zsc_titles').val(info.title);
				//	$('#zsc_imgurls').val(attachment_server + info.picUrl.substring(info.picUrl.indexOf('m.do')));
					$('#zsc_imgurls').val(info.picUrl);
					$('#zsc_urls').val(info.url);
				}
				$('#zsc_t'+i).val(info.title);
				$('#zsc_i'+i).val(info.picUrl);
				$('#zsc_urls'+i).val(info.url);
			});
		}else if(v.type=="yigo_bill"){
			check_tuwen(2);
			$('#zsc_kword').val(v.kyword);
			$('#zsc_yigo_bill').val(v.bill);
			$('#zsc_yigo_path').val(v.targetPath);
		}else if(v.type=="remote_text"){
			$('#zsc_kword').val(key_words);
			$('#zsc_remote_type').val(v.remotetype);
			$('#zsc_remote_url').val(v.url);
		}
	}
}

//加载默认回复
function msg_show(){
	var msg_type = $('#msg_replytype').val();
	var msg_replycontent = $('#msg_replycontent').val();
	var v = {
		type:msg_type
	};
	if('text' == msg_type){
		check_tuwen(0);
		v.kecontent = msg_replycontent;
	}else if('news' == msg_type){
		check_tuwen(1);
		v.titles = JSON.parse(msg_replycontent);
	}
	if(v.type=='text'){
		$('#zsc_kecontent').val(v.kecontent);
	}else if(v.type == 'news'){
		$.each(v.titles,function(i,info){
			if(i>0){
				zsc_addimg(i);
				$('#zsc_simg'+i).attr('src',info.picUrl.substring(info.picUrl.indexOf('m.do')));
			}else{
				$('#zsc_imgs0').attr('src',info.picUrl.substring(info.picUrl.indexOf('m.do')));
				$('#zsc_titles0').html(info.title);
				$('#zsc_titles').val(info.title);
				$('#zsc_imgurls').val(info.picUrl);
				$('#zsc_urls').val(info.url);
			}
			$('#zsc_t'+i).val(info.title);
			$('#zsc_i'+i).val(info.picUrl);
			$('#zsc_urls'+i).val(info.url);
		});
	}
}

//图文判断
function check_tuwen(num){
	if(num==0){
		$('#wen').show();
		$('#tu').hide();
		$('#yigo').hide();
		$('#ch_wen').attr('checked','checked');
	}else if(num == 1){
		$('#wen').hide();
		$('#tu').show();
		$('#yigo').hide();
		$('#ch_tu').attr('checked','checked');
		$('#zsc_imgurls').blur(saveImgUrl);
	}else if(num == 2){
		$('#wen').hide();
		$('#tu').hide();
		$('#yigo').show();
		$('#ch_yigo').attr('checked','checked');
	}
}

//图文信息新增一条
function zsc_addimg(num){
	if($('.simgcon').length<9){
		$('#zsc_add1bg').remove();
		var zsc_html = "<a href='javascript:;' class='simgcon' id='zsc_imgs"+num+"'>"+
					   "<span>标题</span>"+
					   "<img src='img/smallimg.jpg' id='zsc_simg"+num+"' />"+
					   "<div class='smodify'>"+
					   "<div class='tuwen-btn-edit' onclick='zsc_editimg("+num+")'></div>&nbsp;"+
					   "<div class='tuwen-btn-close' onclick='zsc_delimg("+num+")'></div></div>"+
					   "<input type='hidden' value='"+num+"' class='simgcons' />"+
					   "<input type='hidden' value='' id='zsc_urls"+num+"' class='addurls'/>"+
					   "<input type='hidden' value='' class='addimg' id='zsc_i"+num+"'/>"+
					   "<input type='hidden' value='' class='addtitle' id='zsc_t"+num+"'/>"+
					   "</a>";
		$('.tuwen_left').append(zsc_html);
		num = parseInt(num)+1;
		zsc_html = "<div style='width:295px;' id='zsc_add1bg'>"+
				   "<button class='add1' style='cursor:pointer;' type='button' onclick='zsc_addimg("+num+")'>新增一条</button>"+
				   "</div>";
		$('.tuwen_left').append(zsc_html);
	}else{
		alert('图文信息上限！');
	}
}

//删除某一条图文信息
function zsc_delimg(num){
	if(!confirm('是否删除该图文信息')){
		return false;	
	}
	$('#zsc_imgs'+num).remove();
	$('.tuwen_right').css('top','1px');
	$('#zsc_titles').val($('#zsc_titles0').html());
	$('#zsc_urls').val($('#zsc_urls0').val());
}

//编辑某一条图文信息
function zsc_editimg(num){
	var count = num;
	zsc_count = num;
	if (count == 0) {
		num = 0;
	}else{
		num = parseInt(count)*81+1;
	}
	$('.tuwen_right').css('top',num+'px');
	$('.imgcon').removeClass("chosed");
	$('.simgcon').removeClass("chosed");
	$(event.target.parentNode.parentNode).addClass("chosed");
	if(0==zsc_count){
		$('#zsc_titles').val($('#zsc_titles0').html());
		$('#zsc_urls').val($('#zsc_urls0').val());
		if('bigimg.jpg'==$('#zsc_imgs0').attr('src')){
			$('#zsc_imgurls').val();
		}else{
			$('#zsc_imgurls').val($('#zsc_imgs0').attr('src'));
		}
	}else{
		$('#zsc_titles').val($('#zsc_imgs'+zsc_count+" span").html());
		$('#zsc_urls').val($('#zsc_urls'+zsc_count).val());
		var ssurl = $('#zsc_simg'+zsc_count).attr('src');
		if('smallimg.jpg'!=ssurl){
			$('#zsc_imgurls').val($('#zsc_simg'+zsc_count).attr('src'));
		}else{
			$('#zsc_imgurls').val('');
		}
	}
}

//标题改变
function zsc_keytitle(){
	var zsc_title = $('#zsc_titles').val();
	if(0==zsc_count){
		if(""==zsc_title){
			$('#zsc_titles0').html('标题');
			$('#zsc_t0').val('');
		}else{
			$('#zsc_titles0').html(zsc_title);
			$('#zsc_t0').val(zsc_title);
		}
	}else{
		if(""==zsc_title){
			$('#zsc_imgs'+zsc_count+" span").html('标题');
			$('#zsc_t'+zsc_count).val('');
		}else{
			$('#zsc_imgs'+zsc_count+" span").html(zsc_title);
			$('#zsc_t'+zsc_count).val(zsc_title);
		}
	}
}

//链接改变
function zsc_urlinfo(){
	var zsc_url = $('#zsc_urls').val();
	if(0==zsc_count){
		$('#zsc_urls0').val(zsc_url);
	}else{
		$('#zsc_urls'+zsc_count).val(zsc_url);
	}
}

//显示添加新回复
function addreply(){
		var zsc_state = $('#zsc_btninfo'+click_id).attr('state');
		if(zsc_state!='add'){
			$('#zsc_btninfo'+click_id).attr('state','add');
			$('#zsc_btninfo'+click_id).text('展开');
			//$('.list').removeClass("chosed");
			$('#zsc_content_'+click_id+' p').show();
			$('#zsc_content_'+click_id+' div').remove();
			click_id="";
		}
		if(flag){
			var zsc_html = create_zsc_html(0);
			$('#new_reply').append(zsc_html);
			//$('#new_reply').addClass("chosed");
			$('#new_reply').show();
			check_tuwen(0);
			flag = false;
		}else{
			$('#new_reply div').remove();
			$('#new_reply').hide();
			flag = true;
		}
}
//添加文本远程消息
function addremote(){
	var zsc_state = $('#zsc_btninfo'+click_id).attr('state');
	if(zsc_state!='add'){
		$('#zsc_btninfo'+click_id).attr('state','add');	
		$('#zsc_btninfo'+click_id).text('展开');
		$('#zsc_content_'+click_id+' p').show();
		$('#zsc_content_'+click_id+' div').remove();
		click_id="";
	}
	if(flag){
		var zsc_html = create_remote_html(0,'add');
		$('#new_reply').append(zsc_html);
		$('#new_reply').show();
		flag = false;
	}else{
		$('#new_reply div').remove();
		$('#new_reply').hide();
		flag = true;
	}
}

//取消按钮
function btnfalse(num){
	$('#zsc_btninfo'+click_id).parent().parent().css('border','#E7EAEB 1px solid');
	if(0==num){
		$('#new_reply div').remove();
		$('#new_reply').hide();
		flag = true;
	}else{
		$('#zsc_btninfo'+click_id).attr('state','add');
		$('#zsc_btninfo'+click_id).text('展开');
		$('#zsc_content_'+click_id+' p').show();
		$('#zsc_content_'+click_id+' div').remove();
		click_id="";
	}
}

//关闭遮罩层
function zsc_close(){
	$('.showimg').hide();
	$('.zhe').hide();
}

//弹出遮罩层
function choseImg(){
	$('.showimg').show();
	$('.zhe').show();
}

//删除图片
function delImg(num){
	if(!confirm('是否删除该图片')){
		return false;	
	}
	var path = $('#zsc_imgid'+num).attr('src');
	var fileName = path.substring(path.lastIndexOf('=')+1);
	var params={fileName:fileName};
	command("wx-delete-image", params, function(data){
		$('.loadsubmit').html('正在删除...');
		$('.loadsubmit').fadeToggle();
		if(data.code != 0){
			alert(data.message);
			return false;
		}else{
			$('.loadsubmit').html('删除成功！');
			$('.loadsubmit').fadeToggle(1000);
			choseImg();
		}
	});
	$(event.target.parentNode).remove();
}

//选择图片
var chosenum;//记录选择的图片
function choseImages(iid){
	if(''!=chosenum){
		$('#zsc_imgid'+chosenum).css('borderColor','#CCC');
	}
	$('#zsc_imgid'+iid).css('borderColor','#F00');
	chosenum=iid;
}
//确定图片的选择
$(function(){
	$('#zsc_surebtn').click(function(){
		var zsc_src = $('#zsc_imgid'+chosenum).attr('src');
	
		$('#zsc_imgurls').val(zsc_src);
		zsc_close();
		if(0==zsc_count){
			$('#zsc_imgs0').attr('src',zsc_src);
			$('#zsc_i0').val(zsc_src);
		}else{
			$('#zsc_simg'+zsc_count).attr('src',zsc_src);
			$('#zsc_i'+zsc_count).val(zsc_src);
		}
	});	
});

function endWith(str1,str2){
	if(str2==null||str2==""||str1.length==0||str2.length>str1.length)
		return false;
	return str1.substring(str1.length-str2.length)==str2;
}
var fileUploadName;
var fileUploadNameWithSubFix;
//图片上传
function zsc_upload(){
	var path = $("input[name='fileName']").val();
	if(!endWith(path,".jpg") && !endWith(path,".jpeg") && !endWith(path,".png")&&!endWith(path,".JPG") && !endWith(path,".JPEG") && !endWith(path,".PNG")){
		alert('仅限png,jpeg,jpg ');
		return ;
	}
	fileUploadName = path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."));
	fileUploadNameWithSubFix = path.substring(path.lastIndexOf("\\")+1);
	if($('#zsc_imgid'+fileUploadName).length == 1){
		alert('已存在同名文件！');
		return;
	}else{
		$('.loadsubmit').html('正在上传...');
		$('.loadsubmit').fadeToggle();
		var zsc_submit = $('#zsc_myform');
		zsc_submit.submit();
		window.setTimeout(function(){
			$('.loadsubmit').html('上传成功！');
			$('.loadsubmit').fadeToggle(1500);
			var html = "<a href='javascript:choseImages("+picNum+");'><img id='zsc_imgid"+picNum+"'  src='m.do?type=wx-download-image&filePath="+encodeURI(uname+"/"+fileUploadNameWithSubFix)+"&completeName="+fileUploadNameWithSubFix
				+"' width='100' height='100'style='border-color: rgb(204, 204, 204);'><span onclick='delImg("+picNum+")'>X</span></a>";
			picNum ++;
			$('.imgbox').append(html);
			choseImg();
		},3000);
	}
}

//添加、修改 消息
function btnsave(type,msgType,action){
	var keyword = $('#zsc_kword').val();//关键字
	var oldkeyword =$('#key_replykeywords_'+click_id).val();//记录旧关键字
	if(''==keyword){
		alert('请输入关键字');
		return false;
	}
	var jsonobj = formjson(keyword, msgType);
	if(jsonobj == false){
		return;
	}
	var params ={
			jsonobj:jsonobj,
			action:action,
			keyword:keyword,
			oldkeyword:oldkeyword
	};
	command(type, params, function(data){
		if(data.code == 0){
			if(action=="add")
				alert('添加成功！');
			else
				alert('修改成功！');	
			window.location.reload();
		}else{
			alert(data.message);
		}
	});
}

//保存默认及关注回复
function msgsave(type,msgType){
	var jsonobj = formjson('', msgType);
	if(jsonobj == false){
		return;
	}
	var params={jsonobj:jsonobj};
	command(type, params, function(data){
		if(data.code == 0){
			alert('修改成功！');
			window.location.reload();
		}else{
			alert(data.message);
		}
	});
}

//删除某条信息
function zscdel(num){
	var keyword = $('#key_replykeywords_'+num).val();
	if(!confirm('是否删除')){
		return false;	
	}
	var params ={
		keyword:keyword,
		action:"delete"	
	};
	command('wx-update-keyword-reply', params, function(data){
		if(data.code == 0){
			alert('删除成功！');
			window.location.reload();
		}else{
			alert(data.message);
		}
		
	});
}

//JSON格式化
function formjson(keyword,msgtype){
	var jsonobj={};
	if(keyword!='') {
		var keywords = keyword.split(' ');
		for(var i in keywords){
			var key = keywords[i];
			if(key.length > 32){
				alert('关键词长度超过限制，最长为32个字符');
				return false;
			}
		}
		if(keyword.length > 165){
			alert('关键词组合长度超过限制，最长为165个字符');
			return false;
		}
		jsonobj['serviceKey']= keyword;
	}
	if(msgtype=='text'){
		jsonobj['msgType']=msgtype;
		var keycontent = $('#zsc_kecontent').val();//内容
		if(''==keycontent){
			alert('请输入内容');
			return false;
		}
		if(keycontent.length > 1024){
			alert('文本回复内容长度超过限制，最长为1024字节');
			return false;
		}
		jsonobj['content']=keycontent;
	}else if(msgtype=='news'){
		jsonobj['msgType']=msgtype;
		var titles = $('.addtitle'); 
		var arrTitles = [];	//标题数组
		var imgurls = $('.addimg'); 	//图片路径数组
		var arrImgurls = [];
		var curl = $('.addurls');		//链接路径数组
		var arrCurl = [];
		var flag = true;
		var mark = true;
		$.each(titles,function(i,v){
			if('标题'==v.value||''==v.value||null==v.value){
				flag = false;
			}
			if(v.value.length > 64){
				alert('第'+(i+1)+'个图文消息的标题长度超过限制，最长为64个字节');
				mark = false;
			}
			arrTitles[i]=v.value;
		});
		if(!mark) return false;
		$.each(imgurls,function(i,v){
			if(''==v.value||null==v.value){
				flag = false;
			}
			arrImgurls[i]=v.value;
		});
		$.each(curl,function(i,v){
			if(''==v.value||null==v.value){
				flag = false;
			}
			if(v.value.length > 1024){
				alert('第'+(i+1)+'个图文消息的链接长度超过限制，最长为1024个字节');
				mark = false;
			}
			arrCurl[i]=v.value;
		});
		if(!mark) return false;
		if(titles.length == 0 || imgurls.length == 0 || curl.length == 0){
			flag = false;
		}
		if(!flag){
			alert('请输入完整信息再提交');
			return false;
		}
		var jsonarr = new Array();
		var length = arrTitles.length;
		for(i = 0;i<length;i++){
			var innerjson={};
			innerjson['title'] = arrTitles[i];
			innerjson['picUrl'] = arrImgurls[i].substring(0,4) == 'http' ? arrImgurls[i] : attachment_server+arrImgurls[i].substring(arrImgurls[i].indexOf('m.do'));
			innerjson['url'] = arrCurl[i];
			jsonarr[i]=innerjson;
		}
		jsonobj['articles']=jsonarr;
	}else if('yigo_bill' == msgtype){
		jsonobj['msgType']=msgtype;
		jsonobj['bill']=$('#zsc_yigo_bill').val();
		jsonobj['targetPath']=$('#zsc_yigo_path').val();
	}else{
		jsonobj['msgType']=msgtype;
		jsonobj['remoteType']=$('#zsc_remote_type').val();
		jsonobj['url']=$('#zsc_remote_url').val();
	}
	return JSON.stringify(jsonobj);
}
function create_zsc_html(id){
	var f = "<input type='text' class='text1' value='' id='zsc_kword' />"+
		   "</div>"+
		   "<div>"+
		   "<div style='float:left;'>回复内容设置</div><div style='border-bottom: #cccccc 1px solid;float: left;width: 663px;margin: 10px 0px 0px 10px;'></div>"+
		   "<br>"+
		   "<br>"+
		   "<div style='float:left;' onclick='$(\"#ch_wen\")[0].checked = true;check_tuwen(0);' ><div style='float:left;margin: -1px 8px 0px 0px;'><input type='radio' onclick='check_tuwen(0)' id='ch_wen' checked='checked' name='type'></div><div style='float:left;cursor: pointer;'>文本</div></div>"+
		   "<div style='float:left;' onclick='$(\"#ch_tu\")[0].checked = true;check_tuwen(1);' ><div style='float:left;margin: -1px 8px 0px 25px;'><input type='radio' onclick='check_tuwen(1)' id='ch_tu' name='type'></div><div style='float:left;cursor: pointer;'>图文</div></div>"+
		  // "<div style='width:231px' onclick='$(\"#ch_yigo\")[0].checked = true;check_tuwen(2);'><div style='float:left;margin: -1px 8px 0px 25px;'><input type='radio' onclick='check_tuwen(2)' id='ch_yigo' name='type'></div><div style='cursor: pointer;'>Yigo单据</div></div>"+
		   "</div>"+
		   //文本信息
		   "<div class='clear'  id='wen'>"+
		   "<div style='margin-top: 40px;'>"+
		   "<textarea id='zsc_kecontent' style='width:100%;height:137px; border:#ccc solid 1px;'></textarea>"+
		   "</div>"+
		   "<div style=' padding-top:45px; clear:both;'>"+
		   "<p style='float: right;'>"+
		   "<button class='btn btn-large btn-submit' onclick='btnsave(\"wx-update-keyword-reply\",\"text\",\"update\")' style='width:120px;'>保存</button>&emsp;"+
		   "<button class='btn btn-large btn-edit' style='margin-top: -3px;' onclick='javascript:btnfalse("+id+");'>取消</button></p>"+
		   "</div>"+
		   "</div>"+
		   //图文信息
		   "<div class='clear' id='tu'>"+
		   "<div class='tuwen'>"+
		   "<div class='tuwen_left'>"+
		   "<a href='javascript:;' class='imgcon'>"+
		   "<img id='zsc_imgs0' src='img/bigimg.jpg' width='295' height='126'/>"+
		   "<div class='tuwen-head-image'></div>"+
		   "<span id='zsc_titles0'>标题</span>"+
		   "<div class='modify'><div class='tuwen-btn-edit' onclick='zsc_editimg(0)'></div></div>"+
		   "<input type='hidden' value='' class='addtitle' id='zsc_t0'/>"+
		   "<input type='hidden' value='' class='addimg' id='zsc_i0'/>"+
		   "<input type='hidden' value='' class='addurls' id='zsc_urls0' />"+
		   "</a>"+
		   "<div style='width:295px;' id='zsc_add1bg'>"+
		   "<button class='add1' style='cursor:pointer;' type='button' onclick='zsc_addimg(1)'>新增一条</button>"+
		   "</div>"+
		   "</div>"+
		   "<div class='tuwen_right' style='top: 1px;'>"+
		   "<img src='./img/tuwen-left.png'>"+
		   "<p>"+
		   "<br />"+
		   "标题：<input id='zsc_titles' type='text' style='width:360px;' onkeyup='zsc_keytitle()' onchange='zsc_keytitle()' />"+
		   "<br><br>"+
		   "封面：<input type='text' style='width:360px' id='zsc_imgurls'/>"+
//		   "<input class='btn btn-blue' type='button' value='选择图片' onclick='choseImg()'>"+
		   "<br><br>"+
		   "链接：<input id='zsc_urls' type='text' style='width:360px'"+
		   "onkeyup='zsc_urlinfo()' onchange='zsc_urlinfo()' />"+
		   "</p>"+
		   "<div style='float: right;margin-top:6px;'>"+
		   "<button class='btn btn-large btn-submit' onclick='btnsave(\"wx-update-keyword-reply\",\"news\",\"update\")' style='width:120px;'>保存</button>&emsp;"+
		   "<button class='btn btn-large btn-edit' onclick='javascript:btnfalse("+id+");' style='width:120px;margin-right: 3px;margin-top: -3px;'>取消</button></div>"+
		   "</div>"+
		   "</div>"+
		   "</div>"+
		   //yigo单据
		   /*
		   "<div class='clear' id='yigo'>"+
		   "<div class='edit-info' style='padding-top:20px;'>"+
		   "<span>单据名：</span><input id='zsc_yigo_bill' type='text' class='text1' style='margin-left: 14px;'/>"+
		   "<span>tgPath：</span><input id='zsc_yigo_path' type='text' class='text1' style='margin-left: 15px;margin-top: 15px;' value='weixin/weixin.jsp'/>"+
		   "</div>"+
		   "<div style=' padding-top:45px; clear:both;'>"+
		   "<p style='float: right;'>"+
		   "<button class='btn btn-large btn-submit' onclick='btnsave(\"wx-update-keyword-reply\",\"yigo_bill\",\"update\")' style='width:120px;'>保存</button>&emsp;"+
		   "<button class='btn btn-large btn-edit' onclick='javascript:btnfalse(1);' style='width:120px;margin-right: 3px;margin-top: -3px;'>取消</button></div>"+
		   */
		   "</div>"+
		   "</div>";
	if(id != 0){
		return "<div class='edit-info'>"
				+"<input type='hidden' value='"+id+"' id='zsc_iid' />"
				+"<span>关键词：</span>&emsp;"+f;
	}else{
		return "<div><h3>添加新的回复</h3></div>"
				+"<div class='edit-info'>"
				+"<span>关键词：</span>&emsp;"+f;
	}	   
}

function create_remote_html(id,action){
	var f =	"<div class='edit-info'>"+
			"<table style='width:100%;'><tbody><tr>"+
			"<th width='100'>服务名：</th>"+
			"<td><input type='text' style='width:100%' id='zsc_kword' /></td>"+
			"</tr><tr><th>调用类型：</th>"+
			"<td><select style='width:100%;' id='zsc_remote_type'><option value='http'>HTTP</option><option value='yigo'>YIGO</option><option value='local'>LOCAL</option></select></td>"+
			"</tr><tr><th>调用地址：</th>"+
			"<td><input type='text' style='width:100%;' id='zsc_remote_url' /></td>"+
			"</tr></tbody></table>"+
			"<div style='padding-top: 45px; clear: both;'>"+	
			"<p style='float: right;'>"+
			"<button class='btn btn-large btn-submit' onclick='btnsave(\"wx-update-remote-text\",\"remote_text\",\""+action+"\")' style='width:120px;'>保存</button>&emsp;"+
			"<button class='btn btn-large btn-submit' onclick='javascript:btnfalse(0);' style='width:120px;'>取消</button>"+
			"</div>";
	if(id!=0){
		return f;
	}else{
		return "<div><h3>添加文本远程消息</h3></div>"+f;
	}
}

// 加载关键词自动回复列表
function createThumb(replys){
	var html = '';
	var number = 1;
	for(var i in replys){
		var r = replys[i];
		html = html+ "<div class='list'><h3>关键字："+r.serviceKey+
		"<a href='javascript:;' class='btn btn-small' onclick='zscdel("+number+")' style='margin-left:3px; float: right;display:none;'>删除</a>&emsp;"+
		"<a href='javascript:;' class='btn btn-small' state='add' id='zsc_btninfo"+number+"' onclick='zsc_show("+number+")' style='float: right;display:none;'>展开</a></h3>"+
		"<div id='zsc_content_"+number+"'><p>回复："+getMessageType(r.msgType)+"</p>"+
		"<input id='key_replytype_"+number+"' value='"+r.msgType+"' style='display:none;'></input>"+
		"<input id='key_replykeywords_"+number+"' value='"+r.serviceKey+"' style='display:none;'></input>"+
		"<textarea id='key_replycontent_"+number+"' style='display:none;'>"+messageToContent(r)+"</textarea></div></div>"+
		"<input id='key_yigo_path_"+number+"' value='"+r.targetPath+"' style='display:none;'></input>";
		number++ ;
	}
	return html;
}

function createRemoteList(replys){
	var html = '';
	var number = 1;
	for(var i in replys){
		var reply = replys[i];
		html = html +"<div class='list'><h3>服务名："+reply.serviceKey+
		"<a href='javascript:;' class='btn btn-small' onclick='zscdel("+number+")' style='margin-left:3px; float: right;display:none;'>删除</a>&emsp;"+
		  "<a href='javascript:;' class='btn btn-small' state='add' id='zsc_btninfo"+number+"' onclick='zsc_show("+number+")' style='float: right;display:none;'>展开</a></h3>"+
	      "<div id='zsc_content_"+number+"'>"+
    	  "<input id='key_replytype_"+number+"' value='"+reply.msgType+"' style='display:none;'></input>"+
    	  "<input id='key_remotetype_"+number+"' value='"+reply.remoteType+"' style='display:none;'></input>"+
	  	  "<input id='key_replykeywords_"+number+"' value='"+reply.serviceKey+"' style='display:none;'></input>"+
    	  "<input id='key_replycontent_"+number+"' value='"+messageToContent(reply)+"' style='display:none;'></input></div></div>";
		number++;
	}
	return html;
}

//加载已上传的图片列表
var picNum = 1;
function createPicList(filenames,uname){
	var html='';
	for(var i in filenames){
		var str = filenames[i];
		var completeName = str.substring(str.lastIndexOf("/")+1);
		html = html+"<a href='javascript:choseImages("+picNum+");'><img id='zsc_imgid"+picNum+
		"' src='m.do?type=wx-download-image&filePath="+uname+"/"+completeName+"&fileName="+completeName+
		"' width='100' height='100' style='border-color: rgb(204, 204, 204);'/><span onclick='delImg("+picNum+")'>X</span></a>";
		picNum++;
	}
	return html;
}


function saveImgUrl(){
	var zsc_src = $('#zsc_imgurls').val();
	if(0==zsc_count){
		$('#zsc_imgs0').attr('src',zsc_src);
		$('#zsc_i0').val(zsc_src);
	}else{
		$('#zsc_simg'+zsc_count).attr('src',zsc_src);
		$('#zsc_i'+zsc_count).val(zsc_src);
	}
}

function messageToContent(r){
	if ("text" == r.msgType) {
		return r.content;
	}else if ("news" == r.msgType) {
		return JSON.stringify(r.articles);
	}else if ("remote_text" == r.msgType) {
		return JSON.stringify(r);
	}else if ('yigo_bill' == r.msgType) {
		return r.bill;
	}
	return null;
}

function getMessageType(msgType){
	if ('text' == msgType) {
		return "文本类型";
	}else if ('news' == msgType) {
		return "图文类型";
	}else if ('yigo_bill' == msgType) {
		return "Yigo单据类型";
	}
	return null;
}

		
