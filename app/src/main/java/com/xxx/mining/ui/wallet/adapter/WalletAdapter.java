package com.xxx.mining.ui.wallet.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.WalletBean;

import java.util.List;

public class WalletAdapter extends BaseQuickAdapter<WalletBean.ListBean, BaseViewHolder> {

    public WalletAdapter(@Nullable List<WalletBean.ListBean> data) {
        super(R.layout.item_wallet, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletBean.ListBean item) {
        helper.setText(R.id.item_wallet_available, mContext.getString(R.string.item_wallet_available) + item.getAmount())
                .setText(R.id.item_wallet_frozen, mContext.getString(R.string.item_wallet_frozen) + item.getAmountFrozen())
                .setText(R.id.item_wallet_name, item.getCoinName())
                .addOnClickListener(R.id.item_wallet_recharge)
                .addOnClickListener(R.id.item_wallet_withdrawal)
                .addOnClickListener(R.id.item_wallet_deposit);
    }
}
