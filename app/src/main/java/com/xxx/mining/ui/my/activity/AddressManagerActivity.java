package com.xxx.mining.ui.my.activity;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.ui.my.adapter.AddManagerAdpater;
import com.xxx.mining.ui.my.window.ModifyAdressWindow;

/**
 * 提币地址记录
 */
public class AddressManagerActivity extends BaseTitleActivity implements ModifyAdressWindow.Callback {

    private AddManagerAdpater addManagerAdpater;

    @Override
    protected String initTitle() {
        return getString(R.string.mention_adress);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void callback(String password, String code) {

    }
}
