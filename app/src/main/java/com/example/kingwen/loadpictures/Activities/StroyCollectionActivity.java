package com.example.kingwen.loadpictures.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kingwen.loadpictures.Adapters.CollectionAdapter;
import com.example.kingwen.loadpictures.Beans.StoryItemView;
import com.example.kingwen.loadpictures.Config.MyApplication;
import com.example.kingwen.loadpictures.R;
import com.example.kingwen.loadpictures.Utils.ArticalDataBase;
import com.example.kingwen.loadpictures.widght.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingwen on 2016/3/23.
 */
public class StroyCollectionActivity extends AppCompatActivity {

    //head图片
    private ImageView iv_show;

    //recycleview布局
    private RecyclerView collectionRecycleview;

    //适配器
    private CollectionAdapter mCollectionAdapter;
    //布局管理器
    private  RecyclerView.LayoutManager mLayoutManager;

    //数据库中的内容
    private List<StoryItemView> mdatas;

    //数据库游标
    private Cursor cursor;

    //数据库对象
    private ArticalDataBase mDatabase;

    //全局变量对象
    private MyApplication myApplication;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_story_layout);
        /*
        * 通过intent的跳转得到我们的mDatabase
        *
        * */
        initDatas();

        initViews();

        initListener();
    }

    /**
     * 针对于数据进行必要的获取
     */
    private void initDatas() {

        myApplication= (MyApplication) getApplication();
        mDatabase=myApplication.getArticalDataBase();

        mdatas=new ArrayList<StoryItemView>();
        cursor=mDatabase.select();

        //通过数据库游标，将数据库中的内容读取出来，放到一个链表中用于之后的适配器的数据源
        if(cursor.moveToFirst()){
            do{
                String imgUrl=cursor.getString(cursor.getColumnIndex("imgUrl_artical"));
                String storyUrl=cursor.getString(cursor.getColumnIndex("storyUrl_artical"));
                String storyDes=cursor.getString(cursor.getColumnIndex("storyDes_artical"));
                StoryItemView storyItemView =new StoryItemView(imgUrl,storyUrl,storyDes);
                mdatas.add(storyItemView);
            }while (cursor.moveToNext());
        }
//得到数据库对象


        Log.d("ShowStoryActivity ","initDatas"+mdatas.size());
    }


    /**
     * 事件的监听
     */
    private void initListener() {
/*
* 如果数据源不为空，则不显示
* */
        if(mdatas.size()==0){
        Toast.makeText(StroyCollectionActivity.this,"还没有收藏",Toast.LENGTH_SHORT).show();
        }

        mCollectionAdapter.setOnItemClickListener(new CollectionAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int data) {
                String storyUrl=mdatas.get(data).getStoryUrl();
                /*
                * 跳转到显示每篇文章的界面
                * */
                Intent intent=new Intent(StroyCollectionActivity.this,ShowItemStoryActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("storyUrl",storyUrl);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        collectionRecycleview= (RecyclerView) findViewById(R.id.recycleview_showstory);

        //设置固定大小
        collectionRecycleview.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(this);

        collectionRecycleview.setLayoutManager(mLayoutManager);

        //分割线
        collectionRecycleview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

        mCollectionAdapter=new CollectionAdapter(this,mdatas);
        collectionRecycleview.setAdapter(mCollectionAdapter);

        iv_show= (ImageView) findViewById(R.id.iv_showStory_activity);

    }
}
