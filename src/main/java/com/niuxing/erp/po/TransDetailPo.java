package com.niuxing.erp.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "trans_detail" )
public class TransDetailPo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "trans_id")
    private Integer transId;
	
	@Column(name = "trans_no")
    private String transNo;
	
	@Column(name = "purchase_id")
    private Integer purchaseId;
	
	@Column(name = "purchase_no")
    private String purchaseNo;
	
	@Column(name = "sku_no")
    private String skuNo;
	
	@Column(name = "goods_name")
    private String goodsName;
	
	@Column(name = "supplier_id")
    private Integer supplierId;
	
	@Column(name = "supplier_no")
    private String supplierNo;
	
	@Column(name = "purchase_num")
    private Integer purchaseNum;
	
	@Column(name = "purchase_price")
    private Double purchasePrice;
	
	@Column(name = "send_num")
    private Double sendNum;

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

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public Integer getPurchaseNum() {
		return purchaseNum;
	}

	public void setPurchaseNum(Integer purchaseNum) {
		this.purchaseNum = purchaseNum;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getSendNum() {
		return sendNum;
	}

	public void setSendNum(Double sendNum) {
		this.sendNum = sendNum;
	}

	public Integer getTransId() {
		return transId;
	}

	public void setTransId(Integer transId) {
		this.transId = transId;
	}
	
}
