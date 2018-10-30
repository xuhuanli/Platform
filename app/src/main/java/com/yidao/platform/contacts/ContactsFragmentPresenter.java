package com.yidao.platform.contacts;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.MyObserver.BaseResult;
import com.yidao.platform.contacts.bean.IMTokenInfo;

public class ContactsFragmentPresenter {
    private IViewContactsFragment mView;

    public ContactsFragmentPresenter(IViewContactsFragment view) {
        mView = view;
    }

    /**
     * 获取IM token
     *
     * @param userId
     */
    public void requestIMToken(String userId, int hasToken) {
        RxHttpUtils
                .createApi(ApiService.class)
                .requestIMToken(userId, hasToken)
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
                            mView.requestIMTokenSuccess(token);
                        }
                    }
                });
    }
}
