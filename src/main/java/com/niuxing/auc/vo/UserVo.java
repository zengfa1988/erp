package com.niuxing.auc.vo;

import java.io.Serializable;

public class UserVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1728274914515264468L;

	private Long id;
	private String loginName;
	private String password;
	private String userName;
	private String phone;
	private Long createBy;
	private Long roleId;
	private String roleName;//角色名称
	private Long groupId;
	private String groupIds;
	private Long bizId;
	private Integer status;
	private String belongLine;
	private long deparmentParentId;//直属部门id
	private String deparmentName;//直属部门名称
	private long positionId;//岗位id
	private String positionName;//岗位名称

	private Integer roleType;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getStatus() {
		return status;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getBelongLine() {
		return belongLine;
	}

	public void setBelongLine(String belongLine) {
		this.belongLine = belongLine;
	}

	public long getDeparmentParentId() {
		return deparmentParentId;
	}

	public void setDeparmentParentId(long deparmentParentId) {
		this.deparmentParentId = deparmentParentId;
	}

	public String getDeparmentName() {
		return deparmentName;
	}

	public void setDeparmentName(String deparmentName) {
		this.deparmentName = deparmentName;
	}

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserVo other = (UserVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserVo [id=" + id + ", loginName=" + loginName + ", password=" + password + ", userName=" + userName + ", phone=" + phone + ", createBy=" + createBy + ", roleId="
				+ roleId + ", groupId=" + groupId + ", groupIds=" + groupIds + ", bizId=" + bizId + ", status=" + status + ", roleType=" + roleType + "]";
	}

}
