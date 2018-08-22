package com.yidao.platform.discovery.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.google.gson.Gson;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.discovery.bean.MyBottleBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyBottlePresenter {
    private IViewMyBottleActivity mView;

    public MyBottlePresenter(IViewMyBottleActivity mView) {
        this.mView = mView;
    }

    /**
     * 查询我的瓶子列表
     *
     * @param userId
     */
    public void qryBottleList(String userId, String index, String size) {
        RxHttpUtils
                .createApi(ApiService.class)
                .qryBottleList(userId, index, size)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.errorNet();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String errCode = jsonObject.getString("errCode");
                            switch (errCode) {
                                case "1000":
                                    String result = jsonObject.getString("result");
                                    MyBottleBean bean = new Gson().fromJson(result, MyBottleBean.class);
                                    if (bean.getList() != null && (bean.getList().size() < bean.getPage().getPageSize())) {
                                        mView.loadMoreEnd(false);
                                    } else {
                                        mView.loadMoreComplete();
                                    }
                                    List<MyBottleBean.ListBean> dataList = bean.getList();
                                    if (bean.getPage().getPageIndex() == 1) { //page = 1时，表示初始列表值
                                        mView.loadRecyclerData(dataList);
                                    } else { //上拉
                                        mView.loadMoreData(dataList);
                                    }
                                    break;
                                default:
                                    mView.errorNet();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 删除瓶子
     */
    public void deleteBottle(String bottleId, String userId) {
        RxHttpUtils
                .createApi(ApiService.class)
                .deleteBottle(bottleId, userId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String data) {

                    }
                });
    }
}
