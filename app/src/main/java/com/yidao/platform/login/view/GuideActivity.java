package com.yidao.platform.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.yidao.platform.R;
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
        mBackgroundBanner = findViewById(R.id.banner_guide_background);
    }

    private void setListener() {
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_jump, 0, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void processLogic() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.guider_1,
                R.drawable.guider_2,
                R.drawable.guider_3);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }
}
