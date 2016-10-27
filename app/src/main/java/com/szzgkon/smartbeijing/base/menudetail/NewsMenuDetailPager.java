package com.szzgkon.smartbeijing.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.szzgkon.smartbeijing.R;
import com.szzgkon.smartbeijing.base.BaseMenuDetailPager;
import com.szzgkon.smartbeijing.base.TabDetailPager;
import com.szzgkon.smartbeijing.domain.NewsData;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

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
 * 描述：菜单详情页-新闻
 *
 * 修订历史：
 *
 * ===================================================
 **/

public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private ViewPager mViewPager;

    private ArrayList<TabDetailPager> mPagerList;

    private ArrayList<NewsData.NewsTabData> mNewsTabData;//页签网络数据
    private TabPageIndicator mIndicator;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTabData> children) {
        super(activity);

        mNewsTabData = children;
    }

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);

         mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

        //初始化自定义控件TabPageIndicator

        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);

        return view;
    }

    @Override
    public void initData() {

        mPagerList = new ArrayList<TabDetailPager>();

        //初始化页签数据
        for (int i = 0; i < mNewsTabData.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity,mNewsTabData.get(i));

            mPagerList.add(pager);
        }

            mViewPager.setAdapter(new MenuDetailAdapter());

            mIndicator.setViewPager(mViewPager);//必须在setAdapter方法之后才可以调用此方法

    }

    class MenuDetailAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabData.get(position).title;
        }

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
           TabDetailPager pager = mPagerList.get(position);

            container.addView(pager.mRootView);

            pager.initData();


            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
