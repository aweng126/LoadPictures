package com.example.kingwen.loadpictures.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kingwen.loadpictures.Beans.StoryItemView;

/**
 * Created by kingwen on 2016/3/24.
 */
public class ArticalDataBase extends SQLiteOpenHelper{

    //数据库的名字
    private final static String DATABASE_NAME="ArticalDataBase";
    //数据库的版本号
    private  final static int DATABASE_VERSION=1;
    //数据库表的名字
    private final static String TABLE_NAME="artical_table";
    //表中数据一列的名字id
    private final static String ARTICAL_ID="id_artical";
    //表中数据的列名name
    private final static String ARTICAL_IMGURL="imgUrl_artical";
    //表中数据的列名作者
    private final static String ARTICAL_STORYURL="storyUrl_artical";
    //表中文章的具体标题
    private final static String ARTICAL_STORYDES="storyDes_artical";

    private Context mContext;


    /*
    * 添加的具体内容
    * */
    private String picUrl;

    private String storyUrl;

    private String storyDes;

    private String StoryContent;

    //建表语句
    public static final String CREAT_ARTICAL
            = "CREATE TABLE " +TABLE_NAME
            +" ("
            +ARTICAL_ID +" INTEGER primary key autoincrement, "
            +ARTICAL_IMGURL+ " text, "
            +ARTICAL_STORYURL+ " text, "
            +ARTICAL_STORYDES+ " text "
            + " )";

    public ArticalDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=context;
    }

    /**
     * 数据库的构造方法
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL(CREAT_ARTICAL);
    }

    /**
     * 数据库的升级操作
     * @param db  数据库对象
     * @param oldVersion  旧版本
     * @param newVersion  新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      /*
      * 数据库的升级操作，等待郭霖的更改
      * */
        String sql="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * 数据库的插入操作
     * @param item 要插入的数据
     * @return 如果插入返回值为1，否则返回值为0
     */
    public long insert(StoryItemView item){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(ARTICAL_IMGURL,item.getImgUrl());
        cv.put(ARTICAL_STORYURL,item.getStoryUrl());
        cv.put(ARTICAL_STORYDES,item.getStroyDes());
        // 这里的insert要包含这个表单中的所有的列，如果我们的数据中不包含这个参数，null
        long raw=db.insert(TABLE_NAME, null, cv);
        return  raw;
    }

    /**
     * 数据库的删除操作
     * @param id 要删除的数据id
     */
    public void delete(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String where=ARTICAL_ID+"=?";
        String[] whereValue={Integer.toString(id)};
        db.delete(TABLE_NAME,where,whereValue);
    }

    /**
     * 得到初始位置的游标
     * @return 返回初始位置的游标
     */
    public Cursor select(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }


}
