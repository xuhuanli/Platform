package com.yidao.platform.discovery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.allen.library.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.app.utils.MyLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import io.reactivex.functions.Consumer;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class DiscoveryFragment extends BaseFragment implements DiscoveryViewInterface, EasyPermissions.PermissionCallbacks, BGANinePhotoLayout.Delegate, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener {

    //权限的常量
    private static final int PERM_OPEN_ALBUM = 0;
    private static final int PERM_OPEN_CAMERA = 1;
    private static final int PERM_PREVIEW_PHOTO = 2;
    //startActivityForResult的常量
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int REQUEST_CHOOSE_PICTURE = 101;
    @BindView(R.id.btn_take_photo)
    ImageView mBtnPhoto;
    @BindView(R.id.btn_album)
    ImageView mBtnAlbum;
    @BindView(R.id.btn_bottle)
    ImageView mBtnBottle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private DiscoveryPresenter mPresenter;
    private MomentAdapter mAdapter;
    private BGANinePhotoLayout mCurrentClickNpl;
    private Uri uriForFile;
    private File app_photo;

    @Override
    protected void initView() {
        mPresenter = new DiscoveryPresenter(this);
        initToolbar();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MomentAdapter(mRecyclerView);
        setDataToAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        mRecyclerView.addOnScrollListener(new BGARVOnScrollListener(getActivity()));
    }

    private void setDataToAdapter() {
        List<Moment> moments = new ArrayList<>();

        moments.add(new Moment("1张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered1.png"))));
        moments.add(new Moment("2张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered2.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered3.png"))));
        moments.add(new Moment("9张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered19.png"))));
        moments.add(new Moment("5张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png"))));
        moments.add(new Moment("3张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered4.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered5.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered6.png"))));
        moments.add(new Moment("8张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png"))));
        moments.add(new Moment("4张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered7.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered8.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered9.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered10.png"))));
        moments.add(new Moment("2张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered2.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered3.png"))));
        moments.add(new Moment("3张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered4.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered5.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered6.png"))));
        moments.add(new Moment("4张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered7.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered8.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered9.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered10.png"))));
        moments.add(new Moment("9张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered19.png"))));
        moments.add(new Moment("1张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered1.png"))));
        moments.add(new Moment("5张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png"))));
        moments.add(new Moment("6张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png"))));
        moments.add(new Moment("7张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png"))));
        moments.add(new Moment("8张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png"))));

        mAdapter.setData(moments);
    }

    private void initToolbar() {
        mToolbar.inflateMenu(R.menu.discovery_toolbar_menu);
        mToolbar.setTitle(R.string.discovery);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.friends_group:
                        Intent intent = new Intent(getActivity(), DiscoveryEditorMessageActivity.class);
                        startActivity(intent);
                        ToastUtils.showToast("好友圈");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_fragment_content;
    }

    @Override
    protected void initData() {
        //拍照function
        addDisposable(RxView.clicks(mBtnPhoto).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                    mPresenter.openCamera();
                } else {
                    EasyPermissions.requestPermissions(DiscoveryFragment.this, getString(R.string.rationable_ask), PERM_OPEN_CAMERA, perms);
                }
            }
        }));
        //相册function
        addDisposable(RxView.clicks(mBtnAlbum).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                    mPresenter.openAlbum();
                } else {
                    EasyPermissions.requestPermissions(DiscoveryFragment.this, getString(R.string.rationable_ask), PERM_OPEN_ALBUM, perms);
                }
            }
        }));
        //漂流瓶function core ，one of the most important functions
        addDisposable(RxView.clicks(mBtnBottle).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                startActivity(new Intent(getActivity(), DiscoveryDriftingBottleActivity.class));
            }
        }));
    }

    @Override
    public void showImage(Bitmap bitmap, ImageView view) {
        Glide.with(this).load(bitmap).into(view);
    }

    @Override
    public void openCamera() {
        app_photo = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/" + System.currentTimeMillis() + ".jpg");
        if (!app_photo.getParentFile().exists()) {
            app_photo.getParentFile().mkdirs();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uriForFile = FileProvider.getUriForFile(getActivity(), "com.yidao.platform.file_provider", app_photo);
        } else {
            uriForFile = Uri.fromFile(app_photo);
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void openAlbum() {
        choicePhotoWrapper(getActivity());
    }

    @Override
    public void showToast(String s) {
        ToastUtils.showToast(s);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    //不刷新相册 拍照的图片不会在相册显示
                    refreshAlbum(getActivity(), app_photo);
                    Intent takePhotoIntent = new Intent(getActivity(), DiscoveryEditorMessageActivity.class);
                    takePhotoIntent.putExtra("take_photo_path", app_photo.getAbsolutePath());
                    startActivity(takePhotoIntent);
                    break;
                case REQUEST_CHOOSE_PICTURE:
                    Intent choosePictureIntent = new Intent(getActivity(), DiscoveryEditorMessageActivity.class);
                    ArrayList<String> photos = BGAPhotoPickerActivity.getSelectedPhotos(data);
                    for (String photo : photos) {
                        MyLogger.d(photo.toString());
                    }
                    choosePictureIntent.putStringArrayListExtra("choose_picture_path", photos);
                    startActivity(choosePictureIntent);
                    break;
            }
        }
    }

    public static void refreshAlbum(Context context, File file) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

    private void choicePhotoWrapper(Context context) {
        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(context)
                .maxChooseCount(9) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();
        startActivityForResult(photoPickerIntent, REQUEST_CHOOSE_PICTURE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getActivity(), "onPermissionsGranted", Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case PERM_OPEN_ALBUM:
                if (perms.size() == 2) {
                    mPresenter.openAlbum();
                }
                break;
            case PERM_OPEN_CAMERA:
                //Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                if (perms.size() == 2){
                    mPresenter.openCamera();
                }
            case PERM_PREVIEW_PHOTO:
                photoPreviewWrapper();
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
            case PERM_PREVIEW_PHOTO:
                break;
        }
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Intent intent = new Intent(getActivity(), FriendsGroupDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
        return false;
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        requestPreviewPhotoPermission();
    }

    private void requestPreviewPhotoPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            photoPreviewWrapper();
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", PERM_PREVIEW_PHOTO, perms);
        }
    }

    private void photoPreviewWrapper() {
        if (mCurrentClickNpl == null) {
            return;
        }
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "com.yidao.platform");
        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(getActivity())
                .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能
        if (mCurrentClickNpl.getItemCount() == 1) {
            // 预览单张图片
            photoPreviewIntentBuilder.previewPhoto(mCurrentClickNpl.getCurrentClickItem());
        } else if (mCurrentClickNpl.getItemCount() > 1) {
            // 预览多张图片
            photoPreviewIntentBuilder.previewPhotos(mCurrentClickNpl.getData())
                    .currentPosition(mCurrentClickNpl.getCurrentClickItemPosition()); // 当前预览图片的索引
        }
        startActivity(photoPreviewIntentBuilder.build());
    }

    private class MomentAdapter extends BGARecyclerViewAdapter<Moment> {

        MomentAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.discovery_friends_item);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, Moment moment) {
            if (TextUtils.isEmpty(moment.content)) {
                helper.setVisibility(R.id.tv_discovery_content, View.GONE);
            } else {
                helper.setVisibility(R.id.tv_discovery_content, View.VISIBLE);
                helper.setText(R.id.tv_discovery_content, moment.content);
            }
            BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.npl_item_moment_photos);
            ninePhotoLayout.setDelegate(DiscoveryFragment.this);
            ninePhotoLayout.setData(moment.photos);
        }
    }
}
