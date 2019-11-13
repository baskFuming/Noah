package com.xxx.mining.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;

import butterknife.OnClick;

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

    @OnClick({R.id.main_app_eth_browser, R.id.main_app_btc_browser, R.id.main_app_eos_browser})
    public void OnClick(View view) {
        Uri uri = null;
        switch (view.getId()) {
            case R.id.main_app_eth_browser:
                uri = Uri.parse(ConfigClass.APP_ETH_BROWSER);
                break;
            case R.id.main_app_btc_browser:
                uri = Uri.parse(ConfigClass.APP_BTC_BROWSER);
                break;
            case R.id.main_app_eos_browser:
                uri = Uri.parse(ConfigClass.APP_EOS_BROWSER);
                break;
        }
        if (uri != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}
