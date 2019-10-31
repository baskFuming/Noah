package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.DepositProfitBean;
import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class DepositProfitAdapter extends BaseQuickAdapter<DepositProfitBean, BaseViewHolder> {

    public DepositProfitAdapter(@Nullable List<DepositProfitBean> data) {
        super(R.layout.item_deposit_profit, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepositProfitBean item) {
        helper.setText(R.id.item_deposit_profit_name, item.getUnit())
                .setText(R.id.item_deposit_profit_time, item.getTime())
                .setText(R.id.item_deposit_profit_amount, item.getAmount());
    }
}
