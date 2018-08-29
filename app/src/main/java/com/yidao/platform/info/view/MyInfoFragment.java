package com.yidao.platform.info.view;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.info.model.EventChangeInfo;
import com.yidao.platform.info.model.EventTouXiangInfo;
import com.yidao.platform.info.model.MineInfoBean;
import com.yidao.platform.info.model.UserInfoBean;
import com.yidao.platform.info.presenter.MyInfoFragmentPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class MyInfoFragment extends BaseFragment implements IViewMineInfo {

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
    private MyInfoFragmentPresenter mPresenter;
    private String userId;

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mPresenter = new MyInfoFragmentPresenter(this);
        userId = IPreference.prefHolder.getPreference(getActivity()).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        //click 个人属性
        addDisposable(RxView.clicks(cl_container).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), PersonInfomationActivity.class);
            startActivity(intent);
        }));
        //click 设置
        addDisposable(RxView.clicks(tvSettings).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }));
        //click 最近阅读
        addDisposable(RxView.clicks(tvRecentlyRead).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), RecentlyReadActivity.class);
            startActivity(intent);
        }));
        //click 我的收藏
        addDisposable(RxView.clicks(mCollectionItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), InfoMyCollectionActivity.class);
            startActivity(intent);
        }));
        //click 我的发布
        addDisposable(RxView.clicks(mPublishItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), InfoMyPublishActivity.class);
            startActivity(intent);
        }));
        //click 我的消息
        addDisposable(RxView.clicks(mMessageItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), InfoMyMessageActivity.class);
            startActivity(intent);
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_fragment_content;
    }

    @Override
    protected void initData() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(EventTouXiangInfo info) {
        if (info.isStatus) {
            Glide.with(this).load(info.path).into(ivLoginSuccessInfo);
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
}
