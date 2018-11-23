$(function() {
	initUI();
});

var initUI = function(){
	upload('#filePicker1');//主图一
	upload('#filePicker2');
	upload('#filePicker3');
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

function saveSupplier(){
	var validate = $("#addSupplierForm").form('validate');
	if(validate){
		var formData = $.getFormData($("#addSupplierForm"));
		for(var o in formData){
			if(!formData[o]){
				delete formData[o];
			}
		}
//		if($('#imagesList img').length != $('#imagesList li').length){
//			$.messager.alert('操作提示','请上传完整店铺照片', 'info');
//			return;
//		}
		formData.imgs = getImages("#imagesList");
		var successInfo = '添加供应商成功';
		if(formData.id){
			successInfo = '修改供应商成功';
		}
		$.ajax({
			url:'../../supplier/addSupplier.do',
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
							window.location = "supplier_list.html";	 
						}
					});
				}else{
					$.messager.alert('操作提示', result.msg, 'info');
				}
			}
		});
	}
}

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

$.extend($.fn.validatebox.defaults.rules, {    
    phoneNum: { //验证手机号   
        validator: function(value, param){ 
         return /^1[3-8]+\d{9}$/.test(value);
        },    
        message: '请输入正确的手机号码。'   
    },
    
    telNum:{ //既验证手机号，又验证座机号
      validator: function(value, param){ 
          return /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\d3)|(\d{3}\-))?(1[358]\d{9})$)/.test(value);
         },    
         message: '请输入正确的电话号码。' 
    }  
});

