package com.xxx.mining.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseActivity;
import com.xxx.mining.base.fragment.FragmentManager;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.AppVersionBean;
import com.xxx.mining.model.http.bean.IsSettingPayPswBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.sp.SharedConst;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.ExitAppUtil;
import com.xxx.mining.model.utils.PermissionUtil;
import com.xxx.mining.model.utils.SystemUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.home.HomeFragment;
import com.xxx.mining.ui.mining.MiningFragment;
import com.xxx.mining.ui.my.MyFragment;
import com.xxx.mining.ui.wallet.WalletFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Page 主页
 * @Author xxx
 */
public class MainActivity extends BaseActivity {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    //页面下标
    private static final int HOME_TYPE = R.id.main_home;     //首页
    private static final int WALLET_TYPE = R.id.main_wallet; //资产
    private static final int MINING_TYPE = R.id.main_mining;       //矿机
    private static final int MY_TYPE = R.id.main_my;         //我的

    @BindView(R.id.main_home_image)
    ImageView mHomeImage;
    @BindView(R.id.main_wallet_image)
    ImageView mWalletImage;
    @BindView(R.id.main_mining_image)
    ImageView mMiningImage;
    @BindView(R.id.main_my_image)
    ImageView mMyImage;

    @BindView(R.id.main_home_text)
    TextView mHomeText;
    @BindView(R.id.main_wallet_text)
    TextView mWalletText;
    @BindView(R.id.main_mining_text)
    TextView mMiningText;
    @BindView(R.id.main_my_text)
    TextView mMyText;

    private int nowType = HOME_TYPE;   //当前选中下标
    private int lastType = HOME_TYPE;   //上一个下标

    private ExitAppUtil exitAppUtil;    //双击退出

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        //初始化读写权限
        PermissionUtil.checkPermission(this, PermissionUtil.READ_PERMISSION, PermissionUtil.WRITE_PERMISSION);

        //初始化双击退出
        exitAppUtil = ExitAppUtil.getInstance();

        //检测是否设置过支付密码
        checkIsSettingPayPassword();

        //版本更新
        checkAppVersion();

        //加载首页数据
        selectorItem();
    }

    @OnClick({R.id.main_home, R.id.main_wallet, R.id.main_mining, R.id.main_my})
    public void onClick(View v) {
        nowType = v.getId();
        if (nowType != lastType) {
            defaultItem();
            selectorItem();
            lastType = nowType;
        }
    }

    @Override
    public void onBackPressed() {
        exitAppUtil.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(String eventFlag) {
        switch (eventFlag) {
            case ConfigClass.EVENT_LOGIN:
                //等待页面渲染完毕
                this.getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        checkAppVersion();
                        checkIsSettingPayPassword();
                    }
                });
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nowType = savedInstanceState.getInt("type", HOME_TYPE);

        //切换数据
        selectorItem();
        defaultItem();
        lastType = nowType;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("type", nowType);
    }

    //选中的
    public void selectorItem() {
        switch (nowType) {
            case HOME_TYPE:
                mHomeImage.setImageResource(R.mipmap.main_home_selection);
                mHomeText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, HomeFragment.class, R.id.main_frame);
                break;
            case WALLET_TYPE:
                mWalletImage.setImageResource(R.mipmap.main_wallet_selection);
                mWalletText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, WalletFragment.class, R.id.main_frame);
                break;
            case MINING_TYPE:
                mMiningImage.setImageResource(R.mipmap.main_mining_selection);
                mMiningText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, MiningFragment.class, R.id.main_frame);
                break;
            case MY_TYPE:
                mMyImage.setImageResource(R.mipmap.main_my_selection);
                mMyText.setTextColor(getResources().getColor(R.color.colorMainTrue));
                FragmentManager.replaceFragment(this, MyFragment.class, R.id.main_frame);
                break;
        }
    }

    //撤销上次选中的
    public void defaultItem() {
        switch (lastType) {
            case HOME_TYPE:
                mHomeImage.setImageResource(R.mipmap.main_home_default);
                mHomeText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
            case WALLET_TYPE:
                mWalletImage.setImageResource(R.mipmap.main_wallet_default);
                mWalletText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
            case MINING_TYPE:
                mMiningImage.setImageResource(R.mipmap.main_mining_default);
                mMiningText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
            case MY_TYPE:
                mMyImage.setImageResource(R.mipmap.main_my_default);
                mMyText.setTextColor(getResources().getColor(R.color.colorMainFalse));
                break;
        }
    }


    /**
     * @Model 检查App版本
     */
    private void checkAppVersion() {
        Api.getInstance().checkAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<AppVersionBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<AppVersionBean> bean) {
                        if (bean != null) {
                            AppVersionBean data = bean.getData();
                            if (data != null) {
                                String version = data.getVersion();
                                if (!SystemUtil.getVersionName(MainActivity.this).equals(version)) {
                                    UpdateWindow.getInstance(MainActivity.this, data.getDownloadUrl());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }

    /**
     * @Model 检查是否设置支付密码
     */
    private void checkIsSettingPayPassword() {
        Api.getInstance().checkIsSettingPayPassword()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<IsSettingPayPswBean>(this) {
                    @Override
                    public void onSuccess(BaseBean<IsSettingPayPswBean> bean) {
                        if (bean != null) {
                            IsSettingPayPswBean data = bean.getData();
                            if (data != null) {
                                SharedPreferencesUtil.getInstance().saveBoolean(SharedConst.IS_SETTING_PAY_PSW, data.isResult());
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        hideLoading();
                    }
                });
    }
}