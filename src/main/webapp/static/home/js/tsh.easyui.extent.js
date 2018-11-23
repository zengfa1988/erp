$(function() {
	paginationExtent();
	messageExtent();
	tipExtent();
});

// 自定义分页
function paginationExtent() {
	var layout = [ "manual", "first", "prev", "links", "next", "last",
			"refresh" ];
	$.fn.pagination.defaults.layout = layout;
	$.fn.pagination.defaults.links = 5;
	$.fn.pagination.defaults.beforePageText = "";
	$.fn.pagination.defaults.afterPageText = "共：{pages}页";
	// displayMsg: "显示{from}到{to},共{total}记录"
	$.fn.pagination.defaults.displayMsg = '总记录数：<span class="text-info">{total}</span>';
}

function messageExtent() {
	$.messager.defaults.width = 400;
}

function tipExtent() {
	$.extend($.fn.validatebox.defaults.tipOptions, {
		onShow : function() {
			$(this).tooltip("tip").css({
				color : "#333",
				borderColor : "#ccc",
				backgroundColor : "#fff"
			});
		}
	});
}

function err(target, message) {
	var t = $(target);
	if (t.hasClass('textbox-text')) {
		t = t.parent();
	}
	if (message) {
		message = '&nbsp;<i class="i-error"></i>&nbsp;' + message;
	} else {
		message = '&nbsp;<i class="i-success"></i>&nbsp;';
	}
	if ($(target).hasClass('textbox-prompt')) {
		message = "";
	}
	var m = t.next('.error-message');
	if (!m.length) {
		m = $('<div class="error-message" style="display:inline-block"></div>')
				.insertAfter(t);
	}
	m.html(message);
}