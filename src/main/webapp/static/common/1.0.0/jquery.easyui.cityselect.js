//城市地址库控件
/*
于智慧
20160801
使用方法:
html:
<div id="cascadSelector">
    <select name="p" class="form-control"></select>
    <select name="c" class="form-control"></select>
    <select name="a" class="form-control"></select>
    <select name="t" class="form-control"></select>
    <select name="v" class="form-control"></select>
</div>
js:
var cascadSelector = $("#cascadSelector").cityAddress({
            url: 'http://172.16.1.102:8099/app/cityAddress/cascadSelector.do',
            pid: 0,
            nodata: "hidden",
            required: false
        });
//获取当前选择值
cascadSelector.selectedValue;
//设置当前级联列表模型值
//cascadSelector.setSelectedValue(19005);
*/
(function ($) {
    'use strict';

    $.fn.cityAddress = function (options) {
        options.container = this;
        return new $.cityAddress(options);
    };
    $.fn.cityAddress.defaults = {
        container: null,
        url: "",
        pid: 0,
        nodata: null,
        required: true,
        cache: true,
        valueField: 'key',
        textField: 'value'
    };

    $.cityAddress = function (options) {
        this.options = $.extend($.fn.cityAddress.defaults, options);
        this._init();
    };
    $.cityAddress.prototype = {
        _selectObjs: null,
        selectedValue: null,
        selectedObject: null,
        _init: function () {
            var $container = $(this.options.container);
            //获取select对象
            this._selectObjs = $container.find("input.easyui-combobox");
            if (this._selectObjs == null || this._selectObjs.length < 1) {
                return false;
            }
            //填充第一级
            //顶级地址库选择地址
            this._fillData(0, this.options.pid);
            var self = this;
            $.each(this._selectObjs, function (i, ele) {
                var $ele = $(ele);
                var options = {
                    //onChange: function (newValue, oldValue) {
                    //    self.selectChanged(ele, newValue, oldValue);
                    //}
                    onSelect: function () {
                        //var data = $(ele).combobox("getData");
                        //var newValue = $ele.combobox("getValue");
                        //var opts = $ele.combobox('options');
                        var newValue = $ele.combobox("getValue");
                        self.selectChanged(ele, newValue, null);
                    }
                };
                $ele.combobox(options);
            });
        },
        selectChanged: function (ele,newValue, oldValue){
            var index = this._selectIndexOf(ele);
            var lastIndex = index - 1;
            //当前选择的id
            this.selectedValue = newValue === "" ? this._selectedValue(lastIndex) : newValue;
            this.selectedObject = newValue === "" ? this._selectObjs[lastIndex < 0 ? 0 : lastIndex] : ele;
            //填充下一个select
            this._fillData(index + 1, newValue);
        },
        getSelectedObjectIndex:function(){
            return this._selectIndexOf(this.selectedObject);
        },
        _selectIndexOf:function(ele){
            if (ele == null) {
                return -1;
            }
            var index = -1;
            for (var n = 0; n < this._selectObjs.length; n++) {
                if (this._selectObjs[n] == ele) {
                    index = n;
                    break;
                }
            }
            return index;
        },
        _selectedValue: function (selectObjectIndex) {
            if (selectObjectIndex < 0) {
                //第一个选择了空值
                return this.options.pid;
            }
            var ele = this._selectObjs[selectObjectIndex];
            return $(ele).combobox('getValue');
        },
        _fillData: function (selectObjectIndex, pid) {
            if (selectObjectIndex == this._selectObjs.length) {
                //最后一个select选择变化
                return;
            }
            for (var n = selectObjectIndex; n < this._selectObjs.length; n++) {
                //清空
                this._clearSelect(this._selectObjs[n]);
            }
            if (pid === "") {
                return;
            }
            
            this._fillSelectByIndex(selectObjectIndex, pid);
        },
        _fillSelectByIndex: function (selectIndex, pid, _fillFinish) {
        	var self = this;
        	var select = this._selectObjs[selectIndex];
        	var rid=selectIndex>0?$(this._selectObjs[0]).combobox("getValue"):"";
            var url = this.options.url + "?level="+selectIndex+"&pid=" + pid+"&rid=" + rid
            this.getJSON(url, function (data) {
                self._addSelectItems(select, data, _fillFinish);
            });
        },
        _fillSelect: function (select, pid, _fillFinish) {
            var self = this;
            var url = this.options.url + "?pid=" + pid;
            this.getJSON(url, function (data) {
                self._addSelectItems(select, data, _fillFinish);
            });
        },
        _addSelectItems: function (select, data, _fillFinish) {
            var $sel = $(select);
            /*
            var all={};
            all[this.options.valueField] = "";
            all[this.options.textField]="全部";
            data.splice(0,0,all);
            */
            $sel.combobox({
                data: data,
                valueField: this.options.valueField,
                textField: this.options.textField,
                editable: true,
                //panelMaxHeight:300,
                //panelHeight: 'auto',
                onLoadSuccess: function () {
                    if (_fillFinish != null) {
                        _fillFinish(select);
                    }
                }
            });
            $sel.combobox('enable');
        },
        _setSelectValue: function (selectorIndex, value, _setFinish) {
            if (selectorIndex >= this._selectObjs.length) {
                return;
            }
            var self = this;
            var ele = this._selectObjs[selectorIndex];
            var data = $(ele).combobox("getData");

            var selValue = function (_select, _value) {
                if (_value != null) {
                    $(_select).combobox('setValue', _value);
                    //select,setValue
                    self.selectedValue = _value;
                    self.selectedObject = _select;
                }
                if (_setFinish != null) {
                    _setFinish(_select);
                }
            };
            if (data.length==null||data.length < 1) {
                //获取前一个选择值
                var pid = this._selectedValue(selectorIndex - 1);
                this._fillSelect(ele, pid, function (select) {
                    selValue(select, value);
                });
            }
            else {
                selValue(ele, value);
            }
        },
        _clearSelect:function(select){
            var $sel = $(select);
            $sel.combobox('loadData', {});
            $sel.combobox('clear');
            $sel.combobox('disable')
        },
        setSelectedValue: function (value,lastLevel,callBack) {
            var self = this;
            //清空
            for (var i = 0; i < self._selectObjs.length; i++) {
                self._clearSelect(self._selectObjs[i]);
            }

            //逐步选择，直到获取到此值为至
            var url = self.options.url + "?type=pids&id=" + value;
            self.getJSON(url, function (data) {
                var ids =data || [];
                if (!lastLevel) {
                    ids.push(value);
                }
                var n = 0;
                var cb = function () {
                    n++;
                    if (n<=ids.length) {
                        self._setSelectValue(n, n==ids.length?null:ids[n], cb);
                    }
                    else{
                        if(callBack!=null){
                            callBack();
                        }
                    }
                };
                self._setSelectValue(n, ids[n], function (select) {
                    cb();
                });
            });
        },
        getJSON: function (url, callBack) {
            if (!this.options.cache){
                url += "&r="+this.random(1111, 9999);
            }
            $.getJSON(url, function (result) {
                if (result != null && result.status == 200) {
                    callBack(result.data);
                }
            });
        },
        random: function (minNum, maxNum) {
            //返回指定范围的随机数(m-n之间)的公式 
            return parseInt(Math.random() * (maxNum - minNum) + minNum);
        }
    };
})(jQuery);