package com.niuxing.auc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.niuxing.auc.po.SysRolePo;
import com.niuxing.auc.vo.SysRoleVo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;

/**
 * 
 * @author ds
 *
 */
@Repository
public class SysRoleDao extends HibernateDao<SysRolePo, Integer> {

	private Pagination findPagination;
	/**
	 * 
	 * @param result
	 * @param sysRolePo
	 * @param roleId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Result querySysRole(Result result, SysRolePo sysRolePo,  Page<SysRolePo> page) throws Exception {
		StringBuffer hql = new StringBuffer(" from SysRolePo where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (null != sysRolePo.getBizId()) {
			hql.append(" and  bizId =? ");
			params.add(sysRolePo.getBizId());
		}

		if (StringUtils.isNotBlank(sysRolePo.getRoleName())) {
			hql.append(" and  roleName like ?  ");
			String roleName = sysRolePo.getRoleName();
			roleName = roleName.replace("%", "\\%");
			roleName = roleName.replace("_", "\\_");
			params.add("%" + roleName.trim() + "%");
		}
		if (null != sysRolePo.getRoleType() && sysRolePo.getRoleType() > 0) {
			hql.append(" and  roleType =? ");
			params.add(sysRolePo.getRoleType());
		}
		hql.append(" order by createDate desc ");
		findPagination = this.findPagination(page, hql.toString(), params.toArray());
		result.setData(findPagination);

		return result;
	}
	/**
	 * 
	 * @param result
	 * @param sysRolePo
	 * @return
	 * @throws Exception
	 */
	public Result saveSysRole(Result result, SysRolePo sysRolePo) throws Exception {
		this.save(sysRolePo);
		result.setData(sysRolePo);

		return result;
	}
	/**
	 * 
	 * @param result
	 * @param sysRolePo
	 * @return
	 * @throws Exception
	 */
	public Result updateSysRole(Result result, SysRolePo sysRolePo) throws Exception {
		this.update(sysRolePo);
		result.setData(sysRolePo);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result delSysRoleById(Result result, Long id) throws Exception {
		Object[] objects = { id };
		int delcount = this.updateSql("delete from sys_role  where id = ?", objects);
		if (delcount == 0) {
			result.setStatus(500);
			result.setMsg("删除失败!");
			result.setData(null);
			return result;
		}
		result.setData(delcount);
		return result;
	}
	
	/**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result getSysRoleById(Result result, Long id) throws Exception {
		SysRolePo sysRolePo = this.findUnique("from SysRolePo where id = ?", id);
		result.setData(sysRolePo);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param id
	 * @param bizId
	 * @return
	 * @throws Exception
	 */
	public Result querySysRoleById(Result result, Long id, Long bizId) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" from SysRolePo where 1=1 ");
//		if (bizId != null) {
//			hql.append(" and bizId =?");
//			params.add(bizId);
//		}
		if (null != id) {
			hql.append(" and id =? ");
			params.add(id);
		}
		List<SysRolePo> list = this.find(hql.toString(), params.toArray());
		if (null != list && list.size() > 0) {
			result.setData(list.get(0));
		} else {
			result.setData(null);
		}
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param roleType
	 * @return
	 * @throws Exception
	 */
	public Result getSysRoleById(Result result, Integer roleType) throws Exception {

		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" from SysRolePo where bizId=-2 ");
		if (roleType != null) {
			hql.append(" and roleType =?");
			params.add(roleType);
		}
		List<SysRolePo> list = this.find(hql.toString(), params.toArray());
		if (null != list && list.size() > 0) {
			result.setData(list.get(0));
		} else {
			result.setData(null);
		}
		return result;
	}
	/**
	 * 根据类型获取默认角色id
	 * @param result
	 * @param id
	 * @param bizId
	 * @param roleName
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Result checkSysRole(Result result, Long id, Long bizId, String roleName, int type) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" from SysRolePo where 1=1 ");

		hql.append(" and roleName = ?");
		params.add(roleName);
		hql.append(" and bizId =? ");
		params.add(bizId);
		if (type == 1) {
			// update
			hql.append(" and id != ?");
			params.add(id);
		}
		List<SysRolePo> list = this.find(hql.toString(), params.toArray());

		result.setData(null != list ? list.size() : 0);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param bizId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Result querySysRoleVo(Result result, Long bizId, Long roleId) throws Exception {
		StringBuffer hql = new StringBuffer(" from SysRolePo where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (null != bizId) {
			hql.append(" and  bizId = ?");
			params.add(bizId);
		}
		if (null != roleId) {
			hql.append(" and  id !=? ");
			params.add(roleId);
		}
		List<SysRolePo> list = this.find(hql.toString(), params.toArray());
		if (list != null) {
			List<SysRoleVo> lists = new ArrayList<SysRoleVo>();
			for (SysRolePo role : list) {
				SysRoleVo sysRoleVo = new SysRoleVo();
				PropertyUtils.copyProperties(sysRoleVo, role);
				lists.add(sysRoleVo);
			}
			result.setData(lists);
		} else {
			result.setData(null);
		}

		return result;
	}
	/**
	 * 
	 * @param result
	 * @param roleIds
	 * @param bizId
	 * @return
	 * @throws Exception
	 */
	public Result querySysRoleVoByIds(Result result, List<Integer> roleIds, Long bizId) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(" from SysRolePo where 1=1 ");
		if (null != bizId) {
			hql.append(" and  bizId = ?");
			params.add(bizId);
		}
		if (null != roleIds && roleIds.size() > 0) {
			hql.append(" and  id in (?)");
			for (Integer id : roleIds) {
				params.add(id);
			}

		}
		List<SysRolePo> list = this.find(hql.toString(), params.toArray());
		if (list != null) {
			List<SysRoleVo> lists = new ArrayList<SysRoleVo>();
			for (SysRolePo role : list) {
				SysRoleVo sysRoleVo = new SysRoleVo();
				PropertyUtils.copyProperties(sysRoleVo, role);
				lists.add(sysRoleVo);
			}
			result.setData(lists);
		} else {
			result.setData(null);
		}

		return result;
	}

	/**
	 * 批量查询用户和角色的关系
	 * @param result
	 * @param userIds
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Result selectRoleVoByUserIds(Result result, List<Long> userIds) throws Exception {
		Query query = this.createQuery("SELECT t2.userId,t1.roleName FROM SysRolePo t1, UserRolePo t2 WHERE t1.id=t2.roleId AND t2.userId  in(:idList)");
		query.setParameterList("idList", userIds);
		List list = query.list();

		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			if (map.containsKey(object[0].toString())) {
				map.put(object[0].toString(), map.get(object[0].toString()) + "," + object[1].toString());
			} else {
				map.put(object[0].toString(), object[1].toString());
			}
		}
		result.setData(map);
		return result;
	}
}
