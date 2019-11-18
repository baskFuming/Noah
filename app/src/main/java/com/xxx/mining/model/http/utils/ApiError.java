package com.xxx.mining.model.http.utils;

import android.app.Activity;

import com.xxx.mining.R;
import com.xxx.mining.base.App;
import com.xxx.mining.base.activity.ActivityManager;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.ToastUtil;
import com.xxx.mining.ui.login.LoginActivity;
import com.xxx.mining.ui.main.MainActivity;

public class ApiError {

    /**
     * 服务器返回的错误码处理
     *
     * @param code 状态码
     */
    public static void ServiceCodeErrorFun(int code) {
        switch (code) {
            case ApiCode.TOKEN_INVALID:
//                startActivity();
                tokenError();
                break;
        }
    }

    /**
     * Token失效跳转页面
     */
    public static void startActivity() {
        Activity activity = ActivityManager.getInstance().getForegroundActivity();
        if (activity != null) {
            SharedPreferencesUtil.getInstance().cleanAll();
        } else {
            //退出程序
            ActivityManager.getInstance().AppExit();
        }
    }

    /**
     * Token失效跳转页面
     */
    private static void tokenError() {
        SharedPreferencesUtil.getInstance().cleanAll();
        Activity activity = ActivityManager.getInstance().getActivity(MainActivity.class.getName());
        LoginActivity.actionStart(activity);
        ToastUtil.showToast(App.getContext().getResources().getString(R.string.token_overdue_str));
    }
}
