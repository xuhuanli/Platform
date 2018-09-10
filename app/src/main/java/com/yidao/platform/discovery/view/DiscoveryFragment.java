package com.yidao.platform.discovery.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.app.utils.FileUtil;
import com.yidao.platform.discovery.adapter.MomentAdapter;
import com.yidao.platform.discovery.bean.FriendsShowBean;
import com.yidao.platform.discovery.model.DianZanObj;
import com.yidao.platform.discovery.presenter.DiscoveryPresenter;
import com.yidao.platform.events.RefreshDiscoveryEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class DiscoveryFragment extends BaseFragment implements DiscoveryViewInterface, EasyPermissions.PermissionCallbacks, BGANinePhotoLayout.Delegate {

    //权限的常量
    private static final int PERM_OPEN_ALBUM = 0;
    private static final int PERM_OPEN_CAMERA = 1;
    private static final int PERM_PREVIEW_PHOTO = 2;
    //startActivityForResult的常量
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int REQUEST_CHOOSE_PICTURE = 101;
    private static final int REQUEST_DETAIL = 102;
    @BindView(R.id.btn_take_photo)
    TextView mBtnPhoto;
    @BindView(R.id.btn_album)
    TextView mBtnAlbum;
    @BindView(R.id.btn_bottle)
    TextView mBtnBottle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_select_item)
    ImageView mIvIcon;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private DiscoveryPresenter mPresenter;
    private MomentAdapter mAdapter;
    private BGANinePhotoLayout mCurrentClickNpl;
    private File app_photo;
    private String userId;
    private DianZanObj dianZanObj;

    @Override
    protected void initView() {
        mPresenter = new DiscoveryPresenter(this);
        EventBus.getDefault().register(this);
        initToolbar();
        initRecyclerView();
        initSwipeRefreshLayout();
        mRecyclerView.addOnScrollListener(new BGARVOnScrollListener(getActivity()));
        //拍照function
        addDisposable(RxView.clicks(mBtnPhoto).subscribe(o -> {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                mPresenter.openCamera();
            } else {
                EasyPermissions.requestPermissions(DiscoveryFragment.this, getString(R.string.rationable_ask), PERM_OPEN_CAMERA, perms);
            }
        }));
        //相册function
        addDisposable(RxView.clicks(mBtnAlbum).subscribe(o -> {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                mPresenter.openAlbum();
            } else {
                EasyPermissions.requestPermissions(DiscoveryFragment.this, getString(R.string.rationable_ask), PERM_OPEN_ALBUM, perms);
            }
        }));
        //漂流瓶function core ，one of the most important functions
        addDisposable(RxView.clicks(mBtnBottle).subscribe(o -> startActivity(new Intent(getActivity(), DiscoveryDriftingBottleActivity.class))));
        userId = IPreference.prefHolder.getPreference(getActivity()).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
    }

    @Override
    protected void initData() {
        mPresenter.getFriendsList(Constant.PAGE_SIZE, userId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(null);
    }

    private void initToolbar() {
        addDisposable(RxView.clicks(mIvIcon).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            Intent intent = new Intent(getActivity(), DiscoveryEditorMessageActivity.class);
            startActivity(intent);
        }));
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
    }

    private void refresh() {
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        }
        mPresenter.getFriendsList(Constant.PAGE_SIZE, userId);
    }

    /**
     * 需要刷新列表的事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefrshEvent(RefreshDiscoveryEvent event) {
        refresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.discovery_fragment_content;
    }

    @Override
    public void showImage(Bitmap bitmap, ImageView view) {
        Glide.with(this).load(bitmap).into(view);
    }

    @Override
    public void openCamera() {
        app_photo = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/" + "IMG_" + FileUtil.formateTime() + ".jpg");
        if (!app_photo.getParentFile().exists()) {
            app_photo.getParentFile().mkdirs();
        }
        Uri uriForFile;
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
    public void loadMoreEnd(boolean b) {
        if (mAdapter != null) {
            mAdapter.loadMoreEnd(b);
        }
    }

    @Override
    public void loadMoreComplete() {
        if (mAdapter != null) {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadRecyclerData(ArrayList<FriendsShowBean> dataList) {
        mAdapter = new MomentAdapter(dataList, this);
        mAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), FriendsGroupDetailActivity.class);
            intent.putExtra(Constant.STRING_FIND_ID, ((FriendsShowBean) adapter.getItem(position)).getFindId());
            startActivityForResult(intent, REQUEST_DETAIL);
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            FriendsShowBean item = (FriendsShowBean) adapter.getItem(position);
            switch (view.getId()) {
                case R.id.tv_discovery_vote:
                    boolean isLike = item.isLike();
                    if (dianZanObj == null) {
                        dianZanObj = new DianZanObj();
                    }
                    dianZanObj.setUserId(userId);
                    dianZanObj.setFindId(item.getFindId());
                    if (isLike) {  //已点赞，点击后变成不点赞 传服务器
                        mPresenter.cancelFindLike(dianZanObj);
                        item.setLikeAmount(item.getLikeAmount() - 1);
                    } else {
                        mPresenter.sendFindLike(dianZanObj);
                        item.setLikeAmount(item.getLikeAmount() + 1);
                    }
                    item.setLike(!isLike);
                    mAdapter.notifyItemChanged(position);
                    break;
                case R.id.iv_baned:
                    showAlertDialog(R.string.shield, (dialog, which) -> mPresenter.shieldUser(item.getDeployId(), userId));
                    break;
            }
        });
    }

    private void showAlertDialog(int messageId, DialogInterface.OnClickListener positiveListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setMessage(messageId)
                .setPositiveButton(R.string.ensure, positiveListener)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
    }

    private void loadMore() {
        List<FriendsShowBean> dataList = mAdapter.getData();
        FriendsShowBean bean = mAdapter.getData().get(dataList.size() - 1);
        String findId = bean.getFindId();
        mPresenter.qryFindHis(Constant.PAGE_SIZE, findId, userId);
    }

    @Override
    public void loadMoreData(ArrayList<FriendsShowBean> dataList) {
        if (mAdapter != null) {
            mAdapter.addData(dataList);
        }
    }

    @Override
    public void setEnableLoadMore(boolean b) {
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(b);
        }
    }

    @Override
    public void setRefreshing(boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void shieldSuccess() {
        refresh();
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
                    choosePictureIntent.putStringArrayListExtra("choose_picture_path", photos);
                    startActivity(choosePictureIntent);
                    break;
                case REQUEST_DETAIL:
                    if (data != null) {
                        String findId = data.getStringExtra(Constant.STRING_FIND_ID);
                        boolean isLike = data.getBooleanExtra(Constant.STRING_ISLIKE, false);
                        long likeAmount = data.getLongExtra(Constant.STRING_LIKE_AMOUNT, 0);
                        List<FriendsShowBean> dataList = mAdapter.getData();
                        for (int i = 0; i < dataList.size(); i++) {
                            if (TextUtils.equals(dataList.get(i).getFindId(), findId)) {
                                dataList.get(i).setLike(isLike);
                                dataList.get(i).setLikeAmount(likeAmount);
                                mAdapter.notifyItemChanged(i);
                            }
                        }
                    }
                    break;
            }
        }
    }

    private void refreshAlbum(Context context, File file) {
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
        switch (requestCode) {
            case PERM_OPEN_ALBUM:
                if (perms.size() == 2) {
                    mPresenter.openAlbum();
                }
                break;
            case PERM_OPEN_CAMERA:
                if (perms.size() == 2) {
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
            case PERM_OPEN_CAMERA:
            case PERM_PREVIEW_PHOTO:
                ToastUtils.showToast("相关权限被拒绝");
                break;
        }
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
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "yidao");
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
}
