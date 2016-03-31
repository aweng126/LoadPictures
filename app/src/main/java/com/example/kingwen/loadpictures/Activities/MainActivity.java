package com.example.kingwen.loadpictures.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kingwen.loadpictures.Adapters.RecycleviewAdapter;
import com.example.kingwen.loadpictures.Beans.StoryItemView;
import com.example.kingwen.loadpictures.Config.MyApplication;
import com.example.kingwen.loadpictures.R;
import com.example.kingwen.loadpictures.Utils.ArticalDataBase;
import com.example.kingwen.loadpictures.widght.MyDialog;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    //布局
    private RecyclerView mRecycleView;

    //适配器
    private  RecycleviewAdapter mAdapter;

    //布局管理器
    private  RecyclerView.LayoutManager mLayoutManager;

    /*
    * 用于存储数据的链表
    * */
    private ArrayList<StoryItemView> mydata;

    public static int choice;

    //数据库对象
    private ArticalDataBase mDataBase;

    //游标
    private Cursor mCuror;

    //全局变量的对象
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*
               * 这里可以跳转界面用于显示自己的收藏界面
               * */
                Intent intent=new Intent(MainActivity.this,StroyCollectionActivity.class);

               startActivity(intent);

            }
        });

        initDatas();

        initViews();

        initListener();

    }

    /**
     * 针对于recycleview进行view的绑定和布局的监听
     */
    private void initViews() {

        mRecycleView= (RecyclerView) findViewById(R.id.recycleveiw_mainactivity);

        //设置固定大小
        mRecycleView.setHasFixedSize(true);

        mLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        mRecycleView.setLayoutManager(mLayoutManager);

        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        mAdapter=new RecycleviewAdapter(this,mydata);

        mRecycleView.setAdapter(mAdapter);

    }

    /**
     * 进行数据的获取，作为适配器的数据源
     */
    private void initDatas() {
        /*
        * 数据库对象
        * */
        mDataBase=new ArticalDataBase(MainActivity.this);
        mCuror=mDataBase.select();

        myApplication= (MyApplication) getApplication();
        myApplication.setArticalDataBase(mDataBase);
        mydata= (ArrayList<StoryItemView>) myApplication.getMdatas();
        Log.e("MainActivity initview", mydata.size() + "");

    }

    /**
     * 为适配器添加监听事件。实现长按和单机两种事件的响应
     */
   private void initListener() {

      mAdapter.setOnItemClickListener(new RecycleviewAdapter.OnRecyclerViewItemClickListener() {

          @Override
          public void onItemClick(View view, int data) {

              Toast.makeText(MainActivity.this,"点击了"+data+mydata.get(data).getStoryUrl(),Toast.LENGTH_SHORT).show();

              /*
              * 给内容添加监听事件,弹出界面用于之后的文章内容的获取。
              * */

              Intent intent=new Intent(MainActivity.this,ShowItemStoryActivity.class);
              Bundle bundle=new Bundle();
              bundle.putString("storyUrl",mydata.get(data).getStoryUrl());
              intent.putExtras(bundle);
              startActivity(intent);
          }


          //长按按钮可弹出按钮，选择分享或者收藏
          public  void onItemLongClick(View view, int data){

              Toast.makeText(MainActivity.this,"长按了view"+data,Toast.LENGTH_SHORT).show();

              choice=data;

              /*
              * 自定义弹出dialog
              * */
              MyDialog myDialog=new MyDialog(MainActivity.this, "对文章进行操作", mydata.get(data), new MyDialog.OnMyDialogListener() {
                  @Override
                  public void back(int id) {
                   switch (id){
                       case MyApplication.ID_SAVE:{
                           /*
                           * 将当前的这个位置的数据保存
                           * */
                   Toast.makeText(MainActivity.this,"选择了保存操作"+choice,Toast.LENGTH_SHORT).show();

                           mDataBase.insert(mydata.get(choice));
                        //   Log.e("dailog","插入成功看一下"+mCuror.getString(mCuror.getColumnIndex("ARTICAL_STORYDES")));
                        Log.e("dialog",mCuror.getCount()+"");
                           break;
                       }
                       case MyApplication.ID_SHARE:{
                           /*
                           * 将当前这篇文章进行分享（暂时还没有实现）
                           * */
                           Toast.makeText(MainActivity.this,"选择了分享操作"+choice,Toast.LENGTH_SHORT).show();
                           break;
                       }
                       default:{

                       }
                   }
                  }
              });

               myDialog.show();
          }
      });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
