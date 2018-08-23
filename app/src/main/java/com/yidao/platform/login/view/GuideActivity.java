package com.yidao.platform.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class GuideActivity extends BaseActivity {

    private BGABanner mBackgroundBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        processLogic();
    }

    private void initView() {
        if (IPreference.prefHolder.getPreference(this).contains(Constant.STRING_HAS_GUIDE)) {
            startActivity(LoginActivity.class);
            finish();
        } else {
            IPreference.prefHolder.getPreference(this).put(Constant.STRING_HAS_GUIDE, true);
        }
        mBackgroundBanner = findViewById(R.id.banner_guide_background);
    }

    private void setListener() {
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_jump, 0, () -> {
            startActivity(new Intent(GuideActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void processLogic() {
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.guider_1,
                R.drawable.guider_2,
                R.drawable.guider_3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }
}
