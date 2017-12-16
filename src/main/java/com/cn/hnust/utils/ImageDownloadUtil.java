package com.cn.hnust.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by qixuan.chen on 2016/8/18.
 */
public class ImageDownloadUtil {


    public static String download(String savePath,String url) {
        HttpClient httpclient = new DefaultHttpClient();
//        String picSrc = "";
//        String picType = url.substring(url.lastIndexOf(".") + 1, url.length());
        String imageName = ShortUrlUtil.shortUrl(url) + url.substring(url.lastIndexOf("."));
        String path = savePath + imageName;
//        File imageFile = new File(path+imageName);
        File storeFile = null;
        FileOutputStream output = null;
        InputStream instream = null;
        try {
            HttpGet httpget = new HttpGet(url);

            //伪装成google的爬虫
            httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
            httpget.setHeader("Referer","https://www.google.com");
            // Execute HTTP request
            HttpResponse response = httpclient.execute(httpget);
            storeFile = new File(path);
            output = new FileOutputStream(storeFile);

            // 得到网络资源的字节数组,并写入文件
            HttpEntity entity = response.getEntity();
            if (entity != null) {

                instream = entity.getContent();
                try {
                    byte b[] = new byte[1024];
                    int j = 0;
                    while ((j = instream.read(b)) != -1) {
                        output.write(b, 0, j);
                    }
                    output.flush();
                    output.close();
                } catch (IOException ex) {
                    // In case of an IOException the connection will be released
                    // back to the connection manager automatically
                    throw ex;
                } catch (RuntimeException ex) {
                    // In case of an unexpected exception you may want to abort
                    // the HTTP request in order to shut down the underlying
                    // connection immediately.
                    httpget.abort();
                    throw ex;
                } finally {
                    // Closing the input stream will trigger connection release
                    try {
                        instream.close();
                    } catch (Exception ignore) {
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
            if(output!=null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (instream!=null ){
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return imageName;

    }

//    public static void main(String[] args) throws MalformedURLException {
//        //抓取下面图片的测试
//        ImageDownloadUtil.download("http://blog.goyiyo.com/wp-content/uploads/2012/12/6E0E8516-E1DC-4D1D-8B38-56BDE1C6F944.jpg", "d:/aaa.jpg");
//    }
}
