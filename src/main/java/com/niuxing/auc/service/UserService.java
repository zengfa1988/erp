package com.niuxing.auc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.niuxing.auc.dao.SysRoleDao;
import com.niuxing.auc.dao.SysUserRoleDao;
import com.niuxing.auc.dao.UserDao;
import com.niuxing.auc.po.SysRolePo;
import com.niuxing.auc.po.SysUserRolePo;
import com.niuxing.auc.po.UserPo;
import com.niuxing.auc.vo.UserPageVo;
import com.niuxing.auc.vo.UserVo;
import com.niuxing.util.MessageResource;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.DigestUtils;
import com.zengfa.platform.util.security.UserInfo;

/**
 * 
 * @author ds
 * 
 */
@Service
public class UserService {

	@Resource
	UserDao userDao;
	@Resource
	SysRoleDao sysRoleDao;
	@Resource
	SysUserRoleService sysUserRoleService;
	@Resource
	SysUserRoleDao sysUserRoleDao;
	@Resource
	SysRoleResourcesService sysRoleResourcesService;

	protected static Logger logger = Logger.getLogger(UserService.class);
	/**
	 * 添加用户
	 * 
	 * @param result
	 * @param userPo
	 * @param userVo
	 * @param defaultpwd
	 * @return
	 * @throws Exception
	 */
	public Result saveUserInfo(Result result, UserPo userPo, UserVo userVo, UserInfo loginInfo, String defaultpwd) throws Exception {
		if (userPo == null) {
			String msg = "添加的用户信息为空";
			result.setStatus(500);
			msg = "用户信息为空";
			result.setMsg(msg);
			result.setData(null);
		}
		result = userDao.saveUser(result, userPo);
		if (userPo != null) {
			userPo.setLoginPwd(DigestUtils.sha1Hex(defaultpwd + userPo.getId()));
		}
		if (result.getStatus() == 200) {
			// 添加用户和角色关联关系
			SysUserRolePo sysUserRolePo = new SysUserRolePo();
			sysUserRolePo.setUserId(userPo.getId());
			sysUserRolePo.setRoleId(userVo.getRoleId());
			result = sysUserRoleService.saveSysUserRoleService(result, sysUserRolePo);

			if (StringUtils.isBlank(userVo.getGroupIds())) {
				return result;
			}

		}

		return result;
	}

	/**
	 * 根据id修改对象
	 * 
	 * @param result
	 * @param userVo
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result updateUser(Result result, UserVo userVo, Long userId) throws Exception {

		UserPo po = (UserPo) userDao.getUser(result, userVo.getId()).getData();
		if (po == null) {
			result.setStatus(500);
			result.setMsg("用户不存在");
			result.setData(null);
			return result;
		}
		if (userId != 1 && po.getIsManager() == true) {
			result.setStatus(500);
			result.setMsg("非法修改用户信息！");
			result.setData(null);
			return result;
		}
		po.setUserName(userVo.getUserName());
		po.setStatus(userVo.getStatus());
		po.setUpdateBy(userId);
		po.setUpdateDate(new Date());
		po.setPhone(userVo.getPhone());
		if (userId == 1) {// 修改用户不为1（超级管理员）不修改角色
			return result;
		}
		if (result.getStatus() == 200) {
			SysUserRolePo userRole = (SysUserRolePo) sysUserRoleService.findSysUserRoleService(result, userVo.getId()).getData();
			if (null == userRole) {
				SysUserRolePo sysUserRolePo = new SysUserRolePo();
				sysUserRolePo.setUserId(userVo.getId());
				sysUserRolePo.setRoleId(userVo.getRoleId());
				result = sysUserRoleService.saveSysUserRoleService(result, sysUserRolePo);
			} else {
				if (userRole.getRoleId() != null && !userRole.getRoleId().equals(userVo.getRoleId())) {
					// 不相同 则需要修改，用户和角色关联关系
					SysUserRolePo sysUserRolePo = new SysUserRolePo();
					sysUserRolePo.setUserId(userVo.getId());
					sysUserRolePo.setRoleId(userVo.getRoleId());
					result = sysUserRoleService.updateSysUserRole(result, sysUserRolePo);
				}
			}

			if (StringUtils.isBlank(userVo.getGroupIds())) {
				return result;
			}
		}
		return result;
	}

	/**
	 * 根基id修改用户状态 0. 删除 1.正常 2.冻结 3.解冻
	 * 
	 * @param result
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Result updateUserStatus(Result result, Long id, Integer status) throws Exception {
		result = userDao.updateStatus(result, status, id);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param userId
	 * @param status
	 * @param updateBy
	 * @return
	 * @throws Exception
	 */
	public Result frozenUserById(Result result, String userId, Integer status, Long updateBy) throws Exception {
		if (StringUtils.isBlank(userId)) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		result = userDao.updateUserStatus(result, Long.valueOf(userId), status, updateBy);
		return result;
	}

	/**
	 * 检验账号是否存在
	 * 
	 * @param result
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	public Result findUserIsRegister(Result result, String loginName) throws Exception {
		if (StringUtils.isBlank(loginName)) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			result.setData(null);
			return result;
		}
		return userDao.findUserIsRegister(result, loginName);
	}

	/**
	 * 重置密码
	 * 
	 * @param result
	 * @param newPassword
	 * @param id
	 * @param create_by
	 * @param bizId
	 * @return
	 * @throws Exception
	 */
	public Result resetUserPwd(Result result, String newPassword, Long id, Long updateUserId, Long bizId) throws Exception {
		result = userDao.getUser(result, id);
		if (result.getData() == null) {
			result.setStatus(500);
			String msg = "修改用户不存在！";
			result.setMsg(msg);
			return result;
		}
		UserPo userPo = result.getData();

		// 新密码加
		String digestPassword = DigestUtils.sha1Hex(newPassword + id);
		userPo.setLoginPwd(digestPassword);
		userPo.setUpdateBy(updateUserId);
		result.setData(userPo);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result findUserByUserId(Result result, Long id) throws Exception {
		if (null == id) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		UserPo us = (UserPo) userDao.findUserByUserId(result, id).getData();

		SysUserRolePo sysUserRolePo = new SysUserRolePo();
		// 获取用户和角色的关系
		result = sysUserRoleDao.findSysUserRole(result, id);

		List<SysUserRolePo> list = result.getData();
		if (list != null && list.size() > 0) {
			sysUserRolePo = list.get(0);
		}
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(id);
		UserVo vo = new UserVo();

		PropertyUtils.copyProperties(vo, us);
		vo.setRoleId(sysUserRolePo.getRoleId());
		
		SysRolePo sysRolePo = sysRoleDao.getSysRoleById(result,sysUserRolePo.getRoleId()).getData();
		vo.setRoleName(sysRolePo.getRoleName());

		result.setData(vo);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param userVo
	 * @param userId
	 * @param roleId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Result findUserVByUserVo(Result result, UserVo userVo, Long userId, Long roleId, Page<UserPo> page) throws Exception {
		result = userDao.findUserByUser(result, userVo, userId, roleId, page);
		Pagination pagination = (Pagination) result.getData();
		if (pagination==null||pagination.getTotal() <= 0) {
			return result;
		}
		List<UserPo> us = (List<UserPo>) pagination.getRows();
		List<UserPageVo> vos = new ArrayList<UserPageVo>();
		if(us==null||us.isEmpty()){
			return result;
		}
		// 收集用户id
		List<Long> userids = new ArrayList<Long>();
		for (UserPo u : us) {
			userids.add(u.getId());
		}
		
		// 获取用户和角色的关系
		Map<String, String> roleMap = (Map<String, String>) sysRoleDao.selectRoleVoByUserIds(result, userids).getData();
		
		for (UserPo u : us) {
			UserPageVo vo = new UserPageVo();
			PropertyUtils.copyProperties(vo, u);
			if (u.getIsManager()) {
					vo.setManager(1);
			} else {
					vo.setManager(0);
			}
			vo.setRoleName(roleMap.get(u.getId().toString()));
			if (!vos.contains(vo)) {
				vos.add(vo);
			}
		}
		pagination.setRows(vos);
		pagination.setTotal(pagination.getTotal());
		result.setData(pagination);
		return result;
	}

	/**
	 * 获取该用户的角色权限
	 * @param result
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Result queryRoleAuthorityByUserId(Result result, Long userId) throws Exception {
		if (userId == null) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		result = userDao.findUserByUserId(result, userId);
		UserPo userPo = result.getData();
		if (userPo == null) {
			result.setMsg("获取用户信息失败");
			result.setData(null);
			return result;
		}
		Long roleId = 0L;
		
		SysUserRolePo sysUserRolePo = new SysUserRolePo();
		// 获取用户和角色的关系
		result = sysUserRoleDao.findSysUserRole(result, userId);

		List<SysUserRolePo> list = result.getData();
		if (list != null && list.size() > 0) {
			sysUserRolePo = list.get(0);
		}
		roleId = sysUserRolePo.getRoleId();
		List<Long> rrlist = (List<Long>) sysRoleResourcesService.querySysRoleResourcesByRoleIdService(result, Long.valueOf(roleId), null).getData();

		result.setData(rrlist);
		return result;
	}

}
