package com.xxx.mining.ui.wallet.window;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseActivity;
import com.xxx.mining.base.dialog.BaseDialog;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.utils.DownTimeUtil;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.model.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PasswordWindow extends BaseDialog {

    private Callback callback;
    private BaseActivity activity;

    public static PasswordWindow getInstance(BaseActivity context) {
        return new PasswordWindow(context);
    }

    @BindView(R.id.window_password_edit)
    public EditText mPasswordEdit;
    @BindView(R.id.window_password_send)
    public EditText mPasswordSend;
    @BindView(R.id.window_send_sms_code)
    public TextView sendCode;
    public TextView smsCode;
    private String password;
    private String code;


    private PasswordWindow(BaseActivity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.window_password;
    }

    @Override
    protected void initData() {
        setCancelable(true); // 是否可以按“返回键”消失
    }

    @Override
    protected double setWidth() {
        return 0.8;
    }

    @OnClick({R.id.window_password_btn, R.id.window_password_return, R.id.window_send_sms_code})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.window_password_btn:
                password = mPasswordEdit.getText().toString();
                code = mPasswordSend.getText().toString();
                if (callback != null) callback.callback(password, code);
                break;
            case R.id.window_password_return:
                KeyBoardUtil.closeKeyBord(getContext(), mPasswordEdit, mPasswordSend);
                dismiss();
                break;
            case R.id.window_send_sms_code:
                sendSMSCode();
                break;
        }
    }

    private void sendSMSCode() {
        Api.getInstance().sendUpdateSMSCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Object>(activity) {

                    @Override
                    public void onSuccess(BaseBean<Object> bean) {
                        ToastUtil.showToast(bean.getMessage());
                        DownTimeUtil.getInstance().openDownTime(ConfigClass.SMS_CODE_DOWN_TIME, new DownTimeUtil.Callback() {
                            @Override
                            public void run(int nowTime) {
                                sendCode.setText(nowTime + "s");
                            }

                            @Override
                            public void end() {
                                sendCode.setText(R.string.register_send_sms_code);
                            }
                        });
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (activity != null)
                            activity.showLoading();
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (activity != null)
                            activity.hideLoading();
                    }
                });
    }

    public interface Callback {
        void callback(String password, String code);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
