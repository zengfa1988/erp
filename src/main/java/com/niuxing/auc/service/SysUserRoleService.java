package com.niuxing.auc.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.niuxing.auc.dao.SysUserRoleDao;
import com.niuxing.auc.po.SysUserRolePo;
import com.niuxing.util.MessageResource;
import com.niuxing.util.StringTools;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;

/**
 * 
 * @author ds
 *
 */
@Service
public class SysUserRoleService {

	@Resource
	SysUserRoleDao sysUserRoleDao;
	/**
	 * 
	 * @param result
	 * @param sysUserRolePo
	 * @return
	 * @throws Exception
	 */
	public Result saveSysUserRoleService(Result result, SysUserRolePo sysUserRolePo) throws Exception {
		String msg = StringTools.checkEntityProperty(sysUserRolePo);
		if (!StringUtils.isBlank(msg)) {
			result.setMsg("添加信息，对象为" + sysUserRolePo);
			throw new FunctionException(result, "");
		}
		return sysUserRoleDao.saveSysUserRole(result, sysUserRolePo);
	}
	/**
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result findSysUserRoleService(Result result, Long userId) throws Exception {
		if (null == userId) {
			result.setMsg(MessageResource.PARAMETERS_ERROR);
			throw new FunctionException(result, "");
		}
		result = sysUserRoleDao.findSysUserRole(result, userId);
		List<SysUserRolePo> list = result.getData();
		if (null != list && list.size() > 0) {
			result.setData(list.get(0));
		}
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param sysUserRolePo
	 * @return
	 * @throws Exception
	 */
	public Result updateSysUserRole(Result result, SysUserRolePo sysUserRolePo) throws Exception {
		return sysUserRoleDao.updateSysUserRole(result, sysUserRolePo);
	}

	/**
	 * 
	 * @param result
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Result findSysUserNumByRoleId(Result result, Long roleId) throws Exception {
		return sysUserRoleDao.findSysUserNumByRoleId(result, roleId);
	}
	
	/**
	 * 获得用户最大的权限
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Integer getUserRoleType(Result result, Long userId) {
		try {
			result = findSysUserRoleService(result, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SysUserRolePo sysUserRolePo = result.getData();
		if (sysUserRolePo == null || sysUserRolePo.getRoleId() == null) {
			return 0;
		}
		return sysUserRolePo.getRoleId().intValue();
	}
}
