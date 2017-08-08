package com.stats.daqing.feature.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stats.daqing.R;
import com.stats.daqing.bean.MainItemBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    private View.OnClickListener onClickListener;
    private List<MainItemBean> items;

    public MainAdapter(View.OnClickListener onClickListener,@NonNull List<MainItemBean> items) {
        this.onClickListener = onClickListener;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_main, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainItemBean bean = items.get(position);

        holder.rlItem.setTag(position);
        holder.rlItem.setOnClickListener(onClickListener);
        holder.ivIcon.setImageResource(bean.getImgResId());
        holder.tvName.setText(bean.getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(@NonNull List<MainItemBean> items) {
        this.items.clear();
        this.items.addAll(items);
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
