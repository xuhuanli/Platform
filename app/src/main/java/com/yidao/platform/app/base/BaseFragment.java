package com.yidao.platform.app.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.library.RxHttpUtils;
import com.yidao.platform.app.utils.MyLogger;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {

    private boolean isViewCreated;
    private boolean isUIVisible;
    private Unbinder mUnbinder;
    private CompositeDisposable mCompositeDisposable;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    protected abstract void initView();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //在Fragment的View创建并且可见后加载当前fragment内容
        if (isViewCreated && isUIVisible) {
            MyLogger.d("片段可见,加载数据");
            initData();
            //prevent repeat load data ，so set the value = false again.
            isViewCreated = isUIVisible = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isViewCreated = isUIVisible = false;
        clearDisposable();
        RxHttpUtils.clearAllCompositeDisposable();
        mUnbinder.unbind();
    }

    /**
     * 添加订阅
     */
    protected void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    private void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    protected abstract int getLayoutId();

    protected abstract void initData();
}
