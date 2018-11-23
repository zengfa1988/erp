package com.niuxing.auc.vo;

import java.io.Serializable;

/**
 * 
 * @author ds
 *
 */
public class SelectVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	
	/**
	 * @param id
	 * @param value
	 */
	public  SelectVo(String id, String text) {
		this.id = id;
		this.text = text;
	}
	/**
	 * 构造函数
	 */
	public SelectVo() {
		super();
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "SelectVo [id=" + id + ", text=" + text + "]";
	}
	
	
}

