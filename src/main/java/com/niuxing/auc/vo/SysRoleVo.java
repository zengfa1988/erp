package com.niuxing.auc.vo;

import java.io.Serializable;

public class SysRoleVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SysRoleVo [id=" + id + ", roleName=" + roleName + "]";
	}
}
