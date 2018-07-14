package com.yidao.platform.read.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.read.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class ItemChannelActivity extends BaseActivity {

    @BindView(R.id.rv_channel)
    RecyclerView mRecyclerView;
    @BindView(R.id.imageButton)
    ImageButton mBackIB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        List<String> arrayList = new ArrayList<>();
        for (int a = 0; a < 10; a++) {
            arrayList.add("电商消费");
        }
        CardAdapter adapter = new CardAdapter(arrayList);
        mRecyclerView.setAdapter(adapter);
        addDisposable(RxView.clicks(mBackIB).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                finish();
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_activity_channel_select;
    }
}
