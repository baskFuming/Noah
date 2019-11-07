package com.xxx.mining.ui.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;

/**
 * 挖矿
 */
public class MiningActivity extends BaseTitleActivity {

    @Override
    protected String initTitle() {
        return getString(R.string.mining_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mining;
    }

    @Override
    protected void initData() {

    }
}
