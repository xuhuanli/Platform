package com.yidao.platform.discovery.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.MyApplicationLike;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.ScreenUtil;
import com.yidao.platform.discovery.bean.PickBottleBean;
import com.yidao.platform.discovery.model.ThrowBottleObj;
import com.yidao.platform.discovery.presenter.BottleActivityPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class DiscoveryDriftingBottleActivity extends BaseActivity implements IViewBottleActivity {
    /**
     * 打开发布页请求code
     */
    private static final int PUSH_BOTTLE_REQUEST = 100;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cl_rootview)
    ConstraintLayout mRootView;
    @BindView(R.id.cl_container)
    ConstraintLayout mClContainer;
    @BindView(R.id.iv_discovery_push_bottle)
    ImageView mIvPushBottle;
    @BindView(R.id.iv_discovery_pull_bottle)
    ImageView mIvPullBottle;
    @BindView(R.id.iv_discovery_my_bottle)
    ImageView mIvMyBottle;
    @BindView(R.id.tv_ryg)
    TextView tv_ryg;
    @BindView(R.id.tv_jyg)
    TextView tv_jyg;
    @BindView(R.id.tv_my_bottle)
    TextView tv_my_bottle;
    /**
     * 瓶子的内容window
     */
    private PopupWindow mPopupWindow;
    /**
     * 飞船的window
     */
    private PopupWindow mSpaceShipWindow;
    private Animator mPushAnim;
    private Animator mPullAnim;
    private BottleActivityPresenter mPresenter;
    private String userId;
    private ImageView ivDriftBottle;
    private boolean isPickLimited;
    private boolean isThrowLimited;
    private String pickInfo;
    private String throwInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
    }

    private void initView() {
        initToolbar();
        //changeBackground(R.drawable.drift_bottle_has_bar);
        initStatusBar();
        mPresenter = new BottleActivityPresenter(this);
        //扔瓶子
        addDisposable(RxView.clicks(mIvPushBottle).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            if (isThrowLimited) {
                ToastUtils.showToast(throwInfo);
            } else {
                Intent intent = new Intent(DiscoveryDriftingBottleActivity.this, BottlePushActivity.class);
                startActivityForResult(intent, PUSH_BOTTLE_REQUEST);
            }
        }));
        //捡瓶子
        addDisposable(RxView.clicks(mIvPullBottle).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            if (isPickLimited) {
                ToastUtils.showToast(pickInfo);
            } else {
                mPresenter.pickBottle(userId);
            }
        }));
        /**
         * 我的瓶子
         */
        addDisposable(RxView.clicks(mIvMyBottle).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            startActivity(DiscoveryMyBottleActivity.class);
        }));
    }

    private void initStatusBar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        addDisposable(RxToolbar.navigationClicks(mToolbar).subscribe(o -> finish()));
    }

    /**
     * 加载捡瓶子anim
     */
    private void showBottlePopupWindow(View view) {
        mSpaceShipWindow = creatPopupWindow(view);
    }

    private void showPopupWindow(View view) {
        mPopupWindow = creatPopupWindow(view);
        mPopupWindow.setOutsideTouchable(false);
    }

    private PopupWindow creatPopupWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));// 设置背景图片
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(mClContainer, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    /**
     * 扔瓶子：1.先逆时针旋转3圈，并同时达到中间
     * 2.然后同时往上移动缩小直至透明
     *
     * @param view
     */
    private void setPushAnimator(View view, Animator.AnimatorListener listener) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        rotation.setDuration(2000);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenW = metrics.widthPixels / 2;
        int viewW = view.getWidth() / 2;
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "x", 0, (metrics.widthPixels / 2) - (ScreenUtil.dip2px(this, 26)));
        translationX.setDuration(2000);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "y", 300f);
        translationY.setDuration(1000);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        alpha.setDuration(1000);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.6f);
        scaleX.setDuration(1000);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.6f);
        scaleY.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotation).with(translationX).before(translationY).before(alpha).before(scaleX).before(scaleY);
        animatorSet.addListener(listener);
        animatorSet.start();
    }

    private void setPullAnimator(View view, Animator.AnimatorListener listener) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        alpha.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alpha);
        animatorSet.addListener(listener);
        animatorSet.start();
    }

    private void changeBackground(@RawRes @DrawableRes @Nullable Integer resourceId) {
        Glide.with(DiscoveryDriftingBottleActivity.this).load(resourceId).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mClContainer.setBackground(resource);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_drifting_bottle_activity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PUSH_BOTTLE_REQUEST && resultCode == BottlePushActivity.PUSH_SUCCESS) {
            //漂流瓶发布请求中，添加动画效果
            String bottleContent = data.getStringExtra("content");
            String bottleLabel = data.getStringExtra("label");
            ThrowBottleObj obj = new ThrowBottleObj();
            obj.setAuthorId(userId);
            obj.setContent(bottleContent);
            obj.setLabelId(MyApplicationLike.getLabelId(bottleLabel));
            obj.setType(0);
            mPresenter.throwBottle(obj);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPushAnim != null) {
            mPushAnim.removeAllListeners();
            mPushAnim = null;
        }
        if (mPullAnim != null) {
            mPullAnim.removeAllListeners();
            mPullAnim = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSpaceShipWindow != null && mSpaceShipWindow.isShowing()) {
            mSpaceShipWindow.dismiss();
            finish();
        }
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mSpaceShipWindow != null && mSpaceShipWindow.isShowing()) {
            mSpaceShipWindow.dismiss();
            finish();
        } else if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void pickAnim() {
        View view = LayoutInflater.from(DiscoveryDriftingBottleActivity.this).inflate(R.layout.drift_bottle_anim, mClContainer, false);
        DiscoveryDriftingBottleActivity.this.showBottlePopupWindow(view);
        ivDriftBottle = view.findViewById(R.id.iv_bottle);
        DiscoveryDriftingBottleActivity.this.setPullAnimator(ivDriftBottle, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mPullAnim = animation;
                mRootView.setVisibility(View.INVISIBLE);
                ActionBar actionBar = getSupportActionBar();
                actionBar.hide();
                changeBackground(R.drawable.drift_bottle_no_bar);
                ivDriftBottle.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //捡瓶子动画结束后，瓶子可以点击，点击后，飞船遮罩dismiss，对话popup展示
                ivDriftBottle.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @Override
    public void pushAnim() {
        View view = LayoutInflater.from(DiscoveryDriftingBottleActivity.this).inflate(R.layout.drift_bottle_anim, mClContainer, false);
        showBottlePopupWindow(view);
        ImageView IvDriftBottle = view.findViewById(R.id.iv_bottle);
        setPushAnimator(IvDriftBottle, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mPushAnim = animation;
                mRootView.setVisibility(View.INVISIBLE);
                ActionBar actionBar = getSupportActionBar();
                actionBar.hide();
                changeBackground(R.drawable.drift_bottle_no_bar);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRootView.setVisibility(View.VISIBLE);
                ActionBar actionBar = getSupportActionBar();
                actionBar.show();
                changeBackground(R.drawable.drift_bottle_has_bar);
                if (mSpaceShipWindow != null) {
                    mSpaceShipWindow.dismiss();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) { //emmmmm这个方法无法回调
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @Override
    public void dismissSpaceShipWindow() {
        if (mSpaceShipWindow != null) {
            mSpaceShipWindow.dismiss();
        }
    }

    @Override
    public void throwSuccess() {
        pushAnim();
    }

    @Override
    public void getOneBottle(PickBottleBean.ResultBean result) {
        pickAnim();
        ivDriftBottle.setOnClickListener(v -> {
            if (mSpaceShipWindow != null) {
                mSpaceShipWindow.dismiss();
            }
            View messageView = LayoutInflater.from(DiscoveryDriftingBottleActivity.this).inflate(R.layout.discovery_pull_bottle_popupwindow, mClContainer, false);
            showPopupWindow(messageView);
            loadDetailView(messageView, result);
        });
    }

    private void loadDetailView(View messageView, PickBottleBean.ResultBean result) {
        TextView tv_name = messageView.findViewById(R.id.tv_name);
        TextView tv_location = messageView.findViewById(R.id.tv_location);
        TextView tv_content = messageView.findViewById(R.id.tv_content);
        ImageView iv_head_portrait = messageView.findViewById(R.id.iv_head_portrait);
        tv_name.setText(result.getNickName());
        tv_location.setText(result.getAddress());
        tv_content.setText(result.getContent());
        Glide.with(DiscoveryDriftingBottleActivity.this).load(result.getImgUrl()).into(iv_head_portrait);
        Button backSea = messageView.findViewById(R.id.btn_backsea);
        addDisposable(RxView.clicks(backSea).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o1 -> {
            //点击扔回海里 飞船重现，对话框dismiss
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
            }
            if (mSpaceShipWindow != null) {
                mSpaceShipWindow.showAtLocation(mClContainer, Gravity.CENTER, 0, 0);
            }
            setPushAnimator(ivDriftBottle, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mPushAnim = animation;
                    ivDriftBottle.setEnabled(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mRootView.setVisibility(View.VISIBLE);
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.show();
                    changeBackground(R.drawable.drift_bottle_has_bar);
                    if (mSpaceShipWindow != null) {
                        mSpaceShipWindow.dismiss();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }));
        //回应
        Button btnReply = messageView.findViewById(R.id.btn_reply);
        addDisposable(RxView.clicks(btnReply).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o12 -> {
            Intent intent = new Intent(DiscoveryDriftingBottleActivity.this, DiscoveryBottleDetailActivity.class);
            intent.putExtra(Constant.STRING_BOTTLE_ID, result.getId() + "");
            intent.putExtra(Constant.STRING_SESSION_ID, "0");
            intent.putExtra(Constant.STRING_BOTTLE_PAGE_FROM, "1");
            startActivity(intent);
            mRootView.setVisibility(View.VISIBLE);
            ActionBar actionBar = getSupportActionBar();
            actionBar.show();
            changeBackground(R.drawable.drift_bottle_has_bar);
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
            }
        }));
    }

    /**
     * 扔瓶子上限
     *
     * @param info
     */
    @Override
    public void throwLimited(String info) {
        isThrowLimited = true;
        throwInfo = info;
        ToastUtils.showToast(info);
    }

    /**
     * 捡瓶子上限
     *
     * @param info
     */
    @Override
    public void countLimit(String info) {
        /*if (mSpaceShipWindow != null) {
            mSpaceShipWindow.dismiss();
        }
        mRootView.setVisibility(View.VISIBLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        changeBackground(R.drawable.drift_bottle_has_bar);
        isPickLimited = true;
        this.pickInfo = pickInfo;*/
        isPickLimited = true;
        this.pickInfo = info;
        ToastUtils.showToast(info);
    }

    @Override
    public void errorStatus(String info) {
        /*if (mSpaceShipWindow != null) {
            mSpaceShipWindow.dismiss();
        }
        mRootView.setVisibility(View.VISIBLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        changeBackground(R.drawable.drift_bottle_has_bar);*/
        isPickLimited = true;
        this.pickInfo = info;
        ToastUtils.showToast(info);
    }

    @Override
    public void netError() {
        if (mSpaceShipWindow != null) {
            mSpaceShipWindow.dismiss();
        }
        mRootView.setVisibility(View.VISIBLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        changeBackground(R.drawable.drift_bottle_has_bar);
    }
}
