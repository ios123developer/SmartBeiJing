package com.szzgkon.smartbeijing.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.szzgkon.smartbeijing.base.BaseMenuDetailPager;

/**
 * ===================================================
 *
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 *
 * 作者：张勇柯
 *
 * 版本：1.0
 *
 * 创建日期：16/10/26 上午10:21
 *
 * 描述：菜单详情页-互动
 *
 * 修订历史：
 *
 * ===================================================
 **/

public class InteractMenuDetailPager extends BaseMenuDetailPager {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {

        TextView text = new TextView(mActivity);
        text.setText("菜单详情页-互动");
        text.setTextSize(25);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        return text;
    }
}
