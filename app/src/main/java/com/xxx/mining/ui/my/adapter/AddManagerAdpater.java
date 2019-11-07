package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.base.BaseBean;

import java.util.List;

public class AddManagerAdpater extends BaseQuickAdapter<BaseBean, BaseViewHolder> {

    public AddManagerAdpater(@Nullable List<BaseBean> data) {
        super(R.layout.address_manager, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseBean item) {

    }
}
