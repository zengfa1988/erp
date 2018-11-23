package com.niuxing.erp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niuxing.auc.controller.BaseController;
import com.niuxing.erp.service.TransService;
import com.niuxing.erp.vo.TransProgressVo;
import com.niuxing.erp.vo.TransVo;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.bean.ReturnDTO;
import com.zengfa.platform.util.bean.ReturnStatusEnum;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.web.userinfo.UserUtil;

@Controller
@RequestMapping("/trans")
public class TransController extends BaseController{

	@Autowired
	private TransService transService;
	
	@RequestMapping( value = "getListPage.do", method = RequestMethod.GET )
    @ResponseBody
    public Pagination getListPage( TransVo transVo , int rows, int page) {
        Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        }

        try {
            result = transService.getListPage(result, transVo, page, rows);
            return result.getData();
        } catch ( Exception e ) {
            logger.error( "\n获取发货单分页列表数据异常", e );
            result = this.error( result, e );
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping( value = "addSend.do", method = RequestMethod.POST )
	@ResponseBody
    public ReturnDTO addSend( TransVo addVo ) {
        Result result = getResult();
        if ( result.getUserInfo()  == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }

        try {
            result = transService.addSend( result, addVo );
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n保存发货单异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "保存发货单失败!");
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping( value = "getSend.do", method = RequestMethod.GET )
    @ResponseBody
    public ReturnDTO getSend( Integer id ) {
    	Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }
        try {
            result = transService.getSend(result, id);
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n获取发货单异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "获取发货单失败" );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping( value = "getProgress.do", method = RequestMethod.GET )
    @ResponseBody
    public ReturnDTO getProgress( Integer id ) {
    	Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }
        try {
            result = transService.getProgress(result, id);
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n获取物流进度异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "获取物流进度失败" );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping( value = "addProgress.do", method = RequestMethod.POST )
	@ResponseBody
    public ReturnDTO addProgress( TransProgressVo addVo ) {
        Result result = getResult();
        if ( result.getUserInfo()  == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }

        try {
            result = transService.addProgress( result, addVo );
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n保存物流进度异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "保存物流进度失败!");
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping(value = "/changeStatus.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO changeStatus(HttpServletRequest request, Integer id, Integer status) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			result = transService.changeStatus(result, id, status, loginInfo.getUserId());
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}
}
