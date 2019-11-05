package com.xxx.mining.ui.my.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.MyOrderBean;

import java.util.List;

public class MyOrderAdapter extends BaseQuickAdapter<MyOrderBean, BaseViewHolder> {

    public MyOrderAdapter(@Nullable List<MyOrderBean> data) {
        super(R.layout.item_my_order, data);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    protected void convert(BaseViewHolder helper, MyOrderBean item) {
//        helper.setText(R.id.item_my_order_name, item.getName())
//                .setText(R.id.item_my_order_time, mContext.getString(R.string.item_order_time) + TimeUtils.millis2String(item.getOrderDate()))
//                .setText(R.id.item_my_order_code, mContext.getString(R.string.item_order_code) + item.getOrderNum())
//                .setText(R.id.item_my_order_details, String.format(mContext.getString(R.string.item_order_detail), item.getNum(), item.getPrice(), item.getCoinName()));
    }
}