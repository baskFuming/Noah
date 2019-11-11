package com.xxx.mining.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.MillDetailBean;
import com.xxx.mining.model.http.bean.UserInfo;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 挖矿
 */
public class MiningActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, int orderId) {
        Intent intent = new Intent(activity, MiningActivity.class);
        intent.putExtra("orderId", orderId);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId", 0);
    }

    private int orderId;

    @BindView(R.id.cumulative_coin)
    TextView mAmount;
    @BindView(R.id.today_coin)
    TextView mTodayAmount;
    @BindView(R.id.serial_number)
    TextView mNumbrer;
    @BindView(R.id.start_time)
    TextView mTime;
    @BindView(R.id.te_diffcult)
    TextView mDifcult;
    @BindView(R.id.te_day)
    TextView mDay;
    @BindView(R.id.calculate_time)
    ProgressBar mCalculatrPro;
    @BindView(R.id.today_progress)
    ProgressBar mTodayPro;


    @Override
    protected String initTitle() {
        return getString(R.string.mining_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mining;
    }

    @Override
    protected void initData() {
        initBundle();
        loadMIll();
        //设置滚动条可见
        setProgressBarIndeterminateVisibility(true);
    }

    /**
     * @Model 获取用户信息
     */
    private void loadMIll() {
        Api.getInstance().getmillDetail(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<MillDetailBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<MillDetailBean> bean) {
                        if (bean != null) {
                            MillDetailBean data = bean.getData();
                            if (data != null) {
                                mAmount.setText(String.valueOf(data.getCurrencyTotal()));
                                mTodayAmount.setText(String.valueOf(data.getCurrencyToday()));
                                mNumbrer.setText(String.valueOf(data.getMillNum()));
                                mTime.setText(data.getCreateTime());
                                mDifcult.setText(String.valueOf(data.getDifficultyToday()));
                                mDay.setText(data.getMyCalculation() + getString(R.string.day));
                                mCalculatrPro.setProgress(data.getMyCalculation());
                                mTodayPro.setProgress((int) data.getDifficultyToday());
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

    @OnClick({R.id.te_run})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.te_run:
                RunStatusWebActivity.actionStart(this);
                break;
        }
    }

}
