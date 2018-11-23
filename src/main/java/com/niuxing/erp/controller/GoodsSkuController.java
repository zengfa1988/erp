package com.niuxing.erp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.niuxing.auc.controller.BaseController;
import com.niuxing.erp.service.GoodsSkuService;
import com.niuxing.erp.vo.GoodsSkuVo;
import com.niuxing.erp.vo.ImgVo;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.bean.ReturnDTO;
import com.zengfa.platform.util.bean.ReturnStatusEnum;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.web.userinfo.UserUtil;

@Controller
@RequestMapping("/goods/sku")
public class GoodsSkuController extends BaseController{

	@Autowired
	private GoodsSkuService goodsSkuService;
	
	@RequestMapping( value = "getListPage.do", method = RequestMethod.GET )
    @ResponseBody
    public Pagination getListPage( GoodsSkuVo goodsSkuVo,int rows, int page) {
        Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        }

        try {
            result = goodsSkuService.getListPage(result,goodsSkuVo, page, rows);
            return result.getData();
        } catch ( Exception e ) {
            logger.error( "\n获取sku分页列表数据异常", e );
            result = this.error( result, e );
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping(value = "addSku.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO addSku(HttpServletRequest request, GoodsSkuVo goodsSkuVo) {
		Result result = getResult();
		if (result.getUserInfo() == null) {// 校验用户是否登录
			return result.getData();
		}
		try {
			List<ImgVo> imgVos = JSONArray.parseArray(request.getParameter("imgs"), ImgVo.class);
			if(goodsSkuVo.getId() == null){
				result = goodsSkuService.addSupplier(result, goodsSkuVo, imgVos);
			}else{
				result = goodsSkuService.editSupplier(result, goodsSkuVo, imgVos);
			}
		} catch (Exception e) {
			logger.error("保存sku数据异常", e);
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
	@RequestMapping( value = "getSku.do", method = RequestMethod.GET )
    @ResponseBody
    public ReturnDTO getSku( Integer id ) {
    	Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }
        try {
            result = goodsSkuService.getSku(result, id);
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n获取sku异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "获取sku失败" );
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
			result = goodsSkuService.changeStatus(result, id, status, loginInfo.getUserId());
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}
	
	@ResponseBody
	@RequestMapping(value = "/import.do", method = RequestMethod.POST)
	public ReturnDTO importSku(@RequestParam(value = "file", required = false) MultipartFile filedata, HttpServletRequest request, HttpServletResponse response) {
		Result result = getResult();
		try {
			if(filedata.isEmpty()){
				throw new FunctionException(result, "未获取到文件");
			}
			List<List<String>> dataList = com.niuxing.util.ExcelUtil.getExcelData(filedata);
			result = goodsSkuService.importData(result, dataList);
		} catch (Exception e) {
			result.setStatus(500);
			result = this.error(result, e);
		}finally{
		}
		return result.DTO();
	}
	
}
