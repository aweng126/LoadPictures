package com.example.kingwen.loadpictures.widght;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kingwen.loadpictures.Beans.StoryItemView;
import com.example.kingwen.loadpictures.Config.MyApplication;
import com.example.kingwen.loadpictures.R;

/**
 * Created by kingwen on 2016/3/24.
 */
public class MyDialog  extends Dialog {

    private ImageView iv_show;

    private TextView tv_show;

    private Button btn_share;

    private  Button btn_save;

    private StoryItemView item;

    private OnMyDialogListener mylistener;

    private String dialogName;

    private  Context mContext;

    //定义回调事件
    public interface  OnMyDialogListener{
        public void back( int id);
    }

    /**
     * 自定义的dialog的构造函数
     * @param context 上下文对象
     * @param dialogName 弹出框的名字
     * @param item  当前的view对象
     * @param mylistener   监听器
     */
    public MyDialog(Context context,String dialogName,StoryItemView item,OnMyDialogListener mylistener) {
        super(context);
        this.dialogName=dialogName;
        this.item=item;
        this.mylistener=mylistener;
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);

        initViews();

        initListeners();
    }

    /**
     * 为布局中的两个按钮添加监听事件
     */
    private void initListeners() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 保存，放到数据库中进行保存
                * */
                mylistener.back(MyApplication.ID_SAVE);
                //dialog关闭
                dismiss();
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 设置分享事件
                * */
                mylistener.back(MyApplication.ID_SHARE);
                //dialog关闭
                dismiss();
            }
        });
    }

    /**
     * 控件绑定
     */
    private void initViews() {
        iv_show= (ImageView) findViewById(R.id.iv_showImg_dialog);
        tv_show= (TextView) findViewById(R.id.tv_showStroy_dialog);
        btn_save= (Button) findViewById(R.id.btn_save_dailog);
        btn_share= (Button) findViewById(R.id.btn_share_dialog);

        Glide.with(mContext).load(item.getImgUrl()).into(iv_show);
        tv_show.setText(item.getStroyDes());

    }


}
