package com.yidao.platform.info.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.FileUtil;
import com.yidao.platform.app.utils.OssUploadUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import io.reactivex.functions.Consumer;
import pub.devrel.easypermissions.EasyPermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PersonInfomationActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;
    @BindView(R.id.head_portrait)
    ImageView headPortrait;
    @BindView(R.id.rl_head)
    ConstraintLayout rlHead;
    @BindView(R.id.tv_id)
    CustomTextView tvUserId;
    @BindView(R.id.tv_nike_name)
    CustomTextView tvNikeName;
    @BindView(R.id.tv_location)
    CustomTextView tvPhoneNumber;
    @BindView(R.id.tv_status)
    CustomTextView tvStatus;
    private BottomSheetDialog mHeadPhotoDialog;
    private TextView mTvCamera;
    private TextView mTvGallery;
    private TextView mTvCancel;
    //权限的常量
    private static final int PERM_OPEN_ALBUM = 0;
    private static final int PERM_OPEN_CAMERA = 1;
    private File app_photo;
    private Uri uriForFile;
    //startActivityForResult的常量
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int REQUEST_CHOOSE_PICTURE = 101;
    private static final int REQUEST_CHANGE_INFO = 102;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        EventBus.getDefault().register(this);
    }

    @SuppressLint("CheckResult")
    private void initView() {
        RxToolbar.navigationClicks(toolbarInfo).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish());
        Glide.with(this).load(R.drawable.info_head_p).into(headPortrait);
        RxView.clicks(rlHead).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> setDialog());
        RxView.clicks(tvNikeName).throttleFirst(Constant.THROTTLE_TIME,TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(PersonInfomationActivity.this, ChangeInfoActivity.class);
            startActivityForResult(intent,REQUEST_CHANGE_INFO);
        });
    }

    private void setDialog() {
        mHeadPhotoDialog = new BottomSheetDialog(this);
        mHeadPhotoDialog.setCanceledOnTouchOutside(true);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_head_photo_dialog, null);
        mTvCamera = view.findViewById(R.id.tv_camera);
        mTvGallery = view.findViewById(R.id.tv_gallery);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        mHeadPhotoDialog.setContentView(view);
        mHeadPhotoDialog.show();
        mTvCamera.setOnClickListener(this);
        mTvGallery.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_personal_profile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_camera:
                //拍照function
                addDisposable(RxView.clicks(mTvCamera).subscribe(o -> {
                    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                    if (EasyPermissions.hasPermissions(PersonInfomationActivity.this, perms)) {
                        openCamera();
                    } else {
                        EasyPermissions.requestPermissions(PersonInfomationActivity.this, getString(R.string.rationable_ask), PERM_OPEN_CAMERA, perms);
                    }
                }));
                break;
            case R.id.tv_gallery:
//相册function
                addDisposable(RxView.clicks(mTvGallery).subscribe(o -> {
                    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                    if (EasyPermissions.hasPermissions(this, perms)) {
                        openAlbum();
                    } else {
                        EasyPermissions.requestPermissions(this, getString(R.string.rationable_ask), PERM_OPEN_ALBUM, perms);
                    }
                }));
                break;
            case R.id.tv_cancel:
                mHeadPhotoDialog.cancel();
                break;
        }
    }

    private void openCamera() {
        app_photo = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/" + "IMG_" + FileUtil.formateTime() + ".jpg");
        if (!app_photo.getParentFile().exists()) {
            app_photo.getParentFile().mkdirs();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uriForFile = FileProvider.getUriForFile(this, "com.yidao.platform.file_provider", app_photo);
        } else {
            uriForFile = Uri.fromFile(app_photo);
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openAlbum() {
        choicePhotoWrapper(this);
    }

    private void choicePhotoWrapper(Context context) {
        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(context)
                .maxChooseCount(1) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();
        startActivityForResult(photoPickerIntent, REQUEST_CHOOSE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    refreshAlbum(this, app_photo);
                    String path = app_photo.getAbsolutePath();
                    Glide.with(PersonInfomationActivity.this).load(path).into(headPortrait);
                    //不刷新相册 拍照的图片不会在相册显示
                    upload2Oss(path);
                    break;
                case REQUEST_CHOOSE_PICTURE:
                    ArrayList<String> photos = BGAPhotoPickerActivity.getSelectedPhotos(data);
                    Glide.with(PersonInfomationActivity.this).load(photos.get(0)).into(headPortrait);
                    if (photos.size() == 1) {
                        upload2Oss(photos.get(0));
                    }
                    break;
            }
        }
    }

    private void upload2Oss(String path) {
        OssUploadUtil ossUploadUtil = new OssUploadUtil(this);
        if (!needCompress(Constant.NEED_COMPRESS_SIZE, path)) {
            //<300K时，直传
            ossUploadUtil.uploadFile(path, null);
        } else {
            //>300K时，进行压缩处理
            Luban.with(this)
                    .load(path)
                    .setTargetDir(getExternalCacheDir().getAbsolutePath())
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            ossUploadUtil.uploadFile(path, null);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast("图片上传失败");
                        }
                    })
                    .launch();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(String message) {
        if (message.equals("success")) {
            ToastUtils.showToast("头像上传成功");
        } else if (message.equals("fail")) {
            ToastUtils.showToast("头像上传失败");
        }
    }

    private boolean needCompress(int leastCompressSize, String path) {
        if (leastCompressSize > 0) {
            File source = new File(path);
            return source.exists() && (source.length() > (leastCompressSize << 10));
        }
        return true;
    }

    private void refreshAlbum(Context context, File file) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case PERM_OPEN_ALBUM:
                if (perms.size() == 2) {
                    openAlbum();
                }
                break;
            case PERM_OPEN_CAMERA:
                //Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                if (perms.size() == 2) {
                    openCamera();
                }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
