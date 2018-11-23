package com.niuxing.util.kuaidi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.niuxing.erp.vo.TransProgressVo;
import com.zengfa.platform.util.DateUtil;
import com.zengfa.platform.util.HttpUtils;

public class UpsKuaidiSearch {

	public static String searchUrl = "https://www.kuaidi100.com/query?type=ups&postid=|postid|";
	
	public static List<TransProgressVo> search(String postid){
		String url = searchUrl.replace("|postid|", postid);
		String result = HttpUtils.getContent(url);
		JSONObject jSONObject = JSONObject.parseObject(result);
		JSONArray list = (JSONArray)jSONObject.get("data");
		List<TransProgressVo> voList = new ArrayList<TransProgressVo>();
		for(int i=0;i<list.size();i++){
			JSONObject o = list.getJSONObject(i); 
			TransProgressVo transProgressVo = new TransProgressVo();
			Date progressTime = DateUtil.str2Date(o.getString("ftime"));
			transProgressVo.setProgressTime(progressTime);
			transProgressVo.setContent(o.getString("context"));
			voList.add(transProgressVo);
		}
		return voList;
	}
	
	public static void main(String[] args) {
		List<TransProgressVo> voList = search("1ZA5136W0471401747");
		System.out.println(JSONArray.toJSONString(voList));
	}

}
