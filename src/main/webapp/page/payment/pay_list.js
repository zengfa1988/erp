$(function () {
	$('#search').click(function () {
		if (!$("#searchForm").form("validate")) {
		    return;
		}
	 // 重新加载数据
		$('#grid').datagrid("load", getFormParams());
    });
    $('#clear').click(function () {
    	clearForm();
    });
    // 初始化表格
    initGrid(getFormParams());
});

function initGrid ( queryParams ) {
    $('#grid').datagrid(
	    {
		url : '../../payment/getListPage.do',
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
			    field : 'paymentNo',
			    title : '付款单号',
			    width : '80px',
			    align : 'center'
			},
			{
			    field : 'paymentType',
			    title : '请款类型',
			    width : '200px',
			    align : 'center',
			    formatter : parsePaymentType
			},
			
			{
				field : 'purchaseNo',
				title : '采购单号',
				width : '80px',
				align : 'center'
			},
			{
			    field : 'needPayFee',
			    title : '应付金额',
			    width : '100px',
			    align : 'center'
			},
			{
			    field : 'paymentFee',
			    title : '申请金额',
			    width : '100px',
			    align : 'center'
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
			},
			{
			    field : 'operate',
			    title : '操作',
			    width : '150px',
			    align : 'center',
			    formatter : function ( val, row, index ) {
//				var html = '<a class="btn" href="javascript:void(0);" onclick="editPayment(\''
//					+ row.id + '\')"><i class="i-op i-op-modify"></i>编辑</a>&nbsp;';
				var html = '<a class="btn" href="javascript:void(0);" onclick="changeStatus(\''
					+ row.id + '\',4,'+index+')"><i class="i-op i-op-open"></i>完成支付</a>';
				if(row.status==1){
				}else{
//					html += '<a class="btn" href="javascript:void(0);" onclick="changeStatus(\''
//						+ row.id + '\',1,'+index+')"><i class="i-op i-op-unlock"></i>启用</a>';
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
function parseStatus ( value ) {
    if (value === 1) {
    	return '审核中';
    } else if (value === 2) {
    	return '审核成功';
    } else if (value === 3) {
    	return '审核失败';
    } else if (value === 4) {
    	return '已支付';
    } else {
    	return "未知状态";
    }
}
function parsePaymentType(value){
	if (value === 1) {
		return "货款";
	}else if(value === 2){
		return "头程物流";
	}else if(value === 3){
		return "二程物流";
	}else if(value === 4){
		return "图片费";
	}else if(value === 5){
		return "商标支出";
	}else if(value === 6){
		return "办公支出";
	}else if(value === 7){
		return "团建支出";
	}else if(value === 8){
		return "出差费用支出";
	}
}
function clearForm(){
	$("form[class=tsh-toolbar]").form("clear");
	$('#status').combobox('setValue', "");
}
function getFormParams () {
    var $formData = $.getFormData($("#searchForm"));
    for ( var d in $formData) {// 值不存在则删除
	if (!$formData[d]) {
	    delete $formData[d];
	}
    }
    $formData.status = 2;
    return $formData;
}

function editPayment(id){
	window.location = "edit_payment.html?id=" + id;
}
function viewPayment(id){
	window.location = "view_payment.html?id=" + id;
}

function changeStatus(id,status, index){
	var statusText = null;
    var mess = "";
    if (status == 4) {
    	mess = "支付";
        statusText = "完成支付";
    }
	$.messager.confirm({
        title:'确认操作',
        msg:'<div class="content">是否确认'+statusText+'?</div>',
        ok:'<i class="i-ok"></i> 确定',
        cancel:'<i class="i-close"></i> 取消',
        fn:function(r){
        	if (r){
        		$.ajax({
                    url: '../../payment/changeStatus.do',
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
