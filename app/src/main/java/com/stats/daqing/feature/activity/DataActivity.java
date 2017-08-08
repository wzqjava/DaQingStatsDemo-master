package com.stats.daqing.feature.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.base.BasePager;
import com.stats.daqing.bean.ColumnsBean;
import com.stats.daqing.feature.adapter.ColumnPagerAdapter;
import com.stats.daqing.feature.fragment.DataInterpretationFragment;
import com.stats.daqing.feature.fragment.DataQueryFragment;
import com.stats.daqing.feature.fragment.DataRelease2Fragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击首页GridView的item跳转到的详情页面
 */
public class DataActivity extends BaseActivity implements View.OnClickListener {

    private List<String> titles;

    private ColumnPagerAdapter mExamplePagerAdapter;
    private ViewPager mViewPager;
    private int currentPosition;
    private List<ColumnsBean.ColumnsListBean> items;
    private TintToolbar mToolBar;
    private List<BasePager> pagers;
    private ImageView ivDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        revMsg();
        //初始化所有的pager,顶部会有指示器显示
        initVariable();
        initView();
        setListener();
        initMagicIndicator4();
    }


    private void initVariable() {
        titles = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            titles.add(items.get(i).getColumnName());
        }

        pagers = new ArrayList<BasePager>();
        // 统计数据
        pagers.add(new DataRelease2Fragment(DataActivity.this));
        // 统计分析
        pagers.add(new DataInterpretationFragment(DataActivity.this));
        // 数据查询
        pagers.add(new DataQueryFragment(DataActivity.this));
    }


    private void initView() {
        ivDown = (ImageView)findViewById(R.id.iv_down);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mExamplePagerAdapter = new ColumnPagerAdapter(pagers,titles);
        mViewPager.setAdapter(mExamplePagerAdapter);

        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(null);
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
        }
    }


    private void setListener() {
        ivDown.setOnClickListener(this);
    }



    private void revMsg() {
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("position", 0);
        items = intent.getParcelableArrayListExtra("items");
    }


    private void initMagicIndicator4() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        // 设置指针背景色
        magicIndicator.setBackgroundColor(Color.parseColor("#014387"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titles.get(index));
                // 设置指针文字颜色
                simplePagerTitleView.setNormalColor(Color.parseColor("#88ffffff"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        mViewPager.setCurrentItem(currentPosition);
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
            case R.id.iv_down:
                Intent intent = new Intent(DataActivity.this, DownloadActivity.class);
                startActivity(intent);
                break;
        }
    }
}
