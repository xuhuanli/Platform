package com.yidao.platform.app;

import android.content.Intent;
import android.os.Handler;

import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.login.view.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Token拦截器
 */
public class TokenInterceptor implements Interceptor {
    private String resultStr;
    private Handler mHandler = new Handler();

    @Override
    public Response intercept(Chain chain) throws IOException {
        String accessToken = IPreference.prefHolder.getPreference(MyApplicationLike.getAppContext()).get(Constant.STRING_USER_TOKEN, IPreference.DataType.STRING);
        if (accessToken == null) {
            accessToken = "";
        }
        Request request = chain.request().newBuilder()
                .header("token", accessToken)
                .build();
        MyLogger.e("发送出去的token: " + accessToken);
        Response response = chain.proceed(request);
        MediaType mediaType = response.body().contentType();
        if (isTokenExpired(response)) {
            //同步请求方式，获取最新的Token
            String newToken = getNewToken();
            //使用新的Token，创建新的请求
            if (null != newToken && newToken.length() > 0) {
                Request newRequest = chain.request()
                        .newBuilder()
                        .header("token", newToken)
                        .build();
                //重新请求上次的接口
                return chain.proceed(newRequest.newBuilder().build());
            } else {
                return null;
            }
        }
        Response build = response.newBuilder().body(ResponseBody.create(mediaType, resultStr)).build();
        return build;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        try {
            resultStr = response.body().string();
            MyLogger.e(resultStr);
            try {
                JSONObject jsonObject = new JSONObject(resultStr);
                String errCode = jsonObject.getString("errCode");
                return dispatchErrorCode(errCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean dispatchErrorCode(String errorCode) {
        switch (errorCode) {
            case "401":
                MyLogger.e("Token登录过期了");
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        String refreshToken = IPreference.prefHolder.getPreference(MyApplicationLike.getAppContext()).get(Constant.STRING_USER_REFRESHTOKEN, IPreference.DataType.STRING);
        // 通过获取token的接口，同步请求接口
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody
                .Builder()
                .add("refreshToken", refreshToken)
                .build();
        Request request = new Request
                .Builder()
                .post(body)
                .url(Constant.BASE_URL + "app/user/token-refresh")
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        MyLogger.e("保存。。。" + result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            switch (jsonObject.getString("errCode")) {
                case "1000":
                    String token = jsonObject.getJSONObject("result").getString("token");
                    IPreference.prefHolder.getPreference(MyApplicationLike.getAppContext()).put(Constant.STRING_USER_TOKEN, token);
                    response.body().close();
                    return token;
                case "1021":
                    mHandler.post(() -> jumpToLoginPage());
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.body().close();
        return null;
    }

    private void jumpToLoginPage() {
        IPreference.prefHolder.getPreference(MyApplicationLike.getAppContext()).remove(Constant.STRING_USER_ID);
        Intent intent = new Intent(MyApplicationLike.getAppContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplicationLike.getAppContext().startActivity(intent);
    }
}
