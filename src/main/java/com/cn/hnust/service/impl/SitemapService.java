package com.cn.hnust.service.impl;

import com.cn.hnust.domain.Category;
import com.cn.hnust.domain.CategoryDO;
import com.cn.hnust.domain.Tutorial;
import com.cn.hnust.service.ICategoryService;
import com.cn.hnust.service.ISitemapService;
import com.cn.hnust.service.ITutorialService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 网站地图
 * User: zhengdongxiao
 * Date: 2017/11/21
 * Time: 下午10:46
 */
@Service("sitemapService")
public class SitemapService implements ISitemapService {

    private static final String url = "http://data.zz.baidu.com/urls?site=www.51jingxuan.com&token=WdqBssCdTV40UaXR";//网站的服务器连接

    @Resource(name = "tutorialService")
    private ITutorialService tutorialService;
    @Resource(name = "categoryService")
    private ICategoryService categoryService;

    @Override
    public void onlinePush() {

        //待推送数据
        List<String> param =new ArrayList<String>();

        List<CategoryDO> categoryList = categoryService.selectAllCategoryDO();

        if(CollectionUtils.isEmpty(categoryList)){
            return;
        }
        param.add("http://www.51jingxuan.com/");
//        param.add("http://51jingxuan.com/");
        for(CategoryDO category:categoryList){

            if(CollectionUtils.isEmpty(category.getChildren())){
                continue;
            }
            for(Category cate:category.getChildren()){

                param.add("http://www.51jingxuan.com/tutorial/detail-"+cate.getCate_name()+"-.htm");
                //类目链接

                List<Tutorial> tutorialList = tutorialService.selectByCate(cate.getCate_name());
                for(Tutorial tutorial : tutorialList){
                    param.add("http://www.51jingxuan.com/tutorial/detail-"+cate.getCate_name()+"-"+tutorial.getShort_url()+".htm");
                    //课程
                }
            }
        }
        String json = post(url, param);//执行推送方法
        System.out.println("结果是:"+json);
//        System.out.println("结果是:param"+param.toString());
    }


//        tutorialService.

//        WeixinListVo nearlyWeixinList=weiXinArticleService.getNearlyArticles(null, null, null, null, COUNT);
//        if(nearlyWeixinList==null || nearlyWeixinList.getWeiXinArticleList()==null ||nearlyWeixinList.getWeiXinArticleList().size()==0){
//            return;
//        }
//        for(WeiXinArticle weiXinArticle:nearlyWeixinList.getWeiXinArticleList()){
//            param.add("http://www.taochongwu.cn/weixin/detail-"+weiXinArticle.getTitlehash()+".htm");
//            param.add("http://www.taochongwu.cn/weixin/one-"+weiXinArticle.getWeixin_hao()+"--.htm");
//        }

//        String json = post(url, param);//执行推送方法
//        System.out.println("结果是:"+json);
//    }

    /**
     * 百度链接实时推送
     * @param PostUrl
     * @param Parameters
     * @return
     */
    public static String post(String PostUrl,List<String> Parameters){
        if(null == PostUrl || null == Parameters || Parameters.size() ==0){
            return null;
        }
        String result="";
        PrintWriter out=null;
        BufferedReader in=null;
        try {
            //建立URL之间的连接
            URLConnection conn=new URL(PostUrl).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Host","data.zz.baidu.com");
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Content-Length", "83");
            conn.setRequestProperty("Content-Type", "text/plain");
            //发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //获取conn对应的输出流
            out=new PrintWriter(conn.getOutputStream());
            //发送请求参数
            String param = "";
            for(String s : Parameters){
                param += s+"\n";
            }
            out.print(param.trim());
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line=in.readLine())!= null){
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送post请求出现异常！"+e);
            e.printStackTrace();
        } finally{
            try{
                if(out != null){
                    out.close();
                }
                if(in!= null){
                    in.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

}
