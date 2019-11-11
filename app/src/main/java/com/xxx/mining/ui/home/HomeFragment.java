package com.xxx.mining.ui.home;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.fragment.BaseFragment;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.HomeBean;
import com.xxx.mining.model.http.bean.NoticeCenterBean;
import com.xxx.mining.model.http.bean.UserInfo;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.bean.base.PageBean;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.home.activity.CreditCenterActivity;
import com.xxx.mining.ui.home.activity.MoreOtherActivity;
import com.xxx.mining.ui.home.adapter.HomeAdapter;
import com.xxx.mining.ui.my.activity.MyNodeActivity;
import com.xxx.mining.ui.my.activity.NoticeCenterActivity;
import com.xxx.mining.ui.shop.ShopActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 首页布局
 * @Author xxx
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.main_home_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.main_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.main_app_bar)
    AppBarLayout mAppBar;

    @BindView(R.id.main_home_view_flipper)
    ViewFlipper mViewFlipper;

    private HomeAdapter mAdapter;
    private List<HomeBean> mRecyclerList = new ArrayList<>();
    private List<NoticeCenterBean> mNoticeList = new ArrayList<>();
    private boolean flag;
    private boolean isLoadFlipper;   //是否加载文字公告

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        mAdapter = new HomeAdapter(mRecyclerList);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
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
        loadNotice();
        loadInfo();
    }

    @OnClick({R.id.main_home_shop, R.id.main_home_loan, R.id.main_home_news, R.id.main_home_node, R.id.main_home_other})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_home_shop:
                ShopActivity.actionStart(getActivity());
                break;
            case R.id.main_home_loan://信贷
                CreditCenterActivity.actionStart(getActivity());
                break;
            case R.id.main_home_news://资讯
                NoticeCenterActivity.actionStart(getActivity());
                break;
            case R.id.main_home_node:
                MyNodeActivity.actionStart(getActivity(), flag);
                break;
            case R.id.main_home_other://更多
                MoreOtherActivity.actionStart(getActivity());
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadData();
        if (!isLoadFlipper) {
            loadNotice();
        }
    }

    /**
     * @Model 获取首页列表
     */
    private void loadData() {
        Api.getInstance().getHomeList(1, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<HomeBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<HomeBean>> bean) {
                        if (bean != null) {
                            List<HomeBean> list = bean.getData().getList();
                            if (list == null || list.size() == 0) {
                                mNotData.setVisibility(View.VISIBLE);
                                mRecycler.setVisibility(View.GONE);
                                return;
                            }
                            mNotData.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.VISIBLE);
                            mRecyclerList.clear();
                            mRecyclerList.addAll(list);
                            mAdapter.notifyDataSetChanged();
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
     * @Model 获取消息中心数据
     */
    private void loadNotice() {
        String userId = SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_USER_ID);
        Api.getInstance().getNoticeCenterList(1, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<NoticeCenterBean>>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<NoticeCenterBean>> bean) {
                        if (bean != null) {
                            PageBean<NoticeCenterBean> data = bean.getData();
                            if (data != null) {
                                isLoadFlipper = true;

                                List<NoticeCenterBean> list = data.getList();
                                for (int i = 0; i < list.size(); i++) {
                                    NoticeCenterBean noticeCenterBean = list.get(i);
                                    if (noticeCenterBean != null) {
                                        addView(noticeCenterBean);
                                    }
                                }
                                if (list.size() == 1) {
                                    addView(list.get(0));
                                }
                                addOnClick();
                            } else {
                                isLoadFlipper = false;
                            }
                        } else {
                            isLoadFlipper = false;
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        isLoadFlipper = false;
                    }

                    private void addView(NoticeCenterBean noticeCenterBean) {
                        View inflate = View.inflate(getContext(), R.layout.weight_view_flipper, null);
                        TextView mContext = inflate.findViewById(R.id.main_home_notice_content);
                        TextView mTime = inflate.findViewById(R.id.main_home_notice_time);
                        mContext.setText(noticeCenterBean.getName());
                        mTime.setText(noticeCenterBean.getCreateTime());
                        mViewFlipper.addView(inflate);
                        mNoticeList.add(noticeCenterBean);
                    }

                    private void addOnClick() {
                        mViewFlipper.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int child = mViewFlipper.getDisplayedChild();   //点击的条目下标
                                if (mNoticeList.size() != 0) {
                                    NoticeCenterBean bean = mNoticeList.get(child);
                                }
                                //目前版本统一跳转到列表
                                NoticeCenterActivity.actionStart(getActivity());
                            }
                        });
                    }
                });
    }

    /**
     * @Model 获取用户信息
     */
    private void loadInfo() {
        Api.getInstance().getUserinfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<UserInfo>(getActivity()) {

                    @Override
                    public void onSuccess(BaseBean<UserInfo> bean) {
                        if (bean != null) {
                            UserInfo data = bean.getData();
                            if (data != null) {
                                flag = bean.getData().isNode();
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }
                });
    }
}
