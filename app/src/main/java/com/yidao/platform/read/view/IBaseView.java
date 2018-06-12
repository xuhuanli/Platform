package com.yidao.platform.read.view;

import android.content.Context;

/**
 * The interface Base view.
 */
public interface IBaseView {

    void showLoading();

    void hideLoading();

    void showToast(CharSequence msg);

    void showError();

    Context getContext();
}
