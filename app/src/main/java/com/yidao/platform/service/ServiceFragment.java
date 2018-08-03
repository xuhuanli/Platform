package com.yidao.platform.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.app.utils.InputFilterMinMax;
import com.yidao.platform.service.view.CustomBpItemView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class ServiceFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.nested_scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.tv_name)
    CustomBpItemView tvName;
    @BindView(R.id.tv_city)
    CustomBpItemView tvCity;
    @BindView(R.id.tv_industry)
    CustomBpItemView tvIndustry;
    @BindView(R.id.tv_occupation)
    CustomBpItemView tvOccupation;
    @BindView(R.id.tv_phone)
    CustomBpItemView tvPhone;
    @BindView(R.id.tv_user_count)
    CustomBpItemView tvUserCount;
    @BindView(R.id.tv_valuation)
    CustomBpItemView tvValuation;
    @BindView(R.id.et_enterprise_introduction)
    EditText etEnterpriseIntroduction;
    @BindView(R.id.tv_content_length)
    TextView tvContentLength;
    @BindView(R.id.et_sum_money)
    EditText etSumMoney;
    @BindView(R.id.et_equity)
    EditText etEquity;
    @BindView(R.id.checkbox)
    AppCompatCheckBox checkbox;
    @BindView(R.id.tv_bp_protocol)
    TextView tvProtocol;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @SuppressLint({"CheckResult", "DefaultLocale"})
    @Override
    protected void initView() {
        setEditTextInputType();
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(scrollView.getWindowToken(), 0); //强制隐藏键盘
        });
        addDisposable(RxTextView.textChanges(etEnterpriseIntroduction).subscribe(charSequence -> tvContentLength.setText(String.format("%d%s", charSequence.length(), "/100"))));
        setCheckBox();
        addDisposable(RxView.clicks(btnCommit).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> ToastUtils.showToast("提交到服务器")));
    }

    private void setEditTextInputType() {
        tvPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        tvUserCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        tvValuation.setInputType(InputType.TYPE_CLASS_NUMBER);
        etEquity.setFilters(new InputFilter[]{new InputFilterMinMax()});
    }

    private void setCheckBox() {
        String str = getString(R.string.bp_protocol);
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.showToast("跳转到协议page");
            }
        };
        builder.setSpan(clickableSpan,7,str.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#009ad6"));
        builder.setSpan(colorSpan, 7, str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvProtocol.setText(builder);
        tvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.service_fragment_bp_new;
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
