package com.niuxing.erp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.niuxing.auc.controller.BaseController;
import com.niuxing.erp.service.GoodsCategoryService;
import com.niuxing.erp.vo.GoodsCategoryVo;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.bean.ReturnDTO;
import com.zengfa.platform.util.bean.ReturnStatusEnum;

@Controller
@RequestMapping("/goods/category")
public class GoodsCategoryController extends BaseController{

	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	@RequestMapping( value = "getListPage.do", method = RequestMethod.GET )
    @ResponseBody
    public Pagination getListPage( int rows, int page) {
        Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        }

        try {
            result = goodsCategoryService.getListPage(result, page, rows);
            return result.getData();
        } catch ( Exception e ) {
            logger.error( "\n获取品类分页列表数据异常", e );
            result = this.error( result, e );
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping(value = "/addCategory.do")
	@ResponseBody
	public ReturnDTO addCategory(HttpServletRequest request, GoodsCategoryVo goodsCategoryVo) {
		Result result = getResult();
		if (result.getUserInfo() == null) {// 校验用户是否登录
			return result.getData();
		}
		try {
			if(goodsCategoryVo.getId() == null){
				result = goodsCategoryService.addCategory(result, goodsCategoryVo);
			}else{
				result = goodsCategoryService.editCategory(result, goodsCategoryVo);
			}
		} catch (Exception e) {
			logger.error("保存品类数据异常", e);
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
	@RequestMapping( value = "getCategory.do", method = RequestMethod.GET )
    @ResponseBody
    public ReturnDTO getCategory( Integer id ) {
    	Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }
        try {
            result = goodsCategoryService.getCategory(result, id);
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n获取品类异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "获取品类失败" );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping( value = "getCategorySelect.do", method = RequestMethod.GET )
    @ResponseBody
    public Object getCategorySelect() {
        Result result = getResult();
        try {
            result = goodsCategoryService.getListBySelect( result );
            return result.getData();
        } catch ( Exception e ) {
            logger.error( "\n获取品类列表数据异常", e );
            result = this.error( result, e );
        } finally {
            this.send( result );
        }
        return null;
    }
	
}
