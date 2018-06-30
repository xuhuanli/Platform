package com.yidao.platform.info.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseFragment;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MyInfoFragment extends BaseFragment {

    private boolean login_state = false;

    @BindView(R.id.iv_login_success_info)
    ImageView mCircleImg;
    @BindView(R.id.btn_settings_info)
    Button mBtnSettings;

    @Override
    protected void initView() {
        if (login_state) {
            Glide.with(this)
                    .load(getResources().getDrawable(R.drawable.a))
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_fragment_content;
    }

    @Override
    protected void initData() {

    }
}
