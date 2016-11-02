package com.szzgkon.smartbeijing.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.szzgkon.smartbeijing.utils.MD5Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * ===================================================
 *
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 *
 * 作者：张勇柯
 *
 * 版本：1.0
 *
 * 创建日期：16/11/2 下午3:58
 *
 * 描述：本地缓存
 *
 * 修订历史：
 *
 * ===================================================
 **/


public class LoaclCacheUtils {

    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhbj_cache";


    /**
     * 从本地sdcard读图片
     */
    public Bitmap getBitmapFromLocal(String url){

        try {
            String fileName = MD5Encoder.encode(url);

            File file = new File(CACHE_PATH,fileName);

            if(file.exists()){

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

                return bitmap;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 向sccard写图片
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url, Bitmap bitmap){
        try {
            String fileName = new MD5Encoder().encode(url);

            File file = new File(CACHE_PATH,fileName);

            File parentFile = file.getParentFile();

            if(!parentFile.exists()){//如果文件夹不存在，创建文件夹
             parentFile.mkdirs();
            }
            //将图片保存在本地
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,
                                             new FileOutputStream(file));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
