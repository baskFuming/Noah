package com.xxx.mining.ui.home.activity;

import android.app.Activity;
import android.content.Intent;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;

/**
 * 信贷中心
 */
public class CreditCenterActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CreditCenterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected String initTitle() {
        return getString(R.string.credit_center);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_center;
    }

    @Override
    protected void initData() {

    }
}
