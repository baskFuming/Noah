package com.xxx.mining.base.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xxx.mining.R;

import butterknife.ButterKnife;


/**
 * 加载中的Dialog
 */
public class LoadingDialog extends BaseDialog {

    public static LoadingDialog getInstance(Context context) {
        return new LoadingDialog(context);
    }

    private LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.window_loading;
    }

    @Override
    protected void initData() {
        setCancelable(false); // 是否可以按“返回键”消失
        setCanceledOnTouchOutside(false); // 是否可以点击外部
        final Window window = getWindow();
        if (window != null) {
            final WindowManager.LayoutParams lp = window.getAttributes();
            window.getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    int width = (int) (window.getDecorView().getMeasuredWidth() * 0.45);
                    lp.width = width;
                    lp.height = width;
                    lp.dimAmount = 0f;
                    window.setGravity(Gravity.CENTER);
                    window.setAttributes(lp);
                }
            });
        }
    }

    @Override
    protected double setWidth() {
        return 0;
    }
}