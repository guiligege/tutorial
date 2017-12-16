package com.cn.hnust.service;

import com.cn.hnust.domain.Tutorial;
import com.cn.hnust.domain.TutorialDO;

import java.util.List;

/**
 * 教程接口
 */
public interface ITutorialService {

	//新增教程
	public int addTutorial(Tutorial tutorial);

	//更新教程
	public int updateTutorial(Tutorial tutorial);

	//查询类目id，对应的教程列表
	public TutorialDO selectByCateIdAndShortUrl(String cateName, String short_url) ;

	public Tutorial getCurrentTutorial(String short_url);

	//查询类目id，对应的教程列表
	public  List<Tutorial>  selectByCate(String cateName) ;
}
