package com.niuxing.auc.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.web.controller.PlatformController;
import com.zengfa.platform.web.mvc.DefaultDateEditor;
import com.zengfa.platform.web.userinfo.UserUtil;

@Scope("prototype")
@Controller
public class BaseController extends PlatformController{

	@Override
	public Result getResult() {
		return getResult(this.request);
	}

	public Result getResult(HttpServletRequest request) {
		UserInfo userInfo = UserUtil.getUserInfo(request);
		String teamName = "erp";
		Result result = new Result(request, userInfo, teamName);
		return result;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DefaultDateEditor());
	}
	
}
