package com.niuxing.auc.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niuxing.auc.service.LoginLogService;
import com.niuxing.auc.service.OperatePwdLogService;
import com.niuxing.auc.service.UserLoginService;
import com.niuxing.auc.service.UserService;
import com.niuxing.auc.vo.LoginUserVo;
import com.niuxing.auc.vo.LoginUserWebVo;
import com.niuxing.util.Base64;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.bean.ReturnDTO;
import com.zengfa.platform.util.security.DigestUtils;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.util.security.UserResMenu;
import com.zengfa.platform.web.userinfo.LoginUtil;
import com.zengfa.platform.web.userinfo.UserUtil;


/**
 * 登录
 * 
 * @author ds
 * 
 */
@Controller
@RequestMapping("/usercentre/")
@SuppressWarnings("unchecked")
public class UserLoginController extends BaseController {//

	@Resource
	UserLoginService userLoginService;
	@Resource
	LoginLogService loginLogService;
	@Resource
	OperatePwdLogService operatePwdLogService;
	@Resource private UserService userService;
	

	/**
	 * 
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO login(LoginUserWebVo vo, HttpServletRequest request, HttpServletResponse response) {
		Result result = this.getResult(request);
		String homeUrl = "index.html";
		try {
			if (StringUtils.isEmpty(vo.getLoginName()) || StringUtils.isEmpty(vo.getPassword())) {
				return result.NO(500, "登录账号或密码不能为空").DTO();
			}
			String password = new String(Base64.decode(vo.getPassword()));
			String loginName = new String(Base64.decode(vo.getLoginName().trim()));
			int i = password.lastIndexOf('_');
			if (i >= 0) {
				String randomDate = password.substring(i + 1, password.length());
				password = password.substring(0, i);
				// 时间戳
//				Long randomNum = (Long) SerializeUtil.unserialize(RedisSlave.getInstance().get(UserConstants.REDIS_KEY_LEAF_LOGIN_TIME_STAMP + loginName));
//				if (randomNum == null) {
//					randomNum = 0L;
//				}
//				if (StringUtils.isNotBlank(randomDate) && !randomDate.equals(randomNum.toString())) {
//					return result.NO(500, "验证失败，请重试！", null).DTO();
//				}
			}

			result = loginCheck(request, vo, loginName);
			if (result.getStatus() == 500) {
				return result.DTO();
			}

			String loginIp = LoginUtil.getClientIpAddr(request);
			result = userLoginService.validatePassword(result, loginName, password, loginIp);

			if (result.getData() == null || result.getStatus() == 500) {
				return result.NO(500, result.getMsg(), result.getData()).DTO();
			}
			
			LoginUserVo loginUserVo = result.getData();
			UserInfo userInfo = new UserInfo();
			PropertyUtils.copyProperties(userInfo, loginUserVo);
			// 生成token
			String token = "auc_jsession_" + DigestUtils.sha1Hex(loginUserVo.getLoginName()+loginUserVo.getUserId()) + DigestUtils.sha1Hex(new Date().toString());
			userInfo.setToken(token);
			userInfo.setSessionId(token);
			
//			if (loginUserVo.getIsProtection() != null && loginUserVo.getIsProtection().equals(1)) {// 开启登录保护
//				RedisSlave.getInstance().setObject(UserConstants.TSH_USER_LOGIN_PROTECTION + loginUserVo.getLoginName(), SerializeUtil.serialize(userInfo), SessionKey.COOKIE_EXPIRATIOIN_TIME);
//				String phone = loginUserVo.getPhone();
//				String tmp = phone.substring(3, 7);
//				phone = phone.replace(tmp, "****") + "|" + UserConstants.TSH_USER_LOGIN_PROTECTION + loginUserVo.getLoginName();
//				return result.OK(phone).DTO();
//			}
			LoginUtil.saveUserInfo(userInfo, token, request, response);
//			RedisSlave.getInstance().setObject(token, SerializeUtil.serialize(userInfo), SessionKey.COOKIE_EXPIRATIOIN_TIME);

			request.getSession().setAttribute(token, userInfo);
			// 记录登录日志
			loginLogService.saveSysLoginLog(result, userInfo, "浏览器登录");

		} catch (Exception e) {
			result = this.error(result, e);
			return result.NO(500, "程序异常").DTO();
		} finally {
			this.send(result);
		}
		return result.OK(homeUrl).DTO();
	}
	

	/**
	 * 登录检查
	 * 
	 * @param request
	 * @param vo
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	private Result loginCheck(HttpServletRequest request, LoginUserWebVo vo, String loginName) throws Exception {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo != null && !loginName.equals(loginInfo.getLoginName())) {
			return result.NO(500, "你已经登录了账号：" + loginInfo.getLoginName() + ",如需切换账号,请关闭浏览器重新登录！", vo);
		}
		return result.OK(null);
	}

	
	/**
	 * 退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/outLogin.do")
	public String outLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Result result = this.getResult(request);
		String sessionId = UserUtil.getSessionId(request);
		String project_login_url="../login.html";
		try {
			if (StringUtils.isBlank(sessionId)) {
				return "redirect:"+project_login_url;
			}
//			RedisSlave.getInstance().del(sessionId);
			request.getSession().removeAttribute(sessionId);
			LoginUtil.clearLoginInfo(request, response);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return "redirect:"+project_login_url;
	}

	

	/**
	 * 判断该用户是否显示公告
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getHomepageUrl.do", method = RequestMethod.GET)
	@ResponseBody
	public ReturnDTO getHomepageUrl(HttpServletRequest request, HttpServletResponse response) {
		Result result = this.getResult(request);
		try {

			UserInfo loginInfo = UserUtil.getUserInfo(request);

		} catch (Exception e) {
			e.printStackTrace();
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}

	/**
	 * 获取用户菜单权限
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWebMenu.do", method = RequestMethod.GET)
	@ResponseBody
	public ReturnDTO getWebMenu(HttpServletRequest request) {
		Result result = this.getResult(request);
		try {
			UserInfo loginInfo = UserUtil.getUserInfo(request);
			if (loginInfo == null) {
				return result.NO(500, "").DTO();
			}
			List<UserResMenu> resourcesMenu = UserUtil.getUserResMenu(request);
			if (resourcesMenu == null || resourcesMenu.isEmpty()) {
				return result.NO(500, "").DTO();
			}
			result.setData(resourcesMenu);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}

	/**
	 * 获取随机数
	 * @param account
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/randomNum.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO randomNum(String account, HttpServletRequest request) {
		Result result = this.getResult(request);
		try {
			Date date = new Date();
			result.setData(date.getTime());
//			RedisSlave.getInstance().setObject(UserConstants.REDIS_KEY_LEAF_LOGIN_TIME_STAMP + account,
//					SerializeUtil.serialize(date.getTime()),
//					UserConstants.REDIS_KEY_LEAF_LOGIN_TIME_STAMP_VALID);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}

	/**
	 * 获取用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo.do", method = RequestMethod.GET)
	@ResponseBody
	public UserInfo getUserInfo(HttpServletRequest request) {
		Result result = this.getResult(request);
		UserInfo userVo = new UserInfo();
		try {
			UserInfo loginInfo = UserUtil.getUserInfo(request);
			if (loginInfo == null || loginInfo.getUserId() <= 0) {// 用户验证
				return null;
			}
			userVo.setLoginName(loginInfo.getLoginName());
			userVo.setLoginCount(loginInfo.getLoginCount());
			userVo.setErrorCount(loginInfo.getErrorCount());
			userVo.setRoleType(loginInfo.getRoleType());
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return userVo;
	}

	/**
	 * 检查是否登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checklogin.do")
	@ResponseBody
	public boolean checklogin(HttpServletRequest request) {
		Result result = this.getResult(request);
		boolean bl = true;
		try {
			UserInfo loginInfo = UserUtil.getUserInfo(request);
			if (loginInfo == null || loginInfo.getUserId() == null) {// 用户验证
				return false;
			}
//			List<UserResMenu> allList = (List<UserResMenu>) SerializeUtil.unserialize(RedisSlave.getInstance().get(SessionKey.REDIS_KEY_ALL_MUEN_VALUE + loginInfo.getUserId()));
//			if (allList == null || allList.isEmpty()) {
//				return false;
//			}

		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return bl;
	}

	/**
	 * 检查登录是否异常
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/abnormalLoginCheck.do")
	@ResponseBody
	public ReturnDTO abnormalLoginCheck(HttpServletRequest request) {
		Result result = this.getResult(request);
		try {
			UserInfo loginInfo = UserUtil.getUserInfo(request);
			if (loginInfo == null || loginInfo.getUserId() == null) {// 用户验证
				return result.DTO();
			}
//			result = loginLogService.checkLoginAddress(result, loginInfo.getLoginName());

		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/recoverPassword.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO recoverPassword(HttpServletRequest request) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null || loginInfo.getUserId() == null) {// 用户验证
			return result.NO(500, "登录超时！").DTO();
		}
		try {
			String newPassword = request.getParameter("newPassword");
			if (StringUtils.isBlank(newPassword)) {
				return result.NO(500, "用户密码为空").DTO();
			}

			String password = new String(Base64.decode(newPassword));
			if (!VerifiedCodeController.checkPwd(password)) {
				return result.NO(500, "密码过于简单").DTO();
			}
			Long userId = loginInfo.getUserId();
			result = userLoginService.updatePassword(result, password, userId);
			if (result.getStatus() == 200) {
				operatePwdLogService.saveSysPwdOperateLog(result, loginInfo, userId, 1, "设置密码（简单密码）");
				result.setData("/views/login.html");
			}
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}

		return result.DTO();
	}
	
}
