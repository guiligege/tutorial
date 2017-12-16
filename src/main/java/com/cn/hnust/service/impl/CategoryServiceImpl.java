package com.cn.hnust.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.hnust.domain.Category;
import com.cn.hnust.domain.CategoryDO;
import com.cn.hnust.mongo.DomainConstans;
import com.cn.hnust.mongo.MongoService;
import com.cn.hnust.service.ICategoryService;
import com.cn.hnust.utils.ShortUrlUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:类目
 * User: zhengdongxiao
 * Date: 2017/10/5
 * Time: 上午1:36
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    private static final String CATEGORY_TABLE = DomainConstans.mongodb_category_collectionName;

    @Autowired
    private MongoService mongoService;

    @Override
    public int addCategory(Category category) {

        //对象转json
        String jsonString= JSON.toJSONString(category);

        //存mongo
        mongoService.insertMany(jsonString, CATEGORY_TABLE);

        return 1;
    }

    @Override
    public List<Category> selectAllCategory() {

        List<Category> categoryList = Lists.newArrayList();

        List<Document>  docs = mongoService.getAllCategory(CATEGORY_TABLE);

        if(CollectionUtils.isEmpty(docs)){
            return categoryList;
        }
        //转换doc
        for(Document doc:docs){
            String jsonString = doc.toJson();
            JSONObject jsonObject= JSON.parseObject(jsonString);
            Category category = JSONObject.toJavaObject(jsonObject, Category.class);
            categoryList.add(category);
        }

        return categoryList;
    }

    @Override
    public List<CategoryDO> selectAllCategoryDO() {

        List<CategoryDO> categoryDOList = Lists.newArrayList();
        List<Category> categoryList = selectAllCategory();
        //先设置父节点
        for (Category category:categoryList){
            if(category.getParent_id() == 0){
                CategoryDO categoryDO = new CategoryDO();
                categoryDO.setCategory(category);
                categoryDOList.add(categoryDO);
            }
        }

        //设置子节点
        for (CategoryDO categoryDO:categoryDOList){
            List<Category> children = Lists.newArrayList();

            for (Category category:categoryList){
                if(category.getParent_id() == categoryDO.getCategory().getId()){
                    children.add(category);
                }
            }
            categoryDO.setChildren(children);
        }

        return categoryDOList;
    }


}
