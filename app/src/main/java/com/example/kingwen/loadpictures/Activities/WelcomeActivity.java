package com.example.kingwen.loadpictures.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.kingwen.loadpictures.Beans.StoryItemView;
import com.example.kingwen.loadpictures.Config.MyApplication;
import com.example.kingwen.loadpictures.Nets.GetPicUrl;
import com.example.kingwen.loadpictures.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 欢迎界面，同时完成网络数据的获取
 * Created by kingwen on 2016/3/26.
 */
public class WelcomeActivity extends AppCompatActivity {

    /**
     * 全局变量对象
     */
    private MyApplication myApplication;
    /**
     * 存储数据的list链表
     */
    private List<StoryItemView> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);

        myApplication= (MyApplication) getApplication();
        mDatas=new ArrayList<StoryItemView>();

/**
 * 开一个新的线程进行网络数据的获取,并将结果放在list中作为全局变量用于之后的适配器的数据源
 */
        Runner1 runner=new Runner1();
        final   Thread t=new Thread(runner);
        t.start();

        /**
         * 延时启动的数据
         */
        new Handler().postDelayed(new Runnable() {
            public void run() {

                //直到网络数据请求完毕，然后进行跳转
                while(t.isAlive()) { }

                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);

                Log.e("welcome","实现延迟启动并启动跳转");
                finish();
            }
        }, 1500);

    }

    /**
     * 线程类，实现runnable接口
     */
    class Runner1 implements Runnable{
        public void run() {
            mDatas= (ArrayList<StoryItemView>) GetPicUrl.getPicUrl("http://daily.zhihu.com/");
            Log.d("getData（）",mDatas.size()+"");
            myApplication.setMdatas(mDatas);
        }
    }
}
