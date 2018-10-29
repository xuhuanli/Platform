package com.yidao.platform.card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends BaseActivity {
    //相机预览
    @BindView(R.id.camera)
    CameraView camera;
    //返回
    @BindView(R.id.iv_back)
    ImageView iv_back;

    //图片预览
    @BindView(R.id.iv_scan)
    ImageView iv_scan;

    //拍照按钮
    @BindView(R.id.ll_takephoto)
    LinearLayout ll_takephoto;

    //跳转名片信息页
    @BindView(R.id.tv_jump)
    TextView tv_jump;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏功能
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);
                handlePicture(jpeg);

            }
        });
    }

    public static void startCameraActivity(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }

    private void handlePicture(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix matrix = new Matrix();
        matrix.postRotate((float) -90.0);

//        Bitmap sizeBitmap = Bitmap.createScaledBitmap(bitmap,
//                bitmap.getWidth() / 3, bitmap.getHeight() / 3, true);

        //裁剪bitmap
        int leftOffset = (int) (bitmap.getHeight() * 0.13);
        int topOffset = (int) (bitmap.getHeight() * 0.12);
        int height = (int) (bitmap.getHeight() * 0.12 * 0.82);
        Rect rect = new Rect(leftOffset, topOffset, bitmap.getWidth() - leftOffset, bitmap.getHeight() - height);
        Bitmap rectBitmap = Bitmap.createBitmap(bitmap,
                rect.left, rect.top, rect.width(), rect.height());
        rectBitmap = Bitmap.createBitmap(bitmap, 0, 0, rectBitmap.getWidth(),
                rectBitmap.getHeight(), matrix, false);


        try {
            File photoCut = new File(Environment.getExternalStorageDirectory(), "yidao" + File.separator + System.currentTimeMillis() + "photoCut.jpeg");
            FileOutputStream outputStreamCut = new FileOutputStream(photoCut);
            rectBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStreamCut);
            Glide.with(CameraActivity.this)
                    .load(rectBitmap)
                    .into(iv_scan);
            outputStreamCut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AuthenticateInfoActivity.startAuthenticateInfoActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (camera != null) {
            camera.start();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera.stop();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.destroy();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    public void takePick(View view) {
        if (camera != null) {
            camera.capturePicture();
        }

    }


    public static void byte2File(byte[] buf) {
        //
        File app_photo = new File(Environment.getExternalStorageDirectory(), "yidao" + File.separator + System.currentTimeMillis() + "card.jpeg");
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(app_photo);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 跳转到名片信息页
     *
     * @param view
     */
    public void jump(View view) {
    }

    public void back(View view) {
        finish();
    }

}
