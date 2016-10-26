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
    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTabData> children) {
        super(activity);
    }

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);

         mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

        return view;
    }

    @Override
    public void initData() {

        mPagerList = new ArrayList<TabDetailPager>();


    }

    class MenuDetailAdapter extends PagerAdapter{

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
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
