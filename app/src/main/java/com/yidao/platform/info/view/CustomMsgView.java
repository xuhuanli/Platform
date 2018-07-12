package com.yidao.platform.info.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidao.platform.R;

public class CustomMsgView extends LinearLayout {

    private TextView msgNum;
    private TextView msgCategory;
    private LinearLayout rootView;

    public CustomMsgView(Context context) {
        this(context, null);
    }

    public CustomMsgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomMsgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //initTypedArray(context, attrs);
        initView(context);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MsgText);
        String categoryText = typedArray.getString(R.styleable.MsgText_msg_text_category);
        String msgCount = typedArray.getString(R.styleable.MsgText_msg_text_count);
        typedArray.recycle();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_msg_view, this, true);
        msgNum = view.findViewById(R.id.tv_num);
        msgCategory = view.findViewById(R.id.tv_category);
        rootView = view.findViewById(R.id.ll_rootView);
    }

    public void setCategoryText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            msgCategory.setText(text);
        }
    }

    public void setMsgNumText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            msgNum.setText(text);
        }
    }
}
