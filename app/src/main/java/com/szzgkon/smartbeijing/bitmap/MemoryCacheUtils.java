package com.szzgkon.smartbeijing.bitmap;

import android.graphics.Bitmap;
import android.util.LruCache;


public class MemoryCacheUtils {

    //    private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<String, SoftReference<Bitmap>>();
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtils() {

        long maxMemory = Runtime.getRuntime().maxMemory();//获取设备的内存

        mMemoryCache = new LruCache<String, Bitmap>((int) (maxMemory / 8)) {


            //返回一张图片所占内存的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {

                int byteCount = value.getByteCount();
                return byteCount;
            }
        };

    }

    /**
     * 从内存读
     *
     * @param url
     */

    public Bitmap getBitmapFromMemory(String url) {


        return    mMemoryCache.get(url);

    }

    /**
     * 写入内存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapTopMemory(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);

    }


}
