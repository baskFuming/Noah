package com.xxx.mining.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawableResource;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseActivity;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
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

//    GifDrawable gifDrawable;

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initData() {
        getWindow().setBackgroundDrawable(null);
        //Glide 加载Gif图 加控制加载次数
        Glide.with(SplashActivity.this)
                .load(R.mipmap.splash_start)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(gifImageView, 1));

        new Thread(new Runnable() {
            @Override
            public void run() {
//                利用消息处理器实现延迟跳转到登录窗口
                mHandler.sendEmptyMessageAtTime(0, ConfigClass.SPLASH_DELAY_TIME);
            }
        }).start();
//        final View decorView = getWindow().getDecorView();
//        decorView.post(new Runnable() {
//            @Override
//            public void run() {
//                gifDrawable = (GifDrawable) gifImageView.getDrawable();
//                设置播放次数  次数为1
//                gifDrawable.setLoopCount(1);
//                double width = decorView.getWidth();
//                double height = decorView.getHeight();
//                if (height / width >= 1.8) {
//                    decorView.setBackgroundResource(R.mipmap.splash_start);
//                }
//                 利用消息处理器实现延迟跳转到登录窗口
//                mHandler.sendEmptyMessageAtTime(0, ConfigClass.SPLASH_DELAY_TIME);
//            }
//        });
    }

    @OnClick({R.id.skip})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
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
                if (activity != null) {
                    Intent intent;
                    if (SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_LOGIN)) {
                        intent = new Intent(activity, MainActivity.class);
                    } else {
                        intent = new Intent(activity, LoginActivity.class);
                    }
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        }
    }

}
