package com.niuxing.erp.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "sequence" )
public class SequencePo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "seq_sign")
    private String seqSign;
	
	@Column(name = "seq_desc")
    private String seqDesc;
	
	@Column(name = "seq_type")
	private Integer seqType;
	
	@Column(name = "curval")
	private Integer curval;
	
	@Column(name = "baseval")
	private String baseval;
	
	@Column(name = "seq_len")
	private Integer seqLen;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSeqSign() {
		return seqSign;
	}

	public void setSeqSign(String seqSign) {
		this.seqSign = seqSign;
	}

	public String getSeqDesc() {
		return seqDesc;
	}

	public void setSeqDesc(String seqDesc) {
		this.seqDesc = seqDesc;
	}

	public Integer getSeqType() {
		return seqType;
	}

	public void setSeqType(Integer seqType) {
		this.seqType = seqType;
	}

	public Integer getCurval() {
		return curval;
	}

	public void setCurval(Integer curval) {
		this.curval = curval;
	}

	public String getBaseval() {
		return baseval;
	}

	public void setBaseval(String baseval) {
		this.baseval = baseval;
	}

	public Integer getSeqLen() {
		return seqLen;
	}

	public void setSeqLen(Integer seqLen) {
		this.seqLen = seqLen;
	}
	
}
