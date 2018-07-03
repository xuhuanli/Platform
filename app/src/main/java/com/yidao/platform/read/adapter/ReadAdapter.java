package com.yidao.platform.read.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuhuanli.androidutils.toast.ToastUtil;
import com.yidao.platform.R;
import com.yidao.platform.read.view.ReadContentActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ReadViewHolder> {

    ArrayList<String> dataList;
    private ViewGroup parent;

    public ReadAdapter(ArrayList<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ReadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.read_recycle_item, parent, false);
        final ReadViewHolder viewHolder = new ReadViewHolder(view);
        viewHolder.rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过getAdapterPosition获取到的position有点问题 -1后显示正确
                int position = viewHolder.getAdapterPosition();
                String s = dataList.get(position-1);
                ToastUtil.showShort(v.getContext(), s);
                Intent intent = new Intent(v.getContext(), ReadContentActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReadViewHolder holder, int position) {
        holder.content.setText(dataList.get(position));
        Glide
                .with(parent.getContext())
                .load(parent.getContext().getResources().getDrawable(R.drawable.read_rv_placeholder))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ReadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.read_list_content)
        TextView content;
        @BindView(R.id.read_list_image)
        ImageView image;
        @BindView(R.id.read_list_sum)
        TextView readSum;
        @BindView(R.id.read_list_time)
        TextView more;
        @BindView(R.id.cl_rootview)
        ConstraintLayout rootview;

        ReadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
