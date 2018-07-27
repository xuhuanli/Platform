package com.yidao.platform.discovery.view;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.yidao.platform.R;
import com.yidao.platform.app.MyApplication;
import com.yidao.platform.app.MyApplicationLike;

/**
 * @author yiw
 * @Description:
 * @date 16/1/2 16:32
 */
public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {

    /**
     * text颜色
     */
    private int textColor;

    public SpannableClickable(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
