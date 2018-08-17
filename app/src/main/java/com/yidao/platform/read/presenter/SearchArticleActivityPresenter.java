package com.yidao.platform.read.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.info.model.UserReadRecordBean;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.bean.SearchBean;
import com.yidao.platform.read.view.IViewSearchArticleActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchArticleActivityPresenter {
    private IViewSearchArticleActivity mView;

    public SearchArticleActivityPresenter(IViewSearchArticleActivity mView) {
        this.mView = mView;
    }

    /**
     * title search
     * @param condition
     */
    public void searchArticle(String condition) {
        HashMap<String, String> map = new HashMap<>();
        map.put("condition",condition);
        RxHttpUtils.createApi(ApiService.class)
                .searchArticle(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<SearchBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(SearchBean searchBean) {
                        if (searchBean.isStatus()) {
                            List<SearchBean.ResultBean> result = searchBean.getResult();
                            ArrayList<ReadNewsBean> dataList = new ArrayList<>();
                            for (SearchBean.ResultBean resultBean : result) {
                                ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_ONE);
                                readNewsBean.setType(resultBean.getType());
                                readNewsBean.setTitle(resultBean.getTitle());
                                // TODO: 2018/8/17 0017 服务器bug
                                //readNewsBean.setReadAmount(resultBean.getReadAmount());
                                readNewsBean.setId(resultBean.getId());
                                readNewsBean.setHomeImg(resultBean.getHomeImg());
                                // TODO: 2018/8/17 0017 服务器bug
                                //readNewsBean.setDeployTime(resultBean.getDeployTime());
                                readNewsBean.setArticleContent(resultBean.getArticleContent());
                                dataList.add(readNewsBean);
                            }
                            mView.loadRecyclerData(dataList);
                        } else {
                            mView.noData();
                        }
                    }
                });
    }

    private void showError() {
        mView.showError();
        ToastUtils.showToast("网络连接失败，请查看网络");
    }
}
