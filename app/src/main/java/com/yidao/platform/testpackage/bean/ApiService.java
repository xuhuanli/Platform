package com.yidao.platform.testpackage.bean;

import com.yidao.platform.read.bean.ArticleBean;
import com.yidao.platform.read.bean.BannerBean;
import com.yidao.platform.read.bean.CategoryArticleExtBean;
import com.yidao.platform.read.bean.CommonArticleBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("product/style/get/1014051050182672384")
    Observable<TestBean> getGod();

    @GET("sns/oauth2/access_token")
    Observable<WxTokenBean> getWxToken(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String grant_type);

    @GET("sns/userinfo")
    Observable<UserDataBean> getUserInfo(@Query("access_token") String access_token, @Query("openid") String openid);

    /**
     * 查询banner(正在显示的)
     *
     * @return Observable
     */
    @GET("home/article/listShowBanners")
    Observable<BannerBean> getBanner();

    /**
     * 首页18篇文章
     *
     * @return Observable
     */
    @GET("home/article/listCategoryAndArticle")
    Observable<ArticleBean> getMainArticle();

    @POST("home/article/listArticleByDeploytime")
    Observable<CommonArticleBean> getCommonArticle();

    @POST("home/article/getCategoryArticleExt")
    Observable<CategoryArticleExtBean> getCategoryArticleExt(@QueryMap Map<String, String> options);
}
