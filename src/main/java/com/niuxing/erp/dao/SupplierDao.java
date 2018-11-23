package com.niuxing.erp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.SupplierPo;
import com.niuxing.erp.vo.SupplierVo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Result;

@Repository
public class SupplierDao extends HibernateDao<SupplierPo, Long>{

	/**
	 * 分页获得供应商列表
	 * @param result
	 * @param supplierVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Result findByPage(Result result,SupplierVo supplierVo, Integer pageNo, Integer pageSize){
		String hql = "from SupplierPo where 1=1";
		Map<String, Object> values = new HashMap<String, Object>();
		if (supplierVo != null && supplierVo.getStatus() != null) {
			hql += " and status=:status";
			values.put("status", supplierVo.getStatus());
		}
		if (supplierVo != null && supplierVo.getContactPhone() != null) {
			hql += " and contactPhone=:contactPhone";
			values.put("contactPhone", supplierVo.getContactPhone());
		}
		if (supplierVo != null && supplierVo.getSupplierNo() != null) {
			hql += " and supplierNo=:supplierNo";
			values.put("supplierNo", supplierVo.getSupplierNo());
		}
		if (supplierVo != null && supplierVo.getSupplierName() != null) {
			hql += " and supplierName like :supplierName";
			values.put("supplierName", "%"+supplierVo.getSupplierName()+"%");
		}
//		if (supplierVo != null && supplierVo.getContactName() != null) {
//			hql += " and (contactName=:contactName or supplierName=:supplierName or supplierNo=:supplierNo)";
//			values.put("contactName", supplierVo.getContactName());
//			values.put("supplierName", supplierVo.getContactName());
//			values.put("supplierNo", supplierVo.getContactName());
//		}
		hql += " order by createDate desc";
		// 分页查询
		Page<SupplierPo> page = new Page<SupplierPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		result.setData(this.findPage(page, hql, values));
		return result;
	}
	
	public SupplierPo getSupplierByNo(String supplierNo){
		String hql = "from SupplierPo where supplierNo=?";
		return this.findUnique(hql, supplierNo);
	}
	
	public Result findAvaliableList(Result result){
		String hql = "from SupplierPo where status=1";
		result.setData(this.find(hql));
		return result;
	}
	
	public Result findByIds( Result result, List<Long> idList ) {
        Criteria criteria = createCriteria( SupplierPo.class );
        criteria.add( Restrictions.in( "id", idList ) );
        result.setData( criteria.list() );
        return result;
    }
	
}
