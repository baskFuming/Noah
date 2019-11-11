package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.AddressBean;
import com.xxx.mining.model.http.bean.base.BaseBean;

import java.util.List;

public class AddManagerAdpater extends BaseQuickAdapter<AddressBean, BaseViewHolder> {


    public AddManagerAdpater(@Nullable List<AddressBean> data) {
        super(R.layout.address_manager, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        helper.setText(R.id.coin_name, item.getCoinName())
                .setText(R.id.wallet_address, item.getAddress())
                .setText(R.id.wallet_remark, item.getRemarks())
                .addOnClickListener(R.id.address_modify)
                .addOnClickListener(R.id.address_delete);
    }

}
