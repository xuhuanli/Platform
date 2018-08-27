package com.yidao.platform.read.presenter;


import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.view.IViewItemChannelActivity;

import java.util.List;

public class ItemChannelActivityPresenter {
    private IViewItemChannelActivity mView;

    public ItemChannelActivityPresenter(IViewItemChannelActivity view) {
        mView = view;
    }

    public void getListCategories() {
        RxHttpUtils.createApi(ApiService.class)
                .getListCategories()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<ChannelBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(ChannelBean channelBean) {
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
