$(function () {
	initGrid();
	initPurchaseNo();
	initPurchaseDate();
	$("#selectGoods").click(showSelectGoods);
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
			{
			    field : 'skuNo',
			    title : 'sku编号',
			    width : '120px',
			    align : 'center'
			},
			{
			    field : 'img',
			    title : '图片',
			    width : '120px',
			    align : 'center',
			    formatter:getGoodImg
			},
			{
			    field : 'goodsName',
			    title : '产品名称',
			    width : '200px',
			    align : 'left'
			},
			{
			    field : 'supplierNo',
			    title : '供应商ID',
			    width : '100px',
			    align : 'center'
			},
//			{
//			    field : 'goodsDesc',
//			    title : '产品细节',
//			    width : '80px',
//			    align : 'center'
//			},
			{
			    field : 'lastPrice',
			    title : '上次采购价格',
			    width : '90px',
			    align : 'center'
			},
			{
			    field : 'purchaseNum',
			    title : '采购数量',
			    width : '80px',
			    align : 'center',
			    editor : {
					type : 'numberbox'
				}
			},
			{
			    field : 'purchasePrice',
			    title : '采购价格',
			    width : '80px',
			    align : 'center',
			    editor : {
					type : 'numberbox',
					options:{
						min:0, 
						precision:2
					}
				}
			},
//			{
//			    field : 'total',
//			    title : '总金额',
//			    width : '80px',
//			    align : 'center',
//			    formatter:function(value, row, index){
//			    	if(row.purchasePrice && row.purchaseNum){
//			    		return row.purchasePrice * row.purchaseNum;
//			    	}
//			    }
//			},
			{
			    field : 'deliveryDate',
			    title : '交货日期',
			    width : '100px',
			    align : 'center',
			    editor : {
					type : 'datebox'
				}
			},
			{
			    field : 'targetStore',
			    title : '目的仓库',
			    width : '120px',
			    align : 'center',
			    editor : {
					type : 'combobox',
					options: {
                        valueField: "value",
                        textField: "text",
                        editable: false,
                        panelHeight:70,
                        data:[{
                  			text: 'Aamazon warehouse',
                			value: '1'
                		},{
                			text: 'US warehouse',
                			value: '2'
                		}]
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

function showSelectGoods(){
	$('#dialog').dialog({
        title : "选择sku",
        width : 800,
        height : 500,
        closed : false,
        cache : false,
        href : '../goodsku/pickGoodsSku.html',
        modal : true
    });
	return;
}

function selectSelfGoodsCallback(selectGoodsData){
//	var skuIds = [];
	for(var i=0;i<selectGoodsData.length;i++){
		var selectGoods = selectGoodsData[i];
		var key = selectGoods.id;
		if(isExist(key)){
			continue;//$.each不能用continue
		}
		var goods = {};
		goods['key'] = key;
		goods['skuNo'] = selectGoods.skuNo;
		goods['img'] = selectGoods.pics;
		goods['goodsName'] = selectGoods.goodsName;
		goods['supplierNo'] = selectGoods.supplierNo;
		goods['goodsDesc'] = selectGoods.goodsDesc;
		goods['lastPrice'] = selectGoods.lastPrice;
		goods['purchaseNum'] = selectGoods.purchaseNum;
		goods['purchasePrice'] = selectGoods.price;
	    
		$('#goodsGrid').datagrid('appendRow', goods);
		var index = $('#goodsGrid').datagrid("getRowIndex",key);
	    $('#goodsGrid').datagrid('beginEdit', index);    
//	    hasSelGoodsIds.push(key);
//	    skuIds.push(selectGoods.skuId);
	}
//	setActivityPrice(skuIds);
}

function isExist(checkGoodsId){
	var gridDatas = $('#goodsGrid').datagrid("getRows"); // 获取所有数据
    if (!gridDatas || gridDatas.length <= 0) {
    	return false;
    }
    for(var i=0;i<gridDatas.length;i++){
    	var item = gridDatas[i];
    	var goodsId = item.key;
		if(checkGoodsId == goodsId){
			return true;
		}
    }
	return false;
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
function deleteGoods ( id ) {
    var index = $('#goodsGrid').datagrid("getRowIndex",id);
    $('#goodsGrid').datagrid('deleteRow', index);
//    $("#goodsGrid").datagrid("reload");
}

function initPurchaseNo(){
	$.get("../../purchase/getPurchaseNo.do", function(data){
		$("#purchaseNo").html(data.data);
	});
}

function initPurchaseDate(){
	var d = jsUtilTools.dateFormat(new Date());
	$("#purchaseDate").html(d);
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
	$.each(gridDatas, function ( index, item ) {
		var index = $('#goodsGrid').datagrid("getRowIndex",item.key);
	    $('#goodsGrid').datagrid('endEdit', index);    
		$formData['goodsList[' + index + '].skuId'] = item.key;
		$formData['goodsList[' + index + '].purchaseNum'] = item.purchaseNum;
		$formData['goodsList[' + index + '].purchasePrice'] = item.purchasePrice;
		$formData['goodsList[' + index + '].deliveryDate'] = item.deliveryDate;
		$formData['goodsList[' + index + '].targetStore'] = item.targetStore;
	});
	
    // 保存主题
    $.ajax({
	url : '../../purchase/addPurchase.do',
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
		    window.location = 'purchase_list.html';
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
