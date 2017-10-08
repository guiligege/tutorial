package com.cn.hnust.domain;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description:
 * User: zhengdongxiao
 * Date: 2017/10/6
 * Time: 下午12:55
 */
public class TutorialDO {

    //类目下的教程列表
    @Getter
    @Setter
    List<Tutorial> tutorialList = Lists.newArrayList();

    //当前教程
    @Getter
    @Setter
    Tutorial currentTutorial = new Tutorial();

    //上一个
    @Getter
    @Setter
    String pre_short_url;

    //下一个
    @Getter
    @Setter
    String next_short_url;



}
