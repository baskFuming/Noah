package com.xxx.mining.ui.wallet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.RecordWithdrawalBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.bean.base.BooleanBean;
import com.xxx.mining.model.http.bean.base.PageBean;
import com.xxx.mining.model.http.utils.ApiCode;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.model.utils.PermissionUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.main.SweepActivity;
import com.xxx.mining.ui.my.activity.psw.SettingPayPswActivity;
import com.xxx.mining.ui.wallet.adapter.WithdrawalRecordAdapter;
import com.xxx.mining.ui.wallet.window.PasswordWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 提现页
 * @Author xxx
 */
public class WithdrawalActivity extends BaseTitleActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, PasswordWindow.Callback {

    public static void actionStart(Activity activity, double balance, double fee, int coinId, String coinName, String address) {
        Intent intent = new Intent(activity, WithdrawalActivity.class);
        intent.putExtra("balance", balance);
        intent.putExtra("fee", fee);
        intent.putExtra("coinId", coinId);
        intent.putExtra("coinName", coinName);
        intent.putExtra("address", address);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        balance = intent.getDoubleExtra("balance", 0.0);
        fee = intent.getDoubleExtra("fee", 0.0);
        coinId = intent.getIntExtra("coinId", 0);
        coinName = intent.getStringExtra("coinName");
        address = intent.getStringExtra("address");
    }

    @BindView(R.id.main_not_data)
    LinearLayout mNotData;
    @BindView(R.id.withdrawal_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.withdrawal_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.withdrawal_address)
    EditText mAddress;
    @BindView(R.id.withdrawal_amount)
    EditText mAmount;
    @BindView(R.id.withdrawal_balance)
    TextView mBalance;
    @BindView(R.id.withdrawal_fee)
    TextView mFee;

    @BindView(R.id.main_home_app_bar)
    AppBarLayout mAppBar;

    private String maddress;
    private double amount;
    private int coinId;
    private double balance;
    private double fee;
    private String coinName;
    private String address;
    private String remark;
    private int page = ConfigClass.PAGE_DEFAULT;
    private WithdrawalRecordAdapter mAdapter;
    private List<RecordWithdrawalBean> mList = new ArrayList<>();
    private PasswordWindow mPasswordWindow;
    private String code = "中国";    //默认是中国 +86
    private boolean isHavePayPassword;
    private String mamount;


    @Override
    protected String initTitle() {
        return coinName + getString(R.string.withdrawal_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawal;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initBundle();
        mBalance.setText(getString(R.string.withdrawal_balance) + balance + coinName);
        mFee.setText(getString(R.string.withdrawal_fee) + fee + coinName);
        mAdapter = new WithdrawalRecordAdapter(mList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecycler);
        //限定
        KeyBoardUtil.setFilters(mAmount, ConfigClass.DOUBLE_AMOUNT_NUMBER);

        loadData();

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
    }

    @OnClick({R.id.main_return, R.id.withdrawal_btn, R.id.withdrawal_sweep})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_return:
                finish();
                break;
            case R.id.withdrawal_btn:
                isHavePayPassword = SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_SETTING_PAY_PSW);
                if (!isHavePayPassword) {
                    SettingPayPswActivity.actionStart(this);
                } else {
                    showWithdrawalDialog();
                }
                break;
            case R.id.withdrawal_sweep:
                if (!PermissionUtil.checkPermission(this, PermissionUtil.READ_PERMISSION, PermissionUtil.WRITE_PERMISSION, PermissionUtil.CAMERA_PERMISSION)) {
                    startActivityForResult(new Intent(this, SweepActivity.class), ConfigClass.REQUEST_CODE);
                }
                break;
        }
    }

    @Override
    public void finish() {
        KeyBoardUtil.closeKeyBord(this, mAddress);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            String content = data.getStringExtra(Intents.Scan.RESULT);
            mAddress.setText(content);
        }
    }

    @Override
    public void onRefresh() {
        page = ConfigClass.PAGE_DEFAULT;
        loadData();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        loadData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean result = PermissionUtil.onRequestPermissionsResult(permissions, grantResults);
        if (result) {
            startActivityForResult(new Intent(this, CaptureActivity.class), ConfigClass.REQUEST_CODE);
        } else {
            if (!PermissionUtil.checkPermission(this, PermissionUtil.READ_PERMISSION, PermissionUtil.WRITE_PERMISSION, PermissionUtil.CAMERA_PERMISSION)) {
                startActivityForResult(new Intent(this, CaptureActivity.class), ConfigClass.REQUEST_CODE);
            }
        }
    }


    /**
     * @Model 获取存币记录列表
     */
    private void loadData() {
        Api.getInstance().getWithdrawalRecordList(coinId, page, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<RecordWithdrawalBean>>(this) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<RecordWithdrawalBean>> bean) {
                        if (bean == null) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }

                        List<RecordWithdrawalBean> list = bean.getData().getList();
                        if (list == null || list.size() == 0 && page == ConfigClass.PAGE_DEFAULT) {
                            mNotData.setVisibility(View.VISIBLE);
                            mRecycler.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd(true);
                            return;
                        }
                        mRecycler.setVisibility(View.VISIBLE);
                        mNotData.setVisibility(View.GONE);
                        if (page == ConfigClass.PAGE_DEFAULT) {
                            mList.clear();
                        }
                        mList.addAll(list);
                        if (list.size() < ConfigClass.PAGE_SIZE) {
                            mAdapter.loadMoreEnd(true);
                        } else {
                            mAdapter.loadMoreComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {

                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mRefresh != null && page == ConfigClass.PAGE_DEFAULT) {
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
     * 提现验证
     */
    private void showWithdrawalDialog() {
        maddress = mAddress.getText().toString();
        mamount = mAmount.getText().toString();

        if (maddress.isEmpty()) {
            ToastUtil.showToast(R.string.withdrawal_error_1);
            return;
        }
        if (mamount.isEmpty()) {
            ToastUtil.showToast(R.string.withdrawal_error_2);
            return;
        }
        try {
            if (Double.parseDouble(mamount) + fee > balance) {
                ToastUtil.showToast(R.string.withdrawal_error_3);
                return;
            }

        } catch (Exception e) {
            ToastUtil.showToast(R.string.withdrawal_error_4);
            return;
        }

        this.amount = Double.parseDouble(mamount);

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
        withdrawal(password, code);
    }

    /**
     * @Model 提现
     */
    private void withdrawal(String password, String code) {
        Api.getInstance().withdrawal(coinId, Double.parseDouble(mamount), fee, Integer.parseInt(code), maddress, password, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<BooleanBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<BooleanBean> bean) {
                        if (bean != null) {
                            if (bean.getData().isResult()) {
                                mPasswordWindow.dismiss();
                                mPasswordWindow = null;
                                balance -= (amount);
                                mBalance.setText(String.valueOf(balance));
                                mAmount.setText("");
                                mAddress.setText("");

                                loadData();
                                ToastUtil.showToast(bean.getMessage());
                                //更新钱包
                                EventBus.getDefault().post(ConfigClass.EVENT_UPDATE_WALLET);
                            } else {
                                ToastUtil.showToast("fail");
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        if (errorCode == ApiCode.PAY_NOT_SETTING) {
                            ToastUtil.showToast(getString(R.string.window_exchange_error_4));
                            startActivity(new Intent(WithdrawalActivity.this, SettingPayPswActivity.class));
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

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
