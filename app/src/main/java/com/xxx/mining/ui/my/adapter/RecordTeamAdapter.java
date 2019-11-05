package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.RecordTeamBean;

import java.util.List;

public class RecordTeamAdapter extends BaseQuickAdapter<RecordTeamBean, BaseViewHolder> {

    public RecordTeamAdapter(@Nullable List<RecordTeamBean> data) {
        super(R.layout.item_record_team, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordTeamBean item) {
//        helper.setText(R.id.item_record_team_time, item.)
//                .setText(R.id.item_record_team_type, item.)
//                .setText(R.id.item_record_team_amount, item.);
    }
}
