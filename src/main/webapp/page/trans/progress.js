
$(function() {
	loadTransProgress();
});
function loadTransProgress(){
	var id = sessionStorage.getItem("transId");
	$("#transId").val(id);
	$.ajax({
		url : '../../trans/getProgress.do',
		data : {
			id : id
		},
		dataType : 'json',
		type : 'get',
		success : function(result) {
			if (result.status == 200 && result.data != null) {
				$.each(result.data, function ( index, o ) {
					console.info(o);
					var progressTable = document.getElementById("progressTable");
					var tableRow = progressTable.insertRow(index+1);
					var timeCell = tableRow.insertCell(0);
					var contentCell = tableRow.insertCell(1);
					timeCell.innerHTML=jsUtilTools.dateTimeFormat(new Date(o.progressTime));
					contentCell.innerHTML=o.content;
				});
			} else {
//				$.messager.alert('错误提示', '<div class="content">加载数据失败！</div>',
//						'error');
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

function save () {
    var $formData = $.getFormData($("#addForm"));
    for ( var d in $formData) {// 值不存在则删除
		if (!$formData[d]) {
		    delete $formData[d];
		}
    }
    if(!$formData.progressTime){
    	$.messager.alert('错误提示', '<div class="content">时间不能为空</div>', 'error');
		return;
    }
    if(!$formData.content){
    	$.messager.alert('错误提示', '<div class="content">进度内容不能为空</div>', 'error');
		return;
    }
    // 保存主题
    $.ajax({
	url : '../../trans/addProgress.do',
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
			closePick();
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


function closePick() {
	$('#dialog').dialog('close');
}