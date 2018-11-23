package com.niuxing.auc.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.niuxing.auc.po.UserPo;
import com.niuxing.auc.vo.UserVo;
import com.zengfa.platform.data.hibernate.HibernateDao;
import com.zengfa.platform.util.bean.Page;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;


/**
 * 
 * @author ds
 * 
 */
@Repository
public class UserDao extends HibernateDao<UserPo, Long> {

	private Pagination findPagination;

	/**
	 * 
	 * @param result
	 * @param userPo
	 * @return
	 * @throws Exception
	 */
	public Result saveUser(Result result, UserPo userPo) throws Exception {
		this.save(userPo);
		result.setData(userPo);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result getUser(Result result, Long id) throws Exception {
		UserPo userPo = this.findUnique("from UserPo where id=?", id);
		result.setData(userPo);
		return result;
	}

/**
	 * 通过业务id获取用户
	 * 
	 * @author ds <br>
	 * @Date 2016年5月31日<br>
	 * @param result
	 * @param bizId
	 * @return
	 * @throws Exception
	 *//**
	public Result findUserByBizId1(Result result, Long bizId) throws Exception {
		Object[] objects = { bizId };

		List<UserPo> list = this.find("from UserPo where id in (select userId from UserBusinessPo where  bizId =?)", objects);

		result.setData(list);
		return result;
	}*/

	/**
	 * 根据登录名称查询用户数据.
	 * 
	 * @param result
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	public Result getUserByLoginName(Result result, String loginName) throws Exception {
		UserPo userPo = this.findUnique("from UserPo where  loginName=?", loginName);
		if (userPo == null) {
			String msg = "用户账号" + loginName + "不存在";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}

		result.setData(userPo);
		return result;
	}

	/**
	 * 根据手机号查询用户数据
	 * @param result
	 * @param phone
	 * @param userShopType 用户类型
	 * @return
	 * @throws Exception
	 * @author ds
	 * @date 2017年2月9日
	 * @version 3.1.0
	 */
	public Result findUserByPhoneToType(Result result, String phone,int accountType,List<Integer> userShopType) throws Exception {
		List<Object> listParams=new ArrayList<Object>();
		listParams.add(phone);
		listParams.add(accountType);
		String userShopTypeParams="";
		for(int i=0;i<userShopType.size();i++){
			listParams.add(userShopType.get(i));
			userShopTypeParams+=" userShopType=? or ";
		}
		String sql="from UserPo where  phone=?  and  id in (select userId from UserBusinessPo where  accountType =? ) and"
				+ " ("+userShopTypeParams.substring(0, userShopTypeParams.length()-3)+")";
		List<UserPo> list = this.find(sql,listParams);
		if(list==null){
			String msg = "手机号" + phone + "未绑定未绑定账号";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		result.setMsg(null);
		result.setStatus(200);
		result.setData(list);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param loginName
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public Result getUserByLoginNameToPhone(Result result, String loginName, String phone) throws Exception {
		UserPo userPo = this.findUnique("from UserPo where loginName=? and phone=?", loginName, phone);
		if (userPo == null) {
			String msg = "用户账号" + loginName + "不存在";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		result.setData(userPo);
		return result;
	}
	
	

	/**
	 * 获取主账号
	 * 
	 * @param result
	 * @param roleType
	 * @param bizId
	 * @return
	 * @throws Exception
	 */
	public Result getManagerUserByBizId(Result result, Integer roleType, Long bizId) throws Exception {

		UserPo userPo = this.findUnique("from UserPo  where   isManager=1 and  id in (select userId from UserBusinessPo where  accountType =? and bizId =?)", roleType, bizId);

		result.setData(userPo);
		return result;
	}

	/**
	 * 根据账号类型业务id，获取用户
	 * 
	 * @param result
	 * @param roleType
	 * @param bizId
	 * @return
	 * @throws Exception
	 */
	public Result findUserByBizIdToType(Result result, Integer roleType, Long bizId) throws Exception {

		List<UserPo> userList = this.find("from UserPo  where   id in (select userId from UserBusinessPo where  accountType =? and bizId =?)", roleType, bizId);

		result.setData(userList);
		return result;
	}
	/**
	 * 根据id修改用户信息
	 * 
	 * @param result
	 * @param userPo
	 * @return
	 * @throws Exception
	 */
	public Result updateUser(Result result, UserPo userPo) throws Exception {
		this.update(userPo);
		result.setData(userPo);
		return result;
	}

	/**
	 * 根据用户id修改用户状态
	 * 
	 * @param result
	 * @param status
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result updateStatus(Result result, Integer status, Long id) throws Exception {
		Object[] objects = { status, id };
		int i = this.updateSql("update  sys_user  set status = ? where id = ?", objects);
		result.setData(i);
		return result;
	}

	/**
	 * 根据用户id修改用户手机号码
	 * 
	 * @param result
	 * @param phone
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result updatePhone(Result result, String phone, Long id) throws Exception {
		Object[] objects = { phone, id };
		int i = this.updateSql("update  sys_user  set phone = ? where id = ?", objects);
		result.setData(i);
		return result;
	}

	/**
	 * 根据用户id修改用户密码
	 * 
	 * @param result
	 * @param newPassword
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result updatePwd(Result result, String newPassword, Long id) throws Exception {
		Object[] objects = { newPassword, id, id };
		int i = this.updateSql("update  sys_user  set login_pwd = ?,create_by=? where id = ? ", objects);
		result.setData(i);
		return result;
	}

	/**
	 * 重置密码
	 * 
	 * @param result
	 * @param newPassword
	 * @param id
	 * @param create_by
	 * @return
	 * @throws Exception
	 */
	public Result resetUserPwd(Result result, String newPassword, Long id, Long create_by) throws Exception {
		Object[] objects = { newPassword, create_by, id };
		int i = this.updateSql("update  sys_user  set login_pwd = ?,create_by=? where id = ? ", objects);
		result.setData(i);
		return result;
	}

	/**
	 * 根据登录名称查询用户数据.
	 * 
	 * @param result
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Result findUserRoleByUserId(Result result, Long userId) throws Exception {
		Object[] objects = { userId };
		String sql = "select su.user_id as userid ,su.role_id as roleid ,sr.role_type as roletype,sr.role_no as roleNo  "
				+ "from sys_user_role su, sys_role sr where su.role_id=sr.id  and  su.user_id=?";

		List<Object> queryForList = this.queryForList(sql, objects);
		result.setData(queryForList);
		return result;
	}

	/**
	 * 检验账号是否存在
	 * 
	 * @param result
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	public Result findUserIsRegister(Result result, String loginName) throws Exception {
		Object[] objects = { loginName };
		String hql = "select count(1) from UserPo where loginName = ?";
		Long count = this.findUnique(hql, objects);
		result.setData(count);
		return result;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param result
	 * @param user
	 * @param userId
	 * @param roleId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Result findUserByUser(Result result, UserVo user, Long userId, Long roleId, Page<UserPo> page) throws Exception {
		StringBuffer hql = new StringBuffer("from UserPo where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (user != null) {
			if (user.getStatus() != null) {
				hql.append(" and status=?");
				params.add(user.getStatus());
			}
			if (StringUtils.isNotBlank(user.getLoginName())) {
				hql.append(" and (loginName like ? or userName like ?)");
				params.add("%" + user.getLoginName() + "%");
				params.add("%" + user.getLoginName() + "%");
			}

			if (roleId != 1) {// 非管理员登录
				if (null != userId) {
					hql.append(" and id <>?");
					params.add(userId);
				}
				if (null != user.getBizId()) {
					hql.append(" and id in (select userId from UserBusinessPo where  bizId =?)");
					params.add(user.getBizId());
				}
				hql.append(" and isManager <>1");
			}
			if (null != user.getRoleType() && user.getRoleType() > 0) {
				hql.append(" and id in (select userId from UserBusinessPo where  accountType =?)");
				params.add(user.getRoleType());
			}
			if (null != user.getRoleId() && user.getRoleId() > 0) {
				hql.append(" and id in (select userId from SysUserRolePo where  roleId =?)");
				params.add(user.getRoleId());
			}
			if (null != user.getGroupId() && user.getGroupId() > 0) {
				hql.append(" and id in (select userId from SysUserGroupPo where groupId =?)");
				params.add(user.getGroupId());
			}

		}
		hql.append(" order by createDate desc ");
		findPagination = this.findPagination(page, hql.toString(), params.toArray());
		result.setData(findPagination);
		return result;
	}

	
	/**
	 * 查询供应商、县域账号
	 * 
	 * @param result
	 * @param user
	 * @param userId
	 * @param roleId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Result findUserByAccountType(Result result, UserVo user, Page<UserPo> page) throws Exception {
		StringBuffer hql = new StringBuffer("from UserPo where isManager=1 ");
		List<Object> params = new ArrayList<Object>();
		if (user != null) {
			if (StringUtils.isNotBlank(user.getLoginName())) {
				hql.append(" and (loginName like ? or userName like ?)");
				params.add("%" + user.getLoginName() + "%");
				params.add("%" + user.getLoginName() + "%");
			}
			if (null != user.getRoleType() && user.getRoleType() > 0) {
				hql.append(" and id in (select userId from UserBusinessPo where  accountType =?)");
				params.add(user.getRoleType());
			}else{//查询供应商和县域账号
				hql.append(" and id in (select userId from UserBusinessPo where  accountType =3 or accountType=5)");
			}
		}
		hql.append(" order by createDate desc ");
		findPagination = this.findPagination(page, hql.toString(), params.toArray());
		result.setData(findPagination);
		return result;
	}
	
	/**
	 * 修改状态
	 * 
	 * @param result
	 * @param userId
	 * @param status
	 * @param updateBy
	 * @return
	 * @throws Exception
	 */
	public Result updateUserStatus(Result result, Long userId, Integer status, Long updateBy) throws Exception {
		String hql = "update UserPo set status=?,updateBy=?,updateDate=? where id=?";
		Object[] objects = { status, updateBy, new Date(), userId };
		Integer results = this.updateHql(hql, objects);
		if (results < 0) {
			result.setMsg("更新失败");
			result.setStatus(500);
			throw new FunctionException(result, "");
		}
		result.setData(results);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Result findUserByUserId(Result result, Long id) throws Exception {
		Object[] objects = { id };
		StringBuffer hql = new StringBuffer("from UserPo where id=?");
		UserPo userPo = this.findUnique(hql.toString(), objects);
		result.setData(userPo);
		return result;
	}

	/**
	 * 
	 * @param result
	 * @param userIds
	 * @return
	 * @throws Exception
	 */
	public Result findUserByUserIds(Result result, List<Long> userIds) throws Exception {

		if (userIds == null || userIds.size() == 0) {
			result.setData(null);
			return result;
		}
		Query query = this.createQuery("from UserPo where id  in(:idList)");
		query.setParameterList("idList", userIds);
		List<UserPo> list = query.list();
		result.setData(list);

		return result;
	}

	/**
	 * 根据用户ids修改资金账户
	 * 
	 * @param result
	 * @param userId
	 * @param masteraccId
	 * @return
	 * @throws Exception
	 */
	public Result findUserMasteraccId(Result result, List<Long> userIds, Long masteraccId) throws Exception {

		Query query = this.createQuery("from UserPo where id  in(:idList)");
		query.setParameterList("idList", userIds);
		List<UserPo> list = query.list();
		for (UserPo userPo : list) {
			userPo.setMasteraccId(masteraccId);
		}
		result.setData(list);
		return result;
	}
}
