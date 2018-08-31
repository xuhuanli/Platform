package com.yidao.platform.discovery.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.OssBean;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.presenter.EditorMessagePresenter;
import com.yidao.platform.events.RefreshDiscoveryEvent;
import com.yidao.platform.login.view.WaitDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class DiscoveryEditorMessageActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate, View.OnClickListener, DiscoveryEditorMessageInterface {

    @BindView(R.id.btn_cancel)
    TextView mBtnCancel;
    @BindView(R.id.btn_publish)
    TextView mBtnPublish;
    @BindView(R.id.et_editor)
    EditText mEtEditor;
    @BindView(R.id.tv_content_length)
    TextView mContentLength;
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private OssBean.ResultBean mOss;
    /**
     * 选择拍照
     */
    private static final int RC_CHOOSE_PHOTO = 1;
    /**
     * 照片预览
     */
    private static final int RC_PHOTO_PREVIEW = 2;
    /**
     * permission
     */
    private static final int PERMISSION_PHOTO_PICKER = 1;
    private EditorMessagePresenter mPresenter;
    private WaitDialog mProgressDialog;
    private ArrayList<String> picPathList = new ArrayList<>();
    private int uploadPicCounter = 0;  //上传到Oss成功的图片计数器
    private String userId;
    private SparseArray<String> picMap = new SparseArray<>();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //每上传成功1张，计数+1
                    uploadPicCounter++;
                    Bundle data = msg.getData();
                    int index = data.getInt("index");
                    String pathOnOss = data.getString("pathOnOss");
                    picMap.put(index, pathOnOss);
                    //当上传成功数 == 选中图片数时，告知服务器上传成功
                    if (picMap.size() == uploadPicCounter) {
                        String content = mEtEditor.getText().toString().trim();
                        MyLogger.e("picmap size = " + picMap.size() + ", picPathList size = " + picPathList.size());
                        for (int i = 0; i < picMap.size(); i++) {
                            picPathList.add(picMap.get(i));
                        }
                        mPresenter.sendMsg2Server(userId, content, picPathList);
                        //重置数量
                        uploadPicCounter = 0;
                        mProgressDialog.dismiss();
                    }else {
                        mBtnPublish.setEnabled(true);
                    }
                    break;
                case 1:
                    uploadPicFailed();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new EditorMessagePresenter(this);
        userId = IPreference.prefHolder.getPreference(DiscoveryEditorMessageActivity.this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);
        mPhotosSnpl.setDelegate(this);
        initView();
        String takePhotoPath = getIntent().getStringExtra("take_photo_path");
        if (takePhotoPath != null) {
            ArrayList<String> strings = new ArrayList<>();
            strings.add(takePhotoPath);
            mPhotosSnpl.setData(strings);
        }
        ArrayList<String> choose_picture_path = getIntent().getStringArrayListExtra("choose_picture_path");
        if (choose_picture_path != null) {
            mPhotosSnpl.setData(choose_picture_path);
        }
        mPresenter.getOssAccess();
    }

    private void initView() {
        addDisposable(RxTextView.textChanges(mEtEditor).subscribe(charSequence -> mContentLength.setText(String.format("%d%s", charSequence.length(), "/300"))));
        addDisposable(RxView.clicks(mBtnCancel).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> finish()));
        addDisposable(RxView.clicks(mBtnPublish).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> CompressPicAndPushToOss()));
    }

    private void CompressPicAndPushToOss() {
        //在压缩和发布之间不能再次点击
        mBtnPublish.setEnabled(false);
        mPresenter.getOssInstance(DiscoveryEditorMessageActivity.this, mOss.getAccessKeyId(), mOss.getAccessKeySecret(), mOss.getSecurityToken());
        ArrayList<String> mUpLoadPicList = mPhotosSnpl.getData();
        if (mUpLoadPicList.size() > 0) {
            for (int i = 0; i < mUpLoadPicList.size(); i++) {
                String thisPath = mUpLoadPicList.get(i);
                picMap.put(i, thisPath);
                if (!needCompress(Constant.NEED_COMPRESS_SIZE, thisPath)) { //<300K时，直传
                    mPresenter.uploadFile(i, thisPath, mHandler);
                } else {
                    int finalI = i;
                    Luban.with(DiscoveryEditorMessageActivity.this)
                            .load(thisPath)
                            .setTargetDir(getExternalCacheDir().getAbsolutePath())
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onSuccess(File file) {
                                    mPresenter.uploadFile(finalI, file.getAbsolutePath(), mHandler);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    //压缩失败就直传了
                                    mPresenter.uploadFile(finalI, thisPath, mHandler);
                                }
                            })
                            .launch();
                }
            }
        } else { //纯文本
            String content = mEtEditor.getText().toString().trim();
            if (!TextUtils.isEmpty(content)) {
                mPresenter.sendMsg2Server(userId, content, null);
            }else {
                mBtnPublish.setEnabled(true);
                ToastUtils.showToast("写点什么好呢....");
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_editor_message_activity;
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
        //拖动pic时候回调
    }

    @AfterPermissionGranted(PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "com.yidao.platform");
            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PERMISSION_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * @see #choicePhotoWrapper()
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PERMISSION_PHOTO_PICKER) {
            ToastUtils.showToast("您拒绝了「图片选择」所需要的相关权限!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_publish:

                break;
        }
    }

    /**
     * if 图片大小> leastCompressSize，return true else return false
     *
     * @param leastCompressSize
     * @param path
     * @return
     */
    private boolean needCompress(int leastCompressSize, String path) {
        if (leastCompressSize > 0) {
            File source = new File(path);
            return source.exists() && (source.length() > (leastCompressSize << 10));
        }
        return true;
    }

    @Override
    public void uploadPicFailed() {
        mBtnPublish.setEnabled(true);
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            ToastUtils.showToast("图片上传失败,请重试");
        }
    }

    @Override
    public void showDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new WaitDialog(this);
        }
        mProgressDialog.setText("图片上传中...");
        mProgressDialog.show();
    }

    @Override
    public void uploadSuccess() {
        mBtnPublish.setEnabled(true);
        EventBus.getDefault().post(new RefreshDiscoveryEvent());
        ToastUtils.showToast("发布成功");
        finish();
    }

    @Override
    public void saveOss(OssBean.ResultBean bean) {
        mOss = bean;
    }

    @Override
    public void uploadFailed() {
        picPathList.clear();
        uploadPicCounter = 0;
        mBtnPublish.setEnabled(true);
        picMap.clear();
    }
}
