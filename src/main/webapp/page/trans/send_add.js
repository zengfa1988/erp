$(function () {
	initGrid();
	$("#selectGoods").click(showSelectPurchaeGoods);
	$('#expressCompany').combobox({
		required:true,
		editable:false
	});
});

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
			{
			    field : 'operate',
			    title : '操作',
			    width : '100px',
			    align : 'center',
			    formatter : function ( value, row, index ) {
				var html = '<a class="btn" href="javascript:void(0);" onclick="deleteGoods(\'' + row.key
					+ '\')"><i class="i-op i-op-del-1"></i>删除</a>';
				return html;
			    }
			},
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

function showSelectPurchaeGoods(){
	$('#dialog').dialog({
        title : "选择采购单",
        width : 800,
        height : 500,
        closed : false,
        cache : false,
        href : '../purchase/pickPurchaseSku.html',
        modal : true
    });
	return;
}
function selectPurchaseSkuCallback(selectGoodsData){
	for(var i=0;i<selectGoodsData.length;i++){
		var selectGoods = selectGoodsData[i];
		var key = selectGoods.purchaseNo+"_"+selectGoods.skuNo;
		if(isExist(key)){
			continue;//$.each不能用continue
		}
		var goods = {};
		goods['key'] = key;
		goods['purchaseId'] = selectGoods.purchaseId;
		goods['purchaseNo'] = selectGoods.purchaseNo;
		goods['skuId'] = selectGoods.skuId;
		goods['skuNo'] = selectGoods.skuNo;
		goods['goodsName'] = selectGoods.goodsName;
		goods['supplierId'] = selectGoods.supplierId;
		goods['supplierNo'] = selectGoods.supplierNo;
		goods['purchaseNum'] = selectGoods.purchaseNum;
		goods['purchasePrice'] = selectGoods.purchasePrice;
		goods['targetStore'] = selectGoods.targetStore;
	    
		$('#goodsGrid').datagrid('appendRow', goods);
		var index = $('#goodsGrid').datagrid("getRowIndex",key);
	    $('#goodsGrid').datagrid('beginEdit', index);    
//	    hasSelGoodsIds.push(key);
//	    skuIds.push(selectGoods.skuId);
	}
}

function isExist(checkGoodsId){
	var gridDatas = $('#goodsGrid').datagrid("getRows"); // 获取所有数据
    if (!gridDatas || gridDatas.length <= 0) {
    	return false;
    }
    for(var i=0;i<gridDatas.length;i++){
    	var item = gridDatas[i];
    	var goodsId = item.purchaseNo+"_"+item.skuNo;
		if(checkGoodsId == goodsId){
			return true;
		}
    }
	return false;
}

function deleteGoods ( id ) {
    var index = $('#goodsGrid').datagrid("getRowIndex",id);
    $('#goodsGrid').datagrid('deleteRow', index);
}

function save () {
    // 表单校验
    if (!$("#addForm").form("validate")) {
	return;
    }

    var $formData = $.getFormData($("#addForm"));
    for ( var d in $formData) {// 值不存在则删除
		if (!$formData[d]) {
		    delete $formData[d];
		}
    }
    var gridDatas = $('#goodsGrid').datagrid("getRows"); // 获取所有数据
    if (!gridDatas || gridDatas.length <= 0) {
		$.messager.alert('错误提示', '<div class="content">请选择sku商品</div>', 'error');
		return;
    }
    var checkSku = true;
    var checkPurchase = true;
    var checkTargetStore = true;
    var purchaseId = "";
    var purchaseNo = "";
	$.each(gridDatas, function ( index, item ) {
		var index = $('#goodsGrid').datagrid("getRowIndex",item.key);
	    $('#goodsGrid').datagrid('endEdit', index);    
	    $formData['goodsList[' + index + '].purchaseId'] = item.purchaseId;
		$formData['goodsList[' + index + '].purchaseNo'] = item.purchaseNo;
		$formData['goodsList[' + index + '].skuId'] = item.skuId;
		$formData['goodsList[' + index + '].skuNo'] = item.skuNo;
		$formData['goodsList[' + index + '].goodsName'] = item.goodsName;
		$formData['goodsList[' + index + '].supplierId'] = item.supplierId;
		$formData['goodsList[' + index + '].supplierNo'] = item.supplierNo;
		$formData['goodsList[' + index + '].purchaseNum'] = item.purchaseNum;
		$formData['goodsList[' + index + '].purchasePrice'] = item.purchasePrice;
		if(item.sendNum<1){
			checkSku = false;
			$('#goodsGrid').datagrid('beginEdit', index);    
		}
		$formData['goodsList[' + index + '].sendNum'] = item.sendNum;
		if(purchaseId != "" && purchaseId != item.purchaseId){
			checkPurchase = false;
		}
		purchaseId = item.purchaseId;
		purchaseNo = item.purchaseNo;
		if($formData.targetStore != item.targetStore){
			checkTargetStore = false;
		}
	});
	if(!checkSku){
		$.messager.alert('错误提示', '<div class="content">请输入sku发货数量</div>', 'error');
		return;
	}
	if(!checkPurchase){
		$.messager.alert('错误提示', '<div class="content">采购单号不一致</div>', 'error');
		return;
	}
	if(!checkTargetStore){
		$.messager.alert('错误提示', '<div class="content">发货目的仓库与采购单不一致</div>', 'error');
		return;
	}
	$formData.purchaseId = purchaseId;
	$formData.purchaseNo = purchaseNo;
	
    // 保存主题
    $.ajax({
	url : '../../trans/addSend.do',
	traditional : true,
	type : 'post',
	async : true,
	data : $formData,
	cache : false,
	beforeSend : function () {
	},
	success : function ( data ) {
	    if (data.status == 200) {
		$.messager.alert('成功提示', '<div class="content">操作成功！</div>', 'info', function () {
		    window.location = 'send_list.html';
		});
	    } else {
            var errMsg = data.msg ? data.msg : '操作失败！';
		$.messager.alert('错误提示', '<div class="content">'+errMsg+'</div>', 'error');
	    }
	},
	error : function ( data ) {
	    $.messager.alert('错误提示', '<div class="content">操作失败！</div>', 'error');
	},
	complete : function ( xhr ) {
	    xhr = null;
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


