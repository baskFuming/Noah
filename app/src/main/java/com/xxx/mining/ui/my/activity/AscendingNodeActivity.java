package com.xxx.mining.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.NodePayBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.GlideUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.my.activity.psw.SettingPayPswActivity;
import com.xxx.mining.ui.shop.activity.PlaceSuccessActivity;
import com.xxx.mining.ui.wallet.window.PasswordWindow;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 竞升节点
 */
public class AscendingNodeActivity extends BaseTitleActivity implements PasswordWindow.Callback {

    public static void actionStart(Activity activity, int shopId, int imageUrl, double price, int dwttprice, int usdeprice) {
        Intent intent = new Intent(activity, AscendingNodeActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("price", price);
        intent.putExtra("dwttprice", dwttprice);
        intent.putExtra("usdeprice", usdeprice);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        shopId = intent.getIntExtra("shopId", 0);
        imageUrl = intent.getIntExtra("imageUrl", 0);
        price = intent.getDoubleExtra("price", 0);
        dwttprice = intent.getIntExtra("dwttprice", 0);
        usdeprice = intent.getIntExtra("usdeprice", 0);

    }

    private PasswordWindow mPasswordWindow;

    @BindView(R.id.shop_mining_place_number)
    TextView mNumber;
    @BindView(R.id.shop_mining_place_price)
    TextView mPrice;
    @BindView(R.id.shop_mining_place_total)
    TextView mTotalPrice;
    @BindView(R.id.shop_node_image)
    ImageView imageView;
    @BindView(R.id.invite_code)
    EditText edInvite;


    private int number = 1; //购买数量
    private int shopId; //商品Id
    private int imageUrl;    //图片
    private double price;
    private int dwttprice;
    private int usdeprice;
    private String invite;
    private boolean isHavePayPassword;

    @Override
    protected String initTitle() {
        return getString(R.string.node_up);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ascending_node;
    }

    @Override
    protected void initData() {
        initBundle();
        mPrice.setText(usdeprice + "USDT" + "    " + dwttprice + "DWTT");
        mNumber.setText(String.valueOf(number));
        mTotalPrice.setText((number * price) + "USDT");
        GlideUtil.loadBase(this, String.valueOf(imageUrl), imageView);
        //初始化密码输入框
        mPasswordWindow = PasswordWindow.getInstance(this);
        mPasswordWindow.setCallback(this);

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

    @OnClick({R.id.shop_mining_place_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.shop_mining_place_btn:
                invite = edInvite.getText().toString();
                isHavePayPassword = SharedPreferencesUtil.getInstance().getBoolean(SharedConst.IS_SETTING_PAY_PSW);
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

    /**
     * @Model 下单
     */
    private void place(String password, String code) {
        Api.getInstance().uplace(shopId, invite, password, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<NodePayBean>(this) {

                    @Override
                    public void onSuccess(BaseBean<NodePayBean> bean) {
                        if (bean != null) {
                            mPasswordWindow.dismiss();
                            mPasswordWindow = null;
                            String orderNumber = bean.getData().getOrderNum();//订单编号
                            double dwttPrice = bean.getData().getDwttPrice();//实付金额
                            double usdtPrice = bean.getData().getUsdtPrice();//实付金额
                            double number = bean.getData().getNum();//数量
                            String creatTime = bean.getData().getCreateTime();//下单时间
                            SharedPreferencesUtil.getInstance().saveBoolean(SharedConst.IS_SETTING_NODE, true);
                            PlaceSuccessActivity.actionStart(AscendingNodeActivity.this, orderNumber, dwttPrice, usdtPrice, number, creatTime);
                            ToastUtil.showToast(bean.getMessage());
                            finish();
                            //发送更新节点的EventBus
                            EventBus.getDefault().post(ConfigClass.EVENT_UPDATE_NODE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPasswordWindow != null) {
            mPasswordWindow.dismiss();
            mPasswordWindow = null;
        }
    }
}
