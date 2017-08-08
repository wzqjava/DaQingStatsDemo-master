package com.stats.daqing.feature.fragment;

import android.content.Context;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.stats.daqing.R;
import com.stats.daqing.base.BasePager;

/**
 * 数据发布
 * Created by Administrator on 2017/5/21.
 */

public class DataReleaseFragment extends BasePager {


    private WebView wvContent;
    private ProgressBar bar;

    public DataReleaseFragment(Context context) {
        super(context);
        initData();
        initWebView();
    }

    @Override
    public View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_data_release, null);
        wvContent = (WebView)inflate.findViewById(R.id.wv_content);
        bar = (ProgressBar)inflate.findViewById(R.id.myProgressBar);
        return inflate;
    }

    @Override
    public void initData() {



    }

    private void initWebView() {
        WebSettings webSettings = wvContent.getSettings();
        //设置支持javaScript脚步语言
        webSettings.setJavaScriptEnabled(true);

        //支持双击-前提是页面要支持才显示
//        webSettings.setUseWideViewPort(true);

        //支持缩放按钮-前提是页面要支持才显示
        webSettings.setBuiltInZoomControls(true);

        //设置客户端-不跳转到默认浏览器中
        wvContent.setWebViewClient(new WebViewClient());

        //设置支持js调用java
        // wvContent.addJavascriptInterface(new AndroidAndJSInterface(),"Android");


        //加载网络资源
//        wvContent.loadUrl("http://10.0.2.2:8080/assets/JavaAndJavaScriptCall.html");
        // wvContent.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        // wvContent.loadUrl("http://data.stats.gov.cn");
        wvContent.loadUrl("https://www.baidu.com/");


        wvContent.setWebChromeClient(new WebChromeClient() {

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

}
