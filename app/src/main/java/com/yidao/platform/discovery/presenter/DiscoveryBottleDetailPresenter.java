package com.yidao.platform.discovery.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.bean.CommentItem;
import com.yidao.platform.discovery.model.ReplyBottleObj;
import com.yidao.platform.discovery.view.DiscoveryBottleDetailInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class DiscoveryBottleDetailPresenter extends BasePresenter {
    private DiscoveryBottleDetailInterface mView;

    public DiscoveryBottleDetailPresenter(DiscoveryBottleDetailInterface view) {
        this.mView = view;
    }

    @Override
    public void deleteComment(CommentItem commentItem) {
        mView.update2DeleteComment(commentItem);
    }

    /**
     * 回复瓶子
     *
     * @param obj
     */
    public void replyBottle(ReplyBottleObj obj) {
        RxHttpUtils
                .createApi(ApiService.class)
                .replyBottle(obj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String errCode = jsonObject.getString("errCode");
                            switch (errCode) {
                                case "1000":
                                    mView.success();
                                    break;
                                default:
                                    mView.showErrorInfo(jsonObject.getString("info"));
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
