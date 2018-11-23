package com.niuxing.auc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.niuxing.auc.dao.SysRoleDao;
import com.niuxing.auc.dao.SysUserRoleDao;
import com.niuxing.auc.dao.UserDao;
import com.niuxing.auc.po.SysUserRolePo;
import com.niuxing.auc.po.UserPo;
import com.niuxing.auc.vo.LoginUserVo;
import com.niuxing.util.MessageResource;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.DigestUtils;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.util.security.UserResMenu;

/**
 * 
 * @author ds
 *
 */
@Service
@SuppressWarnings("unchecked")
public class UserLoginService {

	@Resource
	UserDao userDao;
	@Resource
	SysRoleDao sysRoleDao;
	@Resource
	SysUserRoleDao sysUserRoleDao;
	@Resource
	SysResourcesService sysResourcesService;
	@Resource
	SysRoleResourcesService sysRoleResourcesService;
	@Resource private SysUserRoleService sysUserRoleService;
	@Resource private OperatePwdLogService operatePwdLogService;
//	@Resource private LoginLogService loginLogService;
//	@Resource private SysUserService sysUserService;
	
	/**
	 * 验证用户名和密码是否正确
	 * @param result
	 * @param loginName
	 * @param password
	 * @param loginIp
	 * @return
	 * @throws Exception
	 */
	public Result validatePassword(Result result, String loginName, String password, String loginIp) throws Exception {
		LoginUserVo userVo = new LoginUserVo();
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {//防止密码处理后产生空值
			result.setStatus(500);
			result.setMsg("账号不存在或密码错误！");
			result.setData(userVo);
			return result;
		}
		UserPo userPo = null;
		result = userDao.getUserByLoginName(result, loginName);
		if (result.getData() == null) {
			
		}else{
			userPo = (UserPo) result.getData();
		}
		if(userPo == null){
			// 记录登录错误次数
//			Integer loginCode = (Integer) SerializeUtil.unserialize(RedisSlave.getInstance().get(UserConstants.REDIS_KEY_LEAF_LOGIN_ERROR_ID + loginName));
//			if (loginCode == null) {
//				loginCode = 0;
//			}
//			userVo.setErrorCount(Long.valueOf(loginCode + 1));
//			RedisSlave.getInstance().setObject(UserConstants.REDIS_KEY_LEAF_LOGIN_ERROR_ID + loginName, SerializeUtil.serialize(loginCode + 1),
//					UserConstants.LOGIN_CODE_EXPIRE_TIME);
			result.setStatus(500);
			result.setMsg("账号不存在或密码错误");
			result.setData(userVo);
			return result;
		}else{
			result.OK(userPo);
		}

		if (userPo.getStatus() == 0) {
			result.setStatus(500);
			result.setMsg("账号已停用");
			result.setData(userVo);
			return result;
		}
		
		boolean bl=checkPassWord(password, userPo.getId(), userPo.getLoginPwd());
		if(bl){
			result = getUserInfo(result, userVo, userPo,loginIp);
			return result;
		}

		result.setStatus(500);
		result.setMsg("账号不存在或密码错误");
		result.setData(userVo);
		return result;
	}
	
	/**
	 * 登录成功获取信息
	 * @param result
	 * @param userVo
	 * @param userPo
	 * @param loginIp
	 * @param loginType	1PC登入，2APP登入
	 * @return
	 * @throws Exception
	 */
	public Result getUserInfo(Result result, LoginUserVo userVo, UserPo userPo,String loginIp) throws Exception {// 获取管理员名称

		PropertyUtils.copyProperties(userVo, userPo);
		userVo.setLoginName(userPo.getUserName());
		
		// 登录成功清除redis缓存
//		RedisSlave.getInstance().del(UserConstants.REDIS_KEY_LEAF_LOGIN_ERROR_ID + userPo.getUserName());
//		userPo.setErrorCount((long) 0);// 登录成功修改登录错误次数为0
//		userVo.setCertificate(userPo.getIsCertificate());
//		userVo.setNet(userPo.getIsNet());
		userVo.setUserId(userPo.getId());
//		Integer roleType = sysUserRoleService.getUserRoleType(result, userPo.getId());
//		userVo.setRoleType(roleType);
//		userVo.setManager(userPo.getIsManager());

		result = sysUserRoleDao.getUserRoleByUserId(result, userVo.getUserId());
		List<SysUserRolePo> queryForLis = (List<SysUserRolePo>) result.getData();
		if (queryForLis == null || queryForLis.isEmpty()) {
			result.setStatus(500);
			result.setMsg("无效账号！");
			result.setData(null);
			return result;
		}
		SysUserRolePo sysUserRolePo = queryForLis.get(0);// 获取用户角色关系
		userVo.setRoleId(sysUserRolePo.getRoleId());
		result = getUserResourcesMenu(result, userVo, userPo);
		
		Long loginCount = userPo.getLoginCount();
		if (loginCount == null) {
			loginCount = (long) 0;
		}
//		Date date = new Date();
//		Date gmtdate = userPo.getLoginTime();// 上次登录时间
//		if (gmtdate == null) {
//			gmtdate = new Date();
//		}
//		userVo.setGmtLast(gmtdate);
//		userVo.setLoginTime(date);
//		userVo.setLastIp(userPo.getLoginIp());
		userVo.setLoginIp(loginIp);
		userVo.setLoginCount(loginCount + 1);
		userPo.setLoginCount(loginCount + 1);
//		userPo.setLoginIp(loginIp);
//		userPo.setLoginTime(date);
//		userPo.setGmtLast(gmtdate);
		result.setData(userVo);
		return result;

	}

	/**
	 * 获取菜单权限
	 * @param result
	 * @param userVo
	 * @param userPo
	 * @return
	 * @throws Exception
	 */
	private Result getUserResourcesMenu(Result result, LoginUserVo userVo, UserPo userPo) throws Exception {// 获取管理员名称
		List<UserResMenu> allList = new ArrayList<UserResMenu>();
		result = sysResourcesService.findResListByProSerivce(result, null);// 获取所有资源
		if(result!=null&&result.getData()!=null){
			allList=result.getData();
		}
		// 缓存用户登录的总菜单，再次登录之前不受影响
//		RedisSlave.getInstance().setObject(SessionKey.REDIS_KEY_ALL_MUEN_VALUE + userVo.getUserId(), SerializeUtil.serialize(allList), SessionKey.REDIS_KEY_BASE_TIME);
		List<UserResMenu> loginList = new ArrayList<UserResMenu>();
		Integer roleType = userVo.getRoleType();
		Long roleIdIsManager = userVo.getRoleId();
		result = sysRoleResourcesService.querySysRoleResourcesByRoleIdService(result, userVo.getRoleId(),userVo.getUserId());// 通过角色id获取资源id
		List<Long> resList = (List<Long>) result.getData();
		for (UserResMenu userResMenu : allList) {
			for (Long str : resList) {
				if (str.equals(userResMenu.getId())) {
					loginList.add(userResMenu);
				}
			}
		}
		userVo.setResourcesMenu(loginList);
		return result;
	}

	/**
	 * 修改用户密码
	 * @param result
	 * @param newPassword
	 * @param id
	 * @return
	 * @throws Exception
	 */
	
	public Result updatePassword(Result result, String newPassword, Long id) throws Exception {
		// 新密码加
		String digestPassword = DigestUtils.sha1Hex(newPassword + id);
		result = userDao.updatePwd(result, digestPassword, id);
		return result;
	}
	
	public Result recoverPwd(Result result,UserInfo loginInfo,String phone,String newPassword) throws Exception{
		UserPo sysUserPo = userDao.get(loginInfo.getUserId());
		if(sysUserPo == null){
			String msg = "用户不存在！";
			result.setStatus(500);
			result.setMsg(msg);
			result.setData(null);
			return result;
		}
		String digestPassword = DigestUtils.sha1Hex(newPassword + sysUserPo.getId());
		result = userDao.updatePwd(result, digestPassword, sysUserPo.getId());
		operatePwdLogService.saveSysPwdOperateLog(result, loginInfo, sysUserPo.getId(),1, "找回密码（浏览器手机验证）");
		
		return result;
	}

	
	public  boolean checkPassWord(String passWord,Long id,String loginPwd){
		if (StringUtils.isBlank(passWord) ||id==null||StringUtils.isBlank(loginPwd)) {
			return false;
		}
		String digestPassword = DigestUtils.sha1Hex(passWord + id);
		if (digestPassword!=null&&digestPassword.equals(loginPwd)) {// 密码验证
			return true;
		}
//		digestPassword = MD5Util.getMD5(passWord);
//		if (digestPassword!=null&&digestPassword.equals(loginPwd)) {// 密码验证
//			return true;
//		}
		digestPassword = DigestUtils.sha1Hex(passWord);
		if (digestPassword!=null&&digestPassword.equals(loginPwd)) {// 密码验证
			return true;
		}
		return false;
	}
	
}
