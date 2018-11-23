$(function() {
	getUserRole();
});
//获取角色类型
function getUserRole(){
	$('#roleId').combobox({
		url:'../../../sysRole/sysRoleVo.do',
		method:'post',
		editable: false,
		panelHeight: 200,
		valueField:'id',
		textField:'text'     
		});
	loadUser();
//	if(userInfo.roleType==1){//超级管理员
//		$("#roleId").combobox({disabled:true});
//	}
}

function loadUser(){	
	//获取用户信息
	userId = acceptParam("userId");
	$.get("../../../user/findUser.do?userId="+userId,function(data){
		if(data.status=200&&data.data!=null){
			$('#updateGUserForm').form('load',{
				id: data.data.id,
				loginName: data.data.loginName,
				loginPwd: data.data.loginPwd,
				userName: data.data.userName,
				phone: data.data.phone,
				status: data.data.status+"",
				roleId: data.data.roleId==null?"-1":data.data.roleId+""
			});
			
		/*	if(data.data.roleId){
				$('#roleId').combobox("setValue", data.data.roleId+"");
			}*/
		}else{
			$.messager.alert({
      	      title:'提示',
      	      msg:'<div class="content">获取信息失败...请联系管理员!</div>',
      	      ok:'<i class="i-ok"></i> 确定',
      	      icon:'warning'
      	    });
		}
	},"JSON");
	
	$("input", $("#userName").next("span")).bind("keyup",function(e){
		
	});
	
	$("#submit").click(
			function() {	
				//if ($("#updateGUserForm").form('validate')) {
		
					var logReg=/^[0-9a-zA-Z][\_0-9a-zA-Z|0-9a-zA-Z]{1,19}$/;
					var nameReg=/^[\u4E00-\u9FA5\w]{2,20}$/;
					/*var pwdReg=/^[\S]{6,16}$/;*/
					var phoneReg=/^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/;
					//提交表单
				    var roleId =$('#roleId').combobox('getValue');
					var status =$('#status').combobox('getValue');
				
					 if(!logReg.test($.trim($('#loginName').val()))){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">账号名称无效，请重新输入</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
				     }else if(!nameReg.test($.trim($('#userName').val()))){
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
					 }else if(status==null||status=='null'||status=='-1'){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">账号状态不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }else if(roleId==null||roleId=='null'||roleId==''||roleId=='-1'){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">角色类型不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }else{
						$("#submit").hide();
						_ajaxSubmit();
					}				
				//}
		});
}

function _ajaxSubmit(){
	var formData = $.getFormData($("#updateGUserForm"));
	var arrNum = new Array();
	$.each($('#updateGUserForm input[name=groupIds]'),function(i,v){
		var temNo=$(this).val();
		arrNum[arrNum.length] = temNo;
	});
	var nary=arrNum.sort();
	for(var i=0;i<arrNum.length;i++){
		if (nary[i]==nary[i+1]){
			$.messager.alert({
        	      title:'提示',
        	      msg:'<div class="content">你选择用户组重复，请重新选择!</div>',
        	      ok:'<i class="i-ok"></i> 确定',
        	      icon:'error'
        	    });
			$("#submit").show();
			return;
		}
	}
	formData.groupIds=$.ValiDateGroup($("#updateGUserForm"),"groupIds");
	$.post("../../../user/updateUser.do", formData, function(result) {
			if (result.status == 200) {
				$.messager.alert({
		      	      title:'提示',
		      	      msg:'<div class="content">修改成功!</div>',
		      	      ok:'<i class="i-ok"></i> 确定',
		      	      icon:'info',
		      	      fn:function(){
		      	    	window.location = "list.html";
		      	      }
		      	    });
			} else {
				$.messager.alert({
		      	      title:'提示',
		      	      msg:'<div class="content">修改失败!</div>',
		      	      ok:'<i class="i-ok"></i> 确定',
		      	      icon:'error'
		      	    });
			}
			$("#submit").show();
	});		
}