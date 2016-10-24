package com.szzgkon.smartbeijing.fragment;


import android.view.View;

import com.szzgkon.smartbeijing.R;

/**
 * A simple {@link Fragment} subclass.
 */
/**
 * ===================================================
 *
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 *
 * 作者：张勇柯
 *
 * 版本：1.0
 *
 * 创建日期：16/10/21 上午11:18
 *
 * 描述：侧边栏
 *
 * 修订历史：
 *
 * ===================================================
 **/

public class LeftMenuFragment extends BaseFragment {



    @Override
    public View initViews() {
        return    View.inflate(mActivity,R.layout.fragment_left_menu,null);
    }

}
