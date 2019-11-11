package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.MyMiningBean;

import java.util.List;

public class MyMiningAdapter extends BaseQuickAdapter<MyMiningBean, BaseViewHolder> {

    public MyMiningAdapter(@Nullable List<MyMiningBean> data) {
        super(R.layout.item_my_mining, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyMiningBean item) {
        helper.setText(R.id.item_my_mining_code, item.getMillNum())
                .setText(R.id.my_mining_amount, String.valueOf(item.getIncome()))
                .addOnClickListener(R.id.item_record_gift_back);
    }
}
