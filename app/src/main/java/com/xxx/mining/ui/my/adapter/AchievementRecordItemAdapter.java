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

public class AchievementRecordItemAdapter extends BaseQuickAdapter<AchievementRecordBean.CommunityInfoListBean.TeamListBean, BaseViewHolder> {

    private Activity activity;

    public AchievementRecordItemAdapter(@Nullable List<AchievementRecordBean.CommunityInfoListBean.TeamListBean> data, Activity activity) {
        super(R.layout.item_achievement_record, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, AchievementRecordBean.CommunityInfoListBean.TeamListBean item) {
        helper.setText(R.id.item_achievement_record_level, item.getPhone())
                .setText(R.id.item_achievement_record_amount, item.getUserStar(activity))
                .setText(R.id.item_achievement_record_ce, item.getReferTeamAsset());
    }
}
