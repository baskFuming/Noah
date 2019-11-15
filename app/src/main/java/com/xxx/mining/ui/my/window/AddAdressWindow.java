package com.xxx.mining.ui.my.window;

import android.view.View;
import android.widget.EditText;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseActivity;
import com.xxx.mining.base.dialog.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class AddAdressWindow extends BaseDialog {

    private Callback callback;
    private BaseActivity activity;

    public static AddAdressWindow getInstance(BaseActivity context) {
        return new AddAdressWindow(context);
    }



    @BindView(R.id.window_password_edit)
    public EditText mPasswordEdit;
    @BindView(R.id.window_password_send)
    public EditText mPasswordSend;
    @BindView(R.id.window_password_coin)
    public EditText mCoin;

    private String coin;
    private String address;
    private String remark;

    private AddAdressWindow(BaseActivity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_manger_pop;
    }

    @Override
    protected void initData() {
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
                coin = mCoin.getText().toString();
                address = mPasswordEdit.getText().toString();
                remark = mPasswordSend.getText().toString();
                if (callback != null) callback.callback(coin,address,remark);
                break;
            case R.id.window_password_return:
                dismiss();
                break;
        }
    }

    public interface Callback {
        void callback(String coin,String address, String remark);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
