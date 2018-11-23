package com.niuxing.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niuxing.erp.dao.SequenceDao;
import com.niuxing.erp.po.SequencePo;
import com.zengfa.platform.util.DateTime;

@Service
public class SequenceService {

	@Autowired
	private SequenceDao sequenceDao;
	
	/**
	 * 获取下一个序列编号
	 * @param seqId
	 * @return
	 */
	public String getNextSeq(Integer seqId,boolean needSave){
		SequencePo sequencePo = sequenceDao.get(seqId);
		if(sequencePo == null){
			return null;
		}
		if(sequencePo.getSeqType()==1){//按年递增
			Integer year = DateTime.getYear();
			String baseVal = sequencePo.getBaseval();
			Integer curVal = sequencePo.getCurval();
			Integer seqLen = sequencePo.getSeqLen();
			if(!baseVal.equals(year.toString())){
				baseVal = year.toString();
				curVal = 0;
			}
			curVal ++;
			String seq = baseVal + String.format("%0"+seqLen+"d", curVal);
			if(needSave){
				sequencePo.setBaseval(baseVal);
				sequencePo.setCurval(curVal);
				sequenceDao.update(sequencePo);
			}
			return seq;
		}else if(sequencePo.getSeqType()==2){//按年月递增
			Integer year = DateTime.getYear();
			Integer month = DateTime.getMonth();
			month++;
			Integer curVal = sequencePo.getCurval();
			Integer seqLen = sequencePo.getSeqLen();
			String baseVal = sequencePo.getBaseval();
			if(!baseVal.equals(year.toString()+month.toString())){
				baseVal = year.toString()+month.toString();
				curVal = 0;
			}
			curVal ++;
			String seq = baseVal + String.format("%0"+seqLen+"d", curVal);
			if(needSave){
				sequencePo.setBaseval(baseVal);
				sequencePo.setCurval(curVal);
				sequenceDao.update(sequencePo);
			}
			return seq;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
//		Integer year = DateTime.getYear();
//		System.out.println(year);
//		System.out.println("2018".equals(year.toString()));
		
		String baseVal = "2018";
		Integer curVal = 0;
		curVal ++;
		String seq = baseVal + String.format("%03d", curVal);
		System.out.println(seq);
	}
}
