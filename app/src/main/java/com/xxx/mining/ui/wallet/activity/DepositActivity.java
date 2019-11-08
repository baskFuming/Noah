package com.xxx.mining.ui.wallet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.xxx.mining.model.http.bean.DepositBean;
import com.xxx.mining.model.http.bean.RecordDepositBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.bean.base.PageBean;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.wallet.adapter.DepositProfitAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 理财页
 * @Author xxx
 */
public class DepositActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static void actionStart(Activity activity, int amount, String coinId) {
        Intent intent = new Intent(activity, DepositActivity.class);
        intent.putExtra("coinId", coinId);
        intent.putExtra("amount", amount);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        coinId = intent.getStringExtra("coinId");
        inBalance = intent.getDoubleExtra("amount", 0);

    }


    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    @BindView(R.id.deposit_asset)
    TextView mTotalAsset;
    @BindView(R.id.deposit_asset_text)
    TextView mTotalAssetText;
    @BindView(R.id.deposit_profit)
    TextView mTotalProfit;

    private int page = ConfigClass.PAGE_DEFAULT;
    private DepositProfitAdapter mAdapter;
    private List<RecordDepositBean> mList = new ArrayList<>();
    private String coinId;
    private double inBalance;
    private double outBalance;

    @Override
    protected String initTitle() {
        return getString(R.string.deposit_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initBundle();
//        mTotalAssetText.setText(getString(R.string.deposit_total_asset) + "(" + coinId + ")");
        mAdapter = new DepositProfitAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);

        loadInfo();
        loadData();

    }

    @OnClick({R.id.deposit_in, R.id.deposit_out})
    public void OnClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.deposit_in:
                intent = new Intent(this, DepositInActivity.class);
                intent.putExtra("amount", inBalance);
                break;
            case R.id.deposit_out:
                intent = new Intent(this, DepositOutActivity.class);
                intent.putExtra("amount", outBalance);
                break;
        }
        if (intent != null) {
            intent.putExtra("coinId", coinId);
            startActivityForResult(intent, ConfigClass.REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInfo();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConfigClass.DEPOSIT_IN_RESULT_CODE && data != null) {
            double in = data.getDoubleExtra("inBalance", inBalance);
            if (in != inBalance) {
                outBalance += new BigDecimal(inBalance).subtract(new BigDecimal(in)).setScale(ConfigClass.DOUBLE_AMOUNT_NUMBER, RoundingMode.DOWN).doubleValue();
                inBalance = in;
            }
        }
        if (resultCode == ConfigClass.DEPOSIT_OUT_RESULT_CODE && data != null) {
            double out = data.getDoubleExtra("outBalance", outBalance);
            if (out != outBalance) {
                inBalance += new BigDecimal(outBalance).subtract(new BigDecimal(out)).setScale(ConfigClass.DOUBLE_AMOUNT_NUMBER, RoundingMode.DOWN).doubleValue();
                outBalance = out;
            }
        }
    }

    /**
     * @Model 获取理财总收益信息
     */
    private void loadInfo() {
        Api.getInstance().getDepositInfo(coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<DepositBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<DepositBean> bean) {
                        if (bean != null) {
                            DepositBean data = bean.getData();
                            if (data != null) {
                                mTotalAsset.setText(String.valueOf(data.getInvest()));
                                mTotalProfit.setText(String.valueOf(data.getIncome()));
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }


    /**
     * @Model 获取首页列表
     */
    private void loadData() {
        Api.getInstance().getRecordDepositList(coinId, page, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<RecordDepositBean>>(this) {
                    @Override
                    public void onSuccess(BaseBean<PageBean<RecordDepositBean>> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        PageBean<RecordDepositBean> pageBean = bean.getData();
                        if (pageBean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        List<RecordDepositBean> list = pageBean.getList();
                        if (list == null || list.size() == 0 && page == ConfigClass.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }

                        mNotData.setVisibility(View.GONE);
                        mRecycler.setVisibility(View.VISIBLE);
                        if (page == ConfigClass.PAGE_DEFAULT) {
                            mList.clear();
                        }

                        mList.addAll(list);
                        if (list.size() < ConfigClass.PAGE_SIZE) {
                            mAdapter.loadMoreEnd(true);
                        } else {
                            mAdapter.loadMoreComplete();
                        }
                        mAdapter.notifyDataSetChanged();
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
