package com.yidao.platform.read.view;

import android.content.Context;

import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

public class CustomDecoration extends Y_DividerItemDecoration {

    private float widthDp;
    private float startPaddingDp;
    private float endPaddingDp;

    public CustomDecoration(Context context, float widthDp, float startPaddingDp, float endPaddingDp) {
        super(context);
        this.widthDp = widthDp;
        this.startPaddingDp = startPaddingDp;
        this.endPaddingDp = endPaddingDp;
    }

    @Override
    public Y_Divider getDivider(int itemPosition) {
        return new Y_DividerBuilder()
                .setBottomSideLine(true, 0xffeaecf1, widthDp, startPaddingDp, endPaddingDp)
                .create();
    }
}
