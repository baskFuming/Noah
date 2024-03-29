package com.xxx.mining.ui.my.activity.record;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.RecordMiningBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.my.adapter.RecordMiningAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecordMiningActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    @BindView(R.id.record_mining_asset)
    TextView mTotalAsset;

    private int page = ConfigClass.PAGE_DEFAULT;
    private RecordMiningAdapter mAdapter;
    private List<RecordMiningBean.ListBean> mList = new ArrayList<>();

    @Override
    protected String initTitle() {
        return getString(R.string.record_mining_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_mining;
    }

    @Override
    protected void initData() {
        mAdapter = new RecordMiningAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);

        loadData();
    }

    @Override
    public void onRefresh() {
        page = ConfigClass.PAGE_DEFAULT;
        loadData();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadData();
    }

    private void loadData() {
        Api.getInstance().getRecordMiningList(page, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<RecordMiningBean>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(BaseBean<RecordMiningBean> bean) {
                        if (bean != null) {
                            RecordMiningBean date = bean.getData();
                            if (date != null) {
                                mTotalAsset.setText(new BigDecimal(date.getTotalIncome()).setScale(2, RoundingMode.DOWN).toPlainString()+" MOS");
                                List<RecordMiningBean.ListBean> list = date.getList();
                                if (list == null || list.size() == 0 && page == ConfigClass.PAGE_DEFAULT) {
                                    mNotData.setVisibility(View.VISIBLE);
                                    mRecycler.setVisibility(View.GONE);
                                    mAdapter.loadMoreEnd(true);
                                    return;
                                }
                                mNotData.setVisibility(View.GONE);
                                mRecycler.setVisibility(View.VISIBLE);
                                mList.clear();
                                mList.addAll(list);
                                if (list.size() < ConfigClass.PAGE_SIZE) {
                                    mAdapter.loadMoreEnd(true);
                                } else {
                                    mAdapter.loadMoreComplete();
                                }
                                mAdapter.notifyDataSetChanged();

                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                        mNotData.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mRefresh != null && page == ConfigClass.PAGE_DEFAULT) {
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
