package com.yidao.platform.app;

import com.yidao.platform.discovery.bean.BottleDtlBean;
import com.yidao.platform.discovery.bean.FindContentBean;
import com.yidao.platform.discovery.bean.FriendsListBean;
import com.yidao.platform.discovery.bean.PyqCommentsBean;
import com.yidao.platform.discovery.model.DeletePyqObj;
import com.yidao.platform.discovery.model.DianZanObj;
import com.yidao.platform.discovery.model.FindDiscoveryObj;
import com.yidao.platform.discovery.model.PyqCommentsObj;
import com.yidao.platform.discovery.model.PyqFindIdObj;
import com.yidao.platform.discovery.model.QryFindContentObj;
import com.yidao.platform.discovery.model.ReplyBottleListObj;
import com.yidao.platform.discovery.model.ReplyBottleObj;
import com.yidao.platform.discovery.model.SendFindObj;
import com.yidao.platform.discovery.model.ThrowBottleObj;
import com.yidao.platform.info.model.BlackListBean;
import com.yidao.platform.info.model.BottleMsgBean;
import com.yidao.platform.info.model.FindMsgBean;
import com.yidao.platform.info.model.MineInfoBean;
import com.yidao.platform.info.model.UserCollectArtBean;
import com.yidao.platform.info.model.UserInfoBean;
import com.yidao.platform.info.model.UserReadRecordBean;
import com.yidao.platform.login.bean.WxCodeBean;
import com.yidao.platform.login.model.BindPhoneObj;
import com.yidao.platform.login.model.LoginObj;
import com.yidao.platform.read.bean.ArticleBean;
import com.yidao.platform.read.bean.BannerBean;
import com.yidao.platform.read.bean.CategoryArticleExtBean;
import com.yidao.platform.read.bean.ChannelBean;
import com.yidao.platform.read.bean.CommonArticleBean;
import com.yidao.platform.read.bean.HotCommentsBean;
import com.yidao.platform.read.bean.LastCommentsBean;
import com.yidao.platform.read.bean.PushCommBean;
import com.yidao.platform.read.bean.RefreshTokenObj;
import com.yidao.platform.read.bean.SearchBean;
import com.yidao.platform.read.bean.ShareBean;
import com.yidao.platform.service.model.BpObj;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    //-------------登录模块---------------

    /**
     * 刷新token
     */
    @FormUrlEncoded
    @POST("app/user/token-refresh")
    Observable<String> refreshToken(@Field("refreshToken") String refreshToken);

    /**
     * 发送code到server
     */
    @GET("app/user/getOssToken")
    Observable<OssBean> getUploadMsg();

    /**
     * 发送code到server
     */
    @GET("app/user/login/wx")
    Observable<WxCodeBean> sendCodeToServer(@QueryMap Map<String, String> options);

    /**
     * 发送验证码
     */
    @GET("app/phone/send-code")
    Observable<String> sendVCode(@Query("phone") String phone);

    /**
     * 发送验证码
     */
    @GET("app/phone/send-code")
    Observable<String> sendVCode(@Query("phone") String phone, @Query("userId") String userId);

    /**
     * 绑定手机号@Field("phoneVerificationCode") String vCode,@Field("userId") String userId
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/phone/bind")
    Observable<String> bindPhone(@Body BindPhoneObj bindPhoneObj);

    /**
     * 手机登录
     *
     * @param loginObj
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("/app/phone/login")
    Observable<WxCodeBean> phoneSignIn(@Body LoginObj loginObj);

    /**
     * 手机注册
     *
     * @param loginObj
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("/app/phone/register")
    Observable<WxCodeBean> registerAccount(@Body LoginObj loginObj);

    //---------个人中心-------------

    /**
     * 修改个人信息 后期注意ip地址的修改
     */
    @FormUrlEncoded
    @POST("app/user/updateUserInfo")
    Observable<String> updateUserInfo(@FieldMap Map<String, String> options);

    /**
     * 按id查询用户明细
     */
    @GET("app/user/qryUserById")
    Observable<UserInfoBean> qryUserById(@Query("id") String userId);

    /**
     * 获取'我的'页面参数
     */
    @GET("app/mine")
    Observable<MineInfoBean> getMineInfo(@Query("userId") String userId);

    /**
     * 查看漂流瓶消息
     */
    @GET("app/message/qryBottleMess")
    Observable<BottleMsgBean> qryBottleMess(@Query("userId") String userId, @Query("index") int index, @Query("size") int size);

    /**
     * 查看朋友圈消息
     */
    @GET("app/message/qryFindMess")
    Observable<FindMsgBean> qryFindMess(@Query("userId") String userId, @Query("index") int index, @Query("size") int size);

    /**
     * 更新消息为已读
     */
    @GET("app/message/upMessageStat")
    Observable<String> upMessageStat(@Query("messageId") String messageId);
    //----------服务模块---------------

    /**
     * 申请 bp  后期注意ip地址的修改 10.10.20.27:8080
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/bp/apply")
    Observable<String> bpApply(@Body BpObj bpObj);

    //----------朋友圈模块----------

    /**
     * 屏蔽用户
     */
    @GET("app/edit/find/shieldUser")
    Observable<String> shieldUser(@Query("shUserId") String shUserId, @Query("userId") String userId);

    /**
     * 取消屏蔽
     */
    @GET("app/edit/find/cancelShieldUser")
    Observable<String> cancelShieldUser(@Query("shUserId") String shUserId, @Query("userId") String userId);

    /**
     * 查询被屏蔽用户列表
     */
    @GET("app/find/qryShieldUsers")
    Observable<BlackListBean> qryShieldUsers(@Query("index") int index, @Query("size") int size, @Query("userId") String userId);

    /**
     * 发布朋友圈图片上传路径到公司服务器 后期注意ip地址的修改
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/edit/find/sendFind")
    Observable<String> sendFind(@Body SendFindObj sendFindObj);

    /**
     * 获取朋友圈列表 后期注意ip地址的修改
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/find/qryFindMagList")
    Observable<FriendsListBean> getFriendsList(@Body FindDiscoveryObj findDiscoveryObj);

    /**
     * 获取朋友圈列表
     */
    @GET("app/find/qryFindLatestNew")
    Observable<FriendsListBean> getFriendsList(@Query("size") int size, @Query("userId") String userId);

    /**
     * 获取朋友圈加载更多
     */
    @GET("app/find/qryFindHisNew")
    Observable<FriendsListBean> qryFindHis(@Query("size") int size, @Query("lastFindId") String lastFindId, @Query("userId") String userId);

    /**
     * 朋友圈点赞
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/edit/find/sendFindLike")
    Observable<String> sendFindLike(@Body DianZanObj dianZanObj);

    /**
     * 取消朋友圈点赞
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/edit/find/cancelFindLike")
    Observable<String> cancelFindLike(@Body DianZanObj dianZanObj);

    /**
     * 获取朋友圈评论详情 testId = 695106241363968
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/find/qryFindComms")
    Observable<PyqCommentsBean> qryFindComms(@Body PyqFindIdObj pyqCommentsObj);

    /**
     * 删除朋友圈 共用一下body
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/edit/find/deleteFind")
    Observable<String> deleteFind(@Body PyqFindIdObj pyqFindIdObj);

    /**
     * 发送朋友圈评论
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/edit/find/sendFindComm")
    Observable<PyqCommentsBean> sendFindComm(@Body PyqCommentsObj pyqCommentsObj);

    /**
     * 删除朋友圈评论
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/edit/find/deleteFindComm")
    Observable<String> deleteFindComm(@Body DeletePyqObj deletePyqObj);

    /**
     * 查询朋友圈详情
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/find/qryFindContent")
    Observable<FindContentBean> qryFindContent(@Body QryFindContentObj qryFindContentObj);
    //----------文章模块------------

    /**
     * 分享回调
     */
    @GET("user/center/shareResult")
    Observable<String> shareResult(@Query("artId") String artId, @Query("userId") String userId);

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
    @FormUrlEncoded
    @POST("home/article/listArticleByDeploytime")
    Observable<CommonArticleBean> getCommonArticle(@FieldMap Map<String, String> options);

    /**
     * 获取类目的扩展文章
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST("home/article/getCategoryArticleExt")
    Observable<CategoryArticleExtBean> getCategoryArticleExt(@FieldMap Map<String, String> options);

    /**
     * 查询类目 （首页 右上角的按钮）
     *
     * @return
     */
    @POST("home/article/listCategories")
    Observable<ChannelBean> getListCategories();

    /**
     * 获取用户收藏的文章
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST("user/center/listuserCollectArt")
    Observable<UserCollectArtBean> getUserCollectArt(@FieldMap Map<String, String> options);

    /**
     * 删除评论
     */
    @FormUrlEncoded
    @POST("user/center/delComment")
    Observable<String> delComment(@FieldMap Map<String, String> options);

    /**
     * 发布评论
     */
    @FormUrlEncoded
    @POST("user/center/pushComment")
    Observable<PushCommBean> pushComment(@FieldMap Map<String, String> options);

    /**
     * 用户阅读
     */
    @FormUrlEncoded
    @POST("user/center/read")
    Observable<String> pushHasRead(@FieldMap Map<String, String> options);

    /**
     * 用户点赞
     */
    @FormUrlEncoded
    @POST("user/center/like")
    Observable<String> pushHasLike(@FieldMap Map<String, String> options);

    /**
     * 用户收藏
     */
    @FormUrlEncoded
    @POST("user/center/collect")
    Observable<String> pushHasCollect(@FieldMap Map<String, String> options);

    /**
     * 取消收藏
     */
    @FormUrlEncoded
    @POST("user/center/unCollect")
    Observable<String> unCollect(@FieldMap Map<String, String> options);

    /**
     * 取消点赞
     */
    @FormUrlEncoded
    @POST("user/center/unLike")
    Observable<String> unLike(@FieldMap Map<String, String> options);

    /**
     * 获取用户最近阅读
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST("user/center/listUserReadArt")
    Observable<UserReadRecordBean> getListUserReadArt(@FieldMap Map<String, String> options);

    /**
     * 标题搜索
     * warning 参数中带有中文的 必须用Filed形式 而不是Query
     */
    @FormUrlEncoded
    @POST("home/article/searchArticle")
    Observable<SearchBean> searchArticle(@FieldMap Map<String, String> options);

    /**
     * 获取文章热评
     *
     * @param artId
     * @return
     */
    @GET("home/article/listArticleCommenty")
    Observable<HotCommentsBean> getHotComments(@Query("id") long artId, @Query("cruId") String cruId);

    /**
     * 获取文章最新评论
     *
     * @param artId
     * @return
     */
    @GET("home/article/listArticleCommentyList")
    Observable<LastCommentsBean> getLastComments(@Query("id") long artId, @Query("pageIndex") long pageIndex, @Query("pageSize") int pageSize, @Query("cruId") String cruId);

    /**
     * 文章评论点赞
     *
     * @param commentId
     * @param userId
     * @return
     */
    @GET("user/center/userLikeComment")
    Observable<String> userLikeComment(@Query("commentId") String commentId, @Query("userId") String userId);

    /**
     * 评论取消点赞
     *
     * @param commentId
     * @param userId
     * @return
     */
    @GET("user/center/userUnLikeComment")
    Observable<String> userUnLikeComment(@Query("commentId") String commentId, @Query("userId") String userId);

    /**
     * 获取分享文章的内容
     *
     * @param artId
     * @return
     */
    @GET("home/article/selectArt")
    Observable<ShareBean> getShareContent(@Query("id") String artId);
    //-------------漂流瓶----------------

    /**
     * 扔瓶子次数验证
     */
    @GET("app/bottle/validThrowTimes")
    Observable<String> validThrowTimes(@Query("userId") String userId);

    /**
     * 扔瓶子
     *
     * @param throwBottleObj
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/bottle/throwBottle")
    Observable<String> throwBottle(@Body ThrowBottleObj throwBottleObj);

    /**
     * 捡瓶子次数验证
     */
    @GET("app/bottle/validPickTimes")
    Observable<String> validPickTimes(@Query("userId") String userId);

    /**
     * 捡瓶子
     */
    @GET("app/bottle/pickBottle")
    Observable<String> pickBottle(@Query("userId") String userId);

    /**
     * 查看漂流瓶列表
     */
    @GET("app/bottle/qryBottleList")
    Observable<String> qryBottleList(@Query("userId") String userId, @Query("index") String index, @Query("size") String size);

    /**
     * 删除瓶子
     */
    @GET("app/bottle/deleteBottle")
    Observable<String> deleteBottle(@Query("bottleId") String bottleId, @Query("userId") String userId);

    /**
     * 瓶子回复
     *
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/bottle/replyBottle")
    Observable<String> replyBottle(@Body ReplyBottleObj replyBottleObj);

    /**
     * 瓶子回复
     *
     * @return
     */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("app/bottle/replyBottleList")
    Observable<String> replyBottleList(@Body ReplyBottleListObj replyBottleListObj);

    /**
     * 查看漂流瓶详情
     */
    @GET("app/bottle/qryBottleDtl")
    Observable<BottleDtlBean> qryBottleDtl(@Query("bottleId") String bottleId, @Query("sessionId") String sessionId);
}
