$(function() {
	initGrid("ids2");
});

function initGrid (idsClass) {
	var queryParams = getFormParams();
	queryParams.status = 2;
	$('#selectDataGrid').datagrid({
		url : '../../purchase/getPurchasePage.do',
		queryParams : queryParams,
		pagination: true, //分页控件
		autoRowHeight : true,
		striped : false, // 设置为true将交替显示行背景
		fitColumns : false,// 设置是否滚动条
		nowrap : false,
		remotesort : true,
//		onClickCell : onClickCell,
		method : "get", // 请求数据的方法
		loadMsg : '数据加载中,请稍候......',
		columns : [ [
//			{field:'skuId',width:30,title:initAllCheckedBtn(idsClass),align:'center',formatter:function(value,row,index){return getCheckedBox(row.skuList,index,idsClass,row);}},
			{field:'id',width:30,checkbox:true},
			{field:'purchaseNo',title:'采购单号',width:150,align:'left'},
			{field:'purchaseFee',title:'采购金额',width:100,align:'left'},
			{field:'createName',title:'添加人',width:250,align:'left'},
			{field:'createTime',title:'添加时间',width:250,align:'left',formatter : function ( value, row, index ) {
		    	return jsUtilTools.dateTimeFormat(new Date(value));
		    }}
		] ],
		toolbar : '#pickGoodsSkuPageTb',
		onLoadError : function () {
		    $.messager.alert('操作提示', '<div class="content">加载数据失败...请联系管理员！</div>', 'error');
		},
	});
}

function parsePrice(value){
	if(!value){
		return "";
	}
	return value/100;
}

function getFormParams () {
    var $formData = $.getFormData($("#searchForm"));
    for ( var d in $formData) {// 值不存在则删除
	if (!$formData[d]) {
	    delete $formData[d];
	}
    }
    $formData['status'] = 1;
    return $formData;
}

function clearFormData(){
	$("#selectPurchaseForm").form("clear");
}

function searchForm(){
	 var data = $.getFormData($('#selectPurchaseForm'));
	 data.status = 2;
	 $('#selectDataGrid').datagrid('load',filterQueryData(data));
}

function filterQueryData(data){
	for(var d in data){
		if(!data[d]){					
			delete data[d];
		}
	}
	return data;
}

function bind(){
	var selectData = $('#selectDataGrid').datagrid('getSelections');
	if(selectData.length!=1){
		$.messager.alert('操作提示', '请选择一个采购单', 'error');
		return;
	}
	if(typeof(selectPurchaseCallback) == "function"){
		selectPurchaseCallback(selectData);
		closePick();
	}else{
		$.messager.alert('系统提示', '没有选择商品回调函数selectSelfGoodsCallback', 'error');
	}
}

function closePick() {
	$('#dialog').dialog('close');
}

