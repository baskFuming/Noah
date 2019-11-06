package com.xxx.mining.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.ActivityManager;
import com.xxx.mining.base.activity.BaseActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.LoginBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.model.utils.LocalManageUtil;
import com.xxx.mining.model.utils.MD5Util;
import com.xxx.mining.model.utils.SystemUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 登录页面
 * @Author xxx
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_selector_phone)
    TextView mSelectorPhone;
    @BindView(R.id.login_account_edit)
    EditText mAccountEdit;
    @BindView(R.id.login_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.login_password_eye)
    CheckBox mPasswordEye;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        //access-token
        String accessToken = MD5Util.getMD5(SystemUtil.getSerialNumber() + SystemUtil.getUUID());
        SharedPreferencesUtil util = SharedPreferencesUtil.getInstance();
        util.saveEncryptString(SharedConst.ENCRYPT_VALUE_TOKEN_2, accessToken);

        //保存记录
        String phone = SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_USER_PHONE);
        mAccountEdit.setText(phone);
    }

    @OnClick({R.id.login_return, R.id.login_selector_phone, R.id.login_password_eye, R.id.login_register, R.id.login_forger_password, R.id.login_btn, R.id.switch_language})
    public void OnClick(View view) {
        KeyBoardUtil.closeKeyBord(this, mAccountEdit);
        switch (view.getId()) {
            case R.id.login_return:
                finish();
                break;
            case R.id.login_selector_phone:
                startActivityForResult(new Intent(this, SelectCountyActivity.class), ConfigClass.REQUEST_CODE);
                break;
            case R.id.login_password_eye:
                KeyBoardUtil.setInputTypePassword(mPasswordEye.isChecked(), mPasswordEdit);
                break;
            case R.id.login_forger_password:
                startActivityForResult(new Intent(this, ForgetLoginPswActivity.class), ConfigClass.REQUEST_CODE);
                break;
            case R.id.login_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), ConfigClass.REQUEST_CODE);
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.switch_language:    //切换语言
                String nowLanguage = SharedPreferencesUtil.getInstance().getString(SharedConst.CONSTANT_LAUNCHER);
                switch (nowLanguage) {
                    case LocalManageUtil.LANGUAGE_CN:
                        SharedPreferencesUtil.getInstance().saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_CN);
                        EventBus.getDefault().post(ConfigClass.EVENT_LANGUAGE_TAG);
                        break;
                    case LocalManageUtil.LANGUAGE_US:
                        SharedPreferencesUtil.getInstance().saveString(SharedConst.CONSTANT_LAUNCHER, LocalManageUtil.LANGUAGE_US);
                        EventBus.getDefault().post(ConfigClass.EVENT_LANGUAGE_TAG);
                        break;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        KeyBoardUtil.closeKeyBord(this, mAccountEdit, mPasswordEdit);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConfigClass.RESULT_CODE && data != null) {
//            String phoneName = data.getStringExtra(SelectCountyActivity.RESULT_NAME_KRY);
            String phoneCode = data.getStringExtra(SelectCountyActivity.RESULT_CODE_KRY);
            mSelectorPhone.setText(phoneCode);
        }
    }

    @Override
    public void onBackPressed() {
        ActivityManager.getInstance().AppExit();
    }


    /**
     * @Model 登录
     */
    private void login() {
        final String account = mAccountEdit.getText().toString();
        final String password = mPasswordEdit.getText().toString();

        if (account.isEmpty()) {
            ToastUtil.showToast(R.string.login_error_1);
            showEditError(mAccountEdit);
            return;
        }
        if (!account.matches(ConfigClass.MATCHES_PHONE)) {
            ToastUtil.showToast(R.string.register_error_4);
            showEditError(mAccountEdit);
            return;
        }
        if (password.isEmpty()) {
            ToastUtil.showToast(R.string.login_error_2);
            showEditError(mPasswordEdit);
            return;
        }
        if (!password.matches(ConfigClass.MATCHES_PASSWORD)) {
            ToastUtil.showToast(R.string.login_error_4);
            showEditError(mPasswordEdit);
            return;
        }

        Api.getInstance().login(account, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<LoginBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<LoginBean> bean) {
                        if (bean != null) {
                            LoginBean data = bean.getData();
                            if (data != null) {
                                ToastUtil.showToast(bean.getMessage());
                                SharedPreferencesUtil util = SharedPreferencesUtil.getInstance();
                                util.saveString(SharedConst.VALUE_USER_PHONE, account);
                                util.saveString(SharedConst.VALUE_USER_NAME, data.getUsername());
                                util.saveString(SharedConst.VALUE_INVITE_CODE, data.getPromotionCode());
                                util.saveString(SharedConst.VALUE_USER_ID, String.valueOf(data.getId()));
                                util.saveBoolean(SharedConst.IS_LOGIN, true);
                                LoginBean.CountryBean country = data.getCountry();
                                if (country != null) {
                                    util.saveString(SharedConst.VALUE_COUNTY_CITY, country.getZhName());
                                }
                                //x-token
                                util.saveEncryptString(SharedConst.ENCRYPT_VALUE_TOKEN_1, data.getToken());

                                //判断下是否进入过首页
                                Activity activity = ActivityManager.getInstance().getActivity(MainActivity.class.getSimpleName());
                                if (activity != null) {
                                    setResult(ConfigClass.LOGIN_RESULT_CODE);
                                    finish();
                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
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

}
