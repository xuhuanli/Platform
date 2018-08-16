package com.yidao.platform.read.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.read.view.IViewReadContentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ReadContentActivityPresenter {
    private IViewReadContentActivity mView;

    public ReadContentActivityPresenter(IViewReadContentActivity view) {
        mView = view;
    }

    private void showError() {
        ToastUtils.showToast("网络连接失败，请查看网络");
    }

    /**
     * 发布评论
     *
     * @param artId
     * @param context
     * @param userId
     */
    public void pushComment(long artId, String context, String userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("artId", String.valueOf(artId));
        map.put("context", context);
        map.put("userId", userId);
        RxHttpUtils.createApi(ApiService.class)
                .pushComment(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            Boolean status = (Boolean) jsonObject.get("status");
                            if (status) {
                                mView.pushCommentSuccess();
                            } else {
                                mView.pushCommentFail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 删除我的文章评论
     *
     * @param commentId
     */
    public void deleteMineComment(long commentId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("commentId", String.valueOf(commentId));
        RxHttpUtils.createApi(ApiService.class)
                .delComment(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            Boolean status = (Boolean) jsonObject.get("status");
                            if (status) {
                                mView.deleteCommentSuccess();
                            } else {
                                mView.deleteCommentFail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
                    .subscribe();
        } else {
            RxHttpUtils.createApi(ApiService.class)
                    .unCollect(map)
                    .compose(Transformer.switchSchedulers())
                    .subscribe();
        }
    }

    /**
     * 点赞了文章后发给server
     *
     * @param isLike
     * @param artId
     * @param userId
     */
    public void sendLikeInfo(boolean isLike, long artId, String userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("artId", String.valueOf(artId));
        map.put("userId", String.valueOf(userId));
        if (isLike) {
            RxHttpUtils.createApi(ApiService.class)
                    .pushHasLike(map)
                    .compose(Transformer.switchSchedulers())
                    .subscribe();
        } else {
            RxHttpUtils.createApi(ApiService.class)
                    .unLike(map)
                    .compose(Transformer.switchSchedulers())
                    .subscribe();
        }
    }

    /**
     * 阅读了文章后发给server
     *
     * @param artId
     * @param userId
     */
    public void sendReadRecordInfo(long artId, String userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("artId", String.valueOf(artId));
        map.put("userId", String.valueOf(userId));
        RxHttpUtils.createApi(ApiService.class)
                .pushHasRead(map)
                .compose(Transformer.switchSchedulers())
                .subscribe();
    }
}
