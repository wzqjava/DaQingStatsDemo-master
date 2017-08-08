package com.stats.daqing.feature.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.google.gson.Gson;
import com.litesuits.common.io.FileUtils;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.ArticlesTitlesBean;
import com.stats.daqing.bean.NewArticlesBean;
import com.stats.daqing.common.Constants;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;
import com.stats.daqing.utils.StorageUtil;
import com.stats.daqing.utils.TimeUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 数据发布 - 文章详情
 * 带webview的文章详情;
 */
public class ArticlesActivity extends BaseActivity implements View.OnClickListener {

    private TintToolbar mToolBar;
    private ProgressBar bar;
    private WebView webView;
    // private ArrayList<ArticlesBean.ArticlesListBean> articlesList;
    private List<ArticlesTitlesBean.ArticlesListBean> mArticlesList;
    private TextView tvPrevious;
    private TextView tvNext;
    private int currentPosition;
    private LinearLayout llPrevisou;
    private TextView tvPreviousContent;
    private LinearLayout llNext;
    private TextView tvNextContent;
    /** 文章标题 **/
    private TextView tvTitle;
    /** 文章发布时间 **/
    private TextView tvTime;
    /** 选中文章 **/
    private ArticlesTitlesBean.ArticlesListBean currentArticles;
    private SwipeRefreshLayout swipeRefres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        revMsg();
        initVariable();
        initView();
        setListener();
        initData();
    }

    private void setListener() {
        llPrevisou.setOnClickListener(this);
        llNext.setOnClickListener(this);
        swipeRefres.setEnabled(false);
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
       /* swipeRefres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                swipeRefres.setRefreshing(true);
                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                getArticleData(currentArticles);

            }
        });*/
    }


    private void revMsg() {
        Intent intent = getIntent();
        currentArticles = intent.getParcelableExtra("articles");
        mArticlesList = intent.getParcelableArrayListExtra("articlesList");
        currentPosition = intent.getIntExtra("currentPosition", 0);
    }

    private void initVariable() {

    }

    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        bar = (ProgressBar) findViewById(R.id.myProgressBar);


        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTime = (TextView)findViewById(R.id.tv_time);

        llPrevisou = (LinearLayout)findViewById(R.id.ll_previsou);
        tvPreviousContent = (TextView)findViewById(R.id.tv_previous_content);

        llPrevisou = (LinearLayout)findViewById(R.id.ll_previsou);
        tvPrevious = (TextView)findViewById(R.id.tv_previous);
        tvPreviousContent = (TextView)findViewById(R.id.tv_previous_content);

        llNext = (LinearLayout)findViewById(R.id.ll_next);
        tvNext = (TextView)findViewById(R.id.tv_next);
        tvNextContent = (TextView)findViewById(R.id.tv_next_content);

        swipeRefres = (SwipeRefreshLayout)findViewById(R.id.swipeRefres);
        swipeRefres.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefres.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        webView = (WebView) findViewById(R.id.webview);
        initWebView();


        /*WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });*/

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

        //设置支持js调用java
        // wvContent.addJavascriptInterface(new AndroidAndJSInterface(),"Android");


        //加载网络资源
//        wvContent.loadUrl("http://10.0.2.2:8080/assets/JavaAndJavaScriptCall.html");
        // webView.loadUrl("file:///android_asset/table.htm");
        // webView.loadUrl("file:///mnt/sdcard/table.htm");


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

    }

    private void initData() {
        if (mArticlesList == null) {
            ToastAlone.showShortToast("无内容");
            return;
        }

        ArticlesTitlesBean.ArticlesListBean bean = mArticlesList.get(currentPosition);
        tvTitle.setText(bean.getTitle());
        tvTime.setText(TimeUtil.millisecond2DateStr(bean.getCreateTime()));
        getArticleData(bean);


        /*File file = StorageUtil.getAppCustomCacheDirectory(Constants.APP_CACHE_DIR_CRASH + File.separator + "table.html");
        try {
            FileUtils.writeStringToFile(file,bean.getContent());
            webView.loadUrl("file:///mnt/sdcard/DaQingStats/files/table.html");
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        // 设置上一篇下一篇按钮状态
        if (mArticlesList.size() == 1) {
            // 只有一篇文章
            llPrevisou.setVisibility(View.GONE);
            tvPreviousContent.setText("");

            llNext.setVisibility(View.GONE);
            tvNextContent.setText("");
        }else{
            // 多篇文章

            if(currentPosition > 0){
                // 显示上一篇按钮
                bean = mArticlesList.get(currentPosition - 1);
                llPrevisou.setVisibility(View.VISIBLE);
                tvPreviousContent.setText(bean.getTitle());
            }else{
                llPrevisou.setVisibility(View.GONE);
                tvPreviousContent.setText("");
            }

            if(currentPosition + 1 < mArticlesList.size()){
                // 显示下一篇按钮
                bean = mArticlesList.get(currentPosition + 1);
                llNext.setVisibility(View.VISIBLE);
                tvNextContent.setText(bean.getTitle());

            }else{
                llNext.setVisibility(View.GONE);
                tvNextContent.setText("");
            }
        }

        /*String data = AssetsUtil.readFile("html3.txt");
        webView.loadDataWithBaseURL(null,data,"text/html", "utf-8", null);*/
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

    @Override
    public void onClick(View v) {
        // ArticlesTitlesBean.ArticlesListBean bean;
        switch (v.getId()) {
            case R.id.ll_previsou:
                // 上一篇
                if (currentPosition > 0) {
                    currentPosition--;
                    currentArticles = mArticlesList.get(currentPosition);
                    tvTitle.setText(currentArticles.getTitle());
                    tvTime.setText(TimeUtil.millisecond2DateStr(currentArticles.getCreateTime()));
                    // webView.loadDataWithBaseURL(null,bean.getContent(),"text/html", "utf-8", null);
                    getArticleData(currentArticles);


                    /*File file = StorageUtil.getAppCustomCacheDirectory(Constants.APP_CACHE_DIR_CRASH + File.separator + "table.html");
                    try {
                        FileUtils.writeStringToFile(file,currentArticles.getContent());
                        webView.loadUrl("file:///mnt/sdcard/DaQingStats/files/table.html");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
                break;

            case R.id.ll_next:
                // 下一篇
                if (mArticlesList != null && currentPosition < mArticlesList.size() - 1) {
                    currentPosition++;
                    currentArticles = mArticlesList.get(currentPosition);
                    tvTitle.setText(currentArticles.getTitle());
                    tvTime.setText(TimeUtil.millisecond2DateStr(currentArticles.getCreateTime()));
                    // webView.loadDataWithBaseURL(null,bean.getContent(),"text/html", "utf-8", null);
                    getArticleData(currentArticles);

                    /*File file = StorageUtil.getAppCustomCacheDirectory(Constants.APP_CACHE_DIR_CRASH + File.separator + "table.html");
                    try {
                        FileUtils.writeStringToFile(file,currentArticles.getContent());
                        webView.loadUrl("file:///mnt/sdcard/DaQingStats/files/table.html");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
                break;
        }
        setPreviousAndNext();
    }

    /**
     * 改变"上一篇"和"下一篇"按钮颜色
     */
    private void setPreviousAndNext() {
        if(mArticlesList == null || mArticlesList.size() == 1){
            return;
        }
        ArticlesTitlesBean.ArticlesListBean bean1;
        ArticlesTitlesBean.ArticlesListBean bean2;
        String title = "";

        if(currentPosition == 0){
            // 首页
            if(mArticlesList.size() > 1){
                bean1 = mArticlesList.get(currentPosition + 1);
                title = bean1.getTitle();
            }
            llPrevisou.setVisibility(View.GONE);
            tvPreviousContent.setText("");

            llNext.setVisibility(View.VISIBLE);
            tvNextContent.setText(title);

        }else if(currentPosition == mArticlesList.size() - 1){
            // 尾页
            bean1 = mArticlesList.get(currentPosition - 1);
            title = bean1.getTitle();

            llPrevisou.setVisibility(View.VISIBLE);
            tvPreviousContent.setText(title);

            llNext.setVisibility(View.GONE);
            tvNextContent.setText("");

        }else{
            // 中间页
            bean1 = mArticlesList.get(currentPosition - 1);
            bean2 = mArticlesList.get(currentPosition + 1);

            llPrevisou.setVisibility(View.VISIBLE);
            tvPreviousContent.setText(bean1.getTitle());

            llNext.setVisibility(View.VISIBLE);
            tvNextContent.setText(bean2.getTitle());
        }
    }


    private void getArticleData(final ArticlesTitlesBean.ArticlesListBean bean) {
        RequestParams entity = new RequestParams(Urls.URL_APP_ARTCLES_DETIAL);
        entity.addParameter("id",bean.getId()+"");
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                NewArticlesBean articlesBean = gson.fromJson(result, NewArticlesBean.class);
                List<NewArticlesBean.ArticlesListBean> articlesList = articlesBean.getArticlesList();

                if (articlesList != null && !articlesList.isEmpty()) {
                    NewArticlesBean.ArticlesListBean bean1 = articlesList.get(0);
                    File file = StorageUtil.getAppCustomCacheDirectory(Constants.APP_CACHE_DIR_CRASH + File.separator + "table.html");
                    try {
                        FileUtils.writeStringToFile(file,bean1.getContent());
                        webView.loadUrl("file:///mnt/sdcard/DaQingStats/files/table.html");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ToastAlone.showShortToast("数据为空");
                }
                swipeRefres.setRefreshing(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastAlone.showShortToast("获取数据失败");
                swipeRefres.setRefreshing(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
