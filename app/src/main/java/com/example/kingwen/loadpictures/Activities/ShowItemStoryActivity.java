package com.example.kingwen.loadpictures.Activities;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.kingwen.loadpictures.R;

/**
 * Created by kingwen on 2016/3/26.
 */
public class ShowItemStoryActivity extends AppCompatActivity {

    //webview对象
    private WebView wv_showItemStory;

    //网页地址
    private String storyUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        设置窗口风格为进度条
        * */
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        setContentView(R.layout.activity_show_itemstory_layout);

        initViews();

        initListener();
    }

    private void initListener() {

        WebSettings settings=wv_showItemStory.getSettings();
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);//支持内置的缩放装置
        settings.setJavaScriptEnabled(true);//启动js脚本

        //设置加载进来的页面自适应手机屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        /**
         *   当点击链接的时候，希望覆盖而不是打开新的窗口
         */
        wv_showItemStory.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                view.requestFocus(View.FOCUS_FORWARD);
                return true;
            }
        });

        /**
         * 网页重复的时候进行回退操作
         */
        wv_showItemStory.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && wv_showItemStory.canGoBack()) {
                        wv_showItemStory.goBack();
                        Log.e("wv_showstory", "goback");
                        return true;
                    }
                }
                return false;
            }
        });

        /**
         * 设置进度条模式
         */
      wv_showItemStory.setWebChromeClient(new WebChromeClient() {
          @Override
          public void onProgressChanged(WebView view, int newProgress) {

              ShowItemStoryActivity.this.setProgress(newProgress*100);
          }
      });

        //在webview中打开自己的url地址
        wv_showItemStory.loadUrl(storyUrl);
        //将我们的焦点放到最开始。
        wv_showItemStory.requestFocus(View.FOCUS_FORWARD);

    }

    private void initViews() {

        //得到我们的链接的地址，用于之后在webview中进行显示
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        storyUrl=bundle.getString("storyUrl");

        wv_showItemStory= (WebView) findViewById(R.id.wv_show_itemstory_activity);
    }
}
