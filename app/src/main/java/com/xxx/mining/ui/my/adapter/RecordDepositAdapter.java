package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.RecordDepositBean;

import java.util.List;

public class RecordDepositAdapter extends BaseQuickAdapter<RecordDepositBean, BaseViewHolder> {

    public RecordDepositAdapter(@Nullable List<RecordDepositBean> data) {
        super(R.layout.item_record_deposit, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordDepositBean item) {
        helper.setText(R.id.item_record_deposit_time, item.getTime())
                .setText(R.id.item_record_deposit_amount, item.getAmount());
    }
}
