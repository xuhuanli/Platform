package com.yidao.platform.info.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MyInfoFragment extends BaseFragment {

    private boolean login_state = true;

    @BindView(R.id.iv_login_success_info)
    ImageView mCircleImg;
    @BindView(R.id.btn_settings_info)
    Button mBtnSettings;
    @BindView(R.id.linearLayout1)
    LinearLayout mMyCollection;
    @BindView(R.id.linearLayout2)
    LinearLayout mMyPuslish;
    @BindView(R.id.linearLayout3)
    LinearLayout mMyMsg;
    @BindView(R.id.tv_collection_count)
    TextView tvCollectionCount;
    @BindView(R.id.tv_publish_count)
    TextView tvPublishCount;
    @BindView(R.id.tv_msg_count)
    TextView tvMsgCount;

    @Override
    protected void initView() {
        if (login_state) {
            Glide.with(this)
                    .load(R.drawable.mypic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mCircleImg);
        }
        addDisposable(RxView.clicks(mBtnSettings).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        }));
        //click 我的收藏
        addDisposable(RxView.clicks(mMyCollection).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Intent intent = new Intent(getActivity(),InfoMyCollectionActivity.class);
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
