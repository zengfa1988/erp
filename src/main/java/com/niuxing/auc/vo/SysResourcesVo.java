package com.niuxing.auc.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class SysResourcesVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6937594778736566428L;

	/**
	 * 
	 */

	private Long id;

	private Long prjId;

	private String menuName;

	private String menuPath;

	private String funCode;

	private String aliases;

	private Integer level;

	private Integer status;

	private Integer sort;
	
	private Integer	isDisplay;

	private String domain;

	private Long parentId;

	private String prjName;

	private Long createBy;

	private Date createDate;

	private Long updateBy;

	private Date updateDate;

	private List<SysResourcesVo> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrjId() {
		return prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public static Long getSerialversionuid() {
		return serialVersionUID;
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<SysResourcesVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysResourcesVo> children) {
		this.children = children;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	@Override
	public String toString() {
		return "SysResourcesVo [id=" + id + ", prjId=" + prjId + ", menuName=" + menuName + ", menuPath=" + menuPath + ", funCode=" + funCode + ", aliases=" + aliases + ", level="
				+ level + ", status=" + status + ", sort=" + sort + ", isDisplay=" + isDisplay + ", domain=" + domain + ", parentId=" + parentId + ", prjName=" + prjName
				+ ", createBy=" + createBy + ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate=" + updateDate + ", children=" + children + "]";
	}

}
