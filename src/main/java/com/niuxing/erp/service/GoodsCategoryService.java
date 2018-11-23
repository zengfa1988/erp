package com.niuxing.erp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niuxing.erp.dao.GoodsCategoryDao;
import com.niuxing.erp.po.GoodsCategoryPo;
import com.niuxing.erp.vo.GoodsCategoryVo;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;

@Service
public class GoodsCategoryService {

	@Autowired
	private GoodsCategoryDao goodsCategoryDao;
	
	public Result getListPage(Result result,Integer page,Integer rows) throws Exception{
		result = goodsCategoryDao.findByPage(result, page, rows);
		Page dataPage = (Page)result.getData();
		List<GoodsCategoryPo> poList = dataPage.getData();
		if(CollectionUtils.isEmpty(poList)){
			Pagination pagination = Pagination.createPagination(0, ListUtils.EMPTY_LIST);
			result.setData(pagination);
			return result;
		}
		
		List<GoodsCategoryVo> voList = new ArrayList<GoodsCategoryVo>();
		for(GoodsCategoryPo po : poList){
			GoodsCategoryVo vo = new GoodsCategoryVo();
			PropertyUtils.copyProperties(vo, po);
			voList.add(vo);
		}
		
		Pagination pagination = Pagination.createPagination(dataPage.getTotalCount(), voList);
		result.setData(pagination);
		return result;
	}
	
	public Result addCategory(Result result,GoodsCategoryVo goodsCategoryVo) throws Exception{
		UserInfo userInfo = result.getUserInfo();
		GoodsCategoryPo goodsCategoryPo = new GoodsCategoryPo();
		PropertyUtils.copyProperties(goodsCategoryPo, goodsCategoryVo);
		goodsCategoryPo.setCreateName(userInfo.getUserName());
		goodsCategoryPo.setCreateDate(new Date());
		goodsCategoryDao.save(goodsCategoryPo);
		return result;
	}
	
	public Result editCategory(Result result,GoodsCategoryVo goodsCategoryVo) throws Exception{
		GoodsCategoryPo po = goodsCategoryDao.get(goodsCategoryVo.getId());
		if(po==null){
			throw new FunctionException(result, "获取品类信息失败!");
		}
		po.setCategoryName(goodsCategoryVo.getCategoryName());
		po.setMemo(goodsCategoryVo.getMemo());
		goodsCategoryDao.update(po);
		return result;
	}
	
	public Result getCategory(Result result,Integer id) throws Exception{
		GoodsCategoryPo po = goodsCategoryDao.get(id);
		GoodsCategoryVo vo = new GoodsCategoryVo();
		PropertyUtils.copyProperties(vo, po);
		result.setData(vo);
		return result;
	}
	
	public Result getListBySelect(Result result) throws Exception{
		result = goodsCategoryDao.findAvaliableList(result);
		return result;
	}
}
