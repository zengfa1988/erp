package com.niuxing.erp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niuxing.erp.dao.GoodsCategoryDao;
import com.niuxing.erp.dao.GoodsSkuDao;
import com.niuxing.erp.dao.SupplierDao;
import com.niuxing.erp.po.GoodsCategoryPo;
import com.niuxing.erp.po.GoodsSkuPo;
import com.niuxing.erp.po.SupplierPo;
import com.niuxing.erp.vo.GoodsSkuVo;
import com.niuxing.erp.vo.ImgVo;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;

@Service
public class GoodsSkuService {

	@Autowired
	private GoodsSkuDao goodsSkuDao;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private GoodsCategoryDao goodsCategoryDao;
	
	public Result getListPage(Result result,GoodsSkuVo goodsSkuVo,Integer page,Integer rows) throws Exception{
		result = goodsSkuDao.findByPage(result, goodsSkuVo,page, rows);
		Page dataPage = (Page)result.getData();
		List<GoodsSkuPo> poList = dataPage.getData();
		if(CollectionUtils.isEmpty(poList)){
			Pagination pagination = Pagination.createPagination(0, ListUtils.EMPTY_LIST);
			result.setData(pagination);
			return result;
		}
		List<Integer> idList = new ArrayList<Integer>();
		List<Long> supplierIdList = new ArrayList<Long>();
		List<Integer> categoryIdList = new ArrayList<Integer>();
		for(GoodsSkuPo po : poList){
			if(po.getSupplierId() != null){
				supplierIdList.add(po.getSupplierId().longValue());
			}
			if(po.getCategoryId() != null){
				categoryIdList.add(po.getCategoryId());
			}
			idList.add(po.getId());
		}
		Map<Integer,SupplierPo> supplierMap = new HashMap<Integer,SupplierPo>();
		List<SupplierPo> supplierList = supplierDao.findByIds(result,supplierIdList).getData();
		if(CollectionUtils.isNotEmpty(supplierList)){
			for(SupplierPo po : supplierList){
				supplierMap.put(po.getId().intValue(), po);
			}
		}
		Map<Integer,GoodsCategoryPo> categoryMap = new HashMap<Integer,GoodsCategoryPo>();
		List<GoodsCategoryPo> categoryList = goodsCategoryDao.findByIds(result, categoryIdList).getData();
		if(CollectionUtils.isNotEmpty(categoryList)){
			for(GoodsCategoryPo po : categoryList){
				categoryMap.put(po.getId(), po);
			}
		}
		
		List<GoodsSkuVo> voList = new ArrayList<GoodsSkuVo>();
		for(GoodsSkuPo po : poList){
			GoodsSkuVo vo = new GoodsSkuVo();
			PropertyUtils.copyProperties(vo, po);
			if(po.getCategoryId() != null){
				GoodsCategoryPo catPo = categoryMap.get(po.getCategoryId());
				if(catPo != null){
					vo.setCategoryName(catPo.getCategoryName());
				}
			}
			if(po.getSupplierId() != null){
				SupplierPo supplierPo = supplierMap.get(po.getSupplierId());
				if(supplierPo != null){
					vo.setSupplierNo(supplierPo.getSupplierNo());
				}
			}
			voList.add(vo);
		}
		
		Pagination pagination = Pagination.createPagination(dataPage.getTotalCount(), voList);
		result.setData(pagination);
		return result;
	}
	
	public Result addSupplier(Result result,GoodsSkuVo goodsSkuVo,List<ImgVo> imgVos) throws Exception{
		UserInfo userInfo = result.getUserInfo();
		String skuNo = sequenceService.getNextSeq(2,true);
		//判断供应商编码是否重复
		GoodsSkuPo goodsSkuPo = goodsSkuDao.getByNo(skuNo);
		while(goodsSkuPo != null){
			throw new FunctionException(result, "sku编号生成失败!");
		}
		goodsSkuPo = new GoodsSkuPo();
		PropertyUtils.copyProperties(goodsSkuPo, goodsSkuVo);
		goodsSkuPo.setSkuNo(skuNo);
		goodsSkuPo.setStatus(1);
		goodsSkuPo.setCreateName(userInfo.getUserName());
		goodsSkuPo.setCreateTime(new Date());
		
		String imgStr = "";
		if(CollectionUtils.isNotEmpty(imgVos)){
			for(ImgVo imgVo : imgVos){
				if(!"".equals(imgStr)){
					imgStr += ",";
				}
				imgStr += imgVo.getImgUrl();
			}
		}
		goodsSkuPo.setPics(imgStr);
		goodsSkuDao.save(goodsSkuPo);
		
		return result;
	}
	
	public Result getSku(Result result,Integer id) throws Exception{
		GoodsSkuPo po = goodsSkuDao.get(id);
		GoodsSkuVo vo = new GoodsSkuVo();
		PropertyUtils.copyProperties(vo, po);
		result.setData(vo);
		return result;
	}
	
	public Result editSupplier(Result result,GoodsSkuVo goodsSkuVo,List<ImgVo> imgVos) throws Exception{
		GoodsSkuPo goodsSkuPo = goodsSkuDao.get(goodsSkuVo.getId());
		if(goodsSkuPo == null){
			throw new FunctionException(result, "获取sku信息失败!");
		}
		Integer id = goodsSkuVo.getId();
		String skuNo = goodsSkuPo.getSkuNo();
		Integer status = goodsSkuPo.getStatus();
		String name = goodsSkuPo.getCreateName();
		Date date = goodsSkuPo.getCreateTime();
		PropertyUtils.copyProperties(goodsSkuPo, goodsSkuVo);
		goodsSkuPo.setId(id);
		goodsSkuPo.setSkuNo(skuNo);
		goodsSkuPo.setStatus(status);
		goodsSkuPo.setCreateName(name);
		goodsSkuPo.setCreateTime(date);
		
		String imgStr = "";
		if(CollectionUtils.isNotEmpty(imgVos)){
			for(ImgVo imgVo : imgVos){
				if(!"".equals(imgStr)){
					imgStr += ",";
				}
				imgStr += imgVo.getImgUrl();
			}
		}
		goodsSkuPo.setPics(imgStr);
		goodsSkuDao.update(goodsSkuPo);
		
		return result;
	}
	
	public Result changeStatus(Result result,Integer id,Integer status,Long userId) throws Exception{
		GoodsSkuPo goodsSkuPo = goodsSkuDao.get(id);
		if(goodsSkuPo==null){
			throw new FunctionException(result, "获取sku信息失败!");
		}
		goodsSkuPo.setStatus(status);
		supplierDao.update(goodsSkuPo);
		return result;
	}
	
	public Result importData(Result result,List<List<String>> dataList){
		UserInfo userInfo = result.getUserInfo();
		if(CollectionUtils.isEmpty(dataList)){
			return result;
		}
		for(List<String> data : dataList){
			if(CollectionUtils.isEmpty(data)){
				continue;
			}
			String skuNo = sequenceService.getNextSeq(2,true);
			GoodsSkuPo goodsSkuPo = goodsSkuDao.getByNo(skuNo);
			while(goodsSkuPo != null){
				continue;
			}
			String goodsName = data.get(0);
			String partNumber = data.size()>1?data.get(1):null;
			if(StringUtils.isEmpty(goodsName)){
				continue;
			}
			
			goodsSkuPo = new GoodsSkuPo();
			goodsSkuPo.setSkuNo(skuNo);
			goodsSkuPo.setGoodsName(goodsName);
			goodsSkuPo.setPartNumber(partNumber);
			goodsSkuPo.setStatus(1);
			goodsSkuPo.setCreateName(userInfo.getUserName());
			goodsSkuPo.setCreateTime(new Date());
			goodsSkuPo.setPics("");
			goodsSkuDao.save(goodsSkuPo);
		}
		
		return result;
	}
	
}
