package com.yidao.platform.info.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.info.model.UserReadRecordBean;
import com.yidao.platform.info.view.IViewRecentlyReadActivity;
import com.yidao.platform.read.bean.ReadNewsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyRecentlyReadActivityPresenter {
    private IViewRecentlyReadActivity mView;

    public MyRecentlyReadActivityPresenter(IViewRecentlyReadActivity view) {
        mView = view;
    }

    /**
     * 最近阅读
     *
     * @param userId
     */
    public void getListUserReadArt(String userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        RxHttpUtils.createApi(ApiService.class)
                .getListUserReadArt(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<UserReadRecordBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(UserReadRecordBean userReadRecordBean) {
                        if (userReadRecordBean.isStatus()) {
                            List<UserReadRecordBean.ResultBean> result = userReadRecordBean.getResult();
                            ArrayList<ReadNewsBean> dataList = new ArrayList<>();
                            for (UserReadRecordBean.ResultBean resultBean : result) {
                                ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_ONE);
                                readNewsBean.setType(resultBean.getType());
                                readNewsBean.setTitle(resultBean.getTitle());
                                readNewsBean.setReadAmount(resultBean.getReadAmount());
                                readNewsBean.setId(resultBean.getId());
                                readNewsBean.setHomeImg(resultBean.getHomeImg());
                                readNewsBean.setDeployTime(resultBean.getDeployTime());
                                readNewsBean.setArticleContent(resultBean.getArticleContent());
                                dataList.add(readNewsBean);
                            }
                            mView.loadRecyclerData(dataList);
                        } else {
                            showError();
                        }
                    }
                });
    }

    private void showError() {
        mView.showError();
    }
}
