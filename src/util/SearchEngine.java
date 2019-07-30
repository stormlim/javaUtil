package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/** 
* @author sunshine 
* @version 1.0
* @date：2015年8月15日 上午9:01:13 
* @description: Java开发搜索引擎爬虫
*     jsoup 类似jQuery的强大功能，什么方便解析操作HTML  DOM 树
*     关联jar包  jsoup-1.8.3.jar
*/ 
public class SearchEngine {
    
    /**
     * 根据一个图片的URL地址，通过这个URL批量下载图片到服务器的磁盘
     * @param imageUrl 要下载的服务器地址
     * @param filePath 下载完成后保存到服务器的图片地址
     * 
     */
    public static void downImages(String imageUrl, String filePath){
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        try {
            //获取图片文件的下载地址
            URL url = new URL(imageUrl);
            //连接网络图片地址
            HttpURLConnection uc =(HttpURLConnection) url.openConnection();
            //获取连接的输出流
            InputStream is = uc.getInputStream();
            //创建文件
            File file = new File(filePath+fileName);
            //创建输出流，写入文件
            OutputStream out = new FileOutputStream(file);
            byte[] bt = new byte[1024];
			while(is.read(bt)!=-1) {
				out.write(bt);
			}
            is.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * 根据网址和页面的编码集  获取网页的源代码
     * @param url
     * @param encoding
     * @return
     */
    public static String getHtmlResourceByUrl(String url, String encoding){
        
        //声明一个存储网页源代码的容器
        StringBuffer buff = new StringBuffer();
        
        URL urlObj = null;
        URLConnection uc = null;
        InputStreamReader in = null;
        BufferedReader reader = null;
        try {
            //建立网络链接
            urlObj = new URL(url);
            
            //打开网络链连接
            uc = urlObj.openConnection();
            
            //建立网络的输入流
            in = new InputStreamReader(uc.getInputStream(),encoding);
            
            //缓冲写入的文件流
            reader = new BufferedReader(in);
            
            String tempLine = null;
            
            //循环读取文件流
            while((tempLine = reader.readLine()) != null){
                buff.append(tempLine + "\n");  //循环追加数据
            }
            
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("Conection timeout ...");
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }
        
        return buff.toString();
    }
    
    public static void main(String[] args) {
        
        //根据网址和页面的编码集  获取网页的源代码
        String htmlResource = getHtmlResourceByUrl("https://hao.360.com/?safe", "UTF-8");
        //System.out.println(htmlResource);
        
        //解析源代码
        Document document = Jsoup.parse(htmlResource);
        
        //获取网页的图片
        //网页图片标签<img src="" alt="" width="" height="" />
        Elements elements = document.getElementsByTag("img");
        
        for(Element element : elements){
            String src = element.attr("src");
            if(src.contains("https:")==false)
            	src="https:/"+src;
            downImages(src,"C:/Users/Administrator/Desktop/images/");
            System.out.println("下载成功:"+src);
        }
        
    }
}