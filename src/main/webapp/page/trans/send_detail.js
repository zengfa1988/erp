$(function() {
	initGrid();
	initLoadSend();
});

function initLoadSend() {
	var id = acceptParam("id");
	$.ajax({
		url : '../../trans/getSend.do',
		data : {
			id : id
		},
		dataType : 'json',
		type : 'get',
		success : function(result) {
			if (result.status == 200 && result.data != null) {
				var o = result.data;
				$("#id").val(o.id);
				var exitTime = jsUtilTools.dateFormat(new Date(o.exitTime));
				$('#exitTime').datebox('setValue',exitTime);
				var arriveTime = jsUtilTools.dateFormat(new Date(o.arriveTime));
				$('#arriveTime').datebox('setValue',arriveTime);
				delete o.exitTime;
				delete o.arriveTime;
				$('#addForm').form('load',o);
				$('#goodsGrid').datagrid('loadData', o.goodsList);
			} else {
				$.messager.alert('错误提示', '<div class="content">加载数据失败！</div>',
						'error');
			}
		},
		error : function(data) {
			$.messager.alert('错误提示', '<div class="content">加载数据失败！</div>',
					'error');
		},
		complete : function(xhr) {
			xhr = null;
		}
	});
}

function initGrid () {
    $('#goodsGrid').datagrid(
	    {
		singleSelect : true, // 是否单选
		autoRowHeight : true,
		striped : false, // 设置为true将交替显示行背景
		fitColumns : true,// 设置是否滚动条
		nowrap : false,
		fit : true,
		remotesort : true,
//		onClickCell : onClickCell,
		loadMsg : '数据加载中,请稍候......',
		idField:"key",
		columns : [ [
		    {field:'key',title:'id',hidden:true},
			{field : 'purchaseId',title : '采购单Id',hidden:true},
			{
			    field : 'purchaseNo',
			    title : '采购单号',
			    width : '120px',
			    align : 'center'
			},
			{field : 'skuId',title : 'sku Id',hidden:true},
			{
			    field : 'skuNo',
			    title : 'sku编号',
			    width : '120px',
			    align : 'center'
			},
			{
			    field : 'goodsName',
			    title : '产品名称',
			    width : '200px',
			    align : 'left'
			},
			{field : 'supplierId',title : '供应商 Id',hidden:true},
			{
			    field : 'supplierNo',
			    title : '供应商ID',
			    width : '100px',
			    align : 'center'
			},
			{
			    field : 'purchaseNum',
			    title : '采购数量',
			    width : '80px',
			    align : 'center'
			},
			{
			    field : 'purchasePrice',
			    title : '采购价格',
			    width : '80px',
			    align : 'center'
			},
			{
			    field : 'targetStore',
			    title : '目的仓库',
			    width : '80px',
			    align : 'center',
			    formatter : parseTargetStore
			},
			{
			    field : 'sendNum',
			    title : '发货数量',
			    width : '80px',
			    align : 'center',
			    editor : {
					type : 'numberbox',
					options:{
						min:0
					}
				}
			}
			 ] ],
		toolbar : '#tb',
		rowStyler : function ( index, row ) {
//		    if (hideIndexs.indexOf(index) >= 0) {
//			return 'display:none';
//		    }
		},
		onLoadError : function () {
		    $.messager.alert('操作提示', '<div class="content">加载数据失败...请联系管理员！</div>', 'error');
		}
	    });
}

function parseTargetStore(value){
	if(value == 1){
		return "Aamazon warehouse";
	}else if(value == 2){
		return "US warehouse";
	}
}