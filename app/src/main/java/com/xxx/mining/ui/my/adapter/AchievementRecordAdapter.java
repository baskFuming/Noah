package com.xxx.mining.ui.my.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mining.R;
import com.xxx.mining.model.http.bean.AchievementRecordBean;
import com.xxx.mining.model.http.bean.ShareRecordBean;
import com.xxx.mining.model.utils.StringUtil;

import java.util.List;

public class AchievementRecordAdapter extends BaseQuickAdapter<AchievementRecordBean.CommunityInfoListBean, BaseViewHolder> {

    private Activity activity;

    public AchievementRecordAdapter(@Nullable List<AchievementRecordBean.CommunityInfoListBean> data,Activity activity) {
        super(R.layout.item_achievement_record, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, AchievementRecordBean.CommunityInfoListBean item) {
        helper.setText(R.id.item_achievement_record_level, item.getLevel(activity))
                .setText(R.id.item_achievement_record_amount, item.getReferCnt())
                .setText(R.id.item_achievement_record_ce, item.getDirectReferAsset());
    }
}
