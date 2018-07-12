package com.github.lzyzsd.jsinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.library.R;

public class PhotoBrowserActivity extends AppCompatActivity {

    private ImageView mImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_show_image);
        Intent intent = getIntent();
        String imageUrls = intent.getStringExtra("imageUrls");
        mImage = findViewById(R.id.imageview);
        Glide.with(this).load(imageUrls).into(mImage);
    }
}
