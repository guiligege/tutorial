package com.cn.hnust.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.hnust.domain.Tutorial;
import com.cn.hnust.domain.TutorialDO;
import com.cn.hnust.mongo.DomainConstans;
import com.cn.hnust.mongo.MongoService;
import com.cn.hnust.service.ITutorialService;
import com.cn.hnust.utils.ShortUrlUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Description: 教程服务
 * User: zhengdongxiao
 * Date: 2017/10/5
 * Time: 上午1:10
 */
@Service("tutorialService")
public class TutorialServiceImpl implements ITutorialService {

    @Autowired
    private MongoService mongoService;
    private static final String TUTORIAL_TABLE = DomainConstans.mongodb_article_collectionName;

    @Override
    public int addTutorial(Tutorial tutorial) {

        //设置短链接
        tutorial.setShort_url(ShortUrlUtil.shortUrl(tutorial.getName()));

        //对象转json
        String jsonString= JSON.toJSONString(tutorial);

        //存mongo
        mongoService.insertMany(jsonString, TUTORIAL_TABLE);

        return 1;
    }

    @Override
    public int updateTutorial(Tutorial tutorial) {

        mongoService.updateTutorial(tutorial.getName(),tutorial.getDescription(),
                                    tutorial.getSort_id(),tutorial.getContent(),tutorial.getShort_url(), TUTORIAL_TABLE);
        return 1;
    }

    @Override
    public TutorialDO selectByCateIdAndShortUrl(String cateName, String short_url) {

        TutorialDO tutorialDO = new TutorialDO();
        if(StringUtils.isEmpty(cateName) && StringUtils.isEmpty(short_url)){
            return tutorialDO;
        }

        //获取文档
        List<Document> docs = mongoService.findByCateName(cateName,TUTORIAL_TABLE);

        if(CollectionUtils.isEmpty(docs)){
            return tutorialDO;
        }

        List<Tutorial> tutorialList = this.getOrderedList(docs);

        //如果short_url为空，那么使用类目的第一个元素
        if(StringUtils.isEmpty(short_url)){
            short_url = tutorialList.get(0).getShort_url();
        }

        //设置类目对应教程列表
        tutorialDO.setTutorialList(tutorialList);

        //计算上一个和下一个
        if(tutorialList.size() <= 1){
            tutorialDO.setPre_short_url("");
            tutorialDO.setNext_short_url("");
        }else{
            for(int i = 0;i<tutorialList.size();i++){
                //找到当前短链接
                if(short_url.equals(tutorialList.get(i).getShort_url())){
                    if(0 == i){
                        tutorialDO.setPre_short_url("");
                        tutorialDO.setNext_short_url(tutorialList.get(i+1).getShort_url());
                    }else if(i == tutorialList.size() - 1){
                        tutorialDO.setNext_short_url("");
                        tutorialDO.setPre_short_url(tutorialList.get(i-1).getShort_url());
                    }else{
                        tutorialDO.setNext_short_url(tutorialList.get(i+1).getShort_url());
                        tutorialDO.setPre_short_url(tutorialList.get(i-1).getShort_url());
                    }
                }
            }
        }

        //取short_url对应的数据
        tutorialDO.setCurrentTutorial(this.getCurrentTutorial(short_url));

        return tutorialDO;
    }

    //获取当前教程
    @Override
    public Tutorial getCurrentTutorial(String short_url){
        List<Document> docs =mongoService.findByShortUrl(short_url,TUTORIAL_TABLE);
        if(CollectionUtils.isEmpty(docs)){
            return null;
        }

        List<Tutorial> tutorialList = Lists.newArrayList();
        //转换doc
        for(Document doc:docs){
            String jsonString = doc.toJson();
            JSONObject jsonObject= JSON.parseObject(jsonString);
            Tutorial tutorial = JSONObject.toJavaObject(jsonObject, Tutorial.class);
            tutorialList.add(tutorial);
        }
        return tutorialList.get(0);
    }

    /**
     * 获取排序的教程列表
     * @param docs
     * @return
     */
    private List<Tutorial> getOrderedList(List<Document> docs){
        List<Tutorial> tutorialList = Lists.newArrayList();
        //转换doc
        for(Document doc:docs){
            String jsonString = doc.toJson();
            JSONObject jsonObject= JSON.parseObject(jsonString);
            Tutorial tutorial = JSONObject.toJavaObject(jsonObject, Tutorial.class);
            tutorialList.add(tutorial);
        }

        //按sort_num 倒序
        Collections.sort(tutorialList, new Comparator<Tutorial>() {

            @Override
            public int compare(Tutorial o1, Tutorial o2) {
                //倒序
                return Integer.valueOf(o1.getSort_id()).compareTo(Integer.valueOf(o2.getSort_id()));// 正确的方式
            }
        });

        return tutorialList;
    }

}
