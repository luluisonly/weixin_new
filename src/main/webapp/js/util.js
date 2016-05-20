String.prototype.Trim = function() { return this.replace(/(^\s*)|(\s*$)/g, ""); };
String.prototype.LTrim = function() { return this.replace(/(^\s*)/g, ""); };
String.prototype.RTrim = function() { return this.replace(/(\s*$)/g, ""); };
function command(m,p,f){
	if(!m){
		return;
	}
	$.post('/weixin-debug/m.do?type='+m,p,f,'json');
}

$(function(){
	var height = $(window).height();
	//$('.body').css('height',height*0.8+'px');
	$('.left-menu').css('height',height*0.85+'px');
});

//alert($(window).height()); //浏览器时下窗口可视区域高度 
//alert($(document).height()); //浏览器时下窗口文档的高度 
//alert($(document.body).height());//浏览器时下窗口文档body的高度 
//alert($(document.body).outerHeight(true));//浏览器时下窗口文档body的总高度 包括border padding margin 
//alert($(window).width()); //浏览器时下窗口可视区域宽度 
//alert($(document).width());//浏览器时下窗口文档对于象宽度 
//alert($(document.body).width());//浏览器时下窗口文档body的高度 
//alert($(document.body).outerWidth(true));//浏览器时下窗口文档body的总宽度 包括border padding margin 

function isNull(s){
	return s == null || s.Trim() == '';
}

function button_click(eventName,fun){
	$('button[event='+eventName+']').click(fun);
}

function div_click(eventName,fun){
	$('div[event='+eventName+']').click(fun);
}


function isCheckBoxChecked($dom){
	return $dom[0].checked;
}

var JSCookie = {
	getCookie : function(key) {
		var cookie = document.cookie;
		var cookieArray = cookie.split(';');
		var getvalue = "";
		for ( var i = 0; i < cookieArray.length; i++) {
			if (cookieArray[i].Trim().substr(0, key.length) == key) {
				getvalue = cookieArray[i].Trim().substr(key.length + 1);
				break;
			}
		}
		return getvalue;
	},
	/**
	 * 默认过期时间30天
	 */
	setCookie : function(key, value, expire, domain, path) {
		var cookie = "";
		if (key != null && value != null)
			cookie += key + "=" + value + ";";
		if (expire != null)
			cookie += "expires=" + expire.toGMTString() + ";";
		else{
			var exdate=new Date();
			exdate.setDate(exdate.getDate() + 30);
			cookie += "expires=" + exdate.toGMTString() + ";";
		}
		if (domain != null)
			cookie += "domain=" + domain + ";";
		if (path != null)
			cookie += "path=" + path + ";";
		document.cookie = cookie;
	},
	expire : function(key) {
		expire_time = new Date();
		expire_time.setFullYear(expire_time.getFullYear() - 1);
		var cookie = " " + key + "=e;expires=" + expire_time + ";";
		document.cookie = cookie;
	}
};

$.extend({
	/** 
	 1. 设置cookie的值，把name变量的值设为value   
	example $.cookie(’name’, ‘value’);
	 2.新建一个cookie 包括有效期 路径 域名等
	example $.cookie(’name’, ‘value’, {expires: 7, path: ‘/’, domain: ‘jquery.com’, secure: true});
	3.新建cookie
	example $.cookie(’name’, ‘value’);
	4.删除一个cookie
	example $.cookie(’name’, null);
	5.取一个cookie(name)值给myvar
	var account= $.cookie('name');
	**/
    cookie: function(name, value, options) {
        if (typeof value != 'undefined') { // name and value given, set cookie
            options = options || {};
            if (value === null) {
                value = '';
                options.expires = -1;
            }
            var expires = '';
            if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
                var date;
                if (typeof options.expires == 'number') {
                    date = new Date();
                    date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
                } else {
                    date = options.expires;
                }
                expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
            }
            var path = options.path ? '; path=' + options.path : '';
            var domain = options.domain ? '; domain=' + options.domain : '';
            var secure = options.secure ? '; secure' : '';
            document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
        } else { // only name given, get cookie
            var cookieValue = null;
            if (document.cookie && document.cookie != '') {
                var cookies = document.cookie.split(';');
                for (var i = 0; i < cookies.length; i++) {
                    var cookie = jQuery.trim(cookies[i]);
                    // Does this cookie string begin with the name we want?
                    if (cookie.substring(0, name.length + 1) == (name + '=')) {
                        cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                        break;
                    }
                }
            }
            return cookieValue;
        }
    }
});