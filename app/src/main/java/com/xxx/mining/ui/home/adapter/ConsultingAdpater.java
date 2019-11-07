package com.xxx.mining.ui.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.base.BaseBean;

import java.util.List;

public class ConsultingAdpater extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public ConsultingAdpater(@Nullable List<BaseBean> data) {
        super(R.layout.consulting_adpater, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {

    }
}
