$(function(){
	getUserRole();
	submitForm();
});

//获取角色类型
function getUserRole(){
	$('#roleType').combobox({
		url:'../../../sysRole/sysRoleVo.do',
		cache: false,
        //panelHeight: 'auto',//自动高度适合
		panelHeight: 200,
		method:'post',
		editable: false,
		valueField:'id',
		textField:'text'
		});
};

function clearForm() {
    $('#addGUserForm').form('clear');
}

function submitForm(){	
	$("#submit").click(
			function() {
				if ($("#addGUserForm").form('validate')) {
					var logReg=/^[0-9a-zA-Z][\_0-9a-zA-Z|0-9a-zA-Z]{1,19}$/;
					var nameReg=/^[\u4E00-\u9FA5\w]{2,20}$/;
					/*var pwdReg=/^[\S]{6,16}$/;*/
					var phoneReg=/^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/;
					//提交表单
				    var roleType =$('#roleType').combobox('getValue');
					//var status =$('#status').combobox('getValue');
				
					 if(!logReg.test($.trim($('#loginName').val()))){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">账号名称无效，请重新输入</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
				     }/*else if(!pwdReg.test($.trim($('#loginPwd').val()))){
						$.messager.alert('提示','密码无效，请重新输入');
					 }*/else if(!nameReg.test($.trim($('#userName').val()))){
						$.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">用户名称无效，请重新输入</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }else if($('#phone').val()!=""&&!phoneReg.test($.trim($('#phone').val()))){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">手机号码无效，请重新输入</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
				/*	 }else if(status==null||status=='null'||status=='-1'){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">账号状态不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });*/
					 }else if(roleType==null||roleType=='null'||roleType==''||roleType=='-1'){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">角色类型不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }/*else if(!$.ValiDateISNULL($("#addGUserForm"),"groupIds")){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">用户组不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }*/else{
						$("#submit").hide();
						var loginName=$.trim($('#loginName').val());
						$.ajax({
							url:'../../../user/findUserIsRegister.do',
							type:'post',
							data:{
								loginName:loginName
							},
							success:function(data){
								if(data.data!=null&&data.data>0){
									 $.messager.alert({
					              	      title:'提示',
					              	      msg:'<div class="content">账户名称已存在，请重新输入</div>',
					              	      ok:'<i class="i-ok"></i> 确定',
					              	      icon:'error'
					              	    });
								}else if(data.data!=null&&data.data==0){
									_ajaxSubmit();
								}
							},
							complete:function(){
								$("#submit").show();
							},
							dataType:'json'		
						});
					}			
				}
		});
}

function _ajaxSubmit(){
	var formData = $.getFormData($("#addGUserForm"));
	var arrNum = new Array();
	$.each($('#addGUserForm input[name=groupIds]'),function(i,v){
		var temNo=$(this).val();
		arrNum[arrNum.length] = temNo;
	});
	var nary=arrNum.sort();
	for(var i=0;i<arrNum.length;i++){
		if (nary[i]==nary[i+1]){
			alert("你选择用户组重复，请重新选择!");
			return;
		}
	}
	formData.groupIds=$.ValiDateGroup($("#addGUserForm"),"groupIds");
	
	$.post("../../../user/addUser.do", formData, function(result) {
		if (result.status == 200) {
			$.messager.alert({
	     	      title:'提示',
	     	      msg:'<div class="content">添加成功!</div>',
	     	      ok:'<i class="i-ok"></i> 确定',
	     	      icon:'info',
	     	     fn:function(){
	     	    	window.location = "list.html";	 
	     	     }
	     	    });
			} else {
				$.messager.alert({
		     	      title:'提示',
		     	      msg:'<div class="content">添加失败!</div>',
		     	      ok:'<i class="i-ok"></i> 确定',
		     	      icon:'error'
		     	    });
			}
	});		
}