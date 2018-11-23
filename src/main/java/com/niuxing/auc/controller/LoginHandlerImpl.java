package com.niuxing.auc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zengfa.platform.util.ListUtil;
import com.zengfa.platform.web.userinfo.LoginHandler;

/**
 * 
 * @author ds
 *
 */
@Component
public class LoginHandlerImpl extends LoginHandler {
	/**
	 * 
	 */
	private static List<String> EXCLUDES_LIST = new ArrayList<String>();
	/**
	 * 
	 */
	@Override
	public List<String> getExcludeUris() {
		if (ListUtil.isEmpty(EXCLUDES_LIST)) {
			EXCLUDES_LIST.add("/usercentre/outLogin.do");// 退出
			EXCLUDES_LIST.add("/usercentre/supplier.do");// 获取供应商入驻链接
			EXCLUDES_LIST.add("/views/login.html");// 登录页面
			EXCLUDES_LIST.add("/views/dmplogin.html");// 数据分析平台登录页面
			EXCLUDES_LIST.add("/usercentre/userLogin.do");// 登录
			EXCLUDES_LIST.add("/usercentre/login.do");// 登录
			EXCLUDES_LIST.add("/usercentre/systemLogin.do");// 登录
			EXCLUDES_LIST.add("/code.do");// 获取验证码
			EXCLUDES_LIST.add("/usercentre/randomNum.do");//登录时获取时间戳
			EXCLUDES_LIST.add("/views/passport.html");// 忘记密码
			EXCLUDES_LIST.add("/forgetMessage.do");// 找回密码(获取验证码)
			EXCLUDES_LIST.add("/checkMessage.do");// 找回密码（验证码验证）
			EXCLUDES_LIST.add("/recoverPwd/recoverPwdPassword.do");// 找回密码（验证码验证）
			EXCLUDES_LIST.add("/question/getQuestion.do");//密保问题
			EXCLUDES_LIST.add("/question/checkSecurityAnswer.do");//密保问题找回密码验证
			EXCLUDES_LIST.add("/recoverPwd/forgetPassword.do");//密保问题找回密码验证
			EXCLUDES_LIST.add("/loginMessage/findUserMessage.do");//登录保护获取手机密码
			EXCLUDES_LIST.add("/loginMessage/userCheckMessage.do");//登录保护密码验证
			

			EXCLUDES_LIST.add("/app/login.do");// 登录
			EXCLUDES_LIST.add("/app/applogin.do");// 手机端登录
			EXCLUDES_LIST.add("/app/JBYlogin.do");// 手机端聚百优登录
			EXCLUDES_LIST.add("/app/recoverPassword.do");// app找回密码
			EXCLUDES_LIST.add("/app/checkMessage.do");// 检查验证码
			EXCLUDES_LIST.add("/app/getMessage.do");// app获取验证码
			EXCLUDES_LIST.add("/app/checkAccount.do");// 检查账号和手机号是否匹配
			EXCLUDES_LIST.add("/app/recoverPassword.do");// app找回密码
			

		}
		return EXCLUDES_LIST;
	}
}
