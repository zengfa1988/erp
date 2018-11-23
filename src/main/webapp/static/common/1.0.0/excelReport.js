

var setDecimal="#.00";
function mapfuntion(key,value){
	this.key=key;
	this.value=value;
}
function showAlias(key,value,aliasValue,setformdata){
	
	this.key=key;
	this.value=value;
	this.aliasValue=aliasValue;//
	this.setformdata=setformdata;//
}

function initReportWord(arraylist,columnName,columnText,decimalNumber){
  var wordArray=new Array();
  wordArray.push(new showAlias(columnName,columnName,"",decimalNumber));
  arraylist.push(new mapfuntion(columnText,wordArray));
}


function commonColumn(arraylist){
	 //下单时间
		var createtimesk=new Array();
		createtimesk.push(new showAlias("createTime","createTime","","yyyy-MM-dd HH:mm:ss"));
		arraylist.push(new mapfuntion("下单时间",createtimesk));
		//货物状态
		var cargo_status=new Array();
		cargo_status.push(new showAlias("cargoStatus","1","未发货"));
		cargo_status.push(new showAlias("cargoStatus","2","已发货"));
		cargo_status.push(new showAlias("cargoStatus","3","已收货"));
		cargo_status.push(new showAlias("cargoStatus","4","货物退回"));
		cargo_status.push(new showAlias("cargoStatus","5","退回完成"));
		arraylist.push(new mapfuntion("货物状态",cargo_status));
		
		statusDummy(arraylist);
}

function statusDummy(arraylist){
		//订单状态
		var status=new Array();
		status.push(new showAlias("status","1","待付款"));
		status.push(new showAlias("status","2","支付中"));
		status.push(new showAlias("status","3","待发货"));
		status.push(new showAlias("status","4","部分发货"));
		status.push(new showAlias("status","5","待收货"));
		status.push(new showAlias("status","6","交易完成"));
		status.push(new showAlias("status","7","交易关闭"));
		arraylist.push(new mapfuntion("订单状态",status));
	
		//售后状态
	   var afterSaleStatus=new Array();
	   afterSaleStatus.push(new showAlias("afterStatus","1","待确认"))
	   afterSaleStatus.push(new showAlias("afterStatus","2","已拒绝"))
	   afterSaleStatus.push(new showAlias("afterStatus","3","待退货"))
	   afterSaleStatus.push(new showAlias("afterStatus","4","待收货"))
	   afterSaleStatus.push(new showAlias("afterStatus","5","买家客服介入"))
	   afterSaleStatus.push(new showAlias("afterStatus","6","卖家客服介入"))
	   afterSaleStatus.push(new showAlias("afterStatus","7","退款关闭"))
	   afterSaleStatus.push(new showAlias("afterStatus","8","退款完成"))
	   afterSaleStatus.push(new showAlias("afterStatus","9","退款支付中"))
	   arraylist.push(new mapfuntion("售后状态",afterSaleStatus));
}

//网点类型
function shopTypeText(arraylist){
	var status=new Array();
	status.push(new showAlias("shopType","0","加盟网点服务站"));
	status.push(new showAlias("shopType","1","聚百优APP网点"));
	arraylist.push(new mapfuntion("网点类型",status));
}

//订单来源
function orderSourceText(arraylist){
	var status=new Array();
	status.push(new showAlias("orderSource","0",""));
	status.push(new showAlias("orderSource","1","手机APP"));
	status.push(new showAlias("orderSource","2","微信商城"));
	status.push(new showAlias("orderSource","3","屏端APP"));
	arraylist.push(new mapfuntion("订单来源",status));
}


//四舍五入保留两位小数

function decimal(num,v){
	var newNum=Math.pow(10,v);
	return Math.round(num*newNum)/newNum;
}




//orderType:1全国零售；2县域批发；3县域零售；4代销；5购销；6虚拟订单；7小店订单,8.全国零售代销
//roleType:1.全国平台角色  2.县域角色 3.供应商角色 4.代销商角色
function getMap(orderType,roleType){  
	//全国平台角色
	//全国零售、全国平台角色
	if(roleType==1&&orderType==1){
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//供应商优惠金额
		initReportWord(arraylist,ExcelReport.SupplierCouponMoney,ExcelReport.SupplierCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		
		commonColumn(arraylist);
		//供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		//所属县域
		initReportWord(arraylist,ExcelReport.SellerAreaName,ExcelReport.SellerAreaNameText);
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		//服务费比例
		initReportWord(arraylist,ExcelReport.PlatcostRatio,ExcelReport.PlatcostRatioText,setDecimal);
		//供应商系数
		initReportWord(arraylist,ExcelReport.SellerCoefficient,ExcelReport.SellerCoefficientText);
		//服务费平台分成比例
		initReportWord(arraylist,ExcelReport.CountryPlatcostRatio,ExcelReport.CountryPlatcostRatioText,setDecimal);
		//服务费平台分成金额
		initReportWord(arraylist,ExcelReport.PlatformTidianMoney,ExcelReport.PlatformTidianMoneyText,setDecimal);
		//服务费供货县域分成比例
		initReportWord(arraylist,ExcelReport.SupplyAreaPlatcostRatio,ExcelReport.SupplyAreaPlatcostRatioText,setDecimal);
		//服务费供货县域分成金额
		initReportWord(arraylist,ExcelReport.SoureAreaTidianMoney,ExcelReport.SoureAreaTidianMoneyText,setDecimal);
		//服务费销售县域分成比例
		initReportWord(arraylist,ExcelReport.AreaPlatcostRatio,ExcelReport.AreaPlatcostRatioText,setDecimal);
		//服务费销售县域分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleAreaTidianMoney,ExcelReport.SaleAreaTidianMoneyText,setDecimal);
		//佣金比例
		initReportWord(arraylist,ExcelReport.CommissionRatio,ExcelReport.CommissionRatioText,setDecimal);
		
		//佣金网点分成比例
		initReportWord(arraylist,ExcelReport.ShopCommissionRatio,ExcelReport.ShopCommissionRatioText,setDecimal);
		//佣金网点分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleStoreCommissionMoney,ExcelReport.SaleStoreCommissionMoneyText,setDecimal);
		//佣金县域分成比例
		initReportWord(arraylist,ExcelReport.AreaCommissionRatio,ExcelReport.AreaCommissionRatioText,setDecimal);
		//佣金县域分成金额
		initReportWord(arraylist,ExcelReport.SaleAreaCommissionMoney,ExcelReport.SaleAreaCommissionMoneyText,setDecimal);
		
		return arraylist;
		
	}else if(roleType==1&&orderType==2){//县域批发
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		initReportWord(arraylist,ExcelReport.SellerAreaName,ExcelReport.SellerAreaNameText);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		
		
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//供应商优惠金额
		initReportWord(arraylist,ExcelReport.SupplierCouponMoney,ExcelReport.SupplierCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		//下单时间
		//initReportWord(arraylist,ExcelReport.CreateTime,ExcelReport.CreateTimeText);
		//
		commonColumn(arraylist);
		//供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		//所属县域
		
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		
		return arraylist;
		
	}else if(roleType==1&&orderType==3){//县域零售
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//供应商优惠金额
		initReportWord(arraylist,ExcelReport.SupplierCouponMoney,ExcelReport.SupplierCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);

		commonColumn(arraylist);
		//供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		//所属县域
		initReportWord(arraylist,ExcelReport.SellerAreaName,ExcelReport.SellerAreaNameText);
		
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		//服务费比例
		initReportWord(arraylist,ExcelReport.PlatcostRatio,ExcelReport.PlatcostRatioText,setDecimal);
		//供应商系数
		initReportWord(arraylist,ExcelReport.SellerCoefficient,ExcelReport.SellerCoefficientText);
		//服务费平台分成比例
		initReportWord(arraylist,ExcelReport.CountryPlatcostRatio,ExcelReport.CountryPlatcostRatioText,setDecimal);
		//服务费平台分成金额
		initReportWord(arraylist,ExcelReport.PlatformTidianMoney,ExcelReport.PlatformTidianMoneyText,setDecimal);
		//服务费销售县域分成比例
		initReportWord(arraylist,ExcelReport.AreaPlatcostRatio,ExcelReport.BelongAreaPlatcostRatioText,setDecimal);
		//服务费销售县域分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleAreaTidianMoney,ExcelReport.BelongSaleAreaTidianMoneyText,setDecimal);
		//佣金比例
		initReportWord(arraylist,ExcelReport.CommissionRatio,ExcelReport.CommissionRatioText,setDecimal);
		
		//佣金网点分成比例
		initReportWord(arraylist,ExcelReport.ShopCommissionRatio,ExcelReport.ShopCommissionRatioText,setDecimal);
		//佣金网点分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleStoreCommissionMoney,ExcelReport.SaleStoreCommissionMoneyText,setDecimal);
		//佣金县域分成比例
		initReportWord(arraylist,ExcelReport.AreaCommissionRatio,ExcelReport.AreaCommissionRatioText,setDecimal);
		//佣金县域分成金额
		initReportWord(arraylist,ExcelReport.SaleAreaCommissionMoney,ExcelReport.SaleAreaCommissionMoneyText,setDecimal);
		
		return arraylist;
	
	}else if(roleType==1&&orderType==4){//全国批发代销
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		
		commonColumn(arraylist);
		//供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		//所属县域
		initReportWord(arraylist,ExcelReport.SellerAreaName,ExcelReport.SellerAreaNameText);
		
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.SupplyPriceText,setDecimal);
		//代销利润
		initReportWord(arraylist,ExcelReport.DxAmount,ExcelReport.DxAmountText,setDecimal);
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		
		return arraylist;
	
	}else if(roleType==1&&orderType==8){//全国零售代销
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		
		commonColumn(arraylist);
		//供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		//所属县域
		initReportWord(arraylist,ExcelReport.SellerAreaName,ExcelReport.SellerAreaNameText);
		
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
	
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.SupplyPriceText,setDecimal);
		//代销利润
		initReportWord(arraylist,ExcelReport.DxAmount,ExcelReport.DxAmountText,setDecimal);
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		//服务费比例
		initReportWord(arraylist,ExcelReport.PlatcostRatio,ExcelReport.PlatcostRatioText,setDecimal);
		//供应商系数
		initReportWord(arraylist,ExcelReport.SellerCoefficient,ExcelReport.SellerCoefficientText);
		//服务费平台分成比例
		initReportWord(arraylist,ExcelReport.CountryPlatcostRatio,ExcelReport.CountryPlatcostRatioText,setDecimal);
		//服务费平台分成金额
		initReportWord(arraylist,ExcelReport.PlatformTidianMoney,ExcelReport.PlatformTidianMoneyText,setDecimal);
		//服务费供货县域分成比例
		initReportWord(arraylist,ExcelReport.SupplyAreaPlatcostRatio,ExcelReport.SupplyAreaPlatcostRatioText,setDecimal);
		//服务费供货县域分成金额
		initReportWord(arraylist,ExcelReport.SoureAreaTidianMoney,ExcelReport.SoureAreaTidianMoneyText,setDecimal);
		//服务费销售县域分成比例
		initReportWord(arraylist,ExcelReport.AreaPlatcostRatio,ExcelReport.AreaPlatcostRatioText,setDecimal);
		//服务费销售县域分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleAreaTidianMoney,ExcelReport.SaleAreaTidianMoneyText,setDecimal);
		//佣金比例
		initReportWord(arraylist,ExcelReport.CommissionRatio,ExcelReport.CommissionRatioText,setDecimal);
		
		//佣金网点分成比例
		initReportWord(arraylist,ExcelReport.ShopCommissionRatio,ExcelReport.ShopCommissionRatioText,setDecimal);
		//佣金网点分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleStoreCommissionMoney,ExcelReport.SaleStoreCommissionMoneyText,setDecimal);
		//佣金县域分成比例
		initReportWord(arraylist,ExcelReport.AreaCommissionRatio,ExcelReport.AreaCommissionRatioText,setDecimal);
		//佣金县域分成金额
		initReportWord(arraylist,ExcelReport.SaleAreaCommissionMoney,ExcelReport.SaleAreaCommissionMoneyText,setDecimal);
		
		return arraylist;
	
	}
	//县域角色
	else if(roleType==2&&orderType==1){//县域角色全国零售
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//供应商优惠金额
		initReportWord(arraylist,ExcelReport.SupplierCouponMoney,ExcelReport.SupplierCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		
		commonColumn(arraylist);
		
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		//服务费比例
		initReportWord(arraylist,ExcelReport.PlatcostRatio,ExcelReport.PlatcostRatioText,setDecimal);
		//供应商系数
		initReportWord(arraylist,ExcelReport.SellerCoefficient,ExcelReport.SellerCoefficientText);
		//服务费平台分成比例
		initReportWord(arraylist,ExcelReport.CountryPlatcostRatio,ExcelReport.CountryPlatcostRatioText,setDecimal);
		//服务费平台分成金额
		initReportWord(arraylist,ExcelReport.PlatformTidianMoney,ExcelReport.PlatformTidianMoneyText,setDecimal);
		//服务费供货县域分成比例
		initReportWord(arraylist,ExcelReport.SupplyAreaPlatcostRatio,ExcelReport.SupplyAreaPlatcostRatioText,setDecimal);
		//服务费供货县域分成金额
		initReportWord(arraylist,ExcelReport.SoureAreaTidianMoney,ExcelReport.SoureAreaTidianMoneyText,setDecimal);
		//服务费销售县域分成比例
		initReportWord(arraylist,ExcelReport.AreaPlatcostRatio,ExcelReport.AreaPlatcostRatioText,setDecimal);
		//服务费销售县域分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleAreaTidianMoney,ExcelReport.SaleAreaTidianMoneyText,setDecimal);
		//佣金比例
		initReportWord(arraylist,ExcelReport.CommissionRatio,ExcelReport.CommissionRatioText,setDecimal);
		
		//佣金网点分成比例
		initReportWord(arraylist,ExcelReport.ShopCommissionRatio,ExcelReport.ShopCommissionRatioText,setDecimal);
		//佣金网点分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleStoreCommissionMoney,ExcelReport.SaleStoreCommissionMoneyText,setDecimal);
		//佣金县域分成比例
		initReportWord(arraylist,ExcelReport.AreaCommissionRatio,ExcelReport.AreaCommissionRatioText,setDecimal);
		//佣金县域分成金额
		initReportWord(arraylist,ExcelReport.SaleAreaCommissionMoney,ExcelReport.SaleAreaCommissionMoneyText,setDecimal);
		
		return arraylist;
		
	}else if(roleType==2&&orderType==2){//县域批发
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//供应商优惠金额
		initReportWord(arraylist,ExcelReport.SupplierCouponMoney,ExcelReport.SupplierCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		
		commonColumn(arraylist);
		
		//供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		//所属县域
		initReportWord(arraylist,ExcelReport.SellerAreaName,ExcelReport.SellerAreaNameText);
		
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		
		return arraylist;
	
		
	}else if(roleType==2&&orderType==3){//县域零售
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//供应商优惠金额
		initReportWord(arraylist,ExcelReport.SupplierCouponMoney,ExcelReport.SupplierCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		
		commonColumn(arraylist);
		//供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		//服务费比例
		initReportWord(arraylist,ExcelReport.PlatcostRatio,ExcelReport.PlatcostRatioText,setDecimal);
		//供应商系数
		initReportWord(arraylist,ExcelReport.SellerCoefficient,ExcelReport.SellerCoefficientText);
		//服务费平台分成比例
		initReportWord(arraylist,ExcelReport.CountryPlatcostRatio,ExcelReport.CountryPlatcostRatioText,setDecimal);
		//服务费平台分成金额
		initReportWord(arraylist,ExcelReport.PlatformTidianMoney,ExcelReport.PlatformTidianMoneyText,setDecimal);
		//服务费销售县域分成比例
		initReportWord(arraylist,ExcelReport.AreaPlatcostRatio,ExcelReport.BelongAreaPlatcostRatioText,setDecimal);
		//服务费销售县域分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleAreaTidianMoney,ExcelReport.BelongSaleAreaTidianMoneyText,setDecimal);
		//佣金比例
		initReportWord(arraylist,ExcelReport.CommissionRatio,ExcelReport.CommissionRatioText,setDecimal);
		
		//佣金网点分成比例
		initReportWord(arraylist,ExcelReport.ShopCommissionRatio,ExcelReport.ShopCommissionRatioText,setDecimal);
		//佣金网点分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleStoreCommissionMoney,ExcelReport.SaleStoreCommissionMoneyText,setDecimal);
		//佣金县域分成比例
		initReportWord(arraylist,ExcelReport.AreaCommissionRatio,ExcelReport.AreaCommissionRatioText,setDecimal);
		//佣金县域分成金额
		initReportWord(arraylist,ExcelReport.SaleAreaCommissionMoney,ExcelReport.SaleAreaCommissionMoneyText,setDecimal);
		
		return arraylist;
	
	}else if(roleType==2&&orderType==4){//全国批发代销
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		commonColumn(arraylist);
		
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);

		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.SupplyPriceText,setDecimal);
		//代销利润
		initReportWord(arraylist,ExcelReport.DxAmount,ExcelReport.DxAmountText,setDecimal);
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		
		return arraylist;
	
	}else if(roleType==2&&orderType==8){//全国零售代销
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		commonColumn(arraylist);
		
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.SupplyPriceText,setDecimal);
		//代销利润
		initReportWord(arraylist,ExcelReport.DxAmount,ExcelReport.DxAmountText,setDecimal);
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		//服务费比例
		initReportWord(arraylist,ExcelReport.PlatcostRatio,ExcelReport.PlatcostRatioText,setDecimal);
		//供应商系数
		initReportWord(arraylist,ExcelReport.SellerCoefficient,ExcelReport.SellerCoefficientText);
		//服务费平台分成比例
		initReportWord(arraylist,ExcelReport.CountryPlatcostRatio,ExcelReport.CountryPlatcostRatioText,setDecimal);
		//服务费平台分成金额
		initReportWord(arraylist,ExcelReport.PlatformTidianMoney,ExcelReport.PlatformTidianMoneyText,setDecimal);
		//服务费供货县域分成比例
		initReportWord(arraylist,ExcelReport.SupplyAreaPlatcostRatio,ExcelReport.SupplyAreaPlatcostRatioText,setDecimal);
		//服务费供货县域分成金额
		initReportWord(arraylist,ExcelReport.SoureAreaTidianMoney,ExcelReport.SoureAreaTidianMoneyText,setDecimal);
		//服务费销售县域分成比例
		initReportWord(arraylist,ExcelReport.AreaPlatcostRatio,ExcelReport.AreaPlatcostRatioText,setDecimal);
		//服务费销售县域分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleAreaTidianMoney,ExcelReport.SaleAreaTidianMoneyText,setDecimal);
		//佣金比例
		initReportWord(arraylist,ExcelReport.CommissionRatio,ExcelReport.CommissionRatioText,setDecimal);
		
		//佣金网点分成比例
		initReportWord(arraylist,ExcelReport.ShopCommissionRatio,ExcelReport.ShopCommissionRatioText,setDecimal);
		//佣金网点分成金额（元）
		initReportWord(arraylist,ExcelReport.SaleStoreCommissionMoney,ExcelReport.SaleStoreCommissionMoneyText,setDecimal);
		//佣金县域分成比例
		initReportWord(arraylist,ExcelReport.AreaCommissionRatio,ExcelReport.AreaCommissionRatioText,setDecimal);
		//佣金县域分成金额
		initReportWord(arraylist,ExcelReport.SaleAreaCommissionMoney,ExcelReport.SaleAreaCommissionMoneyText,setDecimal);
		
		return arraylist;
	
	}
	
	//供应商角色
	else if(roleType==3&&orderType==1){//全国零售
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//供应商优惠金额
		initReportWord(arraylist,ExcelReport.SupplierCouponMoney,ExcelReport.SupplierCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		commonColumn(arraylist);
		
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供应商服务费比例
		initReportWord(arraylist,ExcelReport.SupplierRatio,ExcelReport.SupplierRatioText,setDecimal);
		//供应商服务费金额
		initReportWord(arraylist,ExcelReport.SupplierRatioAmount,ExcelReport.SupplierRatioAmountText,setDecimal);
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		//佣金比例
		initReportWord(arraylist,ExcelReport.CommissionRatio,ExcelReport.CommissionRatioText,setDecimal);
		//佣金金额
		initReportWord(arraylist,ExcelReport.CommissionRatioAmount,ExcelReport.CommissionRatioAmountText,setDecimal,setDecimal);
		
		return arraylist;
	
	}else if(roleType==3&&orderType==2){
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//供应商优惠金额
		initReportWord(arraylist,ExcelReport.SupplierCouponMoney,ExcelReport.SupplierCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		commonColumn(arraylist);
		
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		
		return arraylist;
	
	}else if(roleType==3&&orderType==3){//县域零售
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//供应商优惠金额
		initReportWord(arraylist,ExcelReport.SupplierCouponMoney,ExcelReport.SupplierCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		commonColumn(arraylist);
		
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		//供应商服务费比例
		initReportWord(arraylist,ExcelReport.SupplierRatio,ExcelReport.SupplierRatioText,setDecimal);
		//供应商服务费金额
		initReportWord(arraylist,ExcelReport.SupplierRatioAmount,ExcelReport.SupplierRatioAmountText,setDecimal);
		//佣金比例
		initReportWord(arraylist,ExcelReport.CommissionRatio,ExcelReport.CommissionRatioText,setDecimal);
		//佣金金额
		initReportWord(arraylist,ExcelReport.CommissionRatioAmount,ExcelReport.CommissionRatioAmountText,setDecimal);
		
		return arraylist;
	
	}else if(roleType==3&&orderType==4){//全国批发代销
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		commonColumn(arraylist);
		
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.SupplyPriceText,setDecimal);
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		
		return arraylist;
	
	}else if(roleType==3&&orderType==8){//全国零售代销
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		commonColumn(arraylist);
		
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.SupplyPriceText,setDecimal);
		//供应商应收款
		initReportWord(arraylist,ExcelReport.ActualPayAmount,ExcelReport.ActualPayAmountText,setDecimal);
		//供应商服务费比例
		initReportWord(arraylist,ExcelReport.SupplierRatio,ExcelReport.SupplierRatioText,setDecimal);
		//供应商服务费金额
		initReportWord(arraylist,ExcelReport.SupplierRatioAmount,ExcelReport.SupplierRatioAmountText,setDecimal);
		
		return arraylist;
	
	}
	
	//代销商角色
	else if(roleType==4&&orderType==4){//全国批发代销
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		commonColumn(arraylist);
	   //供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		//所属县域
		initReportWord(arraylist,ExcelReport.SellerAreaName,ExcelReport.SellerAreaNameText);
		
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.SupplyPriceText,setDecimal);
		//代销利润
		initReportWord(arraylist,ExcelReport.DxAmount,ExcelReport.DxAmountText,setDecimal);
		
		return arraylist;
	
	}else if(roleType==4&&orderType==8){//全国零售代销
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//商品名称
		initReportWord(arraylist,ExcelReport.GoodsName,ExcelReport.GoodsNameText);
		//商品编码
		initReportWord(arraylist,ExcelReport.GoodsNo,ExcelReport.GoodsNoText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//销售单价
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.OriginalPriceText,setDecimal);
		//销售数量
		initReportWord(arraylist,ExcelReport.SaleNumber,ExcelReport.SaleNumberText);
		//实付款
		initReportWord(arraylist,ExcelReport.ShouldPayAmount,ExcelReport.ShouldPayAmountText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);
		//优惠活动
		initReportWord(arraylist,ExcelReport.Promotion,ExcelReport.PromotionText);
		//平台优惠金额
		initReportWord(arraylist,ExcelReport.PlatformCouponMoney,ExcelReport.PlatformCouponMoneyText,setDecimal);
		//县域优惠金额
		initReportWord(arraylist,ExcelReport.CountyCouponMoney,ExcelReport.CountyCouponMoneyText,setDecimal);
		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SjAmount,ExcelReport.SjAmountText,setDecimal);
		//退款数量
		initReportWord(arraylist,ExcelReport.PiecesNum,ExcelReport.PiecesNumText);
		//单位
		initReportWord(arraylist,ExcelReport.GoodsUnit,ExcelReport.GoodsUnitText);
		commonColumn(arraylist);
	    //供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		//所属县域
		initReportWord(arraylist,ExcelReport.SellerAreaName,ExcelReport.SellerAreaNameText);
		
		//买家名称
		initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//商品分类
		initReportWord(arraylist,ExcelReport.CategoryPath,ExcelReport.CategoryPathText);
		//规格编码
		initReportWord(arraylist,ExcelReport.SkuCode,ExcelReport.SkuCodeText);
		//商品货号
		initReportWord(arraylist,ExcelReport.GoodsNum,ExcelReport.GoodsNumText);
		//商品标签
		initReportWord(arraylist,ExcelReport.GoodsTagName,ExcelReport.GoodsTagNameText);
		//网点类型
		shopTypeText(arraylist);
		//订单来源
		orderSourceText(arraylist);
		
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.SupplyPriceText,setDecimal);
		//代销利润
		initReportWord(arraylist,ExcelReport.DxAmount,ExcelReport.DxAmountText,setDecimal);
		//佣金比例
		initReportWord(arraylist,ExcelReport.CommissionRatio,ExcelReport.CommissionRatioText,setDecimal);
		//佣金金额
		initReportWord(arraylist,ExcelReport.CommissionRatioAmount,ExcelReport.CommissionRatioAmountText,setDecimal);
		
		return arraylist;
	
	}else if(roleType==1&&orderType==6){//充值话费订单
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//买家名称
		//initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//充值号码
		initReportWord(arraylist,ExcelReport.RechargeMobile,ExcelReport.RechargeMobileText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);

		//实付款
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.DummyOriginalPriceText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);

		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SalesPrice,ExcelReport.SalesPriceText,setDecimal);
		//下单时间
		setCreateTime(arraylist);
		//订单状态、售后状态
		statusDummy(arraylist);
		 //供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.DummySupplyPriceText,setDecimal);
		//网点佣金
		initReportWord(arraylist,ExcelReport.SaleStoreCommissionMoney,ExcelReport.SaleStoreCommissionMoneyText,setDecimal);
		
		return arraylist;
		
	}else if(roleType==2&&orderType==6){//充值话费订单
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//买家名称
		//initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//充值号码
		initReportWord(arraylist,ExcelReport.RechargeMobile,ExcelReport.RechargeMobileText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);

			//实付款
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.DummyOriginalPriceText,setDecimal);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);

		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SalesPrice,ExcelReport.SalesPriceText,setDecimal);
		//下单时间
		setCreateTime(arraylist);
		//订单状态、售后状态
		statusDummy(arraylist);
		 //供应商
		initReportWord(arraylist,ExcelReport.SellerName,ExcelReport.SellerNameText);
		
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.DummySupplyPriceText,setDecimal);
		//网点佣金
		initReportWord(arraylist,ExcelReport.SaleStoreCommissionMoney,ExcelReport.SaleStoreCommissionMoneyText,setDecimal);

		return arraylist;
	}else if(roleType==3&&orderType==6){//充值话费订单
		var arraylist=new Array();
		//订单编号
		initReportWord(arraylist,ExcelReport.OrderNo,ExcelReport.OrderNoText);
		//买家名称
		//initReportWord(arraylist,ExcelReport.FinalConstomer,ExcelReport.FinalConstomerText);
		//买家电话
		initReportWord(arraylist,ExcelReport.FinalConstomerTel,ExcelReport.FinalConstomerTelText);
		//充值号码
		initReportWord(arraylist,ExcelReport.RechargeMobile,ExcelReport.RechargeMobileText);
		//商品规格
		initReportWord(arraylist,ExcelReport.SkuMsg,ExcelReport.SkuMsgText);
		//实付款
		initReportWord(arraylist,ExcelReport.OriginalPrice,ExcelReport.DummyOriginalPriceText,setDecimal);
		//下单县域
		initReportWord(arraylist,ExcelReport.AreaName,ExcelReport.AreaNameText);
		//下单网点
		initReportWord(arraylist,ExcelReport.ShopName,ExcelReport.ShopNameText);
		//网点编号
		initReportWord(arraylist,ExcelReport.ShopNo,ExcelReport.ShopNoText);

		//退款金额
		initReportWord(arraylist,ExcelReport.ActualBackAmount,ExcelReport.ActualBackAmountText,setDecimal);
		//实际成交金额
		initReportWord(arraylist,ExcelReport.SalesPrice,ExcelReport.SalesPriceText,setDecimal);
		//下单时间
		setCreateTime(arraylist);
		//订单状态、售后状态
		statusDummy(arraylist);
		
		//供货价
		initReportWord(arraylist,ExcelReport.SupplyPrice,ExcelReport.DummySupplyPriceText,setDecimal);
		//网点佣金
		initReportWord(arraylist,ExcelReport.SaleStoreCommissionMoney,ExcelReport.DummySaleStoreCommissionMoneyText,setDecimal);
		
		return arraylist;
	}
	
	

}

function addCommonEnd(arraylist){
	//网点联系人
	initReportWord(arraylist,ExcelReport.BuyersContact,ExcelReport.BuyersContactText);
	//网点联系电话
	initReportWord(arraylist,ExcelReport.BuyersTel,ExcelReport.BuyersTelText);
	return arraylist;
}


function setCreateTime(arraylist){
	var createtimesk=new Array();
	createtimesk.push(new showAlias("createTime","createTime","","yyyy-MM-dd HH:mm:ss"));
	arraylist.push(new mapfuntion("下单时间",createtimesk));
}


//orderType:订单类型 ,outType：导出类型 ,roletype：角色类型
function outReportExcel(orderType,roletype,params) {
		
	 var obj=new Object();
//	 obj.tabNo=tabNo;
	 obj.orderType=orderType;
//	 obj.orders=detailIds;
	 var arrayList = getMap(orderType,roletype);
	 if(orderType!=6){
		 arrayList = addCommonEnd(arrayList);
	 }
	 obj.showExcel=arrayList;
	var jsonmap= JSON.stringify(arrayList);
	if($('#input_form').find("[name='showExcel']").length==0){
		$('#input_form').append("<input type='hidden' value=''  name='showExcel' id='showExcel'/> ");
	}

	$('#input_form').find("[name='showExcel']").val(jsonmap);
	var tableObj=$('#table_input');
//	var params={}
	var reportType=orderType;
//	var orderNo=$.trim(tableObj.find("input[name='txt_orderNo']").val());
//	var ordersStatus=$.trim($('#ordersStatus').combobox('getValue'));
//	var shops=$.trim($('#shops').combobox('getText'));
//	var goodName=$.trim(tableObj.find("input[id='goodName']").val());
//	var supplierId=$.trim($('#supplierId').combobox('getValue'));
//	var afterSaleStatus=$.trim($('#afterSaleStatus').combobox('getValue'));
//	var buyersProvince=$.trim($("#provincial").combobox("getText"));
//	var buyersCity=$.trim($("#cities").combobox("getText"));
//	var buyersArea=$.trim($("#counties").combobox("getValue"));
//	var sellerProvince=$.trim($("#ownerProvincial").combobox("getText"));
//	var sellerCity=$.trim($("#ownerCities").combobox("getText"));
//	var sellerArea=$.trim($("#ownerCounties").combobox("getValue"));
//	var startTime=$.trim($('#startTime').datebox('getValue'));    
////	if(!startTime){
////		startTime=dataConvent();
////		 $("#startTime").datebox("setValue", dataConvent());
////	}
////	startTime +=" 00:00:00";
//	var endTime=$.trim($("#endTime").datebox('getValue'));
////	if(!endTime){
////		endTime=dataConvent();
////		$("#endTime").datebox("setValue", dataConvent());
////	}
////	endTime+=" 23:59:59";
//	
//	var orderSource=getComboboxValue("orderSource");
//	var shopType=getComboboxValue("shopType");
//	
//	if(shops=="全部"){
//		shops="";
//	}
//	if(buyersProvince!=''&&buyersProvince!='选择省'){
//		params.buyersProvince=buyersProvince;
//	}
//	if(buyersCity!=''&&buyersCity!='选择市'){
//		params.buyersCity=buyersCity;
//	}
//	if(buyersArea!=''&&buyersArea!='选择县'){
//		params.buyersArea=buyersArea;
//	}
//	if(sellerProvince!=''&&sellerProvince!='选择省'){
//		params.sellerProvince=sellerProvince;
//	}
//	if(sellerCity!=''&&sellerCity!='选择市'){
//		params.sellerCity=sellerCity;
//	}
//	if(sellerArea!=''&&sellerArea!='选择县'){
//		params.sellerArea=sellerArea;
//	}
//	
	   if(reportType!='' && reportType!='-1'){
		   params.reportType=reportType;
	   }
//   if(orderNo!=''){
//	   params.orderNo=orderNo;
//   }
//   if(ordersStatus!='' && ordersStatus!='-1'){
//	   params.ordersStatus=ordersStatus;
//   }
//   if(shops!='' && shops!='-1'){
//	   params.shops=shops;
//   }
//   if(goodName!=''){
//	   params.goodName=goodName;
//   }			   			   
//   if(supplierId!='' && supplierId!='-1'){
//	   params.supplierId=supplierId;
//   }
//   if(afterSaleStatus!='' && afterSaleStatus!='-1'){
//	   params.afterSaleStatus=afterSaleStatus;
//   }
//   if(startTime!=''){
//	   params.startTime=startTime;
//   }
//   if(endTime!=''){
//	   params.endTime=endTime;	
//   }
//   if(orderSource!='' && orderSource!='-1'){
//	   params.orderSource=orderSource;
//   }
//   if(shopType!='' && shopType!='-1'){
//	   params.shopType=shopType;
//   }
//   
	params.type=roletype;
	var queryOrderReportVo=JSON.stringify(params);

	if($('#input_form').find("[name='queryOrderReportVo']").length==0){
		$('#input_form').append("<input type='hidden' value=''  name='queryOrderReportVo' id='queryOrderReportVo'/> ");
	}
	$('#input_form').find("[name='queryOrderReportVo']").val(queryOrderReportVo);
	
	$('#input_form').attr("action","../../outExcel/exportOrderVoList.do");
	
	$('#input_form').submit();
}

function getComboboxValue(id){
	var queryId = "#" + id;
	if($(queryId).length==1){
		return $.trim($(queryId).combobox("getValue"))
	}else{
		return "";
	}
}








