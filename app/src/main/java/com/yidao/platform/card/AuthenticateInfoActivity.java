package com.yidao.platform.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.UIUtil;
import com.yidao.platform.info.model.TagBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author huyimin
 * 身份 信息页
 */

public class AuthenticateInfoActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_count)
    TextView tv_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
    }

    private void initView() {
        ArrayList<TagBean> list = new ArrayList<>();
        list.add(new TagBean("社交", true));
        UIUtil.initRecyclerView(recyclerview, this, list, tv_count);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tb_title);
        title.setText("请填写您的认证信息");
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setElevation(0);
    }

    public static void startAuthenticateInfoActivity(Context context) {
        Intent intent = new Intent(context, AuthenticateInfoActivity.class);
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
        return R.layout.activity_authenticateinfo;
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
