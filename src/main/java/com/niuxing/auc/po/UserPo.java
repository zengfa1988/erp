package com.niuxing.auc.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 用户
 * @author ds
 *
 */
@Entity
@Table(name = "sys_user")
public class UserPo implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 登陆名.
	 */
	@Column(name = "login_name")
	private String loginName;

	/**
	 * 用户昵称.
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 密码.
	 */
	@Column(name = "login_pwd")
	private String loginPwd;
	
	@Column(name = "is_certificate")
	private boolean isCertificate;
	
	
	@Column(name="is_net")
	private boolean isNet;
	
	@Column(name="is_manager")
	private boolean isManager;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "login_time")
	private Date loginTime;
	
	@Column(name = "login_ip")
	private String loginIp;
	
	@Column(name = "gmt_last")
	private Date gmtLast;
	
	@Column(name = "biz_id")
	private Long bizId;
	
	@Column(name = "belong_id")
	private Long belongId;
	
	@Column(name = "masteracc_id")
	private Long masteraccId;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name = "create_by")
	private Long createBy;
	
	@Column(name = "update_by")
	private Long updateBy;
	
	@Column(name = "login_count")
	private Long loginCount;
	
	
	@Column(name = "error_count")
	private Long errorCount;
	
	/**
	 * 账号状态.
	 */
	@Column(name = "status")
	private Integer status;

	@Column(name = "account_type")
	private Integer accountType;
	//是否开启登录保护
	@Column(name = "is_protection")
	private Integer isProtection;
	

	/*//安全邮箱
	@Column(name = "mail")
	private String mail;*/
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getLoginPwd() {
		return loginPwd;
	}


	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Date getLoginTime() {
		return loginTime;
	}


	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}


	public String getLoginIp() {
		return loginIp;
	}


	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}


	public Date getGmtLast() {
		return gmtLast;
	}


	public void setGmtLast(Date gmtLast) {
		this.gmtLast = gmtLast;
	}


/*
	public Long getBizId() {
		return bizId;
	}


	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}


	public Long getBelongId() {
		return belongId;
	}


	public void setBelongId(Long belongId) {
		this.belongId = belongId;
	}
*/

	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public Long getCreateBy() {
		return createBy;
	}


	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}


	public Long getUpdateBy() {
		return updateBy;
	}


	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}


	public Long getLoginCount() {
		return loginCount;
	}


	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Long getErrorCount() {
		return errorCount;
	}


	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}


	public Long getMasteraccId() {
		return masteraccId;
	}


	public void setMasteraccId(Long masteraccId) {
		this.masteraccId = masteraccId;
	}


	public boolean getIsCertificate() {
		return isCertificate;
	}


	public void setIsCertificate(boolean isCertificate) {
		this.isCertificate = isCertificate;
	}


	public boolean getIsManager() {
		return isManager;
	}


	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}


	public boolean getIsNet() {
		return isNet;
	}


	public void setNet(boolean isNet) {
		this.isNet = isNet;
	}



	public Integer getAccountType() {
		return accountType;
	}


	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}


	public Integer getIsProtection() {
		return isProtection;
	}


	public void setIsProtection(Integer isProtection) {
		this.isProtection = isProtection;
	}




	public Long getBizId() {
		return bizId;
	}


	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}


	public Long getBelongId() {
		return belongId;
	}


	public void setBelongId(Long belongId) {
		this.belongId = belongId;
	}


}