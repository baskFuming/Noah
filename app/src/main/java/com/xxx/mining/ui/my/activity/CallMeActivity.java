package com.xxx.mining.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.utils.ImageUtil;

import butterknife.OnClick;

public class CallMeActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CallMeActivity.class);
        activity.startActivity(intent);
    }

    private Bitmap mFacebookBitmap;
    private Bitmap mWeChatBitmap;
    private Bitmap mBitmap;


    @Override
    protected String initTitle() {
        return getString(R.string.call_my_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_me;
    }

    @Override
    protected void initData() {
        mFacebookBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.facebook_code);
        mWeChatBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.twitter_code);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wechat_code);

    }

    @OnClick({R.id.call_me_facebook_save, R.id.call_me_we_chat_save,R.id.call_me_wechat_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.call_me_facebook_save:
                ImageUtil.saveImage(this, mFacebookBitmap);
                break;
            case R.id.call_me_we_chat_save:
                ImageUtil.saveImage(this, mWeChatBitmap);
                break;
            case R.id.call_me_wechat_save:
                ImageUtil.saveImage(this, mBitmap);
                break;
        }
    }
}
