package com.stats.daqing.feature.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;

public class BannerDataDetailsActivity extends BaseActivity {


    private TintToolbar mToolBar;
    private ProgressBar bar;
    private WebView webview;
    private String bannerUrls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_details);

        revMsg();
        initView();
        initWebView();
    }

    private void revMsg() {
        Intent intent = getIntent();
        bannerUrls = intent.getStringExtra("bannerUrls");
    }


    private void initView() {
        bar = (ProgressBar) findViewById(R.id.myProgressBar);
        webview = (WebView) findViewById(R.id.webview);

        // setTitle("");
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(null);
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
        }
    }


    private void initWebView() {
        WebSettings webSettings = webview.getSettings();
        //设置支持javaScript脚步语言
        webSettings.setJavaScriptEnabled(true);

        //支持双击-前提是页面要支持才显示
//        webSettings.setUseWideViewPort(true);

        //支持缩放按钮-前提是页面要支持才显示
        webSettings.setBuiltInZoomControls(true);

        //设置客户端-不跳转到默认浏览器中
        webview.setWebViewClient(new WebViewClient());

        //设置支持js调用java
        // wvContent.addJavascriptInterface(new AndroidAndJSInterface(),"Android");


        //加载网络资源
//        wvContent.loadUrl("http://10.0.2.2:8080/assets/JavaAndJavaScriptCall.html");
        // wvContent.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        webview.loadUrl(bannerUrls);


        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
