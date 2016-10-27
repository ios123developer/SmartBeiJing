package com.szzgkon.smartbeijing.base;


import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.szzgkon.smartbeijing.domain.NewsData;

/**
 * ===================================================
 *
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 *
 * 作者：张勇柯
 *
 * 版本：1.0
 *
 * 创建日期：16/10/26 下午4:39
 *
 * 描述：页签详情页
 *
 * 修订历史：
 *
 * ===================================================
 **/

public class TabDetailPager extends BaseMenuDetailPager{

    NewsData.NewsTabData mTabData;
    private TextView tvText;

    public TabDetailPager(Activity activity, NewsData.NewsTabData newsTabData) {
        super(activity);
        mTabData = newsTabData;
    }

    @Override
    public View initViews() {
        tvText = new TextView(mActivity);
        tvText.setText("页签详情页");
        tvText.setTextColor(Color.RED);
        tvText.setTextSize(25);
        tvText.setGravity(Gravity.CENTER);

        return tvText;
    }

    @Override
    public void initData() {
        tvText.setText(mTabData.title);
    }
}
