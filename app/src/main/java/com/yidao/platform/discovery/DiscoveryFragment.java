package com.yidao.platform.discovery;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuhuanli.androidutils.toast.ToastUtil;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class DiscoveryFragment extends BaseFragment implements DiscoveryViewInterface, EasyPermissions.PermissionCallbacks {

    //权限的常量
    private static final int PERM_OPEN_ALBUM = 0;
    private static final int PERM_OPEN_CAMERA = 1;
    //startActivityForResult的常量
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int REQUEST_CHOOSE_PICTURE = 101;
    @BindView(R.id.btn_take_photo)
    Button mBtnPhoto;
    @BindView(R.id.btn_album)
    Button mBtnAlbum;
    @BindView(R.id.btn_bottle)
    Button mBtnBottle;
    @BindView(R.id.imageView3)
    ImageView img;
    private DiscoveryPresenter mPresenter;

    @Override
    protected void initView() {
        mPresenter = new DiscoveryPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_fragment_content;
    }

    @Override
    protected void initData() {
        addDisposable(RxView.clicks(mBtnPhoto).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                String[] perms = {Manifest.permission.CAMERA};
                if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                    mPresenter.openCamera();
                } else {
                    EasyPermissions.requestPermissions(DiscoveryFragment.this, getString(R.string.rationable_ask), PERM_OPEN_CAMERA, perms);
                }
            }
        }));
        addDisposable(RxView.clicks(mBtnAlbum).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                    mPresenter.openAlbum();
                } else {
                    EasyPermissions.requestPermissions(DiscoveryFragment.this, getString(R.string.rationable_ask), PERM_OPEN_ALBUM, perms);
                }
            }
        }));
    }

    @Override
    public void showImage(Bitmap bitmap, ImageView view) {
        Glide.with(this).load(bitmap).into(view);
    }

    @Override
    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void openAlbum() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        if (openAlbumIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(openAlbumIntent, REQUEST_CHOOSE_PICTURE);
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showShort(getActivity(), s);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    mPresenter.setImage(bitmap, img);
                    break;
                case REQUEST_CHOOSE_PICTURE:
                    if (data != null) {
                        mPresenter.handleImage(getActivity(), data, img);
                    } else {
                        showToast(getString(R.string.error_image));
                    }
                    break;
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getActivity(), "onPermissionsGranted", Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case PERM_OPEN_ALBUM:
                mPresenter.openAlbum();
                break;
            case PERM_OPEN_CAMERA:
                mPresenter.openCamera();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case PERM_OPEN_ALBUM:
                break;
            case PERM_OPEN_CAMERA:
                break;
        }
    }
}
