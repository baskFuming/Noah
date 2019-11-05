package com.xxx.mining.ui.my.activity.record;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.my.activity.InviteFriendActivity;

import butterknife.OnClick;

/**
 * @Page 收益记录页
 * @Author xxx
 */
public class RecordAllActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RecordAllActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected String initTitle() {
        return getString(R.string.record_all_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_all;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.record_all_mining, R.id.record_all_team, R.id.record_all_gift, R.id.record_all_deposit, R.id.record_all_team_detail})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.record_all_mining:
                startActivity(new Intent(this, RecordMiningActivity.class));
                break;
            case R.id.record_all_team:
                startActivity(new Intent(this, RecordTeamActivity.class));
                break;
            case R.id.record_all_gift:
                startActivity(new Intent(this, RecordGiftActivity.class));
                break;
            case R.id.record_all_deposit:
                startActivity(new Intent(this, RecordDepositActivity.class));
                break;
            case R.id.record_all_team_detail:
                ToastUtil.showToast("暂未开放");
                break;
        }
    }
}
