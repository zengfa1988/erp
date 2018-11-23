package com.niuxing.auc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.niuxing.auc.po.SysUserRolePo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Result;

/**
 * 
 * @author ds
 *
 */
@Repository
public class SysUserRoleDao extends HibernateDao<SysUserRolePo, Integer> {
	/**
	 * 
	 * @param result
	 * @param sysUserRolePo
	 * @return
	 * @throws Exception
	 */
	public Result saveSysUserRole(Result result, SysUserRolePo sysUserRolePo) throws Exception {
		this.save(sysUserRolePo);
		result.setData(sysUserRolePo);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result findSysUserRole(Result result, Long userId) throws Exception {
		Object[] objects = { userId };
		StringBuffer hql = new StringBuffer("from SysUserRolePo where userId =? ");
		List<SysUserRolePo> list = this.find(hql.toString(), objects);
		result.setData(list);
		return result;
	}

	/**
	 * 根据登录名称查询用户数据.
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result getUserRoleByUserId(Result result, Long userId) throws Exception {
		Object[] objects = { userId };
		String hql = " from SysUserRolePo  where  userId=?";
		List<SysUserRolePo> list = this.find(hql, objects);
		result.setData(list);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public Result findSysUserNumByRoleId(Result result) throws Exception {
		StringBuffer hql = new StringBuffer("select roleId,count(1) from SysUserRolePo  GROUP BY roleId");
		List<Object> list = this.find(hql.toString());
		Map<String, Long> map = new HashMap<String, Long>();
		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			map.put(object[0].toString(), Long.valueOf(object[1].toString()));
		}
		result.setData(map);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Result findSysUserNumByRoleId(Result result, Long roleId) throws Exception {
		Object[] objects = { roleId };
		StringBuffer hql = new StringBuffer("select count(1) from SysUserRolePo where roleId = ?");
		Long findUnique = this.findUnique(hql.toString(), objects);
		result.setData(findUnique);
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
		Object[] objects = { sysUserRolePo.getRoleId(), sysUserRolePo.getUserId() };
		StringBuffer hql = new StringBuffer("update SysUserRolePo set roleId = ? where userId = ?");
		int updateHql = this.updateHql(hql.toString(), objects);
		result.setData(updateHql);
		return result;
	}
}
