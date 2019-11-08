package com.xxx.mining.ui.shop;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.BannerBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.utils.ApiType;
import com.xxx.mining.model.utils.BannerUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.shop.activity.ShopMiningActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ShopActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.main_shop_banner)
    Banner mBanner;
    private boolean isSuccessBanner;   //是否加载完成轮播图

    private List<String> mBannerList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.main_shop_title);
    }

    @Override
    protected void initData() {
        mRefresh.setOnRefreshListener(this);
        loadBanner();
    }

    @OnClick({R.id.main_shop_mining, R.id.main_shop_imported, R.id.main_shop_hotel, R.id.main_shop_plane, R.id.main_shop_gas, R.id.main_shop_other})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_shop_mining:
                ShopMiningActivity.actionStart(this);
                break;
            case R.id.main_shop_imported:
                ToastUtil.showToast("敬请期待");
                break;
            case R.id.main_shop_hotel:
                ToastUtil.showToast("敬请期待");
                break;
            case R.id.main_shop_plane:
                ToastUtil.showToast("敬请期待");
                break;
            case R.id.main_shop_gas:
                ToastUtil.showToast("敬请期待");
                break;
            case R.id.main_shop_other:
                ToastUtil.showToast("敬请期待");
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (!isSuccessBanner) {
            loadBanner();
        } else {
            mRefresh.setRefreshing(false);
        }
    }

    /**
     * @Model 获取轮播图
     */
    private void loadBanner() {
        Api.getInstance().getShopBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BannerBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<BannerBean> bean) {
                        if (bean != null) {
                            BannerBean date = bean.getData();
                            List<String> list  = date.getBanner();
                             if (list != null) {
                                isSuccessBanner = true;
                                mBannerList.clear();
                                mBannerList.addAll(list);
                                BannerUtil.init(mBanner, mBannerList, null);

                            } else {
                                isSuccessBanner = false;
                            }
                        } else {
                            isSuccessBanner = false;
                        }

                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    }
                });
    }

}
