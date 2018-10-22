package com.yidao.platform.info.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.info.adapter.SectionAdapter;
import com.yidao.platform.info.model.SettingsSection;

import java.util.ArrayList;

import butterknife.BindView;

public class NewSettingsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(v -> finish());
        tbTitle.setText(R.string.settings);
    }

    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<SettingsSection> list = new ArrayList<>();
        list.add(new SettingsSection(true, null));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(true, null));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(true, null));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(""));
        list.add(new SettingsSection(true, null));
        list.add(new SettingsSection(""));

        SectionAdapter sectionAdapter = new SectionAdapter(R.layout.settings_section_item, R.layout.space_section, list);
        sectionAdapter.setOnItemClickListener((adapter, view, position) -> {
            SettingsSection section = list.get(position);
            if (!section.isHeader) {
                Toast.makeText(NewSettingsActivity.this, section.t + "  " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerview.setAdapter(sectionAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_new_settings;
    }
}
