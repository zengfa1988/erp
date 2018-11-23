
/**
  * @copyBy 淘实惠平台脚本基类
  * @author chenwj@dtds.com.cn  
  * @date 2015-08-16
 */
var common = {
	//数据保存
	storage:{
		local:function(){
			
		}
	},
	//数组操作
    array:{
        	//多维数组递归函数
			doExchange:function(doubleArrays){
			    var len=doubleArrays.length;
			    if(len>=2){
				        	var len1=doubleArrays[0].length;
				        	var len2=doubleArrays[1].length;
					        var newlen=len1*len2;
					        var temp=new Array(newlen);
					        var index=0;
					        	for(var i=0;i<len1;i++){
					            for(var j=0;j<len2;j++){
					                temp[index]=doubleArrays[0][i]+"-"+doubleArrays[1][j];//数据字符添加
					                index++;
					            }
				        	}
			        var newArray=new Array(len-1);
			        for(var i=2;i<len;i++){
			            newArray[i-1]= doubleArrays[i];
			        }
			        newArray[0]=temp;
			        return this.doExchange(newArray);
			    }
			    else{
			        return doubleArrays[0];
			    }
			}
    },

	/*
	*js Unicode编码转换
	*/ 
	decToHex :function(str) {
	    var res=[];
	    for(var i=0;i < str.length;i++)
	        res[i]=("00"+str.charCodeAt(i).toString(16)).slice(-4);
	    return "\\u"+res.join("\\u");
	},
	hexToDec: function(str) {
	    str=str.replace(/\\/g,"%");
	    return unescape(str);
	},
	getSessionId:function() {
	    var c_name = 'JSESSIONID';
	    if (document.cookie.length > 0) {
	        c_start = document.cookie.indexOf(c_name + "=");
	        if (c_start != -1) {
	            c_start = c_start + c_name.length + 1;
	            c_end = document.cookie.indexOf(";", c_start);
	            if (c_end == -1)
	                c_end = document.cookie.length;
	            return unescape(document.cookie.substring(c_start, c_end));
	        }
	    }
	},
     //图片上传
     uploadImages:function(selector,url,view,swdUrl,id){
     	 var sessionId = this.getSessionId();
     
    $(selector).uploadify({
        'auto': true, // 是否自动上传   
        'multi': false, // 是否支持多文件上传    
        'buttonText':'上传图片', // 按钮上的文字  
        'fileSizeLimit':'2MB',
        'width':80,
        'height':25,
        'fileObjName':'fileUpload', // 传到后台的对象名    
        'fileTypeExts':'*.jpg;*.jpeg;*.gif;*.png;*.bmp',
        'fileTypeDesc':'请选择图片文件,格式png、gif、jpg、jpeg、bmp',
        'swf'      : swdUrl,
        'uploader': url+';jsessionid='+sessionId, // 上传到后台的处理类    
        
//        formData    附带值，需要通过get or post传递的额外数据，需要结合onUploadStart事件一起使用
//        'formData'      : {'someKey' : 'someValue', 'someOtherKey' : 1},
//        'onUploadStart' : function(file) {
//            $("#fileUpload").uploadify("settings", "someOtherKey", 2);
//        }
        
        'onUploadSuccess' : function(file, data, response) {
            data=JSON.parse(data);
            if(id){
            	$('#imgUrl'+id).attr("value",data.path);
                $('#imgName'+id).val(data.fileName);
                $(view).attr("src",data.path);
                $(view).attr("uploadPath",data.path);
                var index =$("#test").datagrid("getRowIndex",id);
                
            }else{
            	$('#imgUrl').attr("value",data.path);
                $('#imgName').val(data.fileName);
                $(view).attr("src",data.path);
                $(view).attr("uploadPath",data.path);
            }
        },
        'overrideEvents': ['onSelectError','onDialogClose'],  
        'onSelectError': function (file, errorCode, errorMsg) {  
              switch (errorCode) {  
                  case -100:  
                      $.messager.alert('操作提示', '上传的文件数量已经超出系统限制的'+$('#fileUpload').uploadify('settings', 'queueSizeLimit')+'个文件！', 'error');
                      break;  
                  case -110:  
                      $.messager.alert('操作提示', '文件大小超出系统限制的'+$('#fileUpload').uploadify('settings', 'fileSizeLimit')+'大小！', 'error');
                      break;  
                  case -120:  
                      $.messager.alert('操作提示', '文件大小异常！', 'error');
                      break;  
                  case -130:  
                      $.messager.alert('操作提示', '文件类型不正确！', 'error');
                      break;  
              }  
              return false;  
         }, 
         //检测FLASH失败调用   
         'onFallback': function () {  
//           $("#resultMessage").html('<span style="color:red">您未安装FLASH控件，无法上传！请安装FLASH控件后再试。<span>');
             $.messager.alert('操作提示', '您未安装FLASH控件，无法上传！请安装FLASH控件后再试。', 'error');
         } 
    
    });

	},
	//商品图片上传



	uploadGoodsImage:function(selector,bigview,smallview){
		var setHeader = function(object, data, headers) {
			headers['Access-Control-Allow-Origin'] = '*'; 
			headers['Access-Control-Request-Headers'] = 'content-type'; 
			headers['Access-Control-Request-Method'] = 'POST'; 
		} 
		var BASE_URL ="../../";
		 WebUploader.create({
		    auto: true,
		    swf: BASE_URL + 'js/plugin/webuploader-0.1.5/Uploader.swf',
		    server: BASE_URL + 'upload/goodsImg.do',
		    pick: {id:selector,multiple :false},
		    accept: {
		        title: 'Images',
		        extensions: 'gif,jpg,jpeg,bmp,png',
		        mimeTypes: 'image/*'
		    },
		    onUploadBeforeSend:setHeader,
			onUploadError :function( file, response) {
		    	//$error.text('上传失败');

		    	alert("上传失败！");
			},
			onUploadSuccess: function( file,response  ) {
			var ossImage = response.path;
			var ossImageS = common.getOssImgBySize(ossImage,100,100);
			var ossImageL = common.getOssImgBySize(ossImage,800,800);
			$(bigview).attr("src",ossImageL);
			$(smallview).attr("src",ossImageS);	
			$(smallview).attr("rel",ossImageL);
			$(smallview).attr("path",ossImage);	
			 this.removeFile( file );
			},
			onError: function( file,response  ) {

				if(file=="F_DUPLICATE"){
					alert("图片已上传了！");
				}else{
					alert("出错了！");
				}
				
			}

		});
	},
	
	

	//获取网站域名
        getDomain: function () {
            return window.location.protocol + '//' + window.location.host;
        },
        //获取原始URL
        getRawUrl: function () {
            return window.document.location.href;
        },
        //获取自定义json字符串 
        getJsonString: function (attr, datas) {
            return JSON.stringify(TSH_G.Util.getJson(attr, datas));
    },
    getResDomain:function(){
    	alert("a");
    	return "/";
    },
    //去空
    trim:function(str){
　　    return str.replace(/(^\s*)|(\s*$)/g,'');
　　 },
　　 ltrim:function(str){
　　    return str.replace(/(^\s*)/g,'');
　　 },
　　 rtrim:function(str){
　　    return str.replace(/(\s*$)/g,"");
　　 },
	//获取小图地址
        getImgUrl_s: function (url) {
            if (undefined == url) return "";
            var imgname, i_length, index;
            index = url.lastIndexOf('/');
            i_length = url.length - (url.length - url.lastIndexOf('.'));
            imgname = url.substring(index + 1, i_length);
            return url.replace(imgname, imgname + "_s");
        },
        //获取中图地址
        getImgUrl_m: function (url) {
            if (undefined == url) return "";
            var imgname, i_length, index;
            index = url.lastIndexOf('/');
            i_length = url.length - (url.length - url.lastIndexOf('.'));
            imgname = url.substring(index + 1, i_length);
            return url.replace(imgname, imgname + "_m");
        },
        //获取Oss图片地址
        getOssImgUrl: function (filename, param) {
            if (undefined == filename || filename == null || $.trim(filename).length == 0) {
                return App_Config.getResDomain() + "/Content/DefaultRes/Image/NoProduct.jpg";
            }
            if (undefined == param || param == null || $.trim(param).length == 0) {
                if (filename.indexOf("http://") != -1) {
                    return filename;
                }
                return App_Config.getOssDomain() + filename;
            }
            if (filename.indexOf("http://") != -1) {
                return filename + param;
            } else {
                return App_Config.getOssDomain() + filename + param;
            }
        },
         //获取Oss图片地址
        getOssImgBySize: function (url, width,height) {
        	var type =  url.substring(url.lastIndexOf("."));
        	
             return url+"@"+width+"w_"+height+"h";
        },
        //滚动类
        scroll:{
        	scrollTop:function(selector){
        		var top = $(selector).offset().top;
        		$("html,body").scrollTop(top);
        	}
        },
        //替换
        replace:{
        	//金额替换
        	price:function(str){
        		return str.replace(/[^\d.]/g,'');
        	},
        	//数量替换
        	num:function(str){
        		return str.replace(/[^\d]/g,'');
        	}
        	
        },
        //表单验证
        form:{
        	validate:
        	{
        		required:function(selector){
	        		var result = true;
					$(selector).each(function(i,n){
						var _this = $(n);
						var error = '<span class="text-danger"> * 此项必填</span>';
						if(_this.val()==""){
							result = false;
							_this.focus();
							if(!_this.next().hasClass('error')){
								_this.after('<label class="inline error"></label>');
							}
							
							_this.next('.error').html(error);
						}else{
							_this.next('.error').remove();
						}
					})
					return result;
	        	},
	        	validate:function(selector){
	        		var result = true;
	        		var focus = 0;
					 $(selector).each(function(i,n){
					 	var _this = $(n);
					 	var v = _this.attr("validate");
					 	var val = _this.val();
					 	var msg = _this.attr("errormessage") || "输入格式错误";
					 	var error = '<span class="text-danger"> * '+msg+'</span>';
					 	if(!validateType[v](val)){

					 		if(!_this.next().hasClass('error')){
										_this.after('<label class="inline error"></label>');
									}
							result = false;
							if(focus==0){
								_this.focus();
								focus++;
							}
					 		_this.next('.error').html(error);
					 		
					 	}else{
					 		_this.next('.error').remove();
					 	}
					 	
					});
					return result;
	        	}

        	},
        	formToJson:function(selector){
        		var array = [];
        		$(selector).each(function(i,o){
        			var json ={};
        			var _this = $(o);
        			json.id =  _this.attr("attrId");
					json.attrType = _this.attr("attrType");
					//json.propertyName = _this.attr("propertyName");
        			json.propertyValueVo =[];
        			switch (json.attrType) {
	                            //文本输入框
	                            case "text":
	                            	json.propertyValueVo = {"valueName" : _this.find(":input").val()};
	                                break;
	                                //文本输入框
	                            case "textarea":
	                              	json.propertyValueVo = {"valueName" : _this.find("textarea").val()};
	                                break;
	                                //下拉选择框
	                            case "select":
	                            	json.propertyValueVo = {"valueName" : _this.find("select").val()};
		                          	
	                                break;
	                                //复选框
	                            case "checkbox":
	                              	_this.find(":checked").each(function(j,n){
	                              		json.propertyValueVo.push($(n).val());
	                              	})
	                                //单选框
	                            case "radio":
	                                break;
	                            default:
	                            	console.log($(o).attr("attrType"),"出错了");
	                            	//console.log("未设置的类型");
	                                break;
	                        }
	                 array.push(json);
        		});
				return array;
        	},
        	//json转html
        	 bindJson:function(o){
        		var html = "";
		   		switch (o.attrType) {
	                            //文本输入框
	                            case "text":
	                                html = '<div class="form-group" attrType="text" attrType="text" attrId="'+o.id+'" propertyName="'+o.propertyName+'" ><input id="'+o.id+'" name="'+o.id+'"  type="text" class="form-control ';
	                                if(o.required){
	                                	html+='required"  >';
	                                }else{
	                                	 html +='">';
	                                }
	                                html +='</div>';
	                                break;
	                                //文本输入框
	                            case "textarea":
	                                html = '<div class="form-group" attrType="textarea" attrId="'+o.id+'" propertyName="'+o.propertyName+'"><textarea  style="width:100%; height:100px;" id='+o.id+'" name="'+o.id+'"  class="form-control ';
	                                if(o.required){
	                                	html+='required"  >';
	                                }else{
	                                	 html +='">';
	                                }
	                                html +='</textarea></div>';
	                                break;
	                                break;
	                                //下拉选择框
	                            case "select":
		                            html = '<div class="form-group" attrType="select" attrId="'+o.id+'" propertyName="'+o.propertyName+'"><select id="'+o.id+'" name="'+o.id+'" class="form-control ';
	                                if(o.required){
	                                	html+='required"  >';
	                                }else{
	                                	 html +='">';
	                                }
	                               
	                                for(var i=0;i<o.propertyValueVo.length;i++){
	                                	html +='<option  value="'+o.propertyValueVo[i].valueId+'" >'+o.propertyValueVo[i].valueName+'</option>';
	                                }
	                                html +='</select></div>';
	                                break;
	                                //复选框
	                            case "checkbox":
	                              	html +='<div class="form-group" attrType="checkbox" attrId="'+o.id+'"  id="'+o.id+'" propertyName="'+o.propertyName+'">';
									for(var i = 0;i<o.propertyValueVo.length;i++){
										html +='<label class="checkbox inline">'+
						              			'<input type="checkbox"  id="'+o.propertyValueVo[i].valueId+'" name="'+o.propertyValueVo[i].valueId+'" value="'+o.propertyValueVo[i].valueId+'">'+o.propertyValueVo[i].valueName+
						            			'</label>';
									}
									html +='</div>'
	                                //单选框
	                            case "radio":
	                                
	                                break;
	                            default:
	                            
	                                break;
	                        }
		   		return html;
       		 }
        },
        
        //工具类
        util:{

        }
};

