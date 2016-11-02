package com.szzgkon.smartbeijing.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ===================================================
 * 
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 * 
 * 作者：张勇柯
 *
 * 版本：1.0
 *
 * 创建日期：16/11/2 下午2:50
 * 
 * 描述：网络缓存工具类
 *
 * 修订历史：
 *
 * ===================================================
 **/
 

public class NetCacheUtils {

    private ImageView ivPic;
    private String url;
    private LoaclCacheUtils mLocalCacheUtils;

    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils(LoaclCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
         mMemoryCacheUtils = memoryCacheUtils;
    }

    /**
     * 从网络下载图片
     * @param ivPic
     * @param url
     */
    public void getBitmapFromNet(ImageView ivPic, String url) {
        new BitmapTask().execute(ivPic,url);//启动AsyncTask,参数会在doInBackground中获取

    }

    /**
     * 三个返回值：
     *  第一个泛型：参数类型
     *  第二个泛型：更新进度的泛型
     *  第三个泛型：方法onPostExecute的放回结果
     */
    class BitmapTask extends AsyncTask<Object,Void,Bitmap>{

        /**
         * 耗时方法在此执行，在子线程运行
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object... params) {

            ivPic = (ImageView) params[0];
            url = (String) params[1];

            ivPic.setTag(url);//将url和imageview绑定


            return downloadBitmap(url);
        }


        /**
         * 更新进度，在主线程运行
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /**
         * 耗时方法结束后，执行该方法，在主线程运行
         * @param result
         */
        @Override
        protected void onPostExecute(Bitmap result) {

            if(result!= null){

                String bindUrl = (String) ivPic.getTag();
                    if(url.equals(bindUrl)){//确保设定给了正确的imageview

                        ivPic.setImageBitmap(result);



                        mLocalCacheUtils.setBitmapToLocal(url,result);//将图片保存在本地
                        mMemoryCacheUtils.setBitmapTopMemory(url,result);//将图片保存在内存中
                        System.out.println("从网络上获取图片啦");
                    }
            }

        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return null;
    }


}
