package com.yidao.platform.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.bumptech.glide.Glide;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.UIUtil;
import com.yidao.platform.card.bean.UploadCardBean;
import com.yidao.platform.card.model.UploadCardObj;
import com.yidao.platform.info.model.TagBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author huyimin
 * 身份 信息页
 */

public class AuthenticateInfoActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.tv_count)
    TextView tv_count;

    //名片截图
    @BindView(R.id.iv_top)
    ImageView iv_top;
    //姓名
    @BindView(R.id.cb_name)
    CustomBpItemView cb_name;
    //公司
    @BindView(R.id.cb_company)
    CustomBpItemView cb_company;
    //职位
    @BindView(R.id.cb_post)
    CustomBpItemView cb_post;
    //手机
    @BindView(R.id.cb_phone)
    CustomBpItemView cb_phone;
    //邮箱
    @BindView(R.id.cb_email)
    CustomBpItemView cb_email;
    //公司地址
    @BindView(R.id.cb_companyaddr)
    CustomBpItemView cb_companyaddr;
    //身份证号
    @BindView(R.id.cb_number)
    CustomBpItemView cb_number;
    //微信号
    @BindView(R.id.cb_wechat)
    CustomBpItemView cb_wechat;

    //是否申请大师
    @BindView(R.id.cb_ismaster)
    CheckBox cb_ismaster;
    //所选标签
    private ArrayList selecteds;
    private String filePath = "";
    UploadCardObj mUploadCardObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePath = getIntent().getExtras().getString("filePath");
        selecteds = new ArrayList();
        mUploadCardObj = (UploadCardObj) getIntent().getSerializableExtra("mUploadCardObj");
        initToolbar();
        initView();
    }

    private void initView() {
        UIUtil.initRecyclerView(recyclerview, this, selecteds, tv_count);
        cb_name.setValue(mUploadCardObj.getName());
        cb_company.setValue(mUploadCardObj.getCompany());
        cb_post.setValue(mUploadCardObj.getPost());
        cb_phone.setValue(mUploadCardObj.getPhoneNum());
        cb_email.setValue(mUploadCardObj.getEmail());
        cb_companyaddr.setValue(mUploadCardObj.getCompanyAddr());
        cb_number.setValue(mUploadCardObj.getCertNum());
        if ("" != filePath) {
            File photoCut = new File(Environment.getExternalStorageDirectory(), filePath);
            if (photoCut.isFile()) {
                Glide.with(AuthenticateInfoActivity.this)
                        .load(photoCut)
                        .into(iv_top);
            }
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tb_title);
        title.setText("请填写您的认证信息");
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setElevation(0);
    }

    public static void startAuthenticateInfoActivity(Context context, String filePath, UploadCardObj mUploadCardObj) {
        Intent intent = new Intent(context, AuthenticateInfoActivity.class);
        intent.putExtra("filePath", filePath);
        intent.putExtra("mUploadCardObj", mUploadCardObj);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authenticateinfo;
    }

    public void submit(View view) {
        checkUserInfo();

//        AuthenticateSuccessActivity.startAuthenticateSuccActivity(this);
    }

    private void checkUserInfo() {
        boolean checked = cb_ismaster.isChecked();
//        isMaster	是否申请大咖(0 不申请,1申请)	number
        if (checked) {
            mUploadCardObj.setIsMaster(1);
            if (TextUtils.isEmpty(cb_wechat.getValue())) {
                Toast.makeText(this, "申请大咖时请填写微信号！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                mUploadCardObj.setWechatCode(cb_wechat.getValue());
            }
            if (selecteds.size() <= 0) {
                Toast.makeText(this, "申请大咖时请选择标签！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                mUploadCardObj.setMasterLabelId(selecteds);
            }
        } else {
            mUploadCardObj.setIsMaster(0);
        }
        mUploadCardObj.setName(cb_name.getValue());
        mUploadCardObj.setCompany(cb_company.getValue());
        mUploadCardObj.setPost(cb_post.getValue());
        mUploadCardObj.setPhoneNum(cb_phone.getValue());
        mUploadCardObj.setEmail(cb_email.getValue());
        mUploadCardObj.setCompanyAddr(cb_companyaddr.getValue());
        mUploadCardObj.setCertNum(cb_number.getValue());
        String id = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        mUploadCardObj.setUserId(Long.parseLong(id));
        mUploadCardObj.setBusinessId(0);
        mUploadCardObj.setCardUrl("");
        SubCardInfo();
    }

    /**
     * 上传用户信息
     */
    public void SubCardInfo() {
        RxHttpUtils.createApi(ApiService.class)
                .attestation(mUploadCardObj)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<UploadCardBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        String a = errorMsg;
                    }

                    @Override
                    protected void onSuccess(UploadCardBean mUploadCardBean) {


                        String errCode = mUploadCardBean.getErrCode();
                        if (errCode.equals("1000")) {
                            AuthenticateSuccessActivity.startAuthenticateSuccActivity(AuthenticateInfoActivity.this);
                        }

                    }
                });
    }

}
