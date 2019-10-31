package com.xxx.mining.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.RechargeRecordBean;
import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class RechargeRecordAdapter extends BaseQuickAdapter<RechargeRecordBean, BaseViewHolder> {

    public RechargeRecordAdapter(@Nullable List<RechargeRecordBean> data) {
        super(R.layout.item_recharge_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeRecordBean item) {
        helper.setText(R.id.item_recharge_record_name, item.getUnit())
                .setText(R.id.item_recharge_record_time, item.getTime())
                .setText(R.id.item_recharge_record_amount, item.getAmount());
    }
}
