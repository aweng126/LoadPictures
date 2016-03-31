package com.example.kingwen.loadpictures.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kingwen.loadpictures.Beans.StoryItemView;
import com.example.kingwen.loadpictures.R;
import java.util.List;

/**
 * Created by kingwen on 2016/3/21.
 */

public class RecycleviewAdapter  extends RecyclerView.Adapter<MyViewHolder>implements View.OnClickListener,View.OnLongClickListener{

    //布局扩展对象，作用类似于findviewByid，只是他找的是整个文件的布局而不是一个一个组件
    private LayoutInflater mInflater;
    //上下文对象，可有可无，用于之后的构造方法，当然如果后面需要弹出对话框那么就是必须的
    private Context mContext;
    //我们的数据源，这里是一个list链表。当然可以使用数组或者使用数据库对象
    private List<StoryItemView> mDatas;
    //自定义监听接口，用于提供监听事件
    private OnRecyclerViewItemClickListener mOnItemClickListener;


    /*
    * 构造方法
    * */
    public RecycleviewAdapter(Context context,List<StoryItemView> datas){
        this.mContext=context;
        this.mDatas=datas;

        Log.d("recycleview构造方法",datas.size()+"");

        mInflater=LayoutInflater.from(context);
        mOnItemClickListener=null;

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*
        * 得到我们每个item的布局，并且给每一个item添加我们的监听事件
        * */
        View view=mInflater.inflate(R.layout.item_layout_mainactivity,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return viewHolder;
    }

    /*
    * 布局控件和数据源进行绑定
    * */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //加标签，用于之后的添加监听的时候处理
        holder.itemView.setTag(position);

        //每一个view设置我们的文字，在数据源中相应位置的东西
        holder.tv.setText(position + "");

        //为每一个view设置我们的文章标题
        holder.storyDes.setText(mDatas.get(position).getStroyDes());


        /*
        * 通过glide库进行设定我们的图片
        * */

     //   Log.d("Glide显示图片",""+mDatas.get(position));
        Glide
                //上下文
                .with(mContext)

                //设置我们的图片数据（网络图片的url）
                .load(mDatas.get(position).getImgUrl())

                //跳过内存缓存
                .skipMemoryCache(true)     //跳过内存缓存

                //跳过硬盘缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                    /*  括号中的参数表示有
                    * DiskCacheStrategy.NONE 什么都不缓存
                    •DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像。在我们上面的例子中，将会只有一个 1000x1000 像素的图片
                    •DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
                    •DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
                    */


                //图片加载的优先级问题
                // .priority(Priority.HIGH)
                   /*
                   优先级依次递增
                   *Priority.LOW
                    •Priority.NORMAL
                    •Priority.HIGH
                    •Priority.IMMEDIATE
                    */


                //加载出现错误额时候显示萌宠图片
                .error(R.drawable.img_error)

                 //在图片加载过程中添加一个占位符，显示一个默认位置，等图片加载结束之后显示当前图片
                //  .placeholder(R.drawable.img_loading)

                //设置图片淡入淡出
                .crossFade()


                // .dontAnimate()           //拒绝动画，无动画意思
                 .override(500, 500)       //主动设置我们要加载图片的大小
                // .fitCenter()             //缩放去适应我们的imageveiw，所以这个可能不会充满我们的imageveiw
                // .asBitmap()              //如果得到的是一个gif动图，那么这里只显示动图的第一帧，
                // .centerCrop()            //实现裁剪，然后用于适配我们的imageview,可能边缘是被裁剪掉

                .into(holder.iv);        //这里用于之后的图片加载到什么地方

        Log.d("getImage", "glide show"+position);

    }

/*
* 返回数据源的数目
* */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /*
    * 单击事件的监听事件
    * */
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    /*
    * 长按事件的监听事件
    * */
    @Override
    public boolean onLongClick(View v) {

        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemLongClick(v, (Integer) v.getTag());
        }
        return true;
    }

    /*
    * 定义我们的接口，用于实现我们的单击和长按监听事件
    * */
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int data);

        void onItemLongClick(View view , int data);
    }

}
/*
* 通过viewholder来进行布局的绑定
* */
class MyViewHolder extends RecyclerView.ViewHolder{


    //显示文本
    TextView tv;
    //显示图片
    ImageView iv;

    //显示文章描述（标题）
    TextView storyDes;

    public MyViewHolder(View itemView) {
        super(itemView);
        tv= (TextView)itemView.findViewById(R.id.tv_item_itemView);
        iv= (ImageView) itemView.findViewById(R.id.iv_item_itemView);
        storyDes= (TextView) itemView.findViewById(R.id.tv_storyDes_itemview);

    }
}