package com.szzgkon.smartbeijing.fragment;


import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.szzgkon.smartbeijing.MainActivity;
import com.szzgkon.smartbeijing.R;
import com.szzgkon.smartbeijing.base.impl.NewsCenterPager;
import com.szzgkon.smartbeijing.domain.NewsData;

import java.util.ArrayList;

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
    @ViewInject(R.id.lv_list)

    private ListView lvList;

    private ArrayList<NewsData.NewsMenuData> mMenuList;

    private int mCurrentPos;//当前被点击的条目
    private MenuAdapter mAdapter;

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        ViewUtils.inject(this, view);

        return view;
    }

    //设置网络数据
    public void setMenuData(NewsData data) {

        mMenuList = data.data;

        mAdapter = new MenuAdapter();
        lvList.setAdapter(mAdapter);

    }

    @Override
    public void initData() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos = position;

                mAdapter.notifyDataSetChanged();

                setCurrentMenuDetailPager(position);

                toggleSlidingMenu();//隐藏

            }
        });
    }
//切换SlidingMenu的状态
    private void toggleSlidingMenu() {

    MainActivity mainUi = (MainActivity)mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();//切换状态，显示时隐藏，隐藏时显示
    }

    /**
     * 设置当前菜单详情页
     * @param position
     */
    private void setCurrentMenuDetailPager(int position) {
        MainActivity mainUi = (MainActivity) mActivity;
        ContentFragment fragment = mainUi.getContentFragment();//获取主页面fragment
        NewsCenterPager pager = fragment.getNewsCenterPager();//获取新闻中心页面

        pager.setCuttentMenuDetailPager(position);//设置当前菜单详情页

    }


    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mMenuList.size();
        }

        @Override
        public Object getItem(int position) {
            return mMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(mActivity, R.layout.list_menu_item, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);

            NewsData.NewsMenuData newsMenuData = mMenuList.get(position);

            tvTitle.setText(newsMenuData.title);

            if(mCurrentPos == position){//判断当前绘制的view是否被选中
                //显示红色
                tvTitle.setEnabled(true);

            }else {
                //显示白色

                tvTitle.setEnabled(false);

            }

            return view;
        }
    }
}
