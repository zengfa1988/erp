var statusArray = [ {
    "text" : "全部",
    "value" : "",
    "selected" : true
}, {
    "text" : "审核中",
    "value" : "1"
}, {
    "text" : "已审核",
    "value" : "2"
}, {
    "text" : "已完成",
    "value" : "3"
}];
$(function () {
	$("#status").combobox({
    	data : statusArray,
    	editable : false
    });
	initGrid(getFormParams());
	$('#search').click(function () {
	 // 重新加载数据
		$('#grid').datagrid("load", getFormParams());
    });
	$('#clear').click(function () {
    	clearForm();
    });
});
function clearForm(){
	$("form[class=tsh-toolbar]").form("clear");
	$('#status').combobox('setValue', "");
}

function initGrid ( queryParams ) {
    $('#grid').datagrid(
	    {
		url : '../../purchase/getPurchasePage.do',
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
			    field : 'purchaseNo',
			    title : '采购单号',
			    width : '80px',
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
			    field : 'createName',
			    title : '添加人',
			    width : '80px',
			    align : 'center'
			},
			{
			    field : 'createTime',
			    title : '添加时间',
			    width : '150px',
			    align : 'center',
			    formatter : function ( value, row, index ) {
			    	return jsUtilTools.dateTimeFormat(new Date(value));
			    }
			} ] ],
		toolbar : '#tb',
		onLoadError : function () {
		    $.messager.alert('操作提示', '<div class="content">加载数据失败...请联系管理员！</div>', 'error');
		}
	    });
}

function getFormParams () {
    var $formData = $.getFormData($("#searchForm"));
    for ( var d in $formData) {// 值不存在则删除
	if (!$formData[d]) {
	    delete $formData[d];
	}
    }
    return $formData;
}

function parseStatus ( value ) {
    if (value === 0) {
    	return '停用';
    } else if (value === 1) {
    	return '审核中';
    } else if (value === 2) {
    	return '已审核';
    }else if (value === 4) {
    	return '审核失败';
    } else {
    	return "已完成";
    }
}

function editPurchae(id){
	window.location = "edit_purchase.html?id=" + id;
}

function changeStatus(id,status, index){
	var statusText = null;
    var mess = "";
    if (status == 1) {
        mess = "启用";
        statusText = "正常";
    } else if (status == 0) {
        mess = "删除";
        statusText = "停用";
    }
	$.messager.confirm({
        title:'删除采购单',
        msg:'<div class="content">是否确认删除采购单?</div>',
        ok:'<i class="i-ok"></i> 确定',
        cancel:'<i class="i-close"></i> 取消',
        fn:function(r){
        	if (r){
        		$.ajax({
                    url: '../../purchase/changeStatus.do',
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
                            $('#grid').datagrid('deleteRow', index);
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
        msg:'<div class="content">你确认要导出采购单记录吗?</div>',
        ok:'<i class="i-ok"></i> 确定',
        cancel:'<i class="i-close"></i> 取消',
        fn:function(r){
        	if (r){
        		var excelForm= tshForm.createForm({url:"/purchase/download.do",data:$formData});
//                document.body.appendChild(excelForm);
//                excelForm.submit();
        	}
        }
   });
}

function getFormParams () {
    var $formData = $.getFormData($("#searchForm"));
    for ( var d in $formData) {// 值不存在则删除
	if (!$formData[d]) {
	    delete $formData[d];
	}
    }
    return $formData;
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

