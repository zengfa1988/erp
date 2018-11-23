package com.niuxing.auc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.niuxing.auc.dao.SysResourcesDao;
import com.niuxing.auc.dao.SysRoleResourcesDao;
import com.niuxing.auc.po.SysResourcesPo;
import com.niuxing.auc.vo.SysResourcesVo;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserResMenu;

/**
 * 
 * @author ds
 * 
 */
@Service
@SuppressWarnings("unchecked")
public class SysResourcesService {
	
	@Autowired
	SysResourcesDao sysResourcesDao;
	@Autowired
	SysRoleResourcesDao sysRoleResourcesDao;

	public void test() throws Exception {
//		Page<SysResourcesPo> page = new Page<SysResourcesPo>();
//		page.setPageNo(1);
//		page.setPageSize(10);
		List findPagination = (List)sysResourcesDao.findResourcesList(null);
		System.out.println(JSONObject.toJSONString(findPagination));
	}
	
	public Result findSysResources(Result result,SysResourcesVo sysResourcesVo,Page<SysResourcesPo> page) throws Exception {
		
		SysResourcesPo sysResourcesPo=new SysResourcesPo();
		
		PropertyUtils.copyProperties(sysResourcesPo,sysResourcesVo);
		result = sysResourcesDao.findResourcesList(result, sysResourcesPo, page);
		Pagination pagination =(Pagination) result.getData();
		List<SysResourcesPo> list=(List<SysResourcesPo>) pagination.getRows();
		List<SysResourcesVo> listVo=new ArrayList<SysResourcesVo>();
		
		SysResourcesVo sysResourcesVo1;
		for (SysResourcesPo sysResourcesPo1 : list) {
			sysResourcesVo1=new SysResourcesVo();
			PropertyUtils.copyProperties(sysResourcesVo1, sysResourcesPo1);
			listVo.add(sysResourcesVo1);
		}

		pagination.setTotal(listVo.size());
		pagination.setRows(listVo);
		result.setData(pagination);
		return result;
	}
	
	public Result findSysRes(Result result,SysResourcesVo sysResourcesVo,Page<SysResourcesPo> page) throws Exception {
		
		SysResourcesPo sysResourcesPo=new SysResourcesPo();
		
		PropertyUtils.copyProperties(sysResourcesPo,sysResourcesVo);
		result = sysResourcesDao.findResourcesList(result, sysResourcesPo, page);
		Pagination pagination =(Pagination) result.getData();
		List<SysResourcesPo> list=(List<SysResourcesPo>) pagination.getRows();
		List<SysResourcesVo> listVo=new ArrayList<SysResourcesVo>();
		
		SysResourcesVo sysResourcesVo1;
		for (SysResourcesPo sysResourcesPo1 : list) {
			sysResourcesVo1=new SysResourcesVo();
			PropertyUtils.copyProperties(sysResourcesVo1, sysResourcesPo1);
			listVo.add(sysResourcesVo1);
		}
		
		List<SysResourcesVo> listVo1=new ArrayList<SysResourcesVo>();
		
		for (SysResourcesVo sysResourcesVo2 : listVo) {
			if(sysResourcesVo2.getLevel()==1){
				listVo1.add(sysResourcesVo2);
			}
		}
		
		for (SysResourcesVo sysResourcesVo3 : listVo) {
			sysResourcesVo1=new SysResourcesVo();
			if(sysResourcesVo3.getLevel()==2){
				for (SysResourcesVo sysResourcesVo4 : listVo1) {
					if(sysResourcesVo3.getParentId().equals(sysResourcesVo4.getId())){
						if(sysResourcesVo4.getChildren()==null){
							sysResourcesVo4.setChildren(new ArrayList<SysResourcesVo>());
						}
						sysResourcesVo4.getChildren().add(sysResourcesVo3);
					}
				}
			}
		}
		for (SysResourcesVo sysResourcesVo3 : listVo) {
			sysResourcesVo1=new SysResourcesVo();
			if(sysResourcesVo3.getLevel()==3){
				for (SysResourcesVo sysResourcesVo2 : listVo1) {
						if(sysResourcesVo2.getChildren()==null){
							sysResourcesVo2.setChildren(new ArrayList<SysResourcesVo>());
						}
						List<SysResourcesVo> listVo2=sysResourcesVo2.getChildren();
						for (SysResourcesVo sysResourcesVo4 : listVo2) {
							if(sysResourcesVo3.getParentId().equals(sysResourcesVo4.getId())){
								if(sysResourcesVo4.getChildren()==null){
									sysResourcesVo4.setChildren(new ArrayList<SysResourcesVo>());
								}
								sysResourcesVo4.getChildren().add(sysResourcesVo3);
						}
					}
				}
			}
		}
		result.setData(listVo1);
		return result;
	}
	
	public Result saveSysResources(Result result, SysResourcesVo sysResourcesVo,Long userId) throws Exception{
		if(sysResourcesVo==null){
			String msg = "添加的项目信息为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			throw new FunctionException(result, "添加的项目信息为空");
		}
		SysResourcesPo sysResourcesPo=new SysResourcesPo();
		
		
		PropertyUtils.copyProperties(sysResourcesPo,sysResourcesVo);
		sysResourcesPo.setFunCode(sysResourcesPo.getFunCode().toLowerCase());
		sysResourcesPo.setCreateBy(userId);
		sysResourcesPo.setCreateDate(new Date());
		sysResourcesPo.setUpdateBy(userId);
		sysResourcesPo.setUpdateDate(new Date());
		result = sysResourcesDao.saveSysResources(result, sysResourcesPo);
		return result;
	}
	
	/**
	 * 根据id修改对象
	 * @param result
	 * @param SysProjectPo
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Result updateSysResources(Result result,SysResourcesVo sysResourcesVo,Long userId) throws Exception{
		if(sysResourcesVo==null){
			String msg = "添加的项目信息为空";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		SysResourcesPo sysResourcesPo=new SysResourcesPo();
		PropertyUtils.copyProperties(sysResourcesPo,sysResourcesVo);
		sysResourcesPo.setUpdateBy(userId);
		sysResourcesPo.setUpdateDate(new Date());
		result = sysResourcesDao.updateResources(result, sysResourcesPo);
		return result;
	}
	
	public Result updateSysRessStatus(Result result,Long id,Integer status,Long userId) throws Exception{
		
		if(status==0){
			result=sysResourcesDao.getSysResources(result,id);
			SysResourcesPo sysResourcesPo=result.getData();
			if(sysResourcesPo!=null&&sysResourcesPo.getLevel()<3){
				if(sysResourcesPo.getLevel()==2){//二级菜单则关闭对应的功能菜单
					List<Long> idslist=new ArrayList<Long>();
					idslist.add(Long.valueOf(id));
					result=sysResourcesDao.getSysResourcesByParId(result,idslist);
					List<SysResourcesPo> list=result.getData();
					for (SysResourcesPo sysResourcesPo2 : list) {
						sysResourcesPo2.setUpdateBy(userId);
						sysResourcesPo2.setUpdateDate(new Date());
						sysResourcesPo2.setStatus(status);
					}
				}
				if(sysResourcesPo.getLevel()==1){//关闭二级，三级菜单
					List<Long> idslist=new ArrayList<Long>();
					idslist.add(Long.valueOf(id));
					result=sysResourcesDao.getSysResourcesByParId(result,idslist);
					List<SysResourcesPo> list=result.getData();
					List<Long> parIdslist=new ArrayList<Long>();
					for (SysResourcesPo sysResourcesPo2 : list) {
						sysResourcesPo2.setUpdateBy(userId);
						sysResourcesPo2.setUpdateDate(new Date());
						sysResourcesPo2.setStatus(status);
						parIdslist.add(sysResourcesPo2.getId());
					}
					if(parIdslist.size()>0){
						result=sysResourcesDao.getSysResourcesByParId(result,parIdslist);
						List<SysResourcesPo> list1=result.getData();
						for (SysResourcesPo sysResourcesPo2 : list1) {
							sysResourcesPo2.setUpdateBy(userId);
							sysResourcesPo2.setUpdateDate(new Date());
							sysResourcesPo2.setStatus(status);
						}
					}
					
				}
			}
		}
		result = sysResourcesDao.updateSysResourcesStatus(result, status, id,userId);
		return result;
	}
	
	public Result deleteSysResourcesPo(Result result,Integer id) throws Exception{
		List<Long> idslist=new ArrayList<Long>();
		idslist.add(Long.valueOf(id));
		result=sysResourcesDao.getSysResourcesByParId(result,idslist);
		List<SysResourcesPo> list=result.getData();
		if(list!=null&&list.size()>0){
			String msg = "该菜单存在下级菜单不可删除！";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		result = sysResourcesDao.delSysResources(result, id);
		return result;
	}
	
	public Result findResListByRole(Result result, String proId) throws Exception {
		result = sysResourcesDao.findResourcesListByPrjId(result, proId);
		List<SysResourcesPo> srpList = result.getData();

		result.setData(srpList);
		return result;
	}
	
	/**
	 * 获取商家权限（包含限权限）
	 * @param result
	 * @param roleType
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author ds
	 * @date 2017年2月23日
	 * @version 3.1.0
	 */
	public Result findResourcesByRoleTypeToUserId(Result result, Integer roleType, Long userId) throws Exception {

		List<SysResourcesPo> list = new ArrayList<SysResourcesPo>();
		List<SysResourcesPo> allList = (List<SysResourcesPo>) findResListByRole(result, null).getData();
		Long roleId = 0L;
		roleId = Long.valueOf(roleType);
		List<Long> resourcesList=new ArrayList<Long>();
		result = sysRoleResourcesDao.querySysRoleResourcesByRoleId(result, roleId);// 获取角色权限
		if(result.getData()!=null){
			resourcesList = result.getData();
		}
		List<Long> resList = new ArrayList<Long>(new HashSet<Long>(resourcesList));
		for (SysResourcesPo sysResourcesPo : allList) {
			for (Long long1 : resList) {
				if (long1.equals(sysResourcesPo.getId())) {
					sysResourcesPo.setAliases(null);
					list.add(sysResourcesPo);
				}
			}
		}
		JSONArray jsonArray = new JSONArray();
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

				JSONArray c_node = setTreeJSONArray(list, menuId);
				if (c_node.size() > 0) {
					jsonMenu.put("childNode", c_node);
				}
				treeMenu.add(jsonMenu);
				jsonArray.addAll(treeMenu);
			}
		}
		result.setData(jsonArray);
		return result;
	}
	
	/**
	 * 将数组集合转换为树形集合并排除掉已关闭菜单
	 * 
	 * @param list
	 * @param listProject
	 * @param parentId
	 * @param roletype
	 * @return
	 */
	private JSONArray setTreeJSONArray(List<SysResourcesPo> list,  String parentId) {
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
				JSONArray c_node = setTreeJSONArray(list, menuId);
				if (c_node.size() > 0) {
					jsonMenu.put("childNode", c_node);
				}
				treeMenu.add(jsonMenu);

			}
		}
		return treeMenu;
	}
	
	/**
	 * 根据资源菜单信息
	 * 
	 * @param result
	 * @param proId
	 * @return
	 * @throws Exception
	 */
	public Result findResListByProSerivce(Result result, String proId) throws Exception {
		List<SysResourcesPo> srpList = null;
		if (srpList == null || srpList.size() == 0) {
			result = sysResourcesDao.findResourcesListByPrjId(result, proId);
			srpList = (List<SysResourcesPo>) result.getData();
//			RedisSlave.getInstance().setObject(SessionKey.REDIS_KEY_ALL_MUEN_VALUE, SerializeUtil.serialize(srpList), SessionKey.REDIS_KEY_BASE_TIME);
		} else {
//			RedisSlave.getInstance().expire(SessionKey.REDIS_KEY_ALL_MUEN_VALUE, SessionKey.REDIS_KEY_BASE_TIME);

		}
		UserResMenu loginRm = null;
		List<UserResMenu> list = new ArrayList<UserResMenu>();
		for (SysResourcesPo sysResourcesPo : srpList) {
			loginRm = new UserResMenu();
			PropertyUtils.copyProperties(loginRm, sysResourcesPo);
			loginRm.setMenuPath(sysResourcesPo.getMenuPath());
			loginRm.setMenulink(sysResourcesPo.getMenuPath());
			list.add(loginRm);
		}
		result.setData(list);
		return result;
	}

}
