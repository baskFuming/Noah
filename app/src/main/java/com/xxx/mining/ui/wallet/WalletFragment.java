package com.xxx.mining.ui.wallet;

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
import com.xxx.mining.base.fragment.BaseFragment;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.WalletBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.ui.wallet.activity.DepositActivity;
import com.xxx.mining.ui.wallet.activity.RechargeActivity;
import com.xxx.mining.ui.wallet.activity.WithdrawalActivity;
import com.xxx.mining.ui.wallet.adapter.WalletAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 钱包布局
 * @Author xxx
 */
public class WalletFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.main_wallet_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_wallet_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.main_wallet_total)
    TextView mTotalAsset;

    private WalletAdapter mAdapter;
    private List<WalletBean.ListBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);

        mAdapter = new WalletAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnItemChildClickListener(this);
        loadData(true);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        WalletBean.ListBean bean = mList.get(position);
        switch (view.getId()) {
            case R.id.item_wallet_recharge:
                RechargeActivity.actionStart(getActivity(), bean.getAddress(), bean.getCoinId(), bean.getCoinName());
                break;
            case R.id.item_wallet_withdrawal:
                WithdrawalActivity.actionStart(getActivity(), bean.getAmount(), bean.getFee(), bean.getCoinId(), bean.getCoinName(), bean.getAddress());
                break;
            case R.id.item_wallet_deposit:
                DepositActivity.actionStart(getActivity(), bean.getAmount(), bean.getCoinId());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(String eventFlag) {
        switch (eventFlag) {
            case ConfigClass.EVENT_UPDATE_WALLET:
                //刷新
                loadData(false);
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * @param isRefresh 是否刷新
     * @Model 获取钱包列表
     */
    private void loadData(final boolean isRefresh) {
        Api.getInstance().getWalletList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<WalletBean>(getActivity()) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(BaseBean<WalletBean> bean) {
                        if (bean != null) {
                            WalletBean data = bean.getData();
                            if (data != null) {
                                mTotalAsset.setText("$" + new BigDecimal(data.getTotalAsset()).setScale(2, RoundingMode.DOWN).toPlainString());
                                List<WalletBean.ListBean> list = data.getList();
                                if (list == null || list.size() == 0) {
                                    mNotData.setVisibility(View.VISIBLE);
                                    mRecycler.setVisibility(View.GONE);
                                    return;
                                }

                                //移除不展示的币种
                                for (int i = 0; i < list.size(); i++) {
                                    WalletBean.ListBean listBean = list.get(i);
                                    if (!listBean.isDisplay()) {
                                        list.remove(i);
                                        i--;
                                    }
                                }

                                mNotData.setVisibility(View.GONE);
                                mRecycler.setVisibility(View.VISIBLE);
                                mList.clear();
                                mList.addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mRefresh != null && isRefresh) {
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
