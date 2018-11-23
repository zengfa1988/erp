var auc_middle_url;
function get_auc_middle_url ()
{
	$.ajax (
	{
	    type : "get",
	    url : "../../../share/getShare.do?name=auc_middle_url",
	    dataType : "json",
	    success : function (data)
	    {
		    auc_middle_url = data.data;
	    }
	});
}

/**
 * 新建tab页签
 * 
 * @param title
 * @param url
 */
function newTab (title, url)
{
	if (typeof (exec_obj) == 'undefined')
	{
		exec_obj = document.createElement ('iframe');
		exec_obj.name = 'tmp_frame';
		exec_obj.src = auc_middle_url + '?title=' + title + '&url=' + url;
		exec_obj.style.display = 'none';
		document.body.appendChild (exec_obj);
	}
	else
	{
		exec_obj.src = auc_middle_url + '?title=' + title + '&url=' + url + '&' + Math.random ();
	}
}

/**
 * 新建tab页签,从view开始配置地址 如 /views/sendoutandreceive/orderDetail.html
 * 
 * @param title
 * @param url
 */
function newTabViewRelativeUrl(title, url)
{
	url = url.replace("&","||");
	var absoluteUrl = location.protocol + '//' + location.host + url;
	newTab(title,absoluteUrl);
}

/**
 * 打开订单详情,过个测试用||分隔
 * 
 * @param orderId
 * @param orderType
 */
function openOrderDetail (orderId, orderType)
{
	var url = location.protocol + '//' + location.host + '/views/sendoutandreceive/orderDetail.html?type=' + orderType
	        + '||orderid=' + orderId;
	newTab ('订单详情'+orderId, url);
}

/**
 * 打开退款单详情
 * 
 * @param backType
 * @param roleType
 * @param backOrderNo
 */
function openBackOrdDetail (backType, roleType, backOrderNo)
{
	var bis_html = "";
	if (backType == 1)
	{
		// 退款
		if (roleType == 3)
		{
			// 县域
			bis_html = 'detail_refund_afterSales.html';
		}
		else if (roleType == 5)
		{
			bis_html = "detail_refund.html";
		}
		else
		{
			// 平台
			bis_html = 'detail_refund_platform.html';
		}
	}
	else
	{
		// 退货
		if (roleType == 3)
		{
			// 县域
			bis_html = 'detail_re_goods_afterSales.html';
		}
		else if (roleType == 5)
		{
			bis_html = "detail_re_goods.html";
		}
		else
		{
			// 平台
			bis_html = 'detail_re_goods_platform.html';
		}
	}
	var url = location.protocol + '//' + location.host + '/views/orderBack/' + bis_html + '?backOrderNo=' + backOrderNo;
	newTab ('退款单详情' + backOrderNo, url);
}

/**
 * 打开仲裁详情
 * 
 * @param backOrderNo
 */
function openArbitrationDetail (backOrderNo)
{
	var url = location.protocol + '//' + location.host + '/views/orderBack/detail_re_arbitration.html?backOrderNo='
	        + backOrderNo;
	newTab ('仲裁详情'+backOrderNo, url);
}
