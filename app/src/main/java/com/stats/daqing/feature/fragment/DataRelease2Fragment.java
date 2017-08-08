package com.stats.daqing.feature.fragment;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stats.daqing.R;
import com.stats.daqing.base.BasePager;
import com.stats.daqing.bean.DataReleaseBean;
import com.stats.daqing.bean.DataReleaseTreeBean;
import com.stats.daqing.common.Urls;
import com.stats.daqing.feature.adapter.MyExpandableListAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据发布
 * Created by Administrator on 2017/5/21.
 */

public class DataRelease2Fragment extends BasePager implements View.OnClickListener {

    private ExpandableListView elvContent;
    private RelativeLayout rlMonth;
    private View vLine1;
    private TextView tvMonth;
    private RelativeLayout rlSeason;
    private View vLine2;
    private TextView tvSeason;
    private RelativeLayout rlYear;
    private View vLine3;
    private TextView tvYear;
    private DataReleaseTreeBean dataReleaseTree;
    private MyExpandableListAdapter mAdapter;


    public DataRelease2Fragment(Context context) {
        super(context);
        initData();
    }

    @Override
    public View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_data_release2, null);
        assignViews(inflate);
        setListener();
        return inflate;
    }


    @Override
    public void initData() {
        getData();

    }

    /**
     * 获取数据发布数据
     */
    private void getData() {

        RequestParams params = new RequestParams(Urls.URL_APP_TYPES);
        params.addParameter("columnId","1");
        // 传入该参数避免只获取默认20条数据
        params.addParameter("pageSize","999");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                DataReleaseBean bean = gson.fromJson(result, DataReleaseBean.class);
                List<DataReleaseBean.TypesListBean> list = bean.getTypesList();

                dataReleaseTree = new DataReleaseTreeBean();
                List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> monthDataList = new ArrayList<>();
                List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> seasonDataList = new ArrayList<>();
                List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> yearDataList = new ArrayList<>();
                DataReleaseBean.TypesListBean typeBean;
                for (int i = 0; i < list.size(); i++) {
                    typeBean = list.get(i);
                    if (typeBean.getParentId() == 3) {
                        List<DataReleaseBean.TypesListBean> typesList = filterData(list, typeBean);
                        monthDataList.add(new Pair<>(typeBean, typesList));

                    }else if (typeBean.getParentId() == 4){
                        List<DataReleaseBean.TypesListBean> typesList = filterData(list, typeBean);
                        seasonDataList.add(new Pair<>(typeBean, typesList));

                    }else if (typeBean.getParentId() == 5){
                        List<DataReleaseBean.TypesListBean> typesList = filterData(list, typeBean);
                        yearDataList.add(new Pair<>(typeBean, typesList));
                    }
                }

                dataReleaseTree.setMonthDataList(monthDataList);
                dataReleaseTree.setSeasonDataList(seasonDataList);
                dataReleaseTree.setYearDataList(yearDataList);
                showData(1,dataReleaseTree.getMonthDataList());

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

    private List<DataReleaseBean.TypesListBean> filterData(List<DataReleaseBean.TypesListBean> list, DataReleaseBean.TypesListBean typeBean) {

        DataReleaseBean.TypesListBean bean;
        List<DataReleaseBean.TypesListBean> typeList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            bean = list.get(i);
            if(bean.getParentId() == typeBean.getId()){
                typeList.add(bean);
            }
        }
        return typeList;
    }


    private void assignViews(View view) {
        elvContent = (ExpandableListView) view.findViewById(R.id.elv_content);
        rlMonth = (RelativeLayout) view.findViewById(R.id.rl_month);
        vLine1 = view.findViewById(R.id.v_line1);
        tvMonth = (TextView) view.findViewById(R.id.tv_month);
        rlSeason = (RelativeLayout) view.findViewById(R.id.rl_season);
        vLine2 = view.findViewById(R.id.v_line2);
        tvSeason = (TextView) view.findViewById(R.id.tv_season);
        rlYear = (RelativeLayout) view.findViewById(R.id.rl_year);
        vLine3 = view.findViewById(R.id.v_line3);
        tvYear = (TextView) view.findViewById(R.id.tv_year);

        elvContent = (ExpandableListView) view.findViewById(R.id.elv_content);
        mAdapter = new MyExpandableListAdapter(mContext,null);
        elvContent.setAdapter(mAdapter);
    }

    private void setListener() {
        rlMonth.setOnClickListener(this);
        rlSeason.setOnClickListener(this);
        rlYear.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (dataReleaseTree == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.rl_month:
                // 月度数据
                showData(1,dataReleaseTree.getMonthDataList());
                break;
            case R.id.rl_season:
                // 季度数据
                showData(2,dataReleaseTree.getSeasonDataList());
                break;
            case R.id.rl_year:
                // 年度数据
                showData(3,dataReleaseTree.getYearDataList());
                break;
        }
    }

    /**
     * 显示数据
     * @param i 1:月度数据  2:季度数据  3:年度数据
     * @param monthDataList
     */
    private void showData(int i, List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> monthDataList) {
        setBackround(i);
        mAdapter = new MyExpandableListAdapter(mContext,monthDataList);
        elvContent.setAdapter(mAdapter);
    }

    private void setBackround(int index) {
        vLine1.setSelected(index == 1);
        vLine2.setSelected(index == 2);
        vLine3.setSelected(index == 3);

        tvMonth.setSelected(index == 1);
        tvSeason.setSelected(index == 2);
        tvYear.setSelected(index == 3);
    }

}
