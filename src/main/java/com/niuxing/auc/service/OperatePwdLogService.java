package com.niuxing.auc.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.niuxing.auc.dao.SysPwdOperateLogDao;
import com.niuxing.auc.dao.UserDao;
import com.niuxing.auc.po.SysPwdOperateLogPo;
import com.niuxing.auc.po.UserPo;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.security.UserInfo;

@Service
public class OperatePwdLogService {
	@Resource
	SysPwdOperateLogDao sysPwdOperateLogDao;
	@Resource
	UserDao userDao;

	/**
	 * 添加密码修改记录
	 * @param result
	 * @param loginInfo
	 * @param userId
	 * @param operateMode
	 * @return
	 * @throws Exception
	 */
	public Result saveSysPwdOperateLog(Result result, UserInfo loginInfo, Long userId,Integer  operateType,String operateMode) throws Exception {
		if (loginInfo == null || userId == null) {
			result.setStatus(500);
			result.setMsg("参数为空！");
			result.setData(null);
			return result;
		}
		result = userDao.getUser(result, userId);
		UserPo userPo = result.getData();
		if (userPo == null) {
			result.setStatus(500);
			result.setMsg("修改用户为空");
			result.setData(null);
			return result;
		}

		SysPwdOperateLogPo sysPwdOperateLogPo = new SysPwdOperateLogPo();
		sysPwdOperateLogPo.setOperateAddress("");
		sysPwdOperateLogPo.setOperateIp(loginInfo.getLoginIp());
		sysPwdOperateLogPo.setOperateMode(operateMode);
		sysPwdOperateLogPo.setOperateDate(new Date());
		if(loginInfo.getUserId()==null){
			sysPwdOperateLogPo.setOperateUserId(userPo.getId());
			sysPwdOperateLogPo.setOperateUserName(userPo.getLoginName());

		}else{
			sysPwdOperateLogPo.setOperateUserId(loginInfo.getUserId());
			sysPwdOperateLogPo.setOperateUserName(loginInfo.getLoginName());
		}
		sysPwdOperateLogPo.setUserId(userPo.getId());
		sysPwdOperateLogPo.setLoginName(loginInfo.getLoginName());
		result = sysPwdOperateLogDao.findSysPwdOperateLogId(result, userId);
		List<Long> list = result.getData();
		if (list != null && list.size() >98 ) {//记录保留100条，超出后删除
			for (int i = 98; i < list.size(); i++) {
				Long id = list.get(i);
				result = sysPwdOperateLogDao.delSysPwdOperateLog(result, id);
			}
			result.setStatus(200);
		}

		result = sysPwdOperateLogDao.saveSysPwdOperateLog(result, sysPwdOperateLogPo);
		result.setStatus(200);
		return result;
	}
	
	public Result findSysPwdOperateLog(Result result,Long userId) throws Exception{
		result = sysPwdOperateLogDao.findSysPwdOperateLog(result, userId);
		List<SysPwdOperateLogPo> list= result.getData();
		return result;
	}
	
	public Result findSysPwdOperateLog(Result result,String loginName) throws Exception{
		result = sysPwdOperateLogDao.findSysPwdOperateLog(result, loginName);
		List<SysPwdOperateLogPo> list= result.getData();
		return result;
	}
	
}
