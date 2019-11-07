package com.xxx.mining.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.ui.mining.MiningFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的节点
 */
public class MyNodeActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyNodeActivity.class);
        activity.startActivity(intent);
    }


    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    @Override
    protected String initTitle() {
        return getString(R.string.main_my_node);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_node;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.mill_record, R.id.node_up, R.id.node_invite})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.mill_record://矿机记录
                break;
            case R.id.node_up://竟升节点
                AscendingNodeActivity.actionStart(this);
                break;
            case R.id.node_invite://节点邀请码
                KeyBoardUtil.copy(this, "");
                break;
        }
    }
}
