package com.yidao.platform.read.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.read.adapter.MultipleReadAdapter;
import com.yidao.platform.read.adapter.ReadNewsBean;
import com.yidao.platform.testpackage.bean.ApiService;
import com.yidao.platform.testpackage.bean.TestBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class ReadFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.iv_select_item)
    ImageView mSelectItem;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    /**
     * 一页默认的条数
     */
    private static final int PAGE_SIZE = 6;
    /**
     * 请求的下一个页码
     */
    private int mNextRequestPage = 1;
    private MultipleReadAdapter mAdapter;
    private BottomSheetDialog mChannelBottomSheetDialog;

    @Override
    protected void initView() {
        initToolbar();
        initBanner();
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
        mSwipeRefreshLayout.setOnRefreshListener(() -> refresh());
        //进去的时候就刷新一次
        //refresh();
    }

    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        RxHttpUtils.createApi(ApiService.class)
                .getGod()
                .compose(Transformer.<TestBean>switchSchedulers())
                .subscribe(new CommonObserver<TestBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        MyLogger.e(errorMsg);
                        mAdapter.setEnableLoadMore(true);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    protected void onSuccess(TestBean testBean) {
                        mAdapter.setEnableLoadMore(true);
                        mSwipeRefreshLayout.setRefreshing(false);
                        //setData(true, testBean.getData());
                        MyLogger.d(testBean.getData().get(0).getStyleName());
                    }
                });
    }

    /**
     * 往rv里面填充data
     */
    private void setData(boolean isRefresh, List<ReadNewsBean> data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
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

    }

    private void showChannelUI() {
        Intent intent = new Intent(getActivity(), ItemChannelActivity.class);
        startActivity(intent);
    }

    private void initBanner() {
        ArrayList<String> list = new ArrayList<>();
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        banner.setImageLoader(new GlideImageLoader());
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //设置banner动画效果
        banner.setBannerAnimation(com.youth.banner.Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        ArrayList<String> titles = new ArrayList<>();
        titles.add("带");
        titles.add("带");
        titles.add("大");
        titles.add("师");
        titles.add("兄");
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        List<ReadNewsBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new ReadNewsBean(ReadNewsBean.ITEM_ONE));
            list.add(new ReadNewsBean(ReadNewsBean.ITEM_TWO));
        }
        mAdapter = new MultipleReadAdapter(getActivity(), list);
        mAdapter.setOnLoadMoreListener(() -> loadMore(), mRecyclerView);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_item_more:
                    Intent intent = new Intent(getActivity(), ReadItemMoreActivity.class);
                    startActivity(intent);
                    break;
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), ReadContentActivity.class);
            intent.putExtra("url", "http://news.163.com/");
            startActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    private void loadMore() {
        // TODO: 2018/7/9 0009 加载更多
    }
}
