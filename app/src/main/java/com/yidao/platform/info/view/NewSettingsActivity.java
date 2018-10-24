package com.yidao.platform.info.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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
    //CityPickerView mPicker=new CityPickerView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPicker.init(this);
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
            CustomTextView customTextView = (CustomTextView) view;
            switch (position) {
                case 1: //头像
                    break;
                case 2:
                    modifyInfo("昵称修改", customTextView.getValue());
                    break;
                case 3:
                    break;
                case 5:
                    break;
                case 6: //地区
                    /*CityConfig cityConfig = new CityConfig.Builder().build();
                    mPicker.setConfig(cityConfig);
                    mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                        @Override
                        public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                            //省份
                            if (province != null) {

                            }

                            //城市
                            if (city != null) {

                            }

                            //地区
                            if (district != null) {

                            }

                        }

                        @Override
                        public void onCancel() {
                            ToastUtils.showLongToast(getApplicationContext(), "已取消");
                        }
                    });

                    //显示
                    mPicker.showCityPicker( );*/
                    break;
                case 7:
                    modifyInfo("所属公司", customTextView.getValue());
                    break;
                case 8:
                    Intent intent = new Intent(this, SelectLabelsActivity.class);
                    startActivity(intent);
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 14:
                    break;
            }
            /*SettingsSection section = list.get(position);
            if (!section.isHeader) {
                Toast.makeText(NewSettingsActivity.this, section.t + "  " + position, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(NewSettingsActivity.this, section.t + " isHeader  " + position, Toast.LENGTH_SHORT).show();
            }*/
        });
        recyclerview.setAdapter(sectionAdapter);
    }

    private void modifyInfo(String title, String value) {
        Intent intent = new Intent(this, NewChangeInfoActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("value", value);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_new_settings;
    }
}
