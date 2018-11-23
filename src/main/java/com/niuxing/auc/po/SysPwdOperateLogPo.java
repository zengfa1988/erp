package com.niuxing.auc.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_pwd_operate_log")
public class SysPwdOperateLogPo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "operate_ip")
	private String operateIp;

	@Column(name = "operate_address")
	private String operateAddress;
	
	@Column(name = "operate_mode")
	private String operateMode;
	
	@Column(name = "login_name")
	private String  loginName;
	
	@Column(name = "operate_date")
	private Date operateDate;

	@Column(name = "operate_user_id")
	private Long  operateUserId;
	
	@Column(name = "operate_user_name")
	private String  operateUserName;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperateIp() {
		return operateIp;
	}

	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}

	public String getOperateAddress() {
		return operateAddress;
	}

	public void setOperateAddress(String operateAddress) {
		this.operateAddress = operateAddress;
	}

	public String getOperateMode() {
		return operateMode;
	}

	public void setOperateMode(String operateMode) {
		this.operateMode = operateMode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public Long getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(Long operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
