package com.yidao.platform.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsSettingActivity extends BaseActivity {
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.tv_clear_record)
    TextView tvClearRecord;
    @BindView(R.id.tv_mute)
    TextView tvMute;
    @BindView(R.id.switch_toggle)
    Switch switchToggle;
    @BindView(R.id.tv_complaint)
    TextView tvComplaint;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(v -> finish());
        ((TextView)toolbar.findViewById(R.id.tb_title)).setText(R.string.more_settings);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contacts_settings_activity;
    }

}
