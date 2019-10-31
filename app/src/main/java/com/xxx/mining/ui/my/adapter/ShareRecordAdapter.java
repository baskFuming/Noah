package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.ShareRecordBean;
import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class ShareRecordAdapter extends BaseQuickAdapter<ShareRecordBean.RestListBean, BaseViewHolder> {

    public ShareRecordAdapter(@Nullable List<ShareRecordBean.RestListBean> data) {
        super(R.layout.item_share_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareRecordBean.RestListBean item) {
        helper.setText(R.id.item_share_record_time, item.getTime())
                .setText(R.id.item_share_record_amount, item.getAmount());
    }
}
