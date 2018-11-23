package com.niuxing.erp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "purchase" )
public class PurchasePo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "purchase_no")
    private String purchaseNo;
	
	@Column(name = "supplier_id")
    private Integer supplierId;
	
	@Column(name = "category_id")
    private Integer categoryId;
	
	@Column(name = "memo")
    private String memo;
	
	@Column(name = "status")
    private Integer status;
	
	@Column(name = "purchase_fee")
    private Double purchaseFee;
	
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

	public Double getPurchaseFee() {
		return purchaseFee;
	}

	public void setPurchaseFee(Double purchaseFee) {
		this.purchaseFee = purchaseFee;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
}
