package com.xxx.mining.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.BannerBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.BannerUtil;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.shop.activity.PlaceSuccessActivity;
import com.xxx.mining.ui.shop.activity.ShopMiningPlaceActivity;
import com.xxx.mining.ui.wallet.window.PasswordWindow;
import com.youth.banner.Banner;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 竞升节点
 */
public class AscendingNodeActivity extends BaseTitleActivity implements PasswordWindow.Callback {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AscendingNodeActivity.class);
        activity.startActivity(intent);
    }
//    public static void actionStart(Activity activity, List<BannerBean> list, int shopId, double price) {
//        Intent intent = new Intent(activity, AscendingNodeActivity.class);
//        intent.putExtra("banner", (Serializable) list);
//        intent.putExtra("shopId", shopId);
//        intent.putExtra("price", price);
//        activity.startActivity(intent);
//    }
    public void initBundle() {
        Intent intent = getIntent();
        list = (List<BannerBean>) intent.getSerializableExtra("banner");
        shopId = intent.getIntExtra("shopId", 0);
        price = intent.getDoubleExtra("price", 0.0);
    }
    private PasswordWindow mPasswordWindow;

    @BindView(R.id.shop_mining_place_banner)
    Banner mBanner;
    @BindView(R.id.shop_mining_place_number)
    TextView mNumber;
    @BindView(R.id.shop_mining_place_price)
    TextView mPrice;
    @BindView(R.id.shop_mining_place_total)
    TextView mTotalPrice;


    private int number = 1; //购买数量
    private int shopId; //商品Id
    private double price;   //商品价格
    private List<BannerBean> list;  //轮播图
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

//        mPrice.setText(String.valueOf(price) + "DWTT");
//        mNumber.setText(String.valueOf(number));
//        mTotalPrice.setText(String.valueOf(number * price) + "DWTT");
//        BannerUtil.init(mBanner, list, null);

        //初始化密码输入框
        mPasswordWindow = PasswordWindow.getInstance(this);
        mPasswordWindow.setCallback(this);

    }

    @Override
    public void callback(String password, String code) {
        place(password, code);
    }

    @OnClick({R.id.shop_mining_place_delete, R.id.shop_mining_place_add, R.id.shop_mining_place_btn,R.id.invite_code})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.shop_mining_place_delete:
                if (number > 1) {
                    number--;
                }
                mNumber.setText(String.valueOf(number));
                mTotalPrice.setText(String.valueOf(number * price) + "DWTT");
                break;
            case R.id.shop_mining_place_add:
                number++;
                mNumber.setText(String.valueOf(number));
                mTotalPrice.setText(String.valueOf(number * price) + "DWTT");
                break;
            case R.id.shop_mining_place_btn:
                if (mPasswordWindow != null) {
                    mPasswordWindow.show();
                }
                break;
            case R.id.invite_code:
                KeyBoardUtil.copy(this,"");
                break;
        }
    }
    /**
     * @Model 下单
     */
    private void place(String password, String code) {
        String userId = SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_USER_ID);
        Api.getInstance().place(userId, password, code, shopId, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(this) {

                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        if (bean != null) {
                            String orderCode = "1";
                            double price = 1;
                            double amount = 1;
                            String time = "1";
                            PlaceSuccessActivity.actionStart(AscendingNodeActivity.this, orderCode, price, amount, time);
                            ToastUtil.showToast(bean.getMessage());
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
