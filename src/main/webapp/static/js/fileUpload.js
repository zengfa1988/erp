var fileUpLoad = function(selector,url,callbackfun){
	 var multiple = true;
        var maxLength = 30;
       
        var setHeader = function(object, data, headers) {
            if (document.all) {
                headers['Access-Control-Allow-Origin'] = '*';
                headers['Access-Control-Request-Headers'] = 'content-type';
                headers['Access-Control-Request-Method'] = 'POST';
            }
        }
        WebUploader.create({
            auto: true,
            duplicate:true, // 图片可以重复
            compress:null,
            swf: '/static/js/plugin/webuploader-0.1.5/Uploader.swf',
            server: url,
            pick: {
                id: selector,
                multiple: multiple
            },
            accept: {
            	title : 'Applications',
            	extensions : 'xls,xlsx',
            	mimeTypes : 'application/xls,application/xlsx'
            },
            // fileNumLimit : 0,
            // fileSingleSizeLimit: 2097152,
            fileSingleSizeLimit:5242880,
            onUploadBeforeSend: setHeader,
            onFilesQueued:function(files){

              if(files.length>maxLength){
                $.messager.alert({
                    title: '图片上传提示',
                    msg: '<div class="content">最多同时上传'+maxLength+'张图</div>',
                    ok: '<i class="i-ok"></i> 确定',
                    icon: 'warning'
                });
                this.reset();

              }
            },
            onUploadError: function(file, reason) {
                // $error.text('上传失败');
                if (reason == "http") {
                    $.messager.alert({
                        title: '图片上传提示',
                        msg: '<div class="content">网络异常!</div>',
                        ok: '<i class="i-ok"></i> 确定',
                        icon: 'warning'
                    });
                } else {
                    $.messager.alert({
                        title: '图片上传提示',
                        msg: '<div class="content">不支持的图片,换张图片试试!</div>',
                        ok: '<i class="i-ok"></i> 确定',
                        icon: 'warning'
                    });
                }
                this.removeFile(file);
            },
            onUploadProgress: function(file, percentage) {},
            onUploadSuccess: function(file, response) {
            	var msg = "处理失败!";
            	if(response.status == 200){
            		msg = "处理成功!";
            	}else{
            		msg = response.msg;
            	}
                $.messager.alert({
                            title: '操作提示',
                            msg: '<div class="content">'+msg+'</div>',
                            ok: '<i class="i-ok"></i> 确定',
                            icon: 'warning'
                        });
                if(callbackfun){
                	callbackfun();
                }
            },
            onError: function(file, response) {
                switch (file) {
                    case "Q_EXCEED_NUM_LIMIT":
                        $.messager.alert({
                            title: '图片上传提示',
                            msg: '<div class="content">最多同时上30张图片</div>',
                            ok: '<i class="i-ok"></i> 确定',
                            icon: 'warning'
                        });
                        break;
                    case "F_DUPLICATE":
                        $.messager.alert({
                            title: '图片上传提示',
                            msg: '<div class="content">文件重复!</div>',
                            ok: '<i class="i-ok"></i> 确定',
                            icon: 'warning'
                        });
                        break;
                    case "F_EXCEED_SIZE":
                        $.messager.alert({
                            title: '图片上传提示',
                            msg: '<div class="content">图片大小不超5M!</div>',
                            ok: '<i class="i-ok"></i> 确定',
                            icon: 'warning'
                        });

                        break;
                    case "Q_TYPE_DENIED":
                        $.messager.alert({
                            title: '图片上传提示',
                            msg: '<div class="content">请上传jpg,jpeg,bmp,png格式图片!</div>',
                            ok: '<i class="i-ok"></i> 确定',
                            icon: 'warning'
                        });
                        break;
                    default:
                        $.messager.alert({
                            title: '图片上传提示',
                            msg: '<div class="content">上传失败!</div>',
                            ok: '<i class="i-ok"></i> 确定',
                            icon: 'warning'
                        });
                        break;
                }
            }

        });
}