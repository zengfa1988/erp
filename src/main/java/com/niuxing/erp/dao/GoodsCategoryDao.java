package com.niuxing.erp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.GoodsCategoryPo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Result;

@Repository
public class GoodsCategoryDao extends HibernateDao<GoodsCategoryPo, Integer>{

	public Result findByPage(Result result,Integer pageNo, Integer pageSize){
		String hql = "from GoodsCategoryPo where 1=1";
		hql += " order by createDate desc";
		// 分页查询
		Page<GoodsCategoryPo> page = new Page<GoodsCategoryPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		result.setData(this.findPage(page, hql));
		return result;
	}
	
	public Result findAvaliableList(Result result){
		String hql = "from GoodsCategoryPo";
		result.setData(this.find(hql));
		return result;
	}
	
	public Result findByIds( Result result, List<Integer> idList ) {
        Criteria criteria = createCriteria( GoodsCategoryPo.class );
        criteria.add( Restrictions.in( "id", idList ) );
        result.setData( criteria.list() );
        return result;
    }
}
