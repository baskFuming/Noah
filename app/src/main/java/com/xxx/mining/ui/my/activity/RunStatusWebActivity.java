package com.xxx.mining.ui.my.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;

import butterknife.BindView;

public class RunStatusWebActivity extends BaseTitleActivity {


    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RunStatusWebActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.shop_details_web)
    WebView mWebView;
    @BindView(R.id.web_progress)
    ProgressBar mProgress;
    private String Url = "https://torchex.global/swan/index.html";

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_run_status_web;
    }

    @SuppressLint({"SetJavaScriptEnabled", "ObsoleteSdkInt"})
    @Override
    protected void initData() {
        final WebSettings webSetting;
        webSetting = mWebView.getSettings();
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        //设置支持缩放
        webSetting.setUseWideViewPort(true);
        //设置加载进来的页面自适应手机屏幕（可缩放）
        webSetting.setLoadWithOverviewMode(true);
        //支持js
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);  //支持弹窗
        webSetting.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSetting.setMediaPlaybackRequiresUserGesture(false);
        }
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.loadUrl(Url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  //接受所有证书
            }
        });
        //接收相应事件
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    if (mProgress != null) {
                        mProgress.setVisibility(View.GONE);
                    }
                } else {
                    if (mProgress != null) {
                        mProgress.setVisibility(View.VISIBLE);
                        mProgress.setProgress(newProgress);
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        mWebView.reload();
        mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空所有Cookie
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        if (mWebView != null) {
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearCache(true);
            mWebView.stopLoading();
            mWebView.destroy();
        }
    }
}
