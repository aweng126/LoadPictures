package com.example.kingwen.loadpictures.Nets;

import android.util.Log;

import com.example.kingwen.loadpictures.Beans.StoryItemView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created by kingwen on 2016/3/22.
 */
public class GetPicUrl {
    //设置一个链表用于存储我们的url
    public static ArrayList<StoryItemView> picUrlList=null;
    /**
     * 通过得到的网址进行正则表达式的匹配，获得我们所需要的部分存到我们的list链表中
     * @param url  用于爬取数据的url地址
     * @return 元素类型为storyitem的list链表
     */
    public static List<StoryItemView> getPicUrl(String url) {

        picUrlList = new ArrayList<StoryItemView>();

        //得到网页对应的代码
        String xmlResult=getUrlContent(url);



        //写出能够匹配我们目标图片的正则表达式
        String imgStr="src=\"http(.+?)jpg\"";
        //写出能够匹配我们文章地址的正则表达式
        String storyStr="/story(.+?)\"";
        //  写出能够匹配我们文章描述的正则表达式
        String storyDes="\"title\">(.+?)<";


        //匹配我们的图片的地址
        Pattern imgPattern = Pattern.compile(imgStr);
        Matcher imgMatcher = imgPattern.matcher(xmlResult);


        //匹配我们文章的连接地址
        Pattern storyPattern = Pattern.compile(storyStr);
        Matcher storyMatcher = storyPattern.matcher(xmlResult);


        //匹配我们文章描述的连接地址
        Pattern storyDesPattern = Pattern.compile(storyDes);
        Matcher storyDesMatcher = storyDesPattern.matcher(xmlResult);



        boolean imgFind=imgMatcher.find();

        boolean storyFind=storyMatcher.find();

        boolean storyDesFind=storyDesMatcher.find();

        while (imgFind&&storyFind&&storyDesFind) {

            //图片地址
            int length=imgMatcher.group(0).length();
            String tmpImageUrl=imgMatcher.group(0).substring(5, length - 1);

            //文章地址
            String temStoryUrl="http://daily.zhihu.com"+storyMatcher.group(0).substring(0,storyMatcher.group(0).length()-1);

            //文章描述
            int desLen=storyDesMatcher.group(0).length();
            String indexStoryDes=storyDesMatcher.group(0).substring(8,desLen-1);
            Log.e("indexStoryDes","匹配到的内容是"+indexStoryDes);

            StoryItemView indexItemview=new StoryItemView(tmpImageUrl,temStoryUrl,indexStoryDes);


            picUrlList.add(indexItemview);


            imgFind=imgMatcher.find();
            storyFind=storyMatcher.find();
            storyDesFind=storyDesMatcher.find();
        }
        return picUrlList;
    }

    /**
     * 得到传入url地址的网页源码
     * @param url 要爬取的url地址
     * @return  url地址的网页源码
     */
   public  static String getUrlContent(String url){
            /*
        * 得到网页源代码
        */
        // 定义一个字符串用来存储网页内容
        String result = "";
        StringBuilder sb = new StringBuilder();
        // 定义一个缓冲字符输入流
        BufferedReader in = null;

        try {
            // 将string转成url对象
            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连接
            URLConnection connection = realUrl.openConnection();

            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36\"");
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面
                //result += line;
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return sb.toString();
    }
}





