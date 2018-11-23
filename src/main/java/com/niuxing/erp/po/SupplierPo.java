package com.niuxing.erp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 供应商
 * @author zengfa
 *
 */
@Entity
@Table( name = "supplier" )
public class SupplierPo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "supplier_no")
    private String supplierNo;
	
	@Column(name = "supplier_name")
    private String supplierName;
	
	@Column(name = "legal")
    private String legal;
	
	@Column(name = "address")
    private String address;
	
	@Column(name = "website")
    private String website;
	
	@Column(name = "people")
    private Integer people;
	
	@Column(name = "equipment")
    private String equipment;
	
	@Column(name = "aera")
    private String aera;
	
	@Column(name = "product")
    private String product;
	
	@Column(name = "month_capacity")
    private String monthCapacity;
	
	@Column(name = "turnover")
    private String turnover;
	
	@Column(name = "us_proportion")
    private String usProportion;
	
	@Column(name = "contact_man")
    private String contactMan;
	
	@Column(name = "contact_phone")
    private String contactPhone;
	
	@Column(name = "contact_qq")
    private String contactQq;
	
	@Column(name = "contact_mail")
    private String contactMail;
	
	@Column(name = "factory_phone")
    private String factoryPhone;
	
	@Column(name = "factory_fax")
    private String factoryFax;
	
	@Column(name = "factory_mail")
    private String factoryMail;
	
	@Column(name = "factory_year")
    private String factoryYear;
	
	@Column(name = "model")
    private String model;
	
	@Column(name = "delive_date")
    private String deliveDate;
	
	@Column(name = "pics")
    private String pics;
	
	@Column(name = "memo")
    private String memo;
	
	@Column(name = "status")
    private Integer status;
	
	@Column(name = "create_man")
    private String createMan;
	
	@Column(name = "create_date")
    private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getAera() {
		return aera;
	}

	public void setAera(String aera) {
		this.aera = aera;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getMonthCapacity() {
		return monthCapacity;
	}

	public void setMonthCapacity(String monthCapacity) {
		this.monthCapacity = monthCapacity;
	}

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getUsProportion() {
		return usProportion;
	}

	public void setUsProportion(String usProportion) {
		this.usProportion = usProportion;
	}

	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactQq() {
		return contactQq;
	}

	public void setContactQq(String contactQq) {
		this.contactQq = contactQq;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getFactoryPhone() {
		return factoryPhone;
	}

	public void setFactoryPhone(String factoryPhone) {
		this.factoryPhone = factoryPhone;
	}

	public String getFactoryFax() {
		return factoryFax;
	}

	public void setFactoryFax(String factoryFax) {
		this.factoryFax = factoryFax;
	}

	public String getFactoryMail() {
		return factoryMail;
	}

	public void setFactoryMail(String factoryMail) {
		this.factoryMail = factoryMail;
	}

	public String getFactoryYear() {
		return factoryYear;
	}

	public void setFactoryYear(String factoryYear) {
		this.factoryYear = factoryYear;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDeliveDate() {
		return deliveDate;
	}

	public void setDeliveDate(String deliveDate) {
		this.deliveDate = deliveDate;
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

	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
