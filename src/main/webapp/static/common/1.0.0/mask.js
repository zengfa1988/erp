function mask (){
	this.showMask = function (msg){
		 var defMsg = '正在处理，请稍待...';
		 if (msg) {
             defMsg = msg;
         }
		$ ("<div class=\"datagrid-mask\"></div>").css ({
		    display : "block",
		    width : "100%",
		    height : $ (document).height ()
		}).appendTo ("body");
		$ ("<div class=\"datagrid-mask-msg\"></div>").html (defMsg).appendTo ("body").css ({
		    display : "block",
		    left : ($ (document.body).outerWidth (true) - 190) / 2,
		    top : ($ (window).height () - 45) / 2
		});
	};
	this.hideMask = function (){
		$ ("body").find ("div.datagrid-mask").remove ();
		$ ("body").find ("div.datagrid-mask-msg").remove ();
	}
};