package com.yidao.platform.info.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.UIUtil;
import com.yidao.platform.info.adapter.LabelAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class SelectLabelsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.tb_ensure)
    TextView tbEnsure;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private int selectCount = 0;
    private boolean isSelected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(o -> finish());
        tbTitle.setText(R.string.career_label);
        addDisposable(RxView.clicks(tbEnsure).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Toast.makeText(SelectLabelsActivity.this, "上传", Toast.LENGTH_SHORT).show();
        }));
    }

    private void initRecyclerView() {
        UIUtil.initRecyclerView(recyclerview,this,null,null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_select_label;
    }
}
