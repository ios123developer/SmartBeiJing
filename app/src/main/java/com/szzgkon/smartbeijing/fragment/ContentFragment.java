package com.szzgkon.smartbeijing.fragment;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
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
 * 描述：主页内容
 *
 * 修订历史：
 *
 * ===================================================
 **/

public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.rg_group)
    private RadioGroup rgGroup;

    @ViewInject(R.id.vp_content)
    private ViewPager mViewPager;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);

//        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        ViewUtils.inject(this,view);//注入view和事件

        return view;
    }

    @Override
    public void initData() {
        rgGroup.check(R.id.rb_home);//表示默认的勾选首页
    }


    class ContentAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            return null;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
