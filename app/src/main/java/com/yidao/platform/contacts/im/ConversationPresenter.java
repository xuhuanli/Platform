package com.yidao.platform.contacts.im;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.MyObserver.BaseResult;
import com.yidao.platform.contacts.bean.IMTokenInfo;

public class ConversationPresenter {
    private IViewConversation view;
    public ConversationPresenter(IViewConversation view) {
        this.view = view;
    }

    public void requestIMToken(String id, int flag) {
        RxHttpUtils
                .createApi(ApiService.class)
                .requestIMToken(id, flag)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseResult<IMTokenInfo>>() {
                    @Override
                    protected void onError(String s) {

                    }

                    @Override
                    protected void onSuccess(BaseResult<IMTokenInfo> imTokenInfoBaseResult) {
                        IMTokenInfo result = imTokenInfoBaseResult.getResult();
                        if (result != null) {
                            String token = result.getToken();
                            view.requestIMTokenSuccess(token);
                        }
                    }
                });
    }
}
