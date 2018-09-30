package com.yidao.ocr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Random;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "MainActivity";
    private static final int PERM_OPEN_CAMERA = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private File app_photo;
    private TextView tvResult;
    private Uri uriForFile;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvResult = findViewById(R.id.textView);
        image = findViewById(R.id.imageView);
    }

    public void capture(View view) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            openCamera();
        } else {
            EasyPermissions.requestPermissions(this, "我要", PERM_OPEN_CAMERA, perms);
        }
    }

    @SuppressLint("CheckResult")
    public void upload(View view) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody appid = RequestBody.create(MediaType.parse("multipart/form-data"), "1257736280");
        RequestBody bucket = RequestBody.create(MediaType.parse("multipart/form-data"), "orc-20180929-1257736280");
        RequestBody ret_image = RequestBody.create(MediaType.parse("multipart/form-data"), "0");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpeg"), app_photo);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", app_photo.getName(), requestFile);
        RxHttpUtils
                .createApi(ApiService.class)
                .getAuthorization()
                .compose(Transformer.<String>switchSchedulers())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.e(s);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        JSONObject jsonObject = new JSONObject(s);
                        switch (jsonObject.getString("errCode")) {
                            case "1000":
                                String authorization = jsonObject.getString("result");
                                Logger.e(authorization);
                                break;
                        }
                        return RxHttpUtils.createApi(ApiService.class).getAuthorization();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("TAG",s);
                    }
                });

        RxHttpUtils
                .getSInstance()
                .baseUrl("http://recognition.image.myqcloud.com/")
                .createSApi(ApiService.class)
                .uploadFileToTencent(appid, bucket, ret_image, body)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Logger.e(s);
                        tvResult.setText(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "无法识别到名片，请重新上传！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }


    private void openCamera() {
        app_photo = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/" + "IMG_" + System.currentTimeMillis() + "_" + new Random().nextInt(100) + ".jpeg");
        if (!app_photo.getParentFile().exists()) {
            app_photo.getParentFile().mkdirs();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uriForFile = FileProvider.getUriForFile(this, "com.yidao.ocr.file_provider", app_photo);
        } else {
            uriForFile = Uri.fromFile(app_photo);
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case PERM_OPEN_CAMERA:
                if (perms.size() == 2) {
                    openCamera();
                }
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void refreshAlbum(Context context, File file) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    //不刷新相册 拍照的图片不会在相册显示
                    refreshAlbum(this, app_photo);
                    showImg(uriForFile);
                    break;
            }
        }
    }

    private void showImg(Uri uri) {
        ImageView image = findViewById(R.id.imageView);
        Glide.with(this).load(uri).into(image);
    }

    private void showImg(Bitmap bitmap) {
        Glide.with(this).load(bitmap).into(image);
    }
}
