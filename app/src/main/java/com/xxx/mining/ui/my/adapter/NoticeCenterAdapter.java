package com.xxx.mining.ui.my.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.NoticeCenterBean;

import java.util.List;

public class NoticeCenterAdapter extends BaseQuickAdapter<NoticeCenterBean, BaseViewHolder> {

    public NoticeCenterAdapter(@Nullable List<NoticeCenterBean> data) {
        super(R.layout.item_notice_center, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NoticeCenterBean item) {
        helper.setText(R.id.item_notice_center_title, item.getName())
                .setText(R.id.item_notice_center_time, item.getCreateTime())
                .setText(R.id.item_notice_center_content, item.getContent());

        final CheckBox checkBox = helper.getView(R.id.item_notice_center_btn);
        final TextView checkBoxText = helper.getView(R.id.item_notice_center_btn_text);
        final TextView textView = helper.getView(R.id.item_notice_center_content);

        if (item.isCheck()) {
            checkBox.setText(mContext.getString(R.string.close_more));
            checkBoxText.setText("∧");
        } else {
            checkBox.setText(mContext.getString(R.string.open_more));
            checkBoxText.setText("∨");
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = checkBox.isChecked();
                if (checked) {
                    checkBox.setText(mContext.getString(R.string.close_more));
                    checkBoxText.setText("∧");
                    textView.setMaxLines(10000);
                    textView.invalidate();
                } else {
                    checkBox.setText(mContext.getString(R.string.open_more));
                    checkBoxText.setText("∨");
                    textView.setMaxLines(4);
                    textView.invalidate();
                }
                item.setCheck(checked);
            }
        });
    }
}
