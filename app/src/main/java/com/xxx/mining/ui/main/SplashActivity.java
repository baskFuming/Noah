package com.xxx.mining.ui.main;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseActivity;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.GlideUtil;
import com.xxx.mining.ui.login.LoginActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Page 闪屏页
 * @Author xxx
 */
public class SplashActivity extends BaseActivity {

    /**
     * 延迟时间
     */
    private final MyHandler mHandler = new MyHandler(this);

    @BindView(R.id.gif_view)
    ImageView gifImageView;

    //是否登录
    private boolean isLogin;
    //是否执行了跳转页面
    private boolean isStartActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initData() {
        final View decorView = getWindow().getDecorView();
        decorView.post(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                //获取背景，并将其强转成AnimationDrawable
                AnimationDrawable animationDrawable = (AnimationDrawable) decorView.getBackground();
                //判断是否在运行
                if (!animationDrawable.isRunning()) {
                    //开启帧动画
                    if (!SplashActivity.this.isFinishing()) {
                        animationDrawable.start();
                    }
                }

                //是否登录
                isLogin = SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_LOGIN);

                // 利用消息处理器实现延迟跳转到登录窗口
                mHandler.sendEmptyMessageDelayed(0, ConfigClass.SPLASH_DELAY_TIME);
            }
        });

    }

    @OnClick({R.id.skip})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip:
                isStartActivity = true;
                checkUI();
                break;
        }
    }

    private void checkUI() {
        if (isLogin) {
            MainActivity.actionStart(this);
        } else {
            LoginActivity.actionStart(this);
        }
        finish();
    }

    private static class MyHandler extends Handler {

        //弱引用对象
        private final WeakReference<SplashActivity> mActivity;

        MyHandler(SplashActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                SplashActivity activity = mActivity.get();
                if (activity != null && !activity.isStartActivity) {
                    activity.checkUI();
                }
            }
        }
    }
}
