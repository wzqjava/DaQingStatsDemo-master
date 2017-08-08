package com.stats.daqing.feature.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.stats.daqing.R;
import com.stats.daqing.bean.ArticlesBean;
import com.stats.daqing.bean.DataInterpretationBean;
import com.stats.daqing.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据解读适配器
 */
public class DataInterpretationAdapter extends RecyclerView.Adapter<DataInterpretationAdapter.ViewHolder> {


    private View.OnClickListener onClickListener;
    private ImageLoader imageLoader;
    public List<ArticlesBean.ArticlesListBean> datas = null;

    public DataInterpretationAdapter(View.OnClickListener onClickListener,List<ArticlesBean.ArticlesListBean> datas) {
        this.onClickListener = onClickListener;
        this.datas = datas;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_data_interpretation,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ArticlesBean.ArticlesListBean bean = datas.get(position);

        viewHolder.rlItem.setTag(bean);
        viewHolder.rlItem.setOnClickListener(onClickListener);
        viewHolder.tvTitle.setText(bean.getTitle());
        viewHolder.tvCreateTime.setText(TimeUtil.millisecond2DateStr(bean.getCreateTime()));
        if (bean.getImageSmallList().isEmpty()) {
            // 设置默认图片
            bean.setImageSmallList("http://www.suqian.gov.cn/stjj/tjtpxw/201705/e1aae8e2bfab447880e1c86c4bb71c87/images/95c19833a4de4cce98d340b1f35c6f47.jpg");
        }
        imageLoader.displayImage(bean.getImageSmallList(),viewHolder.ivInfo);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<ArticlesBean.ArticlesListBean>  data) {
        this.datas.clear();
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<ArticlesBean.ArticlesListBean>  data) {
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlItem;
        ImageView ivInfo;
        TextView tvTitle;
        TextView tvCreateTime;

        public ViewHolder(View view){
            super(view);
            rlItem = (RelativeLayout) view.findViewById(R.id.rl_item);
            ivInfo = (ImageView) view.findViewById(R.id.iv_info);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvCreateTime = (TextView) view.findViewById(R.id.tv_create_time);
        }
    }






}
