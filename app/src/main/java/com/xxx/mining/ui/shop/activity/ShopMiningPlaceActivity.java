package com.xxx.mining.ui.shop.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.PayOrderBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.BannerUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.my.activity.psw.SettingPayPswActivity;
import com.xxx.mining.ui.wallet.window.PasswordWindow;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShopMiningPlaceActivity extends BaseTitleActivity implements PasswordWindow.Callback {

    public static void actionStart(Activity activity, List<String> list, int shopId, double price) {
        Intent intent = new Intent(activity, ShopMiningPlaceActivity.class);
        intent.putExtra("banner", (Serializable) list);
        intent.putExtra("shopId", shopId);
        intent.putExtra("price", price);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        list = (List<String>) intent.getSerializableExtra("banner");
        shopId = intent.getIntExtra("shopId", 0);
        price = intent.getDoubleExtra("price", 0.0);
    }

    @BindView(R.id.shop_mining_place_banner)
    Banner mBanner;
    @BindView(R.id.shop_mining_place_number)
    TextView mNumber;
    @BindView(R.id.shop_mining_place_price)
    TextView mPrice;
    @BindView(R.id.shop_mining_place_total)
    TextView mTotalPrice;

    private PasswordWindow mPasswordWindow;

    private int number = 1; //购买数量
    private int shopId; //商品Id
    private double price;   //商品价格
    private List<String> list = new ArrayList<>();  //轮播图
    private boolean isHavePayPassword;


    @Override
    protected String initTitle() {
        return getString(R.string.shop_mining_place_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_mining_place;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initBundle();
        isHavePayPassword = SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_SETTING_PAY_PSW);
        mPrice.setText(new BigDecimal(String.valueOf(String.valueOf(price))).setScale(4, BigDecimal.ROUND_DOWN).toPlainString() + "DWTT");
        mNumber.setText(String.valueOf(number));
        mTotalPrice.setText(new BigDecimal(String.valueOf(String.valueOf(number * price))).setScale(4, BigDecimal.ROUND_DOWN).toPlainString() + "DWTT");
        BannerUtil.init(mBanner, list, null);
        //初始化密码输入框
        mPasswordWindow = PasswordWindow.getInstance(this);
        mPasswordWindow.setCallback(this);
    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.shop_mining_place_delete, R.id.shop_mining_place_add, R.id.shop_mining_place_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.shop_mining_place_delete:
                if (number > 1) {
                    number--;
                }
                mNumber.setText(String.valueOf(number));
                mTotalPrice.setText(new BigDecimal(String.valueOf(String.valueOf(number * price))).setScale(4, BigDecimal.ROUND_DOWN).toPlainString() + "DWTT");
                break;
            case R.id.shop_mining_place_add:
                number++;
                mNumber.setText(String.valueOf(number));
                mTotalPrice.setText(new BigDecimal(String.valueOf(String.valueOf(number * price))).setScale(4, BigDecimal.ROUND_DOWN).toPlainString() + "DWTT");
                break;
            case R.id.shop_mining_place_btn:
                if (!isHavePayPassword) {
                    SettingPayPswActivity.actionStart(this);
                } else {
                    if (mPasswordWindow != null) {
                        mPasswordWindow.show();
                    }
                }
                break;
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
        place(password, code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPasswordWindow != null) {
            mPasswordWindow.dismiss();
            mPasswordWindow = null;
        }
    }

    /**
     * @Model 下单
     */
    private void place(String password, String code) {
        Api.getInstance().place(shopId, String.valueOf(number), password, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PayOrderBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<PayOrderBean> bean) {
                        if (bean != null) {
                            mPasswordWindow.dismiss();
                            mPasswordWindow = null;
                            String orderNumber = bean.getData().getOrderNum();//订单编号
                            double dwttPrice = bean.getData().getDwttPrice();//实付金额
                            double number = bean.getData().getNum();//数量
                            String creatTime = bean.getData().getCreateTime();//下单时间
                            SharedPreferencesUtil.getInstance().saveBoolean(SharedConst.IS_SETTING_NODE, true);
                            PlaceSuccessActivity.actionStart(ShopMiningPlaceActivity.this, orderNumber, dwttPrice, 0, number, creatTime);
                            ToastUtil.showToast(bean.getMessage());
                            finish();

                            //是否设置密码
                            EventBus.getDefault().post(SharedConst.IS_SETTING_NODE);
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
