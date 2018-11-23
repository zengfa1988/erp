package com.niuxing.erp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niuxing.erp.dao.PurchaseDetailDao;
import com.niuxing.erp.dao.TransDao;
import com.niuxing.erp.dao.TransDetailDao;
import com.niuxing.erp.dao.TransProgressDao;
import com.niuxing.erp.po.TransDetailPo;
import com.niuxing.erp.po.TransPo;
import com.niuxing.erp.po.TransProgressPo;
import com.niuxing.erp.vo.TransDetailVo;
import com.niuxing.erp.vo.TransProgressVo;
import com.niuxing.erp.vo.TransVo;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;

@Service
public class TransService {

	@Autowired
	private TransDao transDao;
	@Autowired
	private TransDetailDao transDetailDao;
	@Autowired
	private TransProgressDao transProgressDao;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private PurchaseDetailDao purchaseDetailDao;
	
	public Result getListPage(Result result,TransVo transVo,Integer page,Integer rows) throws Exception{
		result = transDao.findByPage(result, transVo, page, rows);
		Page dataPage = (Page)result.getData();
		List<TransPo> poList = dataPage.getData();
		if(CollectionUtils.isEmpty(poList)){
			Pagination pagination = Pagination.createPagination(0, ListUtils.EMPTY_LIST);
			result.setData(pagination);
			return result;
		}
		
		List<TransVo> voList = new ArrayList<TransVo>();
		for(TransPo po : poList){
			TransVo vo = new TransVo();
			PropertyUtils.copyProperties(vo, po);
			voList.add(vo);
		}
		
		Pagination pagination = Pagination.createPagination(dataPage.getTotalCount(), voList);
		result.setData(pagination);
		return result;
	}
	
	public Result addSend(Result result,TransVo transVo) throws Exception{
		UserInfo userInfo = result.getUserInfo();
		String transNo = sequenceService.getNextSeq(4,true);
		TransPo transPo = transDao.getByNo(transNo);
		if(transPo != null){
			throw new FunctionException(result, "批次号生成失败!");
		}
		transPo = new TransPo();
		PropertyUtils.copyProperties(transPo, transVo);
		transPo.setTransNo(transNo);
		transPo.setCreateName(userInfo.getUserName());
		transPo.setCreateTime(new Date());
		transPo.setStatus(1);
		transDao.save(transPo);
		
		
		if(CollectionUtils.isNotEmpty(transVo.getGoodsList())){
			for(TransDetailVo transDetailVo : transVo.getGoodsList()){
				TransDetailPo transDetailPo = new TransDetailPo();
				if(transDetailVo.getPurchaseId() != null){
					
				}
				transDetailPo.setPurchaseId(transDetailVo.getPurchaseId());
				transDetailPo.setPurchaseNo(transDetailVo.getPurchaseNo());
				transDetailPo.setSkuNo(transDetailVo.getSkuNo());
				transDetailPo.setPurchaseNum(transDetailVo.getPurchaseNum());
				transDetailPo.setPurchasePrice(transDetailVo.getPurchasePrice());
				transDetailPo.setGoodsName(transDetailVo.getGoodsName());
				transDetailPo.setSendNum(transDetailVo.getSendNum());
				transDetailPo.setSupplierId(transDetailVo.getSupplierId());
				transDetailPo.setSupplierNo(transDetailVo.getSupplierNo());
				transDetailPo.setTransNo(transPo.getTransNo());
				transDetailPo.setTransId(transPo.getId());
				transDetailDao.save(transDetailPo);
			}
		}
		return result;
	}
	
	public Result getSend(Result result,Integer id) throws Exception{
		TransPo po = transDao.get(id);
		TransVo vo = new TransVo();
		PropertyUtils.copyProperties(vo, po);
		List<TransDetailPo> tdList = transDetailDao.findByTransId(po.getId());
		List<TransDetailVo> goodsList = new ArrayList<TransDetailVo>();
		if(CollectionUtils.isNotEmpty(tdList)){
			for(TransDetailPo transDetailPo : tdList){
				TransDetailVo transDetailVo = new TransDetailVo();
				PropertyUtils.copyProperties(transDetailVo, transDetailPo);
				goodsList.add(transDetailVo);
			}
		}
		vo.setGoodsList(goodsList);
		result.setData(vo);
		return result;
	}
	
	public Result getProgress(Result result,Integer id) throws Exception{
		List<TransProgressPo> progressPoList = transProgressDao.findByTransId(id);
		if(CollectionUtils.isEmpty(progressPoList)){
			result.setData(null);
			return result;
		}
		List<TransProgressVo> progressVoList = new ArrayList<TransProgressVo>();
		for(TransProgressPo transProgressPo : progressPoList){
			TransProgressVo vo = new TransProgressVo();
			PropertyUtils.copyProperties(vo, transProgressPo);
			progressVoList.add(vo);
		}
		
		result.setData(progressVoList);
		return result;
	}
	
	public Result addProgress(Result result,TransProgressVo transVo) throws Exception{
		UserInfo userInfo = result.getUserInfo();
		TransPo transPo = transDao.get(transVo.getTransId());
		if(transPo == null){
			throw new FunctionException(result, "发货单获取失败!");
		}
		TransProgressPo transProgressPo = new TransProgressPo();
		PropertyUtils.copyProperties(transProgressPo, transVo);
		transProgressPo.setTransNo(transPo.getTransNo());
		transProgressPo.setTransId(transPo.getId());
		transProgressPo.setStatus(1);
		transProgressPo.setCreateName(userInfo.getUserName());
		transProgressPo.setCreateTime(new Date());
		transProgressDao.save(transProgressPo);
		return result;
	}
	
	public Result addProgress(Result result,List<TransProgressVo> transVoList,TransVo vo) throws Exception{
		UserInfo userInfo = result.getUserInfo();
		if(CollectionUtils.isEmpty(transVoList)){
			return result;
		}
		transProgressDao.delByTransId(result, vo.getId());
		for(TransProgressVo transProgressVo : transVoList){
			TransProgressPo transProgressPo = new TransProgressPo();
			PropertyUtils.copyProperties(transProgressPo, transProgressVo);
			transProgressPo.setTransNo(vo.getTransNo());
			transProgressPo.setTransId(vo.getId());
			transProgressPo.setStatus(1);
			if(userInfo != null){
				transProgressPo.setCreateName(userInfo.getUserName());
			}
			transProgressPo.setCreateTime(new Date());
			transProgressDao.save(transProgressPo);
		}
		
		return result;
	}
	
	public Result changeStatus(Result result,Integer id,Integer status,Long userId) throws Exception{
		TransPo transPo = transDao.get(id);
		if(transPo==null){
			throw new FunctionException(result, "获取采购发货单信息失败!");
		}
		transPo.setStatus(status);
		transDao.update(transPo);
		return result;
	}
}
