var statusArray = [ {
    "text" : "全部",
    "value" : "",
    "selected" : true
}, {
    "text" : "停用",
    "value" : "0"
}, {
    "text" : "正常",
    "value" : "1"
}];
var isModifyStatus = false;

$(function () {
    $("#status").combobox({
    	data : statusArray,
    	editable : false
    });

    // 搜索
    $('#search').click(function () {
		if (!$("#supplierForm").form("validate")) {
		    return;
		}
	//	clearForm();
	    
	 // 重新加载数据
		$('#grid').datagrid("load", getFormParams());
    });
    $('#clear').click(function () {
    	clearForm();
    });
    // 初始化表格
    initGrid(getFormParams());
    fileUpLoad('#import-btn','../../supplier/import.do',function(){initGrid(getFormParams())});
});

function clearForm(){
	$("form[class=tsh-toolbar]").form("clear");
	$('#status').combobox('setValue', "");
}

/**
 * 初始化表格
 * 
 * @param queryParams
 */
function initGrid ( queryParams ) {
    $('#grid').datagrid(
	    {
		url : '../../supplier/getSupplierPage.do',
		queryParams : queryParams,
		singleSelect : true, // 是否单选
		autoRowHeight : true,
		striped : false, // 设置为true将交替显示行背景
		fitColumns : true,// 设置是否滚动条
		nowrap : false,
		fit : true,
		remotesort : true,
		pagination : true, // 分页控件
		pageNumber : 1,
		pageSize : 10,
		method : "get", // 请求数据的方法
		loadMsg : '数据加载中,请稍候......',
		columns : [ [
			{
			    field : 'supplierNo',
			    title : '供应商ID',
			    width : '80px',
			    align : 'center'
			},
			{
			    field : 'supplierName',
			    title : '供应商名称',
			    width : '200px',
			    align : 'center'
			},
			
			{
				field : 'contactMan',
				title : '联系人',
				width : '80px',
				align : 'center'
			},
			{
			    field : 'contactPhone',
			    title : '联系人电话',
			    width : '100px',
			    align : 'center'
			},
			{
			    field : 'status',
			    title : '状态',
			    width : '80px',
			    align : 'center',
			    formatter : parseStatus
			},
			{
			    field : 'createMan',
			    title : '添加人',
			    width : '80px',
			    align : 'center'
			},
			{
			    field : 'createDate',
			    title : '添加时间',
			    width : '150px',
			    align : 'center',
			    formatter : function ( value, row, index ) {
			    	return jsUtilTools.dateTimeFormat(new Date(value));
			    }
			},
			{
			    field : 'operate',
			    title : '操作',
			    width : '300px',
			    align : 'center',
			    formatter : function ( val, row, index ) {
				var html = '<a class="btn" href="javascript:void(0);" onclick="editSupplier(\''
					+ row.id + '\')"><i class="i-op i-op-modify"></i>编辑</a>&nbsp;';
				html += '<a class="btn" href="javascript:void(0);" onclick="viewSupplier(\''
					+ row.id + '\')"><i class="i-op i-op-open"></i>查看</a>';
				if(row.status==1){
					html += '<a class="btn" href="javascript:void(0);" onclick="changeStatus(\''
						+ row.id + '\',0,'+index+')"><i class="i-op i-op-lock"></i>停用</a>';
				}else{
					html += '<a class="btn" href="javascript:void(0);" onclick="changeStatus(\''
						+ row.id + '\',1,'+index+')"><i class="i-op i-op-unlock"></i>启用</a>';
				}
				return html;
			    }
			} ] ],
		toolbar : '#tb',
		onLoadError : function () {
		    $.messager.alert('操作提示', '<div class="content">加载数据失败...请联系管理员！</div>', 'error');
		}
	    });
}

function excelExport(){
	var $formData = getFormParams();
	$.messager.confirm({
        title:'导出提示',
        msg:'<div class="content">你确认要导出供应商记录吗?</div>',
        ok:'<i class="i-ok"></i> 确定',
        cancel:'<i class="i-close"></i> 取消',
        fn:function(r){
        	if (r){
        		var excelForm= tshForm.createForm({url:"/supplier/download.do",data:$formData});
//                document.body.appendChild(excelForm);
//                excelForm.submit();
        	}
        }
   });
}

/**
 * 获取表单参数
 * 
 * @returns
 */
function getFormParams () {
    var $formData = $.getFormData($("#supplierForm"));
    for ( var d in $formData) {// 值不存在则删除
	if (!$formData[d]) {
	    delete $formData[d];
	}
    }
    return $formData;
}

/**
 * 解析Banner状态
 * 
 * @param value
 * @returns {String}
 */
function parseStatus ( value ) {
    if (value === 0) {
    	return '停用';
    } else if (value === 1) {
    	return '正常';
    } else if (value === 2) {
    	return '审核未通过';
    } else {
    	return "未知状态";
    }
}


function editSupplier ( supplierId ) {
    window.location = "edit_supplier.html?supplierId=" + supplierId;
}
function viewSupplier ( supplierId ) {
    window.location = "supplier_detail.html?supplierId=" + supplierId;
}

function changeStatus(supplierId,status, index){
	var statusText = null;
    var mess = "";
    if (status == 1) {
        mess = "启用";
        statusText = "正常";
    } else if (status == 0) {
        mess = "停用";
        statusText = "停用";
    }
	$.messager.confirm({
        title:'修改供应商状态',
        msg:'<div class="content">是否确认修改供应商状态?</div>',
        ok:'<i class="i-ok"></i> 确定',
        cancel:'<i class="i-close"></i> 取消',
        fn:function(r){
        	if (r){
        		$.ajax({
                    url: '../../supplier/changeStatus.do',
                    type: 'post',
                    data: {
                        'id': supplierId,
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
                            $('#grid').datagrid('updateRow', {
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

function excelExport(){
	var $formData = getFormParams();
	$.messager.confirm({
        title:'导出提示',
        msg:'<div class="content">你确认要导出供应商记录吗?</div>',
        ok:'<i class="i-ok"></i> 确定',
        cancel:'<i class="i-close"></i> 取消',
        fn:function(r){
        	if (r){
        		var excelForm= tshForm.createForm({url:"../../supplier/download.do",data:$formData});
                document.body.appendChild(excelForm);
                excelForm.submit();
        	}
        }
   });
}

var tshForm = {
	    //创建表单
	    createForm: function (options) {
	        var form = document.createElement("form");
	        form.action =options.url;
	        form.method = options.type || "post";
	        form.target = "_blank";
	        form.style.display="none";
	        //设置控件ID
	        form.id=options.formId||"form_"+Math.random();;
	        //提交参数
	        for (var item in options.data) {
	            var el = document.createElement("input");
	            el.setAttribute("name", item);
	            el.setAttribute("type", "hidden");
	            el.setAttribute("value", options.data[item]);
	            form.appendChild(el);
	        }
	        return form;
	    }
	};
