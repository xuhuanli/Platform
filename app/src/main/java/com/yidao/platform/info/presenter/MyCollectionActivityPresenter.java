package com.yidao.platform.info.presenter;

import android.support.annotation.NonNull;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.info.model.UserCollectArtBean;
import com.yidao.platform.info.view.IViewMyCollectionActivity;
import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyCollectionActivityPresenter {
    private IViewMyCollectionActivity mView;

    public MyCollectionActivityPresenter(IViewMyCollectionActivity view) {
        this.mView = view;
    }

    public void getUserCollectArtList(@NonNull String userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        RxHttpUtils.createApi(ApiService.class)
                .getUserCollectArt(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<UserCollectArtBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(UserCollectArtBean userCollectArtBean) {
                        if (userCollectArtBean.isStatus()) {
                            List<UserCollectArtBean.ResultBean> result = userCollectArtBean.getResult();
                            ArrayList<ReadNewsBean> dataList = new ArrayList<>();
                            for (UserCollectArtBean.ResultBean resultBean : result) {
                                ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_TWO);
                                readNewsBean.setType(resultBean.getType());
                                readNewsBean.setTitle(resultBean.getTitle());
                                readNewsBean.setReadAmount(resultBean.getReadAmount());
                                readNewsBean.setId(resultBean.getId());
                                readNewsBean.setHomeImg(resultBean.getHomeImg());
                                readNewsBean.setDeployTime(resultBean.getDeployTime());
                                dataList.add(readNewsBean);
                            }
                            mView.loadRecyclerData(dataList);
                        }
                    }
                });
    }

    private void showError() {
        mView.showError();
        ToastUtils.showToast("网络连接失败，请查看网络");
    }
}
