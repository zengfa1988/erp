$(function() {
	initLoadSku();
});

var initUI = function(){
	upload('#filePicker1');//主图一
	upload('#filePicker2');
	upload('#filePicker3');
}

function initLoadSku() {
	var id = acceptParam("id");
	$.ajax({
		url : '../../goods/sku/getSku.do',
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
				setImages(o.pics);
				initSupplier(o.supplierId);
				initCategory(o.categoryId);
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

function initSupplier(value){
	$('#supplierId').combobox({    
	    url:'../../supplier/getSupplierSelect.do',   
	    method:'get',
	    valueField:'id',    
	    textField:'supplierName',
	    value:value
	}); 
}

function initCategory(value){
	$('#categoryId').combobox({    
	    url:'../../goods/category/getCategorySelect.do',   
	    method:'get',
	    valueField:'id',    
	    textField:'categoryName',
	    value:value
	}); 
}

function saveSku(){
	var validate = $("#addForm").form('validate');
	if(validate){
		var formData = $.getFormData($("#addForm"));
		for(var o in formData){
			if(!formData[o]){
				delete formData[o];
			}
		}
		formData.imgs = getImages("#imagesList");
		var successInfo = '添加供应商成功';
		if(formData.id){
			successInfo = '修改供应商成功';
		}
		$.ajax({
			url:'../../goods/sku/addSku.do',
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
							window.location = "sku_list.html";	 
						}
					});
				}else{
					$.messager.alert('操作提示', result.msg, 'info');
				}
			}
		});
	}
}

function setImages(imgs){
	if(!imgs){
		return;
	}
	var imgArray = imgs.split(",");
	$.each(imgArray,function(i,img){
		$("#filePicker"+(i+1)).parents('div.imagesList').find('img').remove();
		$("#filePicker"+(i+1))
				.after('<img class="smallview" src="' + img + '" >');
	});
}

function upload(selector) {
	var multiple = false;
	var maxLength = 1;
	var setHeader = function(object, data, headers) {

		if (document.all) {
			headers['Access-Control-Allow-Origin'] = '*';
			headers['Access-Control-Request-Headers'] = 'content-type';
			headers['Access-Control-Request-Method'] = 'POST';
		}
	}
	var BASE_URL = "../../";
	WebUploader.create({
		auto : true,
		duplicate : true, // 图片可以重复
		compress : null,
		swf : BASE_URL + 'static/js/plugin/webuploader-0.1.5/Uploader.swf',
		server : BASE_URL + 'upload/uploadImg.do',
		threads : 1,
		pick : {
			id : selector,
			multiple : multiple
		},
		accept : {
			title : 'Images',
			extensions : 'jpg,png',
			mimeTypes : '.jpg,.png'
		},
		fileSingleSizeLimit : 8388608,
		onUploadBeforeSend : setHeader,
		onFilesQueued : function(files) {

			if (files.length > maxLength) {
				$.messager.alert({
					title : '图片上传提示',
					msg : '<div class="content">最多同时上传' + maxLength
							+ '张图</div>',
					ok : '<i class="i-ok"></i> 确定',
					icon : 'warning'
				});
				this.reset();

			}
		},
		onUploadError : function(file, reason) {
			if (reason == "http") {
				$.messager.alert({
					title : '图片上传提示',
					msg : '<div class="content">网络异常!</div>',
					ok : '<i class="i-ok"></i> 确定',
					icon : 'warning'
				});
			} else {
				$.messager.alert({
					title : '图片上传提示',
					msg : '<div class="content">不支持的图片,换张图片试试!</div>',
					ok : '<i class="i-ok"></i> 确定',
					icon : 'warning'
				});
			}
			this.removeFile(file);
		},
		onUploadProgress : function(file, percentage) {
		},
		onUploadSuccess : function(file, response) {
			var ossImage = response.path;
			$(selector).parents('div.imagesList').find('img').remove();
			$(selector)
					.after('<img class="smallview" src="' + ossImage + '" >');
			this.removeFile(file);
		},
		onError : function(file, response) {
			switch (file) {
			case "Q_EXCEED_NUM_LIMIT":
				$.messager.alert({
					title : '图片上传提示',
					msg : '<div class="content">最多同时上30张图片</div>',
					ok : '<i class="i-ok"></i> 确定',
					icon : 'warning'
				});
				break;
			case "F_DUPLICATE":
				$.messager.alert({
					title : '图片上传提示',
					msg : '<div class="content">文件重复!</div>',
					ok : '<i class="i-ok"></i> 确定',
					icon : 'warning'
				});
				break;
			case "F_EXCEED_SIZE":
				$.messager.alert({
					title : '图片上传提示',
					msg : '<div class="content">图片大小不超8M!</div>',
					ok : '<i class="i-ok"></i> 确定',
					icon : 'warning'
				});

				break;
			case "Q_TYPE_DENIED":
				$.messager.alert({
					title : '图片上传提示',
					msg : '<div class="content">请上传jpg,png格式图片!</div>',
					ok : '<i class="i-ok"></i> 确定',
					icon : 'warning'
				});
				break;
			default:
				$.messager.alert({
					title : '图片上传提示',
					msg : '<div class="content">上传失败!</div>',
					ok : '<i class="i-ok"></i> 确定',
					icon : 'warning'
				});
				break;
			}
		}

	});
};

function getImages(selector) {
    var images = [];
    $(selector + " img").each(function(i, n) {
        var path = $(this).attr("src");
        if (path) {
        	var dataModel = {};
        	dataModel.imgUrl = path;
        	dataModel.imgTypeName = $(this).parents('li').find('.imgText').text();
        	dataModel.imgType = $(this).parents('li').find('.imgText').attr('tag');
        	
            images.push(dataModel);
        }
    });
    return JSON.stringify(images);
}
