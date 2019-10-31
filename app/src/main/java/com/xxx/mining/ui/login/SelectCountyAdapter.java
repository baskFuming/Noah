package com.xxx.mining.ui.login;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.CountyBean;

import java.util.List;

public class SelectCountyAdapter extends BaseQuickAdapter<CountyBean, BaseViewHolder> {

    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    SelectCountyAdapter(@Nullable List<CountyBean> data) {
        super(R.layout.item_select_county, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CountyBean item) {
        helper.setText(R.id.item_select_county_name, item.getZhName())
                .setText(R.id.item_select_county_code, item.getAreaCode());

        if (helper.getAdapterPosition() == position) {
            helper.setTextColor(R.id.item_select_county_name, Color.parseColor("#2E5EF4"))
                    .setTextColor(R.id.item_select_county_code, Color.parseColor("#2E5EF4"));
        } else {
            helper.setTextColor(R.id.item_select_county_name, Color.parseColor("#F6F6F6"))
                    .setTextColor(R.id.item_select_county_code, Color.parseColor("#F6F6F6"));
        }
    }
}
