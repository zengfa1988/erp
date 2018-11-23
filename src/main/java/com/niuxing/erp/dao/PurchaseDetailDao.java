package com.niuxing.erp.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.PurchaseDetailPo;
import com.niuxing.erp.vo.PurchaseSkuVo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.DateUtil;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;

@Repository
public class PurchaseDetailDao extends HibernateDao<PurchaseDetailPo, Integer>{

	public List<PurchaseDetailPo> findByPurchaseId(Integer purchaseId){
		return this.find("from PurchaseDetailPo where purchaseId=?", purchaseId);
	}
	
	public Result findByPurchaseIdSql(Result result,Integer purchaseId){
		String sql = "SELECT detail.id,sku.sku_no,sku.pics,sku.goods_name,s.supplier_no,detail.last_price,detail.purchase_num,detail.purchase_price,detail.delivery_date,detail.target_store FROM `purchase_detail` as detail "
				+ "LEFT JOIN goods_sku as sku ON detail.sku_id=sku.ID "
				+ "LEFT JOIN supplier as s ON sku.supplier_id=s.id "
				+ "where detail.purchase_id="+purchaseId;
		List list = this.queryForList(sql);
		if (CollectionUtils.isEmpty(list)) {
			result.setData(null);
			return result;
		}
		List<PurchaseSkuVo> voList = new ArrayList<PurchaseSkuVo>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			PurchaseSkuVo vo = new PurchaseSkuVo();
			String skuNo = null;
			if(map.get("sku_no") != null){
				skuNo = (String) map.get("sku_no");
			}
			String supplierNo = null;
			if(map.get("supplier_no") != null){
				supplierNo = (String) map.get("supplier_no");
			}
			String goodsName = null;
			if( map.get("goods_name") != null){
				goodsName = (String) map.get("goods_name");
			}
			String pics = null;
			if(map.get("pics") != null){
				pics = (String) map.get("pics");
			}
			String deliveryDate = null;
			if(map.get("delivery_date") != null){
				Date _deliveryDate = (Date) map.get("delivery_date");
				deliveryDate = DateUtil.getDate(_deliveryDate);
			}
			Integer purchaseNum = null;
			if(map.get("purchase_num") != null){
				purchaseNum = (Integer) map.get("purchase_num");
			}
			Integer skuId = null;
			if(map.get("id") != null){
				skuId = (Integer) map.get("id");
			}
			Integer targetStore = null;
			if(map.get("target_store") != null){
				targetStore = (Integer) map.get("target_store");
			}
			Double purchasePrice = null;
			if(map.get("purchase_price") != null){
				purchasePrice = ((BigDecimal) map.get("purchase_price")).doubleValue();
			}
			Double lastPrice = null;
			if(map.get("last_price") != null){
				lastPrice = ((BigDecimal) map.get("last_price")).doubleValue();
			}
			vo.setSkuId(skuId);
			vo.setPurchaseNum(purchaseNum);
			vo.setPurchasePrice(purchasePrice);
			vo.setDeliveryDate(deliveryDate);
			vo.setSkuNo(skuNo);
			vo.setSupplierNo(supplierNo);
			vo.setGoodsName(goodsName);
			vo.setLastPrice(lastPrice);
			vo.setPics(pics);
			vo.setTargetStore(targetStore);
			
			voList.add(vo);
		}
		
//		SQLQuery query = getSession().createSQLQuery(sql.toString());
//		query.addScalar("sku_no", StandardBasicTypes.STRING);
//		query.addScalar("pics", StandardBasicTypes.STRING);
//		query.addScalar("goods_name", StandardBasicTypes.STRING);
//		query.addScalar("supplier_no", StandardBasicTypes.STRING);
//		query.addScalar("last_price", StandardBasicTypes.DOUBLE);
//		query.addScalar("purchase_num", StandardBasicTypes.INTEGER);
//		query.addScalar("purchase_price", StandardBasicTypes.DOUBLE);
//		query.addScalar("delivery_date", StandardBasicTypes.TIMESTAMP);
//		query.addScalar("target_store", StandardBasicTypes.INTEGER);
//		query.setResultTransformer(Transformers.aliasToBean(PurchaseSkuVo.class));
//		result.setData(query.list());
		result.setData(voList);
		return result;
	}
	
	public Result findByPage(Result result,PurchaseSkuVo purchaseSkuVo, Integer pageNo, Integer pageSize){
		String sql = "SELECT p.purchase_no purchaseNo,p.id purchaseId,pd.target_store targetStore,gs.sku_no skuNo,gs.id skuId,gs.goods_name goodsName,su.supplier_no supplierNo,su.id supplierId,pd.purchase_num purchaseNum,pd.purchase_price purchasePrice FROM `purchase_detail` AS pd "
				+ "LEFT JOIN purchase as p ON pd.purchase_id=p.id "
				+ "LEFT JOIN goods_sku as gs ON pd.sku_id=gs.ID "
				+ "LEFT JOIN supplier as su ON gs.supplier_id=su.id "
				+ "WHERE 1=1 ";
		if(purchaseSkuVo != null){
			if(StringUtils.isNotBlank(purchaseSkuVo.getPurchaseNo())){
				sql += " and p.purchase_no='"+purchaseSkuVo.getPurchaseNo()+"'";
			}
			if(StringUtils.isNotBlank(purchaseSkuVo.getSkuNo())){
				sql += " and gs.sku_no='"+purchaseSkuVo.getSkuNo()+"'";
			}
			if(StringUtils.isNotBlank(purchaseSkuVo.getGoodsName())){
				sql += " and gs.goods_name like '%"+purchaseSkuVo.getGoodsName()+"%'";
			}
		}
		sql += "order by pd.create_time desc";
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.addScalar("purchaseId", StandardBasicTypes.INTEGER);
		query.addScalar("purchaseNo", StandardBasicTypes.STRING);
		query.addScalar("skuId", StandardBasicTypes.INTEGER);
		query.addScalar("skuNo", StandardBasicTypes.STRING);
		query.addScalar("goodsName", StandardBasicTypes.STRING);
		query.addScalar("supplierId", StandardBasicTypes.INTEGER);
		query.addScalar("supplierNo", StandardBasicTypes.STRING);
		query.addScalar("purchaseNum", StandardBasicTypes.INTEGER);
		query.addScalar("purchasePrice", StandardBasicTypes.DOUBLE);
		query.addScalar("targetStore", StandardBasicTypes.INTEGER);
		query.setResultTransformer(Transformers.aliasToBean(PurchaseSkuVo.class));
		query.setFirstResult( (pageNo - 1) * pageSize );
		query.setMaxResults( pageSize );
        
		// 查询总数
        String fromSql = sql.toString();
        fromSql = "from " + StringUtils.substringAfter( fromSql, "FROM" );
        fromSql = StringUtils.substringBefore( fromSql, "order by" );
        String countSql = "select count(*) ".concat( fromSql );
        SQLQuery countQuery = this.getSession().createSQLQuery( countSql );
//        countQuery.setProperties( queryVo );
        BigInteger totalCount = (BigInteger)countQuery.uniqueResult();
        
        result.setData( Pagination.createPagination( totalCount.longValue(), query.list() ) );
//		result.setData(query.list());
		return result;
		
		/*
		String hql = "from PurchaseDetailPo where 1=1";
		Map<String, Object> values = new HashMap<String, Object>();
		if (purchaseSkuVo != null && purchaseSkuVo.getPurchaseNo() != null) {
			hql += " and purchaseNo=:purchaseNo";
			values.put("purchaseNo", purchaseSkuVo.getPurchaseNo());
		}
		if (purchaseSkuVo != null && purchaseSkuVo.getPurchaseNo() != null) {
			hql += " and purchaseNo=:purchaseNo";
			values.put("purchaseNo", purchaseSkuVo.getPurchaseNo());
		}
		hql += " and status>0";
		hql += " order by createTime desc";
		// 分页查询
		Page<PurchasePo> page = new Page<PurchasePo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		result.setData(this.findPage(page, hql, values));
		return result;
		*/
	}
}
