package com.niuxing.erp.vo;

import java.util.Date;
import java.util.List;

public class PurchaseVo {

	private Integer id;
	
    private String purchaseNo;
	
    private String memo;
	
    private Integer status;
	
    private String createName;
	
    private Date createTime;
    
    private Double purchaseFee;
    
    private Double payFee;
    
    private List<PurchaseSkuVo> goodsList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<PurchaseSkuVo> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<PurchaseSkuVo> goodsList) {
		this.goodsList = goodsList;
	}

	public Double getPurchaseFee() {
		return purchaseFee;
	}

	public void setPurchaseFee(Double purchaseFee) {
		this.purchaseFee = purchaseFee;
	}

	public Double getPayFee() {
		return payFee;
	}

	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}
    
}
