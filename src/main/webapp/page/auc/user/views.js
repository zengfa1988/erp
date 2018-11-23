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
}

function loadUser(){	
	//获取用户信息
	userId = acceptParam("userId");
	$.get("../../../user/findUser.do?userId="+userId,function(data){
		if(data.status=200&&data.data!=null){
			$("#loginName").html(data.data.loginName);
			$("#userName").html(data.data.userName);
			$("#phone").html(data.data.phone);
			$('#updateGUserForm').form('load',{
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
	
}