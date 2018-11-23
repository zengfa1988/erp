package com.niuxing.erp.dao;

import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.SequencePo;
import com.zengfa.platform.data.hibernate.HibernateDao;

@Repository
public class SequenceDao extends HibernateDao<SequencePo, Integer>{

}
