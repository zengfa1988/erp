package com.niuxing.erp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.PaymentPo;
import com.niuxing.erp.vo.PaymentVo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Result;

@Repository
public class PaymentDao extends HibernateDao<PaymentPo, Integer>{

	public Result findByPage(Result result,PaymentVo paymentVo, Integer pageNo, Integer pageSize){
		String hql = "from PaymentPo where 1=1";
		Map<String, Object> values = new HashMap<String, Object>();
		if (paymentVo != null && paymentVo.getStatus() != null) {
			hql += " and status=:status";
			values.put("status", paymentVo.getStatus());
		}
		if (paymentVo != null && paymentVo.getPaymentType() != null) {
			hql += " and paymentType=:paymentType";
			values.put("paymentType", paymentVo.getPaymentType());
		}
		if (paymentVo != null && paymentVo.getPaymentNo() != null) {
			hql += " and paymentNo=:paymentNo";
			values.put("paymentNo", paymentVo.getPaymentNo());
		}
		hql += " and status>0";
		hql += " order by createTime desc";
		// 分页查询
		Page<PaymentPo> page = new Page<PaymentPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		result.setData(this.findPage(page, hql, values));
		return result;
	}
	
	public PaymentPo getPaymentByNo(String paymentNo){
		String hql = "from PaymentPo where paymentNo=?";
		return this.findUnique(hql, paymentNo);
	}
	
	public List<PaymentPo> findByPurchaseId(Integer purchaseId){
		return this.find("from PaymentPo where purchaseId=?", purchaseId);
	}
}
