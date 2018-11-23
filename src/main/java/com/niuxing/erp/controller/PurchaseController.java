package com.niuxing.erp.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.niuxing.auc.controller.BaseController;
import com.niuxing.erp.service.PurchaseService;
import com.niuxing.erp.vo.PurchaseSkuVo;
import com.niuxing.erp.vo.PurchaseVo;
import com.niuxing.erp.vo.SupplierVo;
import com.niuxing.util.DownloadUtils;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;
import com.zengfa.platform.util.bean.ReturnDTO;
import com.zengfa.platform.util.bean.ReturnStatusEnum;
import com.zengfa.platform.util.exception.FunctionException;
import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.web.userinfo.UserUtil;

@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseController{

	@Autowired
	private PurchaseService purchaseService;
	
	@RequestMapping( value = "getPurchasePage.do", method = RequestMethod.GET )
    @ResponseBody
    public Pagination getPurchasePage( PurchaseVo purchaseVo , int rows, int page) {
        Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        }

        try {
            result = purchaseService.getPurchasePage(result, purchaseVo, page, rows);
            return result.getData();
        } catch ( Exception e ) {
            logger.error( "\n获取采购单分页列表数据异常", e );
            result = this.error( result, e );
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping(value = "/getPurchaseNo.do")
	@ResponseBody
	public ReturnDTO getPurchaseNo(HttpServletRequest request) {
		Result result = getResult();
		if (result.getUserInfo() == null) {// 校验用户是否登录
			return result.getData();
		}
		try {
			result = purchaseService.createPurchaseNo(result);
		} catch (Exception e) {
			logger.error("获取采购单号数据异常", e);
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
	@RequestMapping( value = "addPurchase.do", method = RequestMethod.POST )
	@ResponseBody
    public ReturnDTO addPurchase( PurchaseVo addVo ) {
        Result result = getResult();
        if ( result.getUserInfo()  == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }

        try {
            result = purchaseService.addPurchase( result, addVo );
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n保存采购单异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "保存采购单失败!");
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping( value = "getPurchase.do", method = RequestMethod.GET )
    @ResponseBody
    public ReturnDTO getPurchase( Integer id ) {
    	Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }
        try {
            result = purchaseService.getPurchase(result, id);
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n获取采购单异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "获取采购单失败" );
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
			result = purchaseService.changeStatus(result, id, status, loginInfo.getUserId());
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}
	
	@ResponseBody
	@RequestMapping(value = "/import.do", method = RequestMethod.POST)
	public ReturnDTO importPurchase(@RequestParam(value = "file", required = false) MultipartFile filedata, HttpServletRequest request, HttpServletResponse response) {
		Result result = getResult();
		try {
			if(filedata.isEmpty()){
				throw new FunctionException(result, "未获取到文件");
			}
			List<List<String>> dataList = com.niuxing.util.ExcelUtil.getExcelData(filedata);
			result = purchaseService.importData(result, dataList);
		} catch (Exception e) {
			result.setStatus(500);
			result = this.error(result, e);
		}finally{
		}
		return result.DTO();
	}
	
	@RequestMapping(value = "/download.do")
	@ResponseBody
	public void download(PurchaseVo purchaseVo, HttpServletRequest request, HttpServletResponse response) {
		Result result = this.getResult(request);
		OutputStream outputStream = null;
		try {
			result = purchaseService.exportExcel(result, purchaseVo);
			File f = result.getData();
			DownloadUtils.downloadExcel(request, response, f, "采购单报表");
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			IOUtils.closeQuietly(outputStream);
			this.send(result);
		}
	}
	
	@RequestMapping( value = "getSkuListPage.do", method = RequestMethod.GET )
    @ResponseBody
    public Pagination getSkuListPage( PurchaseSkuVo purchaseSkuVo , int rows, int page) {
        Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        }

        try {
            result = purchaseService.getPurchaseSkuPage(result, purchaseSkuVo, page, rows);
            return result.getData();
        } catch ( Exception e ) {
            logger.error( "\n获取采购单SKU分页列表数据异常", e );
            result = this.error( result, e );
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        } finally {
            this.send( result );
        }
    }
	
}
