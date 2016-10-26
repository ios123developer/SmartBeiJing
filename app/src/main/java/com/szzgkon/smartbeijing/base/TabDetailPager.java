package com.szzgkon.smartbeijing.base;


import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

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


    public TabDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(mActivity);
        text.setText("页签详情页");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        return text;
    }
}
