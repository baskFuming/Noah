package com.xxx.mining.ui.mining;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.fragment.BaseFragment;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.BannerBean;
import com.xxx.mining.model.http.bean.ShopMiningBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.bean.base.PageBean;
import com.xxx.mining.model.utils.BannerUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.shop.activity.ShopMiningPlaceActivity;
import com.xxx.mining.ui.shop.adapter.ShopMiningAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MiningFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_app_bar)
    AppBarLayout mAppBar;

    @BindView(R.id.shop_mining_banner)
    Banner mBanner;
    private boolean isSuccessBanner;   //是否加载完成轮播图

    private int page = ConfigClass.PAGE_DEFAULT;
    private ShopMiningAdapter mAdapter;
    private List<ShopMiningBean> mList = new ArrayList<>();
    private List<String> mBannerList = new ArrayList<>();  //轮播图


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mining;
    }

    @Override
    protected void initData() {
        mAdapter = new ShopMiningAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        mAdapter.setOnItemChildClickListener(this);
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
        loadBanner();
        loadList();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ShopMiningPlaceActivity.actionStart(getActivity(), mBannerList, mList.get(position).getId(), mList.get(position).getDwttPrice());
    }

    @Override
    public void onRefresh() {
        page = ConfigClass.PAGE_DEFAULT;
        loadList();
        if (!isSuccessBanner) {
            loadList();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadList();
    }


    /**
     * @Model 获取列表
     */
    private void loadList() {
        Api.getInstance().getShopMiningList("1", page, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<ShopMiningBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<ShopMiningBean>> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }

                        List<ShopMiningBean> list = bean.getData().getList();
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

    /**
     * @Model 获取轮播图
     */
    private void loadBanner() {
        Api.getInstance().getBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BannerBean>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<BannerBean> bean) {
                        if (bean != null) {
                            BannerBean data = bean.getData();
                            List<String> list = data.getBanner();
                            if (list == null || list.size() == 0) {
                                isSuccessBanner = false;
                            } else {
                                isSuccessBanner = true;
                                mBannerList.clear();
                                mBannerList.addAll(list);
                                BannerUtil.init(mBanner, mBannerList, null);
                            }
                        } else {
                            isSuccessBanner = false;
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                        isSuccessBanner = false;
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }

}
