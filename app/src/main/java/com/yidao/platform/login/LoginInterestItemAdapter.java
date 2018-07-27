package com.yidao.platform.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.container.ContainerActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class LoginInterestItemAdapter extends RecyclerView.Adapter<LoginInterestItemAdapter.ItemViewHolder> {

    private Context mContext;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.login_interest_item, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        RxView.clicks(holder.mIvInterestItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                int position = holder.getAdapterPosition();
                mContext.startActivity(new Intent(mContext, ContainerActivity.class));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.mIvInterestItem.setText("hello");
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_interest_item)
        TextView mIvInterestItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
