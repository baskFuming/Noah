package com.xxx.mining.ui.shop.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.ShopMiningBean;

import java.util.List;

public class ShopMiningAdapter extends BaseQuickAdapter<ShopMiningBean, BaseViewHolder> {

    public ShopMiningAdapter(@Nullable List<ShopMiningBean> data) {
        super(R.layout.item_shop_mining, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopMiningBean item) {
//        helper.setText(R.id.item_shop_mining_name, item.getCoinQuotesName())
//                .setText(R.id.item_shop_mining_content, item.getCoinChineseName())
//                .setText(R.id.item_shop_mining_price, item.getCoinFluctuation())
        helper .addOnClickListener(R.id.item_shop_mining_btn);
    }
}
