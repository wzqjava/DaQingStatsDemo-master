package com.stats.daqing.feature.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.ArticlesTitlesBean;
import com.stats.daqing.bean.DataReleaseBean;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;
import com.stats.daqing.feature.adapter.ArticlesAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * 数据发布 - 文章列表
 */
public class ArticlesListActivity extends BaseActivity implements View.OnClickListener {


    private XRecyclerView rvContent;


    private List<ArticlesTitlesBean.ArticlesListBean> mArticlesList;
    private TintToolbar mToolBar;
    private String title;
    private DataReleaseBean.TypesListBean dataReleaseBean;
    private ArticlesAdapter mAdapter;


    /** 当前类型id **/
    private int currentTypeId;
    /** 当前页数 **/
    private int currentPage = 1;
    /** 每页返回数据 **/
    private int pageSize = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_list);

        revMsg();
        initView();
        // getData(dataReleaseBean);
    }


    private void revMsg() {
        Intent intent = getIntent();
        dataReleaseBean = intent.getParcelableExtra("dataReleaseBean");
    }


    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        rvContent = (XRecyclerView) findViewById(R.id.rv_content);


        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(dataReleaseBean.getTypeName());
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
        }

        mArticlesList = new ArrayList<>();
        mAdapter = new ArticlesAdapter(this,mArticlesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ArticlesListActivity.this);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rvContent.setLayoutManager(linearLayoutManager);
        rvContent.setAdapter(mAdapter);



        rvContent.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rvContent.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rvContent.setArrowImageView(R.drawable.iconfont_downgrey);
        rvContent.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                // 下拉刷新
                currentPage = 1;
                getArticleData(dataReleaseBean);
            }


            @Override
            public void onLoadMore() {
                currentPage++;
                getArticleData(dataReleaseBean);
            }
        });
        rvContent.refresh();
    }

    private void getArticleData(final DataReleaseBean.TypesListBean bean) {
        RequestParams entity = new RequestParams(Urls.URL_APP_ARTCLES);
        // entity.addParameter("typeId","10");
        entity.addParameter("typeId",bean.getId()+"");
        entity.addParameter("currentPage", currentPage);
        entity.addParameter("pageSize", pageSize);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArticlesTitlesBean articlesTitlesBean = gson.fromJson(result, ArticlesTitlesBean.class);
                List<ArticlesTitlesBean.ArticlesListBean> articlesList = articlesTitlesBean.getArticlesList();
                if (articlesList.isEmpty()) {
                    ToastAlone.showShortToast("数据为空");
                    rvContent.refreshComplete();
                }else{
                    if(currentPage > articlesTitlesBean.getTotalPage()){
                        // 没有更多
                        ToastAlone.showShortToast("没有更多数据");
                        rvContent.setNoMore(true);

                    }else if(currentPage == 1){
                        // 下拉刷新
                        articlesList = articlesTitlesBean.getArticlesList();
                        mAdapter.setData(articlesList);
                        rvContent.refreshComplete();
                    }else{
                        // 加载更多
                        articlesList = articlesTitlesBean.getArticlesList();
                        mAdapter.addData(articlesList);
                        rvContent.loadMoreComplete();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastAlone.showShortToast("获取数据失败");
                rvContent.refreshComplete();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_item:
                ArticlesTitlesBean.ArticlesListBean bean = (ArticlesTitlesBean.ArticlesListBean) v.getTag();
                if (bean != null) {
                    int currentPosition = mArticlesList.indexOf(bean);
                    Intent intent = new Intent(ArticlesListActivity.this, ArticlesActivity.class);
                    intent.putParcelableArrayListExtra("articlesList", new ArrayList<Parcelable>(mArticlesList));
                    intent.putExtra("articles", bean);
                    intent.putExtra("currentPosition", currentPosition);
                    startActivity(intent);
                }else{
                    ToastAlone.showShortToast("数据为空");
                }

                break;
        }
    }
}
