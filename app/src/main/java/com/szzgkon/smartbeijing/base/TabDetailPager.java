package com.szzgkon.smartbeijing.base;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.szzgkon.smartbeijing.NewsDetailActivity;
import com.szzgkon.smartbeijing.R;
import com.szzgkon.smartbeijing.domain.NewsData.NewsTabData;
import com.szzgkon.smartbeijing.domain.TabData;
import com.szzgkon.smartbeijing.domain.TabData.TopNewsData;
import com.szzgkon.smartbeijing.global.GlobalContants;
import com.szzgkon.smartbeijing.utils.CacheUtils;
import com.szzgkon.smartbeijing.utils.PrefUtils;
import com.szzgkon.smartbeijing.view.RefreshListView;
import com.szzgkon.smartbeijing.view.TopNewsViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * 页签详情页
 *
 * @author Kevin
 */
public class TabDetailPager extends BaseMenuDetailPager implements
        ViewPager.OnPageChangeListener {

    NewsTabData mTabData;
    private TextView tvText;

    private String mUrl;
    private TabData mTabDetailData;

    @ViewInject(R.id.vp_news)
    private TopNewsViewPager mViewPager;

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;// 头条新闻的标题
    private ArrayList<TopNewsData> mTopNewsList;// 头条新闻数据集合

    @ViewInject(R.id.indicator)
    private CirclePageIndicator mIndicator;// 头条新闻位置指示器

    @ViewInject(R.id.lv_list)
    private RefreshListView lvList;// 新闻列表
    private ArrayList<TabData.TabNewsData> mNewsList; // 新闻数据集合
    private NewsAdapter mNewsAdapter;
    private String mMoreUrl;// 更多页面的地址

    private Handler mHandler;

    public TabDetailPager(Activity activity, NewsTabData newsTabData) {
        super(activity);
        mTabData = newsTabData;
        mUrl = GlobalContants.SERVER_URL + mTabData.url;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        // 加载头布局
        View headerView = View.inflate(mActivity, R.layout.list_header_topnews,
                null);

        ViewUtils.inject(this, view);
        ViewUtils.inject(this, headerView);

        // 将头条新闻以头布局的形式加给listview
        lvList.addHeaderView(headerView);

        // 设置下拉刷新监听
        lvList.setOnRefreshListener(new RefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                if (mMoreUrl != null) {
                    getMoreDataFromServer();
                } else {
                    Toast.makeText(mActivity, "最后一页了", Toast.LENGTH_SHORT)
                            .show();
                    lvList.onRefreshComplete(false);// 收起加载更多的布局
                }
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("被点击:" + position);
                // 35311,34221,34234,34342
                // 在本地记录已读状态
                String ids = PrefUtils.getString(mActivity, "read_ids", "");
                String readId = mNewsList.get(position).id;
                if (!ids.contains(readId)) {
                    ids = ids + readId + ",";
                    PrefUtils.setString(mActivity, "read_ids", ids);
                }

                // mNewsAdapter.notifyDataSetChanged();
                changeReadState(view);// 实现局部界面刷新, 这个view就是被点击的item布局对象

                // 跳转新闻详情页
                Intent intent = new Intent();
                intent.setClass(mActivity, NewsDetailActivity.class);
                intent.putExtra("url", mNewsList.get(position).url);
                mActivity.startActivity(intent);
            }
        });

        return view;
    }

    /**
     * 改变已读新闻的颜色
     */
    private void changeReadState(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.GRAY);
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mUrl, mActivity);

        if (!TextUtils.isEmpty(cache)) {
            parseData(cache, false);
        }

        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                System.out.println("页签详情页返回结果:" + result);

                parseData(result, false);

                lvList.onRefreshComplete(true);

                // 设置缓存
                CacheUtils.setCache(mUrl, result, mActivity);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                error.printStackTrace();

                lvList.onRefreshComplete(false);
            }
        });
    }

    /**
     * 加载下一页数据
     */
    private void getMoreDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;

                parseData(result, true);

                lvList.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                lvList.onRefreshComplete(false);
            }
        });
    }

    private void parseData(String result, boolean isMore) {
        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result, TabData.class);
        System.out.println("页签详情解析:" + mTabDetailData);

        // 处理下一页链接
        String more = mTabDetailData.data.more;
        if (!TextUtils.isEmpty(more)) {
            mMoreUrl = GlobalContants.SERVER_URL + more;
        } else {
            mMoreUrl = null;
        }

        if (!isMore) {
            mTopNewsList = mTabDetailData.data.topnews;

            mNewsList = mTabDetailData.data.news;

            if (mTopNewsList != null) {
                mViewPager.setAdapter(new TopNewsAdapter());
                mIndicator.setViewPager(mViewPager);
                mIndicator.setSnap(true);// 支持快照显示
                mIndicator.setOnPageChangeListener(this);

                mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点

                tvTitle.setText(mTopNewsList.get(0).title);
            }

            if (mNewsList != null) {
                mNewsAdapter = new NewsAdapter();
                lvList.setAdapter(mNewsAdapter);
            }

            if (mHandler == null) {
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        System.out.println("handle  ...........");

                        int currentItem = mViewPager.getCurrentItem();
                        if (currentItem < mTopNewsList.size() - 1) {

                            currentItem++;

                        } else {
                            currentItem = 0;
                        }

                        mViewPager.setCurrentItem(currentItem);//切换到下一个界面

                        mHandler.sendEmptyMessageDelayed(0, 3000);//继续延时3秒发消息，形成循环

                    }
                };

                mHandler.sendEmptyMessageDelayed(0, 3000);//延时3秒发消息
            }


        } else {// 如果是加载下一页,需要将数据追加给原来的集合
            ArrayList<TabData.TabNewsData> news = mTabDetailData.data.news;
            mNewsList.addAll(news);
            mNewsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 头条新闻适配器
     */
    class TopNewsAdapter extends PagerAdapter {

        private BitmapUtils utils;

        public TopNewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.mipmap.topnews_item_default);// 设置默认图片
        }

        @Override
        public int getCount() {
            return mTabDetailData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(mActivity);
            image.setScaleType(ImageView.ScaleType.FIT_XY);// 基于控件大小填充图片

            TopNewsData topNewsData = mTopNewsList.get(position);
            utils.display(image, topNewsData.topimage);// 传递imagView对象和图片地址

            container.addView(image);

            image.setOnTouchListener(new TopNewsTouchListener());//设置触摸监听


            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 头条新闻的触摸监听
     */
    class TopNewsTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://按下停止轮播

                    mHandler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_CANCEL://事件取消也要继续发消息

                    mHandler.sendEmptyMessageDelayed(0, 3000);

                    break;

                case MotionEvent.ACTION_UP://抬起继续让handler发消息

                    mHandler.sendEmptyMessageDelayed(0, 3000);

                    break;
                default:
                    break;


            }


            return true;
        }
    }


    /**
     * 新闻列表的适配器
     */
    class NewsAdapter extends BaseAdapter {

        private BitmapUtils utils;

        public NewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.mipmap.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public TabData.TabNewsData getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_news_item,
                        null);
                holder = new ViewHolder();
                holder.ivPic = (ImageView) convertView
                        .findViewById(R.id.iv_pic);
                holder.tvTitle = (TextView) convertView
                        .findViewById(R.id.tv_title);
                holder.tvDate = (TextView) convertView
                        .findViewById(R.id.tv_date);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            TabData.TabNewsData item = getItem(position);

            holder.tvTitle.setText(item.title);
            holder.tvDate.setText(item.pubdate);

            utils.display(holder.ivPic, item.listimage);

            String ids = PrefUtils.getString(mActivity, "read_ids", "");
            if (ids.contains(getItem(position).id)) {
                holder.tvTitle.setTextColor(Color.GRAY);
            } else {
                holder.tvTitle.setTextColor(Color.BLACK);
            }

            return convertView;
        }

    }

    static class ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivPic;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        TopNewsData topNewsData = mTopNewsList.get(arg0);
        tvTitle.setText(topNewsData.title);
    }
}
