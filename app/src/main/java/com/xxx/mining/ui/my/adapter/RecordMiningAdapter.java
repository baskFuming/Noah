package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.RecordMiningBean;

import java.util.List;

public class RecordMiningAdapter extends BaseQuickAdapter<RecordMiningBean, BaseViewHolder> {

    public RecordMiningAdapter(@Nullable List<RecordMiningBean> data) {
        super(R.layout.item_record_mining, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordMiningBean item) {
        helper.setText(R.id.item_record_mining_name, item.getUnit())
                .setText(R.id.item_record_mining_time, item.getTime())
                .setText(R.id.item_record_mining_amount, item.getAmount());
    }
}
