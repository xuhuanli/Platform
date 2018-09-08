package com.yidao.platform.discovery.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.model.DeletePyqObj;
import com.yidao.platform.discovery.model.DianZanObj;
import com.yidao.platform.discovery.view.IViewFriendsGroupDetail;
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

    /**
     * 删除朋友圈评论
     * @param obj
     */
    public void deleteComment(DeletePyqObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .deleteFindComm(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String data) {
                        mView.update2DeleteComment(data);
                    }
                });
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
                            showBean.setTimeStamp(result.getTimeStamp());
                            showBean.setLikeAmount(result.getLikeAmount());
                            showBean.setImgUrls((ArrayList<String>) result.getImgs());
                            showBean.setDeployId(result.getUserId());
                            mView.showDetail(showBean);
                        }
                    }
                });
    }

    /**
     * 取消朋友圈点赞
     * @param dianZanObj
     */
    public void cancelFindLike(DianZanObj dianZanObj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .cancelFindLike(dianZanObj)
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
     * 朋友圈点赞
     * @param dianZanObj
     */
    public void sendFindLike(DianZanObj dianZanObj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .sendFindLike(dianZanObj)
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
