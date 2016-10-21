package com.szzgkon.smartbeijing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.szzgkon.smartbeijing.utils.PrefUtils;

import java.util.ArrayList;

public class GuideActivity extends Activity {

    private static final int[] mImageIds = new int[]{R.mipmap.guide_1,
                                                     R.mipmap.guide_2,
                                                     R.mipmap.guide_3};
    private ViewPager vpGuide;
    private ArrayList<ImageView> mImageViewList;

    private LinearLayout llPointGroup;//引导页的小圆点
    private int mPointWidth;//圆点之间的距离

    private View viewRedPoint;//小红点

    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题

        setContentView(R.layout.activity_guide);

        vpGuide = (ViewPager)findViewById(R.id.vp_guide);

        llPointGroup = (LinearLayout)findViewById(R.id.ll_point_group);

        viewRedPoint = (View)findViewById(R.id.view_red_point);

        btn_start = (Button)findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新sp
                PrefUtils.setBoolean(GuideActivity.this,"is_user_guide_showed",true);

                //跳转主页面
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });


        initViews();
        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.setOnPageChangeListener(new GuidePageListener());
    }


    /**
     * 初始化界面
     */
    private void initViews(){

        mImageViewList = new ArrayList<ImageView>();

        //初始化引导页的界面
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(imageView);
        }

        //初始化引导的小圆点
        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);

            if(i > 0){
                params.leftMargin = 10;//设置圆点间隔

            }
            //设置圆点的布局
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            llPointGroup.addView(point);//将圆点添加到线性布局中
        }

        //获取视图树,对layout结束事件进行监听
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            /**
             * 当layout执行结束之后会回调此方法
             */
            @Override
            public void onGlobalLayout() {
                //measure(测量大小） layout(界面位置) ondraw将控件绘制出来
                //拿到两个小圆点之间的距离
                mPointWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
                System.out.println("圆点之间的宽度是 " + mPointWidth);

                    //为了防止重复调用此方法，第一次执行此方法之后就将监听器删除掉
                    llPointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            }
        });
    }

    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));

            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }

    /**
     * viewPager的滑动监听
     */
    class GuidePageListener implements ViewPager.OnPageChangeListener{

        /**
         * 滑动事件
         * @param position
         * @param positionOffset
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float len = mPointWidth * positionOffset;
            //获取当期红点的布局参数
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewRedPoint.getLayoutParams();
              //设置左边距
            params.leftMargin = (int) len + position * mPointWidth;
            //重新给小红点设置布局参数
            viewRedPoint.setLayoutParams(params);
        }

        /**
         * 某个页面被选中
         * @param position
         */
        @Override
        public void onPageSelected(int position) {

            if(position == mImageIds.length - 1){
                btn_start.setVisibility(View.VISIBLE);
            }else {
                btn_start.setVisibility(View.INVISIBLE);
            }
        }

        /**
         * 滑动状态发生变化
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
