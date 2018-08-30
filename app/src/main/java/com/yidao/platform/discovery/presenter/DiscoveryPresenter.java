package com.yidao.platform.discovery.presenter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.R;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.bean.FriendsListBean;
import com.yidao.platform.discovery.bean.FriendsShowBean;
import com.yidao.platform.discovery.model.DianZanObj;
import com.yidao.platform.discovery.model.FindDiscoveryObj;
import com.yidao.platform.discovery.view.DiscoveryViewInterface;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryPresenter {

    private DiscoveryViewInterface mView;

    public DiscoveryPresenter(DiscoveryViewInterface fragment) {
        this.mView = fragment;
    }

    public void openCamera() {
        mView.openCamera();
    }

    public void setImage(Bitmap bitmap, ImageView imageView) {
        mView.showImage(bitmap, imageView);
    }

    public void openAlbum() {
        mView.openAlbum();
    }

    private String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 获取朋友圈列表新的接口
     */
    public void getFriendsList(int size) {
        RxHttpUtils
                .createApi(ApiService.class)
                .getFriendsList(size)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<FriendsListBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.setEnableLoadMore(true);
                        mView.setRefreshing(false);
                    }

                    @Override
                    protected void onSuccess(FriendsListBean friendsListBean) {
                        mView.setEnableLoadMore(true);
                        mView.setRefreshing(false);
                        if (friendsListBean.isStatus()) {
                            FriendsListBean.ResultBean result = friendsListBean.getResult();
                            if (result != null) {
                                if (result.getList().size() < Constant.PAGE_SIZE) {  //所得数目< pageSize =>到底了
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
                                    bean.setTimeStamp(listBean.getTimeStamp());
                                    dataList.add(bean);
                                }
                                mView.loadRecyclerData(dataList);
                            }else {
                                mView.loadMoreEnd(false);
                            }
                        }
                    }
                });
    }

    /**
     * 点赞
     */
    public void sendFindLike(DianZanObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .sendFindLike(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        MyLogger.e(data);
                    }
                });
    }

    /**
     * 取消点赞
     *
     * @param obj
     */
    public void cancelFindLike(DianZanObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .cancelFindLike(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        MyLogger.e(data);
                    }
                });
    }

    /**
     * 加载更多朋友圈
     *
     * @param pageSize
     * @param findId
     */
    public void qryFindHis(int pageSize, String findId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryFindHis(pageSize, findId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<FriendsListBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(FriendsListBean friendsListBean) {
                        mView.setEnableLoadMore(true);
                        mView.setRefreshing(false);
                        if (friendsListBean.isStatus()) {
                            FriendsListBean.ResultBean result = friendsListBean.getResult();
                            if (result != null) {
                                if (result.getList().size() == 0) {  //所得数目< pageSize =>到底了
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
                                    bean.setTimeStamp(listBean.getTimeStamp());
                                    dataList.add(bean);
                                }
                                mView.loadMoreData(dataList);
                            }else {
                                mView.loadMoreEnd(false);
                            }
                        }
                    }
                });
    }
}
