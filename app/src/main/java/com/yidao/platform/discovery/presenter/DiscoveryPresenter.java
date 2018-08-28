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
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.DiscoveryViewInterface;
import com.yidao.platform.discovery.bean.FriendsListBean;
import com.yidao.platform.discovery.bean.FriendsShowBean;
import com.yidao.platform.discovery.model.DianZanObj;
import com.yidao.platform.discovery.model.FindDiscoveryObj;

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

    public void handleImage(Context context, Intent data, ImageView imageView) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果document类型Uri 通过docId处理
            String docId = DocumentsContract.getDocumentId(uri);
            if (uri.getAuthority().equals("com.android.providers.media.documents")) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if (uri.getAuthority().equals("com.android.providers.download.documents")) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //content类型Uri 普通方式处理
            imagePath = getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //file类型Uri 直接获取图片路径
            imagePath = uri.getPath();
        }
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mView.showImage(bitmap, imageView);
        } else {
            mView.showToast(context.getString(R.string.failed_to_get_image));
        }
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
     * 获取朋友圈列表
     */
    public void getFriendsList(FindDiscoveryObj findDiscoveryObj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .getFriendsList(findDiscoveryObj)
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
                                bean.setTimeStamp(listBean.getTimeStamp());
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
}
