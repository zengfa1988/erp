package com.niuxing.erp.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.PurchasePo;
import com.niuxing.erp.po.SupplierPo;
import com.niuxing.erp.vo.PurchaseVo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Result;

@Repository
public class PurchaseDao extends HibernateDao<PurchasePo, Integer>{

	public Result findByPage(Result result,PurchaseVo purchaseVo, Integer pageNo, Integer pageSize){
		String hql = "from PurchasePo where 1=1";
		Map<String, Object> values = new HashMap<String, Object>();
		if (purchaseVo != null && purchaseVo.getStatus() != null) {
			hql += " and status=:status";
			values.put("status", purchaseVo.getStatus());
		}
		if (purchaseVo != null && purchaseVo.getPurchaseNo() != null) {
			hql += " and purchaseNo=:purchaseNo";
			values.put("purchaseNo", purchaseVo.getPurchaseNo());
		}
		hql += " and status>0";
		hql += " order by createTime desc";
		// 分页查询
		Page<PurchasePo> page = new Page<PurchasePo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		result.setData(this.findPage(page, hql, values));
		return result;
	}
	
	public PurchasePo getPurchaseByNo(String purchaseNo){
		String hql = "from PurchasePo where purchaseNo=?";
		return this.findUnique(hql, purchaseNo);
	}
}
