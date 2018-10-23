package com.yidao.platform.info.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class NewChangeInfoActivity extends BaseActivity {
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.tb_ensure)
    TextView tbEnsure;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_editor)
    EditText etEditor;
    @BindView(R.id.tv_length)
    TextView tvLength;

    private int maxLengthOfEditText = 12;
    private String editHint = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initToolbar();
        initEditor();
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(o -> finish());
        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            tbTitle.setText(title);
            switch (title) {
                case "昵称修改":
                    maxLengthOfEditText = 12;
                    editHint = "修改昵称";
                    break;
                case "个人简介":
                    maxLengthOfEditText = 12;
                    editHint = "快来介绍自己吧~";
                    break;
                case "所属公司":
                    maxLengthOfEditText = 20;
                    editHint = "输入公司名称";
                    break;
            }
        }
        addDisposable(RxView.clicks(tbEnsure).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(etEditor.getWindowToken(), 0); //强制隐藏键盘
            Toast.makeText(NewChangeInfoActivity.this, "上传", Toast.LENGTH_SHORT).show();
        }));
    }

    @SuppressLint("DefaultLocale")
    private void initEditor() {
        String value = getIntent().getStringExtra("value");
        etEditor.setHint(editHint);
        etEditor.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthOfEditText)});
        etEditor.setText(value);
        etEditor.setSelection(value.length());
        addDisposable(RxTextView.textChanges(etEditor).subscribe(charSequence -> tvLength.setText(String.format("%d/%d", charSequence.length(), maxLengthOfEditText))));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_new_change_info;
    }
}
