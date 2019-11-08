package com.xxx.mining.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.RecordRechargeBean;

import java.util.List;

public class RechargeRecordAdapter extends BaseQuickAdapter<RecordRechargeBean, BaseViewHolder> {

    public RechargeRecordAdapter(@Nullable List<RecordRechargeBean> data) {
        super(R.layout.item_record_recharge, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordRechargeBean item) {
        helper.setText(R.id.item_recharge_record_name, "+" + item.getAmount())
                .setText(R.id.item_recharge_record_time, item.getFromAddress())
                .setText(R.id.item_recharge_record_amount, item.getRechargeDate());
    }
}
