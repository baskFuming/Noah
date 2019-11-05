package com.xxx.mining.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.RecordDepositBean;

import java.util.List;

public class DepositProfitAdapter extends BaseQuickAdapter<RecordDepositBean, BaseViewHolder> {

    public DepositProfitAdapter(@Nullable List<RecordDepositBean> data) {
        super(R.layout.item_record_gift, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordDepositBean item) {
//        helper.setText(R.id.item_deposit_profit_name, item.getUnit())
//                .setText(R.id.item_deposit_profit_time, item.getTime())
//                .setText(R.id.item_deposit_profit_amount, item.getAmount());
    }
}
