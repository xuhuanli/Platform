package com.yidao.platform.app;

import com.yidao.platform.discovery.bean.SendFindObj;
import com.yidao.platform.info.model.UserCollectArtBean;
import com.yidao.platform.info.model.UserReadRecordBean;
import com.yidao.platform.read.bean.ArticleBean;
import com.yidao.platform.read.bean.BannerBean;
import com.yidao.platform.read.bean.CategoryArticleExtBean;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.bean.CommonArticleBean;
import com.yidao.platform.testpackage.bean.TestBean;
import com.yidao.platform.testpackage.bean.UserDataBean;
import com.yidao.platform.testpackage.bean.WxTokenBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    /**
     * 获取首页余下的普通文章
     *
     * @param options
     * @return
     */
    @POST("home/article/listArticleByDeploytime")
    Observable<CommonArticleBean> getCommonArticle(@QueryMap Map<String, String> options);

    /**
     * 获取类目的扩展文章
     *
     * @param options
     * @return
     */
    @POST("home/article/getCategoryArticleExt")
    Observable<CategoryArticleExtBean> getCategoryArticleExt(@QueryMap Map<String, String> options);

    /**
     * 查询类目 （首页 右上角的按钮）
     *
     * @return
     */
    @POST("home/article/listCategories")
    Observable<ChannelBean> getListCategories();

    //test user id 21211

    /**
     * 获取用户收藏的文章
     *
     * @param options
     * @return
     */
    @POST("user/center/listuserCollectArt")
    Observable<UserCollectArtBean> getUserCollectArt(@QueryMap Map<String, String> options);

    /**
     * 删除评论
     */
    @POST("user/center/delComment")
    Observable<String> delComment(@QueryMap Map<String, String> options);

    /**
     * 发布评论
     */
    @POST("user/center/pushComment")
    Observable<String> pushComment(@QueryMap Map<String, String> options);

    /**
     * 用户阅读
     */
    @POST("user/center/read")
    Observable<String> pushHasRead(@QueryMap Map<String, String> options);

    /**
     * 用户点赞
     */
    @POST("user/center/like")
    Observable<String> pushHasLike(@QueryMap Map<String, String> options);

    /**
     * 用户收藏
     */
    @POST("user/center/collect")
    Observable<String> pushHasCollect(@QueryMap Map<String, String> options);

    /**
     * 取消收藏
     */
    @POST("user/center/unCollect")
    Observable<String> unCollect(@QueryMap Map<String, String> options);

    /**
     * 取消点赞
     */
    @POST("user/center/unLike")
    Observable<String> unLike(@QueryMap Map<String, String> options);

    /**
     * 获取用户最近阅读
     *
     * @param options
     * @return
     */
    @POST("user/center/listUserReadArt")
    Observable<UserReadRecordBean> getListUserReadArt(@QueryMap Map<String, String> options);

    /**
     * 发布朋友圈图片上传路径到公司服务器 后期注意ip地址的修改
     */
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("edit/find/sendFind")
    Observable<String> sendFind(@Body SendFindObj sendFindObj);
}
