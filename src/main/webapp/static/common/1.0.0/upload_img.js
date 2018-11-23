/**
 * 上传图片
 * 
 * @param uploadBtnId
 *                上传图片按钮ID
 * @param thumbnailId
 *                缩略图ID
 * @param imgInputId
 *                图片隐藏输入框ID
 */
function uploadImg ( uploadBtnId, thumbnailId, imgInputId ) {
    // 上传图片
    $(uploadBtnId).Huploadify({
	auto : true,
	fileTypeExts : '*.jpg;*.png;*.jpeg;*.JPG;*.JPEG;*.PNG;*.gif;*.GIF',
	multi : true,
	fileSizeLimit : 2048,
	showUploadedPercent : false,
	showUploadedSize : false,
	fileObjName : 'uploadFile',
	removeTimeout : 2000,
	buttonText : '',
	uploader : '../../../img/uploadImg.do',
	onUploadStart : function () {
	    $.messager.progress({
		width : 400,
		title : '',
		msg : '图片上传中...'
	    });
	},
	onUploadComplete : function ( file, resp ) {
	    var json = $.parseJSON(resp);
	    $(thumbnailId).attr('src', json.data);
	    $(thumbnailId + "Bak").attr('src', json.data);
	    previewImg(thumbnailId);
	    $(imgInputId).val(json.data);
	    $.messager.progress('close');
	}
    });
}

/**
 * 预览图片
 * 
 * @param imgId
 */
function previewImg ( imgId ) {
    $(imgId).click(function () {
	$.openPhotoGallery($(imgId + "Bak"));
    });
}