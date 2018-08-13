package com.yidao.platform.read.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.read.adapter.ChannelAdapter;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.presenter.ItemChannelActivityPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class ItemChannelActivity extends BaseActivity implements IViewItemChannelActivity {

    @BindView(R.id.rv_channel)
    RecyclerView mRecyclerView;
    @BindView(R.id.imageButton)
    ImageView mBackIB;
    private ItemChannelActivityPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ItemChannelActivityPresenter(this);
        initView();
        ProgressDialog dialog = new ProgressDialog(this);
        initData(dialog);
    }

    private void initData(Dialog dialog) {
        mPresenter.getListCategories(dialog);
    }

    private void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        addDisposable(RxView.clicks(mBackIB).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_channel_select;
    }

    @Override
    public void loadChannel(List<ChannelBean.ResultBean> result) {
        ChannelAdapter channelAdapter = new ChannelAdapter(result);
        mRecyclerView.setAdapter(channelAdapter);
        channelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChannelBean.ResultBean data = (ChannelBean.ResultBean) adapter.getData().get(position);
            }
        });
    }
}
