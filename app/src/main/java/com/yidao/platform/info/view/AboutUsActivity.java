package com.yidao.platform.info.view;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tv_wx_public)
    TextView tvWxPublic;
    @BindView(R.id.toolbar_info)
    Toolbar mToolbar;
    @BindView(R.id.tv_version_name)
    TextView mVersionName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDisposable(RxToolbar.navigationClicks(mToolbar).subscribe(o -> finish()));
        initVersion();
    }

    private void initVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            mVersionName.setText("版本号: "+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }
}
