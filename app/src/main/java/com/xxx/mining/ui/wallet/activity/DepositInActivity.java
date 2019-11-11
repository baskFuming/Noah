package com.xxx.mining.ui.wallet.activity;

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
import com.xxx.mining.model.http.utils.ApiType;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.model.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 转入页
 * @Author xxx
 */
public class DepositInActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, double amount, int coinId) {
        Intent intent = new Intent(activity, DepositInActivity.class);
        intent.putExtra("coinId", coinId);
        intent.putExtra("amount", amount);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        coinId = intent.getIntExtra("coinId", 0);
        amount = intent.getDoubleExtra("amount", 0);
    }

    @BindView(R.id.deposit_in_edit)
    EditText mEdit;

    private double amount;
    private int coinId;

    @Override
    protected String initTitle() {
        return getString(R.string.deposit_in_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit_in;
    }

    @Override
    protected void initData() {
        initBundle();

        mEdit.setHint(getString(R.string.deposit_in_edit) + " " + amount);

        //限定
        KeyBoardUtil.setFilters(mEdit, ConfigClass.DOUBLE_AMOUNT_NUMBER);
    }

    @OnClick({R.id.deposit_in_record, R.id.deposit_in_all, R.id.deposit_in_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.deposit_in_record:
                Intent intent = new Intent(this, DepositRecordActivity.class);
                intent.putExtra("type", ApiType.DEPOSIT_RECORD_IN_TYPE);
                intent.putExtra("coinId", coinId);
                startActivity(intent);
                break;
            case R.id.deposit_in_all:
                String value = String.valueOf(amount);
                mEdit.setText(value);
                mEdit.setSelection(value.length());
                break;
            case R.id.deposit_in_btn:
                depositIn();
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
        intent.putExtra("inBalance", new BigDecimal(amount).setScale(4, RoundingMode.HALF_UP).stripTrailingZeros().doubleValue());
        setResult(ConfigClass.DEPOSIT_IN_RESULT_CODE, intent);
        super.finish();
    }

    /**
     * @Model 理财转入
     */
    private void depositIn() {
        final double value;
        try {
            String s = mEdit.getText().toString();
            if (s.isEmpty()) {
                ToastUtil.showToast(R.string.deposit_in_error_1);
                return;
            }
            value = Double.parseDouble(s);
            if (value <= 0) {
                ToastUtil.showToast(R.string.deposit_in_error_1);
                return;
            }
            if (value > amount) {
                ToastUtil.showToast(R.string.deposit_out_error_3);
                return;
            }
        } catch (Exception e) {
            ToastUtil.showToast(R.string.deposit_in_error_2);
            return;
        }
        Api.getInstance().depositIn(value, coinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        if (bean != null) {
                            ToastUtil.showToast(bean.getMessage());
                            //更新钱包
                            EventBus.getDefault().post(ConfigClass.EVENT_UPDATE_WALLET);

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
