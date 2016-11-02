package com.szzgkon.smartbeijing.bitmap;


import android.widget.ImageView;

/**
 * 自定义的图片加载工具
 */

public class MyBitmapUtils {

    NetCacheUtils mNetCacheUtils;

    public MyBitmapUtils() {
        mNetCacheUtils = new NetCacheUtils();
    }

    public void display(ImageView ivPic, String listimage){
       //从内存读

       //从本地读

       //从网络读

        mNetCacheUtils.getBitmapFromNet(ivPic,listimage);
    }

}
