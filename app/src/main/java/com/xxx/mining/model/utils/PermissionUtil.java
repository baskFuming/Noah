package com.xxx.mining.model.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class PermissionUtil {

    //授权请求码
    private static final int REQUEST_PERMISSION_CODE = 0;

    @SuppressLint("InlinedApi")
    public static final String READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE; //写入权限
    public static final String WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;   //读取权限
    public static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;  //相机权限

    /**
     * 检查权限
     */
    public static boolean checkPermission(Activity activity, String... params) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        if (activity != null) {
            for (String requestPermission : params) {
                if (ActivityCompat.checkSelfPermission(activity, requestPermission) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(activity, params, REQUEST_PERMISSION_CODE);
                    return true;
                }
            }
        }
        return false;
    }
}