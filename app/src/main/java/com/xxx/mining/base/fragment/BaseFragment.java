package com.xxx.mining.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.mining.base.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showLoading() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((BaseActivity) activity).showLoading();
        }
    }

    public void hideLoading() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((BaseActivity) activity).hideLoading();
        }
    }

    //获取到Layout的ID
    protected abstract int getLayoutId();

    //初始化数据
    protected abstract void initData();

}
