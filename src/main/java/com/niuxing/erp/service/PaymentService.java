package com.niuxing.erp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niuxing.erp.dao.PaymentDao;
import com.niuxing.erp.dao.PurchaseDao;
import com.niuxing.erp.po.PaymentPo;
import com.niuxing.erp.po.PurchasePo;
import com.niuxing.erp.vo.PaymentVo;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;

@Service
public class PaymentService {

	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private PurchaseDao purchaseDao;
	
	public Result getListPage(Result result,PaymentVo paymentVo,Integer page,Integer rows) throws Exception{
		result = paymentDao.findByPage(result, paymentVo, page, rows);
		Page dataPage = (Page)result.getData();
		List<PaymentPo> poList = dataPage.getData();
		if(CollectionUtils.isEmpty(poList)){
			Pagination pagination = Pagination.createPagination(0, ListUtils.EMPTY_LIST);
			result.setData(pagination);
			return result;
		}
		
		List<PaymentVo> voList = new ArrayList<PaymentVo>();
		for(PaymentPo po : poList){
			PaymentVo vo = new PaymentVo();
			PropertyUtils.copyProperties(vo, po);
			if(vo.getPurchaseId() != null){
				PurchasePo purchasePo = purchaseDao.get(vo.getPurchaseId());
				if(purchasePo != null){
					vo.setPurchaseNo(purchasePo.getPurchaseNo());
					vo.setNeedPayFee(purchasePo.getPurchaseFee());
				}
			}
			voList.add(vo);
		}
		
		Pagination pagination = Pagination.createPagination(dataPage.getTotalCount(), voList);
		result.setData(pagination);
		return result;
	}
	
	public Result addPayment(Result result,PaymentVo paymentVo) throws Exception{
		UserInfo userInfo = result.getUserInfo();
		String paymentNo = sequenceService.getNextSeq(4,true);
		//判断供应商编码是否重复
		PaymentPo paymentPo = paymentDao.getPaymentByNo(paymentNo);
		while(paymentPo != null){
			throw new FunctionException(result, "付款单号生成失败!");
		}
		paymentPo = new PaymentPo();
		PropertyUtils.copyProperties(paymentPo, paymentVo);
		paymentPo.setPaymentNo(paymentNo);
		paymentPo.setStatus(1);
		paymentPo.setCreateName(userInfo.getUserName());
		paymentPo.setCreateTime(new Date());
		paymentDao.save(paymentPo);
		
		return result;
	}
	
	public Result getPayment(Result result,Integer id) throws Exception{
		PaymentPo po = paymentDao.get(id);
		PaymentVo vo = new PaymentVo();
		PropertyUtils.copyProperties(vo, po);
		if(po.getPurchaseId() != null){
			PurchasePo purchasePo = purchaseDao.get(vo.getPurchaseId());
			if(purchasePo != null){
				vo.setPurchaseNo(purchasePo.getPurchaseNo());
				vo.setNeedPayFee(purchasePo.getPurchaseFee());
			}
			List<PaymentPo> paymentList = paymentDao.findByPurchaseId(po.getPurchaseId());
			if(CollectionUtils.isNotEmpty(paymentList)){
				Double payFee = 0.0;
				for(PaymentPo paymentPo : paymentList){
					payFee += paymentPo.getPaymentFee();
				}
				vo.setHasPayFee(payFee);
			}
		}
		result.setData(vo);
		return result;
	}
	
	public Result changeStatus(Result result,Integer id,Integer status,Long userId) throws Exception{
		PaymentPo paymentPo = paymentDao.get(id);
		if(paymentPo==null){
			throw new FunctionException(result, "获取付款单信息失败!");
		}
		paymentPo.setStatus(status);
		paymentDao.update(paymentPo);
		return result;
	}
}
