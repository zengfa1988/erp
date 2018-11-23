package com.niuxing.erp.vo;

import java.util.Date;
import java.util.List;

public class TransVo {

	private Integer id;
	
    private String transNo;
	
    private Integer purchaseId;
	
    private String purchaseNo;
	
    private Integer expressCompany;
	
    private String expressNo;
	
    private Integer expressType;
	
    private Integer targetStore;
	
    private Integer supplierId;
	
    private Double fee;
	
    private String exitPort;
	
    private Date exitTime;
	
    private Date arriveTime;
	
    private Double volume;
	
    private Double weight;
	
    private String memo;
	
    private Integer status;
	
    private String createName;
	
    private Date createTime;
    
    private List<TransDetailVo> goodsList;

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

	public List<TransDetailVo> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<TransDetailVo> goodsList) {
		this.goodsList = goodsList;
	}
    
}
