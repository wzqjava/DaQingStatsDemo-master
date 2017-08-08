package com.stats.daqing.feature.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stats.daqing.R;
import com.stats.daqing.bean.DataReleaseBean;
import com.stats.daqing.common.DaQingApplication;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */

public class InterpretationTypeAdapter extends RecyclerView.Adapter<InterpretationTypeAdapter.ViewHolder> {


    private final View.OnClickListener onClickListener;
    private List<DataReleaseBean.TypesListBean> list;
    private int currentPosition;

    public InterpretationTypeAdapter(View.OnClickListener onClickListener, @NonNull List<DataReleaseBean.TypesListBean> list) {
        this.onClickListener = onClickListener;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_interpretation_type, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataReleaseBean.TypesListBean bean = list.get(position);
        holder.tvType.setText(bean.getTypeName());
        holder.tvType.setSelected(currentPosition == position);
        holder.vLine.setSelected(currentPosition == position);

        holder.rlTypeItem.setTag(position);
        holder.rlTypeItem.setTag(R.id.rl_type_item,bean);
        holder.rlTypeItem.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setCurrentPosition(int position) {
        currentPosition = position;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rlTypeItem;
        private RelativeLayout rlType;
        private View vLine;
        private TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            rlTypeItem = (RelativeLayout) itemView.findViewById(R.id.rl_type_item);
            rlType = (RelativeLayout) itemView.findViewById(R.id.rl_type);
            vLine = itemView.findViewById(R.id.v_line);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);

            int screenWidth = UIUtil.getScreenWidth(DaQingApplication.getContext());
            int itemWidth = screenWidth / 3;
            rlType.getLayoutParams().width = itemWidth;
        }
    }

}
