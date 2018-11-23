package com.niuxing.erp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niuxing.erp.dao.GoodsSkuDao;
import com.niuxing.erp.dao.PaymentDao;
import com.niuxing.erp.dao.PurchaseDao;
import com.niuxing.erp.dao.PurchaseDetailDao;
import com.niuxing.erp.dao.SupplierDao;
import com.niuxing.erp.po.GoodsSkuPo;
import com.niuxing.erp.po.PaymentPo;
import com.niuxing.erp.po.PurchaseDetailPo;
import com.niuxing.erp.po.PurchasePo;
import com.niuxing.erp.vo.PurchaseSkuVo;
import com.niuxing.erp.vo.PurchaseVo;
import com.zengfa.platform.util.DateUtil;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;

@Service
public class PurchaseService {

	@Autowired
	private PurchaseDao purchaseDao;
	
	@Autowired
	private PurchaseDetailDao purchaseDetailDao;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private GoodsSkuDao goodsSkuDao;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private PaymentDao paymentDao;
	
	public Result getPurchasePage(Result result,PurchaseVo purchaseVo,Integer page,Integer rows) throws Exception{
		result = purchaseDao.findByPage(result, purchaseVo, page, rows);
		Page dataPage = (Page)result.getData();
		List<PurchasePo> poList = dataPage.getData();
		if(CollectionUtils.isEmpty(poList)){
			Pagination pagination = Pagination.createPagination(0, ListUtils.EMPTY_LIST);
			result.setData(pagination);
			return result;
		}
		
		List<PurchaseVo> voList = new ArrayList<PurchaseVo>();
		for(PurchasePo po : poList){
			PurchaseVo vo = new PurchaseVo();
			PropertyUtils.copyProperties(vo, po);
			List<PaymentPo> paymentList = paymentDao.findByPurchaseId(vo.getId());
			if(CollectionUtils.isNotEmpty(paymentList)){
				Double payFee = 0.0;
				for(PaymentPo paymentPo : paymentList){
					payFee += paymentPo.getPaymentFee();
				}
				vo.setPayFee(payFee);
			}
			voList.add(vo);
		}
		
		Pagination pagination = Pagination.createPagination(dataPage.getTotalCount(), voList);
		result.setData(pagination);
		return result;
	}
	
	public Result createPurchaseNo(Result result){
		String purchaseNo = sequenceService.getNextSeq(3,false);
		result.setData(purchaseNo);
		return result;
	}
	
	public Result addPurchase(Result result,PurchaseVo purchaseVo) throws Exception{
		UserInfo userInfo = result.getUserInfo();
		String purchaseNo = sequenceService.getNextSeq(3,true);
		PurchasePo purchasePo = purchaseDao.getPurchaseByNo(purchaseNo);
		if(purchasePo != null){
			throw new FunctionException(result, "采购单号生成失败!");
		}
		purchasePo = new PurchasePo();
		PropertyUtils.copyProperties(purchasePo, purchaseVo);
		purchasePo.setPurchaseNo(purchaseNo);
		purchasePo.setCreateName(userInfo.getUserName());
		purchasePo.setCreateTime(new Date());
		purchasePo.setStatus(1);
		purchaseDao.save(purchasePo);
		Double totalFee = 0.0;
		if(CollectionUtils.isNotEmpty(purchaseVo.getGoodsList())){
			for(PurchaseSkuVo purchaseSkuVo : purchaseVo.getGoodsList()){
				GoodsSkuPo goodsSkuPo = goodsSkuDao.get(purchaseSkuVo.getSkuId());
				PurchaseDetailPo purchaseDetailPo = new PurchaseDetailPo();
//				PropertyUtils.copyProperties(purchaseDetailPo, purchaseSkuVo);
				purchaseDetailPo.setSkuId(purchaseSkuVo.getSkuId());
				purchaseDetailPo.setPurchaseNum(purchaseSkuVo.getPurchaseNum());
				purchaseDetailPo.setPurchasePrice(purchaseSkuVo.getPurchasePrice());
				String date = purchaseSkuVo.getDeliveryDate();
				if(StringUtils.isNotBlank(date)){
					Date deliveryDate = DateUtil.str2Date(date + " 00:00:00");
					purchaseDetailPo.setDeliveryDate(deliveryDate);
				}
				purchaseDetailPo.setTargetStore(purchaseSkuVo.getTargetStore());
				purchaseDetailPo.setPurchaseId(purchasePo.getId());
				purchaseDetailPo.setCreateTime(new Date());
				if(goodsSkuPo != null){
					purchaseDetailPo.setLastPrice(goodsSkuPo.getLastPrice());
				}
				totalFee += purchaseDetailPo.getPurchasePrice() * purchaseDetailPo.getPurchaseNum();
				purchaseDetailDao.save(purchaseDetailPo);
				if(goodsSkuPo == null){
					continue;
				}
				goodsSkuPo.setLastPrice(purchaseDetailPo.getPurchasePrice());
				goodsSkuDao.update(goodsSkuPo);
			}
		}
		purchasePo.setPurchaseFee(totalFee);
		purchaseDao.update(totalFee);
		
		return result;
	}
	
	public Result getPurchase(Result result,Integer id) throws Exception{
		PurchasePo po = purchaseDao.get(id);
		if(po == null){
			return result;
		}
		PurchaseVo vo = new PurchaseVo();
		PropertyUtils.copyProperties(vo, po);
		List<PurchaseSkuVo> purchaseDetailList = purchaseDetailDao.findByPurchaseIdSql(result, po.getId()).getData();
		vo.setGoodsList(purchaseDetailList);
		
		/*
		List<PurchaseDetailPo> list = purchaseDetailDao.findByPurchaseId(po.getId());
		List<PurchaseSkuVo> purchaseDetailList = new ArrayList<PurchaseSkuVo>();
		if(CollectionUtils.isNotEmpty(list)){
			List<Integer> skuIds = new ArrayList<Integer>();
			for(PurchaseDetailPo purchaseDetailPo : list){
				skuIds.add(purchaseDetailPo.getSkuId());
			}
			List<GoodsSkuPo> skuList = goodsSkuDao.findByIds(skuIds);
			Map<Integer,GoodsSkuPo> skuMap = new HashMap<Integer,GoodsSkuPo>();
			if(CollectionUtils.isNotEmpty(list)){
				for(GoodsSkuPo goodsSkuPo : skuList){
					skuMap.put(goodsSkuPo.getId(), goodsSkuPo);
				}
			}
			for(PurchaseDetailPo purchaseDetailPo : list){
				PurchaseSkuVo purchaseDetailVo = new PurchaseSkuVo();
				GoodsSkuPo goodsSkuPo = skuMap.get(purchaseDetailPo.getSkuId());
				if(goodsSkuPo != null){
//					PropertyUtils.copyProperties(purchaseDetailVo, goodsSkuPo);
					purchaseDetailVo.setPics(goodsSkuPo.getPics());
					purchaseDetailVo.setSkuNo(goodsSkuPo.getSkuNo());
					purchaseDetailVo.setGoodsName(goodsSkuPo.getGoodsName());
//					purchaseDetailVo.setSupplierNo(goodsSkuPo.gets);
				}
				PropertyUtils.copyProperties(purchaseDetailVo, purchaseDetailPo);
				purchaseDetailList.add(purchaseDetailVo);
			}
		}
		vo.setGoodsList(purchaseDetailList);
		*/
		result.setData(vo);
		return result;
	}
	
	public Result changeStatus(Result result,Integer id,Integer status,Long userId) throws Exception{
		PurchasePo purchasePo = purchaseDao.get(id);
		if(purchasePo==null){
			throw new FunctionException(result, "获取采购单信息失败!");
		}
		purchasePo.setStatus(status);
		purchaseDao.update(purchasePo);
		return result;
	}
	
	public Result importData(Result result,List<List<String>> dataList){
		UserInfo userInfo = result.getUserInfo();
		if(CollectionUtils.isEmpty(dataList)){
			return result;
		}
		String purchaseNo = sequenceService.getNextSeq(3,true);
		PurchasePo purchasePo = purchaseDao.getPurchaseByNo(purchaseNo);
		if(purchasePo != null){
			return result;
		}
		purchasePo = new PurchasePo();
		purchasePo.setPurchaseNo(purchaseNo);
		purchasePo.setStatus(1);
		purchasePo.setCreateName(userInfo.getUserName());
		purchasePo.setCreateTime(new Date());
		purchaseDao.save(purchasePo);
		
		for(List<String> data : dataList){
			if(CollectionUtils.isEmpty(data)){
				continue;
			}
			
			String skuNo = data.get(0);
			GoodsSkuPo goodsSkuPo = goodsSkuDao.getByNo(skuNo);
			if(goodsSkuPo == null){
				continue;
			}
			PurchaseDetailPo purchaseDetailPo = new PurchaseDetailPo();
			purchaseDetailPo.setSkuId(goodsSkuPo.getId());
			purchaseDetailPo.setLastPrice(goodsSkuPo.getLastPrice());
			String pruchaseNum = data.get(1);
			if(StringUtils.isNotBlank(pruchaseNum)){
				purchaseDetailPo.setPurchaseNum(Integer.parseInt(pruchaseNum));
			}
			String purchasePrice = data.get(2);
			if(StringUtils.isNotBlank(purchasePrice)){
				purchaseDetailPo.setPurchasePrice(Double.parseDouble(purchasePrice));
				
			}
			String deliveryDate = data.get(3);
			if(StringUtils.isNotBlank(deliveryDate)){
				Date d = DateUtil.str2Date(deliveryDate+" 00:00:00");
				purchaseDetailPo.setDeliveryDate(d);
			}
			String _targetStore = data.get(4);
			if(StringUtils.isNotBlank(_targetStore)){
				Integer targetStore = null;
				if("Aamazon warehouse".equals(_targetStore)){
					targetStore = 1;
				}else if("US warehouse".equals(_targetStore)){
					targetStore = 2;
				}
				purchaseDetailPo.setTargetStore(targetStore);
			}
			purchaseDetailPo.setCreateTime(new Date());
			purchaseDetailPo.setPurchaseId(purchasePo.getId());
			purchaseDetailDao.save(purchaseDetailPo);
			goodsSkuPo.setLastPrice(purchaseDetailPo.getPurchasePrice());
			goodsSkuDao.update(goodsSkuPo);
		}
		
		return result;
	}
	
	public Result exportExcel(Result result,PurchaseVo purchaseVo) throws Exception{
		result = purchaseDao.findByPage(result, purchaseVo, 1, Integer.MAX_VALUE);
		
		Page dataPage = (Page)result.getData();
		List<PurchasePo> poList = dataPage.getData();
		/*
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
		*/
		return result;
	}
	
	public Result getPurchaseSkuPage(Result result,PurchaseSkuVo purchaseSkuVo,Integer page,Integer rows) throws Exception{
		result = purchaseDetailDao.findByPage(result, purchaseSkuVo, page, rows);
		Pagination dataPage = (Pagination)result.getData();
		List<PurchaseSkuVo> poList = (List<PurchaseSkuVo>)dataPage.getRows();
		if(CollectionUtils.isEmpty(poList)){
			Pagination pagination = Pagination.createPagination(0, ListUtils.EMPTY_LIST);
			result.setData(pagination);
			return result;
		}
		
//		List<PurchaseVo> voList = new ArrayList<PurchaseVo>();
//		for(PurchasePo po : poList){
//			PurchaseVo vo = new PurchaseVo();
//			PropertyUtils.copyProperties(vo, po);
//			List<PaymentPo> paymentList = paymentDao.findByPurchaseId(vo.getId());
//			if(CollectionUtils.isNotEmpty(paymentList)){
//				Double payFee = 0.0;
//				for(PaymentPo paymentPo : paymentList){
//					payFee += paymentPo.getPaymentFee();
//				}
//				vo.setPayFee(payFee);
//			}
//			voList.add(vo);
//		}
		
		Pagination pagination = Pagination.createPagination(dataPage.getTotal(), poList);
		result.setData(pagination);
		return result;
	}
}
