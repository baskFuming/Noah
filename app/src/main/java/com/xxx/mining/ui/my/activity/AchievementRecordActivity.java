package com.xxx.mining.ui.my.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.AchievementRecordBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.my.adapter.AchievementRecordAdapter;
import com.xxx.mining.ui.my.adapter.AchievementRecordItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 分享业绩页
 * @Author xxx
 */
public class AchievementRecordActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.achievement_record_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.achievement_record_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.achievement_record_total)
    TextView mTotal;

    private AchievementRecordAdapter mAdapter;
    private List<AchievementRecordBean.CommunityInfoListBean> mList = new ArrayList<>();

    private AchievementRecordItemAdapter adapter;
    private List<AchievementRecordBean.CommunityInfoListBean.TeamListBean> dlist = new ArrayList<>();

    @Override
    protected String initTitle() {
        return getString(R.string.profit_record_achievement);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_achievement_record;
    }

    @Override
    protected void initData() {
//        mAdapter = new AchievementRecordAdapter(mList, this);
        adapter = new AchievementRecordItemAdapter(dlist, this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        mRefresh.setOnRefreshListener(this);

//        loadData();
        loadDataList();
    }


    @Override
    public void onRefresh() {
//        loadData();
        loadDataList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        Intent intent = new Intent(this, AchievementRecordItemActivity.class);
//        AchievementRecordBean.CommunityInfoListBean bean = mList.get(position);
//        intent.putExtra("data", bean);
//        intent.putExtra("title", bean.getLevel(this));
//        startActivity(intent);
    }

    /**
     * @Model 获取分享业绩列表
     */
    private void loadData() {
        Api.getInstance().getAchievementRecordList(String.valueOf(SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_USER_ID)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<AchievementRecordBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<AchievementRecordBean> bean) {
                        if (bean != null) {
                            AchievementRecordBean dataBean = bean.getData();
                            if (dataBean != null) {
                                mTotal.setText(dataBean.getReferTeamAsset());
                                List<AchievementRecordBean.CommunityInfoListBean> list = dataBean.getCommunityInfoList();
                                if (list != null && list.size() != 0) {
                                    mNotData.setVisibility(View.GONE);
                                    mRecycler.setVisibility(View.VISIBLE);
                                    mList.clear();
                                    mList.addAll(list);
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    mNotData.setVisibility(View.VISIBLE);
                                    mRecycler.setVisibility(View.GONE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(true);
                        }
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    }
                });
    }

    /**
     * @Model 获取分享业绩列表
     */
    private void loadDataList() {
        Api.getInstance().getAchievementRecordList(String.valueOf(SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_USER_ID)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<AchievementRecordBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<AchievementRecordBean> bean) {
                        if (bean != null) {
                            AchievementRecordBean dataBean = bean.getData();
                            if (dataBean != null) {
                                mTotal.setText(dataBean.getReferTeamAsset());
                                List<AchievementRecordBean.CommunityInfoListBean> list = dataBean.getCommunityInfoList();
                                if (list != null && list.size() != 0) {
                                    List<AchievementRecordBean.CommunityInfoListBean.TeamListBean> tlist = list.get(0).getTeamList();
                                    if (tlist != null && tlist.size() != 0) {
                                        mNotData.setVisibility(View.GONE);
                                        mRecycler.setVisibility(View.VISIBLE);
                                        dlist.clear();
                                        dlist.addAll(tlist);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        mNotData.setVisibility(View.VISIBLE);
                                        mRecycler.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);

                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(true);
                        }
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    }
                });
    }

}
