package com.xxx.mining.ui.my.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.AddressBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.bean.base.PageBean;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.my.adapter.AddManagerAdpater;
import com.xxx.mining.ui.my.window.ModifyAdressWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 提币地址记录
 */
public class AddressManagerActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener, ModifyAdressWindow.Callback {

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    private int page = ConfigClass.PAGE_DEFAULT;
    private AddManagerAdpater addManagerAdpater;
    private List<AddressBean> mRecyclerList = new ArrayList<>();
    private ModifyAdressWindow modifyAdressWindow;
    private int id;
    private String address;

    @Override
    protected String initTitle() {
        return getString(R.string.mention_adress);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    @Override
    protected void initData() {
        addManagerAdpater = new AddManagerAdpater(mRecyclerList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(addManagerAdpater);
        mRefresh.setOnRefreshListener(this);
        addManagerAdpater.setOnItemChildClickListener(this);
        loadData();

        modifyAdressWindow = ModifyAdressWindow.getInstance(this);
        modifyAdressWindow.setCallback(this);
    }

    @Override
    public void onRefresh() {
        page = ConfigClass.PAGE_DEFAULT;
        loadData();
    }

    @Override
    public void callback(String password, String code) {
        getUpdateAddress(password, code);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        id = mRecyclerList.get(position).getId();
        address = mRecyclerList.get(position).getAddress();
        switch (view.getId()) {
            case R.id.address_modify:
                if (modifyAdressWindow != null) {
                    modifyAdressWindow.getAddress(mRecyclerList.get(position).getAddress());
                    modifyAdressWindow.show();
                }
                break;
            case R.id.address_delete:
                deleteAddress();
                break;
        }
    }

    /**
     * @Model 提币地址记录
     */
    private void loadData() {
        Api.getInstance().getUserAddress(1, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<AddressBean>>(this) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<AddressBean>> bean) {
                        if (bean != null) {
                            List<AddressBean> list = bean.getData().getList();
                            if (list == null || list.size() == 0) {
                                mNotData.setVisibility(View.VISIBLE);
                                mRecycler.setVisibility(View.GONE);
                                return;
                            }
                            mNotData.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.VISIBLE);
                            mRecyclerList.clear();
                            mRecyclerList.addAll(list);
                            addManagerAdpater.notifyDataSetChanged();
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
     * @Model 修改地址记录
     */
    private void getUpdateAddress(String address, String remarks) {
        Api.getInstance().getupdateAddress(id, address, remarks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(getString(R.string.update_address));
                        if (bean != null) {
                            loadData();
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mLoadingDialog != null) {
                            mLoadingDialog.show();
                        }
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mLoadingDialog != null || modifyAdressWindow != null) {
                            mLoadingDialog.dismiss();
                            modifyAdressWindow.dismiss();
                        }
                    }
                });
    }

    /**
     * @Model 删除地址记录
     */

    private void deleteAddress() {
        Api.getInstance().getdeleteAddress(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(getString(R.string.modify_success));
                        if (bean != null) {
                            loadData();
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mLoadingDialog != null) {
                            mLoadingDialog.show();
                        }
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mLoadingDialog != null) {
                            mLoadingDialog.dismiss();
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (modifyAdressWindow != null) {
            modifyAdressWindow.dismiss();
            modifyAdressWindow = null;
        }
    }

}
