package com.szzgkon.smartbeijing.base;

import android.app.Activity;

/**
 * ===================================================
 * 
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 * 
 * 作者：张勇柯
 *
 * 版本：1.0
 *
 * 创建日期：16/10/24 上午11:42
 * 
 * 描述：主页下5个子页面的基类
 *
 * 修订历史：
 *
 * ===================================================
 **/
 
public class BasePager {
    public Activity mActivity;

    public BasePager(Activity activity){

        mActivity = activity;

    }

    /**
     * 初始化布局
     */
    public void initViews(){

    }

    /**
     * 初始化数据
     */
    public void initData(){

    }


}
