package com.yidao.platform.info.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tv_wx_public)
    TextView tvWxPublic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }
}
