package com.stats.daqing.feature.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.stats.daqing.base.BasePager;

import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 */

public class ColumnPagerAdapter extends PagerAdapter {
    private List<BasePager> mDataList;
    private List<String> titles;

    public ColumnPagerAdapter(List<BasePager> dataList, List<String> titles) {
        this.mDataList = dataList;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager basePager = mDataList.get(position);
        container.addView(basePager.getRootView());
        return basePager.getRootView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
