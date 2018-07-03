package com.yidao.platform.read.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.jakewharton.rxbinding2.widget.TextViewBeforeTextChangeEvent;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.xuhuanli.androidutils.toast.ToastUtil;
import com.yidao.platform.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CommentDialogFragment extends DialogFragment {

    @BindView(R.id.btn_comment_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_comment_send)
    Button mBtnSend;
    @BindView(R.id.et_comment_content)
    EditText mEtContent;
    public CompositeDisposable mCompositeDisposable;
    private DialogFragmentDataCallback dataCallback;
    private InputMethodManager inputMethodManager;

    @Override
    public void onAttach(Context context) {
        if (!(getActivity() instanceof DialogFragmentDataCallback)) {
            throw new IllegalStateException("DialogFragment 所在的 activity 必须实现 DialogFragmentDataCallback 接口");
        }
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_comment_fragment_dialog, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        getDialog().setCanceledOnTouchOutside(true);
        if (window != null) {
            window.setBackgroundDrawableResource(R.color.colorWhite);
//            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.BottomDialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 2018/7/2 0002 填充内容
        fillContent();
        setSoftKeyboard();
        mEtContent.addTextChangedListener(new TextWatcher() {

                    private CharSequence temp;

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        temp = s;
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (temp.length() > 0) {
                    mBtnSend.setEnabled(true);
                    mBtnSend.setClickable(true);
                    mBtnSend.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    mBtnSend.setEnabled(false);
                    mBtnSend.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }
            }
        });
    }

    private void fillContent() {
        dataCallback = (DialogFragmentDataCallback) getActivity();
        if (dataCallback != null) {
            mEtContent.setText(dataCallback.getCommentText());
            mEtContent.setSelection(dataCallback.getCommentText().length());
            if (dataCallback.getCommentText().length() == 0) {
                mBtnSend.setEnabled(false);
                mBtnSend.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        }
    }

    private void setSoftKeyboard() {
        mEtContent.setFocusable(true);
        mEtContent.setFocusableInTouchMode(true);
        mEtContent.requestFocus();

        //为 commentEditText 设置监听器，在 DialogFragment 绘制完后立即呼出软键盘，呼出成功后即注销
        mEtContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    if (inputMethodManager.showSoftInput(mEtContent, 0)) {
                        mEtContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    @OnClick(R.id.btn_comment_cancel)
    public void cancelComment() {
        ToastUtil.showShort(getContext(), "cancel comment");
        mEtContent.setText("");
        dismiss();
    }

    @OnClick(R.id.btn_comment_send)
    public void sendComment() {
        ToastUtil.showShort(getContext(), "send comment");
        mEtContent.setText("");
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dataCallback.setCommentText(mEtContent.getText().toString());
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dataCallback.setCommentText(mEtContent.getText().toString());
        super.onCancel(dialog);
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
