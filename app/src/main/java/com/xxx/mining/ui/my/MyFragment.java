package com.xxx.mining.ui.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.mining.R;
import com.xxx.mining.base.dialog.LoadingDialog;
import com.xxx.mining.base.fragment.BaseFragment;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.UserInfo;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.login.LoginActivity;
import com.xxx.mining.ui.my.activity.AccountSettingActivity;
import com.xxx.mining.ui.my.activity.CallMeActivity;
import com.xxx.mining.ui.my.activity.InviteFriendActivity;
import com.xxx.mining.ui.my.activity.LanguageActivity;
import com.xxx.mining.ui.my.activity.MyMiningActivity;
import com.xxx.mining.ui.my.activity.MyNodeActivity;
import com.xxx.mining.ui.my.activity.MyOrderActivity;
import com.xxx.mining.ui.my.activity.NoticeCenterActivity;
import com.xxx.mining.ui.my.activity.record.RecordAllActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 我的布局
 * @Author xxx
 */
public class MyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.main_my_icon)
    ImageView mIcon;
    @BindView(R.id.main_my_name)
    TextView mName;
    @BindView(R.id.main_my_invited_text)
    TextView mInvitedText;
    @BindView(R.id.main_my_refresh)
    SwipeRefreshLayout mRefresh;

    private boolean flag;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mRefresh.setOnRefreshListener(this);
        loadInfo();
    }

    @Override
    public void onRefresh() {
        loadInfo();
    }

    @OnClick({
            R.id.main_my_invited, R.id.main_my_record, R.id.main_my_mining, R.id.main_my_node, R.id.main_my_order,
            R.id.main_my_notice, R.id.main_my_call_my, R.id.main_my_setting, R.id.main_my_language,
            R.id.account_setting_out_login})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_my_invited:
                InviteFriendActivity.actionStart(getActivity());
                break;
            case R.id.main_my_record:
                RecordAllActivity.actionStart(getActivity());
                break;
            case R.id.main_my_mining:
                MyMiningActivity.actionStart(getActivity());
                break;
            case R.id.main_my_node:
                flag = SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_SETTING_NODE);
                MyNodeActivity.actionStart(getActivity(), flag);
                break;
            case R.id.main_my_order:
                MyOrderActivity.actionStart(getActivity());
                break;
            case R.id.main_my_notice:
                NoticeCenterActivity.actionStart(getActivity());
                break;
            case R.id.main_my_call_my:
                CallMeActivity.actionStart(getActivity());
                break;
            case R.id.main_my_setting:
                AccountSettingActivity.actionStart(getActivity());
                break;
            case R.id.main_my_language:
                LanguageActivity.actionStart(getActivity());
                break;
            case R.id.account_setting_out_login:
                outLogin();
                break;
        }
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
                                mName.setText(data.getUserName());
                                mInvitedText.setText(data.getValue());
                            }
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

    @Override
    public void onResume() {
        super.onResume();
        loadInfo();
    }

    /**
     * @Model 退出登录
     */
    private void outLogin() {
        Api.getInstance().outLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(getActivity()) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        if (bean != null) {
                            ToastUtil.showToast(bean.getMessage());
                            SharedPreferencesUtil.getInstance().cleanAll(); //清空所有数据
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
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
