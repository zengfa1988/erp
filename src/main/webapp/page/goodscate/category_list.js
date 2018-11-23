$(function () {
	initGrid();
	$('#addCategory').click(function () {
		showAddDialo();
    });
});

function closeAddDialog(){
	$('#addDialog').dialog("close");
}

function showAddDialo(){
	$('#addDialog').dialog({
		title : "添加品类" ,
		width : 350,
		height : 250,
		closed : false,
		modal : true
	});
}

function editCategory(id){
	showAddDialo();
	loadCategory(id);
}

function loadCategory(id) {
	$.ajax({
		url : '../../goods/category/getCategory.do',
		data : {
			id : id
		},
		dataType : 'json',
		type : 'get',
		success : function(result) {
			if (result.status == 200 && result.data != null) {
				var o = result.data;
				$("#id").val(o.id);
				$('#addForm').form('load',o);
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

function saveCategory(){
	var validate = $("#addForm").form('validate');
	if(validate){
		var formData = $.getFormData($("#addForm"));
		for(var o in formData){
			if(!formData[o]){
				delete formData[o];
			}
		}
		var successInfo = '添加品类成功';
		if(formData.id){
			successInfo = '修改品类成功';
		}
		$.ajax({
			url:'../../goods/category/addCategory.do',
			type:'post',
			data:formData,
			success:function(result){
				if(result.status==200){
					$.messager.alert('操作提示', successInfo, 'info');
					$.messager.alert({
						title:'提示',
						msg:'<div class="content">'+successInfo+'</div>',
						ok:'<i class="i-ok"></i> 确定',
						icon:'info',
						fn:function(){
							closeAddDialog();
							initGrid();
						}
					});
				}else{
					$.messager.alert('操作提示', result.msg, 'info');
				}
			}
		});
	}
}


function initGrid ( ) {
    $('#grid').datagrid(
	    {
		url : '../../goods/category/getListPage.do',
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
			    field : 'categoryName',
			    title : '品类名称',
			    width : '100px',
			    align : 'center'
			},
			{
			    field : 'skuNum',
			    title : 'sku数量',
			    width : '100px',
			    align : 'center'
			},
			
			{
				field : 'memo',
				title : '备注',
				width : '200px',
				align : 'center'
			},
			{
			    field : 'createName',
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
			    width : '150px',
			    align : 'center',
			    formatter : function ( val, row, index ) {
				var html = '<a class="btn" href="javascript:void(0);" onclick="editCategory(\''
					+ row.id + '\')"><i class="i-op i-op-modify"></i>编辑</a>&nbsp;';
				return html;
			    }
			} ] ],
		toolbar : '#tb',
		onLoadError : function () {
		    $.messager.alert('操作提示', '<div class="content">加载数据失败...请联系管理员！</div>', 'error');
		}
	    });
}