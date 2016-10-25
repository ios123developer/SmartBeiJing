package com.szzgkon.smartbeijing.base.impl;

import android.app.Activity;
import android.view.Gravity;
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
 * 描述：新闻中心
 *
 *
 * 修订历史：
 *
 * ===================================================
 **/
public class NewsCenterPager extends BasePager{
    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tvTitle.setText("新闻");
        TextView text = new TextView(mActivity);

        setSlidingMenuEnable(true);//打开侧边栏

        text.setText("新闻");
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);


        //向framelayout中动态添加布局
        flContent.addView(text);


    }
}
