package com.yidao.platform.read.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.read.bean.ArticleBean;
import com.yidao.platform.read.bean.BannerBean;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.bean.CommonArticleBean;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.view.IViewReadFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadFragmentPresenter {
    IViewReadFragment mView;

    public ReadFragmentPresenter(IViewReadFragment view) {
        mView = view;
    }

    /**
     * get banner data from server
     */
    public void getBannerData() {
        RxHttpUtils.createApi(ApiService.class)
                .getBanner()
                .compose(Transformer.<BannerBean>switchSchedulers())
                .subscribe(new CommonObserver<BannerBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(BannerBean bannerBean) {
                        if (bannerBean.isStatus()) {
                            List<BannerBean.ResultBean> result = bannerBean.getResult();
                            List<String> imageUrls = new ArrayList<>();
                            List<String> bannerTitles = new ArrayList<>();
                            for (BannerBean.ResultBean resultBean : result) {
                                imageUrls.add(resultBean.getImageUrl());
                                bannerTitles.add(resultBean.getTitle());
                            }
                            mView.showBanner(imageUrls, bannerTitles);
                        }
                    }
                });
    }

    /**
     * 首页18篇热门文章
     */
    public void getMainArticleData() {
        RxHttpUtils.createApi(ApiService.class)
                .getMainArticle()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<ArticleBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.setEnableLoadMore(true);
                        mView.setRefreshing(false);
                        showError();
                    }

                    @Override
                    protected void onSuccess(ArticleBean articleBean) {
                        mView.setEnableLoadMore(true);
                        mView.setRefreshing(false);
                        if (articleBean.isStatus()) {
                            List<ArticleBean.ResultBean> result = articleBean.getResult();
                            //把数据保存到list
                            List<ReadNewsBean> dataList = new ArrayList<>();
                            for (ArticleBean.ResultBean resultBean : result) {
                                List<ArticleBean.ResultBean.ArticleExtListBean> articleExtList = resultBean.getArticleExtList();
                                for (ArticleBean.ResultBean.ArticleExtListBean articleExtListBean : articleExtList) {
                                    int type = articleExtListBean.getType();
                                    if (type == 2) { //0为普通 1为精选 2为类目
                                        ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_ONE);
                                        readNewsBean.setCategoryId(resultBean.getCategoryId());
                                        readNewsBean.setDeployTime(articleExtListBean.getDeployTime());
                                        readNewsBean.setHomeImg(articleExtListBean.getHomeImg());
                                        readNewsBean.setId(articleExtListBean.getId());
                                        readNewsBean.setReadAmount(articleExtListBean.getReadAmount());
                                        readNewsBean.setTitle(articleExtListBean.getTitle());
                                        readNewsBean.setArticleContent(articleExtListBean.getArticleContent());
                                        readNewsBean.setType(articleExtListBean.getType());
                                        dataList.add(readNewsBean);
                                    } else if (type == 1) {
                                        ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_TWO);
                                        readNewsBean.setCategoryId(resultBean.getCategoryId());
                                        readNewsBean.setDeployTime(articleExtListBean.getDeployTime());
                                        readNewsBean.setHomeImg(articleExtListBean.getHomeImg());
                                        readNewsBean.setId(articleExtListBean.getId());
                                        readNewsBean.setReadAmount(articleExtListBean.getReadAmount());
                                        readNewsBean.setTitle(articleExtListBean.getTitle());
                                        readNewsBean.setType(articleExtListBean.getType());
                                        readNewsBean.setArticleContent(articleExtListBean.getArticleContent());
                                        dataList.add(readNewsBean);
                                    }
                                }
                            }
                            mView.showMainArticle(dataList);
                        }
                    }
                });
    }

    /**
     * 首页上拉加载的文章
     */
    public void loadMoreData(String pageIndex, String pageSize) {
        HashMap<String, String> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RxHttpUtils.createApi(ApiService.class)
                .getCommonArticle(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CommonArticleBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.loadMoreFail();
                    }

                    @Override
                    protected void onSuccess(CommonArticleBean commonArticleBean) {
                        if (commonArticleBean.isStatus()) {
                            List<CommonArticleBean.ListBean> list = commonArticleBean.getList();
                            if (list != null && list.size() < commonArticleBean.getPageSize()) {  //所得数目< pageSize =>到底了
                                mView.loadMoreEnd(false);
                            } else {
                                mView.loadMoreComplete();
                            }
                            ArrayList<ReadNewsBean> dataList = new ArrayList<>();
                            for (CommonArticleBean.ListBean listBean : list) {
                                ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_TWO);
                                readNewsBean.setType(listBean.getType());
                                readNewsBean.setTitle(listBean.getTitle());
                                readNewsBean.setReadAmount(listBean.getReadAmount());
                                readNewsBean.setId(listBean.getId());
                                readNewsBean.setHomeImg(listBean.getHomeImg());
                                readNewsBean.setDeployTime(listBean.getDeployTime());
                                readNewsBean.setArticleContent(listBean.getArticleContent());
                                dataList.add(readNewsBean);
                            }
                            mView.loadMoreData(dataList);
                        }
                    }
                });
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
                            ArrayList<ChannelBean.ResultBean> result = (ArrayList<ChannelBean.ResultBean>) channelBean.getResult();
                            mView.saveChannelData(result);
                        }
                    }
                });
    }

    private void showError() {
        mView.showError();
        ToastUtils.showToast("网络连接失败，请查看网络");
    }
}
