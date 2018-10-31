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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.bumptech.glide.Glide;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.yidao.platform.R;
import com.yidao.platform.app.ApiService;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.UIUtil;
import com.yidao.platform.card.bean.AuthenticateInfo;
import com.yidao.platform.card.bean.ItemInfo;
import com.yidao.platform.card.model.UploadCardObj;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
    //跳转名片信息页
    @BindView(R.id.pbNormal)
    ProgressBar pbNormal;

    private String path = "";
    UploadCardObj mUploadCardObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏功能
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mUploadCardObj = new UploadCardObj();
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handlePicture(jpeg);
                    }
                }).start();


            }
        });
    }

    public static void startCameraActivity(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }

    private void handlePicture(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//        //裁剪bitmap
//        int leftOffset = (int) (bitmap.getHeight() * 0.13);
//        int topOffset = (int) (bitmap.getHeight() * 0.12);
//        int height = (int) (bitmap.getHeight() * 0.12 * 0.82);
//        int i = UIUtil.dip2px(CameraActivity.this, 421);
//        Rect rect = new Rect(leftOffset, topOffset, bitmap.getWidth() - leftOffset, topOffset + i);
        Bitmap rectBitmap = Bitmap.createBitmap(bitmap);
        Matrix matrix = new Matrix();
        matrix.postRotate((float) -90.0);
        rectBitmap = Bitmap.createBitmap(bitmap, 0, 0, rectBitmap.getWidth(), rectBitmap.getHeight(), matrix, false);
        try {
            path = "yidao" + File.separator + System.currentTimeMillis() + "photoCut.jpeg";
            String filepath=Environment.getExternalStorageDirectory()+"/yidao";
            File file=new File(filepath);
            if (!file.exists()) {
                file.mkdir();
            }
            File photoCut = new File(Environment.getExternalStorageDirectory(), path);
            FileOutputStream outputStreamCut = new FileOutputStream(photoCut);
            rectBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStreamCut);
            upload(photoCut);
        } catch (Exception e) {

            e.printStackTrace();
        }
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
            pbNormal.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 跳转到名片信息页
     *
     * @param view
     */
    public void jump(View view) {
        AuthenticateInfoActivity.startAuthenticateInfoActivity(this, path, mUploadCardObj);
        finish();
    }

    public void upload(File app_photo) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody appid = RequestBody.create(MediaType.parse("multipart/form-data"), "1257736280");
        RequestBody bucket = RequestBody.create(MediaType.parse("multipart/form-data"), "orc-20180929-1257736280");
        RequestBody ret_image = RequestBody.create(MediaType.parse("multipart/form-data"), "0");
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), app_photo);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", app_photo.getName(), requestFile);
        Map<String, Object> map = new HashMap();
        map.put("Host", "recognition.image.myqcloud.com");
        map.put("Authorization", "OVnWfUmh0FGlNiXsuDSldp6rPaBhPTEyNTc3MzYyODAmYj1vcmMtMjAxODA5MjktMTI1NzczNjI4MCZrPUFLSURWdUp0TGJ5cGpTelFna25jT0h3UmtzcWhOd0tZcVlPNCZ0PTE1MzgyMTA4MzkmZT0xNTQxODEwODM5JnI9NDQ3MTcyNTM0");
        RxHttpUtils
                .getSInstance()
                .baseUrl("http://recognition.image.myqcloud.com/")
                .addHeaders(map)
                .createSApi(ApiService.class)
                .uploadFileToTencent(appid, bucket, ret_image, body)
                .compose(Transformer.<AuthenticateInfo>switchSchedulers())
                .subscribe(new Observer<AuthenticateInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AuthenticateInfo mAuthenticateInfo) {
                        getUploadInfo(mAuthenticateInfo);


                    }

                    @Override
                    public void onError(Throwable e) {
                        pbNormal.setVisibility(View.INVISIBLE);
                        Toast.makeText(CameraActivity.this, "无法识别到名片，请重新上传！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getUploadInfo(AuthenticateInfo mAuthenticateInfo) {

        /**
         * userId : 435434534211111
         * name : 嘿嘿
         * company : 驿道科技
         * post : JJ开发
         * phoneNum : 17621104288
         * companyAddr : 杭州滨江
         * email : 107028109555@qq.com
         * cardUrl : QXXX
         * certType : 1
         * certNum : 420602199608130540
         * businessId : 1
         * masterLabelId : [1,2]
         * isMaster : 1
         *
         * 本接口用于根据用户上传的名片图片，返回识别出的20 多个字段信息，详细字段如下：
         *
         * 姓名、英文姓名、职位、英文职位、部门、英文部门、公司、英文公司、地址、英文地址、邮编、邮箱、网址、手机、电话、传真、QQ、MSN、微信、微博、公司账号、logo、其他。
         */

        ArrayList<ItemInfo> arrayList = new ArrayList<>();
        arrayList.add(new ItemInfo("姓名", "name"));
        arrayList.add(new ItemInfo("公司", "company"));
        arrayList.add(new ItemInfo("职位", "post"));
        arrayList.add(new ItemInfo("手机", "phoneNum"));
        arrayList.add(new ItemInfo("地址", "companyAddr"));
        arrayList.add(new ItemInfo("邮箱", "email"));
        List<AuthenticateInfo.ResultListBean> result_list = mAuthenticateInfo.getResult_list();
        if (result_list.size() > 0) {
            AuthenticateInfo.ResultListBean resultListBean = result_list.get(0);
            List<AuthenticateInfo.ResultListBean.DataBean> data = resultListBean.getData();
            if (data != null) {
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < arrayList.size(); j++) {
                        if (arrayList.get(j).getKey().equals(data.get(i).getItem())) {
                            switch (arrayList.get(j).getKey()) {
                                case "姓名":
                                    mUploadCardObj.setName(data.get(i).getValue());
                                    break;
                                case "公司":
                                    mUploadCardObj.setCompany(data.get(i).getValue());
                                    break;
                                case "职位":
                                    mUploadCardObj.setPost(data.get(i).getValue());
                                    break;
                                case "手机":
                                    mUploadCardObj.setPhoneNum(data.get(i).getValue());
                                    break;
                                case "地址":
                                    mUploadCardObj.setCompanyAddr(data.get(i).getValue());
                                    break;
                                case "邮箱":
                                    mUploadCardObj.setEmail(data.get(i).getValue());
                                    break;
                            }

                        }

                    }

                }
            }
        }


        AuthenticateInfoActivity.startAuthenticateInfoActivity(this, path, mUploadCardObj);
        finish();


    }


    public void back(View view) {
        finish();
    }

}
