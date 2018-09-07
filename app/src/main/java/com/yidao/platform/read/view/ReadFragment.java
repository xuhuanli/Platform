package com.yidao.platform.read.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.read.adapter.ErrorAdapter;
import com.yidao.platform.read.adapter.MultipleReadAdapter;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.presenter.ReadFragmentPresenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class ReadFragment extends BaseFragment implements IViewReadFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.iv_select_item)
    ImageView mSelectItem;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    /**
     * 轮播图
     */
    Banner banner;
    /**
     * 请求的下一个页码
     */
    private int mNextRequestPage = 1;
    private MultipleReadAdapter mAdapter;
    private ReadFragmentPresenter mPresenter;
    private ArrayList<ChannelBean.ResultBean> mChannelBean;
    private View headerView;

    @Override
    protected void initView() {
        initToolbar();
        mPresenter = new ReadFragmentPresenter(this);
        initRecyclerView();
        initSwipeRefreshLayout();
        headerView = getHeaderView();
        //refresh();
    }

    private void initToolbar() {
        addDisposable(RxView.clicks(mSelectItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> showChannelUI()));
        addDisposable(RxView.clicks(mIvSearch).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), SearchArticleActivity.class);
            startActivity(intent);
        }));
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
    }

    private void refresh() {
        mNextRequestPage = 1;
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        }
        mPresenter.getMainArticleData();
        //mPresenter.getListCategories();
        //mPresenter.getBannerData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_fragment_content;
    }

    @Override
    protected void initData() {
        mPresenter.getMainArticleData();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new CustomDecoration(getActivity(), 5, 0, 0));
    }

    /**
     * 右上角频道分类
     */
    private void showChannelUI() {
        Intent intent = new Intent(getActivity(), ItemChannelActivity.class);
        startActivity(intent);
    }

    private View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.read_mainpage_banner, null);
        banner = view.findViewById(R.id.banner);
        initBanner();
        return view;
    }

    /**
     * init banner
     */
    private void initBanner() {
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new GlideImageLoader());
        banner.setOffscreenPageLimit(5);
//        BannerViewPager bannerViewPager = banner.findViewById(com.youth.banner.R.id.bannerViewPager);
//        bannerViewPager.setPageMargin(ScreenUtil.dip2px(getContext(), 16));
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bannerViewPager.getLayoutParams();
//        lp.setMargins(ScreenUtil.dip2px(getContext(), 16), 0, ScreenUtil.dip2px(getContext(), 16), 0);
//        bannerViewPager.setLayoutParams(lp);
        banner.isAutoPlay(true);
        banner.setDelayTime(5000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    @Override
    public void showBanner(List<String> imageUrls, List<String> bannerTitles, List<String> artUrls, List<Long> artIds) {
        mPresenter.getListCategories();
        if (banner != null) {
            banner.setImages(imageUrls);
            banner.setBannerTitles(bannerTitles);
            banner.setOnBannerListener(position -> {
                Intent intent = new Intent(getActivity(), ReadContentActivity.class);
                String artUrl = artUrls.get(position);
                Long artId = artIds.get(position);
                if (artId == 200L) {
                    if (TextUtils.isEmpty(artUrl)) {
                        return;
                    } else {
                        Intent webIntent = new Intent(getActivity(), WebActivity.class);
                        webIntent.putExtra(Constant.STRING_ACTIVITY, artUrl);
                        MyLogger.e(artUrl);
                        startActivity(webIntent);
                    }
                }else {
                    intent.putExtra(Constant.STRING_URL, artUrl);
                    intent.putExtra(Constant.STRING_ART_ID, artId);
                    startActivity(intent);
                }
            });
            banner.start();
        }
    }

    @Override
    public void loadMoreFail() {
        mAdapter.loadMoreFail();
        ToastUtils.showToast(getString(R.string.connection_failed));
    }

    @Override
    public void setEnableLoadMore(boolean b) {
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(b);
        }
    }

    @Override
    public void loadMoreEnd(boolean b) {
        if (mAdapter != null) {
            mAdapter.loadMoreEnd(b);
        }
    }

    @Override
    public void loadMoreComplete() {
        if (mAdapter != null) {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadMoreData(ArrayList<ReadNewsBean> dataList) {
        if (mAdapter != null) {
            mAdapter.addData(dataList);
        }
    }

    @Override
    public void showError() {
        ErrorAdapter adapter = new ErrorAdapter(null);
        adapter.bindToRecyclerView(mRecyclerView);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
        ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.connection_failed);
        adapter.setEmptyView(view);
        adapter.setNewData(null);
    }

    @Override
    public void saveChannelData(ArrayList<ChannelBean.ResultBean> result) {
        //这里拿到了类目的ID和Name，没做本地化存储
        mChannelBean = result;
    }

    @Override
    public void setRefreshing(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void showMainArticle(List<ReadNewsBean> dataList) {
        if (mAdapter == null) {
            mAdapter = new MultipleReadAdapter(getActivity(), dataList);
            mAdapter.addHeaderView(headerView);
            mPresenter.getBannerData();
            mAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.tv_item_more:
                        ReadNewsBean item = (ReadNewsBean) adapter.getItem(position);
                        Intent intent = new Intent(ReadFragment.this.getActivity(), ReadItemMoreActivity.class);
                        long categoryId = item.getCategoryId();
                        for (ChannelBean.ResultBean resultBean : mChannelBean) {
                            if (categoryId == resultBean.getId()) {
                                intent.putExtra("categoryId", categoryId);
                                intent.putExtra("categoryName", resultBean.getName());
                                startActivity(intent);
                            }
                        }
                        break;
                }
            });
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                ReadNewsBean item = (ReadNewsBean) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), ReadContentActivity.class);
                intent.putExtra("url", item.getArticleContent());
                intent.putExtra(Constant.STRING_ART_ID, item.getId());
                startActivity(intent);
            });
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNewData(dataList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    private void loadMore() {
        mPresenter.loadMoreData(String.valueOf(mNextRequestPage), String.valueOf(Constant.PAGE_SIZE));
        mNextRequestPage++; //这里跟更多列表的上拉加载有一点不一样，因为首页这个两个接口，所以把++放在后面
    }
}
