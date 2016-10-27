package com.szzgkon.smartbeijing.base.impl;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.szzgkon.smartbeijing.MainActivity;
import com.szzgkon.smartbeijing.base.BaseMenuDetailPager;
import com.szzgkon.smartbeijing.base.BasePager;
import com.szzgkon.smartbeijing.domain.NewsData;
import com.szzgkon.smartbeijing.fragment.LeftMenuFragment;
import com.szzgkon.smartbeijing.global.GlobalContants;
import com.szzgkon.smartbeijing.base.menudetail.InteractMenuDetailPager;
import com.szzgkon.smartbeijing.base.menudetail.NewsMenuDetailPager;
import com.szzgkon.smartbeijing.base.menudetail.PhotoMenuDetailPager;
import com.szzgkon.smartbeijing.base.menudetail.TopicMenuDetailPager;

import java.util.ArrayList;

/**
 * ===================================================
 *
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 *
 * 作者：zhangyongke
 *
 * 版本：1.0
 *
 * 创建日期：16/10/24 下午2:18
 *
 * 描述：新闻中心
 *
 *
 * 修订历史：
 *
 * ===================================================
 **/
public class NewsCenterPager extends BasePager{

    private ArrayList<BaseMenuDetailPager> mPages;//4个菜单详情页的集合
    private NewsData mNewsData;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tvTitle.setText("新闻");

        setSlidingMenuEnable(true);//打开侧边栏


        getDataFromServer();

    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {



        HttpUtils utils = new HttpUtils();


        //使用xuitls发送请求
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.CATEGORIES_URL, new RequestCallBack<String>() {


            /**
             * 访问成功
             * @param responseInfo
             */
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;

                System.out.println(result);

                //解析数据
                paserData(result);

            }

            /**
             * 访问失败
             * @param e
             * @param s
             */
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity,s,Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }
        });
    }

    /**
     * 解析数据
     * @param result
     */
    private void paserData(String result) {
        Gson gson = new Gson();

        mNewsData = gson.fromJson(result, NewsData.class);

        System.out.println("解析结果：" + mNewsData);

        MainActivity mainUi = (MainActivity) mActivity;


        LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();

        leftMenuFragment.setMenuData(mNewsData);


        //准备4个菜单详情页

        mPages = new ArrayList<BaseMenuDetailPager>();
        mPages.add(new NewsMenuDetailPager(mActivity,
                mNewsData.data.get(0).children));
        mPages.add(new TopicMenuDetailPager(mActivity));
        mPages.add(new PhotoMenuDetailPager(mActivity));
        mPages.add(new InteractMenuDetailPager(mActivity));


        setCuttentMenuDetailPager(0);//设置菜单详情页-新闻为默认当前页

    }

    /**
     * 设置当前菜单详情页
     */
    public void setCuttentMenuDetailPager(int position){

        BaseMenuDetailPager pager = mPages.get(position);//获取当前要显示的菜单详情页
        flContent.removeAllViews();//清楚之前的布局
        flContent.addView(pager.mRootView);//将菜单详情页的布局设置给帧布局

        //设置当前页的标题
        NewsData.NewsMenuData menuData = mNewsData.data.get(position);
        tvTitle.setText(menuData.title);

        pager.initData();//初始化当前页面的数据


    }

}
