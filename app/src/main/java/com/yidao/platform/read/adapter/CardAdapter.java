package com.yidao.platform.read.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidao.platform.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<String> mCardList;

    public CardAdapter(List<String> cardList) {
        mCardList = cardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.read_channel_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.channel.setText(mCardList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView channel;

        ViewHolder(View itemView) {
            super(itemView);
            channel = itemView.findViewById(R.id.tv_channel);
        }
    }
}
