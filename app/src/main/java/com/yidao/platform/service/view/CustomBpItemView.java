package com.yidao.platform.service.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidao.platform.R;

public class CustomBpItemView extends ConstraintLayout {

    private TextView tvKey;
    private EditText etValue;
    private ImageView ivClear;

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
        ivClear = view.findViewById(R.id.iv_clear);
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
        int leftSrcId = ta.getResourceId(R.styleable.CustomBpItemView_setDrawableRight,0);
        //右侧文字
        String value = ta.getString(R.styleable.CustomBpItemView_rightText);
        //右侧文字颜色
        int valueColor = ta.getColor(R.styleable.CustomBpItemView_rightColor, Color.BLACK);
        //右侧文字大小
        float valueSize = ta.getDimension(R.styleable.CustomBpItemView_rightSize, 0f);
        //右侧文字hint
        String valueHint = ta.getString(R.styleable.CustomBpItemView_rightHint);
        //右侧图片资源
        int rightSrcId = ta.getResourceId(R.styleable.CustomBpItemView_rightImg, 0);
        //是否展示右侧图片
        boolean showRight = ta.getBoolean(R.styleable.CustomBpItemView_showRightImg, false);
        int type = ta.getInt(R.styleable.CustomBpItemView_setInputType, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        ta.recycle();
        setKey(key);
        setKeyColor(keyColor);
        setKeySize(keySize);
        setValue(value);
        setValueColor(valueColor);
        setValueSize(valueSize);
        setLeftSrcId(leftSrcId);
        setRightSrcId(rightSrcId);
        isShowRight(showRight);
        setHint(valueHint);
        setInputType(type);
        initEditText();
    }

    private void initEditText() {
        boolean visible =false;
        Drawable drawableRight = etValue.getCompoundDrawables()[2];
        if (drawableRight == null)
            return;
        etValue.setCompoundDrawablesWithIntrinsicBounds(null, null, visible ? drawableRight : null, null);
    }

    public void setLeftSrcId(int rightSrcId) {
        tvKey.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightSrcId, 0);
    }

    public void setRightSrcId(int rightSrcId) {
        ivClear.setImageResource(rightSrcId);
    }

    public void isShowRight(boolean b) {
        ivClear.setVisibility(b ? VISIBLE : INVISIBLE);
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
        return String.valueOf(etValue.getText());
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

    @SuppressLint("ClickableViewAccessibility")
    public void setClearIconListener(){
        etValue.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (etValue.getRight() - etValue.getCompoundDrawables()[2].getBounds().width())) {
                    // your action here
                    etValue.setText("");
                    return true;
                }
            }
            return false;
        });
    }
}
