package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.WDepathBean;
import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class MNodeAdpater extends BaseQuickAdapter<WDepathBean, BaseViewHolder> {

    public MNodeAdpater(@Nullable List<WDepathBean> data) {
        super(R.layout.item_depath, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WDepathBean item) {
        helper.setText(R.id.te_phone, StringUtil.getPhone(item.getPhone()))
                .setText(R.id.te_depthAmount, String.valueOf(item.getAmount()));
    }
}
