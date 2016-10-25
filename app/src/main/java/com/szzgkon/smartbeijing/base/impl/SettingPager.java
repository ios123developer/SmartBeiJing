package com.szzgkon.smartbeijing.base.impl;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.szzgkon.smartbeijing.base.BasePager;

/**
 * ===================================================
 *
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 *
 * 作者：zhangyongke
 *
 * 版本：1.0
 *
 * 创建日期：16/10/24 下午2:18
 *
 * 描述：设置页面
 *
 *
 * 修订历史：
 *
 * ===================================================
 **/
public class SettingPager extends BasePager{
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tvTitle.setText("设置");

        btnMenu.setVisibility(View.GONE);//隐藏菜单栏
        setSlidingMenuEnable(false);//关闭侧边栏

        TextView text = new TextView(mActivity);
        text.setText("设置");
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);


        //向framelayout中动态添加布局
        flContent.addView(text);


    }
}
