package com.yidao.platform.read.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.app.utils.ScreenUtil;
import com.yidao.platform.read.adapter.MultipleReadAdapter;
import com.yidao.platform.read.bean.CommonArticleBean;
import com.yidao.platform.read.bean.ReadNewsBean;
import com.yidao.platform.read.presenter.ReadFragmentPresenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.view.BannerViewPager;

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
     * 一页默认的条数
     */
    private static final int PAGE_SIZE = 6;
    /**
     * 请求的下一个页码
     */
    private int mNextRequestPage = 1;
    private MultipleReadAdapter mAdapter;
    private ReadFragmentPresenter mPresenter;

    @Override
    protected void initView() {
        initToolbar();
        mPresenter = new ReadFragmentPresenter(this);
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        addDisposable(RxView.clicks(mSelectItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> showChannelUI()));
        addDisposable(RxView.clicks(mIvSearch).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), SearchArticleActivity.class);
            startActivity(intent);
        }));
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
    }

    private void refresh() {
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        mPresenter.getMainArticleData();
    }

    /**
     * 往rv里面填充data
     */
    private void setData(boolean isRefresh, List<CommonArticleBean.ListBean> data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            //mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                //mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_fragment_content;
    }

    @Override
    protected void initData() {
        //第一次进入加载Banner
        mPresenter.getBannerData();
        //加载首页类目文章
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
        View view = getLayoutInflater().inflate(R.layout.read_mainpage_banner, (ViewGroup) mRecyclerView.getParent(), false);
        banner = view.findViewById(R.id.banner);
        initBanner();
        mPresenter.getBannerData();
        return view;
    }

    /**
     * init banner
     */
    private void initBanner() {
        banner.setImageLoader(new GlideImageLoader());
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setOffscreenPageLimit(5);
        BannerViewPager bannerViewPager = (BannerViewPager) banner.findViewById(com.youth.banner.R.id.bannerViewPager);
        bannerViewPager.setPageMargin(ScreenUtil.dip2px(getContext(), 16));
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bannerViewPager.getLayoutParams();
        lp.setMargins(ScreenUtil.dip2px(getContext(), 16), 0, ScreenUtil.dip2px(getContext(), 16), 0);
        bannerViewPager.setLayoutParams(lp);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    @Override
    public void showBanner(List<String> imageUrls, List<String> bannerTitles) {
        banner.setImages(imageUrls);
        banner.setBannerTitles(bannerTitles);
        banner.start();
    }

    @Override
    public void loadMoreFail() {
        mAdapter.loadMoreFail();
        ToastUtils.showToast(getString(R.string.connection_failed));
    }

    @Override
    public void loadMoreSuccess(CommonArticleBean ordinaryArticleBean) {
        List<CommonArticleBean.ListBean> list = ordinaryArticleBean.getList();
        setData(false, list);
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
    public void setRefreshing(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void showMainArticle(List<ReadNewsBean> dataList) {
        mAdapter = new MultipleReadAdapter(getActivity(), dataList);
        mAdapter.addHeaderView(getHeaderView());
        mAdapter.setOnLoadMoreListener(() -> loadMore(), mRecyclerView);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_item_more:
                    ReadNewsBean item = (ReadNewsBean) adapter.getItem(position);
                    Intent intent = new Intent(ReadFragment.this.getActivity(), ReadItemMoreActivity.class);
                    intent.putExtra("categoryId", String.valueOf(item.getCategoryId()));
                    startActivity(intent);
                    break;
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), ReadContentActivity.class);
            intent.putExtra("url", "https://news.163.com/");
            startActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);
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
        mNextRequestPage++;
        mPresenter.loadMoreData();
    }
}
