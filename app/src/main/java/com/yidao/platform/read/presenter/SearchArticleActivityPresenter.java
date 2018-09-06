package com.yidao.platform.read.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.yidao.platform.app.ApiService;
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
     *
     * @param condition
     */
    public void searchArticle(String condition, int pageIndex, int pageSize,boolean isFirstRequest) {
        HashMap<String, String> map = new HashMap<>();
        map.put("condition", condition);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
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
                            SearchBean.ResultBean result = searchBean.getResult();
                            List<SearchBean.ResultBean.ListBean> list = result.getList();
                            if (list != null && list.size() < searchBean.getResult().getPageSize()) {  //所得数目< pageSize =>到底了
                                mView.loadMoreEnd(true);
                            } else {
                                mView.loadMoreComplete();
                            }
                            ArrayList<ReadNewsBean> dataList = new ArrayList<>();
                            for (SearchBean.ResultBean.ListBean listBean : list) {
                                ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_TWO);
                                readNewsBean.setId(listBean.getId());
                                readNewsBean.setTitle(listBean.getTitle());
                                readNewsBean.setArticleContent(listBean.getArticleContent());
                                readNewsBean.setDeployTime(listBean.getDeployTime());
                                readNewsBean.setHomeImg(listBean.getHomeImg());
                                readNewsBean.setReadAmount(listBean.getReadAmount());
                                dataList.add(readNewsBean);
                            }
                            if (result.getPageIndex() == 1) {  //page = 1时，表示初始列表值
                                mView.loadRecyclerData(dataList);
                            } else { //page !=1 表示上拉加载
                                mView.loadMoreData(dataList);
                            }
                        }else if (isFirstRequest){
                            mView.noData();
                        }else {
                            mView.loadMoreEnd(false);
                        }
                    }
                });
    }

    private void showError() {
        mView.showError();
    }
}
