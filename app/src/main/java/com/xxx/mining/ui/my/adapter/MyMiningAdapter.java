package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.MyMiningBean;
import com.xxx.mining.model.utils.GlideUtil;

import java.util.List;

public class MyMiningAdapter extends BaseQuickAdapter<MyMiningBean, BaseViewHolder> {

    public MyMiningAdapter(@Nullable List<MyMiningBean> data) {
        super(R.layout.item_my_mining, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyMiningBean item) {
        helper.setText(R.id.item_my_mining_code, String.valueOf(item.getIncome()))
                .setText(R.id.my_mining_amount, item.getMillNum());

//        GlideUtil.loadBase(mContext, String.valueOf(item.getId()),(ImageView) helper.getView(R.id.imageView1));
    }
}
