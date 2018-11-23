package com.niuxing.erp.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.niuxing.erp.po.GoodsSkuPo;
import com.niuxing.erp.vo.GoodsSkuVo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Result;

@Repository
public class GoodsSkuDao extends HibernateDao<GoodsSkuPo, Integer>{

	public Result findByPage(Result result,GoodsSkuVo goodsSkuVo, Integer pageNo, Integer pageSize){
		String hql = "from GoodsSkuPo where 1=1";
		Map<String, Object> values = new HashMap<String, Object>();
		if (goodsSkuVo != null && goodsSkuVo.getStatus() != null) {
			hql += " and status=:status";
			values.put("status", goodsSkuVo.getStatus());
		}
		if (goodsSkuVo != null && goodsSkuVo.getSkuNo() != null) {
			hql += " and skuNo=:skuNo";
			values.put("skuNo", goodsSkuVo.getSkuNo());
		}
		if (goodsSkuVo != null && goodsSkuVo.getCategoryId() != null) {
			hql += " and categoryId=:categoryId";
			values.put("categoryId", goodsSkuVo.getCategoryId());
		}
		if (goodsSkuVo != null && goodsSkuVo.getGoodsName() != null) {
			hql += " and goodsName like :goodsName";
			values.put("goodsName", "%"+goodsSkuVo.getGoodsName()+"%");
		}
		hql += " order by createTime desc";
		// 分页查询
		Page<GoodsSkuPo> page = new Page<GoodsSkuPo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		result.setData(this.findPage(page, hql, values));
		return result;
	}
	
	public GoodsSkuPo getByNo(String skuNo){
		String hql = "from GoodsSkuPo where skuNo=?";
		return this.findUnique(hql, skuNo);
	}
	
}
