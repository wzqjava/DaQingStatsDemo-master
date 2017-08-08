package com.stats.daqing.feature.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.common.Urls;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private TintToolbar mToolBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        setListener();
        initWebView();
        getData();

    }

    private void initWebView() {
        //配置web设置的一个工具类;
        WebSettings settings = webView.getSettings();
        //支持通过js打开新的窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持加载javaScript,默认是false,
        settings.setJavaScriptEnabled(true);


        //WebViewClient主要帮助WebView处理各种通知、请求事件的
        webView.setWebViewClient(new WebViewClient() {
        });

        //WebChromeClient主要辅助WebView处理JavaScript的对话框、网站图标、网站title、加载进度等比如
        webView.setWebChromeClient(new WebChromeClient() {
        });


    }


    private void getData() {
        x.http().get(new RequestParams(Urls.ABOUT_US), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: result = " + result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject aboutUsJSONObject = jsonObject.getJSONObject("aboutUs");
                    String aboutHtml = aboutUsJSONObject.optString("content");
                    LogUtil.e("onSuccess: aboutHtml = " + aboutHtml);
                        webView.loadData(aboutHtml, "text/html;charset=utf-8",null);
//                    webView.loadUrl("http://www.baidu.com");


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webview);
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(null);
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
        }
    }


    private void setListener() {

    }


    @Override
    public void onClick(View v) {

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
