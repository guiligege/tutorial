package com.cn.hnust.mongo;

/**
 * womaime init param for website 
 * 
 */

/**
 *  配置
 */
public final class DomainConstans{


//	#product
//	mongodb.host=10.160.39.89
//	mongodb.port=27017
//	mongodb.userName=root1
//	mongodb.password=root1234
//	mongodb.databaseName=product
//	mongodb.total.colls=article_info|login_info|weixinhao_info
//	mongodb.logininfo.collectionName=login_info
//	mongodb.article.collectionName=article_info
//	mongodb.weixinhao.collectionName=weixinhao_info
	
//	public static ResourceBundle	mongodb			= ResourceBundle.getBundle("config/mongodb");
	//public static final String mongodb_host="47.92.129.120";
	public static final String mongodb_host="127.0.0.1";
	public static final Integer mongodb_port=27017;
	public static final String mongodb_userName="root2";
	public static final String mongodb_password="root1234";
	public static final String mongodb_databaseName="product";
	public static final String mongodb_total_colls="tutorial|category";
	//微信信息collection
	public static final String mongodb_article_collectionName="tutorial";
	public static final String mongodb_category_collectionName="category";

	public static final Integer		LOG_BATCH_SIZE		= 1;

	//test
//	public static String IMAGE_PATH = "/Users/zhengdongxiao/jingxuan_resource/images/detail_images/";
	//product
	public static String IMAGE_PATH = "/usr/local/jingxuan_resource/detail_images/";
	public static String IMAGE_URL = "http://www.51jingxuan.com/detail_images/";

}
