$(function() {
	orderinit("../../../resources/findSysRes.do");
	$('#form').delegate('#addSystem','click',showSystemDialog);
})
function orderinit(url){
	var columns  = [[
	                 {field:'id',title:'菜单名称',hidden:true,align:'center'},
	                 {field:'menuName',title:'菜单名称',height:40,align:'left',width : 170},
	                 {field:'aliases',title:'菜单别名',height:40, align:'left',width : 100},
	                 {field:'menuPath',title:'链接地址',align:'center',width : 300},
	                 {field:'funCode',title:'功能编码',align:'center',width : 100},
	                 {field:'sort',title:'排序',align:'center',width : 100},
	                 {field:'menuStatus',title:'状态',align:'center',width : 100,formatter: function(value,row,index){
	     				if (row.status == '1'){
	    					return '开启';
	    				} else {
	    					return '关闭';
	    				}
	    			 }},
	                 {field:'opt',title:'操作',align:'center',width : 400, formatter:function(value, row, index) {
	                	 return optformater(value,row,index);
	                 }}
	                ]];
	$('#dataGrid').treegrid({
		url: url,
		rownumbers: true,
		idField: 'id',
		treeField: 'menuName',
//			loadFilter: myLoadFilter,
		animate:true,
		checkOnSelect: true,
        selectOnCheck: true,
        autoRowHeight: true,
		fit: true,
		striped: true, // 设置为true将交替显示行背景
		fitColumns: true,
		nowrap: false,
		remotesort: false,
		checkOnSelect: false,
		method: "get", // 请求数据的方�
		loadMsg: '数据加载中请稍后',
		rowStyler: function(index,row){
			return 'font-size: 20px;';
		},
		columns:columns,
		onLoadError: function() {
			$.messager.alert('操作提示', '获取信息失败...请联系管理员!', 'error');
		},
		onLoadSuccess:function(data){
			$('.datagrid').undelegate('a[name=addChilBtn]','click',showAddChilDialog);
			$('.datagrid').undelegate('a[name=editBtn]','click',editMenuDialog);
			$('.datagrid').undelegate('a[name=delBtn]','click',delMenu);
			$('.datagrid').undelegate('a[name=colBtn]','click',colMenuItem);
			$('.datagrid').undelegate('a[name=openBtn]','click',openMenuItem);
//			$('.datagrid').undelegate('a[name=addFunBtn]','click',showFunMenuItem);
//			
			$('.datagrid').delegate('a[name=addChilBtn]','click',showAddChilDialog);
			$('.datagrid').delegate('a[name=editBtn]','click',editMenuDialog);
			$('.datagrid').delegate('a[name=delBtn]','click',delMenu);
			$('.datagrid').delegate('a[name=colBtn]','click',colMenuItem);
			$('.datagrid').delegate('a[name=openBtn]','click',openMenuItem);
//			$('.datagrid').delegate('a[name=addFunBtn]','click',showFunMenuItem);
			
		},
        toolbar:'#tb'
	});
}

var showSystemDialog = function(){
	//var sysName = $("#systemSelect").combobox('getText');
	//var sysCode = $("#systemSelect").combobox('getValue');
	var html = $('#system-dialog-template').html();
	$(html).dialog({  
	    title: '新增  一级菜单',  
	    width: 420,  
	    height: 400,  
	    closed: false,  
	    cache: false,  
	    //href: 'get_content.php',  
	    modal: true,
	    buttons: [{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				var url = "../../../resources/addResources.do";
				addMenu(url,'menu',1);
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#system-dialog').dialog('close');
			}
		}],onOpen:function(){
			/*$("#sysName-dialog").combobox({
	 			onChange:function(){
	 				var sysCode = $("#sysName-dialog").combobox('getValue');
	 				 $.each(systemList,function(i,v){
	 					 if(sysCode==v.id){
	 						 $("#domain").val(v.domain);
	 					 }
	 				 });
	 			}
			});*/
			$("#funCode").blur(function(){
//				checkFunCode($("#funCode").val(),'');
			});
			//$('#sysName-dialog').val(sysName);
			//$('#sysName-dialog').attr('sysCode',sysCode);
	    },
		onClose:function(){
			$('#system-dialog').dialog('destroy');
	    }
	});  
};

function optformater(value,row,index){
	var temp = "";
	//console.log(row);
	if(row.level == 1){
		temp += "<a  href='javascript:;' name='addChilBtn' '  class='btn' ><i class='i-op i-op-add'></i>添加子菜单</a>&emsp; ";
	}else if(row.level == 2){
//		temp += "<a  href='javascript:;' name='addFunBtn' '  class='btn'><i class='i-op i-op-add'></i>添加功能</a>&emsp;";
	}
	if(row.status == '1'){
		temp += "<a  href='javascript:;' name='colBtn' '  class='btn'><i class='i-op i-op-lock'></i>关闭</a>&emsp; ";
	}else{
		temp += "<a  href='javascript:;' name='openBtn' ' class='btn'><i class='i-op i-op-unlock'></i>开启</a>&emsp;";
	}
	temp += "<a  href='javascript:;' name='editBtn' '  class='btn'><i class='i-op i-op-edit'></i>编辑</a>&emsp; ";
	temp += "<a  href='javascript:;' name='delBtn' '  class='btn'><i class='i-op i-op-del-1'></i>删除</a>&emsp; ";
	return temp;
}

var addMenu = function(url,menuType,level){
	var dataModel = {};
	var menuName = $.trim($('#system-dialog input[name=menuName]').val());
	var menuPath = $.trim($('#system-dialog input[name=menuPath]').val());
	var sort = $.trim($('#system-dialog input[name=sort]').val());
	var funCode	= '';
	var menuStatus = $.trim($('#system-dialog input[name=menuStatus]:checked').val());
	
	var parentId = $.trim($('#pMenuName').attr('pMenuId'));
	 var reg = new RegExp("^[0-9]*$");
	 if(sort&&!reg.test(sort)){
		$.messager.alert({
	  	      title:'操作提示',
	  	      msg:'<div class="content">排序必须为整数!!</div>',
	  	      ok:'<i class="i-ok"></i> 确定',
	  	      icon:'error'
	  	    });
		return false;
		}
	if(menuName == ''){
		$.messager.alert({
	  	      title:'操作提示',
	  	      msg:'<div class="content">功菜单名称不能为空!</div>',
	  	      ok:'<i class="i-ok"></i> 确定',
	  	      icon:'error'
	  	    });
		return false;
	}
	if(menuPath == ''){
		$.messager.alert({
	  	      title:'操作提示',
	  	      msg:'<div class="content">菜单地址不能为空!</div>',
	  	      ok:'<i class="i-ok"></i> 确定',
	  	      icon:'error'
	  	    });
		return false;
	}
//	if($('#system-dialog input[name=funCode]').length == 1){
//		funCode = $.trim($('#system-dialog input[name=funCode]').val());
//		if(funCode == ''){
//			$.messager.alert({
//		  	      title:'操作提示',
//		  	      msg:'<div class="content">功能编码不能为空!</div>',
//		  	      ok:'<i class="i-ok"></i> 确定',
//		  	      icon:'error'
//		  	    });
//			return false;
//		}
//		
//	}
//	if(level!=1){
//		var prjId=$('#sysName-dialog').combobox('getValue');
//		if(prjId==''||prjId==null){
//			$.messager.alert({
//		  	      title:'操作提示',
//		  	      msg:'<div class="content">请选择系统!</div>',
//		  	      ok:'<i class="i-ok"></i> 确定',
//		  	      icon:'error'
//		  	    });
//			return false;
//		}
//		dataModel.prjId=$('#sysName-dialog').combobox('getValue');
//		dataModel.domain=$('#domain').val();
//	}
	
	
	dataModel.menuName = menuName;
	dataModel.menuPath = menuPath;
	dataModel.sort = sort;
	dataModel.status = menuStatus;
	if(level==1){
		parentId=-1;
		dataModel.domain="";
		dataModel.prjId=-1;
	}
	dataModel.parentId = parentId;
	dataModel.funCode = funCode;
	dataModel.level = level;
	dataModel.aliases =$('#aliases').val() ;
	$.ajax({
		url:url,
		traditional:true,
		type:'POST',
		async:false,
		data:dataModel,
		contentType: "application/x-www-form-urlencoded",
		cache:false,
		success:function(data){
			if(data.status == 200){
				$('#system-dialog').dialog('close');
				$('#system-dialog').dialog('destroy');
				doSearch();
			}else{
				$.messager.alert({
					title:'提示',
					msg:'<div class="content">'+data.msg+'</div>',
					ok:'<i class="i-ok"></i> 确定',
					icon:'error'
				});
			}
		},error:function(data){
			$.messager.alert({
				title:'提示',
				msg:'<div class="content">操作失败</div>',
				ok:'<i class="i-ok"></i> 确定',
				icon:'error'
			});
		}
	});
};
//搜索
var doSearch = function (){
	orderinit("../../../resources/findSysRes.do");
};
var showAddChilDialog = function(){
	/*	var sysName = $("#systemSelect").combobox('getText');
		var sysCode = $("#systemSelect").combobox('getValue');*/
		var menuName = $.trim($(this).parents('tr.datagrid-row').find('td[field=menuName]').text());
		var menuId = $.trim($(this).parents('tr.datagrid-row').find('td[field=id]').text());
		//var menuNode = $('#dataGrid').treegrid('find',menuId);
		//loadSystemListbyAdd(menuNode.prjId);
//		loadSystemListbyAdd();
		var html = $('#system-menu-dialog-template').html();
		$(html).dialog({  
		    title: '新增 （'+menuName+'）子菜单',  
		    width: 420,  
		    height: 540,  
		    closed: false,  
		    cache: false,  
		    //href: 'get_content.php',  
		    modal: true,
		    buttons: [{
				text:'提交',
				iconCls:'icon-ok',
				handler:function(){
					var url = "../../../resources/addResources.do";
					addMenu(url,'menu',2);
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#system-dialog').dialog('close');
				}
			}],
			onOpen:function(){
				//$('#sysName-dialog').val(sysName);
				//$('#sysName-dialog').attr('sysCode',sysCode);
//				$("#sysName-dialog").combobox({
//		 			onChange:function(){
//		 				var sysid = $("#sysName-dialog").combobox('getValue');
//		 				 $.each(systemList,function(i,v){
//		 					 if(sysid==v.id){
//		 						 $("#domain").val(v.domain);
//		 					 }
//		 				 });
//		 			}
//				});
				/*$("#domain").val(menuNode.domain);*/
				$('#pMenuName').val(menuName);
				$('#pMenuName').attr('pMenuId',menuId);
				$("#funCode").blur(function() { 
//					checkFunCode($("#funCode").val(),'');
					
				});
		    },
			onClose:function(){
				$('#system-dialog').dialog('destroy');
		    }
		});  
	};
	var editMenuDialog = function(){
		/*var sysName = $("#systemSelect").combobox('getText');
		var sysCode = $("#systemSelect").combobox('getValue');*/
		var menuId = $.trim($(this).parents('tr.datagrid-row').find('td[field=id]').text());
		//var pMenuId = $('#dataGrid').treegrid('getParent',menuId);
		var menuNode = $('#dataGrid').treegrid('find',menuId);
		//var funCode = $.trim($(this).parents('tr.datagrid-row').find('td[field=funCode]').text());
		if(menuNode.level == 1){//一级菜�			
			editSystemDialog(menuId);
		}else if(menuNode.level == 2){//二级菜单
			editSystemChilDialog(menuId);
		}else{//三级菜单(功能按钮菜单)
//			editFunMenuDialog(menuId);
		}
	};
	var editSystemDialog = function(menuId){
		/*	var sysName = $("#systemSelect").combobox('getText');
			var sysCode = $("#systemSelect").combobox('getValue');*/
			
			var menuNode = $('#dataGrid').treegrid('find',menuId);
//			loadSystemListbyAdd(menuNode.prjId);
			var html = $('#system-dialog-template').html();
			$(html).dialog({  
			    title: '编辑（'+menuNode.menuName+'）一级菜单',  
			    width: 420,  
			    height: 380,  
			    closed: false,  
			    cache: false,  
			    //href: 'get_content.php',  
			    modal: true,
			    buttons: [{
					text:'提交',
					iconCls:'icon-ok',
					handler:function(){
						var url = "../../../resources/updateResources.do";
						editMenu(url);
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						$('#system-dialog').dialog('close');
					}
				}],onOpen:function(){
					//$('#sysName-dialog').val(sysName);
					//$('#sysName-dialog').attr('sysCode',sysCode);
					$('#menuName').attr('menuId',menuNode.id);
					$('#menuName').val(menuNode.menuName);
					$('#menuPath').val(menuNode.menuPath);
					$('#funCode').val(menuNode.funCode);
					$('#sort').val(menuNode.sort);
					$('#domain').val(menuNode.domain);
					$('#aliases').val(menuNode.aliases);
					$('input[name=menuStatus][value='+menuNode.menuStatus+']').attr('checked','checked');
					
					$("#domain").val(menuNode.domain);
			 		
					$('#menuStatus-content').hide();
					$("#funCode").blur(function() { 
//						checkFunCode($("#funCode").val(),menuId);
						
					});
					
			    },
				onClose:function(){
					$('#system-dialog').dialog('destroy');
			    }
			});  
		};
		
		var editSystemChilDialog = function(menuId){
			
			var menuNode = $('#dataGrid').treegrid('find',menuId);
			var pMenuId = $('#dataGrid').treegrid('getParent',menuId);
//			loadSystemListbyAdd(menuNode.prjId);
			var html = $('#system-menu-dialog-template').html();
			
			$(html).dialog({  
			    title: '编辑 （'+menuNode.menuName+'）子菜单',  
			    width: 450,  
			    height: 520,  
			    closed: false,  
			    cache: false,  
			    //href: 'get_content.php',  
			    modal: true,
			    buttons: [{
					text:'提交',
					iconCls:'icon-ok',
					handler:function(){
						var url = "../../../resources/updateResources.do";
						editMenu(url);
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						$('#system-dialog').dialog('close');
					}
				}],onOpen:function(){
			
					$('#menuName').attr('menuId',menuNode.id);
					$('#menuName').val(menuNode.menuName);
					$('#menuPath').val(menuNode.menuPath);
					$('#pMenuName').val(pMenuId.menuName);
					$('#funCode').val(menuNode.funCode);
					$('#sort').val(menuNode.sort);
					$('input[name=menuStatus][value='+menuNode.menuStatus+']').attr('checked','checked');
					$("#domain").val(menuNode.domain);
					$('#aliases').val(menuNode.aliases);
					$('#menuStatus-content').hide();
					$("#funCode").blur(function() { 
//						checkFunCode($("#funCode").val(),menuId);
						
					});
			    },
				onClose:function(){
					$('#system-dialog').dialog('destroy');
			    }
			});  
		};
		var editMenu = function(url){
			var menuName = $.trim($('#system-dialog input[name=menuName]').val());
			var menuPath = $.trim($('#system-dialog input[name=menuPath]').val());
			var sort = $.trim($('#system-dialog input[name=sort]').val());
			var menuStatus = $.trim($('#system-dialog input[name=menuStatus]:checked').val());
			var menuId = $.trim($('#menuName').attr('menuId'));
			var funCode	= $.trim($('#system-dialog input[name=funCode]').val());
			 var reg = new RegExp("^[0-9]*$");
				if(sort&&!reg.test(sort)){
					$.messager.alert({
				  	      title:'操作提示',
				  	      msg:'<div class="content">排序必须为整数!!</div>',
				  	      ok:'<i class="i-ok"></i> 确定',
				  	      icon:'error'
				  	    });
					return false;
					}
			 
			if(menuName == ''){
				 $.messager.alert({
				      title:'提示',
				      msg:'<div class="content">菜单名称不能为空!</div>',
				      ok:'<i class="i-ok"></i> 确定'
				    });
				return false;
			}
			if(menuPath == ''){
				 $.messager.alert({
				      title:'提示',
				      msg:'<div class="content">菜单地址不能为空!</div>',
				      ok:'<i class="i-ok"></i> 确定'
				    });
				return false;
			}
//			if($('#system-dialog input[name=funCode]').length == 1){
//				funCode = $.trim($('#system-dialog input[name=funCode]').val());
//				if(funCode == ''){
//					 $.messager.alert({
//					      title:'提示',
//					      msg:'<div class="content">功能编码不能为空!</div>',
//					      ok:'<i class="i-ok"></i> 确定'
//					    });
//					return false;
//				}
//				
//			}
		
			var dataModel = {};
			//dataModel.prjId=$('#systemSelect').combobox('getValue');
			dataModel.menuName = menuName;
			dataModel.menuPath = menuPath;
			dataModel.status = menuStatus;
			dataModel.aliases =$('#aliases').val() ;
			dataModel.id = menuId;
			dataModel.sort = sort;
			dataModel.funCode = funCode;
			$.ajax({
				url:url,
				traditional:true,
				type:'POST',
				async:false,
				data:dataModel,
				contentType: "application/x-www-form-urlencoded",
				cache:false,
				success:function(data){
					if(data.status == 200){
						$('#system-dialog').dialog('close');
						$('#system-dialog').dialog('destroy');
						doSearch();
					}else{
						$.messager.alert({
							title:'提示',
							msg:'<div class="content">'+data.msg+'</div>',
							ok:'<i class="i-ok"></i> 确定',
							icon:'error'
						});
					}
				},error:function(data){
					$.messager.alert({
						title:'提示',
						msg:'<div class="content">操作失败!</div>',
						ok:'<i class="i-ok"></i> 确定',
						icon:'error'
					});
				}
			});
		
		};
		var colMenuItem = function(){
			var menuId = $.trim($(this).parents('tr.datagrid-row').find('td[field=id]').text());
			
			$.messager.confirm({
			      title:'关闭菜单',
			      msg:'<div class="content">你确定要关闭该菜单信息吗?</div>',
			      ok:'<i class="i-ok"></i> 确定',
			      cancel:'<i class="i-close"></i> 取消',
			      fn:function(r){
			    	  if (r) { 
			    		  var dataModel = {};
				    	  dataModel.id = menuId;
				    	  dataModel.status  = 0;
				    	  var url = '../../../resources/updateStatus.do';
				    	  $.ajax({
				    			url:url,
				    			traditional:true,
				    			type:'Post',
				    			async:false,
				    			data:dataModel,
				    			cache:false,
				    			success:function(data){
				    				if(data.status == 200){
				    					 doSearch();
				    				}
				    				$.messager.alert({
								  	      title:'操作提示',
								  	      msg:'<div class="content">'+data.msg+'</div>',
								  	      ok:'<i class="i-ok"></i> 确定',
								  	      icon:'info'
								  	    });
				    			},error:function(data){
				    				$.messager.alert({
								  	      title:'操作提示',
								  	      msg:'<div class="content">操作失败!</div>',
								  	      ok:'<i class="i-ok"></i> 确定',
								  	      icon:'error'
								  	    });
				    			}
				    	  });
				          return true;  
				      }  
			      }
		    });  
		    return false;  
		};
		var openMenuItem = function(){
			var menuId = $.trim($(this).parents('tr.datagrid-row').find('td[field=id]').text());
			$.messager.confirm({
			      title:'开启菜单',
			      msg:'<div class="content">你确定要开启该菜单信息?</div>',
			      ok:'<i class="i-ok"></i> 确定',
			      cancel:'<i class="i-close"></i> 取消',
			      fn:function(r){
			        if (r) { 
			    		var dataModel = {};
			    		dataModel.id = menuId;
			    		dataModel.status = 1;
			    		var url = '../../../resources/updateStatus.do';
			    		$.ajax({
			    			url:url,
			    			traditional:true,
			    			type:'post',
			    			async:false,
			    			data:dataModel,
			    			cache:false,
			    			success:function(data){
			    				if(data.status == 200){
			    					 doSearch();
			    				}
			    				$.messager.alert({
							  	      title:'操作提示',
							  	      msg:'<div class="content">'+data.msg+'</div>',
							  	      ok:'<i class="i-ok"></i> 确定',
							  	      icon:'info'
							  	    });
			    			},error:function(data){
			    				$.messager.alert({
							  	      title:'操作提示',
							  	      msg:'<div class="content">操作失败!</div>',
							  	      ok:'<i class="i-ok"></i> 确定',
							  	      icon:'error'
							  	    });
			    			}
			    		});
			            return true;  
			        }
			      }
		    });  
		    return false;  
		};
		var delMenu = function(){
			var menuId = $.trim($(this).parents('tr.datagrid-row').find('td[field=id]').text());
			$.messager.confirm({
			      title:'删除菜单',
			      msg:'<div class="content">你确定要删除该菜单信息吗?</div>',
			      ok:'<i class="i-ok"></i> 确定',
			      cancel:'<i class="i-close"></i> 取消',
			      fn:function(r){
			      	if (r){
			      		var dataModel = {};
			    		dataModel.id = menuId;
			    		//dataModel.sysCode = sysCode;
			    		var url = '../../../resources/delResources.do';
			    		$.ajax({
			    			url:url,
			    			traditional:true,
			    			type:'get',
			    			async:false,
			    			data:dataModel,
			    			cache:false,
			    			success:function(data){
			    				if(data.status == 200){
			    					 doSearch();
			    				}
			    				$.messager.alert({
							  	      title:'操作提示',
							  	      msg:'<div class="content">'+data.msg+'</div>',
							  	      ok:'<i class="i-ok"></i> 确定',
							  	      icon:'info'
							  	    });
			    			},error:function(data){
			    				$.messager.alert({
							  	      title:'操作提示',
							  	      msg:'<div class="content">操作失败!</div>',
							  	      ok:'<i class="i-ok"></i> 确定',
							  	      icon:'error'
							  	    });
			    			}
			    		});
			            return true;  
			      	}
			      }
			    });
		    return false;  
		};		
		