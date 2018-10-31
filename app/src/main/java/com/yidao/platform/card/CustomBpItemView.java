package com.yidao.platform.card;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yidao.platform.R;


public class CustomBpItemView extends ConstraintLayout {

    private TextView tvKey;
    private EditText etValue;

    public CustomBpItemView(Context context) {
        this(context, null);
    }

    public CustomBpItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBpItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_bp_item_view, this, true);
        tvKey = view.findViewById(R.id.tv_key);
        etValue = view.findViewById(R.id.et_value);
        init(context, attrs, R.styleable.CustomBpItemView);
    }

    private void init(Context context, AttributeSet attrs, int[] res) {
        TypedArray ta = context.obtainStyledAttributes(attrs, res);
        //左侧文字
        String key = ta.getString(R.styleable.CustomBpItemView_leftText);
        //左侧文字颜色
        int keyColor = ta.getColor(R.styleable.CustomBpItemView_leftColor, Color.BLACK);
        //左侧文字大小
        float keySize = ta.getDimension(R.styleable.CustomBpItemView_leftSize, 0f);
        //左侧TextView Drawable
        int importantIcon = ta.getResourceId(R.styleable.CustomBpItemView_setImportantIcon, R.drawable.ic_star_non_6dp);
        //右侧文字
        String value = ta.getString(R.styleable.CustomBpItemView_rightText);
        //右侧文字颜色
        int valueColor = ta.getColor(R.styleable.CustomBpItemView_rightColor, Color.BLACK);
        //右侧文字大小
        float valueSize = ta.getDimension(R.styleable.CustomBpItemView_rightSize, 0f);
        int clearIconRef = ta.getResourceId(R.styleable.CustomBpItemView_setClearIcon, 0);
        //右侧文字hint
        String valueHint = ta.getString(R.styleable.CustomBpItemView_rightHint);
        int type = ta.getInt(R.styleable.CustomBpItemView_setInputType, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        ta.recycle();
        setKey(key);
        setKeyColor(keyColor);
        setKeySize(keySize);
        setValue(value);
        setValueColor(valueColor);
        setValueSize(valueSize);
        setImportantIconSrcId(importantIcon);
        setHint(valueHint);
        setInputType(type);
        initEditText(clearIconRef);
    }

    private void initEditText(int clearIcon) {
        etValue.clearFocus();
        setClearIconSrcId(0);
        setOnFocusChangedListener(clearIcon);
    }

    private void setOnFocusChangedListener(int clearIcon) {
        etValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                setClearIconSrcId(clearIcon);
                //edittext获取到焦点后设置右侧drawable监听
                setClearIconListener();
            } else {
                setClearIconSrcId(0);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setClearIconListener() {
        Drawable drawableRight = etValue.getCompoundDrawables()[2];
        etValue.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (etValue.getRight() - drawableRight.getBounds().width())) {
                    //计算drawableRight的坐标
                    etValue.setText("");
                    return true;
                }
            }
            return false;
        });
    }

    public void setImportantIconSrcId(int importantIconId) {
        tvKey.setCompoundDrawablesWithIntrinsicBounds(importantIconId, 0, 0, 0);
    }


    public void setClearIconSrcId(int clearIconSrcId) {
        etValue.setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIconSrcId, 0);
    }

    public void setKey(CharSequence text) {
        tvKey.setText(text);
    }

    public String getKey() {
        return tvKey.getText().toString();
    }

    public void setKeyColor(int keyColor) {
        tvKey.setTextColor(keyColor);
    }

    public void setKeySize(float keySize) {
        tvKey.setTextSize(TypedValue.COMPLEX_UNIT_PX, keySize);
    }

    public String getValue() {
        if (TextUtils.isEmpty(etValue.getText().toString().trim())) {
            return "";
        } else {
            return etValue.getText().toString().trim();
        }

    }

    public void setValue(String value) {
        etValue.setText(value);
    }

    public void setValueColor(int valueColor) {
        etValue.setTextColor(valueColor);
    }

    public void setValueSize(float valueSize) {
        etValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, valueSize);
    }

    public void setHint(String hint) {
        etValue.setHint(hint);
    }

    public void setInputType(int type) {
        etValue.setInputType(type);
    }
}
