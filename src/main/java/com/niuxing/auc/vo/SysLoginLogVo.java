package com.niuxing.auc.vo;

import java.io.Serializable;
import java.util.Date;



public class SysLoginLogVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	
	private Long userId;
	
	private String loginIp;

	private String loginAddress;
	
	private String loginMode;
	
	private String  loginName;
	
	private Long bizId;
	
	private Long belongId;
	
	private Date loginDate;
	
	private Date startDate;	//操作时间查询开始时间
	
	private Date endDate;	//操作时间查询结束时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginAddress() {
		return loginAddress;
	}

	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}

	public String getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "SysLoginLogPo [id=" + id + ", userId=" + userId + ", loginIp="
				+ loginIp + ", loginAddress=" + loginAddress + ", loginMode="
				+ loginMode + ", loginName=" + loginName + ", bizId=" + bizId
				+ ", belongId=" + belongId + ", loginDate=" + loginDate + "]";
	}

	
}
