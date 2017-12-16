package com.cn.hnust.utils;

//import com.sun.deploy.net.HttpResponse;
//import com.sun.imageio.plugins.common.ImageUtil;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.springframework.http.HttpEntity;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class ImageRequest {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        System.out.println("aa");
//        saveImage("","http://img.hexun.com/2011-06-21/130726386.jpg");
    }

    public static String saveImage(String path,String sourceUrl){

        FileOutputStream outStream = null;
        InputStream inStream = null;

        try{
            //        new一个URL对象
            URL url = new URL(sourceUrl);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            //new一个文件对象用来保存图片，默认保存当前工程根目录

            String imageName = ShortUrlUtil.shortUrl(sourceUrl)+sourceUrl.substring(sourceUrl.lastIndexOf("."));

            File imageFile = new File(path+imageName);
            //创建输出流
            outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);

            return imageName;
        }catch (Exception e){
            e.printStackTrace();

        }finally {

            if(inStream!=null){
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //关闭输出流
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";

    }


//    public static String download(HttpServletRequest request, String url, String savePath, Integer width, Integer height) {
//        HttpClient httpclient = new DefaultHttpClient();
//        String picSrc = "";
//        String picType = url.substring(url.lastIndexOf(".")+1,url.length());
//        String fileName = UUID.randomUUID().toString().replace("-", "")+"."+picType;
//        String path = request.getSession().getServletContext().getRealPath(savePath+fileName);
//        File storeFile = null;
//        try {
//            HttpGet httpget = new HttpGet(url);
//
//            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000) // 设置连接超时时间
//                    .setConnectionRequestTimeout(3000) // 设置请求超时时间
//                    .setSocketTimeout(3000).setRedirectsEnabled(true)
//                    .build();// 默认允许自动重定向
//
//            //伪装成google的爬虫
////            httpget.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
//            httpget.setConfig(requestConfig);
//            httpget.
//            // Execute HTTP request
//            HttpResponse response = httpclient.execute(httpget);
//            storeFile = new File(path);
//            FileOutputStream output = new FileOutputStream(storeFile);
//
//            // 得到网络资源的字节数组,并写入文件
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                InputStream instream = entity.getContent();
//                try {
//                    byte b[] = new byte[1024];
//                    int j = 0;
//                    while( (j = instream.read(b))!=-1){
//                        output.write(b,0,j);
//                    }
//                    output.flush();
//                    output.close();
//                } catch (IOException ex) {
//                    // In case of an IOException the connection will be released
//                    // back to the connection manager automatically
//                    throw ex;
//                } catch (RuntimeException ex) {
//                    // In case of an unexpected exception you may want to abort
//                    // the HTTP request in order to shut down the underlying
//                    // connection immediately.
//                    httpget.abort();
//                    throw ex;
//                } finally {
//                    // Closing the input stream will trigger connection release
//                    try { instream.close(); } catch (Exception ignore) {}
//                }
//
//            }
//
//        } catch (Exception e) {
//        } finally {
//            httpclient.getConnectionManager().shutdown();
//        }
//
//
//        return picSrc;
//
//    }


    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}