package com.xxx.mining.ui.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.mining.R;
import com.xxx.mining.base.fragment.BaseFragment;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.ui.my.activity.AccountSettingActivity;
import com.xxx.mining.ui.my.activity.CallMeActivity;
import com.xxx.mining.ui.my.activity.InviteFriendActivity;
import com.xxx.mining.ui.my.activity.LanguageActivity;
import com.xxx.mining.ui.my.activity.MyMiningActivity;
import com.xxx.mining.ui.my.activity.MyNodeActivity;
import com.xxx.mining.ui.my.activity.MyOrderActivity;
import com.xxx.mining.ui.my.activity.NoticeCenterActivity;
import com.xxx.mining.ui.my.activity.record.RecordAllActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Page 我的布局
 * @Author xxx
 */
public class MyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.main_my_icon)
    ImageView mIcon;
    @BindView(R.id.main_my_name)
    TextView mName;
    @BindView(R.id.main_my_invited_text)
    TextView mInvitedText;
    @BindView(R.id.main_my_refresh)
    SwipeRefreshLayout mRefresh;

    private String inviteCode;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mName.setText(SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_USER_NAME));
        inviteCode = SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_INVITE_CODE);
        mInvitedText.setText(getString(R.string.main_my_invited_text) + "：" + inviteCode);

        mRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(false);
    }

    @OnClick({R.id.main_my_invited_text,
            R.id.main_my_invited, R.id.main_my_record, R.id.main_my_mining, R.id.main_my_node, R.id.main_my_order,
            R.id.main_my_notice, R.id.main_my_call_my, R.id.main_my_setting, R.id.main_my_language})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.main_my_invited:
                InviteFriendActivity.actionStart(getActivity());
                break;
            case R.id.main_my_invited_text:
                KeyBoardUtil.copy(getContext(), inviteCode);
                break;
            case R.id.main_my_record:
                RecordAllActivity.actionStart(getActivity());
                break;
            case R.id.main_my_mining:
                MyMiningActivity.actionStart(getActivity());
                break;
            case R.id.main_my_node:
                MyNodeActivity.actionStart(getActivity());
                break;
            case R.id.main_my_order:
                MyOrderActivity.actionStart(getActivity());
                break;
            case R.id.main_my_notice:
                NoticeCenterActivity.actionStart(getActivity());
                break;
            case R.id.main_my_call_my:
                CallMeActivity.actionStart(getActivity());
                break;
            case R.id.main_my_setting:
                AccountSettingActivity.actionStart(getActivity());
                break;
            case R.id.main_my_language:
                LanguageActivity.actionStart(getActivity());
                break;
        }
    }
}
