package com.yidao.platform.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yidao.platform.app.base.BaseActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            finish();
        },2000);
    }
}
