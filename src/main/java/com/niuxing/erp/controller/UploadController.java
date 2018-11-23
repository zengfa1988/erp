package com.niuxing.erp.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.niuxing.auc.controller.BaseController;
import com.zengfa.platform.util.UUIDUtil;
import com.zengfa.platform.util.bean.Result;

/**
 * 
 * @author ds
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings("static-access")
@RequestMapping("/upload")
public class UploadController extends BaseController{	
	private static final String OPERATION_FAILURE = "操作失败";
	private static final String FILENAME_STR= "fileName";
	private static final String CHARSET_UTF8_STR= "text/plain;charset=utf-8";
	
	
	/**
	 * 适用于webuploader
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadImg.do",method=RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> uploadImg(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request){
		Result result = this.getResult();
		if(file.isEmpty()){
			return null;
		}
		String fileName = file.getOriginalFilename();
		
		// 项目在容器中实际发布运行的根路径
		String realPath=request.getSession().getServletContext().getRealPath("/");
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String newFileName = UUIDUtil.getUUID()+suffix;
		String path=realPath+"/upload/images/"+newFileName;// 文件路径
		String urlpath = request.getContextPath()+"/upload/images/"+newFileName;
		
		try {
			file.transferTo(new File(path));
		} catch (Exception e) {
			result = this.error(result, e);
			result.setStatus(500);
			result.setMsg(OPERATION_FAILURE);
		}
		Map<Object, Object> map = new HashMap<Object, Object>();		
		map.put(FILENAME_STR, fileName);
		map.put("path", urlpath);
		return map;
	}
	
}
