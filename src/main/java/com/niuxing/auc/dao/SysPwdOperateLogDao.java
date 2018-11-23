package com.niuxing.auc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niuxing.auc.po.SysPwdOperateLogPo;
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
public class SysPwdOperateLogDao extends HibernateDao<SysPwdOperateLogPo, Integer> {
	private Pagination findPagination;
	/**
	 * 
	 * @param result
	 * @param SysPwdOperateLogPo
	 * @return
	 * @throws Exception
	 */
	public Result saveSysPwdOperateLog(Result result, SysPwdOperateLogPo SysPwdOperateLogPo) throws Exception {
		this.save(SysPwdOperateLogPo);
		result.setData(SysPwdOperateLogPo);
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
	public Result findSysPwdOperateLog(Result result, Long userId) throws Exception {
		String  hql="from SysPwdOperateLogPo where userId=? order by operateDate desc";
		Object[] objects={userId};
		Page<SysPwdOperateLogPo> page=new Page<SysPwdOperateLogPo>();
		page.setPageNo(1);
		page.setPageSize(20);
		findPagination = this.findPagination(page, hql.toString(), objects);
		List<SysPwdOperateLogPo> list= (List<SysPwdOperateLogPo>) findPagination.getRows();
		result.setData(list);
		return result;
	}
	
	public Result findSysPwdOperateLog(Result result, String loginName) throws Exception {
		String  hql="from SysPwdOperateLogPo where loginName=? order by operateDate desc";
		Object[] objects={loginName};
		Page<SysPwdOperateLogPo> page=new Page<SysPwdOperateLogPo>();
		page.setPageNo(1);
		page.setPageSize(20);
		findPagination = this.findPagination(page, hql.toString(), objects);
		List<SysPwdOperateLogPo> list= (List<SysPwdOperateLogPo>) findPagination.getRows();
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
	public Result findSysPwdOperateLogId(Result result, Long userId) throws Exception {
		String  hql="from SysPwdOperateLogPo where userId=? order by operateDate desc";
		Object[] objects={userId};
		
		List<Long> list= this.find(hql, objects);
		result.setData(list);
		return result;
	}

	/**
	 * 删除
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result delSysPwdOperateLog(Result result, Long id) throws Exception {
		String  hql="delete from SysPwdOperateLogPo where id=?";
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

}
