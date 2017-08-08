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
import com.stats.daqing.bean.MaterialBean;
import com.stats.daqing.common.Constants;
import com.stats.daqing.utils.StorageUtil;
import com.stats.daqing.utils.TimeUtil;

import java.io.File;
import java.util.List;

/**
 * 数据解读适配器
 */
public class DataQueryAdapter extends RecyclerView.Adapter<DataQueryAdapter.ViewHolder> {


    private View.OnClickListener onClickListener;
    private ImageLoader imageLoader;
    private List<MaterialBean.MaterialListBean> datas = null;

    public DataQueryAdapter(View.OnClickListener onClickListener, List<MaterialBean.MaterialListBean> datas) {
        this.onClickListener = onClickListener;
        this.datas = datas;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_data_query,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MaterialBean.MaterialListBean bean = datas.get(position);

        //viewHolder.rlItem.setTag(bean);
        viewHolder.rlItem.setOnClickListener(onClickListener);
        viewHolder.tvDown.setTag(bean);
        viewHolder.tvDown.setOnClickListener(onClickListener);
        viewHolder.tvTitle.setText(bean.getName());
        viewHolder.tvCreateTime.setText(TimeUtil.millisecond2DateStr(bean.getCreateTime()));
        if (bean.getImgUrl().isEmpty()) {
            // 设置默认图片
            bean.setImgUrl("http://www.suqian.gov.cn/stjj/tjtpxw/201705/e1aae8e2bfab447880e1c86c4bb71c87/images/95c19833a4de4cce98d340b1f35c6f47.jpg");
        }
        imageLoader.displayImage(bean.getImgUrl(),viewHolder.ivInfo);


        // 判断文件是否已下载
        String fileName = bean.getFileName();
        int endIndex = fileName.lastIndexOf(".");
        String end = fileName.substring(endIndex);
        fileName = bean.getName() + end;
        File file = StorageUtil.getAppCustomCacheDirectory(Constants.APP_CACHE_DIR_CRASH + File.separator + fileName);
        if (file.exists()) {
            viewHolder.tvDown.setClickable(false);
            viewHolder.tvDown.setText("已下载");
            viewHolder.rlItem.setTag(file);
        }else{
            viewHolder.tvDown.setClickable(true);
            viewHolder.tvDown.setText("下载");
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<MaterialBean.MaterialListBean> data) {
        this.datas.clear();
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<MaterialBean.MaterialListBean>  data) {
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlItem;
        ImageView ivInfo;
        TextView tvTitle;
        TextView tvCreateTime;
        TextView tvDown;

        public ViewHolder(View view){
            super(view);
            rlItem = (RelativeLayout) view.findViewById(R.id.rl_item);
            ivInfo = (ImageView) view.findViewById(R.id.iv_info);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvCreateTime = (TextView) view.findViewById(R.id.tv_create_time);
            tvDown = (TextView) view.findViewById(R.id.tv_down);
        }
    }

}
