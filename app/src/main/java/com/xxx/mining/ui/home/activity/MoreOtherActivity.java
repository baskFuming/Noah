package com.xxx.mining.ui.home.activity;

import android.app.Activity;
import android.content.Intent;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;

/**
 * 更多
 */
public class MoreOtherActivity extends BaseTitleActivity {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MoreOtherActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected String initTitle() {
        return getString(R.string.main_home_other);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_other;
    }

    @Override
    protected void initData() {

    }
}
