package com.xxx.mining.ui.my.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.RecordTeamBean;

import java.util.List;

public class RecordTeamAdapter extends BaseQuickAdapter<RecordTeamBean.ListBean, BaseViewHolder> {

    public RecordTeamAdapter(@Nullable List<RecordTeamBean.ListBean> data) {
        super(R.layout.item_record_team, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, RecordTeamBean.ListBean item) {
        helper.setText(R.id.item_record_team_time, item.getCreateTime())
                .setText(R.id.item_record_team_amount, "+" + item.getValue() + "USDT");
        if (item.getStatus() == 0) {
            helper.setText(R.id.item_record_team_type, mContext.getString(R.string.earnings));
        } else {
            helper.setText(R.id.item_record_team_type, mContext.getString(R.string.earnings_up));
            helper.setBackgroundColor(R.id.lin_back, mContext.getResources().getColor(R.color.color_item));
        }
    }
}
