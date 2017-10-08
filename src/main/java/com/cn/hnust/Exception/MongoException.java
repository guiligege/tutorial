package com.cn.hnust.Exception;

/**
 * Description: mongo
 * User: zhengdongxiao
 * Date: 2017/10/5
 * Time: 上午12:09
 */
public class MongoException extends  RuntimeException {

    public MongoException(){

    }

    public MongoException(Throwable cause) {
        super(cause);
    }
    public MongoException(String msg,Throwable cause) {
        super(msg,cause);
    }

}
