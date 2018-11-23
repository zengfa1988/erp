var gVersion="1.0.0";
$(function() {
  commoninit();
  init();
  menuInit();//菜单数据初始化
  menuAnimate();//菜单动画
  tabOpenEven();//打开菜单事件
  tabCloseEven();//关闭菜单事件
//  abnormalLoginCheck();//登录异常检查
});

function init() {
	$("#userName").text(userInfo.loginName);
//	if(userInfo.loginCount<=1){
//		showUpdatePwd();
//		return;
//	}
}

function tabOpenEven(){
  $('.addTab').on("click","a",function() {
      $('.addTab li').removeClass("click");
      $(this).closest("li").toggleClass("click");
			var $this = $(this);
			var href = $this.attr('src');
			var title = $this.text();
      if(href){
        addTab(title, href);
      }
		});
}

function menuInit() {
  $("#menu").html('<div class="loading"><p class="bg-warning " style="padding:10px;color:#fff ;text-align:center">菜单加载中...</p></div>');
  var url = 'usercentre/getWebMenu.do';
  $.ajax({
    url:url,
    traditional:true,
    type:'get',
    async:true,
    cache:false,
    dataType:"json",
    success:function(data){
    	if(data.data){
    		menuRender(data.data);
    		menuAnimate();
//    		loadMasteraccPage();
    		loadHomePage(data.data);
    	}else{	
    		 $.messager.alert({
    		      title:'错误提示',
    		      width: 400,
    		      msg:'<div class="content">该用户角色没有权限，请向管理员申请权限后登陆！</div>',
    		      ok:'<i class="i-ok"></i> 确定',
    		      icon:'error',
    		    	 fn:function(r){
    		    	window.location.href = "../usercentre/outLogin.do";
  		      }
    		});
    	}
    },error:function(data){
      $.messager.alert('提示', '操作失败!', 'error');
      window.location.href = "usercentre/outLogin.do";
    }
  });
}

function menuRender(data) {
  $("#menu").html('');
  var content = '';
  var m=1;
  content += '<div class="sysMenu"><ul>';
	  $.each(data,function(i,v){
		  if(v.level&&v.level == 1&&(v.isDisplay==0||v.isDisplay==''||v.isDisplay==null)){
		    var icon='<i class="i-menu i-menu-'+m+'"></i>';
		    m++;
		    content += '<li><div class="link">'+icon+v.menuName+'<i class="i-arrow-down"></i></div>';
		    content+='<ul>';
		    $.each(data,function(j,k){
				if(k.parentId==v.id){
					content+= '<li id="'+k.funCode+'"><a href="javascript:void(0);" onclick="checklogin()"  src="'+k.menuPath+/*'?gVersion='+gVersion+*/'" >'+k.menuName+'</a></li>';
				}
		      });
		      content+='</ul>';
		    
		    content+='</li>';
		  }
	  });
	  content+= '</ul></div>';
	  $("#menu").html(content);
}

function checklogin(){
	var url = 'usercentre/checklogin.do';
	  $.ajax({
	    url:url,
	    traditional:true,
	    type:'get',
	    async:true,
	    cache:false,
	    dataType:"json",
	    success:function(data){
	    	if(!data){
	    		window.location.href = "usercentre/outLogin.do";
	    	}
	    },error:function(data){
	    	window.location.href = "usercentre/outLogin.do";
	      //$.messager.alert('提示', '操作失败!', 'error');
	    }
	  });
}


function abnormalLoginCheck(){
	var url = '../usercentre/abnormalLoginCheck.do';
	  $.ajax({
	    url:url,
	    traditional:true,
	    type:'get',
	    async:true,
	    cache:false,
	    dataType:"json",
	    success:function(data){
	    	if(data&&data.data&&data.data.isChange==1){
	    		$.messager.alert({
				      title:'提示',
				      width: 400,
				      msg:'<div class="content">系统检测到本次登录地址与上次登录地址不一致，上次登录地址为：'+data.data.lastLoginAddress+'</div>',
				      ok:'<i class="i-ok"></i> 确定',
				      icon:'error',
				    });
	    	}
	    },error:function(data){
	    	
	      //$.messager.alert('提示', '操作失败!', 'error');
	    }
	  });
}
function loadMasteraccPage(){
	$.ajax({
	    url:'../usercentre/masteraccUrl.do',
	    type:'get',
	    success:function(data){
	    	if(data){
	    		if(data.data!=null&&data.data.zsyUrl){
	    			addTab("首页",data.data.zsyUrl);
	    			$('.link').unbind();
	    		}
	    	}
	    }
	});
}

function loadHomePage(menudata){
	$.ajax({
	    url:'../usercentre/getHomepageUrl.do',
	    type:'get',
	    success:function(data){
	    	if(data){
	    		var iframe="";
	    		$.each(menudata,function(i,v){
	    			if(v.funCode=='auc_home'){
	    				 $.each(menudata,function(j,k){
	    						if(k.parentId==v.id){
	    							var i=k.funCode.lastIndexOf("_")+1;
	    							var tmp=k.funCode.substring(i,k.funCode.length);
	    							var width=tmp.split("-")[0];
	    							var height=tmp.split("-")[1];
	    							if(k.menuPath.indexOf("/views/pc/noticeListIndex.html")==-1||data.data=='true'){
	    								if(height>600){
	    									iframe+='<br><iframe id="welcome_iframe" src="'+k.menuPath+'" frameborder="0"  style="width:'+width+'px;height:'+height+'px;border:0;"></iframe>';
	    								}else{
	    									iframe+='<iframe id="welcome_iframe" src="'+k.menuPath+'" frameborder="0"  style="width:'+width+'px;height:'+height+'px;border:0;overflow-y:hidden;vertical-align:top;margin-right:20px;"></iframe>';
	    								}
	    							}
	    						}
	    				      });
	    				
	    			}
	    		});
	    		$("#welcome_auc").html(iframe);
	    	}
	    }
	});
}


function outlogin(){
	window.location.href = "usercentre/outLogin.do";
}

function menuAnimate(){
  var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;

		// Variables privadas
		var links = this.el.find('.link');
		// Evento
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
	};

	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
			$this = $(this),
			$next = $this.next();

		$next.slideToggle();
		$this.parent().toggleClass('open');

		if (!e.data.multiple) {
			$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
		};
	};
	var accordion = new Accordion($('#menu'), false);
}

function addTab(title, url){
	if ($('#tabs').tabs('exists', title)){
		$('#tabs').tabs('select', title);//选中并刷新
		var currTab = $('#tabs').tabs('getSelected');
		//var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != 'Home') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			});
		}
	} else {
		var content = createFrame(url);
		$('#tabs').tabs('add',{
			title:title,
			content:content,
			closable:true
		});
	}
	tabClose();
}
function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;overflow-y : hidden;   "></iframe>';
	return s;
}


/**
	 * 刷新tab
	 * @cfg
	 *example: {tabTitle:'tabTitle',url:'refreshUrl'}
	 *如果tabTitle为空，则默认刷新当前选中的tab
	 *如果url为空，则默认以原来的url进行reload
	 */
	function refreshTab(cfg){
	    var refresh_tab = cfg.tabTitle?$('#tabs').tabs('getTab',cfg.tabTitle):$('#tabs').tabs('getSelected');
	    if(refresh_tab && refresh_tab.find('iframe').length > 0){
	    var _refresh_ifram = refresh_tab.find('iframe')[0];
	    var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;
	    //_refresh_ifram.src = refresh_url;
	    _refresh_ifram.contentWindow.location.href=refresh_url;
	    }
	}
function tabClose() {
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	});
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven() {
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		if(url != undefined && currTab.panel('options').title != '首页') {
			$('#tabs').tabs('update',{
				tab:currTab,
				options:{
					content:createFrame(url)
				}
			});
		}
	});
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	});
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t != '首页') {
				$('#tabs').tabs('close',t);
			}
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		var nextall = $('.tabs-selected').nextAll();
		if(prevall.length>0){
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '首页') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		if(nextall.length>0) {
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t != '首页') {
					$('#tabs').tabs('close',t);
				}
			});
		}
		return false;
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	});
	

}
function showUpdatePwd(){
		var html = $('#user-update-pwd').html();
		
		$(html).dialog({  
		    title: '系统检测到您是第一次登入，请修改您的登录密码',  
		    width: 430,  
		    height: 320,  
		    closed: false,  
		    cache: false,  
		    modal: true,
		    buttons: [{
				text:'保&nbsp;&nbsp;&nbsp;存',
				iconCls:'icon-ok',
				handler:function(){
					submitData();
				}
			}],
			onOpen:function(){
				$("#newpwd").blur(function(){
					$("#newpwd"+1).remove();
					if(validatePwd($("#newpwd").val(),'new')){
						var error= '<div id="newpwd'+1+'" class=" text-danger ">*'+validatePwd($("#newpwd").val(),'new')+'</div>';
						$("#newpwd").after(error);
					}
				});
				$("#repwd").blur(function(){
					$("#repwd"+1).remove();
					if(validatePwd($("#repwd").val(),'new')){
						var error= '<div id="repwd'+1+'" class=" text-danger ">*'+validatePwd($("#repwd").val(),'new')+'</div>';
						$("#repwd").after(error);
					}
				});
				$("#oldpwd").blur(function(){
					$("#oldpwd"+1).remove();
					if(validatePwd($("#oldpwd").val())){
						var error= '<div id="oldpwd'+1+'" class=" text-danger ">*'+validatePwd($("#oldpwd").val())+'</div>';
						$("#oldpwd").after(error);
					}
				});
		    },
			onClose:function(){
				$('#system-dialog').dialog('destroy');
		    }
		});  
		
		$('input[type=text]').validatebox(); 
		$(".panel-tool").html('');
		
	};
	function submitData(){
		
			var newpwd = $("#newpwd").val();
			var repwd = $("#repwd").val();
			//var oldpwd = $("#oldpwd").val();
			if(newpwd==null||newpwd==''){
				$.messager.alert({
				      title:'提示',
				      msg:'<div class="content">密码不能为空！</div>',
				      ok:'<i class="i-ok"></i> 确定',
				      icon:'error'
				    });
			}else if(newpwd!=repwd){
				$.messager.alert({
				      title:'提示',
				      width: 420,
				      msg:'<div class="content">两次输入的新密码不一致!</div>',
				      ok:'<i class="i-ok"></i> 确定',
				      icon:'error'
				    });
			}else{
			
				if(validatePwd(newpwd,'new')){
					$.messager.alert({
					      title:'提示',
					      msg:'<div class="content">'+validatePwd(newpwd,'new')+'</div>',
					      ok:'<i class="i-ok"></i> 确定',
					      icon:'error'
					    });
					return false;
				}
				$("#submit").prop("disabled", true);
				$.post("../../../usercentre/recoverPassword.do",{
					newPassword:Base64.encode($.trim(newpwd))
				},function(data){
					if(data.status==200){
						$("#submit").prop("disabled", false);
						 $.messager.alert({
						      title:'提示',
						      msg:'<div class="content">'+data.msg+'</div>',
						      ok:'<i class="i-ok"></i> 确定',
						      fn:function(r){
						      	  window.parent.location="../usercentre/outLogin.do";
						      }
						    });
					
					}else{
						$("#submit").prop("disabled", false);
						$.messager.alert({
						      title:'提示',
						      msg:'<div class="content">'+data.msg+'</div>',
						      ok:'<i class="i-ok"></i> 确定',
						      icon:'error'
						    });
					};
				},"JSON");
			}
	}
	//
	function validatePwd(password,pwdtype){
		var pwd=['123456','234567','345678','456789','987654','876543','765432','654321'];
		if(password.length<=5||password.length>=17){
			return "密码长度为6~16位";
		}
		if((/(^\s+)|(\s+$)/g).test(password)){
			return "首尾不支持空格";
		}
		if(pwdtype=='new'&&(/^([0-9a-zA-Z])\1{5}$/.test(password)||$.inArray(password, pwd )>-1)){
			return "密码过于简单";
		}
	}
