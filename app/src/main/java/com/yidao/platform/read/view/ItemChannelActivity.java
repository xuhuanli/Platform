package com.yidao.platform.read.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.read.adapter.ChannelAdapter;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.presenter.ItemChannelActivityPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

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
        initData();
    }

    private void initData() {
        mPresenter.getListCategories();
    }

    private void initView() {
        int taskId = getTaskId();
        MyLogger.e("ItemChannelActivity:所在的任务的id为: " +  taskId);
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
        ChannelAdapter mAdapter = new ChannelAdapter(result);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ChannelBean.ResultBean data = (ChannelBean.ResultBean) adapter.getData().get(position);
            Intent intent = new Intent(ItemChannelActivity.this, ReadItemMoreActivity.class);
            intent.putExtra("categoryId", data.getId());
            intent.putExtra("categoryName", data.getName());
            startActivity(intent);
        });
    }
}
