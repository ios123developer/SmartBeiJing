package com.szzgkon.smartbeijing.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.szzgkon.smartbeijing.MainActivity;
import com.szzgkon.smartbeijing.R;

/**
 * ===================================================
 * 
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 * 
 * 作者：张勇柯
 *
 * 版本：1.0
 *
 * 创建日期：16/10/24 上午11:42
 * 
 * 描述：主页下5个子页面的基类
 *
 * 修订历史：
 *
 * ===================================================
 **/
 
public class BasePager {
    public Activity mActivity;
    public View mRootView;

    public TextView tvTitle;//标题对象

    public FrameLayout flContent;//内容

    public ImageButton btnMenu;//菜单按钮

    public ImageButton btnPhoto;//组图切换按钮

    public BasePager(Activity activity){

        mActivity = activity;
        initViews();
    }

    /**
     * 初始化布局
     */
    public void initViews(){
        mRootView = View.inflate(mActivity, R.layout.base_pager,null);
            tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);

            flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);

        btnMenu = (ImageButton)mRootView.findViewById(R.id.btn_menu);

        btnPhoto = (ImageButton) mRootView.findViewById(R.id.btn_photo);


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
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
     * 初始化数据
     */
    public void initData(){

    }

    //设置侧边栏开启或者关闭
    public void setSlidingMenuEnable (boolean enable){
        MainActivity mainUi = (MainActivity) mActivity;

        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        if(enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        }


    }


}
