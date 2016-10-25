package com.szzgkon.smartbeijing.fragment;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.szzgkon.smartbeijing.R;
import com.szzgkon.smartbeijing.base.BasePager;
import com.szzgkon.smartbeijing.base.impl.GovAffairsPager;
import com.szzgkon.smartbeijing.base.impl.HomePager;
import com.szzgkon.smartbeijing.base.impl.NewsCenterPager;
import com.szzgkon.smartbeijing.base.impl.SettingPager;
import com.szzgkon.smartbeijing.base.impl.SmartServicePager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * ===================================================
 * <p/>
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 * <p/>
 * 作者：张勇柯
 * <p/>
 * 版本：1.0
 * <p/>
 * 创建日期：16/10/21 上午11:18
 * <p/>
 * 描述：主页内容
 * <p/>
 * 修订历史：
 * <p/>
 * ===================================================
 **/

public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.rg_group)
    private RadioGroup rgGroup;

    @ViewInject(R.id.vp_content)
    private ViewPager mViewPager;

    private ArrayList<BasePager> mPagerList;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);

//        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        ViewUtils.inject(this, view);//注入view和事件

        return view;
    }

    @Override
    public void initData() {
        rgGroup.check(R.id.rb_home);//表示默认的勾选首页

        //初始化5个子页面
        mPagerList = new ArrayList<BasePager>();

//        for (int i = 0; i < 5; i++) {
//            BasePager pager = new BasePager(mActivity);
//            mPagerList.add(pager);
//        }

        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new NewsCenterPager(mActivity));
        mPagerList.add(new SmartServicePager(mActivity));
        mPagerList.add(new GovAffairsPager(mActivity));
        mPagerList.add(new SettingPager(mActivity));

        mViewPager.setAdapter(new ContentAdapter());

        /**
         * 监听RadioGroup的选择时间
         */
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        //第二个参数是去掉切换页面的滑动效果
                        mViewPager.setCurrentItem(0,false);

                        break;
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(1,false);

                        break;
                    case R.id.rb_smart:
                        mViewPager.setCurrentItem(2,false);

                        break;
                    case R.id.rb_gov:
                        mViewPager.setCurrentItem(3,false);

                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4,false);

                        break;

                    default:
                        break;


                }
            }
        });


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mPagerList.get(position).initData();//获取当前被选中的页面，初始化该页面的数据
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPagerList.get(0).initData();//初始化首页数据
    }


    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            BasePager pager = mPagerList.get(position);


            container.addView(pager.mRootView);
//            pager.initData();//初始化数据..........不要放在此处初始化数据，否则会预加载下一个页面，导致逻辑问题
            return pager.mRootView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
