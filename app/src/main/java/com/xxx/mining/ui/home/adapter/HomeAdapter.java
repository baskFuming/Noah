package com.xxx.mining.ui.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.HomeBean;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder> {

    public HomeAdapter(@Nullable List<HomeBean> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean item) {
        helper.setText(R.id.item_home_name, item.getCoinSymbol())
                .setText(R.id.item_home_price, item.getCoinPriceUSDT())
                .setText(R.id.item_home_range, item.getCoinFluctuation());

        if ("+".equals(String.valueOf(item.getCoinFluctuation().charAt(0)))) {
            helper.setTextColor(R.id.item_home_range, mContext.getResources().getColor(R.color.colorItemRise));
        } else {
            helper.setTextColor(R.id.item_home_range, mContext.getResources().getColor(R.color.colorItemFall));
        }
    }
}
