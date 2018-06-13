package com.yidao.platform.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.hotfix.BugClass;
import com.yidao.platform.read.view.ReadActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_jump2read)
    Button mBtnJump2Read;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        jump2Read();
    }

    private void jump2Read() {
        RxView.clicks(mBtnJump2Read)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        startActivity(new Intent(MainActivity.this, ReadActivity.class));
                    }
                });
    }

    public void onClick(View view) {
        new BugClass().bug();
        /*HttpRetrofit.getHttpRetrofit().creatRetrofit()
                .create(ApiService.class)
                .getBlog(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DataObserver<Reception>() {
                    @Override
                    protected void onSuccess(Reception data) {
                        MyLogger.d(data.getMsg());
                    }

                    @Override
                    protected void onError(String msg) {
                        MyLogger.d(msg);
                    }
                });*/
    }
}
