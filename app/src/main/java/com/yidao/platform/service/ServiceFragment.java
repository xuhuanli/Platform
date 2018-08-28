package com.yidao.platform.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.app.utils.InputFilterMinMax;
import com.yidao.platform.app.utils.PhoneRegUtil;
import com.yidao.platform.service.model.BpObj;
import com.yidao.platform.service.presenter.ServiceFragmentPresenter;
import com.yidao.platform.service.view.CustomBpItemView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class ServiceFragment extends BaseFragment implements IViewServiceFragment {

    @BindView(R.id.nested_scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.tv_name)
    CustomBpItemView tvName;  //公司name
    @BindView(R.id.tv_city)
    CustomBpItemView tvCity;  //城市
    @BindView(R.id.tv_industry)
    CustomBpItemView tvIndustry;  //行业
    @BindView(R.id.tv_occupation)
    CustomBpItemView tvOccupation;  //职业
    @BindView(R.id.tv_phone)
    CustomBpItemView tvPhone;  //手机
    @BindView(R.id.tv_user_count)
    CustomBpItemView tvUserCount;  //人数
    @BindView(R.id.tv_valuation)
    CustomBpItemView tvValuation;  //估值
    @BindView(R.id.et_enterprise_introduction)
    EditText etEnterpriseIntroduction;  //企业概述
    @BindView(R.id.tv_content_length)
    TextView tvContentLength;  //概述字数
    @BindView(R.id.et_sum_money)
    EditText etSumMoney;  //金额
    @BindView(R.id.et_equity)
    EditText etEquity;   //股权
    @BindView(R.id.checkbox)
    AppCompatCheckBox checkbox;
    @BindView(R.id.tv_bp_protocol)
    TextView tvProtocol;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private ServiceFragmentPresenter mPresenter;
    private String userId;

    @SuppressLint({"CheckResult", "DefaultLocale"})
    @Override
    protected void initView() {
        setEditTextInputType();
        mPresenter = new ServiceFragmentPresenter(this);
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(scrollView.getWindowToken(), 0); //强制隐藏键盘
        });
        addDisposable(RxTextView.textChanges(etEnterpriseIntroduction).subscribe(charSequence -> tvContentLength.setText(String.format("%d%s", charSequence.length(), "/100"))));
        //setCheckBox();
        addDisposable(RxView.clicks(btnCommit).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            BpObj json = assemblyJson();
            if (json != null) {
                mPresenter.sendBpApply(json);
            }
        }));
    }

    private BpObj assemblyJson() {
        String name = tvName.getValue();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast("企业名称不能为空");
            return null;
        }
        String phoneNum = tvPhone.getValue();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.showToast("手机号码不能为空");
            return null;
        }
        if (!PhoneRegUtil.checkPhoneNumber(phoneNum)) {
            ToastUtils.showToast("请输入正确的手机号");
            return null;
        }
        BpObj bpObj = new BpObj(name, phoneNum, "1111");
        bpObj.setCityName(tvCity.getValue());
        bpObj.setCompanyEvaluate(tvValuation.getValue());
        bpObj.setDescription(etEnterpriseIntroduction.getText().toString().trim());
        bpObj.setExceptInvest(etSumMoney.getText().toString().trim());
        bpObj.setIndustry(tvIndustry.getValue());
        bpObj.setNumberOfPeople(tvUserCount.getValue());
        bpObj.setProfession(tvOccupation.getValue());
        bpObj.setTransferStock(etEquity.getText().toString().trim());
        return bpObj;
    }

    private void setEditTextInputType() {
        tvPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        tvUserCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        tvValuation.setInputType(InputType.TYPE_CLASS_NUMBER);
        etEquity.setFilters(new InputFilter[]{new InputFilterMinMax()});
    }

    @Override
    protected int getLayoutId() {
        return R.layout.service_fragment_bp_new;
    }


    @Override
    protected void initData() {
        userId = IPreference.prefHolder.getPreference(getContext()).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
    }

    @Override
    public void applySuccess() {
        tvName.setValue("");
        tvCity.setValue("");
        tvIndustry.setValue("");
        tvOccupation.setValue("");
        tvPhone.setValue("");
        tvUserCount.setValue("");
        tvValuation.setValue("");
        etEnterpriseIntroduction.setText("");
        etSumMoney.setText("");
        etEquity.setText("");
    }
}
