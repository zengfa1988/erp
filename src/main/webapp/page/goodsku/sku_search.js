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
$(function () {
	initGrid(getFormParams());
	$('#search').click(function () {
		if (!$("#supplierForm").form("validate")) {
		    return;
		}
	 // 重新加载数据
		$('#grid').datagrid("load", getFormParams());
    });
    $('#clear').click(function () {
    	clearForm();
    });
});

function clearForm(){
	$("form[class=tsh-toolbar]").form("clear");
}

function initGrid ( queryParams ) {
    $('#grid').datagrid(
	    {
		url : '../../goods/sku/getListPage.do',
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
			    field : 'skuNo',
			    title : 'SKU编号',
			    width : '120px',
			    align : 'center'
			},
			{
			    field : 'goodsName',
			    title : '产品名称',
			    width : '200px',
			    align : 'center'
			},
			
			{
				field : 'pics',
				title : '图片',
				width : '150px',
				align : 'center',
				formatter : parseImg
			},
			{
			    field : 'partNumber',
			    title : 'PART NUMBER',
			    width : '120px',
			    align : 'center'
			},
			{
			    field : 'supplierNo',
			    title : '供应商ID',
			    width : '100px',
			    align : 'center'
			},
			{
			    field : 'goodsNo',
			    title : '供应商产品编号',
			    width : '120px',
			    align : 'center'
			},
			{
			    field : 'price',
			    title : '单价',
			    width : '100px',
			    align : 'center'
			},
			{
			    field : 'sendPlatform',
			    title : '发货平台',
			    width : '100px',
			    align : 'center',
			    formatter : parseSendPlatform
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
			},
			{
			    field : 'operate',
			    title : '操作',
			    width : '300px',
			    align : 'center',
			    formatter : function ( val, row, index ) {
//				var html = '<a class="btn" href="javascript:void(0);" onclick="editSku(\''
//					+ row.id + '\')"><i class="i-op i-op-modify"></i>编辑</a>&nbsp;';
				var html = '<a class="btn" href="javascript:void(0);" onclick="viewSku(\''
					+ row.id + '\')"><i class="i-op i-op-open"></i>查看</a>';
//				if(row.status==1){
//					html += '<a class="btn" href="javascript:void(0);" onclick="changeStatus(\''
//						+ row.id + '\',0,'+index+')"><i class="i-op i-op-lock"></i>禁用</a>';
//				}else{
//				}
				return html;
			    }
			} ] ],
		toolbar : '#tb',
		onLoadError : function () {
		    $.messager.alert('操作提示', '<div class="content">加载数据失败...请联系管理员！</div>', 'error');
		}
	    });
}

function getFormParams () {
    var $formData = $.getFormData($("#skuForm"));
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
    	return '正常';
    } else if (value === 2) {
    	return '审核未通过';
    } else {
    	return "未知状态";
    }
}
function parseSendPlatform(value){
	if (value === 1) {
    	return '亚马逊';
    } else if (value === 2) {
    	return 'ebay';
    }
}

function parseImg(value){
	if(!value){
		return;
	}
	var imgArray = value.split(",");
	var html = "<img src='"+imgArray[0]+"' height='100px' width='100px'>";
	return html;
}

function editSku ( id ) {
    window.location = "edit_sku.html?id=" + id;
}
function viewSku ( id ) {
    window.location = "sku_detail.html?id=" + id;
}

function changeStatus(id,status, index){
	var statusText = null;
    var mess = "";
    if (status == 1) {
        mess = "启用";
        statusText = "正常";
    } else if (status == 0) {
        mess = "禁用";
        statusText = "停用";
    }
	$.messager.confirm({
        title:'修改sku状态',
        msg:'<div class="content">是否确认修改sku状态?</div>',
        ok:'<i class="i-ok"></i> 确定',
        cancel:'<i class="i-close"></i> 取消',
        fn:function(r){
        	if (r){
        		$.ajax({
                    url: '../../goods/sku/changeStatus.do',
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
