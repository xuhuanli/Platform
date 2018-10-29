package com.yidao.platform.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

/**
 * @author huyimin
 * 身份 验证成功
 */

public class AuthenticateSuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
    }

    private void initView() {
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tb_title);
        title.setText("请填写您的认证信息");
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setElevation(0);
    }

    public static void startAuthenticateInfoActivity(Context context) {
        Intent intent = new Intent(context, AuthenticateSuccessActivity.class);
        context.startActivity(intent);
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
        return R.layout.activity_authenticatesuccess;
    }

    public void takePick(View view) {
    }

    /**
     * 跳转到拍照页面
     *
     * @param view
     */
    public void toAuthenticate(View view) {
        CameraActivity.startCameraActivity(this);
    }
}
