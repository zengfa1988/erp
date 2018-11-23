package com.niuxing.erp.task;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.niuxing.erp.service.TransService;
import com.niuxing.erp.vo.TransProgressVo;
import com.niuxing.erp.vo.TransVo;
import com.niuxing.util.kuaidi.UpsKuaidiSearch;
import com.zengfa.platform.util.bean.Pagination;
import com.zengfa.platform.util.bean.Result;

@Component
public class TransTask {

	private static Logger logger = LoggerFactory.getLogger(TransTask.class);
	
	@Autowired
	private TransService transService;
	
	@Scheduled(cron = "0 0 23 * * ?")//每天晚上11点执行执行
//	@Scheduled(cron = "* 0/5 * * * ?")//每天晚上11点执行执行
	public void crawlKudai() throws Exception{
		logger.info("快递单号物流查询定时任务开始");
		Result result = new Result();
		TransVo transVo = new TransVo();
		transVo.setStatus(1);
		Pagination pagination = transService.getListPage(result, transVo, 1, 50).getData();
		List<TransVo> voList = (List<TransVo>)pagination.getRows();
		if(CollectionUtils.isEmpty(voList)){
			return;
		}
		for(TransVo vo : voList){
			String expressNo = vo.getExpressNo();
			Integer expressCompany = vo.getExpressCompany();
			if(StringUtils.isBlank(expressNo) || expressCompany == null){
				continue;
			}
			if(expressCompany == 1){
				List<TransProgressVo> transProgressVoList = UpsKuaidiSearch.search(expressNo);
				transService.addProgress(result, transProgressVoList, vo);
			}
			Thread.sleep(1000*60);
		}
	}
}
