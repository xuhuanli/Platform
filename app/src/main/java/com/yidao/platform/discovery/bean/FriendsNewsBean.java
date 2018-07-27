package com.yidao.platform.discovery.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class FriendsNewsBean implements MultiItemEntity {
    public static final int ITEM_ONE = 1;
    public static final int ITEM_TWO = 2;
    public static final int ITEM_THREE = 3;
    private int itemType;

    public FriendsNewsBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return this.itemType;
    }
}
