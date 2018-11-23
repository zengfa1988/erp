package com.niuxing.auc.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.niuxing.auc.po.SysRoleResourcesPo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Result;

/**
 * 
 * @author ds
 *
 */
@Repository
public class SysRoleResourcesDao extends HibernateDao<SysRoleResourcesPo, Integer> {
	/**
	 * 
	 * @param result
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Result querySysRoleResourcesByRoleId(Result result, Long roleId) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select resourcesId from SysRoleResourcesPo where 1=1");
		if (null != roleId && roleId > 0) {
			hql.append(" and  roleId =? ");
			params.add(roleId);
		}
		List<Long> find = this.find(hql.toString(), params.toArray());
		result.setData(find);

		return result;
	}
	/**
	 * 
	 * @param result
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	public Result querySysRoleResourcesByRoleIds(Result result, String roleIds) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select resourcesId from SysRoleResourcesPo where 1=1");
		if (!StringUtils.isBlank(roleIds)) {
			hql.append(" and  roleId in (?)");
			params.add(roleIds);
		}
		List<Long> find = this.find(hql.toString(), params.toArray());
		result.setData(find);

		return result;
	}

	/**
	 * 
	 * @param result
	 * @param poList
	 * @return
	 * @throws Exception
	 */
	public Result saveSysRoleResourcesList(Result result, List<SysRoleResourcesPo> poList) throws Exception {
		this.batchSave(poList);
		result.setData(poList);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Result delSysRoleResources(Result result, Long roleId) throws Exception {
		Object[] objects = { roleId };
		int delcount = this.updateSql("delete from sys_role_resources  where role_id = ?", objects);
		result.setData(delcount);
		return result;
	}
}
