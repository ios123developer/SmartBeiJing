package com.szzgkon.smartbeijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ===================================================
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 * 作者：zhangyongke
 * 版本：1.0
 * 创建日期：16/10/21 上午9:50
 * 描述：SharePreference封装
 * 修订历史：
 * ===================================================
 **/
public class PrefUtils {

    public static final String PREF_NAME = "config";

    public static boolean getBoolean(Context ctx, String key, boolean defaultValue){
        //判断之前有没有显示过新手引导
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                                            ctx.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }
    public static void setBoolean(Context ctx, String key, boolean defaultValue){
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                                            ctx.MODE_PRIVATE);
            sp.edit().putBoolean(key,defaultValue).commit();

    }

}
