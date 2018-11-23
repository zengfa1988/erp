$(function() {
	initLoadPayment();
});

function initLoadPayment(){
	var id = acceptParam("id");
	$.ajax({
		url : '../../payment/getPayment.do',
		data : {
			id : id
		},
		dataType : 'json',
		type : 'get',
		beforeSend : function() {
		},
		success : function(result) {
			if (result.status == 200 && result.data != null) {
				var o = result.data;
				$("#id").val(o.id);
				$('#addForm').form('load',o);
				$("#purchaseFee").html(o.needPayFee);
//				var needFee = o.needPayFee - o.hasPayFee;
//				$("#needFee").html(needFee);
//				$("#supplierName").textbox("setValue", supplier.supplierName);
//				$("#licenceNo").textbox("setValue", supplier.licenceNo);
//				$("#legalPersonName").textbox("setValue", supplier.legalPersonName);
//				$("#idCard").textbox("setValue", supplier.idCard);
//				$("#contactName").textbox("setValue", supplier.contactName);
//				$("#contactPhone").textbox("setValue", supplier.contactPhone);
//				$("#contactAddress").textbox("setValue", supplier.contactAddress);
//				if(supplier.supplierImg){
//					$("#thumbnail").attr('src', supplier.supplierImg);
//				}
//				changeSupplierType(supplier.supplierType);
//				if(supplier.categoryPath){
//					categoryPathArray = supplier.categoryPath.split(";");
//				}
//				if(supplier.userStatus==0){
//					$("#userStatusSpan").show();
//					$("#resetPwd").hide();
//				}else{
//					$("#userStatusSpan").html("已开通账号");
//				}
//				initCategorySelect(true);
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
//			maskObj.hideMask();// 隐藏遮蔽罩
			xhr = null;
		}
	});
}