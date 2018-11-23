package com.niuxing.erp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niuxing.auc.controller.BaseController;
import com.niuxing.erp.service.PaymentService;
import com.niuxing.erp.vo.PaymentVo;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.bean.ReturnDTO;
import com.zengfa.platform.util.bean.ReturnStatusEnum;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.web.userinfo.UserUtil;

@Controller
@RequestMapping("/payment")
public class PaymentController extends BaseController{

	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping( value = "getListPage.do", method = RequestMethod.GET )
    @ResponseBody
    public Pagination getListPage( PaymentVo paymentVo , int rows, int page) {
        Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        }

        try {
            result = paymentService.getListPage(result, paymentVo, page, rows);
            return result.getData();
        } catch ( Exception e ) {
            logger.error( "\n获取付款单分页列表数据异常", e );
            result = this.error( result, e );
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping(value = "/addPayment.do")
	@ResponseBody
	public ReturnDTO addPayment(HttpServletRequest request, PaymentVo paymentVo) {
		Result result = getResult();
		if (result.getUserInfo() == null) {// 校验用户是否登录
			return result.getData();
		}
		try {
			result = paymentService.addPayment(result, paymentVo);
		} catch (Exception e) {
			logger.error("保存付款单数据异常", e);
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
	@RequestMapping( value = "getPayment.do", method = RequestMethod.GET )
    @ResponseBody
    public ReturnDTO getPayment(Integer id ) {
    	Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }
        try {
            result = paymentService.getPayment(result, id);
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n获取付款单异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "获取付款单失败" );
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
			result = paymentService.changeStatus(result, id, status, loginInfo.getUserId());
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}
}
