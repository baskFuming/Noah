package com.xxx.mining.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.WithdrawalRecordBean;
import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class WithdrawalRecordAdapter extends BaseQuickAdapter<WithdrawalRecordBean, BaseViewHolder> {

    public WithdrawalRecordAdapter(@Nullable List<WithdrawalRecordBean> data) {
        super(R.layout.item_withdrawal_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawalRecordBean item) {
        helper.setText(R.id.item_withdrawal_record_name, item.getUnit())
                .setText(R.id.item_withdrawal_record_time, item.getTime())
                .setText(R.id.item_withdrawal_record_amount, item.getAmount());
    }
}
