package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.RecordGiftBean;

import java.util.List;

public class RecordGiftAdapter extends BaseQuickAdapter<RecordGiftBean.DataBean, BaseViewHolder> {

    public RecordGiftAdapter(@Nullable List<RecordGiftBean.DataBean> data) {
        super(R.layout.item_record_gift, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordGiftBean.DataBean item) {
        helper.setText(R.id.item_record_gift_time, item.getTime())
                .setText(R.id.item_record_gift_amount, item.getAmount());
    }
}