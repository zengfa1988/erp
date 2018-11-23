package com.niuxing.erp.vo;

import java.util.Date;

public class TransProgressVo {

	private Integer id;
	
    private Integer transId;
	
    private String transNo;
	
    private Date progressTime;
	
    private String content;
	
    private Integer status;
	
    private String createName;
	
    private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTransId() {
		return transId;
	}

	public void setTransId(Integer transId) {
		this.transId = transId;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public Date getProgressTime() {
		return progressTime;
	}

	public void setProgressTime(Date progressTime) {
		this.progressTime = progressTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
    
}
