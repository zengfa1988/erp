$(function() {
	var url = "../../user/query";
	initDataGrid(url,{});
	initCreateTypeData();
});

function initDataGrid(url,queryParams){
	$('#dataGrid').datagrid({
        url: url,
        queryParams: queryParams,
        rownumbers: true, // 行号
        singleSelect: true, // 是否单选
        pagination: true, // 分页控件
        pageSize: 15,
        pageList: [15, 30, 50],
        autoRowHeight: false,
        fit: true,
        striped: false, // 设置为true将交替显示行背景
        fitColumns: true,
        nowrap: false,
        remotesort: false,
        checkOnSelect: false,
        selectOnCheck: false,
        method: "GET", // 请求数据的方法
        loadMsg: '数据加载中,请稍候......',
        idField: 'id',
//        rowStyler: function(index, row) {
//            return 'font-size: 20px;';
//        },
        columns:[ [
                   {field: 'loginName', title: '账号名', width: 100, align: 'center'},
                   {field: 'userName',title: '用户名称',width: 100, align: 'center'}, 
                   {field: 'createDate',title: '创建时间',width: 100,align: 'center'}, 
                   {field: 'roleName', title: '角色', width: 80,align: 'center'}, 
                   { field: 'manager', title: '是否为管理员',width: 80,align: 'center'},
                   { field: 'status',title: '状态',width: 80,align: 'center'},
                   {field: 'opts',title: '操作', width: 250,align: 'center',formatter: function(value, row, index) {
                	   var temp ="";
                	   temp += "<a class='btn' href='javascript:;'   onclick='viewsUser(\"" + row.id + "\")'><i class='i-op i-op-view'></i>查看</a>&nbsp;";
                	   temp += "<a class='btn' href='javascript:;'  onclick='viewGuser(\"" + row.id + "\")'><i class='i-op i-op-modify'></i>编辑</a>&nbsp;";
                	   temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '1,' + index + ")'><i class='i-op i-op-unlock'></i>启用</a>&nbsp;";
                	   temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '0,' + index + ")'><i class='i-op i-op-lock'></i>停用</a>&nbsp;";
                	   temp += "<a class='btn' href='javascript:;'     onclick='resetPwd("+row.id+");'><i class='i-op i-op-set'></i>重置密码</a>&nbsp;";
                	   temp +=   "<a class='btn' href='javascript:;'  onclick='editUserAuthority(\""+ row.id + "\")'><i class='i-op i-op-edit'></i>授权</a>&nbsp;";
                	   return temp;
                   }}
        ]] ,
        onLoadError: function() {
//        	 $.messager.alert({
//        	      title:'提示',
//        	      msg:'<div class="content">获取信息失败...请联系管理员!</div>',
//        	      ok:'<i class="i-ok"></i> 确定',
//        	      icon:'warning'
//        	    });
        	 $.messager.alert('操作提示', '获取信息失败...请联系管理员!', 'error');
        },
        onLoadSuccess: function(data) {
//        	$(".datagrid-row").css("height", "45px");
        	if(data==null||data.total==0){
        		$.messager.alert({
           	      title:'提示',
           	      msg:'<div class="content">当前没有查询到记录!</div>',
           	      ok:'<i class="i-ok"></i> 确定',
           	      icon:'warning'
           	    });
        	}
        },
        toolbar: '#tb'
    });
}

function initCreateTypeData(){
	var createType = [];
	createType.push({'id': -1,'text': '全部',"selected":true});
	createType.push({'id': 1,'text': '平台'});
	createType.push({'id': 2,'text': '县域中心'});
	$('#createType').combobox({
		  data: createType,
		  valueField: 'id',
		  textField: 'text',
		  editable: true
//		  panelHeight:'500px'
	});
}