package com.yidao.platform.info.model;

import com.chad.library.adapter.base.entity.SectionEntity;

public class SettingsSection extends SectionEntity<String> {
    public SettingsSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SettingsSection(String s) {
        super(s);
    }
}
