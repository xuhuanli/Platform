package com.yidao.platform.app;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class TapListener extends SimpleOnGestureListener {
    private OnDoubleTapListener mDoubleTapListener;

    TapListener(OnDoubleTapListener onDoubleTapListener) {
        mDoubleTapListener = onDoubleTapListener;
    }

    public interface OnDoubleTapListener {
        void onDouble();
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        mDoubleTapListener.onDouble();
        return true;
    }
}
