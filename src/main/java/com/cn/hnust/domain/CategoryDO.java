package com.cn.hnust.domain;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Description: 类目转换对象
 * User: zhengdongxiao
 * Date: 2017/10/5
 * Time: 上午1:46
 */
public class CategoryDO {

    @Setter
    @Getter
    private Category category;

    @Setter
    @Getter
    private List<Category> children = Lists.newArrayList();

}
