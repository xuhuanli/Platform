package com.yidao.platform.webview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

public class PhotoBrowserActivity extends BaseActivity {

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String imageUrls = intent.getStringExtra("imageUrls");
        mImage = findViewById(R.id.imageview);
        Glide.with(this).load(imageUrls).into(mImage);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_browser;
    }
}
