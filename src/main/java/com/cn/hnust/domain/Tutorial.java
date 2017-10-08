package com.cn.hnust.domain;


import lombok.Getter;
import lombok.Setter;

/**
 * Description: 教程
 * User: zhengdongxiao
 * Date: 2017/10/5
 * Time: 上午1:00
 */
public class Tutorial {

    @Getter
    @Setter
    private String short_url;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private int sort_id;

    @Getter
    @Setter
    private int cate_id;

    @Getter
    @Setter
    private String cate_name;
}
