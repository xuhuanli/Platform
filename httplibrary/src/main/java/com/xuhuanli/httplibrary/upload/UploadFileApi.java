package com.xuhuanli.httplibrary.upload;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface UploadFileApi {

    @Multipart
    @POST
    Observable<ResponseBody> uploadImg(@Url String uploadUrl,
                                       @Part MultipartBody.Part file);


    @Multipart
    @POST
    Observable<ResponseBody> uploadImgs(@Url String uploadUrl,
                                        @Part List<MultipartBody.Part> files);
}
