$(function() {
	initGrid("ids2");
});

function initGrid (idsClass) {
	$('#selectDataGrid').datagrid({
		url : '../../purchase/getSkuListPage.do',
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
			{field:'purchaseId',title:'采购单Id',hidden:true},
			{field:'purchaseNo',title:'采购单号',width:100,align:'left'},
			{field:'skuId',title:'SKU ID',hidden:true},
			{field:'skuNo',title:'SKU编号',width:100,align:'left'},
			{field:'goodsName',title:'产品名称',width:200,align:'left'},
			{field:'supplierId',title:'供应商 id',hidden:true},
			{field:'supplierNo',title:'供应商ID',width:100,align:'center'},
			{field:'purchaseNum',title:'采购数量',width:70,align:'center'},
			{field:'purchasePrice',title:'采购价格',width:70,align:'center'},
			{field:'targetStore',title:'目的仓库',width:70,align:'center',formatter:parseTargetStore}
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
function parseTargetStore(value){
	if(value == 1){
		return "Aamazon warehouse";
	}else if(value == 2){
		return "US warehouse";
	}
}

function clearFormData(){
	$("#pickGoodsSkuForm").form("clear");
	$("#supplierId").combobox("setValue","");
}

function searchForm(){
	 var data = $.getFormData($('#pickGoodsSkuForm'));
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
	if(selectData.length<1){
		$.messager.alert('操作提示', '请选择sku', 'error');
		return;
	}
	if(typeof(selectPurchaseSkuCallback) == "function"){
		selectPurchaseSkuCallback(selectData);
		closePick();
	}else{
		$.messager.alert('系统提示', '没有选择商品回调函数selectSelfGoodsCallback', 'error');
	}
}

function getGoodImg(value, row, index){
	if(!value){
		return;
	}
	var imgArray = value.split(",");
  	var temp = "" +
  			   "<img src='"+imgArray[0]+"' style='display:inline-block;vertical-align: top;max-width:80px;max-height:80px' />";
  	return temp;
}

/**
 * 设置列表表头复选框
 * @param idsClass
 * @returns {String}
 */
function initAllCheckedBtn(idsClass){
	if(idsClass=="ids1"){
		return '<input type="checkbox" value="0" checked="checked" for="ids1" name="c1" class="allcheck"/>';
	}else if(idsClass=="ids2"){
		return '<input type="checkbox" value="0" for="ids2" name="c2" class="allcheck"/>';
	}
}

function getCheckedBox(data,index,clazz,row){
	  var list = data;
	  if(!list){
		  return "";
	  }
	  var ul_begin = "<ul class='datagrid-ul'>"
	  var lis = "";
	  var end = false;
	  for(var i=0;i<list.length;i++){
		  var d = list[i];
		  if(i==list.length-1){
			  end = true;
		  }
		  if (d.selected) {
			  lis +="<li end="+end+"></li>"
		  } else {
			  lis +="<li end="+end+"><input type='checkbox' rowindex='"+index +"' goodsid='"+row.goodsId+"' value='"
			  +d.skuId + "' skumsg='" + d.skuMsg + "' skustock='" + d.stock + "' class='"+clazz+"'/></li>";
		  }
	  }
	  var ul_end="</ul>";
	  var temp= ul_begin+lis+ul_end; 
	  return temp;
	  
}

function closePick() {
	$('#dialog').dialog('close');
}
