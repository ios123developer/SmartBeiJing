package com.szzgkon.smartbeijing;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.szzgkon.smartbeijing.fragment.ContentFragment;
import com.szzgkon.smartbeijing.fragment.LeftMenuFragment;

/**
 * ===================================================
 *
 * 版权：深圳市中广控信息科技有限公司 版权所有（c）2016
 *
 * 作者：张勇柯
 *
 * 版本：1.0
 *
 * 创建日期：16/10/21 上午9:13
 *
 * 描述：主页面
 *
 * 修订历史：
 *
 * ===================================================
 **/

public class MainActivity extends SlidingFragmentActivity {

    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTENT = "fragment_content";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.left_menu);//设置侧边栏

        SlidingMenu slidingMenu = getSlidingMenu();//获取侧边栏对象
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//支持全屏触摸

        slidingMenu.setBehindOffset(200);

        initFragment();

    }

    /**
     * 初始化fragment，将fragment数据填充给布局文件
     */
    private void initFragment(){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();//开启事务
        transaction.replace(R.id.fl_left_menu,new LeftMenuFragment(),
                                            FRAGMENT_LEFT_MENU);//用fragment替换framelayout
        transaction.replace(R.id.fl_content,new ContentFragment(),
                                            FRAGMENT_CONTENT);

        transaction.commit();//提交事务

        //Fragment leftMenuFragment = fm.findFragmentByTag(FRAGMENT_LEFT_MENU);

    }
}
