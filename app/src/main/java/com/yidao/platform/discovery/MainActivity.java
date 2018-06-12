package com.yidao.platform.discovery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yidao.platform.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
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
