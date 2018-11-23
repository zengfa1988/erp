var userInfo;
// 判断是否在权限中
function inAuthority (btn, btnAuthority){
	if ($.inArray (btn, btnAuthority) > -1){
		return true
	}else{
		return false
	}
}
// 通过页面地址获取id
function getFunAuthority (){
	var url = "/platform/getfunCode.do";
	var buttonAutority = null;
	$.ajax ({
	    type : "get",
	    url : url,
	    async : false,
	    cache : false,
	    success : function (data){
		    buttonAutority = eval (data);
	    }
	});
	return buttonAutority;
}

function commoninit (){
	var url = '/usercentre/getUserInfo.do';
	// $(".form").tshFrom();
	$.ajax ({
	    type : "GET",
	    url : url,
	    async : false,
	    cache : false,
	    success : function (data){
		    userInfo = data;
	    },
	    error : function (xmlHttpRequest, textStatus, errorThrown){
		    $ ("#userName").text ("guest");
	    }
	});
}

// 页面加载时，立即加载数据
Date.prototype.pattern = function (fmt){
	var o ={
	    "M+" : this.getMonth () + 1, // 月份
	    "d+" : this.getDate (), // 日
	    "h+" : this.getHours () % 12 == 0 ? 12 : this.getHours () % 12, // 小时
	    "H+" : this.getHours (), // 小时
	    "m+" : this.getMinutes (), // 分
	    "s+" : this.getSeconds (), // 秒
	    "q+" : Math.floor ((this.getMonth () + 3) / 3), // 季度
	    "S" : this.getMilliseconds ()
	// 毫秒
	};
	var week ={
	    "0" : "/u65e5",
	    "1" : "/u4e00",
	    "2" : "/u4e8c",
	    "3" : "/u4e09",
	    "4" : "/u56db",
	    "5" : "/u4e94",
	    "6" : "/u516d"
	};
	if (/(y+)/.test (fmt)){
		fmt = fmt.replace (RegExp.$1, (this.getFullYear () + "").substr (4 - RegExp.$1.length));
	}
	if (/(E+)/.test (fmt)){
		fmt = fmt.replace (RegExp.$1,
		        ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "")
		                + week[this.getDay () + ""]);
	}
	for ( var k in o){
		if (new RegExp ("(" + k + ")").test (fmt)){
			fmt = fmt.replace (RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
			        : (("00" + o[k]).substr (("" + o[k]).length)));
		}
	}
	return fmt;
};
function newTab (title, url){
	parent.addTab (title, url);
}

/**
 * 模拟提交表单
 * 
 * @param url
 * @param parName
 * @param criteria
 */
function buildExportFormSubmit (url, parName, criteria){
	$form = $ ("#exportExcelTmpFormId");
	if ($form.size () == 0){
		$form = $ ("<form id='exportExcelTmpFormId' style='display:none' target='exportIframe' method='POST'></form>");
		$ (document.body).append ($form);
		$ (document.body).append ('<iframe name="exportIframe" style="display: none;"></iframe>');
	}
	// 显示进度条
	$.messager.progress ({
	    title : "友情提示",
	    text : "正在下载中,请稍后..."
	});
	// 提交
	if ($form.find ("[name='" + parName + "']").length == 0){
		$form.append ("<input type='hidden' value=''  name='" + parName + "' /> ");
	}
	$form.form ('clear')
	$form.find ("[name='" + parName + "']").val (criteria);
	$form.attr ("action", url);
	$form.submit ();
	// 关闭进度条
	window.setTimeout (function (){
		$.messager.progress ('close');
	}, 2000);
}

/**
 * 导出提示信息
 * 
 * @param type
 * @param msg
 */
function exportAlert (type, msg){
	$.messager.show ({
	    title : '友情提示',
	    msg : '<i class="i-l i-l-' + type + '"></i><span>' + msg + '</span>',
	    showType : 'fade',
	    timeout : 2000,
	    height : 200,
	    width : 350,
	    style :
	    {
	        right : '',
	        bottom : ''
	    }
	});
}

/**
 * 显示消息
 * 
 * @param showType
 * @param msg
 */
function showMessage (showType, msg){
	$.messager.show ({
	    title : '',
	    msg : '<i class="i-l i-l-' + showType + '"></i><span>' + msg + '</span>',
	    showType : 'fade',
	    timeout : 1000,
	    height : 150,
	    width : 300,
	    style :
	    {
	        right : '',
	        bottom : ''
	    }
	});
}
