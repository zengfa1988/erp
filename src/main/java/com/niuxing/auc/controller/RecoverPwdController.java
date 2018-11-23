package com.niuxing.auc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niuxing.auc.service.LoginLogService;
import com.niuxing.auc.service.OperatePwdLogService;
import com.niuxing.auc.service.UserLoginService;
import com.niuxing.util.Base64;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.bean.ReturnDTO;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.web.userinfo.LoginUtil;

/**
 * 
 * @author ds
 *
 */
@Controller
@RequestMapping("/recoverPwd/")
public class RecoverPwdController extends BaseController {

	@Resource
	UserLoginService userLoginService;
	@Resource
	OperatePwdLogService operatePwdLogService;
	@Resource
	LoginLogService loginLogService;


	/**
	 * 找回密码（修改密码）
	 * @param password
	 * @param account
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword.do" )
	@ResponseBody
	public ReturnDTO modifyPassword(String password, String account, String phone, String code)  {

		Result result = this.getResult();

//		String validateCode = (String) SerializeUtil.unserialize(RedisSlave.getInstance().get(UserConstants.PHONE_PREFIX + phone ));
		try {
//			if (StringUtils.isBlank(validateCode)||!validateCode.equals(code)) {
//				return result.NO(500, "验证码过期或者错误，请重试！").DTO();
//			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
			UserInfo loginInfo = result.getUserInfo();
			if (loginInfo == null) {
				result.setStatus(500);
				result.setMsg("登录信息失效");
				return result.DTO();
			}
			String pwd = new String(Base64.decode(password));
			if (!VerifiedCodeController.checkPwd(pwd)) {
				return result.NO(500, "密码过于简单").DTO();
			}
			
			String loginIp = LoginUtil.getClientIpAddr(request);
			loginInfo.setLoginIp(loginIp);
			userLoginService.recoverPwd(result, loginInfo, phone, pwd);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}
	
}
