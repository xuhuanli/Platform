package com.yidao.platform.read.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.read.view.IViewReadContentActivity;

import java.util.HashMap;

public class ReadContentActivityPresenter {
    private IViewReadContentActivity mView;

    public ReadContentActivityPresenter(IViewReadContentActivity view) {
        mView = view;
    }

    /**
     * 发布评论
     *
     * @param artId
     * @param context
     * @param userId
     */
    public void pushComment(long artId, String context, String userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("artId", String.valueOf(artId));
        map.put("context", context);
        map.put("userId", userId);
        RxHttpUtils.createApi(ApiService.class)
                .pushComment(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(String data) {

                    }
                });
    }

    private void showError() {
        ToastUtils.showToast("网络连接失败，请查看网络");
    }
}
