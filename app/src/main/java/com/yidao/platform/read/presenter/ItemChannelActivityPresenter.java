package com.yidao.platform.read.presenter;


import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.utils.ToastUtils;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.view.IViewItemChannelActivity;

import java.util.List;

public class ItemChannelActivityPresenter {
    private IViewItemChannelActivity mView;

    public ItemChannelActivityPresenter(IViewItemChannelActivity view) {
        mView = view;
    }

    public void getListCategories() {
        RxHttpUtils.createApi(ApiService.class)
                .getListCategories()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<ChannelBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(ChannelBean channelBean) {
                        if (channelBean.isStatus()) {
                            List<ChannelBean.ResultBean> result = channelBean.getResult();
                            //mView.loadChannel(result);
                        }
                    }
                });
    }

    /**
     * 获取某一类目的文章列表
     * pageIndex 按服务端要求 从 1 开始
     */
    /*public void getCategoryArticleExt(String categoryId, String pageIndex, String pageSize) {
        HashMap<String, String> map = new HashMap<>();
        map.put("categoryId", categoryId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RxHttpUtils.createApi(ApiService.class)
                .getCategoryArticleExt(map)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<CategoryArticleExtBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        showError();
                    }

                    @Override
                    protected void onSuccess(CategoryArticleExtBean categoryArticleExtBean) {
                        if (categoryArticleExtBean.isStatus()) {
                            CategoryArticleExtBean.ResultBean result = categoryArticleExtBean.getResult();
                            if (result.getPageIndex() == 1) {  //page = 1时，表示初始列表值
                                List<CategoryArticleExtBean.ResultBean.ListBean> list = result.getList();
                                ArrayList<ReadNewsBean> dataList = new ArrayList<>();
                                for (CategoryArticleExtBean.ResultBean.ListBean listBean : list) {
                                    ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_ONE);
                                    readNewsBean.setType(listBean.getType());
                                    readNewsBean.setTitle(listBean.getTitle());
                                    readNewsBean.setReadAmount(listBean.getReadAmount());
                                    readNewsBean.setId(listBean.getId());
                                    readNewsBean.setHomeImg(listBean.getHomeImg());
                                    readNewsBean.setDeployTime(listBean.getDeployTime());
                                    dataList.add(readNewsBean);
                                }
                                mView.loadRecyclerData(dataList);
                            } else { //page !=1 表示上拉加载
                                List<CategoryArticleExtBean.ResultBean.ListBean> list = result.getList();
                                if (list != null && list.size() < result.getPageSize()) {  //所得数目< pageSize =>到底了
                                    mView.loadMoreEnd(false);
                                } else {
                                    mView.loadMoreComplete();
                                }
                                ArrayList<ReadNewsBean> dataList = new ArrayList<>();
                                for (CategoryArticleExtBean.ResultBean.ListBean listBean : list) {
                                    ReadNewsBean readNewsBean = new ReadNewsBean(ReadNewsBean.ITEM_ONE);
                                    readNewsBean.setType(listBean.getType());
                                    readNewsBean.setTitle(listBean.getTitle());
                                    readNewsBean.setReadAmount(listBean.getReadAmount());
                                    readNewsBean.setId(listBean.getId());
                                    readNewsBean.setHomeImg(listBean.getHomeImg());
                                    readNewsBean.setDeployTime(listBean.getDeployTime());
                                    dataList.add(readNewsBean);
                                }
                                mView.loadMoreData(dataList);
                            }
                        }
                    }
                });
    }*/
    private void showError() {
        ToastUtils.showToast("网络连接失败，请查看网络");
    }
}
