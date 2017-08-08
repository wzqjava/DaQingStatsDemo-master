package com.stats.daqing.feature.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stats.daqing.R;
import com.stats.daqing.base.BasePager;
import com.stats.daqing.bean.ArticlesBean;
import com.stats.daqing.bean.DataReleaseBean;
import com.stats.daqing.common.Constants;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;
import com.stats.daqing.feature.activity.DataDetailsActivity;
import com.stats.daqing.feature.adapter.DataInterpretationAdapter;
import com.stats.daqing.feature.adapter.InterpretationTypeAdapter;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 统计分析
 * Created by Administrator on 2017/5/21.
 * 统计分析
 */

public class DataInterpretationFragment extends BasePager implements View.OnClickListener {


    private XRecyclerView mRecyclerView;
    private RecyclerView rvTypes;
    private DataInterpretationAdapter mAdapter;
    private InterpretationTypeAdapter typeAdapter;
    private List<ArticlesBean.ArticlesListBean> articlesList;
    /** 当前类型id **/
    private int currentTypeId;
    /** 当前页数 **/
    private int currentPage = 1;
    /** 每页返回数据 **/
    private int pageSize = 15;


    public DataInterpretationFragment(Context context) {
        super(context);
    }

    private void initVariable() {
        articlesList = new ArrayList<>();
    }

    @Override
    public View initView() {
        initVariable();
        View inflate = assignViews();
        getTypes();
        return inflate;
    }

    @NonNull
    private View assignViews() {
        View inflate = View.inflate(mContext, R.layout.fragment_data_interpretation, null);
        mRecyclerView = (XRecyclerView) inflate.findViewById(R.id.recyclerview);
        rvTypes = (RecyclerView) inflate.findViewById(R.id.rv_types);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                // 下拉刷新
                currentPage = 1;
                getArticleData(currentTypeId);
            }


            @Override
            public void onLoadMore() {
                currentPage++;
                getArticleData(currentTypeId);
            }
        });

        mAdapter = new DataInterpretationAdapter(this,articlesList);
        mRecyclerView.setAdapter(mAdapter);
        return inflate;
    }


    private void getTypes() {
        RequestParams params = new RequestParams(Urls.URL_APP_TYPES);
        params.addParameter("columnId","2");//id是2代表统计分析;

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: result = " + result);
                Gson gson = new Gson();
                DataReleaseBean bean = gson.fromJson(result, DataReleaseBean.class);
                List<DataReleaseBean.TypesListBean> list = bean.getTypesList();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvTypes.setLayoutManager(linearLayoutManager);
                typeAdapter = new InterpretationTypeAdapter(DataInterpretationFragment.this, list);
                rvTypes.setAdapter(typeAdapter);
                /**
                 * 获取对应type对应的列表数据;
                 */
                getArticleData(list.get(0).getId());
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

    /**
     * 获取文章
     * @param typeId
     */
    private void getArticleData(int typeId) {
        currentTypeId = typeId;
        RequestParams entity = new RequestParams(Urls.URL_APP_ARTCLES);
        entity.addParameter("typeId", currentTypeId);
        entity.addParameter("currentPage", currentPage);
        entity.addParameter("pageSize", pageSize);

        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: 文章列表result = " + result);
                Gson gson = new Gson();
                ArticlesBean articlesBean = gson.fromJson(result, ArticlesBean.class);

                if(currentPage > articlesBean.getTotalPage()){
                    // 没有更多
                    ToastAlone.showShortToast("没有更多数据");
                    mRecyclerView.setNoMore(true);

                }else if(currentPage == 1){
                    // 下拉刷新
                    articlesList = articlesBean.getArticlesList();
                    mAdapter.setData(articlesList);
                    mRecyclerView.refreshComplete();
                }else{
                    // 加载更多
                    articlesList = articlesBean.getArticlesList();
                    mAdapter.addData(articlesList);
                    mRecyclerView.loadMoreComplete();
                }
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


    @Override
    public void initData() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_item:
                // 进入文章详情
                ArticlesBean.ArticlesListBean articlesListBean = (ArticlesBean.ArticlesListBean) v.getTag();

                if (articlesListBean == null) {
                    ToastAlone.showShortToast("内容为空");
                }else{
                    Intent intent = new Intent(mContext, DataDetailsActivity.class);
                    intent.putExtra(Constants.ARTICLE_ID, articlesListBean.getId());
                    mContext.startActivity(intent);
                }

                break;

            case R.id.rl_type_item:
                // 点击类型,刷新文章列表
                int position = (int) v.getTag();
                DataReleaseBean.TypesListBean type = (DataReleaseBean.TypesListBean) v.getTag(R.id.rl_type_item);
                typeAdapter.setCurrentPosition(position);
                getArticleData(type.getId());
                break;
        }
    }




}
