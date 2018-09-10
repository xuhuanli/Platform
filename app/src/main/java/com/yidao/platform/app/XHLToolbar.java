package com.yidao.platform.app;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class XHLToolbar extends Toolbar implements TapListener.OnDoubleTapListener {
    private OnTwoTapListener mOnTwoTapListener;
    private GestureDetector mDetector;

    public interface OnTwoTapListener {
        void onTwoTap();
    }

    public void setOnTwoTapListener(OnTwoTapListener onTwoTapListener) {
        mOnTwoTapListener = onTwoTapListener;
    }

    public XHLToolbar(Context context) {
        this(context, null);
    }

    public XHLToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public XHLToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TapListener tapListener = new TapListener(this);
        mDetector = new GestureDetector(context, tapListener);
    }

    @Override
    public void onDouble() {
        mOnTwoTapListener.onTwoTap();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        mDetector.onTouchEvent(ev);
        return true;
    }

}
