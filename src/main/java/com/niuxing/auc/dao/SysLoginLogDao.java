package com.niuxing.auc.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.niuxing.auc.po.SysLoginLogPo;
import com.niuxing.auc.vo.SysLoginLogVo;
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
public class SysLoginLogDao extends HibernateDao<SysLoginLogPo, Integer> {
	private Pagination findPagination;
	/**
	 * 
	 * @param result
	 * @param SysLoginLogPo
	 * @return
	 * @throws Exception
	 */
	public Result saveSysLoginLog(Result result, SysLoginLogPo SysLoginLogPo) throws Exception {
		this.save(SysLoginLogPo);
		result.setData(SysLoginLogPo);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Result findSysLoginLog(Result result, Long userId) throws Exception {
		String  hql="from SysLoginLogPo where userId=? order by loginDate desc";
		Object[] objects={userId};
		Page<SysLoginLogPo> page=new Page<SysLoginLogPo>();
		page.setPageNo(1);
		page.setPageSize(20);
		findPagination = this.findPagination(page, hql.toString(), objects);
		List<SysLoginLogPo> list= (List<SysLoginLogPo>) findPagination.getRows();
		result.setData(list);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result findSysLoginLogByUserIdTop2(Result result, Long userId) throws Exception {
		String  hql="from SysLoginLogPo where userId=? order by loginDate desc";
		Object[] objects={userId};
		Query query = this.createQuery(hql, objects);
		query.setMaxResults(2);
		@SuppressWarnings("unchecked")
		List<SysLoginLogPo> list = query.list();
		result.setData(list);
		return result;
	}
	/**
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result findSysLoginLogId(Result result, Long userId) throws Exception {
		String  hql="select  id from SysLoginLogPo where userId=? order by loginDate desc";
		Object[] objects={userId};
		List<Long> list= this.find(hql, objects);
		result.setData(list);
		return result;
	}

	/**
	 * 删除日志
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result delSysLoginLog(Result result, Long id) throws Exception {
		String  hql="delete from SysLoginLogPo where id=?";
		Object[] objects={id};
		int i = this.deleteHql(hql, objects);
		if(i<=0){
			result.setStatus(500);
			result.setMsg("删除失败!");
			result.setData(null);
			return result;
		}
		result.setData(i);
		return result;
	}
	/**
	 * 分页查询
	 * @param result
	 * @param sysLoginLogPo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Result findLoginLog(Result result, SysLoginLogVo sysLoginLogVo,Page<SysLoginLogPo> page) throws Exception {


		
		StringBuffer hql = new StringBuffer(" from SysLoginLogPo where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (null != sysLoginLogVo.getBelongId()) {
			hql.append(" and  belongId =? ");
			params.add(sysLoginLogVo.getBelongId());
		}

		if (StringUtils.isNotBlank(sysLoginLogVo.getLoginName())) {
			hql.append(" and  loginName = ?  ");
			String roleName = sysLoginLogVo.getLoginName();
			params.add(roleName.trim());
		}
		
		if (StringUtils.isNotBlank(sysLoginLogVo.getLoginMode())) {
			hql.append(" and  loginMode = ?  ");
			String loginMode = sysLoginLogVo.getLoginMode();
			params.add(loginMode.trim());
		}
		
		if (null != sysLoginLogVo.getStartDate()) {
			hql.append(" and  loginDate >= ?  ");
			params.add(sysLoginLogVo.getStartDate());
		}
		if (null != sysLoginLogVo.getEndDate()) {
			hql.append(" and  loginDate <= ?  ");
			params.add(sysLoginLogVo.getEndDate());
		}
	
		hql.append(" order by loginDate desc ");
		findPagination = this.findPagination(page, hql.toString(), params.toArray());
		result.setData(findPagination);
		return result;
	}
}
