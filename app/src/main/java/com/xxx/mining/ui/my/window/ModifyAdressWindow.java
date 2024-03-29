package com.xxx.mining.ui.my.window;

import android.view.View;
import android.widget.EditText;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseActivity;
import com.xxx.mining.base.dialog.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyAdressWindow extends BaseDialog {

    private Callback callback;
    private BaseActivity activity;

    public static ModifyAdressWindow getInstance(BaseActivity context) {
        return new ModifyAdressWindow(context);
    }


    public void getAddress(String password) {
        this.password = password;
    }

    @BindView(R.id.window_password_edit)
    public EditText mPasswordEdit;
    @BindView(R.id.window_password_send)
    public EditText mPasswordSend;
    private String password;
    private String code;

    private ModifyAdressWindow(BaseActivity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adress_manger_pop;
    }

    @Override
    protected void initData() {
        mPasswordEdit.setText(password);
        setCancelable(true); // 是否可以按“返回键”消失
    }

    @Override
    protected double setWidth() {
        return 0.8;
    }

    @OnClick({R.id.window_password_btn, R.id.window_password_return})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.window_password_btn:
                password = mPasswordEdit.getText().toString();
                code = mPasswordSend.getText().toString();
                if (callback != null) callback.callback(password, code);
                break;
            case R.id.window_password_return:
                dismiss();
                break;
        }
    }

    public interface Callback {
        void callback(String password, String code);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
