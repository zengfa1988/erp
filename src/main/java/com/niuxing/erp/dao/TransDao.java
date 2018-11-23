package com.niuxing.erp.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.PurchasePo;
import com.niuxing.erp.po.TransPo;
import com.niuxing.erp.vo.TransVo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Result;

@Repository
public class TransDao extends HibernateDao<TransPo, Integer>{

	public Result findByPage(Result result,TransVo transVo, Integer pageNo, Integer pageSize){
		String hql = "from TransPo where 1=1";
		Map<String, Object> values = new HashMap<String, Object>();
		if (transVo != null && transVo.getStatus() != null) {
			hql += " and status=:status";
			values.put("status", transVo.getStatus());
		}
		if (transVo != null && transVo.getTransNo() != null) {
			hql += " and transNo=:transNo";
			values.put("transNo", transVo.getTransNo());
		}
		if (transVo != null && transVo.getPurchaseNo() != null) {
			hql += " and purchaseNo=:purchaseNo";
			values.put("purchaseNo", transVo.getPurchaseNo());
		}
		hql += " and status>0";
		hql += " order by createTime desc";
		// 分页查询
		Page<TransPo> page = new Page<TransPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		result.setData(this.findPage(page, hql, values));
		return result;
	}
	
	public TransPo getByNo(String transNo){
		String hql = "from TransPo where transNo=?";
		return this.findUnique(hql, transNo);
	}
}
