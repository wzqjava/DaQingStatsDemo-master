package com.stats.daqing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.google.gson.Gson;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.AppVersionBean;
import com.stats.daqing.bean.ArticlesBean;
import com.stats.daqing.bean.ColumnsBean;
import com.stats.daqing.bean.MainItemBean;
import com.stats.daqing.common.Constants;
import com.stats.daqing.common.DaQingApplication;
import com.stats.daqing.common.ThemeHelper;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;
import com.stats.daqing.feature.activity.AboutActivity;
import com.stats.daqing.feature.activity.BannerArticlesActivity;
import com.stats.daqing.feature.activity.DataActivity;
import com.stats.daqing.feature.activity.LoginActivity;
import com.stats.daqing.feature.activity.SettingActivity;
import com.stats.daqing.feature.adapter.ColumnAdapter;
import com.stats.daqing.feature.banner.BannerImageLoader;
import com.stats.daqing.service.AppUpdateService;
import com.stats.daqing.utils.PublicUtil;
import com.stats.daqing.utils.SPUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 待处理
 * 1.更换栏目详情标题栏颜色
 * 2.数据解读详情页,添加上一篇下一篇
 * 3.栏目详情的item,在时间旁边添加栏目名称(未做)
 * 4.动态加载首页顶部轮播图
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, OnBannerClickListener {

    private TintToolbar mToolBar;
    private Banner mBanner;
    private ArrayList images;
    private ArrayList titles;
    private RecyclerView rvContent;
    // private MainAdapter mAdapter;
    private ColumnAdapter mAdapter;
    private ArrayList<MainItemBean> items;
    private long mkeyTime;
    private String[] bannerUrls;
    private List<ColumnsBean.ColumnsListBean> columnsList;
    /**
     * 轮播图文章列表
     **/
    private List<ArticlesBean.ArticlesListBean> articlesList;
    private ImageView iv_user;
    private TextView connect_us;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
        initVariable();
        initView();
        setListener();
        assignViews();
        initData();
        getData();
        getArticles();
        //检查版本更新;
        checkVersion();
    }


    private void initVariable() {
        mCurrentTheme = ThemeHelper.getTheme(this);
        images = new ArrayList();
        titles = new ArrayList();

        items = new ArrayList<>();
        MainItemBean bean;
        String[] itemNames = getResources().getStringArray(R.array.item_names);

        int[] itemIcons = new int[]{
                R.drawable.home_01,
                R.drawable.home_02,
                R.drawable.home_03,
                R.drawable.home_04,
                R.drawable.home_05,
        };

        for (int i = 0; i < itemNames.length; i++) {
            bean = new MainItemBean();
            bean.setName(itemNames[i]);
            bean.setImgResId(itemIcons[i]);
            items.add(bean);
        }

        columnsList = new ArrayList<>();
    }

    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        iv_user = (ImageView) findViewById(R.id.iv_user);
        connect_us = (TextView) findViewById(R.id.connect_us);
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(null);
        }

        mBanner = (Banner) findViewById(R.id.banner);
        rvContent = (RecyclerView) findViewById(R.id.rv_content);


    }

    private void setListener() {
        iv_user.setOnClickListener(this);
        connect_us.setOnClickListener(this);
    }

    private void initData() {

        //默认是CIRCLE_INDICATOR
        /*mBanner.setImages(images)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new BannerImageLoader())
                .setOnBannerClickListener(this)
                .start();*/


        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        rvContent.setLayoutManager(layoutManager);
        rvContent.setHasFixedSize(true);
        rvContent.setNestedScrollingEnabled(false);

        mAdapter = new ColumnAdapter(this, columnsList);
        rvContent.setAdapter(mAdapter);
    }

    private void getData() {
        x.http().get(new RequestParams(Urls.URL_APP_COLUMN), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: result = " + result);
                Gson gson = new Gson();
                ColumnsBean bean = gson.fromJson(result, ColumnsBean.class);
                columnsList = bean.getColumnsList();

                mAdapter.setData(columnsList);


                // 设置主题背景色
                if (TextUtils.equals(bean.getColor(), "0")) {
                    // 蓝色
                    mCurrentTheme = ThemeHelper.CARD_STORM;
                } else if (TextUtils.equals(bean.getColor(), "1")) {
                    // 红色
                    mCurrentTheme = ThemeHelper.CARD_FIREY;
                }
                updateTheme(mCurrentTheme);
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
     * 获取轮播图数据
     */
    private void getArticles() {
        RequestParams entity = new RequestParams(Urls.URL_APP_ARTCLES);
        entity.addBodyParameter("publish_flag", "0");
        entity.addBodyParameter("isFistTopFlip", "0");
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArticlesBean bean = gson.fromJson(result, ArticlesBean.class);

                images.clear();
                titles.clear();

                articlesList = bean.getArticlesList();
                ArticlesBean.ArticlesListBean artcles;
                for (int i = 0; i < articlesList.size(); i++) {
                    artcles = articlesList.get(i);
                    images.add(artcles.getImageLogo());
                    titles.add(artcles.getTitle());
                }


                mBanner.setImages(images)
                        .setBannerTitles(titles)
                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                        .setImageLoader(new BannerImageLoader())
                        .setOnBannerClickListener(MainActivity.this)
                        .start();

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


    @Override
    protected void onStart() {
        super.onStart();
        if (mBanner != null) {
            //开始轮播
            mBanner.startAutoPlay();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBanner != null) {
            //结束轮播
            mBanner.stopAutoPlay();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录头像按钮
            case R.id.iv_user:
                Intent intent;
                String userInfo = SPUtil.getPreferences(SPUtil.TYPE_USER, "userInfo", "");
                if (TextUtils.isEmpty(userInfo)) {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, SettingActivity.class);
                }
                startActivity(intent);

                break;
            //联系我们
            case R.id.connect_us:
                Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intentAbout);

                break;
            case R.id.rl_item:
                // 进入栏目详情
                int position = (int) v.getTag();
                // ToastAlone.showShortToast("onclick " + position);
                Intent intentTemp = new Intent(MainActivity.this, DataActivity.class);
                intentTemp.putExtra("position", position);
                intentTemp.putParcelableArrayListExtra("items", new ArrayList<Parcelable>(columnsList));
                startActivity(intentTemp);
                break;

            case R.id.iv_pink:
                // 粉色
                mCurrentTheme = ThemeHelper.CARD_SAKURA;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_purple:
                // 蓝色
                mCurrentTheme = ThemeHelper.CARD_HOPE;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_blue:
                // 灰色
                mCurrentTheme = ThemeHelper.CARD_STORM;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_green:
                mCurrentTheme = ThemeHelper.CARD_WOOD;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_green_light:
                mCurrentTheme = ThemeHelper.CARD_LIGHT;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_yellow:
                mCurrentTheme = ThemeHelper.CARD_THUNDER;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_orange:
                mCurrentTheme = ThemeHelper.CARD_SAND;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_red:
                mCurrentTheme = ThemeHelper.CARD_FIREY;
                updateTheme(mCurrentTheme);
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                ToastAlone.showShortToast("再按一次返回键,将退出程序");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    ;

    @Override
    public void OnBannerClick(int position) {
        if (articlesList != null) {
            ArticlesBean.ArticlesListBean bean = articlesList.get(position - 1);
            Intent intent = new Intent(MainActivity.this, BannerArticlesActivity.class);
            ArrayList<ArticlesBean.ArticlesListBean> value = new ArrayList<>();
            value.add(bean);
            intent.putExtra("articlesList", value);
            startActivity(intent);
            // ToastAlone.showShortToast("positino = " + position);
        }
    }


    private ImageView ivPink;
    private ImageView ivBlue;
    private ImageView ivPurple;
    private ImageView ivGreen;
    private ImageView ivGreenLight;
    private ImageView ivYellow;
    private ImageView ivOrange;
    private ImageView ivRed;
    private int mCurrentTheme;

    private void assignViews() {
        ivPink = (ImageView) findViewById(R.id.iv_pink);
        ivBlue = (ImageView) findViewById(R.id.iv_blue);
        ivPurple = (ImageView) findViewById(R.id.iv_purple);
        ivGreen = (ImageView) findViewById(R.id.iv_green);
        ivGreenLight = (ImageView) findViewById(R.id.iv_green_light);
        ivYellow = (ImageView) findViewById(R.id.iv_yellow);
        ivOrange = (ImageView) findViewById(R.id.iv_orange);
        ivRed = (ImageView) findViewById(R.id.iv_red);

        ivPink.setOnClickListener(this);
        ivBlue.setOnClickListener(this);
        ivPurple.setOnClickListener(this);
        ivGreen.setOnClickListener(this);
        ivGreenLight.setOnClickListener(this);
        ivYellow.setOnClickListener(this);
        ivOrange.setOnClickListener(this);
        ivRed.setOnClickListener(this);

    }

    public void checkVersion() {
        x.http().get(new RequestParams(Urls.URL_APP_UPDATE_APP), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: result = " + result);
                Gson gson = new Gson();
                final AppVersionBean appVersionBean = gson.fromJson(result, AppVersionBean.class);

                final int currentVersion = PublicUtil.getCurVersion(DaQingApplication.getContext());
                if (appVersionBean == null){
                    return;
                }
                if (currentVersion>= Integer.parseInt(appVersionBean.getVersionCode())) {
                    // 判断是否与当前版本号一致
//                    PublicUtil.checkVervisonSuccess(downAppUrl, DaQingApplication.getContext());
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(MainActivity.this);
                    normalDialog.setCancelable(false);
                    normalDialog.setIcon(R.drawable.iconfont_downgrey);
                    normalDialog.setTitle("数据大庆升级提示");
                    normalDialog.setMessage(appVersionBean.getUpdateMessage());
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                    // 点击更新，后台service下载程序，完成后提示安装即可。
                                    dialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.setClass(DaQingApplication.getContext(), AppUpdateService.class);
                                    intent.putExtra(Constants.INTENT_KEY_DONWLOAD_URL, appVersionBean.getDownAppUrl());
                                    DaQingApplication.getContext().startService(intent);
                                }
                            });

                    // 显示
                    normalDialog.show();

                } else {
                    Toast.makeText(DaQingApplication.getContext(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
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


}
