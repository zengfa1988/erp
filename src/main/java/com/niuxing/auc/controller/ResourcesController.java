package com.niuxing.auc.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niuxing.auc.po.SysResourcesPo;
import com.niuxing.auc.service.SysResourcesService;
import com.niuxing.auc.vo.SysResourcesVo;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.bean.ReturnDTO;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.web.userinfo.UserUtil;

/**
 * 菜单资源配置
 * @author zengfa
 *
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController extends BaseController{

	@Autowired
	private SysResourcesService sysResourcesService;
	
	/**
	 * 查询
	 * @param request
	 * @param prjId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "findSysRes.do", method = RequestMethod.GET)
	@ResponseBody
	public List<SysResourcesVo> findSysRes(HttpServletRequest request,  HttpServletResponse response) {
		Result result = this.getResult();
		SysResourcesVo sysResourcesVo = new SysResourcesVo();
		Page<SysResourcesPo> page = new Page<SysResourcesPo>();
		page.setPageNo(1);
		page.setPageSize(10000);

		try {
			result = this.sysResourcesService.findSysRes(result, sysResourcesVo, page);
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}

		return result.getData();
	}
	
	@RequestMapping(value = "addResources.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO addResources(HttpServletRequest request, HttpServletResponse response, SysResourcesVo sysResourcesVo) {
		Result result = this.getResult();
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		try {
			if (loginInfo == null) {
				result.setStatus(500);
				result.setMsg("登录信息失效");
				return result.DTO();
			}
			if (sysResourcesVo == null) {
				result.setMsg("添加信息为空！");
				result.setStatus(500);
				result.setData(null);
				return result.DTO();
			}

			result = sysResourcesService.saveSysResources(result, sysResourcesVo, loginInfo.getUserId());
//			RedisSlave.getInstance().del(SessionKey.REDIS_KEY_ALL_MUEN_VALUE);// 菜单改动
																				// 删除缓存
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
	/**
	 * 更新
	 * @param request
	 * @param response
	 * @param sysResourcesVo
	 * @return
	 */
	@RequestMapping(value = "updateResources.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO updateResources(HttpServletRequest request, HttpServletResponse response, SysResourcesVo sysResourcesVo) {
		Result result = this.getResult();
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		try {
			if (loginInfo == null) {
				result.setStatus(500);
				result.setMsg("登录信息失效");
				return result.DTO();
			}
			if (sysResourcesVo == null) {
				result.setMsg("参数为空！");
				result.setStatus(500);
				result.setData(null);
				return result.DTO();
			}
			sysResourcesVo.setUpdateDate(new Date());
			result = sysResourcesService.updateSysResources(result, sysResourcesVo, loginInfo.getUserId());
//			RedisSlave.getInstance().del(SessionKey.REDIS_KEY_ALL_MUEN_VALUE);// 菜单改动
																				// 删除缓存
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
	/**
	 * 更新
	 * @param request
	 * @param response
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "updateStatus.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO updateStatus(HttpServletRequest request, HttpServletResponse response, Integer id, Integer status) {

		Result result = this.getResult();
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		try {
			if (loginInfo == null) {
				result.setStatus(500);
				result.setMsg("登录信息失效");
				return result.DTO();
			}
			if (id == null || status == null) {
				result.setMsg("参数为空！");
				result.setStatus(500);
				result.setData(null);
				return result.DTO();
			}
			result = sysResourcesService.updateSysRessStatus(result, Long.valueOf(id), status, loginInfo.getUserId());
//			RedisSlave.getInstance().del(SessionKey.REDIS_KEY_ALL_MUEN_VALUE);// 菜单改动
																				// 删除缓存
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
	@RequestMapping(value = "delResources.do", method = RequestMethod.GET)
	@ResponseBody
	public ReturnDTO delResources(HttpServletRequest request, HttpServletResponse response, Integer id) {
		Result result = this.getResult();
		try {

			if (id == null) {
				result.setMsg("删除信息为空");
				result.setStatus(500);
				result.setData(null);
				return result.DTO();
			}
			result = sysResourcesService.deleteSysResourcesPo(result, id);
//			RedisSlave.getInstance().del(SessionKey.REDIS_KEY_ALL_MUEN_VALUE);// 菜单改动
																				// 删除缓存
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
}
