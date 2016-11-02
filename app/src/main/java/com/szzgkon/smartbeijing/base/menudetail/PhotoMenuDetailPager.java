package com.szzgkon.smartbeijing.base.menudetail;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.szzgkon.smartbeijing.R;
import com.szzgkon.smartbeijing.base.BaseMenuDetailPager;
import com.szzgkon.smartbeijing.bitmap.MyBitmapUtils;
import com.szzgkon.smartbeijing.domain.PhotosData;
import com.szzgkon.smartbeijing.global.GlobalContants;
import com.szzgkon.smartbeijing.utils.CacheUtils;

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
 * 描述：菜单详情页-组图
 *
 * 修订历史：
 *
 * ===================================================
 **/

public class PhotoMenuDetailPager extends BaseMenuDetailPager {

    private ListView lvPhoto;
    private GridView gvPhoto;
    private ArrayList<PhotosData.PhotoInfo> mPhotosList;
    private PhotoAdapter mAdapter;

    private ImageButton btnPhoto;

    private boolean isListDisplay = true;//是否是列表展示

    public PhotoMenuDetailPager(Activity activity,ImageButton btnPhoto) {
        super(activity);

        this.btnPhoto = btnPhoto;

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDisPlay();

            }
        });
    }

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);

        lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
        gvPhoto = (GridView) view.findViewById(R.id.gv_photo);

        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(GlobalContants.PHOTOS_URL, mActivity);
        if(TextUtils.isEmpty(cache)){

        }else {
            getDataFromServer();
        }
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.PHOTOS_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;

                System.out.println("返回结果：" + result);

                parseData(result);

                CacheUtils.setCache(GlobalContants.PHOTOS_URL,result,mActivity);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity,s,Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    //解析数据
    private void parseData(String result) {
        Gson gson = new Gson();

        PhotosData data = gson.fromJson(result, PhotosData.class);

        mPhotosList = data.data.news;//获取组图列表集合

        if(mPhotosList != null){
            mAdapter = new PhotoAdapter();

            lvPhoto.setAdapter(mAdapter);
            gvPhoto.setAdapter(mAdapter);
        }

    }

    class PhotoAdapter extends BaseAdapter{

//        private final BitmapUtils utils;

        private final MyBitmapUtils utils;

        public  PhotoAdapter() {

//            utils = new BitmapUtils(mActivity);

//            utils.configDefaultLoadingImage(R.drawable.news_pic_default);

            utils = new MyBitmapUtils();
        }

        @Override
        public int getCount() {
            return mPhotosList.size();
        }

        @Override
        public PhotosData.PhotoInfo getItem(int position) {
            return mPhotosList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if(convertView == null){
                  convertView = View.inflate(mActivity,R.layout.list_photo_item,null);
                holder = new ViewHolder();

                holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
                holder.ivPic = (ImageView)convertView.findViewById(R.id.iv_pic);

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            PhotosData.PhotoInfo item = getItem(position);

            holder.tvTitle.setText(item.title);

            utils.display(holder.ivPic,item.listimage);


            return convertView;
        }
    }

    static class ViewHolder{
        public TextView tvTitle;
        public ImageView ivPic;
    }

    //切换展现方式
    private void changeDisPlay(){
        if(isListDisplay){
         isListDisplay = false;

            lvPhoto.setVisibility(View.GONE);
            gvPhoto.setVisibility(View.VISIBLE);

            btnPhoto.setImageResource(R.mipmap.icon_pic_list_type);

        }else {
            isListDisplay = true;

            lvPhoto.setVisibility(View.VISIBLE);
            gvPhoto.setVisibility(View.GONE);

            btnPhoto.setImageResource(R.mipmap.icon_pic_grid_type);
        }


    }

}
