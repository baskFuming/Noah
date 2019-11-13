package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.RecordMiningBean;

import java.util.ArrayList;
import java.util.List;

public class RecordMiningAdapter extends BaseQuickAdapter<RecordMiningBean.ListBean, BaseViewHolder> {


    public RecordMiningAdapter(@Nullable List<RecordMiningBean.ListBean> data) {
        super(R.layout.item_record_mining, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordMiningBean.ListBean item) {
        helper.setText(R.id.item_record_mining_name, item.getMillNum())
                .setText(R.id.item_record_mining_time, item.getDate())
                .setText(R.id.item_record_mining_amount, "+" + item.getValue()+"MOS");
    }
}
