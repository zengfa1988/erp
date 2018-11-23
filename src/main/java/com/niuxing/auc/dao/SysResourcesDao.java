package com.niuxing.auc.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.niuxing.auc.po.SysResourcesPo;
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
public class SysResourcesDao extends HibernateDao<SysResourcesPo, Long>{

	private Pagination findPagination;
	
	public Result saveSysResources(Result result, SysResourcesPo sysResourcesPo) throws Exception {
		this.save(sysResourcesPo);
		result.setData(sysResourcesPo);
		return result;
	}
	
	/**
	 * 分页查询菜单资源
	 * @param result
	 * @param sysResourcesPo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Result findResourcesList(Result result, SysResourcesPo sysResourcesPo,Page<SysResourcesPo> page) throws Exception {
		String  hql="from SysResourcesPo where 1=1";
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotBlank(sysResourcesPo.getMenuName())){
			hql+="  and ( menuName like ?";
			params.add("%" + sysResourcesPo.getMenuName() + "%");
			hql+="  or id  in (select parentId from SysResourcesPo where  menuName like ?))";
			params.add("%" + sysResourcesPo.getMenuName() + "%");
		}
		hql+=" order by sort";
		
		findPagination = this.findPagination(page, hql,params.toArray());
		result.setData(findPagination);
		return result;
	}
	

	/**
	 * 分页查询菜单资源
	 * @param result
	 * @param sysResourcesPo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Object findResourcesList(SysResourcesPo sysResourcesPo) throws Exception {
		
		Query query = createQuery( "from SysResourcesPo " );
        query.setFirstResult( 0 );
        query.setMaxResults( 10 );
        return query.list();
        
        /*
		String  hql="from SysResourcesPo where 1=1";
		
		List<Object> params = new ArrayList<Object>();
		if(sysResourcesPo != null) {
			if(sysResourcesPo.getPrjId()!=null){
				hql+="  and( prjId=?";
				hql+="  or id  in (select parentId from SysResourcesPo where  prjId=?))";
				params.add(sysResourcesPo.getPrjId());
				params.add(sysResourcesPo.getPrjId());
			}
			if(StringUtils.isNotBlank(sysResourcesPo.getMenuName())){
				hql+="  and ( menuName like ?";
				params.add("%" + sysResourcesPo.getMenuName() + "%");
				hql+="  or id  in (select parentId from SysResourcesPo where  menuName like ?))";
				params.add("%" + sysResourcesPo.getMenuName() + "%");
			}
		}
		
		hql+=" order by sort";
		
		findPagination = this.findPagination(page, hql,params.toArray());
//		result.setData(findPagination);
//		return findPagination;
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List list = query.list();
		return list;
		*/
	}
	
	/**
	 * 更新菜单资源
	 * @param result
	 * @param sysResourcesPo
	 * @return
	 * @throws Exception
	 */
	public Result updateResources(Result result, SysResourcesPo sysResourcesPo) throws Exception {
		Object[] objects={sysResourcesPo.getId()};
		String hql=DaoUtil.updateEntityByCondition(sysResourcesPo,"id=?");
		int count = this.updateHql(hql,objects);
		result.setData(count);
		return result;
	}
	
	public Result getSysResources(Result result, Long id) throws Exception {
		Object[] objects={id};
		SysResourcesPo SysResourcesPo =this.findUnique("from SysResourcesPo where id=?",objects);
		result.setData(SysResourcesPo);
		return result;
	}
	
	/**
	 * 根据父id查询菜单
	 * @param result
	 * @param parentIds
	 * @return
	 * @throws Exception
	 */
	public Result getSysResourcesByParId(Result result, List<Long> parentIds) throws Exception {

		Query query =  this.createQuery("from SysResourcesPo where parentId in(:parentIds)");
		query.setParameterList("parentIds", parentIds);
		@SuppressWarnings("unchecked")
		List<SysResourcesPo> list = query.list();
		result.setData(list);
		return result;
	}
	
	/**
	 * 修改资源菜单状态
	 * @param result
	 * @param status
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result updateSysResourcesStatus(Result result,Integer status, Long id,Long userId) throws Exception {
		
		Object[] objects={status,userId.toString(),id};
		int i = this.updateSql("update  sys_resources  set status = ? ,update_by = ?,update_date=SYSDATE() where id = ? ", objects);
		result.setData(i);
		return result;
	}
	
	public Result delSysResources(Result result, Integer id) throws Exception {
		
		Object[] objects={id};
		int i = this.updateSql("delete from  sys_resources  where id = ?", objects);
		result.setData(i);
		return result;
	}
	
	public Result findResourcesListByPrjId(Result result, String prjId) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String  hql="from SysResourcesPo where status = 1 ";
		if(StringUtils.isNotBlank(prjId)){
			hql+=" and prjId = ?" ;
			params.add(Long.valueOf(prjId));
		}
		hql+=" order by sort";
		List<SysResourcesPo> list = this.find(hql,params.toArray());
		result.setData(list);
		return result;
	}

}
