package com.yidao.ocr;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    /**
     * 获取鉴权
     */
    @GET("app/user/getTXOCRAuth")
    Observable<String> getAuthorization();

    @Multipart
    @Headers({"Host:recognition.image.myqcloud.com", "Authorization:OVnWfUmh0FGlNiXsuDSldp6rPaBhPTEyNTc3MzYyODAmYj1vcmMtMjAxODA5MjktMTI1NzczNjI4MCZrPUFLSURWdUp0TGJ5cGpTelFna25jT0h3UmtzcWhOd0tZcVlPNCZ0PTE1MzgyMTA4MzkmZT0xNTQxODEwODM5JnI9NDQ3MTcyNTM0"})
    @POST("ocr/businesscard")
    Observable<String> uploadFileToTencent(@Part("appid") RequestBody appid, @Part("bucket") RequestBody bucket, @Part("ret_image") RequestBody ret_image, @Part MultipartBody.Part file);
}
