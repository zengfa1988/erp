package com.niuxing.erp.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.niuxing.auc.controller.BaseController;
import com.niuxing.erp.po.SupplierPo;
import com.niuxing.erp.service.SupplierService;
import com.niuxing.erp.vo.ImgVo;
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
@RequestMapping("/supplier")
public class SupplierController extends BaseController{

	private Logger logger = LoggerFactory.getLogger( SupplierController.class );
	
	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping( value = "getSupplierPage.do", method = RequestMethod.GET )
    @ResponseBody
    public Pagination getSupplierPage( SupplierVo supplierVo , int rows, int page) {
        Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        }

        try {
            result = supplierService.getSupplierPage(result, supplierVo, page, rows);
            return result.getData();
        } catch ( Exception e ) {
            logger.error( "\n获取供应商分页列表数据异常", e );
            result = this.error( result, e );
            return Pagination.createPagination( 0, ListUtils.EMPTY_LIST );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping(value = "/addSupplier.do")
	@ResponseBody
	public ReturnDTO addSupplier(HttpServletRequest request, SupplierVo supplierVo) {
		Result result = getResult();
		if (result.getUserInfo() == null) {// 校验用户是否登录
			return result.getData();
		}
		try {
			List<ImgVo> imgVos = JSONArray.parseArray(request.getParameter("imgs"), ImgVo.class);
			result = supplierService.addSupplier(result, supplierVo, imgVos);
		} catch (Exception e) {
			logger.error("保存供应商数据异常", e);
			result = this.error(result, e);
		} finally {
			this.send(result);
		}
		return result.DTO();
	}
	
	@RequestMapping( value = "getSupplier.do", method = RequestMethod.GET )
    @ResponseBody
    public ReturnDTO getSupplier( Long supplierId ) {
    	Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }
        try {
            result = supplierService.getSupplier(result, supplierId);
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n获取供应商异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "获取供应商失败" );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping( value = "editSupplier.do", method = RequestMethod.POST )
    @ResponseBody
    public ReturnDTO editSupplier( HttpServletRequest request,SupplierVo addVo ) {
        Result result = getResult();
        if ( result.getUserInfo() == null ) {// 校验用户是否登录
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), "未登录！" );
        }

        try {
        	List<ImgVo> imgVos = JSONArray.parseArray(request.getParameter("imgs"), ImgVo.class);
            result = supplierService.editSupplier( result, addVo,imgVos );
            return result.DTO();
        } catch ( Exception e ) {
            logger.error( "\n保存供应商异常", e );
            result = this.error( result, e );
            return ReturnDTO.NO( ReturnStatusEnum.Error.getValue(), result.getMsg() );
        } finally {
            this.send( result );
        }
    }
	
	@RequestMapping(value = "/changeStatus.do", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO changeStatus(HttpServletRequest request, Long id, Integer status) {
		Result result = this.getResult(request);
		UserInfo loginInfo = UserUtil.getUserInfo(request);
		if (loginInfo == null) {
			result.setStatus(500);
			result.setMsg("登录信息失效");
			return result.DTO();
		}
		try {
			result = supplierService.changeStatus(result, id, status, loginInfo.getUserId());
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			send(result);
		}
		return result.DTO();
	}
	
	@RequestMapping(value = "/download.do")
	@ResponseBody
	public void download(SupplierVo supplierVo, HttpServletRequest request, HttpServletResponse response) {
		Result result = this.getResult(request);
		OutputStream outputStream = null;
		try {
			result = supplierService.exportExcel(result, supplierVo);
			File f = result.getData();
			DownloadUtils.downloadExcel(request, response, f, "供应商报表");
		} catch (Exception e) {
			result = this.error(result, e);
		} finally {
			IOUtils.closeQuietly(outputStream);
			this.send(result);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/import.do", method = RequestMethod.POST)
	public ReturnDTO importSupplier(@RequestParam(value = "file", required = false) MultipartFile filedata, HttpServletRequest request, HttpServletResponse response) {
		Result result = getResult();
		try {
			if(filedata.isEmpty()){
				throw new FunctionException(result, "未获取到文件");
			}
			List<List<String>> dataList = com.niuxing.util.ExcelUtil.getExcelData(filedata);
			result = supplierService.importData(result, dataList);
		} catch (Exception e) {
			result.setStatus(500);
			result = this.error(result, e);
		}finally{
		}
		return result.DTO();
	}
	
	@RequestMapping( value = "getSupplierSelect.do", method = RequestMethod.GET )
    @ResponseBody
    public Object getSupplierSelect() {
        Result result = getResult();
        try {
            result = supplierService.getListBySelect( result );
            return result.getData();
        } catch ( Exception e ) {
            logger.error( "\n获取供应商列表数据异常", e );
            result = this.error( result, e );
        } finally {
            this.send( result );
        }
        return null;
    }
	
}
