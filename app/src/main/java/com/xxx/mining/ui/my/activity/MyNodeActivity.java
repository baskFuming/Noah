package com.xxx.mining.ui.my.activity;

import android.app.Activity;
import android.content.Intent;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;

public class MyNodeActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyNodeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_node;
    }

    @Override
    protected void initData() {

    }
}
