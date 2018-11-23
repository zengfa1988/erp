package com.niuxing.auc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.niuxing.auc.po.SysResourcesPo;
import com.niuxing.auc.po.SysRolePo;
import com.niuxing.auc.po.SysRoleResourcesPo;
import com.niuxing.auc.service.SysResourcesService;
import com.niuxing.auc.service.SysRoleResourcesService;
import com.niuxing.auc.service.SysRoleService;
import com.niuxing.auc.service.SysUserRoleService;
import com.niuxing.auc.vo.SelectVo;
import com.niuxing.auc.vo.SysRoleVo;
import com.niuxing.util.MessageResource;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.bean.ReturnDTO;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.web.userinfo.UserUtil;

/**
 * 角色权限管理
 * 
 * @author xyn
 * 
 */
@Controller
@RequestMapping("/sysRole")
@SuppressWarnings("unchecked")
public class SysRoleController extends BaseController {

	@Resource
	SysRoleService sysRoleService;
	@Resource
	SysResourcesService sysResourcesService;
	@Resource
	SysRoleResourcesService sysRoleResourcesService;
	@Resource
	SysUserRoleService sysUserRoleService;

	/**
	 * 列表
	 * @param request
	 * @param sysRolePo
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	@ResponseBody
	public Object querSysRolelist(HttpServletRequest request, SysRolePo sysRolePo, int page, int rows) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		Pagination findPagination = Pagination.createPagination(0, null);
		try {
			if (loginInfo == null) {
				result.setStatus(500);
				result.setMsg("登录信息失效");
				return result.DTO();
			}
			Page<SysRolePo> pages = new Page<SysRolePo>();
			pages.setPageNo(page);
			pages.setPageSize(rows);
//			sysRolePo.setBizId(loginInfo.getBizId());
//			if (loginInfo.getRoleId() != 1) {
//				sysRolePo.setRoleType(loginInfo.getRoleType());
//			}
			result = sysRoleService.querySysRoleService(result, sysRolePo, pages);
			if (result.getData() != null) {
				findPagination = result.getData();
			}
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return findPagination;
	}

	/**
	 * 添加
	 * @param request
	 * @param role
	 * @param authority
	 * @return
	 */
	@RequestMapping(value = "/saveRole.do", method = RequestMethod.POST)
	@ResponseBody
	public Object saveRole(HttpServletRequest request, SysRolePo role, String authority) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			if (null == role) {
				result.setMsg("添加角色信息，对象为" + role);
				throw new FunctionException(result, "");
			}
			if (StringUtils.isBlank(role.getRoleName())) {
				result.setMsg("角色名称为空！");
				result.setStatus(500);
				return result;
			}
			JSONArray parseArray = JSONArray.parseArray(authority);
			/** 新增角色 **/
			role.setStatus(1);
//			if (loginInfo.getRoleType() != null) {
//				role.setRoleType(loginInfo.getRoleType());
//			}
//			role.setBizId(loginInfo.getBizId());
			result = sysRoleService.saveSysRoleService(result, role, loginInfo.getUserId());

			if (result.getStatus() == 200) {
				SysRolePo sysRole = result.getData();
				/** 新增权限 **/
				saveAuthority(request, parseArray, sysRole.getId());
			}
		} catch (Exception e) {
			result = this.error(result, e);
		}  finally {
			send(result);
		}
		return result;
	}
	
	/**
	 * 获取菜单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMenuList.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray authorityList(HttpServletRequest request) {
		Result result = this.getResult(request);
		JSONArray jsonArray = new JSONArray();
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return new JSONArray();
		}
//		Integer roletype = loginInfo.getRoleType();
		Integer roletype = 1;
		Long roleId = 0L;
		try {
			List<SysResourcesPo> list = new ArrayList<SysResourcesPo>();
			
			List<SysResourcesPo> allList= (List<SysResourcesPo>) sysResourcesService.findResListByRole(result, null).getData();
			if (roletype == 1) {
				// 超级管理员，查询所有权限菜单
				list =allList;
			} else {
				/*
				Long userId=loginInfo.getUserId();
				if(!loginInfo.isManager()){
					userId=userBusinessService.getManegerUserId(result, loginInfo.getBizId(), loginInfo.getRoleType()).getData();
				}
				if (roletype.equals(RoleTypeEnum.plfsupplier.getSign())) {// 供应商账号
					roleId=Long.valueOf(loginInfo.getSupplierType());
				} else {
					roleId=Long.valueOf(loginInfo.getRoleId());
				}
				result = sysRoleResourcesService.querySysRoleResourcesByRoleIdService(result, roleId,userId);// 通过角色id获取资源id
				List<Long> resList = (List<Long>) result.getData();
				for (SysResourcesPo sysResourcesPo : allList) {
					for (Long long1 : resList) {
						if (long1.equals(sysResourcesPo.getId())) {
							sysResourcesPo.setAliases(null);
							list.add(sysResourcesPo);
						}
					}
				}
				*/
			}
			for (SysResourcesPo sysResourcesPo : list) {
				if (sysResourcesPo.getParentId() < 1) {
					sysResourcesPo.setParentId(Long.valueOf(-1));
					JSONArray treeMenu = new JSONArray();
					String pId = sysResourcesPo.getParentId().toString();

					String menuId = sysResourcesPo.getId().toString();
					JSONObject jsonMenu = new JSONObject();
					jsonMenu.put("id", menuId);
					jsonMenu.put("parentMenuId", pId);
					jsonMenu.put("menuName", sysResourcesPo.getMenuName());
					jsonMenu.put("proName", sysResourcesPo.getMenuName());
					jsonMenu.put("sysId", sysResourcesPo.getPrjId());
					jsonMenu.put("aliases", sysResourcesPo.getAliases());
//					if (roletype == 1) {
//						String sysName=getSysNmae(listProject, sysResourcesPo.getPrjId());
//						jsonMenu.put("sysName", sysName);
//					}
					JSONArray c_node = setTreeJSONArray(list, menuId, roletype);
					if (c_node.size() > 0) {
						jsonMenu.put("childNode", c_node);
					}
					treeMenu.add(jsonMenu);
					jsonArray.addAll(treeMenu);
				}
			}

		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return jsonArray;
	}

	private JSONArray setTreeJSONArray(List<SysResourcesPo> list, String parentId, Integer roletype) {
		JSONArray treeMenu = new JSONArray();
		for (SysResourcesPo po : list) {
			String pId = po.getParentId().toString();
			String menuStatus = po.getStatus().toString();
			if (parentId.equals(pId) && "1".equals(menuStatus)) {
				String menuId = po.getId().toString();
				JSONObject jsonMenu = new JSONObject();
				jsonMenu.put("id", menuId);
				jsonMenu.put("parentMenuId", pId);
				jsonMenu.put("menuName", po.getMenuName());
				jsonMenu.put("proName", po.getMenuName());
				jsonMenu.put("sysId", po.getPrjId());
				jsonMenu.put("aliases", po.getAliases());
//				if (roletype == 1) {
//					String sysName=getSysNmae(listProject, po.getPrjId());
//					jsonMenu.put("sysName", sysName);
//				}
				JSONArray c_node = setTreeJSONArray(list, menuId, roletype);
				if (c_node.size() > 0) {
					jsonMenu.put("childNode", c_node);
				}
				treeMenu.add(jsonMenu);

			}
		}
		return treeMenu;
	}
	
	/**
	 * 新增权限
	 * @param request
	 * @param parseArray
	 * @param rid
	 * @author ds
	 * @date 2017年2月9日
	 * @version 3.1.0
	 */
	public void saveAuthority(HttpServletRequest request, JSONArray parseArray, Long rid)    {
		Result result = this.getResult(request);
		List<SysRoleResourcesPo> poList = new ArrayList<SysRoleResourcesPo>();
		for (int j = 0; j < parseArray.size(); j++) {
			JSONObject jsonObject = parseArray.getJSONObject(j);
			SysRoleResourcesPo sysRoleResourcesPo = new SysRoleResourcesPo();
			sysRoleResourcesPo.setRoleId(rid);
			sysRoleResourcesPo.setResourcesId(jsonObject.getLong("menuId"));
			poList.add(sysRoleResourcesPo);
		}

		if (!poList.isEmpty()) {
			try {
				result=sysRoleResourcesService.saveSysRoleResourcesListService(result, poList);
			} catch (Exception e) {
				result = this.error(result, e);
			} finally {
				send(result);
			}
		}
	}
	
	/**
	 * 查询角色详情
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sysRoleById.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO getSysRoleById(HttpServletRequest request, String id) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			result = sysRoleService.querySysRoleByIdService(result, Long.valueOf(id), null);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}
	
	/**
	 * 角色编辑编辑
	 * @param request
	 * @param sysRolePo
	 * @param authority
	 * @return
	 */
	@RequestMapping(value = "/updateRole.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO updateRole(HttpServletRequest request, SysRolePo sysRolePo, String authority) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			if (null == sysRolePo) {
				result.setMsg("编辑角色信息，对象为" + sysRolePo);
				throw new FunctionException(result, "");
			}
			if (null == sysRolePo.getId()) {
				result.setMsg(MessageResource.PARAMETERS_ERROR);
				throw new FunctionException(result, "");
			}
			if (StringUtils.isBlank(sysRolePo.getRoleName())) {
				result.setMsg("角色名称为空！");
				result.setStatus(500);
				return result.DTO();
			}
			JSONArray parseArray = JSONArray.parseArray(authority);
			/** 编辑角色 **/
			SysRolePo role = (SysRolePo) sysRoleService.querySysRoleByIdService(result, sysRolePo.getId(), 1l).getData();
			role.setRoleName(sysRolePo.getRoleName());
			role.setRemarks(sysRolePo.getRemarks());
			sysRoleService.updateSysRoleService(result, role, 1l);

			/** 清空权限 **/
			sysRoleResourcesService.delSysRoleResourcesService(result, sysRolePo.getId());
			/** 保存新权限 **/
			saveAuthority(request, parseArray, sysRolePo.getId());

		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}
	
	/**
	 * 展示平台功能列表
	 * @param request
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/authorityList.do", method = RequestMethod.POST)
	@ResponseBody
	public Object authorityList(HttpServletRequest request, String roleId) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			// 权限id集合
			List<Long> list = (List<Long>) sysRoleResourcesService.querySysRoleResourcesByRoleIdService(result, Long.valueOf(roleId),null).getData();
			result.setData(list);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.getData();
	}
	
	/**
	 *  删除
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delSysRole.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO updateDelSysRole(HttpServletRequest request, String id) {
		Result result = this.getResult(request);
		if (Integer.valueOf(id) <= 1) {
			result.setStatus(500);
			result.setMsg("该角色为系统默认角色不可删除！");
			return result.DTO();
		}

		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (null == loginInfo) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			Long userNum = (Long) sysUserRoleService.findSysUserNumByRoleId(result, Long.valueOf(id)).getData();
			if (userNum > 0) {
				result.setStatus(500);
				result.setMsg("该角色下与用户存在绑定关系,不能删除！");
				return result.DTO();
			}
			result = sysRoleService.delSysRoleByIdService(result, Long.valueOf(id));
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}
	
	/**
	 * 获取角色类型列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sysRoleVo.do", method = RequestMethod.POST)
	@ResponseBody
	public Object getSysRoleVo(HttpServletRequest request) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		ArrayList<SelectVo> list2 = new ArrayList<SelectVo>();
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			Page<SysRolePo> pages = new Page<SysRolePo>();
			pages.setPageNo(1);
			pages.setPageSize(100);
			SysRolePo sysRolePo = new SysRolePo();
//			sysRolePo.setBizId(loginInfo.getBizId());
//			sysRolePo.setRoleType(loginInfo.getRoleType());
			result = sysRoleService.findSysRoleService(result, sysRolePo, pages);
			if (result.getData() != null) {
				Pagination findPagination = result.getData();
				List<SysRolePo> rows = (List<SysRolePo>) findPagination.getRows();
				List<SysRoleVo> list = new ArrayList<SysRoleVo>();
				for (SysRolePo srPo : rows) {
					SysRoleVo sysRoleVo = new SysRoleVo();
					PropertyUtils.copyProperties(sysRoleVo, srPo);
					list.add(sysRoleVo);
				}
				roleType2SVO(list, list2);
			}

		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}

		return list2;
	}
	
	private void roleType2SVO(List<SysRoleVo> list1, List<SelectVo> list2) {
		if (list1 == null || list2 == null) {
			return;
		}
		list2.add(new SelectVo(String.valueOf(-1), "全部"));
		for (SysRoleVo a : list1) {
			list2.add(new SelectVo(a.getId() + "", a.getRoleName()));
		}
	}
	
}
