package com.niuxing.erp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niuxing.erp.dao.SupplierDao;
import com.niuxing.erp.po.SupplierPo;
import com.niuxing.erp.vo.ImgVo;
import com.niuxing.erp.vo.SupplierVo;
import com.niuxing.util.StringTools;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.excel.ExcelUtil;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;

@Service
public class SupplierService {

	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private SequenceService sequenceService;
	
	public Result getSupplierPage(Result result,SupplierVo supplierVo,Integer page,Integer rows) throws Exception{
		result = supplierDao.findByPage(result, supplierVo, page, rows);
		Page dataPage = (Page)result.getData();
		List<SupplierPo> poList = dataPage.getData();
		if(CollectionUtils.isEmpty(poList)){
			Pagination pagination = Pagination.createPagination(0, ListUtils.EMPTY_LIST);
			result.setData(pagination);
			return result;
		}
		
		List<SupplierVo> voList = new ArrayList<SupplierVo>();
		for(SupplierPo po : poList){
			SupplierVo vo = new SupplierVo();
			PropertyUtils.copyProperties(vo, po);
			voList.add(vo);
		}
		
		Pagination pagination = Pagination.createPagination(dataPage.getTotalCount(), voList);
		result.setData(pagination);
		return result;
	}
	
	public Result addSupplier(Result result,SupplierVo supplierVo,List<ImgVo> imgVos) throws Exception{
		UserInfo userInfo = result.getUserInfo();
		String supplierNo = sequenceService.getNextSeq(1,true);
		//判断供应商编码是否重复
		SupplierPo supplierPo = supplierDao.getSupplierByNo(supplierNo);
//		int redoNum = 1;
		while(supplierPo != null){
//			supplierNo = StringTools.randomCode6();
//			supplierPo = supplierDao.getSupplierByNo(supplierNo);
//			redoNum++;
//			if(redoNum>10){
//			}
			throw new FunctionException(result, "供应商ID生成失败!");
		}
		supplierPo = new SupplierPo();
		PropertyUtils.copyProperties(supplierPo, supplierVo);
		supplierPo.setSupplierNo(supplierNo);
		supplierPo.setStatus(1);
		supplierPo.setCreateMan(userInfo.getUserName());
		supplierPo.setCreateDate(new Date());
		String imgStr = "";
		if(CollectionUtils.isNotEmpty(imgVos)){
			for(ImgVo imgVo : imgVos){
				if(!"".equals(imgStr)){
					imgStr += ",";
				}
				imgStr += imgVo.getImgUrl();
			}
		}
		supplierPo.setPics(imgStr);
		supplierDao.save(supplierPo);
		
		 // 保存图片
//        saveImg( result, supplierPo.getId(), supplierVo );
        
		return result;
	}
	
	public Result getSupplier(Result result,Long supplierId) throws Exception{
		SupplierPo po = supplierDao.get(supplierId);
		SupplierVo vo = new SupplierVo();
		PropertyUtils.copyProperties(vo, po);
		
//		result = imgService.getImgByBizId(result, supplierId);
//		List<ImgPo> imgList = result.getData();
//		if(CollectionUtils.isNotEmpty(imgList)){
//			for(ImgPo imgPo : imgList){
//				if(!imgPo.getImgType().equals(ImgTpyeEnum.SUPPLIER_IMG.getValue())){
//					continue;
//				}
//				vo.setSupplierImg(imgPo.getImgUrl());
//			}
//		}
		
		result.setData(vo);
		return result;
	}
	
	public Result editSupplier(Result result,SupplierVo supplierVo,List<ImgVo> imgVos) throws Exception{
		SupplierPo supplierPo = supplierDao.get(supplierVo.getId());
		if(supplierPo==null){
			throw new FunctionException(result, "获取供应商信息失败!");
		}
		SupplierPo newPo = new SupplierPo();
		supplierVo.setId(null);
		PropertyUtils.copyProperties(newPo, supplierVo);
		newPo.setSupplierNo(supplierPo.getSupplierNo());
		newPo.setStatus(supplierPo.getStatus());
		newPo.setCreateMan(supplierPo.getCreateMan());
		newPo.setCreateDate(supplierPo.getCreateDate());
		
		String imgStr = "";
		for(ImgVo imgVo : imgVos){
			if(!"".equals(imgStr)){
				imgStr += ",";
			}
			imgStr += imgVo.getImgUrl();
		}
		newPo.setPics(imgStr);
		supplierDao.delete(supplierPo);
		supplierDao.save(newPo);
        
		return result;
	}
	
	public Result changeStatus(Result result,Long supplierId,Integer status,Long userId) throws Exception{
		SupplierPo supplierPo = supplierDao.get(supplierId);
		if(supplierPo==null){
			throw new FunctionException(result, "获取供应商信息失败!");
		}
		supplierPo.setStatus(status);
		supplierDao.update(supplierPo);
		return result;
	}
	
	public Result exportExcel(Result result,SupplierVo supplierVo) throws Exception{
		result = supplierDao.findByPage(result, supplierVo, 1, Integer.MAX_VALUE);
		Page dataPage = (Page)result.getData();
		List<SupplierPo> poList = dataPage.getData();
		
		List<Long> supplierIds = new ArrayList<Long>();
		for(SupplierPo po : poList){
			supplierIds.add(po.getId());
		}
		List<Map> mapList = new ArrayList<Map>();
		if(CollectionUtils.isNotEmpty(poList)){
			for(SupplierPo po : poList){
				Map map = PropertyUtils.describe(po);
				Integer status = po.getStatus();
				String statusName = "";
				switch(status){
					case 0:
						statusName = "停用";
						break;
					case 1:
						statusName = "正常";
						break;
					case 2:
						statusName = "审核未通过";
						break;
				}
				map.put("statusName", statusName);
				mapList.add(map);
			}
		}
		
		String head = "供应商记录";
		String[] fields = new String[] { "supplierNo","supplierName","statusName", "contactMan", "contactPhone",  "createDate"};
		String[] titles = new String[] { "供应商ID","供应商名称","状态",  "联系人", "联系人电话", "创建时间"};
		File dir = new File("supplier_export_dir");
		dir.mkdir();
		File f = ExcelUtil.export(null, head, fields, titles, mapList, dir);
		result.setData(f);
		return result;
	}
	
	/*
	public Result importData(Result result,List<SupplierPo> list){
		UserInfo userInfo = result.getUserInfo();
		for(SupplierPo po : list){
			String supplierNo = sequenceService.getNextSeq(1);
			//判断供应商编码是否重复
			SupplierPo supplierPo = supplierDao.getSupplierByNo(supplierNo);
			while(supplierPo != null){
				continue;
			}
			po.setSupplierNo(supplierNo);
			po.setStatus(1);
			po.setCreateMan(userInfo.getUserName());
			po.setCreateDate(new Date());
			String imgStr = "";
			po.setPics(imgStr);
			supplierDao.save(po);
		}
		return result;
	}
	*/
	
	public Result importData(Result result,List<List<String>> dataList){
		UserInfo userInfo = result.getUserInfo();
		if(CollectionUtils.isEmpty(dataList)){
			return result;
		}
		for(List<String> data : dataList){
			if(CollectionUtils.isEmpty(data)){
				continue;
			}
			String supplierNo = sequenceService.getNextSeq(1,true);
			//判断供应商编码是否重复
			SupplierPo supplierPo = supplierDao.getSupplierByNo(supplierNo);
			while(supplierPo != null){
				continue;
			}
			String supplierName = data.get(0);
			String contactMan = data.size()>1?data.get(1):null;
			if(StringUtils.isEmpty(supplierName)){
				continue;
			}
			
			SupplierPo po = new SupplierPo();
			po.setSupplierName(supplierName);
			po.setContactMan(contactMan);
			po.setSupplierNo(supplierNo);
			po.setStatus(1);
			po.setCreateMan(userInfo.getUserName());
			po.setCreateDate(new Date());
			String imgStr = "";
			po.setPics(imgStr);
			supplierDao.save(po);
		}
		
		return result;
	}
	
	public Result getListBySelect(Result result) throws Exception{
		result = supplierDao.findAvaliableList(result);
		return result;
	}
}
