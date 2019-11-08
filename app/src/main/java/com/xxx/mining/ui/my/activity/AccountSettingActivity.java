package com.xxx.mining.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.AppVersionBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.SystemUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.login.LoginActivity;
import com.xxx.mining.ui.main.UpdateWindow;
import com.xxx.mining.ui.my.activity.psw.PasswordSettingActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 账户设置页
 * @Author xxx
 */
public class AccountSettingActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AccountSettingActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.account_setting_version_code)
    TextView mVersionCode;

    private String versionName;

    @Override
    protected String initTitle() {
        return getString(R.string.account_setting_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_setting;
    }

    @Override
    protected void initData() {
        versionName = SystemUtil.getVersionName(this);
        mVersionCode.setText(versionName);
    }

    @OnClick({R.id.account_setting_set_password, R.id.account_setting_address_manager, R.id.account_setting_check_version, R.id.account_setting_out_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_setting_set_password:
                startActivity(new Intent(AccountSettingActivity.this, PasswordSettingActivity.class));
                break;
            case R.id.account_setting_address_manager:
                startActivity(new Intent(AccountSettingActivity.this, AddressManagerActivity.class));
                break;
            case R.id.account_setting_check_version:
                checkAppVersion();
                break;
            case R.id.account_setting_out_login:
                outLogin();
                break;
        }
    }

    /**
     * @Model 检查App版本
     */
    private void checkAppVersion() {
        Api.getInstance().checkAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<AppVersionBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<AppVersionBean> bean) {
                        if (bean != null) {
                            AppVersionBean data = bean.getData();
                            if (data != null) {
                                String version = data.getVersion();
                                if (versionName.equals(version)) {
                                    ToastUtil.showToast(R.string.check_version_not);
                                } else {
                                    UpdateWindow.getInstance(AccountSettingActivity.this, data.getDownloadUrl());
                                }
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
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }

    /**
     * @Model 退出登录
     */
    private void outLogin() {
        Api.getInstance().outLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {
                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        if (bean != null) {
                            ToastUtil.showToast(bean.getMessage());
                            SharedPreferencesUtil.getInstance().cleanAll(); //清空所有数据
                            startActivity(new Intent(AccountSettingActivity.this, LoginActivity.class));
                            finish();
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
