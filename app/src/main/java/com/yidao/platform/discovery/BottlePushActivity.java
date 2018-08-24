package com.yidao.platform.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class BottlePushActivity extends BaseActivity {

    public static final int PUSH_SUCCESS = 100;
    @BindView(R.id.tv_bottle_cancel)
    TextView tvBottleCancel;
    @BindView(R.id.tv_bottle_push)
    TextView tvBottlePush;
    @BindView(R.id.et_bottle_content)
    EditText mBottleContent;
    @BindView(R.id.tv_content_length)
    TextView mContentLength;
    @BindView(R.id.tv_bottle_label)
    TextView mTvLabel;
    private OptionsPickerView<String> pickerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        addDisposable(RxTextView.textChanges(mBottleContent).subscribe(charSequence -> mContentLength.setText(String.format("%d%s", charSequence.length(), "/150"))));
        addDisposable(RxView.clicks(tvBottleCancel).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
        addDisposable(RxView.clicks(tvBottlePush).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            // TODO: 2018/7/14 0014 if push commentSuccess
            String content = mBottleContent.getText().toString().trim();
            String label = mTvLabel.getText().toString().trim();
            if (TextUtils.isEmpty(label) || "添加标签".equals(label)) {
                ToastUtils.showToast("请选择标签");
                return;
            }
            if (content.length() < 5) {
                ToastUtils.showToast("内容不能少于5个字");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("content", content);
            intent.putExtra("label", label);
            setResult(PUSH_SUCCESS, intent);
            finish();
        }));
        addDisposable(RxView.clicks(mTvLabel).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            // TODO: 2018/8/8 0008 展示标签选择器
            InputMethodManager mImm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mImm.hideSoftInputFromWindow(mTvLabel.getWindowToken(), 0);
            List<String> labelsList = Arrays.asList("商业计划书", "产品原型", "技术开发", "投融资需求", "资源对接", "路演/峰会", "项目投资", "项目评估");
            setWheelView(labelsList, (options1, options2, options3, v) -> {
                String s = labelsList.get(options1);
                mTvLabel.setText(s);
            });
        }));
    }

    private void setWheelView(List<String> labelsList, OnOptionsSelectListener listener) {
        pickerView = new OptionsPickerBuilder(this, listener).build();
        pickerView.setPicker(labelsList);
        pickerView.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_activity_push_bottle;
    }
}
