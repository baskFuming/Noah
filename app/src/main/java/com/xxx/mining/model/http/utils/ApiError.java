package com.xxx.mining.model.http.utils;

import android.app.Activity;
import android.content.Intent;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.base.activity.ActivityManager;
import com.xxx.mining.model.sp.SharedPreferencesUtil;
import com.xxx.mining.model.utils.ExitAppUtil;
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
                startActivity();
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
}
