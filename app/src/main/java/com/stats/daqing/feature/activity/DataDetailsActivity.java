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
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.google.gson.Gson;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.ArticlesBean;
import com.stats.daqing.common.Constants;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;
import com.stats.daqing.utils.TimeUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static com.stats.daqing.R.id.webview;

/**
 * 数据解读 - 文章详情
 */
public class DataDetailsActivity extends BaseActivity {


    private TintToolbar mToolBar;
    private ProgressBar bar;
    private WebView webView;
    /**
     * 文章标题
     **/
    private TextView tvTitle;
    /**
     * 文章发布时间
     **/
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_details);
        initView();
        initWebView();
        initData();
    }


    private void initData() {
        Intent intent = getIntent();
        int article_id = intent.getIntExtra(Constants.ARTICLE_ID,0);
        LogUtil.e("article_id" + article_id + "");

        RequestParams entity = new RequestParams(Urls.URL_APP_ARTCLES_DETIAL);
        entity.addParameter("id", article_id);


        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: 文章列表result = " + result);
                Gson gson = new Gson();
                ArticlesBean articlesBean = gson.fromJson(result, ArticlesBean.class);
                List<ArticlesBean.ArticlesListBean> articlesList = articlesBean.getArticlesList();
                ArticlesBean.ArticlesListBean articlesListBean = articlesList.get(0);
                tvTitle.setText(articlesListBean.getTitle());
                tvTime.setText(TimeUtil.millisecond2DateStr(articlesListBean.getPublishTime()));
                String html = articlesListBean.getContent();
                LogUtil.e("详细文章----" + articlesListBean.getContent());
                webView.loadData(html, "text/html;charset=utf-8",null);
//                webView.loadData(articlesListBean.getContent(), "text/html", "utf-8");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastAlone.showShortToast("获取数据失败");
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
        bar = (ProgressBar) findViewById(R.id.myProgressBar);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTime = (TextView) findViewById(R.id.tv_time);

        webView = (WebView) findViewById(webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(null);
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
        }
    }


    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        //设置支持javaScript脚步语言
        webSettings.setJavaScriptEnabled(true);

        //支持双击-前提是页面要支持才显示
//        webSettings.setUseWideViewPort(true);

        //支持缩放按钮-前提是页面要支持才显示
        webSettings.setBuiltInZoomControls(true);

        //设置客户端-不跳转到默认浏览器中

        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {

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
