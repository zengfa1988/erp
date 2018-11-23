package com.niuxing.auc.vo;

import java.io.Serializable;
import java.util.Date;

public class RoleVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String roleName;

	private Long bizId;

	private Integer roleType;

	private Long userNum;

	private Integer status;

	private Long createBy;

	private Date createDate;

	private Long updateBy;

	private Date updateDate;

	private String remarks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getBizId() {
		return bizId;
	}

	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getUserNum() {
		return userNum;
	}

	public void setUserNum(Long userNum) {
		this.userNum = userNum;
	}

	@Override
	public String toString() {
		return "RoleV [id=" + id + ", roleName=" + roleName + ", bizId=" + bizId + ", roleType=" + roleType + ", status=" + status + ", createBy=" + createBy + ", createDate="
				+ createDate + ", updateBy=" + updateBy + ", updateDate=" + updateDate + ", remarks=" + remarks + ", userNum=" + userNum + "]";
	}
}
