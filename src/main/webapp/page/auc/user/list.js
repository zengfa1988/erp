$(function() {
	var status=-1;
	getSelectVo();
	init('../../../user/getPageForGUserInfo.do?randnum=' + Math.random() + '&status='+status, null);
	$('#search').click(function() {
        search();
    });
});
function getSelectVo() {
    $('#roleType').combobox({
		url:'../../../sysRole/sysRoleVo.do',
		cache: false,
        //panelHeight: 'auto',//自动高度适合
		panelHeight: 200,
		method:'post',
		editable: false,
		valueField:'id',
		textField:'text'
		});
}
function init(url, queryParams) {
	var columns;
	columns = [ [{field: 'loginName', title: '账号名', width: 100, align: 'center'},
                 {field: 'userName',title: '用户名称',width: 100, align: 'center'}, 
                 {field: 'createDate',title: '创建时间',width: 100,align: 'center',
                     formatter: function(value, row, index) {
                         if (null != value) {
                             var date = new Date(value);
                             return date.pattern("yyyy-MM-dd hh:mm:ss");
                         } else {
                             value = '--';
                             return value;
                         }
                     }
                 }, 
                 {field: 'roleName', title: '角色', width: 80,align: 'center'}, 
                 { field: 'manager', title: '是否为管理员',width: 80,align: 'center',
	  	                formatter: function(value, row, index) {
	  	                    var statusContent = "";
	  	                    if (value==1) {
	  	                        statusContent += "<span class='goodsStatus'>是</span>";
	  	                    } else {
	  	                        statusContent += "<span class='goodsStatus'>否</span>";
	  	                    }
	  	                    return statusContent;
	  	                }
	                  },
             		{ field: 'status',title: '状态',width: 50,align: 'center',
 	                formatter: function(value, row, index) {
 	                    var statusContent = "";
 	                    if (value == 1) {
 	                        statusContent += "<span class='goodsStatus'>已启用</span>";
 	                    } else if (value == 0) {
 	                        statusContent += "<span style='color: red;' class='goodsStatus'>已停用</span>";
 	                    } else {
 	                        statusContent += "<span class='goodsStatus'>未知</span>";
 	                    }
 	                    return statusContent;
 	                }
                 },
                 {field: 'opts',title: '操作', width: 270,align: 'center',
               	  formatter: function(value, row, index) {
                     	var temp ="";
                     	temp += "<a class='btn' href='javascript:;'   onclick='viewsUser(\"" + row.id + "\")'><i class='i-op i-op-view'></i>查看</a>&nbsp;";
                     	temp += "<a class='btn' href='javascript:;'  onclick='viewGuser(\"" + row.id + "\")'><i class='i-op i-op-modify'></i>编辑</a>&nbsp;";
                     	if (row.status == 0) {
                     		temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '1,' + index + ")'><i class='i-op i-op-unlock'></i>启用</a>&nbsp;";
                     	} else {
                     		temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '0,' + index + ")'><i class='i-op i-op-lock'></i>停用</a>&nbsp;";
                     	}
                     	temp += "<a class='btn' href='javascript:;'     onclick='resetPwd("+row.id+");'><i class='i-op i-op-set'></i>重置密码</a>&nbsp;";
//                     	temp +=   "<a class='btn' href='javascript:;'  onclick='editUserAuthority(\""+ row.id + "\")'><i class='i-op i-op-edit'></i>授权</a>&nbsp;";
                     	return temp;
                     }
                 }]
             ];
    $('#dataGrid')
        .datagrid({
            url: url,
            queryParams: queryParams,
            rownumbers: true, // 行号
            singleSelect: true, // 是否单选
            pagination: true, // 分页控件
            pageSize: 15,
            pageList: [15, 30, 50],
            autoRowHeight: false,
            fit: true,
            striped: false, // 设置为true将交替显示行背景
            fitColumns: true,
            nowrap: false,
            remotesort: false,
            checkOnSelect: false,
            selectOnCheck: false,
            method: "GET", // 请求数据的方法
            loadMsg: '数据加载中,请稍候......',
            idField: 'id',
//            rowStyler: function(index, row) {
//                return 'font-size: 20px;';
//            },
            columns:columns ,
            onLoadError: function() {
            	 $.messager.alert({
            	      title:'提示',
            	      msg:'<div class="content">获取信息失败...请联系管理员!</div>',
            	      ok:'<i class="i-ok"></i> 确定',
            	      icon:'warning'
            	    });
            },
            onLoadSuccess: function(data) {
            	 $(".datagrid-row").css("height", "45px");
            	if(data==null||data.total==0){
            		$.messager.alert({
               	      title:'提示',
               	      msg:'<div class="content">当前没有查询到记录!</div>',
               	      ok:'<i class="i-ok"></i> 确定',
               	      icon:'warning'
               	    });
            	}
            },
            toolbar: '#tb'
        });
}

function search() {
    if ($("#tb").form('validate')) {
        var name = $.trim($('#name').textbox('getValue'));
    /*    if((/[^\u4E00-\u9FA5A-Za-z0-9]/g).test(name)){
    		$.messager.alert({
    	  	      title:'温馨提示',
    	  	      msg:'<div class="content">账号不支持特殊字符搜索!</div>',
    	  	      ok:'<i class="i-ok"></i> 确定',
    	  	      icon:'error'
    	  	    });
    			return;
    	}*/
        if (name) {
        	name = decToHex(name);
        }
        var status = $('#status').textbox('getValue');
        if (!status) {
            status = null;
        }
        if(status==null||status=='undefined'){
        	status = -1;
        }
        var roleType = $('#roleType').textbox('getValue');
        if (!roleType) {
        	roleType = null;
        }
        if(roleType==null||roleType=='undefined'){
        	roleType = -1;
        }
        var userGroup = $('#userGroup').textbox('getValue');
        if (!userGroup) {
        	userGroup = null;
        }
        if(userGroup==null||userGroup=='undefined'){
        	userGroup = -1;
        }
        queryParams = {
            'name': name,
            'status': status,
            'roleType':roleType,
            'userGroup':userGroup
        };
        init('../../../user/getPageForGUserInfo.do?randnum=' + Math.random() + '', queryParams);
    };
}
function clearForm(){
	 $('#name').textbox('setValue','');
	 $('#status').combobox('clear');
	 $('#roleType').combobox('clear');
	 $('#userGroup').combobox('clear');
}

function viewsUser(id) {
    window.location = 'views.html?userId=' + id;
}
function viewGuser(id) {
    window.location = 'edit.html?userId=' + id;
}
function frozenGUser(id, status, index, createType) {
    var statusText = null;
    var mess = "";
    if (status == 1) {
        mess = "启用";
        statusText = "已启用";
    } else if (status == 0) {
        mess = "停用";
        statusText = "已停用";
    }
    $.messager.confirm({
        title:'修改用户状态',
        msg:'<div class="content">是否确认修改用户状态?</div>',
        ok:'<i class="i-ok"></i> 确定',
        cancel:'<i class="i-close"></i> 取消',
        fn:function(r){
        	if (r){
        		$.ajax({
                    url: '../../../user/frozenGUser.do',
                    type: 'post',
                    data: {
                        'id': id,
                        'status': status
                    },
                    success: function(data) {
                        if (data && data.status == 200) {
                        	$.messager.alert({
                         	      title:'提示',
                         	      msg:'<div class="content">'+mess+'成功</div>',
                         	      ok:'<i class="i-ok"></i> 确定',
                         	      icon:'info'
                         	    });
                            $('#dataGrid').datagrid('updateRow', {
                                index: index,
                                row: {
                                    statusText: statusText,
                                    status: status
                                }
                            });
//                            if(funAuthority){
//	           					 $.each(funAuthority,function(index,data){ 
//	           						 $("."+data).show();
//	           					 });
//                            }
                            $(".datagrid-row").css("height", "45px");
                        } else {
                            $.messager.alert({
                       	      title:'提示',
                       	      msg:'<div class="content">'+mess+'失败，请重试</div>',
                       	      ok:'<i class="i-ok"></i> 确定',
                       	      icon:'error'
                       	    });
                        }
                    },
                    dataType: 'json'
                });
        	}
        }
      });
}

//重置密码
function resetPwd(id){
	$.messager.confirm({
	      title:'重置密码提示',
	      msg:'<div class="content">你确定要重置该用户的密码吗?</div>',
	      ok:'<i class="i-ok"></i> 确定',
	      cancel:'<i class="i-close"></i> 取消',
	      fn:function(r){
	    	  if (r) { 
	    	  $.ajax({
	    	         url: '../../../user/resetPwd.do',
	    	         type: 'post',
	    	         data: {
	    	             'userId': id
	    	         },
	    	         success: function(data) {
	    	             if (data.status == 200) {
	    	            	 $.messager.alert({
	    	              	      title:'提示',
	    	              	      msg:'<div class="content">重置密码成功！</div>',
	    	              	      ok:'<i class="i-ok"></i> 确定',
	    	              	      icon:'info'
	    	              	    });
	    	             } else {
	    	            	 $.messager.alert({
	    	             	      title:'提示',
	    	             	      msg:'<div class="content">重置密码失败，请重试！</div>',
	    	             	      ok:'<i class="i-ok"></i> 确定',
	    	             	      icon:'error'
	    	             	    });
	    	             }
	    	         },
	    	         dataType: 'json'
	    	     });
	    	  }
	      }});
}

