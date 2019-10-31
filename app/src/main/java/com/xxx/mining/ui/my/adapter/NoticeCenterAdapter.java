package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.NoticeCenterBean;
import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class NoticeCenterAdapter extends BaseQuickAdapter<NoticeCenterBean.ContentBean, BaseViewHolder> {

    public NoticeCenterAdapter(@Nullable List<NoticeCenterBean.ContentBean> data) {
        super(R.layout.item_notice_center, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeCenterBean.ContentBean item) {
        helper.setText(R.id.item_notice_title, item.getTitle())
                .setText(R.id.item_notice_time, item.getCreateTime())
                .setText(R.id.item_notice_content, item.getContent());
    }
}
