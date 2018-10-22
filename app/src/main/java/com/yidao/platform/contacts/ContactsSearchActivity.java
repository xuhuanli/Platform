package com.yidao.platform.contacts;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class ContactsSearchActivity extends BaseActivity implements IViewContactsSearch, View.OnClickListener {
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_search_history)
    TextView tvSearchHistory;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.tv_his_1)
    TextView tvHis1;
    @BindView(R.id.tv_his_2)
    TextView tvHis2;
    @BindView(R.id.tv_his_3)
    TextView tvHis3;
    @BindView(R.id.tv_his_4)
    TextView tvHis4;
    @BindView(R.id.tv_his_5)
    TextView tvHis5;
    @BindView(R.id.cl_search_his)
    ConstraintLayout clSearchHis;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    private String[] recentSearch = {"1", "2", "3", "4", "5"};
    private TextView[] tvArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initSearch();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> strings = new ArrayList<>();
        strings.add("搜索结果");
        mRecyclerView.setAdapter(new ContactsAdapter(R.layout.contacts_card, strings));
    }

    private void initSearch() {
        tvArray = new TextView[]{tvHis1, tvHis2, tvHis3, tvHis4, tvHis5};
        for (int i = 0; i < tvArray.length; i++) {
            if (!TextUtils.isEmpty(recentSearch[i])) {
                tvArray[i].setText(recentSearch[i]);  //在搜索一次后重新赋值
            } else {
                tvArray[i].setVisibility(View.INVISIBLE);
            }
            tvArray[i].setOnClickListener(this);
        }
        addDisposable(RxView.clicks(tvCancel).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
            finish();
        }));
        addDisposable(RxTextView.editorActionEvents(etSearch).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(textViewEditorActionEvent -> {
            if (textViewEditorActionEvent.actionId() == EditorInfo.IME_ACTION_SEARCH) {
                Toast.makeText(ContactsSearchActivity.this, "搜索中...", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contacts_search_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_his_1:
                solveClickEvent(tvHis1);
                break;
            case R.id.tv_his_2:
                solveClickEvent(tvHis2);
                break;
            case R.id.tv_his_3:
                solveClickEvent(tvHis3);
                break;
            case R.id.tv_his_4:
                solveClickEvent(tvHis4);
                break;
            case R.id.tv_his_5:
                solveClickEvent(tvHis5);
                break;
        }
    }

    private void solveClickEvent(TextView textView) {
        CharSequence text = textView.getText();
        etSearch.setText(text);
        //发起请求,填充数据
        clSearchHis.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
