package com.example.kingwen.loadpictures.Beans;

import java.io.Serializable;

/**
 * Created by kingwen on 2016/3/21.
 */
public class StoryItemView implements Serializable {
    //网络地址
    private String imgUrl;

    //文章地址
    private String storyUrl;

    //文章描述
    private String stroyDes;


    public StoryItemView(String imgUrl, String storyUrl, String stroyDes) {
        this.imgUrl = imgUrl;
        this.storyUrl = storyUrl;
        this.stroyDes = stroyDes;
    }

    public String getImgUrl() {

        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }

    public String getStroyDes() {
        return stroyDes;
    }

    public void setStroyDes(String stroyDes) {
        this.stroyDes = stroyDes;
    }
}
