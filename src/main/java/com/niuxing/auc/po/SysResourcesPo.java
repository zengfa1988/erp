package com.niuxing.auc.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_resources")
public class SysResourcesPo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "prj_id")
	private Long prjId;
	
	@Column(name = "menu_name")
	private String menuName;
	
	@Column(name = "menu_path")
	private String menuPath;
	
	@Column(name = "funcode")
	private String funCode;
	
	@Column(name = "domain")
	private String  domain;
	
	@Column(name = "aliases")
	private String aliases;
	
	@Column(name = "level")
	private Integer level;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "sort")
	private Integer sort;
	
	@Column( name="is_display")
	private Integer isDisplay;
	
	@Column(name = "parent_id")
	private Long parentId;
	
	@Column(name = "create_by")
	private Long createBy;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "update_by")
	private Long updateBy;
	
	@Column(name = "update_date")
	private Date updateDate;
	
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

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	@Override
	public String toString() {
		return "SysResourcesPo [id=" + id + ", prjId=" + prjId + ", menuName=" + menuName + ", menuPath=" + menuPath + ", funCode=" + funCode + ", domain=" + domain + ", aliases="
				+ aliases + ", level=" + level + ", status=" + status + ", sort=" + sort + ", isDisplay=" + isDisplay + ", parentId=" + parentId + ", createBy=" + createBy
				+ ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate=" + updateDate + "]";
	}
	

}
