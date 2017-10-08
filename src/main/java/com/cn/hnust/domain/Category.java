package com.cn.hnust.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:类目
 * User: zhengdongxiao
 * Date: 2017/10/5
 * Time: 上午1:32
 */
public class Category {
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String cate_name;

    @Getter
    @Setter
    private String descrip;

    @Getter
    @Setter
    private int sort_id;

    @Getter
    @Setter
    private int parent_id;

    @Getter
    @Setter
    private String parent_name;

}
