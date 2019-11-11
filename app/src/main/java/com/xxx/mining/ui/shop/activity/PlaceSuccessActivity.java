package com.xxx.mining.ui.shop.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PlaceSuccessActivity extends BaseActivity {

    public static void actionStart(Activity activity, String orderCode, double price, double amount, String time) {
        Intent intent = new Intent(activity, PlaceSuccessActivity.class);
        intent.putExtra("orderCode", orderCode);
        intent.putExtra("price", price);
        intent.putExtra("amount", amount);
        intent.putExtra("time", time);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        orderCode = intent.getStringExtra("orderCode");
        price = intent.getDoubleExtra("price", 0);
        amount = intent.getDoubleExtra("amount", 0);
        time = intent.getStringExtra("time");
    }

    @BindView(R.id.place_success_code)
    TextView mCode;
    @BindView(R.id.place_success_price)
    TextView mPrice;
    @BindView(R.id.place_success_amount)
    TextView mAmount;
    @BindView(R.id.place_success_time)
    TextView mTime;
    private String orderCode;
    private double price;
    private double amount;
    private String time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_place_success;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initBundle();
        mCode.setText(orderCode);
        mPrice.setText((price * amount) + "元");
        mAmount.setText(amount + "");
        mTime.setText(time);
    }

    @OnClick({R.id.main_return})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_return:
                finish();
                break;
        }

    }
}
