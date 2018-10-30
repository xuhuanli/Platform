package com.yidao.platform.card;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * @author huyimin
 * 身份认证  开始页
 *
 */

public class AuthenticationActivity  extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tb_title);
        title.setText("身份认证");
        toolbar.setNavigationOnClickListener(v -> finish());
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }

    public void takePick(View view) {



    }

    public static void startAuthenticationActivity(Context context) {
        Intent intent = new Intent(context, AuthenticationActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到拍照页面
     *
     * @param view
     */
    public void toAuthenticate(View view) {
//        MainActivity.startMainActivity(this);
        CameraActivity.startCameraActivity(this);
    }
}
