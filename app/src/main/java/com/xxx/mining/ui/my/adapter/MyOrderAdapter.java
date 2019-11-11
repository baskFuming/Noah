package com.xxx.mining.ui.my.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.MyOrderBean;
import com.xxx.mining.model.utils.GlideUtil;

import java.util.List;

public class MyOrderAdapter extends BaseQuickAdapter<MyOrderBean, BaseViewHolder> {

    public MyOrderAdapter(@Nullable List<MyOrderBean> data) {
        super(R.layout.item_my_order, data);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    protected void convert(BaseViewHolder helper, MyOrderBean item) {
        helper.setText(R.id.item_my_order_name, item.getMillName())
                .setText(R.id.item_my_order_time, mContext.getString(R.string.item_order_time) + item.getDate())
                .setText(R.id.item_my_order_code, mContext.getString(R.string.item_order_code) + item.getOrderNum());
        if (item.getType() == 0) {
            helper.setText(R.id.item_my_order_details, mContext.getString(R.string.item_order_amount) + item.getMillNum() + mContext.getString(R.string.item_order_goods) + mContext.getString(R.string.item_order_combined) + item.getMillPrice() + "DWTT");
        }
        if (item.getType() == 1) {
            helper.setText(R.id.item_my_order_details, mContext.getString(R.string.item_order_amount) + item.getMillNum() + mContext.getString(R.string.item_order_goods) + mContext.getString(R.string.item_order_combined) + item.getMillUSDTPrice() + "USDT" +
                    " " + item.getMillDWTTPrice() + "DWTT");
        }
        GlideUtil.load(mContext, String.valueOf(item.getImgUrl()), (ImageView) helper.getView(R.id.imageView1));
    }
}