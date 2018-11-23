package com.niuxing.erp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.TransProgressPo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Result;

@Repository
public class TransProgressDao extends HibernateDao<TransProgressPo, Integer>{

	public List<TransProgressPo> findByTransId(Integer transId){
		return this.find("from TransProgressPo where transId=? order by createTime desc", transId);
	}
	
	public Result delByTransId( Result result, Integer transId ) {
        int rows = deleteHql( "delete from TransProgressPo where transId = ?", transId );
        if ( rows > 0 ) {
            result.setData( true );
        } else {
            result.setData( false );
        }
        return result;
    }
}
