package com.niuxing.erp.vo;

import java.util.Date;

public class PaymentVo {

	private Integer id;
	
    private String paymentNo;
	
    private Integer paymentType;
	
    private Integer purchaseId;
    
    private String purchaseNo;
	
    private Double paymentFee;
    
    private Double needPayFee;
    
    private Double hasPayFee;
	
    private String memo;
	
    private Integer status;
	
    private String createName;
	
    private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Double getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(Double paymentFee) {
		this.paymentFee = paymentFee;
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

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Double getNeedPayFee() {
		return needPayFee;
	}

	public void setNeedPayFee(Double needPayFee) {
		this.needPayFee = needPayFee;
	}

	public Double getHasPayFee() {
		return hasPayFee;
	}

	public void setHasPayFee(Double hasPayFee) {
		this.hasPayFee = hasPayFee;
	}
    
}
