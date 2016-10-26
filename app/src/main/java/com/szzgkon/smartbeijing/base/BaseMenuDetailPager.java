package com.szzgkon.smartbeijing.base;

import android.app.Activity;
import android.view.View;

/**
 * ===================================================
 *
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 *
 * 作者：zhangyongke
 *
 * 版本：1.0
 *
 * 创建日期：16/10/26 上午10:08
 *
 * 描述：菜单详情页基类
 *
 *
 * 修订历史：
 *
 * ===================================================
 **/
public abstract class BaseMenuDetailPager {

    public Activity mActivity;

    public View mRootView;//跟布局对象

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initViews();
    }

    /**
     * 初始化界面
     */
    public abstract View initViews();

    /**
     * 初始化数据
     */
    public  void initData(){

    }
}
