package com.yidao.platform.contacts;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsMainPageActivity extends BaseActivity {
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.iv_head_portrait)
    CircleImageView ivHeadPortrait;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.tv_label1)
    TextView tvLabel1;
    @BindView(R.id.tv_label2)
    TextView tvLabel2;
    @BindView(R.id.tv_label3)
    TextView tvLabel3;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.tv_album)
    TextView tvAlbum;
    @BindView(R.id.iv_photo1)
    ImageView ivPhoto1;
    @BindView(R.id.iv_photo2)
    ImageView ivPhoto2;
    @BindView(R.id.iv_photo3)
    ImageView ivPhoto3;
    @BindView(R.id.iv_photo4)
    ImageView ivPhoto4;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.cl_album)
    ConstraintLayout clAlbum;
    @BindView(R.id.tv_sendMsg)
    TextView tvSendMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.FF007AFF));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initView();
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.contacts_mainpage_activity;
    }
}
