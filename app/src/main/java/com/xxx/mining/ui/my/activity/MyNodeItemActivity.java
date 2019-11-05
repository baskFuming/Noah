package com.xxx.mining.ui.my.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;

import butterknife.BindView;

/**
 * @Page 分享收益详情页
 * @Author xxx
 */
public class MyNodeItemActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.achievement_record_item_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.achievement_record_item_refresh)
    SwipeRefreshLayout mRefresh;

    @Override
    protected String initTitle() {
        return getIntent().getStringExtra("title");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order_item;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
//        RecordTeamBean.CommunityInfoListBean data = (RecordTeamBean.CommunityInfoListBean) intent.getSerializableExtra("data");
//        List<RecordTeamBean.CommunityInfoListBean.TeamListBean> list = data.getTeamList();
//        mRecycler.setLayoutManager(new LinearLayoutManager(this));
//        mRecycler.setAdapter(new AchievementRecordItemAdapter(list, this));
//        mRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(false);
    }
}
