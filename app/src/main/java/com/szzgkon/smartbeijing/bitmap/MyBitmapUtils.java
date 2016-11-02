package com.szzgkon.smartbeijing.bitmap;


import android.graphics.Bitmap;
import android.widget.ImageView;

import com.szzgkon.smartbeijing.R;

/**
 * 自定义的图片加载工具
 */

public class MyBitmapUtils {

    NetCacheUtils mNetCacheUtils;
    LoaclCacheUtils mLocalCacheUtils;
    MemoryCacheUtils mMemoryCacheUtils ;

    public MyBitmapUtils() {
        mMemoryCacheUtils = new MemoryCacheUtils();

        mLocalCacheUtils = new LoaclCacheUtils();

        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils,mMemoryCacheUtils);
    }

    public void display(ImageView ivPic, String url){

        ivPic.setImageResource(R.drawable.news_pic_default);

        Bitmap bitmap = null;

       //从内存读

        bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);

        if(bitmap != null){
            ivPic.setImageBitmap(bitmap);
            System.out.println("从内存读取图片啦");
            return;
        }


        //从本地读
         bitmap = mLocalCacheUtils.getBitmapFromLocal(url);

        if(bitmap != null){
            ivPic.setImageBitmap(bitmap);
            System.out.println("从本地读取图片啦");

            mMemoryCacheUtils.setBitmapTopMemory(url,bitmap);//将图片保存在内存中

            return;
        }

        //从网络读

        mNetCacheUtils.getBitmapFromNet(ivPic,url);
    }

}
