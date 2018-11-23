/**
 * 获取HTML传过来的参数
 * 
 * @param keys
 * @returns
 */
var jsUtilTools = {
		
    getHtmlParams: function(keys) {
        var reg = new RegExp("(^|&)" + keys + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return (r[2]);
        return null;
    },

    doubleShow: function(value, precision) {
        if (value) {
        	var tmpValue = 0.01;
        	for(var i=0; i<precision; i++){
        		tmpValue = tmpValue*0.1;
        	}
        	value = value+tmpValue;
            return value.toFixed(precision);
        }
        return "";
    },

    dateTimeMillFormat: function(mill) {
        if (mill > 0) {
            return jsUtilTools.dateTimeFormat(new Date(mill));
        }
        return "";
    },

    dateTimeFormat: function(date) {
        if (!date) {
            date = new Date();
        }
        var fmt = "yyyy-MM-dd HH:mm:ss";
        return jsUtilTools.dateFormatStr(date, fmt);
    },

    dateFormat: function(date) {
        if (!date) {
            date = new Date();
        }
        var fmt = "yyyy-MM-dd";
        return jsUtilTools.dateFormatStr(date, fmt);
    },
    
    dateFormatStr: function(date, fmt) {
        var o = {
            "M+": date.getMonth() + 1,
            // 月份
            "d+": date.getDate(),
            // 日
            "H+": date.getHours(),
            // 小时
            "m+": date.getMinutes(),
            // 分
            "s+": date.getSeconds() // 秒
            // "q+": date.floor((this.getMonth() + 3) / 3), //季度
            // "S": date.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o) if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    },
    getTextById:function(id,datas){
    	if(!datas){
    		return "";
    	}
    	for(var i=0; i<datas.length; i++){
    		var obj = datas[i];
    		if(obj.id==id){
    			return obj.text;
    		}
    	}
    	return "";
    },
    
    setEasyUIFormValue: function(formId,obj){
		var $form = $("#" + formId);
		// 开始遍历
		 for ( var p in obj) {
			// 方法
			if (typeof (obj[p]) == "function") {
				continue;
			}
			$form.find("#" + p).each(function(i, tmpObj) {
				try {
					$obj = $(tmpObj);
					if ($obj.hasClass("easyui-textbox")) {
						$obj.textbox("setValue", obj[p]);
					} else if ($obj.hasClass("easyui-combobox")) {
						$obj.combobox("setValues", obj[p]);
					} else if ($obj.hasClass("easyui-datebox")) {
						$obj.datebox("setValue", obj[p]);
					} else if (tmpObj.tagName == "SPAN") {
						$obj.text(obj[p]);
					} else if (tmpObj.tagName == "INPUT") {
						$obj.val(obj[p]);
					}
				} catch (e) {
					return true;
				}
			});
		}
    },
    
    getEasyUIFormValue: function(formId,names){
    	var $form = $("#" + formId);
    	var params = {};
    	jQuery.each($form.serializeArray(),function(index, obj) {
    		  if(obj.value!=''){
    			  params[obj.name]=obj.value;
    		  }
    	});
      //地址选择器设置
      if (names != null){
          var nameArray = names.split(",");
          for(var n=0;n<nameArray.length;n++)
          {
              var name=nameArray[n];
              if(params[name]==null){
                  continue;
              }
              if(params[name]==-1){
            	  params[name]="";
              }else{
            	  params[name] = $form.find("input[comboname='" + name + "']:first").combobox('getText');
              }
//              debugger;
//              $obj = $form.find("input[comboname='" + name + "']:first");
//              var value = $form.find("input[comboname='" + name + "']:first").combobox('getValue');
//              if (text != null && text != "" && text != "全部" && text.indexOf("选择")<0) {
//            	  params[name] = text;
//              }
          }
      }
    	return params;
    },
    
    /*
     * 获取表单提交的参数值
     * 于智慧
     * 20161101
     * */
//    getQueryParams:($from,names){
//        var queryParams = {};
//        var sa = $from.serializeArray();
//        $.each(sa, function (i, field) {
//
//        	var value=field.value;
//        	if(value!=null&&value!=""){
//        		queryParams[field.name]=value;
//    		}
//        });
//        //地址选择器设置
//        if (names != null){
//            var nameArray = names.split(",");
//            for(var n=0;n<nameArray.length;n++)
//            {
//                var name=nameArray[n];
//                if(queryParams[name]==null){
//                    continue;
//                }
//                var text = $from.find("input[comboname='" + name + "']:first").combobox('getText');
//                if (text != null && text != "" && text != "全部" && text.indexOf("选择")<0) {
//                    queryParams[name] = text;
//                }
//            }
//        }
//
//        return queryParams;
//    }
    
    checkValueIsNullException : function(value, errorMsg){
    	if(null==value || ""==$.trim(value)){
    		$.messager.alert('操作提示', errorMsg, 'info');
    		throw '参数为空异常';
    	}
    },
    
    //添加回车事件
    addKenEntryEvent:function(parentId,callbackFun){
    	var inputs = $("#" +parentId).find("input");
    	for(var i=0;i<inputs.size();i++){
    		if($(inputs[i]).hasClass("easyui-textbox")){
        		$(inputs[i]).textbox('textbox').keydown(function (e){
      		        if(e.keyCode == 13)   
      		        {  
      		        	callbackFun();
      		        }  
      			});
    		}
    	}
    }
}