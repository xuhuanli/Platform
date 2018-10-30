package com.yidao.platform.info.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.RxHttpUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.events.RefreshInfoEvent;
import com.yidao.platform.info.model.EventChangeInfo;
import com.yidao.platform.info.model.EventTouXiangInfo;
import com.yidao.platform.info.model.MineInfoBean;
import com.yidao.platform.info.model.UserInfoBean;
import com.yidao.platform.info.presenter.MyInfoFragmentPresenter;
import com.yidao.platform.wallet.WalletActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MyInfoFragment extends Fragment implements IViewMineInfo {

    @BindView(R.id.info_container)
    ConstraintLayout cl_container;
    @BindView(R.id.iv_login_success_info)
    ImageView ivLoginSuccessInfo;
    @BindView(R.id.tv_login_success_name_info)
    TextView tvLoginSuccessNameInfo;
    @BindView(R.id.tv_login_hint)
    TextView tvLoginHint;
    @BindView(R.id.tv_recent_read)
    TextView tvRecentlyRead;
    @BindView(R.id.tv_settings)
    TextView tvSettings;
    //我的收藏
    @BindView(R.id.linearLayout1)
    LinearLayout mCollectionItem;
    //我的发布
    @BindView(R.id.linearLayout2)
    LinearLayout mPublishItem;
    //我的消息
    @BindView(R.id.linearLayout3)
    LinearLayout mMessageItem;
    @BindView(R.id.tv_collection_count)
    TextView tvCollectionCount;
    @BindView(R.id.tv_publish_count)
    TextView tvPublishCount;
    @BindView(R.id.tv_msg_count)
    TextView tvMsgCount;
    //钱包
    @BindView(R.id.tv_wallet)
    TextView tvWallet;
    private MyInfoFragmentPresenter mPresenter;
    private String userId;

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
            MyLogger.e("片段可见,加载数据");
            initData();
        }
    }

    /**
     * 添加订阅
     */
    private void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    private void initView() {
        EventBus.getDefault().register(this);
        mPresenter = new MyInfoFragmentPresenter(this);
        userId = IPreference.prefHolder.getPreference(getActivity()).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        //click 个人属性
        addDisposable(RxView.clicks(cl_container).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> startNewActivity(PersonInfomationActivity.class)));
        //click 设置
        addDisposable(RxView.clicks(tvSettings).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> startNewActivity(NewSettingsActivity.class)));
        //click 最近阅读
        addDisposable(RxView.clicks(tvRecentlyRead).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> startNewActivity(RecentlyReadActivity.class)));
        //click 我的收藏
        addDisposable(RxView.clicks(mCollectionItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> startNewActivity(InfoMyCollectionActivity.class)));
        //click 我的发布
        addDisposable(RxView.clicks(mPublishItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> startNewActivity(InfoMyPublishActivity.class)));
        //click 我的消息
        addDisposable(RxView.clicks(mMessageItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> startNewActivity(InfoMyMessageActivity.class)));
        //钱包
        addDisposable(RxView.clicks(tvWallet).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> startNewActivity(WalletActivity.class)));
    }

    private void startNewActivity(Class<?> clz) {
        Intent intent = new Intent(getActivity(), clz);
        startActivity(intent);
    }

    private int getLayoutId() {
        return R.layout.info_fragment_content;
    }

    private void initData() {
        mPresenter.qryUserById(userId);
    }


    @Override
    public void success(MineInfoBean data) {
        tvCollectionCount.setText(data.getResult().getMineCollect());
        tvPublishCount.setText(data.getResult().getMineFind());
        tvMsgCount.setText(data.getResult().getMineMessage());
    }

    @Override
    public void successInfo(UserInfoBean.ResultBean result) {
        mPresenter.getMineInfo(userId);
        Glide.with(this)
                .load(result.getHeadImgUrl())
                .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.info_head_p))
                .into(ivLoginSuccessInfo);
        tvLoginSuccessNameInfo.setText(result.getNickname());
        if (result.getIntroduction() != null) {
            tvLoginHint.setText(result.getIntroduction());
        }
    }

    @Override
    public void showError(String info) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void headImageEventBus(EventTouXiangInfo info) {
        if (info.isStatus) {
            Glide.with(this).load(info.path).apply(RequestOptions.placeholderOf(R.drawable.info_head_p)).into(ivLoginSuccessInfo);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeInfoEvent(EventChangeInfo event) {
        if (TextUtils.equals(event.title, "昵称")) {
            tvLoginSuccessNameInfo.setText(event.value);
        }
        if (TextUtils.equals(event.title, "简介")) {
            tvLoginHint.setText(event.value);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshInfoEvent event) {
        MyLogger.e("activity返回刷新");
        initData();
    }

    private void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        isViewCreated = isUIVisible = false;
        clearDisposable();
        RxHttpUtils.cancelAll();
        mUnbinder.unbind();
    }
}
