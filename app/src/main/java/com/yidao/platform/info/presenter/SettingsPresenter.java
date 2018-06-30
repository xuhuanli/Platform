package com.yidao.platform.info.presenter;

import android.widget.ProgressBar;

import com.yidao.platform.info.view.SettingsViewInterface;

public class SettingsPresenter {

    private SettingsViewInterface view;

    public SettingsPresenter(SettingsViewInterface view) {
        this.view = view;
    }

    public void showProgressBar(ProgressBar progressBar) {
        view.showProgressBar(progressBar);
    }

    public void dismissProgressBar(ProgressBar progressBar) {
        view.dismissProgressbar(progressBar);
    }
}
