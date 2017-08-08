package com.stats.daqing.bean;

import android.util.Pair;

import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class DataReleaseTreeBean {


    /** 月度数据 **/
    private List<Pair<DataReleaseBean.TypesListBean,List<DataReleaseBean.TypesListBean>>> monthDataList;

    /** 季度数据 **/
    private List<Pair<DataReleaseBean.TypesListBean,List<DataReleaseBean.TypesListBean>>> seasonDataList;

    /** 年度数据 **/
    private List<Pair<DataReleaseBean.TypesListBean,List<DataReleaseBean.TypesListBean>>> yearDataList;


    public List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> getMonthDataList() {
        return monthDataList;
    }

    public void setMonthDataList(List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> monthDataList) {
        this.monthDataList = monthDataList;
    }

    public List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> getSeasonDataList() {
        return seasonDataList;
    }

    public void setSeasonDataList(List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> seasonDataList) {
        this.seasonDataList = seasonDataList;
    }

    public List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> getYearDataList() {
        return yearDataList;
    }

    public void setYearDataList(List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> yearDataList) {
        this.yearDataList = yearDataList;
    }
}
