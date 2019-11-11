package com.xxx.mining.ui.wallet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.utils.ApiCode;
import com.xxx.mining.model.http.utils.ApiType;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.my.activity.psw.SettingPayPswActivity;
import com.xxx.mining.ui.wallet.window.PasswordWindow;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 转出页
 * @Author xxx
 */
public class DepositOutActivity extends BaseTitleActivity implements PasswordWindow.Callback {

    public static void actionStart(Activity activity, double amount, int coinId) {
        Intent intent = new Intent(activity, DepositOutActivity.class);
        intent.putExtra("coinId", coinId);
        intent.putExtra("amount", amount);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        coinId = intent.getIntExtra("coinId", 0);
        balance = intent.getDoubleExtra("amount", 0);
    }

    @BindView(R.id.deposit_out_edit)
    EditText mEdit;

    private double value;
    private double balance;
    private int coinId;
    private PasswordWindow mPasswordWindow;

    @Override
    protected String initTitle() {
        return getString(R.string.deposit_out_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit_out;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initBundle();

        mEdit.setHint(getString(R.string.deposit_out_edit) + " " + balance);

        //限定
        KeyBoardUtil.setFilters(mEdit, ConfigClass.DOUBLE_AMOUNT_NUMBER);
    }

    @OnClick({R.id.deposit_out_record, R.id.deposit_out_all, R.id.deposit_out_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.deposit_out_record:
                Intent intent = new Intent(this, DepositRecordActivity.class);
                intent.putExtra("type", ApiType.DEPOSIT_RECORD_OUT_TYPE);
                intent.putExtra("coinId", coinId);
                startActivity(intent);
                break;
            case R.id.deposit_out_all:
                String value = String.valueOf(balance);
                mEdit.setText(value);
                mEdit.setSelection(value.length());
                break;
            case R.id.deposit_out_btn:
                showDepositOutDialog();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        KeyBoardUtil.closeKeyBord(this, mEdit);
        Intent intent = new Intent(this, DepositActivity.class);
        intent.putExtra("outBalance", new BigDecimal(balance).setScale(4, RoundingMode.HALF_UP).stripTrailingZeros().doubleValue());
        setResult(ConfigClass.DEPOSIT_OUT_RESULT_CODE, intent);
        super.finish();
    }

    /**
     * 理财转出验证
     */
    private void showDepositOutDialog() {
        try {
            String s = mEdit.getText().toString();
            if (s.isEmpty()) {
                ToastUtil.showToast(R.string.deposit_out_error_1);
                return;
            }
            value = Double.parseDouble(s);
            if (value <= 0) {
                ToastUtil.showToast(R.string.deposit_out_error_1);
                return;
            }
            if (value > balance) {
                ToastUtil.showToast(R.string.deposit_out_error_3);
                return;
            }
        } catch (Exception e) {
            ToastUtil.showToast(R.string.deposit_out_error_2);
            return;
        }

        if (mPasswordWindow == null || !mPasswordWindow.isShowing()) {
            mPasswordWindow = PasswordWindow.getInstance(this);
            mPasswordWindow.setCallback(this);
            mPasswordWindow.show();
        }
    }

    @Override
    public void callback(String password, String code) {
        if (password == null || password.isEmpty()) {
            ToastUtil.showToast(R.string.window_password_error_1);
            return;
        }
        if (!password.matches(ConfigClass.MATCHES_JY_PASSWORD)) {
            ToastUtil.showToast(R.string.window_password_error_2);
            return;
        }
        if (code == null || code.isEmpty()) {
            ToastUtil.showToast(R.string.window_password_error_3);
            return;
        }
        if (!code.matches(ConfigClass.MATCHES_SMS_CODE)) {
            ToastUtil.showToast(R.string.window_password_error_4);
            return;
        }
        depositOut(password, code);
    }

    /**
     * @Model 理财转出
     */
    private void depositOut(String password, String code) {
        Api.getInstance().depositOut(value, coinId, code, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        if (bean != null) {
                            mPasswordWindow.dismiss();
                            mPasswordWindow = null;
                            ToastUtil.showToast(bean.getMessage());
                            //更新钱包
                            EventBus.getDefault().post(ConfigClass.EVENT_UPDATE_WALLET);
                            finish();
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (errorCode == ApiCode.PAY_NOT_SETTING) {
                            ToastUtil.showToast(getString(R.string.deposit_out_error_4));
                            startActivity(new Intent(DepositOutActivity.this, SettingPayPswActivity.class));
                            return;
                        }
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
