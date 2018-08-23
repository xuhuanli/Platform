package com.yidao.platform.discovery.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.IViewFriendsGroupDetail;
import com.yidao.platform.discovery.bean.CommentsItem;
import com.yidao.platform.discovery.bean.FindContentBean;
import com.yidao.platform.discovery.bean.FriendsShowBean;
import com.yidao.platform.discovery.bean.PyqCommentsBean;
import com.yidao.platform.discovery.model.PyqCommentsObj;
import com.yidao.platform.discovery.model.PyqFindIdObj;
import com.yidao.platform.discovery.model.QryFindContentObj;

import java.util.ArrayList;
import java.util.List;

public class FriendsGroupDetailPresenter {

    private IViewFriendsGroupDetail mView;

    public FriendsGroupDetailPresenter(IViewFriendsGroupDetail view) {
        this.mView = view;
    }

    public void deleteComment(CommentsItem commentsItem) {
        mView.update2DeleteComment(commentsItem);
    }

    /**
     * 获取朋友圈评论
     *
     * @param obj
     */
    public void qryFindComms(PyqFindIdObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryFindComms(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<PyqCommentsBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(PyqCommentsBean pyqCommentsBean) {
                        if (pyqCommentsBean.isStatus()) {
                            List<PyqCommentsBean.ResultBean> result = pyqCommentsBean.getResult();
                            ArrayList<CommentsItem> dataList = new ArrayList<>();
                            for (PyqCommentsBean.ResultBean resultBean : result) {
                                CommentsItem item = new CommentsItem();
                                item.setCommentId(resultBean.getId());
                                item.setMemberId(resultBean.getMemberId());
                                item.setContent(resultBean.getContent());
                                item.setDeployId(resultBean.getDeployId());
                                item.setDeployName(resultBean.getDeployName());
                                item.setOwnerId(resultBean.getOwnerId());
                                item.setOwnerName(resultBean.getOwnerName());
                                dataList.add(item);
                            }
                            mView.showComments(dataList);
                        } else {
                            MyLogger.e("加载评论失败");
                        }
                    }
                });
    }

    /**
     * 发布朋友圈评论
     *
     * @param obj
     */
    public void sendFindComm(PyqCommentsObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .sendFindComm(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<PyqCommentsBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(PyqCommentsBean pyqCommentsBean) {
                        mView.addCommentSuccess();
                    }
                });
    }

    /**
     * 查询朋友圈详情
     * @param findContentObj
     */
    public void qryFindContent(QryFindContentObj findContentObj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryFindContent(findContentObj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<FindContentBean>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(FindContentBean findContentBean) {
                        if (findContentBean.isStatus()) {
                            FindContentBean.ResultBean result = findContentBean.getResult();
                            FriendsShowBean showBean = new FriendsShowBean();
                            showBean.setLike(result.isIsLike());
                            showBean.setFindId(result.getId());
                            showBean.setContent(result.getContent());
                            showBean.setDeployTime(result.getCreateTime());
                            showBean.setDeployName(result.getUserName());
                            showBean.setHeadImg(result.getHeadImg());
                            showBean.setLikeAmount(result.getLikeAmount());
                            showBean.setImgUrls((ArrayList<String>) result.getImgs());
                            mView.showDetail(showBean);
                        }
                    }
                });
    }
}
