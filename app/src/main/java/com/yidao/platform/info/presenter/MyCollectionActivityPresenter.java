package com.yidao.platform.info.presenter;

import android.support.annotation.NonNull;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
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

    /**
     * 收藏了文章后发给server
     *
     * @param isCollection
     * @param artId
     * @param userId
     */
    public void sendCollectionInfo(Boolean isCollection, long artId, String userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("artId", String.valueOf(artId));
        map.put("userId", String.valueOf(userId));
        if (isCollection) {
            RxHttpUtils.createApi(ApiService.class)
                    .pushHasCollect(map)
                    .compose(Transformer.switchSchedulers())
                    .subscribe(new CommonObserver<String>() {
                        @Override
                        protected void onError(String errorMsg) {

                        }

                        @Override
                        protected void onSuccess(String s) {

                        }
                    });
        } else {
            RxHttpUtils.createApi(ApiService.class)
                    .unCollect(map)
                    .compose(Transformer.switchSchedulers())
                    .subscribe(new CommonObserver<String>() {
                        @Override
                        protected void onError(String errorMsg) {

                        }

                        @Override
                        protected void onSuccess(String s) {

                        }
                    });
        }
    }

    /**
     * 收藏列表
     * @param userId
     * @param pageIndex
     * @param pageSize
     */
    public void getUserCollectArtList(@NonNull String userId, String pageIndex, String pageSize) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
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
                            UserCollectArtBean.ResultBean result = userCollectArtBean.getResult();
                            List<UserCollectArtBean.ResultBean.ListBean> list = result.getList();
                            if (list != null && list.size() < result.getPageSize()) {  //所得数目< pageSize =>到底了
                                mView.loadMoreEnd(false);
                            } else {
                                mView.loadMoreComplete();
                            }
                            ArrayList<ReadNewsBean> dataList = new ArrayList<>();
                            for (UserCollectArtBean.ResultBean.ListBean listBean : list) {
                                ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_ONE);
                                readNewsBean.setType(listBean.getType());
                                readNewsBean.setTitle(listBean.getTitle());
                                readNewsBean.setReadAmount(listBean.getReadAmount());
                                readNewsBean.setId(listBean.getId());
                                readNewsBean.setHomeImg(listBean.getHomeImg());
                                readNewsBean.setDeployTime(listBean.getCollectTime());
                                readNewsBean.setArticleContent(listBean.getArticleContent());
                                dataList.add(readNewsBean);
                            }
                            if (result.getPageIndex() == 1) { //page = 1时，表示初始列表值
                                mView.loadRecyclerData(dataList);
                            }else { //上拉
                                mView.loadMoreData(dataList);
                            }
                        }else {
                            showError();
                        }
                    }
                });
    }

    private void showError() {
        mView.showError();
    }
}
