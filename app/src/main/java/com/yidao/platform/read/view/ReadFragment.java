package com.yidao.platform.read.view;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xuhuanli.androidutils.toast.ToastUtil;
import com.yidao.bannerlibrary.ScaleInTransformer;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.read.adapter.ReadAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class ReadFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vp_banner)
    ViewPager mBannerViewpager;
    @BindView(R.id.xrecycleview)
    XRecyclerView mXRecyclerView;
    int[] imgRes = {R.drawable.a, R.drawable.b, R.drawable.c};

    @Override
    protected void initView() {
        initToolbar();
        initBanner();
        initRecyclerView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.read_fragment_content;
    }

    @Override
    protected void initData() {

    }

    private void initToolbar() {
        mToolbar.inflateMenu(R.menu.read_toolbar_menu);
        mToolbar.setTitle("hhh");
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.special_topic:
                        ToastUtil.showShort(getActivity(), "阅读");
                        break;
                    case R.id.search:
                        ToastUtil.showShort(getActivity(), "搜索");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initBanner() {
        mBannerViewpager.getParent().requestDisallowInterceptTouchEvent(true);
        //设置Page间间距
        mBannerViewpager.setPageMargin(20);
        //设置缓存的页面数量
        mBannerViewpager.setOffscreenPageLimit(3);
        mBannerViewpager.setAdapter(new PagerAdapter() {
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.banner_layout, container, false);
                ImageView imageView = view.findViewById(R.id.imageView);
                //imageView.setImageResource(imgRes[position]);
                Glide.with(getActivity()).load(getResources().getDrawable(imgRes[position])).into(imageView);
                TextView textView = view.findViewById(R.id.textView);
                textView.setText("放到哈大书法家卡登仕开发和电视");
                container.addView(view);
                onTouchViewPager(view, position);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return imgRes.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });

        mBannerViewpager.setCurrentItem(1);
        mBannerViewpager.setPageTransformer(true, new ScaleInTransformer());
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mXRecyclerView.setLayoutManager(layoutManager);
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add(String.valueOf(i));
        }
        ReadAdapter adapter = new ReadAdapter(dataList);
        mXRecyclerView.setAdapter(adapter);
    }

    private void onTouchViewPager(View view, int position) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
}
