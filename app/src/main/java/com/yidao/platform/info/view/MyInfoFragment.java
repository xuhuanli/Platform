package com.yidao.platform.info.view;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MyInfoFragment extends BaseFragment {

    @BindView(R.id.info_container)
    ConstraintLayout cl_container;
    @BindView(R.id.iv_login_success_info)
    ImageView ivLoginSuccessInfo;
    @BindView(R.id.tv_login_success_name_info)
    TextView tvLoginSuccessNameInfo;
    @BindView(R.id.tv_recent_read)
    TextView tvRecentlyRead;
    @BindView(R.id.tv_settings)
    TextView tvSettings;
    @BindView(R.id.linearLayout1)  //我的收藏
    LinearLayout mCollectionItem;
    @BindView(R.id.linearLayout2)  //我的发布
    LinearLayout mPublishItem;
    @BindView(R.id.linearLayout3)  //我的消息
    LinearLayout mMessageItem;
    @BindView(R.id.tv_login_hint)
    TextView tvLoginHint;
    private boolean login_state = true;

    @Override
    protected void initView() {
        if (login_state) {
            Glide.with(this)
                    .load(R.drawable.mypic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivLoginSuccessInfo);
        }
        //click 个人属性
        addDisposable(RxView.clicks(cl_container).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Intent intent = new Intent(getActivity(), PersonInfomationActivity.class);
                startActivity(intent);
            }
        }));
        //click 设置
        addDisposable(RxView.clicks(tvSettings).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }));
        //click 最近阅读
        addDisposable(RxView.clicks(tvRecentlyRead).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(o ->{
            Intent intent = new Intent(getActivity(), RecentlyReadActivity.class);
            startActivity(intent);
        }));
        //click 我的收藏
        addDisposable(RxView.clicks(mCollectionItem).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                Intent intent = new Intent(getActivity(), InfoMyCollectionActivity.class);
                startActivity(intent);
            }
        }));
        //click 我的发布
        addDisposable(RxView.clicks(mPublishItem).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Intent intent = new Intent(getActivity(), InfoMyPublishActivity.class);
                startActivity(intent);
            }
        }));
        //click 我的消息
        addDisposable(RxView.clicks(mMessageItem).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Intent intent = new Intent(getActivity(), InfoMyMessageActivity.class);
                startActivity(intent);
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_fragment_content;
    }

    @Override
    protected void initData() {

    }
}
