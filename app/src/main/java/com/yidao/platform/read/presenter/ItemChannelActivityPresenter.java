package com.yidao.platform.read.presenter;


import android.app.Dialog;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.view.IViewItemChannelActivity;
import com.yidao.platform.testpackage.bean.ApiService;

import java.util.List;

public class ItemChannelActivityPresenter {
    private IViewItemChannelActivity mView;

    public ItemChannelActivityPresenter(IViewItemChannelActivity view) {
        mView = view;
    }

    public void getListCategories(Dialog dialog) {
        RxHttpUtils.createApi(ApiService.class)
                .getListCategories()
                .compose(Transformer.switchSchedulers(dialog))
                .subscribe(new CommonObserver<ChannelBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        dialog.dismiss();
                        showError();
                    }

                    @Override
                    protected void onSuccess(ChannelBean channelBean) {
                        dialog.dismiss();
                        if (channelBean.isStatus()) {
                            List<ChannelBean.ResultBean> result = channelBean.getResult();
                            mView.loadChannel(result);
                        }
                    }
                });
    }

    private void showError() {
        ToastUtils.showToast("网络连接失败，请查看网络");
    }
}
