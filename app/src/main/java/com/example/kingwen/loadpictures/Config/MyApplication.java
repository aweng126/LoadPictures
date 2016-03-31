package com.example.kingwen.loadpictures.Config;

import android.app.Application;

import com.example.kingwen.loadpictures.Beans.StoryItemView;
import com.example.kingwen.loadpictures.Utils.ArticalDataBase;

import java.util.List;

/**
 * Created by kingwen on 2016/3/24.
 */
public class MyApplication extends Application {

    /**
     * dialog中回调函数作为参数进行回调，然后在mainActivity中进行事件的监听
     */
    public  static  final int ID_SAVE=1111;
    public  static  final int ID_SHARE=2222;

    /**
     *   数据库对象,在dialog弹出框收藏操作进行插入。在收藏界面作为数据源进行匹配
     */
    private ArticalDataBase articalDataBase;


    //数据源链表，用于在欢迎界面的时候进行更新，便于之后的adapter的数据源设置
    private List<StoryItemView> mdatas;


    public List<StoryItemView> getMdatas() {
        return mdatas;
    }

    public void setMdatas(List<StoryItemView> mdatas) {
        this.mdatas = mdatas;
    }


    public ArticalDataBase getArticalDataBase() {
        return articalDataBase;
    }

    public void setArticalDataBase(ArticalDataBase articalDataBase) {
        this.articalDataBase = articalDataBase;
    }
}
