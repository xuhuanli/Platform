package com.yidao.platform.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.container.ContainerActivity;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class GuideActivity extends BaseActivity {

    private static final String mPageName = "GuideActivity";
    private BGABanner mBackgroundBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        processLogic();
    }

    private void initView() {
        String userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        // TODO: 2018/8/21 0021 发布时候记得删除注释
        if (!TextUtils.isEmpty(userId)) {  //如果userId不为空 则表示用户已经登录 直接跳转到首页  token过期的情况会在首页处理 然后重新跳转到登录
            startActivity(ContainerActivity.class);
            finish();
        }
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
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onPause(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onPause(this);
    }
}
