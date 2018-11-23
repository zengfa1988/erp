comboboxUtil = {
	getTextByCode : function(code, datas) {
		if(code==null){
			return "";
		}
		for (var i = 0; i < datas.length; i++) {
			var obj = datas[i];
			if (obj.code == code) {
				return obj.text;
			}
		}
		return code;
	},

	/* easy ui 下拉列表设置值 */
	setData : function($comboObj, valueList) {
		if (!$comboObj) {
			return;
		}
		$comboObj.combobox({
			data : valueList,
			valueField : 'code',
			textField : 'text',
			editable : false,
			panelHeight : 'auto'
		})
	},

	certificateTypeList : [ {
		"code" : "01",
		"text" : "身份证",
		"selected" : true
	}, {
		"code" : "02",
		"text" : "军官证"
	}, {
		"code" : "03",
		"text" : "中国护照"
	}, {
		"code" : "04",
		"text" : "港澳台居民通行证"
	}, {
		"code" : "05",
		"text" : "暂住证"
	}, {
		"code" : "06",
		"text" : "警官证"
	}, {
		"code" : "07",
		"text" : "外国护照"
	} ],
	certificateTypeText : function(code) {
		return comboboxUtil.getTextByCode(code,
				comboboxUtil.certificateTypeList);
	},
	certificateTypeCombobox : function($comboObj) {
		return comboboxUtil.setData($comboObj,
				comboboxUtil.certificateTypeList);
	},
	
	// 商户状态
	merchantStatusList : [{
		"code" : "",
		"text" : "选择状态",
		"selected" : true
	}, {
		"code" : "2",
		"text" : "正常",
		"selected" : true
	}, {
		"code" : "1",
		"text" : "待审核"
	}, {
		"code" : "3",
		"text" : "审核失败"
	} ],
	merchantStatusText : function(code) {
		return comboboxUtil.getTextByCode(code,
				comboboxUtil.merchantStatusList);
	},
	merchantStatusCombobox : function($comboObj) {
		return comboboxUtil.setData($comboObj,
				comboboxUtil.merchantStatusList);
	},
	
	// 商户审核状态
	merchantAuditStatusList : [{
		"code" : "",
		"text" : "选择状态",
		"selected" : true
	}, {
		"code" : "1",
		"text" : "正常"
	}, {
		"code" : "2",
		"text" : "微众审核未通过"
	}, {
		"code" : "3",
		"text" : "待提交到微信"
/*	}, {
		"code" : "4",
		"text" : "微信审核中"*/
	}, {
		"code" : "5",
		"text" : "微信审核未通过"
	}, {
		"code" : "6",
		"text" : "待用户确认"
	}, {
		"code" : "7",
		"text" : "钱包待激活"
	}, {
/*		"code" : "8",
		"text" : "待提交到微众"
	}, {*/
		"code" : "9",
		"text" : "支付宝入驻失败"
	}, {
		"code" : "10",
		"text" : "微信入驻失败"
	}, {
		"code" : "11",
		"text" : "商户入驻失败"
	}, {
		"code" : "12",
		"text" : "钱包开通失败"
	}, {
		"code" : "13",
		"text" : "钱包已激活"
	} ],
	merchantAuditStatusText : function(code) {
		return comboboxUtil.getTextByCode(code,
				comboboxUtil.merchantAuditStatusList);
	},
	merchantAuditStatusCombobox : function($comboObj) {
		return comboboxUtil.setData($comboObj,
				comboboxUtil.merchantAuditStatusList);
	}

}