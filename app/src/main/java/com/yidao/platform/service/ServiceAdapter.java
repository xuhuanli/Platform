package com.yidao.platform.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidao.platform.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class ServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum ITEM_TYPE {
        ITEM_TYPE_HEADER,
        ITEM_TYPE_LABEL,
        ITEM_TYPE_EDITOR,
        ITEM_TYPE_FOOTER
    }

    private Context mContext;
    private static final int ITEM_COUNT = 10;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) { //加载header
            View view = LayoutInflater.from(mContext).inflate(R.layout.service_recycle_item_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_LABEL.ordinal()) { //加载label
            View view = LayoutInflater.from(mContext).inflate(R.layout.service_recycle_item_label, parent, false);
            return new LabelViewHolder(view);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_FOOTER.ordinal()) { //加载footer
            View view = LayoutInflater.from(mContext).inflate(R.layout.service_recycle_footer_label, parent, false);
            return new FooterViewHolder(view);
        } else { //加载editor
            View view = LayoutInflater.from(mContext).inflate(R.layout.service_recycle_editor_label, parent, false);
            return new EditorViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            Glide.with(mContext)
                    .load(mContext.getResources().getDrawable(R.drawable.a))
                    .into(((HeaderViewHolder) holder).mHeaderImg);
        } else if (holder instanceof LabelViewHolder) {
            ((LabelViewHolder) holder).tv_label.setText(R.string.base_info);
        }else if (holder instanceof FooterViewHolder){
            ((FooterViewHolder)holder).tv_protocal.setText(R.string.base_info);
        }else {
            ((EditorViewHolder)holder).et_information.setHint(R.string.service_protocal);
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal();
        } else if (position == 1 || position == 9) {
            return ITEM_TYPE.ITEM_TYPE_LABEL.ordinal();
        } else if (position == getItemCount() - 1) {
            return ITEM_TYPE.ITEM_TYPE_FOOTER.ordinal();
        } else {
            return ITEM_TYPE.ITEM_TYPE_EDITOR.ordinal();
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_service_header)
        ImageView mHeaderImg;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class LabelViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_service_label)
        TextView tv_label;

        LabelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_service_protocal)
        CheckBox checkBox_protocal;
        @BindView(R.id.tv_service_protocal)
        TextView tv_protocal;
        @BindView(R.id.btn_service_protocal)
        Button button_protocal;

        FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    class EditorViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.et_service_information)
        EditText et_information;

        EditorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
