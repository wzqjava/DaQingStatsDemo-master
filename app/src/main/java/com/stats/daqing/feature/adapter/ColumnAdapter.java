package com.stats.daqing.feature.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.stats.daqing.R;
import com.stats.daqing.bean.ColumnsBean;
import com.stats.daqing.bean.MainItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */

public class ColumnAdapter extends RecyclerView.Adapter<ColumnAdapter.ViewHolder> {


    private ImageLoader imageLoader;
    private View.OnClickListener onClickListener;
    private List<ColumnsBean.ColumnsListBean> items;

    public ColumnAdapter(View.OnClickListener onClickListener, @NonNull List<ColumnsBean.ColumnsListBean> items) {
        this.onClickListener = onClickListener;
        this.items = items;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_main, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ColumnsBean.ColumnsListBean bean = items.get(position);

        holder.rlItem.setTag(position);
        holder.rlItem.setOnClickListener(onClickListener);
        String columnIco = bean.getColumnIco();
        if (columnIco.isEmpty()) {
            holder.ivIcon.setImageResource(R.drawable.home_03);
        }else{
            imageLoader.displayImage(columnIco,holder.ivIcon);
        }
        holder.tvName.setText(bean.getColumnName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(@NonNull List<ColumnsBean.ColumnsListBean> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addData(List<ColumnsBean.ColumnsListBean> columnsList) {
        this.items.addAll(columnsList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rlItem;
        TextView tvName;
        ImageView ivIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);
            ivIcon = (ImageView)itemView.findViewById(R.id.iv_icon);
            tvName = (TextView)itemView.findViewById(R.id.tv_name);
        }
    }


}
