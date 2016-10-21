package com.szzgkon.smartbeijing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

    private RelativeLayout rl_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rl_root = (RelativeLayout)findViewById(R.id.rl_root);
        startAnim();
    }

    /**
     * 开启动画
     */
    private void startAnim(){
        //动画集合
        AnimationSet set = new AnimationSet(false);

            //选装动画
        RotateAnimation rotate = new RotateAnimation(0,360,
                        Animation.RELATIVE_TO_SELF,0.5f,
                        Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);//时间
        rotate.setFillAfter(true);//保持动画状态

        //缩放动画
        ScaleAnimation scale = new ScaleAnimation(0,1,0,1,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);//时间
        scale.setFillAfter(true);//保持动画状态

            //渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        alpha.setDuration(1000);//时间
        alpha.setFillAfter(true);//保持动画状态


        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               //跳转到新手引导页
                jumpNextpage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rl_root.startAnimation(set);
    }

    private void jumpNextpage(){

        //判断之前有没有显示过新手引导
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean userGuide = sp.getBoolean("is_user_guide_showed", false);
        if(!userGuide){
            startActivity(new Intent(SplashActivity.this,GuideActivity.class));

        }else {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }

        finish();
    }
}
