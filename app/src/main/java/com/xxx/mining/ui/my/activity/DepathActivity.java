package com.xxx.mining.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.RecordTeamBean;
import com.xxx.mining.model.http.bean.UserInfo;
import com.xxx.mining.model.http.bean.WDepathBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.my.adapter.AddManagerAdpater;
import com.xxx.mining.ui.my.adapter.MNodeAdpater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DepathActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static void actionStartS(Activity activity) {
        Intent intent = new Intent(activity, DepathActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    private MNodeAdpater madpater;
    private List<WDepathBean> mlist = new ArrayList<>();

    @Override
    protected String initTitle() {
        return getString(R.string.my_depath);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_depath;
    }

    @Override
    protected void initData() {
        madpater = new MNodeAdpater(mlist);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(madpater);
        mRefresh.setOnRefreshListener(this);
        loadDepath();
    }

    @Override
    public void onRefresh() {
        loadDepath();
    }

    /**
     * @Model 获取用户信息
     */
    private void loadDepath() {
        Api.getInstance().getmyDepth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<List<WDepathBean>>(this) {
                    @Override
                    public void onSuccess(BaseBean<List<WDepathBean>> bean) {
                        if (bean != null) {
                            List<WDepathBean> list = bean.getData();
                            if (list == null || list.size() == 0) {
                                mNotData.setVisibility(View.VISIBLE);
                                mRecycler.setVisibility(View.GONE);
                                madpater.loadMoreEnd(true);
                                return;
                            }

                            mNotData.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.VISIBLE);
                            mlist.clear();
                            mlist.addAll(list);
                            if (list.size() < ConfigClass.PAGE_SIZE) {
                                madpater.loadMoreEnd(true);
                            } else {
                                madpater.loadMoreComplete();
                            }
                            madpater.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mLoadingDialog != null && mRefresh != null) {
                            mRefresh.setRefreshing(true);
                            mLoadingDialog.show();
                        }
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mLoadingDialog != null && mRefresh != null) {
                            mRefresh.setRefreshing(false);
                            mLoadingDialog.dismiss();
                        }
                    }
                });
    }
}
