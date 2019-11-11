package com.xxx.mining.ui.my.activity.record;

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
import com.xxx.mining.model.http.bean.RecordGiftBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.my.adapter.RecordGiftAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 分红记录
 * @Author xxx
 */
public class RecordGiftActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    @BindView(R.id.record_gift_total)
    TextView mTotal;
    @BindView(R.id.record_gift_s)
    TextView mS;
    @BindView(R.id.record_gift_k)
    TextView mK;
    @BindView(R.id.record_gift_pair)
    TextView mPair;

    private int page = ConfigClass.PAGE_DEFAULT;
    private RecordGiftAdapter mAdapter;
    private List<RecordGiftBean.DataBean> mList = new ArrayList<>();

    @Override
    protected String initTitle() {
        return getString(R.string.record_gift_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_gift;
    }

    @Override
    protected void initData() {
        mAdapter = new RecordGiftAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);

        loadList();
    }

    @Override
    public void onRefresh() {
        page = ConfigClass.PAGE_DEFAULT;
        loadList();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadList();
    }

    private void loadList() {
        Api.getInstance().getRecordGiftList(page, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<RecordGiftBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<RecordGiftBean> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        RecordGiftBean data = bean.getData();
                        if (data == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        mTotal.setText(data.getTotalIncome());
                        mS.setText(data.getDepthAmount());
                        mK.setText(data.getWidthAmount());
                        mPair.setText(data.getTouchNum());
                        List<RecordGiftBean.DataBean> list = data.getList();
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
