package com.xxx.mining.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.ui.home.adapter.ConsultingAdpater;

/**
 * 资讯
 */
public class ConsultingActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ConsultingActivity.class);
        activity.startActivity(intent);
    }

    private ConsultingAdpater consultingAdpater;

    @Override
    protected String initTitle() {
        return getString(R.string.main_home_news);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_consulting;
    }

    @Override
    protected void initData() {

    }
}
