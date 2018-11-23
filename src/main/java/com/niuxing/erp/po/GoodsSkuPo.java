package com.niuxing.erp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "goods_sku" )
public class GoodsSkuPo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "sku_no")
    private String skuNo;
	
	@Column(name = "goods_name")
    private String goodsName;
	
	@Column(name = "supplier_id")
    private Integer supplierId;
	
	@Column(name = "part_number")
    private String partNumber;
	
	@Column(name = "price")
    private Double price;
	
	@Column(name = "last_price")
	private Double lastPrice;
	
	@Column(name = "goods_no")
    private String goodsNo;
	
	@Column(name = "category_id")
    private Integer categoryId;
	
	@Column(name = "weight")
    private String weight;
	
	@Column(name = "goods_desc")
    private String goodsDesc;
	
	@Column(name = "package_min")
    private String packageMin;
	
	@Column(name = "send_platform")
    private Integer sendPlatform;
	
	@Column(name = "hs_code")
    private String hsCode;
	
	@Column(name = "delivery")
    private String delivery;
	
	@Column(name = "pics")
    private String pics;
	
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

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getPackageMin() {
		return packageMin;
	}

	public void setPackageMin(String packageMin) {
		this.packageMin = packageMin;
	}

	public Integer getSendPlatform() {
		return sendPlatform;
	}

	public void setSendPlatform(Integer sendPlatform) {
		this.sendPlatform = sendPlatform;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
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

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	
}
