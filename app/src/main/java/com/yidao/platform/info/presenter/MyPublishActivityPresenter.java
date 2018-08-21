package com.yidao.platform.info.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.bean.FriendsListBean;
import com.yidao.platform.discovery.bean.FriendsShowBean;
import com.yidao.platform.discovery.model.FindDiscoveryObj;
import com.yidao.platform.discovery.model.PyqFindIdObj;
import com.yidao.platform.info.view.IViewMyPublishActivity;

import java.util.ArrayList;
import java.util.List;

public class MyPublishActivityPresenter {
    private IViewMyPublishActivity mView;

    public MyPublishActivityPresenter(IViewMyPublishActivity mView) {
        this.mView = mView;
    }

    /**
     * 获取朋友圈列表 特指我的发布obj
     */
    public void getFriendsList(FindDiscoveryObj findDiscoveryObj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .getFriendsList(findDiscoveryObj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<FriendsListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                    }

                    @Override
                    protected void onSuccess(FriendsListBean friendsListBean) {
                        if (friendsListBean.isStatus()) {
                            FriendsListBean.ResultBean result = friendsListBean.getResult();
                            if (result != null && result.getList().size() < result.getPageSize()) {  //所得数目< pageSize =>到底了
                                mView.loadMoreEnd(false);
                            } else {
                                mView.loadMoreComplete();
                            }

                            List<FriendsListBean.ResultBean.ListBean> list = result.getList();
                            ArrayList<FriendsShowBean> dataList = new ArrayList<>();
                            for (FriendsListBean.ResultBean.ListBean listBean : list) {
                                FriendsShowBean bean = new FriendsShowBean();
                                bean.setHeadImg(listBean.getHeadImg());
                                bean.setDeployName(listBean.getDeployName());
                                bean.setDeployTime(listBean.getDeployTime());
                                bean.setLikeAmount(listBean.getLikeAmount());
                                bean.setContent(listBean.getFind().getContent());
                                bean.setImgUrls((ArrayList<String>) listBean.getImgs());
                                bean.setFindId(String.valueOf(listBean.getFindId()));
                                bean.setLike(listBean.isIsLike());
                                dataList.add(bean);
                            }

                            if (result.getPageIndex() == 1) {  //page = 1时，表示初始列表值
                                mView.loadRecyclerData(dataList);
                            } else {
                                mView.loadMoreData(dataList);
                            }


                        }
                    }
                });
    }

    /**
     * 删除朋友圈
     */
    public void deleteFind(PyqFindIdObj obj, FriendsShowBean item) {
        RxHttpUtils
                .createApi(ApiService.class)
                .deleteFind(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        mView.deleteSuccess(item);
                    }
                });
    }
}
