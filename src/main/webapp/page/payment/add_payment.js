$(function () {
	$("#selectPurchase").click(showSelectPurchase);
});
function savePayment(){
	var validate = $("#addForm").form('validate');
	if(validate){
		var formData = $.getFormData($("#addForm"));
		for(var o in formData){
			if(!formData[o]){
				delete formData[o];
			}
		}
		console.info(formData);
		if(!formData.paymentType){
			$.messager.alert('提示', '请选择请款类型', 'error');
			return;
		}
		if(formData.paymentType==1&&!formData.purchaseId){
			$.messager.alert('提示', '请选择采购单', 'error');
			return;
		}
		if(formData.paymentType==1){
			var needFee = $("#needFee").html();
			var paymentFee = formData.paymentFee;
			if(paymentFee>needFee){
				$.messager.alert('提示', '申请金额不能大于剩余付款金额', 'error');
				return;
			}
		}
		var successInfo = '添加付款单成功';
		if(formData.id){
			successInfo = '修改付款单成功';
		}
		$.ajax({
			url:'../../payment/addPayment.do',
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
							window.location = "payment_list.html";	 
						}
					});
				}else{
					$.messager.alert('操作提示', result.msg, 'info');
				}
			}
		});
	}
}

function showSelectPurchase(){
	$('#dialog').dialog({
        title : "选择采购单",
        width : 800,
        height : 500,
        closed : false,
        cache : false,
        href : '../purchase/selectPurchase.html',
        modal : true
    });
	return;
}

function selectPurchaseCallback(data){
	console.info(data[0]);
	$("#purchaseId").val(data[0].id);
	$("#purchaseNo").textbox("setValue", data[0].purchaseNo);
	var payFee = 0;
	if(data[0].payFee){
		payFee = data[0].payFee;
	}
	if(data[0].purchaseFee){
		$("#purchaseFee").html(data[0].purchaseFee);
		var needFee = data[0].purchaseFee - payFee;
		$("#needFee").html(needFee);
	}
}
