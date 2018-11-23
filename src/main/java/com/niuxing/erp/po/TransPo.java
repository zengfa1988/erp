package com.niuxing.erp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "trans" )
public class TransPo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "trans_no")
    private String transNo;
	
	@Column(name = "purchase_id")
    private Integer purchaseId;
	
	@Column(name = "purchase_no")
    private String purchaseNo;
	
	@Column(name = "express_company")
    private Integer expressCompany;
	
	@Column(name = "express_no")
    private String expressNo;
	
	@Column(name = "express_type")
    private Integer expressType;
	
	@Column(name = "target_store")
    private Integer targetStore;
	
	@Column(name = "supplier_id")
    private Integer supplierId;
	
	@Column(name = "fee")
    private Double fee;
	
	@Column(name = "exit_port")
    private String exitPort;
	
	@Column(name = "exit_time")
    private Date exitTime;
	
	@Column(name = "arrive_time")
    private Date arriveTime;
	
	@Column(name = "volume")
    private Double volume;
	
	@Column(name = "weight")
    private Double weight;
	
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

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Integer getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(Integer expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Integer getExpressType() {
		return expressType;
	}

	public void setExpressType(Integer expressType) {
		this.expressType = expressType;
	}

	public Integer getTargetStore() {
		return targetStore;
	}

	public void setTargetStore(Integer targetStore) {
		this.targetStore = targetStore;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getExitPort() {
		return exitPort;
	}

	public void setExitPort(String exitPort) {
		this.exitPort = exitPort;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
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
	
}
