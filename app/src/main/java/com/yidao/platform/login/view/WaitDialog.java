package com.yidao.platform.login.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.yidao.platform.R;

public class WaitDialog extends Dialog {
    private TextView waitText;

    public WaitDialog(Context context) {
        super(context);//设置样式
        setCanceledOnTouchOutside(false);//按对话框以外的地方不起作用，按返回键可以取消对话框
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(R.layout.elemento_progress_splash);
        waitText = findViewById(R.id.tv_pb_message);
    }

    /**
     * 设置显示文字
     *
     * @param waitMsg
     */
    public void setText(CharSequence waitMsg) {
        waitText.setText(waitMsg);
    }

    /**
     * 设置文字
     *
     * @param resId
     */
    public void setText(int resId) {
        waitText.setText(resId);
    }
}
