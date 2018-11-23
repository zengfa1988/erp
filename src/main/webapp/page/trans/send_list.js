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
function clearForm(){
	$("form[class=tsh-toolbar]").form("clear");
}
function initGrid ( queryParams ) {
    $('#grid').datagrid(
	    {
		url : '../../trans/getListPage.do',
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
			    field : 'transNo',
			    title : '批次号',
			    width : '80px',
			    align : 'center'
			},
			{
			    field : 'purchaseNo',
			    title : '采购单号',
			    width : '80px',
			    align : 'center'
			},
			{
			    field : 'expressType',
			    title : '运输方式',
			    width : '80px',
			    align : 'center',
			    formatter : parseExpressType
			},
			
			{
				field : 'expressCompany',
				title : '物流公司',
				width : '80px',
				align : 'center',
				formatter : parseExpressCompany
			},
			{
			    field : 'exitPort',
			    title : '出货港口',
			    width : '100px',
			    align : 'center'
			},
			{
			    field : 'targetStore',
			    title : '目的仓库',
			    width : '100px',
			    align : 'center',
			    formatter : parseTargetStore
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
			},{
			    field : 'operate',
			    title : '操作',
			    width : '200px',
			    align : 'center',
			    formatter : function ( val, row, index ) {
				var html = '<a class="btn" href="javascript:void(0);" onclick="viewSend(\''
					+ row.id + '\')"><i class="i-op i-op-open"></i>查看</a>';
				html += '<a class="btn" href="javascript:void(0);" onclick="viewProgress(\''
					+ row.id + '\')"><i class="i-op i-op-open"></i>进度</a>';
				return html;
			    }
			}
			 ] ],
		toolbar : '#tb',
		onLoadError : function () {
		    $.messager.alert('操作提示', '<div class="content">加载数据失败...请联系管理员！</div>', 'error');
		}
	    });
}
function parseExpressType(value){
	if(value==1){
		return "海运";
	}else if(value==2){
		return "空运";
	}
}
function parseExpressCompany(value){
	if(value==1){
		return "UPS";
	}else if(value==2){
		return "EMS";
	}else if(value==3){
		return "FEDEX";
	}
}
function parseTargetStore(value){
	if(value==1){
		return "亚马逊";
	}else if(value==2){
		return "eBay";
	}
}
function parseStatus(value){
	if(value==1){
		return "已发货";
	}else if(value==2){
		return "已收货";
	}
}

function getFormParams () {
    var $formData = $.getFormData($("#searchForm"));
    for ( var d in $formData) {// 值不存在则删除
	if (!$formData[d]) {
	    delete $formData[d];
	}
    }
    $formData.status = 1;
    return $formData;
}

function viewSend(id){
	window.location = "send_detail.html?id=" + id;
}
function viewProgress(id){
	window.location = "send_detail.html?id=" + id;
}
function viewProgress(id){
	sessionStorage.setItem("transId",id);
	$('#dialog').dialog({
        title : "进度",
        width : 800,
        height : 500,
        closed : false,
        cache : false,
        href : '../trans/progress.html?id='+id,
        modal : true
    });
	return;
}
