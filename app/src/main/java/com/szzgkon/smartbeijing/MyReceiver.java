package com.szzgkon.smartbeijing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * ===================================================
 * <p>
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 * <p>
 * 作者：zhangyongke
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：16/11/3 下午12:17
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ===================================================
 **/
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            System.out.println("收到了自定义消息。消息内容是："
                    + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            System.out.println("收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            System.out.println("用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            System.out.println("附加信息:" + extra);

            try {
                JSONObject jo = new JSONObject(extra);
                String url = jo.getString("url");

                System.out.println("url:" + url);
                // 跳浏览器加载网页
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}

