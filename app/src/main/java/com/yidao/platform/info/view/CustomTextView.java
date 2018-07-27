package com.yidao.platform.info.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yidao.platform.R;

public class CustomTextView extends ConstraintLayout {

    private TextView tvKey;
    private TextView tvValue;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_text_view, this, true);
        tvKey = view.findViewById(R.id.tv_key);
        tvValue = view.findViewById(R.id.tv_value);
        init(context, attrs, R.styleable.CustomTextView);
    }

    private void init(Context context, AttributeSet attrs, int[] res) {
        TypedArray ta = context.obtainStyledAttributes(attrs, res);
        String key = ta.getString(R.styleable.CustomTextView_key);
        int keyColor = ta.getColor(R.styleable.CustomTextView_keyColor, Color.BLACK);
        float keySize = ta.getDimension(R.styleable.CustomTextView_keySize, 0f);
        String value = ta.getString(R.styleable.CustomTextView_value);
        int valueColor = ta.getColor(R.styleable.CustomTextView_valueColor, Color.BLACK);
        float valueSize = ta.getDimension(R.styleable.CustomTextView_valueSize, 0f);
        ta.recycle();
        setKey(key);
        setKeyColor(keyColor);
        setKeySize(keySize);
        setValue(value);
        setValueColor(valueColor);
        setValueSize(valueSize);
    }

    public void setKey(CharSequence text) {
        tvKey.setText(text);
    }

    public String getKey() {
        return String.valueOf(tvKey.getText());
    }

    public void setKeyColor(int keyColor) {
        tvKey.setTextColor(keyColor);
    }

    public void setKeySize(float keySize) {
        tvKey.setTextSize(TypedValue.COMPLEX_UNIT_PX, keySize);
    }

    public String getValue() {
        return String.valueOf(tvValue.getText());
    }

    public void setValue(String value) {
        tvValue.setText(value);
    }

    public void setValueColor(int valueColor) {
        tvValue.setTextColor(valueColor);
    }

    public void setValueSize(float valueSize) {
        tvValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, valueSize);
    }
}
