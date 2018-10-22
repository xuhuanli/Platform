package com.yidao.platform.info.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidao.platform.R;

public class CustomTextView extends ConstraintLayout {

    private TextView tvKey;
    private TextView tvValue;
    private ImageView icon;
    private ImageView head;
    private Context context;

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
        icon = view.findViewById(R.id.imageView);
        head = view.findViewById(R.id.head);
        init(context, attrs, R.styleable.CustomTextView);
    }

    private void init(Context context, AttributeSet attrs, int[] res) {
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, res);
        String key = ta.getString(R.styleable.CustomTextView_key);
        int keyColor = ta.getColor(R.styleable.CustomTextView_keyColor, Color.BLACK);
        float keySize = ta.getDimension(R.styleable.CustomTextView_keySize, 0f);
        String value = ta.getString(R.styleable.CustomTextView_value);
        int valueColor = ta.getColor(R.styleable.CustomTextView_valueColor, Color.BLACK);
        float valueSize = ta.getDimension(R.styleable.CustomTextView_valueSize, 0f);
        boolean visible = ta.getBoolean(R.styleable.CustomTextView_arrowVisible, true);
        Drawable drawable = ta.getDrawable(R.styleable.CustomTextView_imageViewFrom);
        boolean isShowImage = ta.getBoolean(R.styleable.CustomTextView_imageVisible, false);
        ta.recycle();
        setKey(key);
        setKeyColor(keyColor);
        setKeySize(keySize);
        setValue(value);
        setValueColor(valueColor);
        setValueSize(valueSize);
        setArrowVisible(visible);
        setImageView(null, isShowImage);
    }

    public void setImageView(@Nullable String imageUrl, boolean isShow) {
        if (imageUrl != null) {
            if (isShow) {
                tvValue.setVisibility(INVISIBLE);
                Glide.with(context).load(imageUrl).into(head);
                head.setVisibility(VISIBLE);
            } else {
                tvValue.setVisibility(VISIBLE);
                head.setVisibility(INVISIBLE);
            }
        }
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

    public void setArrowVisible(boolean arrowVisible) {
        icon.setVisibility(arrowVisible ? VISIBLE : INVISIBLE);
    }
}
