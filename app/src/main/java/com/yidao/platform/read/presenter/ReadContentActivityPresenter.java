package com.yidao.platform.read.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.read.adapter.ReadNewsDetailBean;
import com.yidao.platform.read.bean.HotCommentsBean;
import com.yidao.platform.read.bean.LastCommentsBean;
import com.yidao.platform.read.view.IViewReadContentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.yidao.platform.read.adapter.ReadNewsDetailBean.ITEM_COMMENTS;

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
    public void deleteMineComment(String commentId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("commentId", commentId);
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
        map.put("userId", userId);
        RxHttpUtils.createApi(ApiService.class)
                .pushHasRead(map)
                .compose(Transformer.switchSchedulers())
                .subscribe();
    }

    /**
     * 热门
     *
     * @param id
     */
    public void getHotComments(long id) {
        RxHttpUtils
                .createApi(ApiService.class)
                .getHotComments(id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<HotCommentsBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(HotCommentsBean hotCommentsBean) {
                        if (hotCommentsBean.isStatus()) {
                            mView.showHotComment(hotCommentsBean.getResult());
                        }
                    }
                });
    }

    /**
     * 最新
     */
    public void getLastestComments(long artId, long pageIndex, int pageSize) {
        RxHttpUtils
                .createApi(ApiService.class)
                .getLastComments(artId, pageIndex, pageSize)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<LastCommentsBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(LastCommentsBean lastCommentsBean) {
                        if (lastCommentsBean.isStatus()) {
                            List<LastCommentsBean.ResultBean.ListBean> list = lastCommentsBean.getResult().getList();
                            if (list != null && list.size() < lastCommentsBean.getResult().getPageSize()) {  //所得数目< pageSize =>到底了
                                mView.loadMoreEnd(false);
                            } else {
                                mView.loadMoreComplete();
                            }
                            ArrayList<ReadNewsDetailBean> dataList = new ArrayList<>();
                            for (LastCommentsBean.ResultBean.ListBean listBean : list) {
                                ReadNewsDetailBean bean = new ReadNewsDetailBean(ReadNewsDetailBean.ITEM_COMMENTS);
                                bean.setContent(listBean.getContent());
                                bean.setHeadImg(listBean.getCommentUserHeadImgUrl());
                                bean.setNickName(listBean.getNickname());
                                bean.setId(listBean.getId());
                                bean.setLikeCount(listBean.getLikeCount());
                                bean.setTimeSamp(listBean.getTime());
                                bean.setUserId(listBean.getUserId());
                                dataList.add(bean);
                            }
                            mView.loadMoreData(dataList);
                        }
                    }
                });
    }

    /**
     * 点赞评论
     * @param id
     * @param userId
     */
    public void userLikeComment(String id, String userId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .userLikeComment(id,userId)
                .compose(Transformer.switchSchedulers())
                .subscribe();
    }
}
