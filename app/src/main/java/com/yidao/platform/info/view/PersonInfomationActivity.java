package com.yidao.platform.info.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class PersonInfomationActivity extends BaseActivity {

    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;
    @BindView(R.id.head_portrait)
    ImageView headPortrait;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.rl_head)
    ConstraintLayout rlHead;
    @BindView(R.id.tv_nick_name)
    CustomTextView tvNickName;
    @BindView(R.id.tv_sex)
    CustomTextView tvSex;
    @BindView(R.id.tv_phone_number)
    CustomTextView tvPhoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @SuppressLint("CheckResult")
    private void initView() {
        RxToolbar.navigationClicks(toolbarInfo).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish());
        Glide.with(this).load(R.drawable.mypic).into(headPortrait);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_personal_profile;
    }
}
