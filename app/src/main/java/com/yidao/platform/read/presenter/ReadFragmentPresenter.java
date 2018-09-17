package com.yidao.platform.read.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.utils.ToastUtils;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.MyApplicationLike;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.read.bean.ArticleBean;
import com.yidao.platform.read.bean.BannerBean;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.bean.CommonArticleBean;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.bean.RefreshTokenObj;
import com.yidao.platform.read.view.IViewReadFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReadFragmentPresenter {
    private IViewReadFragment mView;

    public ReadFragmentPresenter(IViewReadFragment view) {
        mView = view;
    }

    /**
     * get banner data from server
     */
    public void getBannerData() {
        RxHttpUtils
                .createApi(ApiService.class)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        if (bannerBean.isStatus()) {
                            List<BannerBean.ResultBean> result = bannerBean.getResult();
                            List<String> imageUrls = new ArrayList<>();
                            List<String> bannerTitles = new ArrayList<>();
                            List<String> artUrls = new ArrayList<>();
                            List<Long> artIds = new ArrayList<>();
                            for (BannerBean.ResultBean resultBean : result) {
                                imageUrls.add(resultBean.getImageUrl());
                                bannerTitles.add(resultBean.getTitle());
                                artUrls.add(resultBean.getUrl());
                                artIds.add(resultBean.getArtId());
                            }
                            mView.showBanner(imageUrls, bannerTitles, artUrls, artIds);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 首页18篇热门文章
     */
    public void getMainArticleData() {
        RxHttpUtils
                .createApi(ApiService.class)
                .getMainArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        MyLogger.e("onSubscribe");
                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        mView.setEnableLoadMore(true);
                        mView.setRefreshing(false);
                        if (articleBean.isStatus()) {
                            List<ArticleBean.ResultBean> result = articleBean.getResult();
                            if (result != null) {
                                List<ReadNewsBean> dataList = new ArrayList<>();
                                for (ArticleBean.ResultBean resultBean : result) {
                                    List<ArticleBean.ResultBean.ArticleExtListBean> articleExtList = resultBean.getArticleExtList();
                                    if (articleExtList != null) {
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
                                }
                                mView.showMainArticle(dataList);
                            }else {
                                showError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setEnableLoadMore(true);
                        mView.setRefreshing(false);
                        showError();
                        MyLogger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

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
                            if (list != null) {
                                if (list.size() < commonArticleBean.getPageSize()) {  //所得数目< pageSize =>到底了
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
                    }
                });
    }

    /**
     * 获取文章类目
     */
    public void getListCategories() {
        RxHttpUtils.createApi(ApiService.class)
                .getListCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChannelBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChannelBean channelBean) {
                        MyLogger.e("获取文章类目: onNext");
                        MyLogger.e("ChannelBean toString := " + channelBean.getResult().toString());
                        if (channelBean.isStatus()) {
                            ArrayList<ChannelBean.ResultBean> result = (ArrayList<ChannelBean.ResultBean>) channelBean.getResult();
                            mView.saveChannelData(result);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showError() {
        mView.showError();
        ToastUtils.showToast("服务器连接失败");
    }

    /**
     * 刷新token
     * @param refreshToken
     */
    public void refreshToken(String  refreshToken,String artUrl) {
        RxHttpUtils
                .createApi(ApiService.class)
                .refreshToken(refreshToken)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String errCode = jsonObject.getString(Constant.STRING_ERRCODE);
                            switch (errCode) {
                                case "1000":
                                    String token = jsonObject.getJSONObject("result").getString("token");
                                    IPreference.prefHolder.getPreference(MyApplicationLike.getAppContext()).put(Constant.STRING_USER_TOKEN, token);
                                    mView.loadNewToken(artUrl);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MyLogger.e(s);
                    }
                });
    }
}
