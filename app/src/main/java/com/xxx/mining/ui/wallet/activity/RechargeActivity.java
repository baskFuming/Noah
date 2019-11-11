package com.xxx.mining.ui.wallet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.RecordRechargeBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.bean.base.PageBean;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.model.utils.StringUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.model.utils.ZXingUtil;
import com.xxx.mining.ui.wallet.adapter.RechargeRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 充值页
 * @Author xxx
 */
public class RechargeActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static void actionStart(Activity activity, String address, int coinId, String coinName) {
        Intent intent = new Intent(activity, RechargeActivity.class);
        intent.putExtra("address", address);
        intent.putExtra("coinId", coinId);
        intent.putExtra("coinName", coinName);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        content = intent.getStringExtra("address");
        coinId = intent.getIntExtra("coinId", 0);
        coinName = intent.getStringExtra("coinName");
    }

    @BindView(R.id.recharge_image)
    ImageView mImage;
    @BindView(R.id.recharge_my_address)
    TextView mAddress;
    @BindView(R.id.recharge_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.recharge_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_home_app_bar)
    AppBarLayout mAppBar;

    private int coinId;
    private String content;
    private String coinName;
    private int page = ConfigClass.PAGE_DEFAULT;
    private RechargeRecordAdapter mAdapter;
    private List<RecordRechargeBean> mList = new ArrayList<>();

    @Override
    protected String initTitle() {
        return coinName + getString(R.string.recharge_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initBundle();

        Bitmap bitmap = ZXingUtil.createQRCode(content, (int) getResources().getDimension(R.dimen.zxCode_size));
        mImage.setImageBitmap(bitmap);
        mAddress.setText(StringUtil.getAddress(content));

        mAdapter = new RechargeRecordAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mRefresh.setEnabled(true);
                } else {
                    mRefresh.setEnabled(false);
                }
            }
        });
        loadData();
    }

    @OnClick({R.id.main_return, R.id.recharge_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_return:
                finish();
                break;
            case R.id.recharge_btn:
                KeyBoardUtil.copy(this, content);
                break;
        }
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


    /**
     * @Model 获取存币记录列表
     */
    private void loadData() {
        Api.getInstance().getRechargeRecordList(coinId, page, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<RecordRechargeBean>>(this) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<RecordRechargeBean>> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }

                        List<RecordRechargeBean> list = bean.getData().getList();
                        if (list == null || list.size() == 0 && page == ConfigClass.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        mRecycler.setVisibility(View.VISIBLE);
                        mNotData.setVisibility(View.GONE);
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
