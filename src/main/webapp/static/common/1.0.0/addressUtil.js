/**
 * 
 */


addressUtil = {
	//初始化地址控件
	/***
	 * provinceId comboBox组件ID-省ID
	 * cityId comboBox组件ID-市ID
	 * countyId comboBox组件ID-县ID
	 * townId comboBox组件ID-镇/街道ID，为空时可传null
	 * values 值对象，可不传，地址信息如：values.provinceId=1 values.cityId=2 values.countyId=3
	 * 
	 * 例如省市区：
	 *	var addressObj={};
	 *  addressObj.provinceId=1;
	 *  addressObj.cityId=2;
	 *  addressObj.countyId=3;
	 *  addressUtil.initAddress("provinceId", "cityId", "countyId", null, addressObj);
	 */
    initAddress: function(provinceId, cityId, countyId, townId, addressObj) {
		 addressUtil.areaURL = "/share/getAddress.do?cid=";
		 addressUtil.initProvince(provinceId,cityId,countyId,townId);
    	 if(addressObj){
    		 $("#"+provinceId).combobox("setValue", addressObj.provinceId);
    		 $("#"+cityId).combobox("setValue", addressObj.cityId);
    		 if(addressObj.countyId){
    			 $("#"+countyId).combobox("setValue", addressObj.countyId);
    		 }
    	 }

    	
    },
    
    initProvince: function(param_provinceId,param_cityId,param_countyId,param_townId) {
        var provinceList = new Array();
        var url = addressUtil.areaURL + 0;
        /* 异步请求加载数据 */
        $.ajax({
            type: 'get',
//            dataType: "jsonp",
//            jsonp: "callback",
            url: url,
            cache: false,
            async: false,
            success: function(data) {
                var jsonData = data;
                $(jsonData).each(function(i) {
                    var provincial = {};
                    provincial.id = this.id;
                    provincial.text = this.name;
                    provinceList[i + 1] = provincial;
                });
            },
            error: function() {
                provinceList = new Array();
            },
            complete: function(data) {
                var provincial = {};
                provincial.id = "-1";
                provincial.text = "选择省";
                provincial.selected = true;
                provinceList[0] = provincial;
                addressUtil.setProvinceInfo(provinceList, 1,param_provinceId, param_cityId,param_countyId,param_townId);
                addressUtil.initCity(null, 0, param_cityId,param_countyId,param_townId);
            }
        });
    },

    setProvinceInfo: function(infoList, indexOf, param_provinceId,param_cityId,param_countyId,param_townId) {
    	var provinceObj = $('#' + param_provinceId);
        $(provinceObj).combobox({
            data: infoList,
            valueField: 'id',
            textField: 'text',
            onChange: function(n, o) {
                var provincialIDValue = $(provinceObj).combobox('getValue');
                addressUtil.initCity(provincialIDValue, 1,param_cityId,param_countyId,param_townId);
            },
            editable: false,
            panelHeight: '200px'
        });
    },

    initCity: function(values, flag, param_cityId, param_countyId, param_townId) {
    	var cityObj = $('#' + param_cityId);
        var cityList = new Array();
        var city = {};
        city.id = "-1";
        city.text = "选择市";
        city.selected = true;
        cityList[0] = city;
        if (values != -1 && values != null && flag == 1) {
            var url = addressUtil.areaURL + values;
            /* 异步请求加载数据 */
            $.ajax({
                type: 'get',
//                dataType: "jsonp",
                //jsonp: "callback",
                url: url,
                cache: false,
                async: false,
                success: function(data) {
                    var jsonData = data;
                    $(jsonData).each(function(i) {
                        var city = {};
                        city.id = this.id;
                        city.text = this.name;
                        cityList[i + 1] = city;
                    });
                },
                error: function() {},
                complete: function(data) {
                    $(cityObj).combobox({
                        data: cityList,
                        valueField: 'id',
                        textField: 'text',
                        editable: false,
                        onChange: function(n, o) {
                            var cityIDValue = $(cityObj).combobox('getValue');
                            addressUtil.initCounty(cityIDValue, 1,param_countyId, param_townId);
                        },
                        panelHeight: '200px'
                    });

                }
            });
        } else {
            $(cityObj).combobox({
                data: cityList,
                valueField: 'id',
                textField: 'text',
                editable: false,
                panelHeight: '200px'
            });
        }
        addressUtil.initCounty(null, 0,param_countyId, param_townId);
    },

    initCounty:function(values, flag,param_countyId, param_townId) {
    	if(!param_countyId){
    		return;
    	}
    	var countieObj = $('#' + param_countyId);
    	if(!countieObj){
    		return;
    	}
        var countieList = new Array();
        var countie = {};
        countie.id = "-1";
        countie.text = "选择县";
        countie.selected = true;
        countieList[0] = countie;
        if (values != -1 && values != null && flag == 1) {
            var url = addressUtil.areaURL + values;
            /* 异步请求加载数据 */
            $.ajax({
                type: 'get',
//                dataType: "jsonp",
//                jsonp: "callback",
                url: url,
                cache: false,
                async: false,
                success: function(data) {
                    var jsonData = data;
                    $(jsonData).each(function(i) {
                        var countie = {};
                        countie.id = this.id;
                        countie.text = this.name;
                        countieList[i + 1] = countie;
                    });
                },
                error: function() {},
                complete: function(data) {
                    $(countieObj).combobox({
                        data: countieList,
                        valueField: 'id',
                        textField: 'text',
                        editable: false,
                        onChange: function(n, o) {
                            var countyIDValue = $(countieObj).combobox('getValue');
                            
                            addressUtil.initTown(countyIDValue, 1, param_townId);
                        },
                        panelHeight: '200px'
                    });
                }
            });
        } else {
            $(countieObj).combobox({
                data: countieList,
                valueField: 'id',
                textField: 'text',
                editable: false,
                panelHeight: '200px'
            });
        }
        addressUtil.initTown(null, 0, param_townId);
    },
    
    initTown:function(values, flag, param_townId) {
    	if(!param_townId){
    		return;
    	}
    	var townObj = $('#' + param_townId);
    	if(!townObj){
    		return;
    	}
        var townList = new Array();
        var town = {};
        town.id = "-1";
        town.text = "镇/街道";
        town.selected = true;
        townList[0] = town;
        if (values != -1 && values != null && flag == 1) {
            var url = addressUtil.areaURL + values;
            /* 异步请求加载数据 */
            $.ajax({
                type: 'get',
//                dataType: "jsonp",
//                jsonp: "callback",
                url: url,
                cache: false,
                async: false,
                success: function(data) {
                    var jsonData = data;
                    $(jsonData).each(function(i) {
                        var town = {};
                        town.id = this.id;
                        town.text = this.name;
                        townList[i + 1] = town;
                    });
                },
                error: function() {},
                complete: function(data) {
                    $(townObj).combobox({
                        data: townList,
                        valueField: 'id',
                        textField: 'text',
                        editable: false,
                        panelHeight: '200px'
                    });
                }
            });
        } else {
            $(townObj).combobox({
                data: townList,
                valueField: 'id',
                textField: 'text',
                editable: false,
                panelHeight: '200px'
            });
        }
    }
}