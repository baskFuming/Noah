package com.xxx.mining.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.model.utils.ZXingUtil;
import com.xxx.mining.ui.shop.ShopActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Page 邀请好友页
 * @Author xxx
 */
public class InviteFriendActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, InviteFriendActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.invite_friend_image)
    ImageView mImage;
    @BindView(R.id.invite_friend_code)
    TextView mCode;

    private String content;
    private Bitmap bitmap;  //二维码

    @Override
    protected String initTitle() {
        return getString(R.string.invite_friend_title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    protected void initData() {
        content = ConfigClass.INVITE_URL + SharedPreferencesUtil.getInstance().getString(SharedConst.VALUE_INVITE_CODE);
        bitmap = ZXingUtil.createQRCode(content, (int) getResources().getDimension(R.dimen.zxCode_size));
        mImage.setImageBitmap(bitmap);
        mCode.setText(content);
    }

    @OnClick({R.id.invite_friend_copy})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.invite_friend_copy:
                KeyBoardUtil.copy(this, content);
                break;
        }
    }
}
