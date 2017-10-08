package com.cn.hnust.service;

import com.cn.hnust.domain.Category;
import com.cn.hnust.domain.CategoryDO;

import java.util.List;

/**
 * 类目接口
 */
public interface ICategoryService {

	//新增教程
	int addCategory(Category tutorial);

	//需要缓存类目
	List<Category> selectAllCategory();

	/**
	 * 获取父子结构
	 * @return
	 */
	List<CategoryDO> selectAllCategoryDO();
}
