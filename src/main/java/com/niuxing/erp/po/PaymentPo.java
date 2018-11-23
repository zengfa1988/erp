package com.niuxing.erp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "payment" )
public class PaymentPo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "payment_no")
    private String paymentNo;
	
	@Column(name = "payment_type")
    private Integer paymentType;
	
	@Column(name = "purchase_id")
    private Integer purchaseId;
	
	@Column(name = "purchase_no")
    private Integer purchaseNo;
	
	@Column(name = "payment_fee")
    private Double paymentFee;
	
	@Column(name = "memo")
    private String memo;
	
	@Column(name = "status")
    private Integer status;
	
	@Column(name = "create_name")
    private String createName;
	
	@Column(name = "create_time")
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

	public Integer getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(Integer purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	
}
