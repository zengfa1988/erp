package com.niuxing.auc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.niuxing.auc.dao.SysLoginLogDao;
import com.niuxing.auc.dao.UserDao;
import com.niuxing.auc.po.SysLoginLogPo;
import com.niuxing.auc.po.UserPo;
import com.niuxing.auc.vo.SysLoginLogVo;
import com.niuxing.auc.vo.UserVo;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;


/**
 * 
 * @author ds
 * 
 */
@Service
public class LoginLogService {
	@Resource
	SysLoginLogDao sysLoginLogDao;
	@Resource
	UserDao userDao;

	/**
	 * 添加
	 * 
	 * @param result
	 * @param loginInfo
	 * @param loginMode
	 * @return
	 * @throws Exception
	 */
	@Async
	public Result saveSysLoginLog(Result result, UserInfo loginInfo, String loginMode) throws Exception {
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("参数为空！");
			result.setData(null);
			return result;
		}

		SysLoginLogPo sysLoginLogPo = new SysLoginLogPo();
		PropertyUtils.copyProperties(sysLoginLogPo, loginInfo);
		sysLoginLogPo.setLoginAddress("");
		sysLoginLogPo.setLoginMode(loginMode);
		sysLoginLogPo.setLoginDate(new Date());

		result = sysLoginLogDao.findSysLoginLogId(result, loginInfo.getUserId());
		List<Long> list = result.getData();

		if (list != null && list.size() > 98) {// 记录保留100条，超出后删除
			for (int i = 98; i < list.size(); i++) {
				Long id = list.get(i);
				result = sysLoginLogDao.delSysLoginLog(result, id);
			}
			result.setStatus(200);
		}
		result = sysLoginLogDao.saveSysLoginLog(result, sysLoginLogPo);
		return result;
	}

	/**
	 * 根据id查询20条记录
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result findSysLoginLog(Result result, Long userId) throws Exception {
		if (userId == null) {
			String msg = "参数为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		result = sysLoginLogDao.findSysLoginLog(result, userId);
		List<SysLoginLogPo> list = result.getData();
		List<SysLoginLogVo> listVo = new ArrayList<SysLoginLogVo>();
		SysLoginLogVo sysLoginLogVo = null;
		for (SysLoginLogPo sysLoginLogPo : list) {
			sysLoginLogVo = new SysLoginLogVo();
			if (StringUtils.isBlank(sysLoginLogPo.getLoginAddress())) {
//				sysLoginLogPo.setLoginAddress(FindIpUtil.getIpAddress(sysLoginLogPo.getLoginIp()));
			}
			PropertyUtils.copyProperties(sysLoginLogVo, sysLoginLogPo);

			listVo.add(sysLoginLogVo);

		}
		result.setData(listVo);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result getUserVo(Result result, Long id) throws Exception {
		UserPo user = (UserPo) userDao.getUser(result, id).getData();
		if (user == null) {
			result.setData(null);
			return result;
		}
		UserVo userVo = new UserVo();
		PropertyUtils.copyProperties(userVo, user);
		userVo.setId(id);
		userVo.setLoginName(user.getLoginName());
		userVo.setPhone(user.getPhone());
		userVo.setStatus(user.getStatus());
		userVo.setUserName(user.getUserName());
		userVo.setPassword(null);
		result.setData(userVo);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	public Result getUserVoByloginName(Result result, String loginName) throws Exception {
		UserPo user = (UserPo) userDao.getUserByLoginName(result, loginName).getData();
		if (user == null) {
			result.setData(null);
			return result;
		}

		UserVo userVo = new UserVo();
		PropertyUtils.copyProperties(userVo, user);
		userVo.setPassword(null);
		result.setData(userVo);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param id
	 * @param isProtection
	 * @return
	 * @throws Exception
	 */
	public Result updateProtectionLogin(Result result, Long id, int isProtection) throws Exception {
		UserPo user = (UserPo) userDao.getUser(result, id).getData();
		if (user == null) {
			result.setMsg("用户不存在");
			result.setStatus(500);
			return result;
		}
//		user.setIsProtection(isProtection);
		result.setData(null);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param sysLoginLogVo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Result findLoginLog(Result result, SysLoginLogVo sysLoginLogVo, Page<SysLoginLogPo> page) throws Exception {
		if (sysLoginLogVo == null) {
			result.setMsg("查询对象不能为空");
			result.setStatus(500);
			result.setData(null);
			throw new FunctionException(result, "查询不能为空");
		}
//		SysLoginLogPo sysLoginLogPo = new SysLoginLogPo();
//		PropertyUtils.copyProperties(sysLoginLogPo, sysLoginLogVo);
		result = sysLoginLogDao.findLoginLog(result, sysLoginLogVo, page);
		Pagination findPagination = result.getData();
		if (findPagination != null &&findPagination.getRows()!=null) {
			List<SysLoginLogPo> list = (List<SysLoginLogPo>) findPagination.getRows();
			for (SysLoginLogPo loginLogPo : list) {
				if (StringUtils.isBlank(loginLogPo.getLoginAddress())) {
//					loginLogPo.setLoginAddress(FindIpUtil.getIpAddress(loginLogPo.getLoginIp()));
				}
			}
		}
		return result;
	}
}

