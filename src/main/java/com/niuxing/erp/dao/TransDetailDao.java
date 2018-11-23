package com.niuxing.erp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.TransDetailPo;
import com.zengfa.platform.data.hibernate.HibernateDao;

@Repository
public class TransDetailDao extends HibernateDao<TransDetailPo, Integer>{

	public List<TransDetailPo> findByTransId(Integer transId){
		return this.find("from TransDetailPo where transId=?", transId);
	}
}
